DROP DATABASE IF EXISTS MovieCatalogue;

CREATE DATABASE MovieCatalogue;

USE MovieCatalogue;

CREATE TABLE Genre (
		GenreID INT PRIMARY KEY AUTO_INCREMENT,
        GenreName VARCHAR(30) NOT NULL
);

CREATE TABLE Directo (
		DirectorID INT PRIMARY KEY AUTO_INCREMENT,
        FirstName VARCHAR(30) NOT NULL,
        LastName VARCHAR(30) NOT NULL,
        BirthDate DATE NULL
);

CREATE TABLE Rating (
		RatingID INT PRIMARY KEY AUTO_INCREMENT,
        RatingName CHAR(5) NOT NULL
);

CREATE TABLE Movie (
		MovieID INT PRIMARY KEY AUTO_INCREMENT,
        GenreID INT NOT NULL,
        DirectorID INT NULL,
        RatingID INT NULL,
        Title VARCHAR(128) NOT NULL,
        ReleaseDate DATE NULL,
        FOREIGN KEY fk_Movie_Genre (GenreID)
				REFERENCES Genre(GenreID),
		FOREIGN KEY fk_Movie_Directo (DirectorID)
				REFERENCES Directo(DirectorID),
		FOREIGN KEY fk_Movie_Rating (RatingID)
				REFERENCES Rating(RatingID)
);

CREATE TABLE Actor (
		ActorID INT PRIMARY KEY AUTO_INCREMENT,
        FirstName VARCHAR(30) NOT NULL,
        LastName VARCHAR(30) NOT NULL,
        BirthDate DATE NULL
);

CREATE TABLE CastMembers (
		CastMemberID INT PRIMARY KEY AUTO_INCREMENT,
        ActorID INT NOT NULL,
        MovieID INT NOT NULL,
        Role VARCHAR(50) NOT NULL,
        FOREIGN KEY fk_CastMembers_Actor (ActorID)
				REFERENCES Actor(ActorID),
		FOREIGN KEY fk_CastMembers_Movie (MovieID)
				REFERENCES Movie(MovieID)
);