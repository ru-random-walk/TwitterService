CREATE TABLE IF NOT EXISTS USER_ACCOUNT(
    USER_ID uuid primary key,
    EMAIL varchar,
    PUSH_DISABLED bool default false
);

CREATE TABLE IF NOT EXISTS DEVICE(
    ID uuid primary key,
    DEVICE_TOKEN varchar,
    USER_ID uuid references USER_ACCOUNT,
    CREATED_AT timestamptz
);