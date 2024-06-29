DROP TABLE IF EXISTS users;
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(50) NOT NULL,
    name VARCHAR(50) NOT NULL ,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP,
    modified_at TIMESTAMP,
    last_login TIMESTAMP,
    is_active BOOLEAN NOT NULL,
    jwt_token VARCHAR,
    role VARCHAR(30) NOT NULL
);

DROP TABLE IF EXISTS phone;
CREATE TABLE phone (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    number VARCHAR(20) NOT NULL,
    citycode VARCHAR(10) NOT NULL,
    contrycode VARCHAR(10) NOT NULL,
    user_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

INSERT INTO users (user_id, name, email, password, is_active, role)
VALUES ('21d7914e-3a8a-439a-80c0-7c6ae439c904', 'Miguel San Juan M.', 'miguel.sanjuanm@gmail.com', '.p4ssword.2024.', 'true', 'ADMIN');
