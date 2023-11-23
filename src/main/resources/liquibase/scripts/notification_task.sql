-- liquibase formatted sql

-- changeset john:1
CREATE TABLE notification_task (
    id BIGINT PRIMARY KEY,
    chat_id BIGINT NOT NULL,
    massage TEXT NOT NULL,
    right_date_time TIMESTAMP NOT NULL
)
