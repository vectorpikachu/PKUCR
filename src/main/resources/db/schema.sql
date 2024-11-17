CREATE DATABASE IF NOT EXISTS pku_database;
USE pku-database;

CREATE TABLE IF NOT EXISTS tasks (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255),
  date DATETIME,
  priority INT,
  description TEXT
);

CREATE TABLE IF NOT EXISTS documents (
    id INT PRIMARY KEY,
    course VARCHAR(255),
    file_name VARCHAR(255),
    file_path VARCHAR(500),
    file_type VARCHAR(50),
    permission INT,
    current_version INT,
    updated_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS document_versions (
    id INT,
    course VARCHAR(255),
    file_name VARCHAR(255),
    file_path VARCHAR(500),
    file_type VARCHAR(50),
    permission INT,
    updated_at TIMESTAMP,
    version INT,
    PRIMARY KEY (document_id, version)
);