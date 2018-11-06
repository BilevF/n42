CREATE TABLE `USER` (
	`ID` int NOT NULL AUTO_INCREMENT,
	`FIRST_NAME` varchar(45) NOT NULL,
	`LAST_NAME` varchar(45) NOT NULL,
	`BIRTH_DATE` DATE NOT NULL,
	`PASSPORT` varchar(100) NOT NULL,
	`ADDRESS` varchar(100) NOT NULL,
	`EMAIL` varchar(100) NOT NULL UNIQUE,
	`PASSWORD` varchar(255) NOT NULL,
	`ROLE_ID` int NOT NULL,
	PRIMARY KEY (`ID`)
);

CREATE TABLE `ROLE` (
	`ID` int NOT NULL AUTO_INCREMENT,
	`NAME` varchar(45) NOT NULL UNIQUE,
	PRIMARY KEY (`ID`)
);

CREATE TABLE `CONTRACT` (
	`ID` int NOT NULL AUTO_INCREMENT,
	`PHONE_NUMBER` varchar(45) NOT NULL UNIQUE,
	`BALANCE` double NOT NULL,
	`USER_ID` int NOT NULL,
	`TARIFF_ID` int NOT NULL,
	`BLOCK_ID` int NOT NULL,
	PRIMARY KEY (`ID`)
);

CREATE TABLE `BLOCK` (
	`ID` int NOT NULL AUTO_INCREMENT,
	`TYPE` varchar(45) NOT NULL UNIQUE,
	PRIMARY KEY (`ID`)
);

CREATE TABLE `TARIFF` (
	`ID` int NOT NULL AUTO_INCREMENT,
	`NAME` varchar(45) NOT NULL UNIQUE,
	`PRICE` double NOT NULL,
	`INFO` varchar(255) NOT NULL,
	`VALID` bool NOT NULL,
	PRIMARY KEY (`ID`)
);

CREATE TABLE `TARIFF_OPTION` (
	`ID` int NOT NULL AUTO_INCREMENT,
	`NAME` varchar(45) NOT NULL,
	`PRICE` double NOT NULL,
	`CONNECTION_PRICE` double NOT NULL,
	`INFO` varchar(255) NOT NULL,
	`TARIFF_ID` int NOT NULL,
	PRIMARY KEY (`ID`)
);

CREATE TABLE `CONTRACT_OPTIONS` (
	`CONTRACT_ID` int NOT NULL,
	`OPTION_ID` int NOT NULL
);

CREATE TABLE `REQUIRED_OPTIONS` (
	`FIRST_OPTION_ID` int NOT NULL,
	`SECOND_OPTION_ID` int NOT NULL
);

CREATE TABLE `INCOMPATIBLE_OPTIONS` (
	`FIRST_OPTION_ID` int NOT NULL,
	`SECOND_OPTION_ID` int NOT NULL
);

CREATE TABLE `HISTORY` (
	`ID` int NOT NULL AUTO_INCREMENT,
	`NAME` varchar(45) NOT NULL,
	`DATE` DATETIME NOT NULL,
	`PRICE` double NOT NULL,
	`CONTRACT_ID` int NOT NULL,
	PRIMARY KEY (`ID`)
);

CREATE TABLE `BASKET` (
	`CONTRACT_ID` int NOT NULL,
	`OPTION_ID` int NOT NULL
);

ALTER TABLE `USER` ADD CONSTRAINT `USER_fk0` FOREIGN KEY (`ROLE_ID`) REFERENCES `ROLE`(`ID`);

ALTER TABLE `CONTRACT` ADD CONSTRAINT `CONTRACT_fk0` FOREIGN KEY (`USER_ID`) REFERENCES `USER`(`ID`);

ALTER TABLE `CONTRACT` ADD CONSTRAINT `CONTRACT_fk1` FOREIGN KEY (`TARIFF_ID`) REFERENCES `TARIFF`(`ID`);

ALTER TABLE `CONTRACT` ADD CONSTRAINT `CONTRACT_fk2` FOREIGN KEY (`BLOCK_ID`) REFERENCES `BLOCK`(`ID`);

ALTER TABLE `TARIFF_OPTION` ADD CONSTRAINT `TARIFF_OPTION_fk0` FOREIGN KEY (`TARIFF_ID`) REFERENCES `TARIFF`(`ID`);

ALTER TABLE `CONTRACT_OPTIONS` ADD CONSTRAINT `CONTRACT_OPTIONS_fk0` FOREIGN KEY (`CONTRACT_ID`) REFERENCES `CONTRACT`(`ID`);

ALTER TABLE `CONTRACT_OPTIONS` ADD CONSTRAINT `CONTRACT_OPTIONS_fk1` FOREIGN KEY (`OPTION_ID`) REFERENCES `TARIFF_OPTION`(`ID`);

ALTER TABLE `REQUIRED_OPTIONS` ADD CONSTRAINT `REQUIRED_OPTIONS_fk0` FOREIGN KEY (`FIRST_OPTION_ID`) REFERENCES `TARIFF_OPTION`(`ID`);

ALTER TABLE `REQUIRED_OPTIONS` ADD CONSTRAINT `REQUIRED_OPTIONS_fk1` FOREIGN KEY (`SECOND_OPTION_ID`) REFERENCES `TARIFF_OPTION`(`ID`);

ALTER TABLE `INCOMPATIBLE_OPTIONS` ADD CONSTRAINT `INCOMPATIBLE_OPTIONS_fk0` FOREIGN KEY (`FIRST_OPTION_ID`) REFERENCES `TARIFF_OPTION`(`ID`);

ALTER TABLE `INCOMPATIBLE_OPTIONS` ADD CONSTRAINT `INCOMPATIBLE_OPTIONS_fk1` FOREIGN KEY (`SECOND_OPTION_ID`) REFERENCES `TARIFF_OPTION`(`ID`);

ALTER TABLE `HISTORY` ADD CONSTRAINT `HISTORY_fk0` FOREIGN KEY (`CONTRACT_ID`) REFERENCES `CONTRACT`(`ID`);

ALTER TABLE `BASKET` ADD CONSTRAINT `BASKET_fk0` FOREIGN KEY (`CONTRACT_ID`) REFERENCES `CONTRACT`(`ID`);

ALTER TABLE `BASKET` ADD CONSTRAINT `BASKET_fk1` FOREIGN KEY (`OPTION_ID`) REFERENCES `TARIFF_OPTION`(`ID`);
