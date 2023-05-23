-- Database: Stageopdracht

-- DROP DATABASE IF EXISTS Stageopdracht;

-- CREATE DATABASE Stageopdracht;
--     WITH
--     OWNER = "user"
--     ENCODING = 'UTF8'
--     LC_COLLATE = 'en_US.utf8'
--     LC_CTYPE = 'en_US.utf8'
--     TABLESPACE = pg_default
--     CONNECTION LIMIT = -1
--     IS_TEMPLATE = False;
	
CREATE TABLE Kiten (
DataID VARCHAR (100) PRIMARY KEY,
Loc VARCHAR (100) NOT NULL,
Windspeed VARCHAR (100) NOT NULL,
WindspeedUnit VARCHAR (100) NOT NULL,
Waveheight VARCHAR (100) NOT NULL,
WaveheightUnit VARCHAR (100) NOT NULL,
Winddirection VARCHAR (100) NOT NULL,
WinddirectionUnit VARCHAR (100) NOT NULL,
TimestampMeasurment INTEGER NOT NULL
);

CREATE TABLE Feedback(
 FeedbackID VARCHAR(100) PRIMARY KEY,
 Loc VARCHAR(100) NOT NULL,
 Username VARCHAR(100) NOT NULL,
 Feedback VARCHAR(256) NOT NULL,
 TimestampFeedback BIGSERIAL NOT NULL
);

CREATE TABLE keepUpdated (
	SubscriptionID VARCHAR(200) PRIMARY KEY,
	Email VARCHAR (100) NOT NULL,
    Location VARCHAR(100) NOT NULL
);

INSERT INTO Kiten(DataID, Loc, Windspeed, WindspeedUnit, waveheight, waveheightUnit, winddirection, winddirectionUnit, TimestampMeasurment) 
VALUES ('NieuwpoortKiteable1', 'Nieuwpoort', '9.00', 'm/s', '151.00', 'cm', '10.00', 'deg', 1),
('De PanneKiteable1', 'De Panne', '10.00', 'm/s', '151.00', 'cm', '10.00', 'deg', 1);
INSERT INTO Feedback(FeedbackID, Loc, Username, Feedback, TimestampFeedback) VALUES ('JoranNieuwpoort1', 'Nieuwpoort', 'Joran', 'The waves are amazing', 1);
INSERT INTO Feedback(FeedbackID, Loc, Username, Feedback, TimestampFeedback) VALUES ('JoranDe Panne1', 'De Panne', 'Joran', 'There is too little wind here', 1);