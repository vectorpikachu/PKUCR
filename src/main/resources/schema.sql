CREATE TABLE IF NOT EXISTS users (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    permission INT
);

CREATE TABLE IF NOT EXISTS courses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    courseID VARCHAR(255),
    courseName VARCHAR(255),
    teacher VARCHAR(255),
    credit INT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS all_courses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    courseID VARCHAR(255),
    courseName VARCHAR(255),
    category VARCHAR(255),
    teacher VARCHAR(255),
    credit INT
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
    user_id BIGINT,
    course_id VARCHAR(255),
    name VARCHAR(255) NOT NULL,
    url VARCHAR(255) NOT NULL,
    time DATETIME,
    FOREIGN KEY (user_id) REFERENCES users(id)
);