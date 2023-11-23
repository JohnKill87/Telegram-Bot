package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;

    @Autowired
    private NotificationTaskRepository notificationTaskRepository;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            String messageText = update.message().text();
            long chatId = update.message().chat().id();
            if (messageText.equals("/start")) {
                SendResponse response = telegramBot.execute(new SendMessage(chatId, "Приветствую хозяин!"));
            }

            Pattern pattern = Pattern.compile("([0-9\\.\\:\\s]{16})(\\s)([\\W+]+)");

            Matcher matcher = pattern.matcher(messageText);
            CharSequence dateTime = null;
            if (matcher.matches()) {
                dateTime = matcher.group(1);
            }

            System.out.println("messageText = " + messageText);
            System.out.println("chatId = " + chatId);
            System.out.println("dateTime = " + dateTime);

            if (dateTime != null) {

                LocalDateTime localDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));

                NotificationTask notificationTask = new NotificationTask
                        (1L, chatId, messageText, localDateTime);

                notificationTaskRepository.save(notificationTask);
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @Scheduled(cron =  "0 0/1 * * * *")
    public void scheduledProgram() {

        logger.info("scheduledProgram starts");
        LocalDateTime localDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        logger.info("rightDateTime now = {}", localDateTime);

        List<NotificationTask> notificationTasks = notificationTaskRepository
                .findByRightDateTime(localDateTime);
        notificationTasks
                .forEach(notificationTask -> {
                    SendResponse responseMessage = telegramBot.execute(new SendMessage(
                            notificationTask.getChatId(),
                            notificationTask.getMassage()));
                    if (responseMessage.isOk()) {
                        logger.info("responseMessage = OK");
                    } else {
                        logger.info("responseMessage = BAD");
                    }
                });
    }
}
