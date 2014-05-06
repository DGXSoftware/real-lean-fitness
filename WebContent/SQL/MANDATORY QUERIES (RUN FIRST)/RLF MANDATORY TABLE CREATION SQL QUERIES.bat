@echo off
::=======================================================================================================::
:: ADD ANY SQL QUERY BELOW
:: START THE MySQL QUERY; Save Query lines to the DGXExecuteTemporarySQLCommand File ::

ECHO /* Create database */ > DGXExecuteTemporarySQLCommand.txt
ECHO CREATE DATABASE RLFDB; >> DGXExecuteTemporarySQLCommand.txt
ECHO. >> DGXExecuteTemporarySQLCommand.txt
ECHO /* Log into the Database */ >> DGXExecuteTemporarySQLCommand.txt
ECHO USE RLFDB; >> DGXExecuteTemporarySQLCommand.txt
ECHO. >> DGXExecuteTemporarySQLCommand.txt
ECHO /* Create the Accounts Table */ >> DGXExecuteTemporarySQLCommand.txt
ECHO CREATE TABLE RLFDB_Accounts ( >> DGXExecuteTemporarySQLCommand.txt
ECHO. >> DGXExecuteTemporarySQLCommand.txt
ECHO Account_ID INT NOT NULL AUTO_INCREMENT, >> DGXExecuteTemporarySQLCommand.txt
ECHO. >> DGXExecuteTemporarySQLCommand.txt
ECHO Username VARCHAR(32) NOT NULL, >> DGXExecuteTemporarySQLCommand.txt
ECHO Password VARCHAR(32), >> DGXExecuteTemporarySQLCommand.txt
ECHO EMail VARCHAR(255), >> DGXExecuteTemporarySQLCommand.txt
ECHO Gender VARCHAR(32), >> DGXExecuteTemporarySQLCommand.txt
ECHO Date_Of_Birth DATE, >> DGXExecuteTemporarySQLCommand.txt
ECHO Account_Creation_Date DATE, >> DGXExecuteTemporarySQLCommand.txt
ECHO Account_Creation_Time TIME, >> DGXExecuteTemporarySQLCommand.txt
ECHO Account_Creation_TimeZone VARCHAR(32), >> DGXExecuteTemporarySQLCommand.txt
ECHO. >> DGXExecuteTemporarySQLCommand.txt
ECHO PRIMARY KEY (Account_ID), >> DGXExecuteTemporarySQLCommand.txt
ECHO UNIQUE (Account_ID), >> DGXExecuteTemporarySQLCommand.txt
ECHO UNIQUE (Username) >> DGXExecuteTemporarySQLCommand.txt
ECHO. >> DGXExecuteTemporarySQLCommand.txt
ECHO )ENGINE=INNODB; >> DGXExecuteTemporarySQLCommand.txt
ECHO. >> DGXExecuteTemporarySQLCommand.txt
ECHO /* Begin the Auto Increment for Account_ID at 1000000000 */ >> DGXExecuteTemporarySQLCommand.txt
ECHO ALTER TABLE RLFDB_Accounts AUTO_INCREMENT = 1000000000; >> DGXExecuteTemporarySQLCommand.txt
ECHO. >> DGXExecuteTemporarySQLCommand.txt
ECHO /* Create the Images Table */ >> DGXExecuteTemporarySQLCommand.txt
ECHO CREATE TABLE RLFDB_Images ( >> DGXExecuteTemporarySQLCommand.txt
ECHO. >> DGXExecuteTemporarySQLCommand.txt
ECHO Image_ID INT NOT NULL AUTO_INCREMENT, >> DGXExecuteTemporarySQLCommand.txt
ECHO Account_ID INT NOT NULL, >> DGXExecuteTemporarySQLCommand.txt
ECHO Image_Location VARCHAR(255), >> DGXExecuteTemporarySQLCommand.txt
ECHO Image_Caption VARCHAR(255), >> DGXExecuteTemporarySQLCommand.txt
ECHO Primary_Image TINYINT(1), >> DGXExecuteTemporarySQLCommand.txt
ECHO. >> DGXExecuteTemporarySQLCommand.txt
ECHO PRIMARY KEY (Image_ID), >> DGXExecuteTemporarySQLCommand.txt
ECHO UNIQUE (Image_ID), >> DGXExecuteTemporarySQLCommand.txt
ECHO FOREIGN KEY (Account_ID) REFERENCES RLFDB_Accounts (Account_ID) ON DELETE CASCADE >> DGXExecuteTemporarySQLCommand.txt
ECHO. >> DGXExecuteTemporarySQLCommand.txt
ECHO )ENGINE=INNODB; >> DGXExecuteTemporarySQLCommand.txt
ECHO. >> DGXExecuteTemporarySQLCommand.txt
ECHO /* Create the UserInformation Table */ >> DGXExecuteTemporarySQLCommand.txt
ECHO CREATE TABLE RLFDB_User_Information ( >> DGXExecuteTemporarySQLCommand.txt
ECHO. >> DGXExecuteTemporarySQLCommand.txt
ECHO Account_ID INT NOT NULL, >> DGXExecuteTemporarySQLCommand.txt
ECHO. >> DGXExecuteTemporarySQLCommand.txt
ECHO First_Name VARCHAR(32), >> DGXExecuteTemporarySQLCommand.txt
ECHO Middle_Name VARCHAR(32), >> DGXExecuteTemporarySQLCommand.txt
ECHO Last_Name VARCHAR(32), >> DGXExecuteTemporarySQLCommand.txt
ECHO Location_Address VARCHAR(32), >> DGXExecuteTemporarySQLCommand.txt
ECHO Location_City VARCHAR(32), >> DGXExecuteTemporarySQLCommand.txt
ECHO Location_State VARCHAR(32), >> DGXExecuteTemporarySQLCommand.txt
ECHO Location_ZipCode VARCHAR(32), >> DGXExecuteTemporarySQLCommand.txt
ECHO Location_Country VARCHAR(32), >> DGXExecuteTemporarySQLCommand.txt
ECHO. >> DGXExecuteTemporarySQLCommand.txt
ECHO PRIMARY KEY (Account_ID), >> DGXExecuteTemporarySQLCommand.txt
ECHO UNIQUE (Account_ID), >> DGXExecuteTemporarySQLCommand.txt
ECHO FOREIGN KEY (Account_ID) REFERENCES RLFDB_Accounts (Account_ID) ON DELETE CASCADE >> DGXExecuteTemporarySQLCommand.txt
ECHO. >> DGXExecuteTemporarySQLCommand.txt
ECHO )ENGINE=INNODB; >> DGXExecuteTemporarySQLCommand.txt

:: END THE MySQL QUERY; Save Query lines to the DGXExecuteTemporarySQLCommand File ::
::==================================================================================================================::

::==================================================================================================================::
:: START THE SQL QUERY INPUT SECTION ; Adds the SQL Query which will be executed to the DGXExecuteSQLCommandOutput File ::
ECHO ---------------------------------- > DGXExecuteSQLCommandOutput.txt
ECHO SQL QUERY INPUT >> DGXExecuteSQLCommandOutput.txt
ECHO ---------------------------------- >> DGXExecuteSQLCommandOutput.txt
ECHO. >> DGXExecuteSQLCommandOutput.txt
type DGXExecuteTemporarySQLCommand.txt >> DGXExecuteSQLCommandOutput.txt
ECHO. >> DGXExecuteSQLCommandOutput.txt
:: END THE SQL QUERY INPUT SECTION ; Adds the SQL Query which will be executed to the DGXExecuteSQLCommandOutput File ::
::==================================================================================================================::

::==================================================================================================================::
:: START THE SQL QUERY OUTPUT SECTION ; Adds the Output of the DGXExecuteTemporarySQLCommand File to the DGXExecuteSQLCommandOutput File ::
ECHO ---------------------------------- >> DGXExecuteSQLCommandOutput.txt
ECHO SQL QUERY OUTPUT >> DGXExecuteSQLCommandOutput.txt
ECHO ---------------------------------- >> DGXExecuteSQLCommandOutput.txt
ECHO. >> DGXExecuteSQLCommandOutput.txt

:: LOGIN AS DGXSoftware, EXECUTE THE DGXExecuteTemporarySQLCommand FILE, AND SAVE THE OUTPUT ON THE DGXExecuteSQLCommandOutput FILE ::
mysql --port=10000 --host=localhost -u DGXSoftware -p < DGXExecuteTemporarySQLCommand.txt >> DGXExecuteSQLCommandOutput.txt

ECHO. >> DGXExecuteSQLCommandOutput.txt
:: END THE SQL QUERY OUTPUT SECTION ; Adds the Output of the DGXExecuteTemporarySQLCommand File to the DGXExecuteSQLCommandOutput File ::
::==================================================================================================================::

::==================================================================================================================::
:: DELETE The DGXExecuteTemporarySQLCommand File ::
DEL DGXExecuteTemporarySQLCommand.txt

:: Notify the user of the actions taken
echo "The query was executed Successfully!"

pause
::==================================================================================================================::