-- liquibase formatted sql

-- changeset john:1
CREATE TABLE notification_task (
    id SERIAL PRIMARY KEY,
    chat_id SERIAL NOT NULL,
    massage TEXT NOT NULL,
    right_date_time TIMESTAMP NOT NULL
)
