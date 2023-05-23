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
	Email VARCHAR (100) PRIMARY KEY,
    Location VARCHAR(100) NOT NULL
);

--INSERT INTO Kiten(DataID, Loc, Windspeed, WindspeedUnit, waveheight, waveheightUnit, winddirection, winddirectionUnit, TimestampMeasurment) 
--VALUES ('NieuwpoortKiteable1', 'Nieuwpoort', '9.00', 'm/s', '151.00', 'cm', '10.00', 'deg', 1),
--('De PanneKiteable1', 'De Panne', '10.00', 'm/s', '151.00', 'cm', '10.00', 'deg', 1);
--INSERT INTO Feedback(FeedbackID, Loc, Username, Feedback, TimestampFeedback) VALUES ('JoranNieuwpoort1', 'Nieuwpoort', 'Joran', 'The waves are amazing', 1);
--INSERT INTO Feedback(FeedbackID, Loc, Username, Feedback, TimestampFeedback) VALUES ('JoranDe Panne1', 'De Panne', 'Joran', 'There is too little wind here', 1);