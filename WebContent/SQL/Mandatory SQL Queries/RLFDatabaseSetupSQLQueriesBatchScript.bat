@echo off
::==================================================================================================================::
:: ADD ANY SQL QUERY BELOW
:: START THE MySQL QUERY; Save Query lines to the TEMP\DGXExecuteTemporarySQLCommand File ::
if not exist ".\TEMP" mkdir ".\TEMP"
ECHO. > TEMP\DGXExecuteTemporarySQLCommand.txt
::==================================================================================================================::

:: Create RLF Database
ECHO /* Create RLF Database */ >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO CREATE DATABASE RLFDB; >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO. >> TEMP\DGXExecuteTemporarySQLCommand.txt

:: Log into the RLF Database
ECHO /* Log into the Database */ >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO USE RLFDB; >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO. >> TEMP\DGXExecuteTemporarySQLCommand.txt

:: Create the Accounts Table
ECHO /* Create the Accounts Table */ >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO CREATE TABLE RLF_Accounts ( >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO. >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO Account_ID INT NOT NULL AUTO_INCREMENT, >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO. >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO Username VARCHAR(32) NOT NULL, >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO First_Name VARCHAR(32), >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO Last_Name VARCHAR(32), >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO Password VARCHAR(32), >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO EMail VARCHAR(255), >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO Gender VARCHAR(32), >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO Date_Of_Birth DATE, >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO Account_Created_On DATETIME, >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO Is_Activated CHAR(1), >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO In_NewsLetter CHAR(1), >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO. >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO PRIMARY KEY (Account_ID), >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO UNIQUE (Account_ID), >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO UNIQUE (Username), >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO UNIQUE (EMail) >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO. >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO )ENGINE=INNODB; >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO. >> TEMP\DGXExecuteTemporarySQLCommand.txt

:: Begin the Auto Increment for Account_ID at 1000000000
ECHO /* Begin the Auto Increment for Account_ID at 1000000000 */ >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO ALTER TABLE RLF_Accounts AUTO_INCREMENT = 1000000000; >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO. >> TEMP\DGXExecuteTemporarySQLCommand.txt

:: Create the Images Table
ECHO /* Create the Images Table */ >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO CREATE TABLE RLF_Images ( >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO. >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO Image_ID INT NOT NULL AUTO_INCREMENT, >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO Account_ID INT NOT NULL, >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO Image_Location VARCHAR(255), >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO Image_Caption VARCHAR(255), >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO Primary_Image TINYINT(1), >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO. >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO PRIMARY KEY (Image_ID), >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO UNIQUE (Image_ID), >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO FOREIGN KEY (Account_ID) REFERENCES RLF_Accounts (Account_ID) ON DELETE CASCADE >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO. >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO )ENGINE=INNODB; >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO. >> TEMP\DGXExecuteTemporarySQLCommand.txt

:: Create the UserInformation Table
ECHO /* Create the UserInformation Table */ >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO CREATE TABLE RLF_User_Information ( >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO. >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO Account_ID INT NOT NULL, >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO. >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO Location_Address VARCHAR(32), >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO Location_City VARCHAR(32), >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO Location_State VARCHAR(32), >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO Location_ZipCode VARCHAR(32), >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO Location_Country VARCHAR(32), >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO. >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO PRIMARY KEY (Account_ID), >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO UNIQUE (Account_ID), >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO FOREIGN KEY (Account_ID) REFERENCES RLF_Accounts (Account_ID) ON DELETE CASCADE >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO. >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO )ENGINE=INNODB; >> TEMP\DGXExecuteTemporarySQLCommand.txt

::==================================================================================================================::
:: END THE MySQL QUERY; Save Query lines to the TEMP\DGXExecuteTemporarySQLCommand File ::
::==================================================================================================================::

::==================================================================================================================::
:: START THE SQL QUERY INPUT SECTION ; Adds the SQL Query which will be executed to the OUTPUT\DGXExecuteSQLCommandOutput File ::
if not exist ".\OUTPUT" mkdir ".\OUTPUT"
ECHO. > OUTPUT\DGXExecuteSQLCommandOutput.txt
::==================================================================================================================::

ECHO =============================================================================================================== >> OUTPUT\DGXExecuteSQLCommandOutput.txt
ECHO SQL QUERY INPUT >> OUTPUT\DGXExecuteSQLCommandOutput.txt
ECHO =============================================================================================================== >> OUTPUT\DGXExecuteSQLCommandOutput.txt
ECHO. >> OUTPUT\DGXExecuteSQLCommandOutput.txt
type TEMP\DGXExecuteTemporarySQLCommand.txt >> OUTPUT\DGXExecuteSQLCommandOutput.txt
ECHO. >> OUTPUT\DGXExecuteSQLCommandOutput.txt

::==================================================================================================================::
:: END THE SQL QUERY INPUT SECTION ; Adds the SQL Query which will be executed to the OUTPUT\DGXExecuteSQLCommandOutput File ::
::==================================================================================================================::

::==================================================================================================================::
:: START THE SQL QUERY OUTPUT SECTION ; Adds the Output of the TEMP\DGXExecuteTemporarySQLCommand File to the OUTPUT\DGXExecuteSQLCommandOutput File ::
ECHO =============================================================================================================== >> OUTPUT\DGXExecuteSQLCommandOutput.txt
ECHO SQL QUERY OUTPUT >> OUTPUT\DGXExecuteSQLCommandOutput.txt
ECHO =============================================================================================================== >> OUTPUT\DGXExecuteSQLCommandOutput.txt
ECHO. >> OUTPUT\DGXExecuteSQLCommandOutput.txt

:: LOGIN AS RLFSoftware, EXECUTE THE TEMP\DGXExecuteTemporarySQLCommand FILE, AND SAVE THE OUTPUT ON THE OUTPUT\DGXExecuteSQLCommandOutput FILE ::
mysql --port=10000 --host=localhost -u RLFSoftware -p < TEMP\DGXExecuteTemporarySQLCommand.txt >> OUTPUT\DGXExecuteSQLCommandOutput.txt

ECHO. >> OUTPUT\DGXExecuteSQLCommandOutput.txt
:: END THE SQL QUERY OUTPUT SECTION ; Adds the Output of the TEMP\DGXExecuteTemporarySQLCommand File to the OUTPUT\DGXExecuteSQLCommandOutput File ::
::==================================================================================================================::

::==================================================================================================================::
:: DELETE The TEMP\DGXExecuteTemporarySQLCommand File ::
DEL TEMP\DGXExecuteTemporarySQLCommand.txt

:: Notify the user of the actions taken
echo.
echo "The query was executed Successfully!"
echo.

pause
::==================================================================================================================::