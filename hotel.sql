/**
Some elements repurposed from the Chinook sample database
https://github.com/lerocha/chinook-database

All other content by M. Andrew Juby (jubydoo@gmail.com)
(c)2018
*/

/*******************************************************************************
   Drop database if it exists
********************************************************************************/
DROP USER hotel CASCADE;


/*******************************************************************************
   Create database
********************************************************************************/
CREATE USER hotel
IDENTIFIED BY p4ssw0rd
DEFAULT TABLESPACE users
TEMPORARY TABLESPACE temp
QUOTA 10M ON users;

GRANT connect to hotel;
GRANT resource to hotel;
GRANT create session TO hotel;
GRANT create table TO hotel;
GRANT create view TO hotel;

conn hotel/p4ssw0rd;

/*******************************************************************************
   Create tables
********************************************************************************/
CREATE TABLE guests
(
    g_id NUMBER NOT NULL,
    g_uname VARCHAR2(30) NOT NULL,
    g_fname VARCHAR2(50) NOT NULL,
    g_lname VARCHAR2(50) NOT NULL,
    g_email VARCHAR2(100) NOT NULL,
    g_pwd VARCHAR2(32) NOT NULL,
    g_salt VARCHAR(16) NOT NULL,
    g_istmp NUMBER(1) NOT NULL,
    CONSTRAINT pk_guests PRIMARY KEY (g_id)
);

CREATE TABLE hosts
(
    h_id NUMBER NOT NULL,
    h_uname VARCHAR2(30) NOT NULL,
    h_fname VARCHAR2(50) NOT NULL,
    h_lname VARCHAR2(50) NOT NULL,
    h_email VARCHAR2(100) NOT NULL,
    h_pwd VARCHAR(32) NOT NULL,
    h_salt VARCHAR(16) NOT NULL,
    CONSTRAINT pk_hosts PRIMARY KEY (h_id)
);

CREATE TABLE issues(
    i_id NUMBER NOT NULL,
    i_rezid NUMBER,
    i_guestid NUMBER NOT NULL,
    i_title VARCHAR2(50) NOT NULL,
    i_contents VARCHAR(500) NOT NULL,
    i_hostid NUMBER,
    i_resolved NUMBER(1) NOT NULL,
    i_response VARCHAR2(500),
    CONSTRAINT pk_issues PRIMARY KEY (i_id)
);

CREATE TABLE reservations(
    rez_id NUMBER NOT NULL,
    rez_guestid NUMBER NOT NULL,
    rez_roomid NUMBER NOT NULL,
    rez_hostid NUMBER,
    rez_status NUMBER NOT NULL,
    rez_checkin DATE NOT NULL,
    rez_checkout DATE NOT NULL,
    CONSTRAINT pk_reservations PRIMARY KEY (rez_id)
);

CREATE TABLE rooms(
    rm_id NUMBER NOT NULL,
    rm_pic VARCHAR(100),
    CONSTRAINT pk_rooms PRIMARY KEY (rm_id)
);

CREATE TABLE guest_sessions(
	gs_id NUMBER NOT NULL,
	gs_userid NUMBER NOT NULL,
	gs_expires TIMESTAMP NOT NULL,
    CONSTRAINT pk_guest_sessions PRIMARY KEY (gs_id)
);

CREATE TABLE host_sessions(
	hs_id NUMBER NOT NULL,
	hs_userid NUMBER NOT NULL,
	hs_expires TIMESTAMP NOT NULL,
    CONSTRAINT pk_host_sessions PRIMARY KEY (hs_id)
);

/*******************************************************************************
   Create Foreign Keys
********************************************************************************/
ALTER TABLE issues ADD CONSTRAINT fk_issues_guestid
    FOREIGN KEY (i_guestid) REFERENCES guests (g_id)
    ON DELETE CASCADE;

ALTER TABLE issues ADD CONSTRAINT fk_issues_hostid
    FOREIGN KEY (i_hostid) REFERENCES hosts (h_id);
    
ALTER TABLE issues ADD CONSTRAINT fk_issues_rezid
    FOREIGN KEY (i_rezid) REFERENCES reservations (rez_id);
    
ALTER TABLE reservations ADD CONSTRAINT fk_rez_guestid
    FOREIGN KEY (rez_guestid) REFERENCES guests (g_id)
    ON DELETE CASCADE;
    
ALTER TABLE reservations ADD CONSTRAINT fk_rez_roomid
    FOREIGN KEY (rez_roomid) REFERENCES rooms (rm_id);
    
ALTER TABLE reservations ADD CONSTRAINT fk_rez_hostid
    FOREIGN KEY (rez_hostid) REFERENCES hosts (h_id);
    
ALTER TABLE guest_sessions ADD CONSTRAINT fk_guest_session_userid
    FOREIGN KEY (gs_userid) REFERENCES guests (g_id)
    ON DELETE CASCADE;
    
ALTER TABLE host_sessions ADD CONSTRAINT fk_host_session_userid
    FOREIGN KEY (hs_userid) REFERENCES hosts (h_id)
    ON DELETE CASCADE;
    
/*******************************************************************************
   SEQUENCES & TRIGGERS
********************************************************************************/
CREATE SEQUENCE sq_guests_pk START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE sq_hosts_pk START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE sq_issues_pk START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE sq_reservations_pk START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE sq_host_sessions_pk START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE sq_guest_sessions_pk START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER tr_insert_guest
BEFORE INSERT ON guests
FOR EACH ROW
BEGIN
    SELECT sq_guests_pk.NEXTVAL INTO :NEW.g_id FROM DUAL;
END;
/

CREATE OR REPLACE TRIGGER tr_insert_host
BEFORE INSERT ON hosts
FOR EACH ROW
BEGIN
    SELECT sq_hosts_pk.NEXTVAL INTO :NEW.h_id FROM DUAL;
END;
/

CREATE OR REPLACE TRIGGER tr_insert_issues
BEFORE INSERT ON issues
FOR EACH ROW
BEGIN
    SELECT sq_issues_pk.NEXTVAL INTO :NEW.i_id FROM DUAL;
END;
/

CREATE OR REPLACE TRIGGER tr_insert_rez
BEFORE INSERT ON reservations
FOR EACH ROW
BEGIN
    SELECT sq_reservations_pk.NEXTVAL INTO :NEW.rez_id FROM DUAL;
END;
/

CREATE OR REPLACE TRIGGER tr_host_sessions_rez
BEFORE INSERT ON host_sessions
FOR EACH ROW
BEGIN
    SELECT sq_host_sessions_pk.NEXTVAL INTO :NEW.hs_id FROM DUAL;
END;
/

CREATE OR REPLACE TRIGGER tr_guest_sessions_rez
BEFORE INSERT ON guest_sessions
FOR EACH ROW
BEGIN
    SELECT sq_guest_sessions_pk.NEXTVAL INTO :NEW.gs_id FROM DUAL;
END;
/

/*******************************************************************************
   INITIALIZE DATA
********************************************************************************/

Insert into rooms values (101, NULL);
Insert into rooms values (102, NULL);
Insert into rooms values (103, NULL);
Insert into rooms values (104, NULL);
Insert into rooms values (105, NULL);
Insert into rooms values (201, NULL);
Insert into rooms values (202, NULL);
Insert into rooms values (203, NULL);
Insert into rooms values (204, NULL);
Insert into rooms values (205, NULL);
Insert into rooms values (301, NULL);
Insert into rooms values (302, NULL);
Insert into rooms values (303, NULL);
Insert into rooms values (304, NULL);
Insert into rooms values (305, NULL);

Insert into hosts values (1, 'albus', 'Albus', 'Dumbledore', 'headmaster@hogwarts.edu', 'tmppass', 'tmpsalt');
Insert into guests values (1, 'harry', 'Harry', 'Potter', 'harry.potter@hogwarts.edu', 'tmppass', 'tmpsalt', 0);