drop table vehicle if exists;
drop table reservation if exists;

CREATE TABLE if not exists reservation(
id bigint PRIMARY KEY AUTO_INCREMENT ,
clientName VARCHAR(255) not null ,
payment VARCHAR(255) not null,
start TIMESTAMP ,
end TIMESTAMP ,
vehicle_id integer NOT NULL,
Price integer NOT NULL,
totalPrice integer NOT NULL,
date Timestamp NOT NULL DEFAULT CURRENT_DATE(),
status varchar(20) CHECK (status IN ('open','closed','canceled')),
bookingid integer,
finalizationdate TIMESTAMP );


CREATE TABLE if not exists vehicle (id bigint PRIMARY KEY AUTO_INCREMENT ,
title VARCHAR(255) not null ,
year integer not null,
description VARCHAR(255),
seats integer,
licensePlate VARCHAR(255),
typeOfDrive VARCHAR(255) not null,
power numeric(10,2),
price numeric(10,2) not null,
imgPath VARCHAR(255),
licenseClass varchar(255),
date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
isDeleted boolean  NOT NULL default FALSE,
lastEditDate TIMESTAMP  );


INSERT INTO vehicle (title, year, description, seats, licensePlate, typeOfDrive, power,price,imgPath,licenseClass)
VALUES ('BENZ', 2018, 'f700', 4, 'MFN3Z4U', 'motorized',324,24,'/Users/zi/Desktop/sepm-individual-assignment-java/src/main/resources/imgFolder/1.jpeg','A B' );
INSERT INTO vehicle (title, year, description, seats, licensePlate, typeOfDrive, power,price,imgPath,licenseClass)
VALUES ('GTO', 1966, 'Pontiac', 2, 'WFW3533', 'muscle',0,35,'/Users/zi/Desktop/sepm-individual-assignment-java/src/main/resources/imgFolder/2.jpeg','B' );
INSERT INTO vehicle (title, year, description, seats, licensePlate, typeOfDrive, power,price,imgPath,licenseClass)
VALUES ('BMW', 2011, 'x6', 4, 'WFW2424', 'motorized',252,45,'/Users/zi/Desktop/sepm-individual-assignment-java/src/main/resources/imgFolder/3.jpeg','B' );
INSERT INTO vehicle (title, year, description, seats, licensePlate, typeOfDrive, power,price,imgPath,licenseClass)
VALUES ('Audi',2013 , 's7',4 , 'EQD2456', 'motorized',252,55,'/Users/zi/Desktop/sepm-individual-assignment-java/src/main/resources/imgFolder/4.jpeg','B' );
INSERT INTO vehicle (title, year, description, seats, licensePlate, typeOfDrive, power,price,imgPath,licenseClass)
VALUES ('BMW', 2014, 'x5',4 , 'GEW245O', 'motorized',253,45,'/Users/zi/Desktop/sepm-individual-assignment-java/src/main/resources/imgFolder/5.png','B' );
INSERT INTO vehicle (title, year, description, seats, licensePlate, typeOfDrive, power,price,imgPath,licenseClass)
VALUES ('MINI', 2014, 'cooper',2 , 'MRF3535', 'motorized',245,65,'/Users/zi/Desktop/sepm-individual-assignment-java/src/main/resources/imgFolder/6.jpeg','B' );
INSERT INTO vehicle (title, year, description, seats, licensePlate, typeOfDrive, power,price,imgPath,licenseClass)
VALUES ('Audi',2012 , 'new', 4, 'NFR2435', 'motorized',252,34,'/Users/zi/Desktop/sepm-individual-assignment-java/src/main/resources/imgFolder/7.jpeg','B' );
INSERT INTO vehicle (title, year, description, seats, licensePlate, typeOfDrive, power,price,imgPath,licenseClass)
VALUES ('Benz',2014 , 'old', 4, 'RR34536', 'motorized',235,77,'/Users/zi/Desktop/sepm-individual-assignment-java/src/main/resources/imgFolder/8.jpeg','B' );
INSERT INTO vehicle (title, year, description, seats, licensePlate, typeOfDrive, power,price,imgPath,licenseClass)
VALUES ('MINI',2015 , 'new',4 , 'EGJ3535', 'motorized',252,65,'/Users/zi/Desktop/sepm-individual-assignment-java/src/main/resources/imgFolder/9.jpeg','B' );
INSERT INTO vehicle (title, year, description, seats, licensePlate, typeOfDrive, power,price,imgPath,licenseClass)
VALUES ('Audi',2018 , 'sport',2 , 'MGN4535', 'motorized',435,25,'/Users/zi/Desktop/sepm-individual-assignment-java/src/main/resources/imgFolder/10.jpeg','B');
INSERT INTO vehicle (title, year, description, seats, licensePlate, typeOfDrive, power,price,imgPath,licenseClass)
VALUES ('plymouth',1968 , 'road-runner', 2, 'FRE3533', 'muscle',0,67,'/Users/zi/Desktop/sepm-individual-assignment-java/src/main/resources/imgFolder/11.jpeg','B' );
INSERT INTO vehicle (title, year, description, seats, licensePlate, typeOfDrive, power,price,imgPath,licenseClass)
VALUES ('Audi',2012 , 'a6',4 , 'ERF3433', 'motorized',245,87,'/Users/zi/Desktop/sepm-individual-assignment-java/src/main/resources/imgFolder/12.jpeg','A B' );
INSERT INTO vehicle (title, year, description, seats, licensePlate, typeOfDrive, power,price,imgPath,licenseClass)
VALUES ('Audi', 2013, 's1',4 , 'EFE3535', 'motorized',352,55,'/Users/zi/Desktop/sepm-individual-assignment-java/src/main/resources/imgFolder/13.jpeg','B' );
INSERT INTO vehicle (title, year, description, seats, licensePlate, typeOfDrive, power,price,imgPath,licenseClass)
VALUES ('Audi',2014 , 's3', 4, 'MDS2424', 'motorized',252,54,'/Users/zi/Desktop/sepm-individual-assignment-java/src/main/resources/imgFolder/14.jpeg','B' );
INSERT INTO vehicle (title, year, description, seats, licensePlate, typeOfDrive, power,price,imgPath,licenseClass)
VALUES ('BMW',2016 , 'x1',4 , 'JHG3425', 'motorized',214,45,'/Users/zi/Desktop/sepm-individual-assignment-java/src/main/resources/imgFolder/15.jpeg','B C' );
INSERT INTO vehicle (title, year, description, seats, licensePlate, typeOfDrive, power,price,imgPath,licenseClass)
VALUES ('BMW',2016 , 'x2', 2, 'WEJ2534', 'motorized',353,66,'/Users/zi/Desktop/sepm-individual-assignment-java/src/main/resources/imgFolder/16.jpeg','B' );
INSERT INTO vehicle (title, year, description, seats, licensePlate, typeOfDrive, power,price,imgPath,licenseClass)
VALUES ('BMW', 2017, 'x3',4 , 'MDF2463', 'motorized',235,65,'/Users/zi/Desktop/sepm-individual-assignment-java/src/main/resources/imgFolder/17.jpeg','B' );
INSERT INTO vehicle (title, year, description, seats, licensePlate, typeOfDrive, power,price,imgPath,licenseClass)
VALUES ('BMW',2010 , 'x4', 2, 'MED3464', 'motorized',367,84,'/Users/zi/Desktop/sepm-individual-assignment-java/src/main/resources/imgFolder/18.jpeg','B' );
INSERT INTO vehicle (title, year, description, seats, licensePlate, typeOfDrive, power,price,imgPath,licenseClass)
VALUES ('BMW',2016 , 'x7', 4, 'MSB3464', 'motorized',346,36,'/Users/zi/Desktop/sepm-individual-assignment-java/src/main/resources/imgFolder/19.jpeg','B' );
INSERT INTO vehicle (title, year, description, seats, licensePlate, typeOfDrive, power,price,imgPath,licenseClass)
VALUES ('MINI', 2005, 'ds3',4 , 'MBD6354', 'motorized',366,35,'/Users/zi/Desktop/sepm-individual-assignment-java/src/main/resources/imgFolder/20.jpeg','B' );
INSERT INTO vehicle (title, year, description, seats, licensePlate, typeOfDrive, power,price,imgPath,licenseClass)
VALUES ('MercedesBenz', 2011, 'CLA',4 , 'MNG3547', 'motorized',356,57,'/Users/zi/Desktop/sepm-individual-assignment-java/src/main/resources/imgFolder/21.jpeg','B' );
INSERT INTO vehicle (title, year, description, seats, licensePlate, typeOfDrive, power,price,imgPath,licenseClass)
VALUES ('Morris_Mini', 1959, '621_AOK',2 , 'OKI3574', 'motorized',341,75,'/Users/zi/Desktop/sepm-individual-assignment-java/src/main/resources/imgFolder/22.jpeg','B' );
INSERT INTO vehicle (title, year, description, seats, licensePlate, typeOfDrive, power,price,imgPath,licenseClass)
VALUES ('buick', 1970, 'gsx', 2, 'MNF2435', 'muscle',0,44,'/Users/zi/Desktop/sepm-individual-assignment-java/src/main/resources/imgFolder/23.jpeg','B C' );
INSERT INTO vehicle (title, year, description, seats, licensePlate, typeOfDrive, power,price,imgPath,licenseClass)
VALUES ('plymouth', 1970, 'hemi-cuda', 2, 'MNC4535', 'muscle',0,36,'/Users/zi/Desktop/sepm-individual-assignment-java/src/main/resources/imgFolder/24.jpeg','B' );
INSERT INTO vehicle (title, year, description, seats, licensePlate, typeOfDrive, power,price,imgPath,licenseClass)
VALUES ('chevrolet',1969 , 'camero-zl1', 2, 'VVS5742', 'muscle',0,46,'/Users/zi/Desktop/sepm-individual-assignment-java/src/main/resources/imgFolder/25.jpeg','B' );
INSERT INTO vehicle (title, year, description, seats, licensePlate, typeOfDrive, power,price,imgPath,licenseClass)
VALUES ('ford-mustang', 1969, 'boss',2 , 'BGS6743', 'muscle',0,65,'/Users/zi/Desktop/sepm-individual-assignment-java/src/main/resources/imgFolder/26.jpeg','B' );
INSERT INTO vehicle (title, year, description, seats, licensePlate, typeOfDrive, power,price,imgPath,licenseClass)
VALUES ('chevrolet',1970 , 'chevelle',2 , 'DBG6757', 'muscle',0,57,'/Users/zi/Desktop/sepm-individual-assignment-java/src/main/resources/imgFolder/27.jpeg','B' );
INSERT INTO vehicle (title, year, description, seats, licensePlate, typeOfDrive, power,price,imgPath,licenseClass)
VALUES ('Audi',2017 , 'new', 4, 'MGH5753', 'motorized',325,46,'/Users/zi/Desktop/sepm-individual-assignment-java/src/main/resources/imgFolder/28.jpeg','B' );
INSERT INTO vehicle (title, year, description, seats, licensePlate, typeOfDrive, power,price,imgPath,licenseClass)
VALUES ('BSA',2016 , 'sport16', 1, 'MFH8364', 'motorized',322,64,'/Users/zi/Desktop/sepm-individual-assignment-java/src/main/resources/imgFolder/29.jpeg','A B' );
INSERT INTO vehicle (title, year, description, seats, licensePlate, typeOfDrive, power,price,imgPath,licenseClass)
VALUES ('DRYTONA',2015 , 'sport675', 1, 'SDG7664', 'motorized',325,64,'/Users/zi/Desktop/sepm-individual-assignment-java/src/main/resources/imgFolder/30.jpeg','A' );
INSERT INTO vehicle (title, year, description, seats, licensePlate, typeOfDrive, power,price,imgPath,licenseClass)
VALUES ('yamaha',2014 , 'superfast', 1, 'JEF4635', 'motorized',325,54,'/Users/zi/Desktop/sepm-individual-assignment-java/src/main/resources/imgFolder/31.jpeg','A' );
INSERT INTO vehicle (title, year, description, seats, licensePlate, typeOfDrive, power,price,imgPath,licenseClass)
VALUES ('mslaz',2013 , 'fast', 1, 'WJR3452', 'motorized',325,96,'/Users/zi/Desktop/sepm-individual-assignment-java/src/main/resources/imgFolder/32.png','A' );
INSERT INTO vehicle (title, year, description, seats, licensePlate, typeOfDrive, power,price,imgPath,licenseClass)
VALUES ('Kawasaki',2017 , 'ninja', 1, 'JWD3253', 'motorized',432,85,'/Users/zi/Desktop/sepm-individual-assignment-java/src/main/resources/imgFolder/33.jpeg','A' );
INSERT INTO vehicle (title, year, description, seats, licensePlate, typeOfDrive, power,price,imgPath,licenseClass)
VALUES ('harleydavidson',2017 , 'new', 1, 'WDR4534', 'motorized',346,88,'/Users/zi/Desktop/sepm-individual-assignment-java/src/main/resources/imgFolder/34.png','A' );
INSERT INTO vehicle (title, year, description, seats, licensePlate, typeOfDrive, power,price,imgPath,licenseClass)
VALUES ('Kawasaki)',2018 , 'Ninja_H2R', 1, 'WFR4643', 'motorized',564,88,'/Users/zi/Desktop/sepm-individual-assignment-java/src/main/resources/imgFolder/35.png','A' );
INSERT INTO vehicle (title, year, description, seats, licensePlate, typeOfDrive, power,price,imgPath,licenseClass)
VALUES ('BENZ_LKW',2018 , 'FAST', 3, 'MFR3349', 'motorized',700,300,'/Users/zi/Desktop/sepm-individual-assignment-java/src/main/resources/imgFolder/36.jpeg','C' );
INSERT INTO vehicle (title, year, description, seats, licensePlate, typeOfDrive, power,price,imgPath,licenseClass)
VALUES ('SCANIA',2018 , 'HEAVY', 2, 'MEF3524', 'motorized',750,180,'/Users/zi/Desktop/sepm-individual-assignment-java/src/main/resources/imgFolder/37.jpeg','C' );

INSERT INTO vehicle (title, year, description, seats, licensePlate, typeOfDrive, power,price,imgPath,licenseClass)
VALUES ('bicycle',2018 , 'noLicense', 2, 'bMH8489', 'motorized',750,180,'/Users/zi/Desktop/sepm-individual-assignment-java/src/main/resources/imgFolder/38.jpeg','' );





INSERT INTO Reservation (CLIENTNAME,payment,START,END ,VEHICLE_ID ,PRICE ,TOTALPRICE,STATUS,BOOKINGID)
VALUES ('GEORGE','CH0204835000626882001' , '2018-04-11 11:30:00','2018-04-11 12:30:00' , 4 ,55,100,'open',1 );
INSERT INTO Reservation (CLIENTNAME,payment,START,END ,VEHICLE_ID ,PRICE ,TOTALPRICE,STATUS,BOOKINGID)
VALUES ('GEORGE','CH0204835000626882001' , '2018-04-11 11:30:00','2018-04-11 12:30:00' , 3 ,45,100,'open',1 );

INSERT INTO Reservation (CLIENTNAME,payment,START,END ,VEHICLE_ID ,PRICE ,TOTALPRICE,STATUS,BOOKINGID)
VALUES ('Darlene Tate','DE02600100700013715708' , '2018-04-11 13:30:00','2018-04-11 14:30:00' , 1 ,24,104,'open',2 );
INSERT INTO Reservation (CLIENTNAME,payment,START,END ,VEHICLE_ID ,PRICE ,TOTALPRICE,STATUS,BOOKINGID)
VALUES ('Darlene Tate','DE02600100700013715708' , '2018-04-11 13:30:00','2018-04-11 14:30:00' , 2 ,35,104,'open',2 );
INSERT INTO Reservation (CLIENTNAME,payment,START,END ,VEHICLE_ID ,PRICE ,TOTALPRICE,STATUS,BOOKINGID)
VALUES ('Darlene Tate','DE02600100700013715708' , '2018-04-11 13:30:00','2018-04-11 14:30:00' , 3 ,45,104,'open',2 );

INSERT INTO Reservation (CLIENTNAME,payment,START,END ,VEHICLE_ID ,PRICE ,TOTALPRICE,STATUS,BOOKINGID)
VALUES ('Austin Christensen','DE02700100800030876808' , '2018-05-11 13:30:00','2018-05-11 18:30:00' , 5 ,45,225,'open',3 );

INSERT INTO Reservation (CLIENTNAME,payment,START,END ,VEHICLE_ID ,PRICE ,TOTALPRICE,STATUS,BOOKINGID)
VALUES ('Earnest Underwood','DE02300606010002474689' , '2018-05-17 09:30:00','2018-05-17 11:30:00' , 6 ,65,130,'canceled',4 );

INSERT INTO Reservation (CLIENTNAME,payment,START,END ,VEHICLE_ID ,PRICE ,TOTALPRICE,STATUS,BOOKINGID)
VALUES ('Samuel Fisher','DE02120300000000202051' , '2018-06-01 09:30:00','2018-06-01 11:30:00' , 7 ,34,68,'open',5 );

INSERT INTO Reservation (CLIENTNAME,payment,START,END ,VEHICLE_ID ,PRICE ,TOTALPRICE,STATUS,BOOKINGID)
VALUES ('Dale Scott','DE02120300000000202051' , '2018-06-01 09:30:00','2018-06-01 11:30:00' , 8 ,77,154,'open',6 );

INSERT INTO Reservation (CLIENTNAME,payment,START,END ,VEHICLE_ID ,PRICE ,TOTALPRICE,STATUS,BOOKINGID)
VALUES ('Samuel Fisher','DE02300209000106531065' , '2018-06-01 19:00:00','2018-06-01 21:00:00' , 9 ,65,130,'closed',7 );

INSERT INTO Reservation (CLIENTNAME,payment,START,END ,VEHICLE_ID ,PRICE ,TOTALPRICE,STATUS,BOOKINGID)
VALUES ('Sandy Phelps','AT023225000000704957' , '2018-06-02 10:00:00','2018-06-02 12:00:00' , 21 ,57,114,'open',8 );


INSERT INTO Reservation (CLIENTNAME,payment,START,END ,VEHICLE_ID ,PRICE ,TOTALPRICE,STATUS,BOOKINGID)
VALUES ('Alberta Mckenzie','4964553300297409','2018-06-12 10:00:00','2018-06-12 11:00:00' ,10,65,65,'open',9 );
INSERT INTO Reservation (CLIENTNAME,payment,START,END ,VEHICLE_ID ,PRICE ,TOTALPRICE,STATUS,BOOKINGID)
VALUES ('Ivan Graves','4803381802764071','2018-06-22 10:00:00','2018-06-22 11:00:00' ,11,25,25,'open',10 );
INSERT INTO Reservation (CLIENTNAME,payment,START,END ,VEHICLE_ID ,PRICE ,TOTALPRICE,STATUS,BOOKINGID)
VALUES ('Raymond Flores','4517355914903147','2018-07-03 17:00:00','2018-07-03 19:00:00' ,12,87,174,'open',11 );
INSERT INTO Reservation (CLIENTNAME,payment,START,END ,VEHICLE_ID ,PRICE ,TOTALPRICE,STATUS,BOOKINGID)
VALUES ('Dana Mcdaniel','372327384265598','2018-08-02 15:00:00','2018-08-02 16:00:00' ,13,55,55,'open',12 );
INSERT INTO Reservation (CLIENTNAME,payment,START,END ,VEHICLE_ID ,PRICE ,TOTALPRICE,STATUS,BOOKINGID)
VALUES ('Martha Conner','372583235926065','2018-09-02 10:00:00','2018-09-02 17:00:00' ,14,54,378,'open',13 );

INSERT INTO Reservation (CLIENTNAME,payment,START,END ,VEHICLE_ID ,PRICE ,TOTALPRICE,STATUS,BOOKINGID)
VALUES ('Gertrude Hamilton','LI5708801200185100814','2019-07-02 11:00:00','2019-07-02 12:00:00' ,15,45,111,'open',14 );
INSERT INTO Reservation (CLIENTNAME,payment,START,END ,VEHICLE_ID ,PRICE ,TOTALPRICE,STATUS,BOOKINGID)
VALUES ('Gertrude Hamilton','LI5708801200185100814 ','2019-07-02 11:00:00','2019-07-02 12:00:00' ,16,66,111,'open',14 );

INSERT INTO Reservation (CLIENTNAME,payment,START,END ,VEHICLE_ID ,PRICE ,TOTALPRICE,STATUS,BOOKINGID)
VALUES ('Jenna Powell','5272131867160791','2020-01-02 10:00:00','2020-01-02 11:00:00' ,17,65,220,'closed',15 );
INSERT INTO Reservation (CLIENTNAME,payment,START,END ,VEHICLE_ID ,PRICE ,TOTALPRICE,STATUS,BOOKINGID)
VALUES ('Jenna Powell','5272131867160791','2020-01-02 10:00:00','2020-01-02 11:00:00',18,84,220,'closed',15 );
INSERT INTO Reservation (CLIENTNAME,payment,START,END ,VEHICLE_ID ,PRICE ,TOTALPRICE,STATUS,BOOKINGID)
VALUES ('Jenna Powell','527213186vxy7160791','2020-01-02 10:00:00','2020-01-02 11:00:00' ,19,36,220,'closed',15 );
INSERT INTO Reservation (CLIENTNAME,payment,START,END ,VEHICLE_ID ,PRICE ,TOTALPRICE,STATUS,BOOKINGID)
VALUES ('Jenna Powell','5272131867160791','2020-01-02 10:00:00','2020-01-02 11:00:00' ,20,35,220,'closed',15 );

INSERT INTO Reservation (CLIENTNAME,payment,START,END ,VEHICLE_ID ,PRICE ,TOTALPRICE,STATUS,BOOKINGID)
VALUES ('Jenna mi','5272131867160791','2020-01-02 10:00:00','2020-01-02 11:00:00' ,38,0,0,'open',16 );



