DROP DATABASE IF EXISTS hotel;

CREATE DATABASE hotel;

USE hotel;

CREATE TABLE Guest
(
  GuestId INT PRIMARY KEY AUTO_INCREMENT,
  Name VARCHAR(50) NOT NULL,
  Address VARCHAR(50) NOT NULL,
  City VARCHAR(50) NOT NULL,
  State CHAR(2) NOT NULL,
  Zip CHAR(5) NOT NULL,
  Phone CHAR(14) NOT NULL
);

CREATE TABLE RoomType
(
  TypeId INT PRIMARY KEY AUTO_INCREMENT,
  Name VARCHAR(25) NOT NULL,
  BasePrice DECIMAL(10,2) NOT NULL,
  ExtraPerson DECIMAL(10,2) NULL,
  Standard_Occupancy INT NOT NULL,
  Maximum_Occupancy INT NOT NULL
);

CREATE TABLE Room
(
  RoomNumber INT NOT NULL PRIMARY KEY,
  TypeId INT NOT NULL,
  AdaAccessable TINYINT(1) NOT NULL,
  FOREIGN KEY (TypeId) REFERENCES RoomType(TypeId)
);

CREATE TABLE Amenity
(
  AmenityId INT PRIMARY KEY AUTO_INCREMENT,
  Type VARCHAR(25) NOT NULL,
  AmenityPrice DECIMAL(10,2) NULL
);

CREATE TABLE Reservation
(
  ReservationId INT PRIMARY KEY AUTO_INCREMENT,
  GuestId INT NOT NULL,
  StartDate DATE NOT NULL,
  EndDate DATE NOT NULL,
  FOREIGN KEY (GuestId) REFERENCES Guest(GuestId)
);

CREATE TABLE RoomAmenity
(
  RoomNumber INT NOT NULL,
  AmenityId INT NOT NULL,
  PRIMARY KEY (RoomNumber, AmenityId),
  FOREIGN KEY (RoomNumber) REFERENCES Room(RoomNumber),
  FOREIGN KEY (AmenityId) REFERENCES Amenity(AmenityId)
);

CREATE TABLE RoomReservation
(
  ReservationId INT NOT NULL,
  RoomNumber INT NOT NULL,
  Adults INT NOT NULL,
  Children INT NOT NULL,
  TotalRoomCost DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (RoomNumber, ReservationId),
  FOREIGN KEY (RoomNumber) REFERENCES Room(RoomNumber),
  FOREIGN KEY (ReservationId) REFERENCES Reservation(ReservationId)
);