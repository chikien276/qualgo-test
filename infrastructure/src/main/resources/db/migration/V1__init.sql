CREATE TABLE user_info
(
    id          BIGINT       NOT NULL PRIMARY KEY,
    username    VARCHAR(255) NOT NULL,
    email       VARCHAR(255) NULL,
    password    TEXT         NOT NULL,
    version     INT          NOT NULL DEFAULT 0,
    create_time TIMESTAMP    NOT NULL,
    update_time TIMESTAMP    NOT NULL,
    constraint  uni_username UNIQUE (username)
);
