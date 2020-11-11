DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS groups;
CREATE TABLE IF NOT EXISTS groups (group_id SERIAL PRIMARY KEY  NOT NULL, group_name VARCHAR(20) NOT NULL);
CREATE TABLE IF NOT EXISTS students (student_id SERIAL PRIMARY KEY NOT NULL, first_name VARCHAR(25) NOT NULL, last_name VARCHAR (25) NOT NULL, patronymic VARCHAR (25), birth_date DATE,  group_id INT REFERENCES groups(group_id));