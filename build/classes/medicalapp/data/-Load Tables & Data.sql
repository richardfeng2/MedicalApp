
CREATE SCHEMA SA;
CREATE TABLE SA.APPOINTMENT
(
appointmentID int,
date timestamp,
patientID int,
doctorID int,
purpose varchar(255),
duration double,
referringGP varchar(255),
expired boolean,
finished boolean,
PRIMARY KEY(appointmentID)
);

CREATE TABLE SA.DOCTOR
(
doctorID int,
staffID int,
PRIMARY KEY(doctorID)
);

CREATE TABLE SA.DOCUMENT
(
documentID int,
appointmentID int,
title varchar(255),
file blob,
isClinical boolean,
expired boolean,
locked boolean,
PRIMARY KEY (documentID)
);

CREATE TABLE SA.NOTE
(
noteID int,
appointmentID int,
text varchar(2000),
expired boolean,
locked boolean,
PRIMARY KEY(noteID)
);

CREATE TABLE SA.PATIENT
(
patientID int,
personID int,
billingInfo varchar(255),
conditions varchar(1000),
PRIMARY KEY(patientID)
);

CREATE TABLE SA.INVOICE
(
invoiceID int,
appointmentID int,
isPaid boolean,
dateIssued date,
datePaid date,
expired boolean,
PRIMARY KEY(invoiceID)
);

CREATE TABLE SA.INVOICE_SERVICE
(
invoiceID int,
serviceID int
);

CREATE TABLE SA.SERVICE
(
serviceID int,
description varchar(255),
price double,
PRIMARY KEY(serviceID)
);

CREATE TABLE SA.PERSON
(
personID int,
firstName varchar(255),
lastName varchar(255),
address varchar(255),
contactNumber varchar(255),
dateOfBirth date,
isPatient boolean,
isStaff boolean,
expired boolean,
PRIMARY KEY(personID)
);

CREATE TABLE SA.STAFF
(
staffID int,
personID int,
username varchar(255),
password varchar(255),
isAdmin boolean,
isNurse boolean,
isDoctor boolean,
PRIMARY KEY(staffID)
);

CREATE TABLE SA.TESTRESULT
(
testResultID int,
weight double,
bloodPressure varchar(255),
heartRate int,
oxygenLevel double,
lungCapacity double,
oxygenUptake double,
appointmentID int,
expired boolean,
locked boolean,
PRIMARY KEY(testResultID)
);

INSERT INTO PERSON
      VALUES (1,'John','Smith','123 Fake St, Fakeville', '0422 123 123', '1991-05-18',true,false,false);
INSERT INTO PERSON
      VALUES (2,'Taylor','Swift','123 Anzac Parade, Kensington', '0422 421 342', '1991-04-18',true,false,false);
INSERT INTO PERSON
      VALUES (3,'Jason','Derulo','123 High St, Kensington', '0422 523 123', '1990-05-18',true,false,false);
INSERT INTO PERSON
      VALUES (4,'Calvin','Harris','7 Fake St Fakeville', '0412 123 123', '1991-01-18',true,false,false);
INSERT INTO PERSON
      VALUES (5,'Chris','Brown','123 Fake St, Punchbowl', '0422 911 911', '1991-03-18',false,true,false);
INSERT INTO PERSON
      VALUES (6,'Julia','Gillard','23 Fake St, Newtown', '0422 911 911', '1991-03-18',false,true,false);
INSERT INTO PERSON
      VALUES (7,'Jacky','Chan','1 Dixon St, Haymarket', '0422 911 911', '1991-03-18',false,true,false);
INSERT INTO PERSON
      VALUES (8,'Joe','Tam','12 Bank St, Haymarket', '0423 911 911', '1991-03-18',false,true,false);

INSERT INTO PATIENT
      VALUES (1,1,'Medicare 1234567890', 'Asthma; Lung Cancer');
INSERT INTO PATIENT
      VALUES (2,2,'Medicare 1234565890', 'Asthma; Pneumonia; Tuberculosis');
INSERT INTO PATIENT
      VALUES (3,3,'Medicare 1234567890', 'Asthma; Bronchitis');
INSERT INTO PATIENT
      VALUES (4,4,'Medicare 1234567890', 'Asthma');

INSERT INTO STAFF
      VALUES (1,5,'calvin','calvin',true,false,false);
INSERT INTO STAFF
      VALUES (2,6,'julia','julia',false,true,false);
INSERT INTO STAFF
      VALUES (3,7,'jacky','jacky',false,false,true);
INSERT INTO STAFF
      VALUES (4,8,'joe','joe',false,false,true);

INSERT INTO DOCTOR
      VALUES (1,3);
INSERT INTO DOCTOR
      VALUES (2,4);

INSERT INTO APPOINTMENT
    VALUES (1,TIMESTAMP('2014-04-08 10:00:00'),1,1,'General Checkup',15,'Dr Who',false, true);
INSERT INTO APPOINTMENT
    VALUES (2,TIMESTAMP('2014-07-08 12:00:00'),2,2,'Respiratory Tests',15,'Dr Referrer',false, true);
INSERT INTO APPOINTMENT
    VALUES (3,TIMESTAMP('2014-09-30 14:15:00'),3,3,'General Checkup',15,'Dr Who',false, false);

INSERT INTO PAYMENT
    VALUES (1,1,'General Checkup', true, '2014-04-08','2014-04-08',false,true);
INSERT INTO PAYMENT
    VALUES (2,2,'Respiratory Tests', false, '2014-04-08',null,false,false);

INSERT INTO TESTRESULT
    VALUES (1, 60, '120/80',80, 75, 100, 250,1,false,true);
INSERT INTO TESTRESULT
    VALUES (2, 65, '110/90',85, 75, 90, 240,1,false,true);

INSERT INTO NOTE
    VALUES (1,1,'Patient has reported difficulty in breathing after collapsing in a 7km run', false,true);
INSERT INTO NOTE
    VALUES (2,2,'Patient has a history of substance abuse', false,true);


