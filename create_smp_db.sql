/*
	FILE: 	create_smp_db.sql
    DATE:	2024-02-22
    AUTHOR:	Jared Harvey
    DESCRIPTION:
		Builds a database made for the Java III final project
*/

/*****************************************************************************
							DATABASE CREATION
*****************************************************************************/

DROP DATABASE IF EXISTS smp_db;
CREATE DATABASE smp_db;
USE smp_db;

/*****************************************************************************
							TABLE CREATION
*****************************************************************************/

DROP TABLE IF EXISTS Role;
CREATE TABLE Role (
    Role NVARCHAR(50) PRIMARY KEY NOT NULL
);

DROP TABLE IF EXISTS User;
CREATE TABLE User (
	UserID NVARCHAR(255) PRIMARY KEY COMMENT 'Primary key of user is their email',
	DisplayName NVARCHAR(255) NOT NULL UNIQUE COMMENT 'NOT THE PRIMARY KEY BECAUSE USER CAN CHANGE IT',
	Password NVARCHAR(255) NOT NULL,
	Language NVARCHAR(255) NOT NULL DEFAULT 'en-US',
	Status ENUM('inactive', 'active', 'locked') NOT NULL,
    Role NVARCHAR(50) NOT NULL DEFAULT 'user',
	CreatedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	LastLoggedIn DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	UpdatedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	Pfp LONGBLOB,
    CONSTRAINT fk_User_Role
        FOREIGN KEY(Role)
            REFERENCES Role(Role)
);

CREATE TABLE 2fa_code (
	CodeID INT AUTO_INCREMENT PRIMARY KEY,
	UserID NVARCHAR(255) NOT NULL,
	Code VARCHAR(6) NOT NULL,
	Method ENUM ('email', 'sms', 'phone') NOT NULL,
	CreatedAt DATETIME DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY (UserID) REFERENCES User(UserID) ON DELETE CASCADE
);

DROP TABLE IF EXISTS World;
CREATE TABLE World (
    WorldID NVARCHAR(255) COMMENT 'Name of world is primary key',
    DateStarted DATE,
    Description TEXT,
    CONSTRAINT pk_World PRIMARY KEY(WorldID)
);

DROP TABLE IF EXISTS BuildType;
CREATE TABLE BuildType (
    BuildType NVARCHAR(255),
    Description TEXT,
    CONSTRAINT pk_BuildType PRIMARY KEY(BuildType)
);

DROP TABLE IF EXISTS Build;
CREATE TABLE Build (
	BuildID NVARCHAR(100) COMMENT 'Primary key is the name of build',
	UserID NVARCHAR(255) NOT NULL,
	Image LONGBLOB NULL,
	WorldID NVARCHAR(255) NULL,
	BuildType NVARCHAR(255) NULL,
	DateBuilt DATE NULL,
	Coordinates NVARCHAR(255) NULL,
	CreatedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	Description TEXT NULL,
	CONSTRAINT fk_Build_UserID
	    FOREIGN KEY(UserID)
		  REFERENCES User(UserID),
    CONSTRAINT fk_Build_WorldID
        FOREIGN KEY(WorldID)
            REFERENCES World(WorldID)
            ON DELETE SET NULL
            ON UPDATE CASCADE,
    CONSTRAINT fk_Build_BuildType
        FOREIGN KEY(BuildType)
            REFERENCES BuildType(BuildType)
            ON DELETE SET NULL
            ON UPDATE CASCADE,
	CONSTRAINT pk_Build PRIMARY KEY(BuildID)
);

DROP TABLE IF EXISTS DiscussionForm;
CREATE TABLE DiscussionForm (
	DiscussionID NVARCHAR(100),
	CreatedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	Active BIT DEFAULT 1,
	CONSTRAINT pk_Discussion PRIMARY KEY(DiscussionID)
);

DROP TABLE IF EXISTS Message;
CREATE TABLE Message (
	MessageID INT,
	UserID NVARCHAR(255) NOT NULL,
	DiscussionID NVARCHAR(100) NOT NULL,
	Text TEXT NOT NULL,
	SentAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT fk_Message_UserID
	 FOREIGN KEY(UserID)
		 REFERENCES User(UserID),
	CONSTRAINT fk_Message_DiscussionID
	 FOREIGN KEY(DiscussionID)
		 REFERENCES DiscussionForm(DiscussionID),
	CONSTRAINT pk_Message PRIMARY KEY(MessageID)
);

DROP TABLE IF EXISTS Vote;
CREATE TABLE Vote (
	VoteID NVARCHAR(255) PRIMARY KEY,
	UserID NVARCHAR(255) NOT NULL,
	Description TEXT NOT NULL,
	StartTime DATETIME NOT NULL,
	EndTime DATETIME NOT NULL,
    CONSTRAINT fk_Vote_UserID
        FOREIGN KEY(UserID)
            REFERENCES User(UserID)
);

DROP TABLE IF EXISTS VoteOption;
CREATE TABLE VoteOption (
    OptionID INT AUTO_INCREMENT PRIMARY KEY,
    VoteID NVARCHAR(255),
    Title NVARCHAR(255) NOT NULL,
    Description TEXT NOT NULL,
    Image LONGBLOB,
    CONSTRAINT fk_VoteOption_VoteID
        FOREIGN KEY(VoteID)
            REFERENCES Vote(VoteID)
            ON UPDATE CASCADE
            ON DELETE CASCADE
);

DROP TABLE IF EXISTS UserVote;
CREATE TABLE UserVote (
	UserID NVARCHAR(255) NOT NULL,
	VoteID NVARCHAR(255) NOT NULL,
	OptionID INT NOT NULL,
	VoteTime DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT fk_UserVote_UserID
	  FOREIGN KEY(UserID)
		  REFERENCES User(UserID),
	CONSTRAINT fk_UserVote_VoteID
	  FOREIGN KEY(VoteID)
		  REFERENCES Vote(VoteID)
          ON UPDATE CASCADE
          ON DELETE CASCADE,
	CONSTRAINT pk_UserVote PRIMARY KEY(UserID, VoteID)
);

/*****************************************************************************
					STORED PROCEDURES / CRUD FUNCTIONS
*****************************************************************************/

/*-----------------------------------USER------------------------------------*/

/* INSERT USER */
DROP PROCEDURE IF EXISTS sp_insert_user;
CREATE PROCEDURE sp_insert_user(
    IN p_UserID NVARCHAR(255),
    IN p_Password NVARCHAR(255),
    IN p_DisplayName NVARCHAR(100)
)
BEGIN
    INSERT INTO User
		(UserID, Password, DisplayName)
    VALUES
        (p_UserID, p_Password, p_DisplayName)
    ;
    -- Generate a random 6 digit code
    SET @code=LPAD(FLOOR(RAND() * 999999.99), 6, '0');
    -- Create new 2 Factor Authentication Code
    INSERT INTO 2fa_code (UserID, Code, Method) VALUES (p_UserID, @code, 'email')
    ;
END;

/* GET ALL USER */
DROP PROCEDURE IF EXISTS sp_get_all_users;
CREATE PROCEDURE sp_get_all_users()
BEGIN
    SELECT UserID, DisplayName, Password, Language, Status, Role, CreatedAt, LastLoggedIn, UpdatedAt, Pfp
    FROM User
    ;
END;

/* UPDATE USER */
DROP PROCEDURE IF EXISTS sp_update_user;
CREATE PROCEDURE sp_update_user(
    IN p_UserID NVARCHAR(255),
    IN p_DisplayName NVARCHAR(100),
    IN p_Language NVARCHAR(255),
    IN p_Status NVARCHAR(255),
    IN p_Role NVARCHAR(255),
    IN p_LastLoggedIn DATETIME
)
BEGIN
    UPDATE User
    SET
        DisplayName =  p_DisplayName,
        Language =  p_Language,
        Status = p_Status,
        Role = p_Role,
        LastLoggedIn = p_LastLoggedIn
    WHERE UserID = p_UserID
    ;
END;

/* DELETE USER */
DROP PROCEDURE IF EXISTS sp_delete_user;
CREATE PROCEDURE sp_delete_user(
    IN p_UserID NVARCHAR(255)
)
BEGIN
    DELETE FROM User
    WHERE UserID = p_UserID
    ;
END;

/* GET USER */
DROP PROCEDURE IF EXISTS sp_get_user;
CREATE PROCEDURE sp_get_user(
    IN p_UserID NVARCHAR(255)
)
BEGIN
    SELECT 	UserID, Password, DisplayName, Language, Status,
			Role, CreatedAt, LastLoggedIn, UpdatedAt, Pfp
	FROM User
	WHERE UserID = p_UserID
    ;
END;

/* GET user by displayname */
DROP PROCEDURE IF EXISTS sp_get_user_by_displayname;
CREATE PROCEDURE sp_get_user_by_displayname(
    IN p_DisplayName NVARCHAR(255)
)
BEGIN
    SELECT 	UserID, Password, DisplayName, Language, Status,
              Role, CreatedAt, LastLoggedIn, UpdatedAt, Pfp
    FROM User
    WHERE DisplayName = p_DisplayName
    ;
END;

/*-----------------------------------2FACODE------------------------------------*/

/* ADD 2FA CODE */
DROP PROCEDURE IF EXISTS sp_add_2fa_code;
CREATE PROCEDURE sp_add_2fa_code(
    IN p_UserID NVARCHAR(255),
    IN p_Code VARCHAR(6),
    IN p_Method ENUM('email', 'sms', 'phone')
)
BEGIN
    -- Delete any previous 2fa_code
    DELETE FROM 2fa_code WHERE UserID = p_UserID;
    -- Create a new 2FA code
    INSERT INTO 2fa_code (UserID, Code, Method) VALUES (p_UserID, p_Code, p_Method)
    ;
END;

/* GET 2FA CODE */
DROP PROCEDURE IF EXISTS sp_get_2fa_code;
CREATE PROCEDURE sp_get_2fa_code(
	IN p_UserID VARCHAR(255)
)
BEGIN
    SELECT t.Code, t.Method, t.CreatedAt
    FROM User AS u
    INNER JOIN 2fa_code AS t
    ON u.UserID = t.UserID
    WHERE t.UserID = p_UserID
    ;
END;

/*-----------------------------------BUILD------------------------------------*/

/* INSERT Build */
DROP PROCEDURE IF EXISTS sp_insert_build;
CREATE PROCEDURE sp_insert_build(
    IN p_BuildID NVARCHAR(100),
    IN p_UserID NVARCHAR(255),
    IN p_Image LONGBLOB,
    IN p_WorldID NVARCHAR(255),
    IN p_BuildType NVARCHAR(255),
    IN p_DateBuilt DATE,
    IN p_Coordinates NVARCHAR(255),
    IN p_Description TEXT
)
BEGIN
    INSERT INTO Build
        (BuildID, UserID, Image, WorldID, BuildType, DateBuilt, Coordinates, Description)
    VALUES
        (p_BuildID, p_UserID, p_Image, p_WorldID, p_BuildType, p_DateBuilt, p_Coordinates, p_Description)
    ;
END;

/* UPDATE Build */
DROP PROCEDURE IF EXISTS sp_update_build;
CREATE PROCEDURE sp_update_build(
    IN p_BuildID NVARCHAR(100),
    IN p_Image LONGBLOB,
    IN p_WorldID NVARCHAR(255),
    IN p_BuildType NVARCHAR(255),
    IN p_DateBuilt DATE,
    IN p_Coordinates NVARCHAR(255),
    IN p_Description TEXT
)
BEGIN
    UPDATE Build
    SET Image = p_Image,
        WorldID = p_WorldID,
        BuildType = p_BuildType,
        DateBuilt = p_DateBuilt,
        Coordinates = p_Coordinates,
        Description = p_Description
    WHERE BuildID = p_BuildID
    ;
END;

/* DELETE Build */
DROP PROCEDURE IF EXISTS sp_delete_build;
CREATE PROCEDURE sp_delete_build(
    IN p_BuildID NVARCHAR(100)
)
BEGIN
    DELETE FROM Build
    WHERE BuildID = p_BuildID
    ;
END;

/* GET Builds */
DROP PROCEDURE IF EXISTS sp_get_builds;
CREATE PROCEDURE sp_get_builds(
    IN p_limit INT,
    IN p_offset INT,
    IN p_WorldID NVARCHAR(255),
    IN p_BuildType NVARCHAR(255),
    # TODO: Add ability to search by dateBuilt
    IN p_UserDisplayName NVARCHAR(255)
)
BEGIN
    SELECT BuildID, Image, DateBuilt, Coordinates, b.CreatedAt, b.Description AS 'build_description', u.UserID, u.DisplayName, u.Pfp, w.WorldID, w.DateStarted, w.Description AS 'world_description', bt.BuildType, bt.Description AS 'buildtype_description'
	FROM Build AS b
	INNER JOIN User AS u ON u.UserID = b.UserID
	INNER JOIN World AS w ON b.WorldID = w.WorldID
	INNER JOIN BuildType AS bt ON b.BuildType = bt.BuildType
    WHERE (
        IF(p_WorldID <> '', p_WorldID LIKE CONCAT('%', b.WorldID, '%'), TRUE)
    )
    AND (
        IF(p_BuildType <> '', p_BuildType LIKE CONCAT('%', b.BuildType, '%'), TRUE)
    )
    AND (
        IF(p_UserDisplayName <> '', p_UserDisplayName LIKE CONCAT('%', u.DisplayName, '%'), TRUE)
    )
    LIMIT p_limit OFFSET p_offset
    ;
END;

/* GET Build */
DROP PROCEDURE IF EXISTS sp_get_build;
CREATE PROCEDURE sp_get_build(
	IN p_BuildID NVARCHAR(100)
)
BEGIN
    SELECT BuildID, UserID, Image, WorldID, BuildType, DateBuilt, Coordinates, CreatedAt, Description
    FROM Build
    WHERE BuildID = p_BuildID
    ;
END;

/*-----------------------------------WORLD------------------------------------*/

/* INSERT World */
DROP PROCEDURE IF EXISTS sp_insert_world;
CREATE PROCEDURE sp_insert_world(
    IN p_WorldID NVARCHAR(255),
    IN p_DateStarted DATE,
    IN p_Description TEXT
)
BEGIN
    INSERT INTO World
        (WorldID, DateStarted, Description)
    VALUES
        (p_WorldID, p_DateStarted, p_Description)
    ;
END;

/* UPDATE World */
DROP PROCEDURE IF EXISTS sp_update_world;
CREATE PROCEDURE sp_update_world(
    IN p_WorldID NVARCHAR(255),
    IN p_DateStarted DATE,
    IN p_Description TEXT
)
BEGIN
    UPDATE World
    SET DateStarted = p_DateStarted,
        Description = p_Description
    WHERE WorldID = p_WorldID
    ;
END;

/* DELETE World */
DROP PROCEDURE IF EXISTS sp_delete_world;
CREATE PROCEDURE sp_delete_world(
    IN p_WorldID NVARCHAR(255)
)
BEGIN
    DELETE FROM World
    WHERE WorldID = p_WorldID
    ;
END;

/* GET Worlds */
DROP PROCEDURE IF EXISTS sp_get_worlds;
CREATE PROCEDURE sp_get_worlds()
BEGIN
    SELECT WorldID, DateStarted, Description
    FROM World
    ORDER BY DateStarted DESC
    ;
END;

/* GET World */
DROP PROCEDURE IF EXISTS sp_get_world;
CREATE PROCEDURE sp_get_world(
    IN p_WorldID NVARCHAR(255)
)
BEGIN
    SELECT WorldID, DateStarted, Description
    FROM World
    WHERE WorldID = p_WorldID
    ;
END;

/*-----------------------------------BUILDTYPE------------------------------------*/

/* INSERT BuildType */
DROP PROCEDURE IF EXISTS sp_insert_buildtype;
CREATE PROCEDURE sp_insert_buildtype(
    IN p_BuildType NVARCHAR(255),
    IN p_Description TEXT
)
BEGIN
    INSERT INTO BuildType
        (BuildType, Description)
    VALUES
        (p_BuildType, p_Description)
    ;
END;

/* UPDATE BuildType */
DROP PROCEDURE IF EXISTS sp_update_buildtype;
CREATE PROCEDURE sp_update_buildtype(
    IN p_BuildType NVARCHAR(255),
    IN p_Description TEXT
)
BEGIN
    UPDATE BuildType
    SET Description = p_Description
    WHERE BuildType = p_BuildType
    ;
END;

/* DELETE BuildType */
DROP PROCEDURE IF EXISTS sp_delete_buildtype;
CREATE PROCEDURE sp_delete_buildtype(
    IN p_BuildType NVARCHAR(255)
)
BEGIN
    DELETE FROM BuildType
    WHERE BuildType = p_BuildType
    ;
END;

/* GET BuildTypes */
DROP PROCEDURE IF EXISTS sp_get_buildtypes;
CREATE PROCEDURE sp_get_buildtypes()
BEGIN
    SELECT BuildType, Description
    FROM BuildType
    ;
END;

/* GET BuildType */
DROP PROCEDURE IF EXISTS sp_get_buildtype;
CREATE PROCEDURE sp_get_buildtype(
    IN p_BuildType NVARCHAR(255)
)
BEGIN
    SELECT BuildType, Description
    FROM BuildType
    WHERE BuildType = p_BuildType
    ;
END;

/*-----------------------------------VOTE------------------------------------*/

/* INSERT Vote */
DROP PROCEDURE IF EXISTS sp_insert_vote;
CREATE PROCEDURE sp_insert_vote(
    IN p_VoteID NVARCHAR(255),
    IN p_UserID NVARCHAR(255),
    IN p_Description TEXT,
    IN p_StartTime DATETIME,
    IN p_EndTime DATETIME
)
BEGIN
    INSERT INTO Vote
        (VoteID, UserID, Description, StartTime, EndTime)
    VALUES
        (p_VoteID, p_UserID, p_Description, p_StartTime, p_EndTime)
    ;
END;

/* UPDATE Vote */
DROP PROCEDURE IF EXISTS sp_update_vote;
CREATE PROCEDURE sp_update_vote(
    IN p_VoteID NVARCHAR(255),
    IN p_Description TEXT,
    IN p_StartTime DATETIME,
    IN p_EndTime DATETIME
)
BEGIN
    UPDATE Vote
    SET VoteID = p_VoteID,
        Description = p_Description,
        StartTime = p_StartTime,
        EndTime = p_EndTime
    WHERE VoteID = p_VoteID
    ;
END;

/* DELETE Vote */
DROP PROCEDURE IF EXISTS sp_delete_vote;
CREATE PROCEDURE sp_delete_vote(
    IN p_VoteID NVARCHAR(255)
)
BEGIN
    DELETE FROM Vote
    WHERE VoteID = p_VoteID
    ;
END;

/* GET Votes */
DROP PROCEDURE IF EXISTS sp_get_votes;
CREATE PROCEDURE sp_get_votes()
BEGIN
    SELECT v.VoteID, v.UserID, Description, StartTime, EndTime, COUNT(uv.UserID) AS 'num_of_votes'
    FROM Vote AS v
    LEFT JOIN UserVote AS uv ON v.VoteID = uv.VoteID
    GROUP BY v.VoteID, v.UserID, Description, StartTime, EndTime
    ;
END;

/* GET Vote */
DROP PROCEDURE IF EXISTS sp_get_vote;
CREATE PROCEDURE sp_get_vote(
    IN p_VoteID NVARCHAR(255)
)
BEGIN
    SELECT v.VoteID, v.UserID, v.Description, v.StartTime, v.EndTime, COUNT(vo.VoteID) AS 'num_of_options'
    FROM Vote AS v
    INNER JOIN VoteOption AS vo ON v.VoteID = vo.VoteID
    WHERE v.VoteID = p_VoteID
    GROUP BY vo.VoteID
    ;
END;

/*-----------------------------------VOTEOPTION------------------------------------*/

/* INSERT VoteOption */
DROP PROCEDURE IF EXISTS sp_insert_voteoption;
CREATE PROCEDURE sp_insert_voteoption(
    IN p_VoteID NVARCHAR(255),
    IN p_Title NVARCHAR(255),
    IN p_Description TEXT,
    IN p_Image LONGBLOB
)
BEGIN
    INSERT INTO VoteOption
        (VoteID, Title, Description, Image)
    VALUES
        (p_VoteID, p_Title, p_Description, p_Image)
    ;
END;

/* UPDATE VoteOption */
DROP PROCEDURE IF EXISTS sp_update_voteoption;
CREATE PROCEDURE sp_update_voteoption(
    IN p_OptionID INT,
    IN p_Title NVARCHAR(255),
    IN p_Description TEXT,
    IN p_Image LONGBLOB
)
BEGIN
    UPDATE VoteOption
    SET Title = p_Title,
        Description = p_Description,
        Image = p_Image
    WHERE OptionID = p_OptionID
    ;
END;

/* DELETE VoteOption */
DROP PROCEDURE IF EXISTS sp_delete_voteoption;
CREATE PROCEDURE sp_delete_voteoption(
    IN p_OptionID INT
)
BEGIN
    DELETE FROM VoteOption
    WHERE OptionID = p_OptionID
    ;
END;

/* GET VoteOptions */
DROP PROCEDURE IF EXISTS sp_get_voteoptions;
CREATE PROCEDURE sp_get_voteoptions(
    IN p_VoteID NVARCHAR(255)
)
BEGIN
    SELECT OptionID, Title, Description, Image
    FROM VoteOption
    WHERE VoteID = p_VoteID
    ;
END;

/* GET VoteOption */
DROP PROCEDURE IF EXISTS sp_get_voteoption;
CREATE PROCEDURE sp_get_voteoption(
    IN p_OptionID INT
)
BEGIN
    SELECT OptionID, Title, Description, Image
    FROM VoteOption
    WHERE OptionID = p_OptionID
    ;
END;

/*****************************************************************************
					            TEST RECORDS
*****************************************************************************/

INSERT INTO Role (Role) VALUE ('user');
INSERT INTO Role (Role) VALUE ('moderator');
INSERT INTO Role (Role) VALUE ('admin');

INSERT INTO BuildType (BuildType, Description) VALUES ('Sky Scraper', 'Very tall Build.');
INSERT INTO BuildType (BuildType, Description) VALUES ('Capitol', 'An elegant government Build where congressional hearings are sometimes held.');
INSERT INTO BuildType (BuildType, Description) VALUES ('Cathedral', 'Religious Build of great stature');
INSERT INTO BuildType (BuildType, Description) VALUES ('Mega-build', 'Huge Builds that take forever to build.');
INSERT INTO BuildType (BuildType, Description) VALUES ('Shop', 'Place to buy and/or sell goods.');

INSERT INTO World (WorldID, DateStarted, Description) VALUES ('No Go Outside', '2020-5-11', 'Built in the No Go Outside world');
INSERT INTO World (WorldID, DateStarted, Description) VALUES ('SummerSMP2022', '2022-6-18', 'Built in the SummerSMP2022 world');

INSERT INTO User (UserID, DisplayName, Password, Status, Role) VALUES ('jared.harvey10@gmail.com', 'Jared Harvey', '$2a$12$ChtjH38V8VqFtkJNUPK8Y.khnWXhwzyeBDVZlDqwUrR.wnBZhy51e', 'active', 'admin');

INSERT INTO Build (BuildID, UserID, WorldID, BuildType, Description) VALUES ('Test Build', 'jared.harvey10@gmail.com', 'SummerSMP2022', 'Cathedral', 'beansbeansbeansbeansbeans');
