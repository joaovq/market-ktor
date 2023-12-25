CREATE TABLE IF NOT EXISTS user_tb (
    id UUID PRIMARY KEY,
    user_username VARCHAR(128) NOT NULL,
    user_password VARCHAR(255) NOT NULL,
    user_email VARCHAR(255) NOT NULL,
    user_active BOOLEAN DEFAULT TRUE NOT NULL,
    user_role VARCHAR(30) NOT NULL
);
ALTER TABLE user_tb ADD CONSTRAINT user_tb_unique_username UNIQUE (user_username);