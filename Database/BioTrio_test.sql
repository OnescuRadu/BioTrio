-- -----------------------------------------------------
-- Testing queries
-- BioTrio - Team Six
-- -----------------------------------------------------

-- -----------------------------------------------------
-- USERS
-- -----------------------------------------------------
-- Insert user (in the project password is encoded using Java)
INSERT INTO user values(null, 'admin', 'pass', 'ROLE_MANAGER', 1);

-- Edit user's password
UPDATE user set password = 'pass' where user_id = 1;

-- Edit user
UPDATE user set username = 'username' , role = 'ROLE_EMPLOYEE' , enabled = 1 WHERE user_id = 1;

-- Find all users
SELECT * FROM user;

-- Find user by username
SELECT * FROM user WHERE username = 'admin' ;

-- Find user by id
SELECT * FROM user WHERE user_id = 1 ;

-- Find user that have the same username but not the same id
SELECT * FROM user WHERE username = 'admin' and user_id != '1';

-- Delete user
DELETE from user where user_id = 1;


-- -----------------------------------------------------
-- THEATER ROOMS
-- -----------------------------------------------------
-- Insert theater room
INSERT INTO theater_room values(null, 'Purple Room', 7, 10, true);

-- Edit theater room
UPDATE theater_room set name = 'Green Room' , rows_no = 6 , columns_no = 10, 3d_capability = false WHERE theater_room_id = 1 ;

-- Find all theater rooms
SELECT * from theater_room;

-- Find theater room by id
SELECT * FROM theater_room WHERE theater_room_id = 1 ;

-- Delete theater room
DELETE from theater_room where theater_room_id = 1 ;


-- -----------------------------------------------------
-- MOVIE DETAILS
-- -----------------------------------------------------
-- Find all movie details
SELECT * from movie_details;

-- Insert movie details
INSERT INTO movie_details (movie_details_id, description, genre, language, name, poster, trailer, duration_minutes, release_date) VALUES 
(NULL,'A mean lord exiles fairytale creatures to the swamp of a grumpy ogre, who must go on a quest and rescue a princess for the lord in order to get his land back.', 'Animation', 'ENG', 'SHREK', 'https://m.media-amazon.com/images/M/MV5BOGZhM2FhNTItODAzNi00YjA0LWEyN2UtNjJlYWQzYzU1MDg5L2ltYWdlL2ltYWdlXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_SY1000_CR0,0,671,1000_AL_.jpg','https://www.youtube.com/embed/W37DlG1i61s', 90, '2001-05-18');

-- Update movie details
UPDATE movie_details SET name = 'Shrek Forever After' , genre = 'Animation', release_date = '2010-05-21', duration_minutes= 93, 
description = 'Rumpelstiltskin tricks a mid-life crisis burdened Shrek into allowing himself to be erased from existence and cast in a dark alternate timeline where Rumpel rules supreme.', 
language = 'ENG', poster = 'https://m.media-amazon.com/images/M/MV5BMTY0OTU1NzkxMl5BMl5BanBnXkFtZTcwMzI2NDUzMw@@._V1_.jpg', trailer = 'https://www.youtube.com/embed/tJtlzGGZWqY' WHERE movie_details_id = 1;


-- -----------------------------------------------------
-- MOVIES
-- Note: Available movies are the ones that are planned for showing on or after the current date.
-- -----------------------------------------------------
-- Find all movies
SELECT movie_id, movie.movie_details_id, type, name, genre, release_date, duration_minutes, description, language, poster, trailer 
FROM movie
INNER JOIN movie_details
ON (movie.movie_details_id = movie_details.movie_details_id)
ORDER BY release_date DESC;

-- Find all available movies
SELECT distinct movie.movie_id, movie.movie_details_id, type, name, genre, release_date, duration_minutes, description, language, poster, trailer
FROM movie
INNER JOIN movie_details
ON (movie.movie_details_id = movie_details.movie_details_id)
INNER JOIN movie_plan
on movie_plan.movie_id = movie.movie_id
WHERE movie_plan.date_time >= curdate()
ORDER BY release_date DESC;

-- Find movie
SELECT movie_id, movie.movie_details_id, type, name, genre, release_date, duration_minutes, description, language, poster, trailer
FROM movie 
INNER JOIN movie_details
ON (movie.movie_details_id = movie_details.movie_details_id)
WHERE movie_id = 1 ;

-- Insert movie
INSERT INTO movie VALUES (NULL, 2, 1);

-- Find available movie by gender
SELECT DISTINCT movie.movie_id, movie.movie_details_id, type, name, genre, release_date, duration_minutes, description, language, poster, trailer
FROM movie
INNER JOIN movie_details
ON (movie.movie_details_id = movie_details.movie_details_id)
INNER JOIN movie_plan
on movie_plan.movie_id = movie.movie_id
WHERE (movie_details.genre) like '%Adventure%'
&& movie_plan.date_time >= curdate()
ORDER BY release_date DESC;

-- Find available movie by name
SELECT DISTINCT movie.movie_id, movie.movie_details_id, type, name, genre, release_date, duration_minutes, description, language, poster, trailer
FROM movie
INNER JOIN movie_details
ON (movie.movie_details_id = movie_details.movie_details_id)
INNER JOIN movie_plan
on movie_plan.movie_id = movie.movie_id
WHERE (movie_details.name) LIKE '%Avengers%'
&& movie_plan.date_time >= curdate()
ORDER BY release_date DESC;

-- Delete movie
DELETE from movie where movie_id = 1;

-- Update movie
UPDATE movie SET type = 1  WHERE movie_id = 1;


-- -----------------------------------------------------
-- TICKETS
-- -----------------------------------------------------

-- Find all tickets
SELECT * FROM ticket;

-- Find tickets by booking
SELECT * FROM ticket WHERE booking_id = 1;

-- Find tickets by movie plan
SELECT ticket.ticket_id, seat_number, ticket.booking_id FROM ticket
inner join booking
on booking.booking_id = ticket.booking_id
where movie_plan_id = 1 ;

-- Find seats by movie plan
select seat_number from ticket
inner join booking
on ticket.booking_id = booking.booking_id
inner join movie_plan
on movie_plan.movie_plan_id = booking.movie_plan_id
where movie_plan.movie_plan_id = 1 ;

-- Insert ticket
INSERT INTO ticket values(null, '01-02', 1);


-- -----------------------------------------------------
-- Movie Plans
-- -----------------------------------------------------

-- Find all movie plans
SELECT movie_plan_id, date_time,  price, movie_plan.movie_id, movie.movie_details_id, type, movie_details.name,movie_plan.theater_room_id, theater_room.name as theater_room_name,rows_no,columns_no,3d_capability
FROM movie_plan
INNER JOIN theater_room
ON (movie_plan.theater_room_id = theater_room.theater_room_id)
INNER JOIN movie
ON (movie_plan.movie_id = movie.movie_id)
INNER JOIN movie_details
ON (movie.movie_details_id = movie_details.movie_details_id)
ORDER BY date_time desc;
                
-- Find movie plan by id
SELECT movie_plan_id, date_time,  price, movie_plan.movie_id, movie.movie_details_id, type, movie_details.name, movie_plan.theater_room_id, theater_room.name as theater_room_name,rows_no,columns_no,3d_capability
FROM movie_plan
INNER JOIN theater_room
ON (movie_plan.theater_room_id = theater_room.theater_room_id)
INNER JOIN movie
ON (movie_plan.movie_id = movie.movie_id)
INNER JOIN movie_details
ON (movie.movie_details_id = movie_details.movie_details_id) 
WHERE movie_plan.movie_plan_id = 1 ;

-- Find movie plans by movie id
SELECT movie_plan_id, date_time,  price, movie_plan.movie_id, movie.movie_details_id, type, movie_details.name,movie_plan.theater_room_id, theater_room.name as theater_room_name,rows_no,columns_no,3d_capability
FROM movie_plan
INNER JOIN theater_room
ON (movie_plan.theater_room_id = theater_room.theater_room_id)
INNER JOIN movie
ON (movie_plan.movie_id = movie.movie_id)
INNER JOIN movie_details
ON (movie.movie_details_id = movie_details.movie_details_id)
WHERE movie_plan.movie_id = 1 ;

-- Insert movie plan
INSERT INTO movie_plan values(null, 1, 1, '2019-05-05 15:00', 150);

-- Edit movie plan
UPDATE movie_plan set movie_id = 1 , theater_room_id = 1 , price = 100 , date_time = '2019-06-03 12:00' WHERE movie_plan_id = 1 ;

-- Delete movie plan
DELETE from movie_plan where movie_plan_id = 1;

-- Select movie plans to see if they overlap with a given movie plan
select theater_room_id, duration_minutes, date_time from movie_plan
inner join movie
on movie.movie_id = movie_plan.movie_id
inner join movie_details
on movie_details.movie_details_id = movie.movie_details_id
where theater_room_id = 1 
&& (date_time = '2019-06-29 12:00' || date_time <= '2019-06-29 12:00' && '2019-06-29 12:00' <= DATE_ADD(date_time, INTERVAL duration_minutes minute) || date_time <= DATE_ADD('2019-06-29 12:00', INTERVAL 90 minute) 
&& DATE_ADD('2019-06-29 12:00', INTERVAL 90 minute) <= DATE_ADD(date_time, INTERVAL duration_minutes minute));


-- -----------------------------------------------------
-- Bookings
-- -----------------------------------------------------
-- Find all bookings
SELECT booking.booking_id, booking.movie_plan_id, phone_number, email, confirmation_code, paid, movie_plan.movie_plan_id, date_time, price as ticket_price, duration_minutes, movie_plan.movie_id,
movie.movie_details_id, type as movie_type, movie_details.name as movie_name, language, movie_plan.theater_room_id,
theater_room.name as theater_room_name from booking
INNER JOIN movie_plan
on booking.movie_plan_id = movie_plan.movie_plan_id
INNER JOIN theater_room
ON (movie_plan.theater_room_id = theater_room.theater_room_id)
INNER JOIN movie
ON (movie_plan.movie_id = movie.movie_id)
INNER JOIN movie_details
ON (movie.movie_details_id = movie_details.movie_details_id);

-- Find booking by id
SELECT booking.booking_id, booking.movie_plan_id, phone_number, email, confirmation_code, paid, movie_plan.movie_plan_id, date_time, price as ticket_price, duration_minutes, movie_plan.movie_id,
movie.movie_details_id, type as movie_type, movie_details.name as movie_name, language, movie_plan.theater_room_id,
theater_room.name as theater_room_name from booking
INNER JOIN movie_plan
on booking.movie_plan_id = movie_plan.movie_plan_id
INNER JOIN theater_room
ON (movie_plan.theater_room_id = theater_room.theater_room_id)
INNER JOIN movie
ON (movie_plan.movie_id = movie.movie_id)
INNER JOIN movie_details
ON (movie.movie_details_id = movie_details.movie_details_id)
WHERE booking_id = 1;

-- Find booking by confirmation code
SELECT booking.booking_id, phone_number, email, confirmation_code, paid, movie_plan.movie_plan_id, date_time, price as ticket_price, duration_minutes, movie_plan.movie_id,
movie.movie_details_id, type as movie_type, movie_details.name as movie_name, language, movie_plan.theater_room_id, theater_room.name as theater_room_name from booking
INNER JOIN movie_plan
on booking.movie_plan_id = movie_plan.movie_plan_id
INNER JOIN theater_room
ON (movie_plan.theater_room_id = theater_room.theater_room_id)
INNER JOIN movie
ON (movie_plan.movie_id = movie.movie_id)
INNER JOIN movie_details
ON (movie.movie_details_id = movie_details.movie_details_id)
WHERE confirmation_code = '5e24999a-50ed-4101-b641-dfc745bf888a';

-- Find booking by email/phone number and confirmation code
SELECT booking.booking_id, phone_number, email, confirmation_code, paid, movie_plan.movie_plan_id, date_time, price as ticket_price, duration_minutes, movie_plan.movie_id,
movie.movie_details_id, type as movie_type, movie_details.name as movie_name, language, movie_plan.theater_room_id,
theater_room.name as theater_room_name from booking
INNER JOIN movie_plan
on booking.movie_plan_id = movie_plan.movie_plan_id
INNER JOIN theater_room
ON (movie_plan.theater_room_id = theater_room.theater_room_id)
INNER JOIN movie
ON (movie_plan.movie_id = movie.movie_id)
INNER JOIN movie_details
ON (movie.movie_details_id = movie_details.movie_details_id)
WHERE ((email = '12345678' or phone_number = '12345678') and confirmation_code = '25fe0a3d-7019-445c-9faa-09709e41c3ec');

-- Insert booking
INSERT INTO booking VALUES(null, 1, '12345678', 'onescuradu@yahoo.com', '17a477c4-1dad-4a8e-ade0-f44ffd784277', 1);

-- Delete booking by id
DELETE from booking where booking_id = 1;

-- Delete booking by confirmation code and phone number
DELETE from booking where confirmation_code = '25fe0a3d-7019-445c-9faa-09709e41c3ec' and phone_number = '12345678';

-- Edit booking
UPDATE booking set phone_number = '87654321' , email = 'mail@mail.com' , paid = 1 WHERE booking_id = 1 ;
