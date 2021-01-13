DROP DATABASE IF EXISTS superheroSightings;
CREATE DATABASE superheroSightings;

USE superheroSightings;

CREATE TABLE superpower(
	id INT PRIMARY KEY AUTO_INCREMENT,
    superpower VARCHAR(30) NOT NULL
);

CREATE TABLE `character`(
    id INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    `description` VARCHAR(255) NOT NULL,
    superpowerId INT NOT NULL,
    isVillain TINYINT(1) NOT NULL,
    FOREIGN KEY (superpowerId) REFERENCES superpower(id)
);

CREATE TABLE location(
    id INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    `description` VARCHAR(255) NOT NULL,
    address VARCHAR(50) NOT NULL,
    latitude DECIMAL(9,6) NOT NULL,
    longitude DECIMAL(9,6) NOT NULL
);

CREATE TABLE `organization`(
	id INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    `description` VARCHAR(255) NOT NULL,
    address VARCHAR(50) NOT NULL,
    contact VARCHAR(50) NOT NULL,
    isVillain TINYINT(1) NOT NULL
);

CREATE TABLE character_organization(
    characterId INT NOT NULL,
    organizationId INT NOT NULL,
    PRIMARY KEY(characterId, organizationId),
    FOREIGN KEY (characterId) REFERENCES `character`(id),
    FOREIGN KEY (organizationId) REFERENCES `organization`(id)
);

CREATE TABLE sighting(
	id INT PRIMARY KEY AUTO_INCREMENT,
    characterId INT NOT NULL,
    locationId INT NOT NULL,
    `date` DATE NOT NULL,
    image VARCHAR(255) NULL,
    FOREIGN KEY (characterId) REFERENCES `character`(id),
    FOREIGN KEY (locationId) REFERENCES location(id)
);