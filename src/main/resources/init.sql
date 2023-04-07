CREATE SCHEMA IF NOT EXISTS `attendance2_db` DEFAULT CHARACTER SET utf8 ;
USE `attendance2_db` ;

-- -----------------------------------------------------
-- Table `attendance2_db`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `attendance2_db`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `account_id` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `role` ENUM('ADMIN', 'TEACHER', 'STUDENT') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `account_id_UNIQUE` (`account_id` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `attendance2_db`.`user_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `attendance2_db`.`user_info` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `surname` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_users_info_users_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_users_info_users`
    FOREIGN KEY (`user_id`)
    REFERENCES `attendance2_db`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `attendance2_db`.`room`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `attendance2_db`.`room` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `number` INT NOT NULL,
  `capacity` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idrooms_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `attendance2_db`.`place`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `attendance2_db`.`place` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `number` INT NOT NULL,
  `status` ENUM('FREE', 'BOOKED') NOT NULL,
  `room_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_place_room1_idx` (`room_id` ASC) VISIBLE,
  CONSTRAINT `fk_place_room1`
    FOREIGN KEY (`room_id`)
    REFERENCES `attendance2_db`.`room` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `attendance2_db`.`attendence`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `attendance2_db`.`attendence` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `place_id` INT NOT NULL,
  `date_time` DATETIME NOT NULL,
  `status` ENUM('CONFIRMED', 'NOT_CONFIRMED') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_attendence_user1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_attendence_place1_idx` (`place_id` ASC) VISIBLE,
  CONSTRAINT `fk_attendence_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `attendance2_db`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_attendence_place1`
    FOREIGN KEY (`place_id`)
    REFERENCES `attendance2_db`.`place` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;