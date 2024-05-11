CREATE TABLE channel_message
(
    id                  BIGINT      GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    sender_user_info_id BIGINT      NOT NULL,
    chat_channel_id     BIGINT      NOT NULL,
    content_type        VARCHAR(63) NOT NULL,
    content_body        TEXT        NOT NULL,
    version             INT         NOT NULL DEFAULT 0,
    create_time         TIMESTAMP   NOT NULL,
    update_time         TIMESTAMP   NOT NULL
);

CREATE TABLE chat_channel
(
    id                   BIGINT       GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name                 VARCHAR(255) NOT NULL,
    creator_user_info_id BIGINT NOT NULL,
    version              INT          NOT NULL DEFAULT 0,
    create_time          TIMESTAMP    NOT NULL,
    update_time          TIMESTAMP    NOT NULL
);