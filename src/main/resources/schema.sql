CREATE DATABASE IF NOT EXISTS pkucr_database;
USE pkucr_database;


CREATE TABLE IF NOT EXISTS users (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    permission INT,
    username VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS courses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    courseID VARCHAR(255),
    courseName VARCHAR(255),
    teacher VARCHAR(255),
    credit INT,
    classroom VARCHAR(255),
    time VARCHAR(255),
    week VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS all_courses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    courseID VARCHAR(255),
    courseName VARCHAR(255),
    category VARCHAR(255),
    teacher VARCHAR(255),
    credit INT,
    classroom VARCHAR(255),
    time VARCHAR(255),
    week VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS tasks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    name VARCHAR(255) NOT NULL,
    date DATETIME,
    priority INT,
    description TEXT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS comments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    course_id VARCHAR(255),
    content TEXT,
    time DATETIME,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS materials (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    userID BIGINT,
    courseID VARCHAR(255),
    filename VARCHAR(255) NOT NULL,
    filedir VARCHAR(255) NOT NULL,
    url VARCHAR(255),
    time DATETIME,
    FOREIGN KEY (userID) REFERENCES users(id)
);