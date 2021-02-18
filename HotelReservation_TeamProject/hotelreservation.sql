DROP DATABASE IF EXISTS HotelReservation;
CREATE DATABASE HotelReservation;
USE HotelReservation; 

DROP TABLE IF EXISTS Rooms;
create table Rooms(roomID int NOT NULL auto_increment, 
floor int, 
room_type text, 
room_status text, 
cost int, 
PRIMARY KEY(roomID));

insert into Rooms (floor,room_type,room_status,cost) values(1, "King", "Available", 210);
insert into Rooms (floor,room_type,room_status,cost) values(1, "King", "Available", 210);
insert into Rooms (floor,room_type,room_status,cost) values(2, "Queen", "Available", 150);
insert into Rooms (floor,room_type,room_status,cost) values(4, "King", "Available", 210);
insert into Rooms (floor,room_type,room_status,cost) values(4, "King", "Available", 168);
insert into Rooms (floor,room_type,room_status,cost) values(4, "King", "Available", 201);
insert into Rooms (floor,room_type,room_status,cost) values(4, "King", "Available", 252);
insert into Rooms (floor,room_type,room_status,cost) values(4, "King", "Available", 199);
insert into Rooms (floor,room_type,room_status,cost) values(2, "Queen", "Available", 150);
insert into Rooms (floor,room_type,room_status,cost) values(3, "Queen", "Available", 150);
insert into Rooms (floor,room_type,room_status,cost) values(4, "Queen", "Available", 150);
insert into Rooms (floor,room_type,room_status,cost) values(3, "Queen", "Available", 140);
insert into Rooms (floor,room_type,room_status,cost) values(4, "Queen", "Available", 102);
insert into Rooms (floor,room_type,room_status,cost) values(2, "Queen", "Available", 156);
insert into Rooms (floor,room_type,room_status,cost) values(1, "Queen", "Available", 130);
insert into Rooms (floor,room_type,room_status,cost) values(5, "Suite", "Available", 510);
insert into Rooms (floor,room_type,room_status,cost) values(7, "President", "Available", 1001);


DROP TABLE IF EXISTS RoomInfo;
CREATE TABLE RoomInfo(roomID int NOT NULL,
room_type VARCHAR(15),
wifi tinyint(1),
view tinyint(1),
breakfast tinyint(1),
UNIQUE (roomID, room_type));

insert into RoomInfo values (1, "King", 1, 1, 1);
insert into RoomInfo values (2, "King", 0, 1, 1);
insert into RoomInfo values (3, "Queen", 1, 0, 1);
insert into RoomInfo values (4, "King", 1, 0, 0);
insert into RoomInfo values (5, "King", 1, 0, 1);
insert into RoomInfo values (6, "King", 1, 1, 1);
insert into RoomInfo values (7, "King", 1, 0, 1);
insert into RoomInfo values (8, "King", 1, 1, 0);
insert into RoomInfo values (9, "Queen", 0, 0, 1);
insert into RoomInfo values (10, "Queen", 0, 0, 0);
insert into RoomInfo values (11, "Queen", 1, 1, 0);
insert into RoomInfo values (12, "Queen", 1, 0, 0);
insert into RoomInfo values (13, "Queen", 0, 1, 0);
insert into RoomInfo values (14, "Queen", 1, 1, 1);
insert into RoomInfo values (15, "Queen", 1, 1, 0);
insert into RoomInfo values (16, "Suite", 1, 1, 1);
insert into RoomInfo values (17, "President", 1, 1, 1);

DROP TABLE IF EXISTS Guest;
CREATE TABLE Guest(guestID int NOT NULL auto_increment,
name VARCHAR(30) NOT NULL,
party_size int,
reservationID int,
roomID int,
PRIMARY KEY(guestID, name));

DROP TABLE IF EXISTS Reservations;
CREATE TABLE Reservations(guestID int NOT NULL,
roomID int,
reservationID int,
checkin_date date NOT NULL,
checkout_date date NOT NULL,
PRIMARY KEY(reservationID));

DROP TRIGGER IF EXISTS newRoomTrigger;
CREATE TRIGGER newRoomTrigger
AFTER INSERT ON Reservations
FOR EACH ROW
	update Rooms
    set room_status = "Reserved"
    where roomID = new.roomID;
    
insert into Guest (name, party_size, reservationID, roomID) values ('KitKat', 4, 20516, 1);
insert into Guest (name, party_size, reservationID, roomID) values ('Snickers', 2, 48162, 4);
insert into Guest (name, party_size, reservationID, roomID) values ('Milky Way', 3, 08748, 3);
insert into Guest (name, party_size, reservationID, roomID) values ('Twix', 2, 49801, 5);
insert into Guest (name, party_size, reservationID, roomID) values ('Whoppers', 4, 50849, 7);

insert into Reservations values (1, 1, 20516, "2020-01-16", "2020-01-21");
insert into Reservations values (2, 4, 48162, "2020-03-01", "2020-03-03");
insert into Reservations values (3, 3, 08748, "2020-06-29", "2020-07-02");
insert into Reservations values (4, 5, 49801, "2020-02-07", "2020-02-13");
insert into Reservations values (5, 7, 50849, "2020-05-20", "2020-06-19");

alter table Guest
add FOREIGN KEY(reservationID) REFERENCES Reservations(reservationID);
alter table Reservations
add FOREIGN KEY(guestID) REFERENCES Guest(guestID),
add FOREIGN KEY(roomID) REFERENCES Rooms(roomID);

DROP TABLE IF EXISTS Payment;
CREATE TABLE Payment (paymentID int NOT NULL,
guestID int NOT NULL,
reservationID int NOT NULL,
roomID int,
amountDue int NOT NULL,
updatedAt DATETIME default current_timestamp,
UNIQUE (paymentID, guestID));

insert into Payment values (29750180, 1, 20516, 1, 210, now());
insert into Payment values (05812592, 2, 48162, 4, 210, now());
insert into Payment values (45578918, 3, 08748, 3, 150, now());
insert into Payment values (40198401, 4, 49801, 5, 150, now());
insert into Payment values (57280313, 5, 50849, 7, 150, now());

DROP TABLE IF EXISTS PaymentArchive;
CREATE TABLE PaymentArchive(paymentID int NOT NULL,
guestID int NOT NULL,
reservationID int NOT NULL,
roomID int,
amountDue int NOT NULL,
archiveDate DATETIME default current_timestamp);

DROP TRIGGER IF EXISTS newGuest;
CREATE TRIGGER newGuest
BEFORE INSERT ON Guest
FOR EACH ROW
	insert into reservations
    values ((select max(guestID) from Guest limit 1), new.roomID, new.reservationID, "1990-01-01", "1990-01-02");

DROP TRIGGER IF EXISTS archiveTriggerGuest;
CREATE TRIGGER archiveTriggerGuest
BEFORE UPDATE ON Guest
FOR EACH ROW
	update Payment
    set updatedAt = now()
    where Payment.guestID = new.guestID;
    
DROP TRIGGER IF EXISTS archiveTriggerReservation;
CREATE TRIGGER archiveTriggerReservation
BEFORE UPDATE ON Reservations
FOR EACH ROW
	update Payment
    set updatedAt = now()
    where Payment.reservationID = new.reservationID;

DROP PROCEDURE IF EXISTS archivePayment;
DELIMITER //
CREATE PROCEDURE archivePayment(
IN cutOffDate date)
BEGIN
	insert into PaymentArchive (paymentID, guestID, reservationID, roomID, amountDue, archiveDate)
    select paymentID, guestID, reservationID, roomID, amountDue, now()
    from Payment
    where cutOffDate > date(updatedAt);
    
    delete from Payment
    where cutOffDate > date(updatedAt);
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS createReservation;
DELIMITER //
CREATE PROCEDURE createReservation(
IN name VARCHAR(30),
IN party_size int,
IN new_roomID int,
IN reservationID int,
IN paymentID int,
IN startDate date,
IN endDate date)
BEGIN
	insert into Guest (name, party_size, reservationID, roomID) values (name, party_size, reservationID, new_roomID);
    
    update Reservations as R1
    left join Reservations as R2 on R1.reservationID = R2.reservationID
    set R1.guestID = (select max(guestID) from Guest limit 1), R1.checkin_date = startDate, R1.checkout_date = endDate
    where R1.reservationID = reservationID;
    
    insert into Payment values (paymentID, (select max(guestID) from Guest limit 1), reservationID, new_roomID, (select cost from Rooms where roomID = new_roomID), now());
END //
DELIMITER ;


DROP PROCEDURE IF EXISTS getReservationList;
DELIMITER //
CREATE PROCEDURE getReservationList()
BEGIN
	select * from Reservations;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS getGuestInfo;
DELIMITER //
CREATE PROCEDURE getGuestInfo(
IN get_guestID int)
BEGIN
	select * from Guest
    where Guest.guestID = get_guestID;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS getAvailableRooms;
DELIMITER //
CREATE PROCEDURE getAvailableRooms()
BEGIN
	select * from Rooms
    where room_status = "Available";
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS getRoomCosts;
DELIMITER //
CREATE PROCEDURE getRoomCosts()
BEGIN 
	SELECT distinct room_type, cost
    FROM Rooms;
END //
DELIMITER ; 


DROP PROCEDURE IF EXISTS extendReservation;
DELIMITER //
CREATE PROCEDURE extendReservation(IN newCheckOut date, IN rID int)
BEGIN 
	UPDATE Reservations
    SET checkout_date = newCheckOut
    WHERE reservationID = rID;
    
    select * from Reservations
    WHERE reservationID = rID;
END //
DELIMITER ; 

DROP PROCEDURE IF EXISTS getRoomInfo;
DELIMITER //
CREATE PROCEDURE getRoomInfo(IN get_roomID int)
BEGIN 
	select * from RoomInfo
    WHERE roomID = get_roomID;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS memberDiscount;
DELIMITER //
CREATE PROCEDURE memberDiscount(
IN new_guestID int)
BEGIN
update Payment
    set amountDue = amountDue * (0.85)
    where guestID = new_guestID;
    
    select guestID, roomID, amountDue
    from Payment
    where guestID = new_guestID;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS updateGuest;
DELIMITER //
CREATE PROCEDURE updateGuest(
IN guestID int,
IN new_name text,
IN new_party_size int
)
BEGIN
update Guest
    set name = new_name, party_size = new_party_size
    where guestID = guestID;
    
    select * from Guest
    where Guest.guestID = guestID;
END //
DELIMITER ;


DROP PROCEDURE IF EXISTS addAmenaties;
DELIMITER //
CREATE PROCEDURE addAmenaties(
IN new_guestID int)
BEGIN
	update RoomInfo
    set wifi = 1, breakfast = 1
    where roomID = (select roomID from Guest where guestID = new_guestID);
    
    update Payment
    set amountDue = amountDue + (select count(wifi) from RoomInfo where wifi = 1) * 30
    where guestID = new_guestID;
    
    select amountDue from Payment
    where guestID = new_guestID;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS getPartySize;
DELIMITER //
CREATE PROCEDURE getPartySize(IN partySize int)
BEGIN
	select name,party_size,reservationID from Guest
    group by name,party_size,reservationID
    having party_size = partySize;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS mostExpensive;
DELIMITER //
CREATE PROCEDURE mostExpensive()
BEGIN
	select roomID, room_type, max(cost) as cost
    from Rooms
    where Rooms.room_status = "Available"
    group by room_type;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS leastExpensive;
DELIMITER //
CREATE PROCEDURE leastExpensive()
BEGIN
	select roomID, room_type, min(cost) as cost
   from Rooms
   where Rooms.room_status = "Available"
   group by room_type;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS wifiAndBreakfast;
DELIMITER //
CREATE PROCEDURE wifiAndBreakfast()
BEGIN
	select roomID, room_type, wifi, breakfast
   from RoomInfo
   where RoomInfo.wifi = 1 AND RoomInfo.breakfast = 1
   group by room_type;
END //
DELIMITER ;
