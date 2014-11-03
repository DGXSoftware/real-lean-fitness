/* ================================================================================================================ */
ENM MANDATORY TABLE CREATION SQL QUERIES
/* ================================================================================================================ */
/* ================================================================================================================ */
/* ================================================================================================================ */

/* MySQLWindows Service Name (Configuration) */
DGXMySQL

/* DGX SQL Service Port (Configuration) */
Mysql@localhost:10000

/* ---------------------------------------------------------------------------------------------------------------- */
/* (Command Prompt)*/
/* ---------------------------------------------------------------------------------------------------------------- */

/* Shutdown MySQL Server */
net stop DGXMySQL

/* Start MySQL Server */
net start DGXMySQL

/* ---------------------------------------------------------------------------------------------------------------- */

/* Manualy add the MySQL Bin path to the Environmental System Variable "path" */
/* NOTE: Separate paths with the semi-colon delimiter ";" */
C:\Program Files\MySQL\MySQL Server 5.5\bin

/* Login as Root WITH the Environment Variables path */
mysql  --port=10000 --host=localhost -u root -p

/* Login as Root WITHOUT using the Environment Variables path */
"C:\Program Files\MySQL\MySQL Server 5.5\bin\mysql" --port=10000 --host=localhost -u root -p

/* ---------------------------------------------------------------------------------------------------------------- */

/* Create an User and Password */
CREATE USER 'RLFSoftware'@'localhost' IDENTIFIED BY 'RLFPassword';

/* Grant the user access to use the database and login */
/* NOTE: The asterisks in this command refer to the database and table (respectively) that 
they can access—this specific command allows to the user to read, edit, execute and perform 
all tasks across all the databases and tables.  */
GRANT ALL PRIVILEGES ON *.* TO 'RLFSoftware'@'localhost';

/* Once you have finalized the permissions that you want to set up for your new users, 
always be sure to reload all the privileges.  */
FLUSH PRIVILEGES;

/* Show the current User's along with their host and Encrypted Passwords */
SELECT User,Host,Password from MySQL.USER;

/* Delete an User */
DROP USER 'RLFSoftware'@'localhost';

/* ---------------------------------------------------------------------------------------------------------------- */

/* Log into DB Accounts (NOTE: WRITE THIS IN THE WINDOWS COMMAND PROMPT) */

/* Log in as Root */
mysql -u root -p
/* :: Login as root WITH the Environment Variables path, port and host :: */
mysql --port=10000 --host=localhost -u root -p
/* :: OR Login as root WITHOUT using the Environment Variables path :: */
"C:\Program Files\MySQL\MySQL Server 5.5\bin\mysql" --port=10000 --host=localhost -u root -p

/* Log in as User */
mysql -u RLFSoftware -p
/* :: Login as RLFSoftware WITH the Environment Variables path, port and host :: */
mysql --port=10000 --host=localhost -u RLFSoftware -p
/* :: OR Login as RLFSoftware WITHOUT using the Environment Variables path :: */
"C:\Program Files\MySQL\MySQL Server 5.5\bin\mysql" --port=10000 --host=localhost -u RLFSoftware -p

/* ---------------------------------------------------------------------------------------------------------------- */

/* Create RLF Database */
CREATE DATABASE RLFDB;

/* ---------------------------------------------------------------------------------------------------------------- */

/* Log into the RLF Database */
USE RLFDB;

/* ---------------------------------------------------------------------------------------------------------------- */

/* Create the Accounts Table */
CREATE TABLE RLF_Accounts (

Account_ID INT NOT NULL AUTO_INCREMENT,

Username VARCHAR(32) NOT NULL,
First_Name VARCHAR(32),
Last_Name VARCHAR(32),
Password VARCHAR(32),
EMail VARCHAR(255),
Gender VARCHAR(32),
Date_Of_Birth DATE,
Account_Created_On DATETIME,
Is_Activated CHAR(1),
Is_Verified CHAR(1),

PRIMARY KEY (Account_ID),
UNIQUE (Account_ID),
UNIQUE (Username),
UNIQUE (EMail)

)ENGINE=INNODB;

/* Begin the Auto Increment for Account_ID at 1000000000 */
ALTER TABLE RLF_Accounts AUTO_INCREMENT = 1000000000;

/* ---------------------------------------------------------------------------------------------------------------- */

/* Create the Images Table */
CREATE TABLE RLF_Images (

Image_ID INT NOT NULL AUTO_INCREMENT,
Account_ID INT NOT NULL,
Image_Location VARCHAR(255),
Image_Caption VARCHAR(255),
Primary_Image TINYINT(1),

PRIMARY KEY (Image_ID),
UNIQUE (Image_ID),
FOREIGN KEY (Account_ID) REFERENCES RLF_Accounts (Account_ID) ON DELETE CASCADE

)ENGINE=INNODB;

/* ---------------------------------------------------------------------------------------------------------------- */

/* Create the UserInformation Table */
CREATE TABLE RLF_User_Information (

Account_ID INT NOT NULL,

Location_Address VARCHAR(32),
Location_City VARCHAR(32),
Location_State VARCHAR(32),
Location_ZipCode VARCHAR(32),
Location_Country VARCHAR(32),

PRIMARY KEY (Account_ID),
UNIQUE (Account_ID),
FOREIGN KEY (Account_ID) REFERENCES RLF_Accounts (Account_ID) ON DELETE CASCADE

)ENGINE=INNODB;

/* ---------------------------------------------------------------------------------------------------------------- */

/* Create the Newsletters Table */
CREATE TABLE RLF_Newsletters (

EMail VARCHAR(255) NOT NULL,
Full_Name VARCHAR(32),
In_Newsletter CHAR(1),
Subscribed_On DATETIME,
Has_Account CHAR(1),

PRIMARY KEY (EMail),
UNIQUE (EMail)

)ENGINE=INNODB;

/* ---------------------------------------------------------------------------------------------------------------- */
/* ---------------------------------------------------------------------------------------------------------------- */
DROP All Tables and Database
/* ---------------------------------------------------------------------------------------------------------------- */

/* Log into the Database */
USE RLFDB;

/* Drop All The RLF Tables */
DROP TABLE RLF_Newsletters;
DROP TABLE RLF_User_Information;
DROP TABLE RLF_Images;
DROP TABLE RLF_Accounts;

/* Drop The RLF Database */
DROP DATABASE RLFDB;

/* ================================================================================================================ */
