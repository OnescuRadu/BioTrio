CREATE DATABASE biotrio;

USE biotrio;

CREATE TABLE user (
user_id int NOT NULL auto_increment,
username VARCHAR(255) NOT NULL,
password VARCHAR(255) NOT NULL,
role VARCHAR(255) NOT NULL,
enabled TINYINT NOT NULL DEFAULT 1,
PRIMARY KEY (user_id)
);

CREATE TABLE theater_room (
theater_room_id INT NOT NULL AUTO_INCREMENT,
name VARCHAR(255) NOT NULL,
rows_no INT NOT NULL,
columns_no INT NOT NULL,
3d_capability TINYINT NOT NULL,
PRIMARY KEY (theater_room_id)
);

CREATE TABLE movie_details(
movie_details_id INT NOT NULL AUTO_INCREMENT,
name VARCHAR(255) NOT NULL,
genre VARCHAR(255) NOT NULL,
release_date DATE NOT NULL,
duration_minutes INT NOT NULL,
description TEXT NOT NULL,
language VARCHAR(10) NOT NULL,
poster VARCHAR(255) NOT NULL,
trailer VARCHAR(255) NOT NULL,
PRIMARY KEY (movie_details_id)
);

CREATE TABLE movie(
movie_id INT NOT NULL AUTO_INCREMENT,
movie_details_id INT NOT NULL,
type TINYINT NOT NULL DEFAULT 0,
PRIMARY KEY (movie_id),
FOREIGN KEY (movie_details_id) REFERENCES movie_details(movie_details_id)
);

CREATE TABLE movie_plan(
movie_plan_id INT NOT NULL AUTO_INCREMENT,
movie_id INT NOT NULL,
theater_room_id int NOT NULL,
date_time DATETIME NOT NULL,
price DECIMAL(6,2) NOT NULL,
PRIMARY KEY (movie_plan_id),
FOREIGN KEY (movie_id) REFERENCES movie(movie_id),
FOREIGN KEY (theater_room_id) REFERENCES theater_room(theater_room_id)
);

CREATE TABLE booking(
booking_id INT NOT NULL AUTO_INCREMENT,
movie_plan_id INT NOT NULL,
phone_number VARCHAR(11) NOT NULL,
email varchar(255) NOT NULL,
confirmation_code VARCHAR(10) NOT NULL,
paid TINYINT NOT NULL DEFAULT 1,
PRIMARY KEY (booking_id),
FOREIGN KEY (movie_plan_id) REFERENCES movie_plan(movie_plan_id)
);

CREATE TABLE ticket(
ticket_id INT NOT NULL AUTO_INCREMENT,
seat_number varchar(10) NOT NULL,
booking_id int NOT NULL,
PRIMARY KEY (ticket_id),
FOREIGN KEY (booking_id) REFERENCES booking(booking_id)
); 

insert into user values
(NULL,'admin', '$2a$10$D6SL8gFHaSuVS6fpzqW4q.CbU.XbbXn85BfdM8crcwz23P4SPeUey', 'ROLE_ADMIN', 1),
(NULL,'radu', '$2a$10$ER.Vsj3gbmGa6a1H2oOJoO1/rZw1VEbTSZr.G8S.kgsGcVGEDhHJq', 'ROLE_ADMIN', 1),
(NULL,'user', '$2a$10$DNiWOsiDNuDtnzKzQQ6M4OS1HP3DvfkFbJnl7w4DVf0R5lDmmVER6', 'ROLE_USER', 1);


-- QUERY FOR INSERTING MOVIE DESCRIPTION
insert into movie_details values
(NULL,"Avengers: Endgame", "Adventure", "2019-04-22", "181", "After the devastating events of Avengers: Infinity War, the universe is in ruins due to the efforts of the Mad Titan, Thanos. With the help of remaining allies, the Avengers must assemble once more in order to undo Thanos' actions and restore order to the universe once and for all, no matter what consequences may be in store.","ENG","1.jpg", "1.mp4");


-- QUERY FOR INSERTING MOVIE AND LINKING IT WITH MOVIE DESCRIPTION
insert into movie values
(NULL, 1, 0);

-- QUERY FOR INSERTING THEATER ROOM
insert into theater_room values
(NULL, "ORANGE", 10, 10, 1),
(NULL, "YELLOW", 15, 12, 0);

-- QUERY FOR INSERTING MOVIE PLAN
insert into movie_plan values
(NULL, 1, 1, '2018-04-03 12:00', 100);

-- QUERY FOR INSERTING BOOKING
insert into booking values
(NULL, 1, '12345678', 'email@email.com', '12asd123', TRUE);

-- QUERY FOR INSERTING TICKET
insert into ticket values
(NULL,'01-01', 1),
(NULL,"01-02", 1);
-- ------------------------------------------------------------------------------END OF INSERTING
