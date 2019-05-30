-- -----------------------------------------------------
-- Schema biotrio
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `biotrio` ;
USE `biotrio` ;

-- -----------------------------------------------------
-- Table `biotrio`.`movie_details`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biotrio`.`movie_details` (
  `movie_details_id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `genre` VARCHAR(255) NOT NULL,
  `release_date` DATE NOT NULL,
  `duration_minutes` INT(11) NOT NULL,
  `description` TEXT NOT NULL,
  `language` VARCHAR(255) NOT NULL,
  `poster` VARCHAR(255) NOT NULL,
  `trailer` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`movie_details_id`));


-- -----------------------------------------------------
-- Table `biotrio`.`movie`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biotrio`.`movie` (
  `movie_id` INT(11) NOT NULL AUTO_INCREMENT,
  `movie_details_id` INT(11) NOT NULL,
  `type` TINYINT(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`movie_id`),
  CONSTRAINT `movie_ibfk_1`
    FOREIGN KEY (`movie_details_id`)
    REFERENCES `biotrio`.`movie_details` (`movie_details_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);


-- -----------------------------------------------------
-- Table `biotrio`.`theater_room`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biotrio`.`theater_room` (
  `theater_room_id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `rows_no` INT(11) NOT NULL,
  `columns_no` INT(11) NOT NULL,
  `3d_capability` TINYINT(1) NOT NULL,
  PRIMARY KEY (`theater_room_id`));


-- -----------------------------------------------------
-- Table `biotrio`.`movie_plan`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biotrio`.`movie_plan` (
  `movie_plan_id` INT(11) NOT NULL AUTO_INCREMENT,
  `movie_id` INT(11) NOT NULL,
  `theater_room_id` INT(11) NOT NULL,
  `date_time` DATETIME NOT NULL,
  `price` DECIMAL(6,2) NOT NULL,
  PRIMARY KEY (`movie_plan_id`),
  CONSTRAINT `movie_plan_ibfk_1`
    FOREIGN KEY (`movie_id`)
    REFERENCES `biotrio`.`movie` (`movie_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `movie_plan_ibfk_2`
    FOREIGN KEY (`theater_room_id`)
    REFERENCES `biotrio`.`theater_room` (`theater_room_id`));



-- -----------------------------------------------------
-- Table `biotrio`.`booking`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biotrio`.`booking` (
  `booking_id` INT(11) NOT NULL AUTO_INCREMENT,
  `movie_plan_id` INT(11) NOT NULL,
  `phone_number` VARCHAR(11) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `confirmation_code` VARCHAR(36) NOT NULL,
  `paid` TINYINT(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`booking_id`),
  CONSTRAINT `booking_ibfk_1`
    FOREIGN KEY (`movie_plan_id`)
    REFERENCES `biotrio`.`movie_plan` (`movie_plan_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);


-- -----------------------------------------------------
-- Table `biotrio`.`ticket`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biotrio`.`ticket` (
  `ticket_id` INT(11) NOT NULL AUTO_INCREMENT,
  `seat_number` VARCHAR(10) NOT NULL,
  `booking_id` INT(11) NOT NULL,
  PRIMARY KEY (`ticket_id`),
  CONSTRAINT `ticket_ibfk_1`
    FOREIGN KEY (`booking_id`)
    REFERENCES `biotrio`.`booking` (`booking_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);


-- -----------------------------------------------------
-- Table `biotrio`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biotrio`.`user` (
  `user_id` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `role` VARCHAR(255) NOT NULL,
  `enabled` TINYINT(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`user_id`));

