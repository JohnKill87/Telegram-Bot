package pro.sky.telegrambot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "notification_task")
public class NotificationTask {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private Long chatId;
    private String massage;
    private LocalDateTime rightDateTime;

    public NotificationTask(Long id, Long chatId, String massage, LocalDateTime rightDateTime) {
        this.id = id;
        this.chatId = chatId;
        this.massage = massage;
        this.rightDateTime = rightDateTime;
    }

    public NotificationTask() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public LocalDateTime getRightDateTime() {
        return rightDateTime;
    }

    public void setRightDateTime(LocalDateTime rightDateTime) {
        this.rightDateTime = rightDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationTask that = (NotificationTask) o;
        return Objects.equals(id, that.id) && Objects.equals(chatId, that.chatId) && Objects.equals(massage, that.massage) && Objects.equals(rightDateTime, that.rightDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, massage, rightDateTime);
    }

    @Override
    public String toString() {
        return "NotificationTask{" +
                "notificationTaskId=" + id +
                ", chatId=" + chatId +
                ", massage='" + massage + '\'' +
                ", rightDateTime=" + rightDateTime +
                '}';
    }
}
