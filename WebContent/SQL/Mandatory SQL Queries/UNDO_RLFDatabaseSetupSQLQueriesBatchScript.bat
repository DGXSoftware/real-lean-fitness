@echo off
::==================================================================================================================::
:: ADD ANY SQL QUERY BELOW
:: START THE MySQL QUERY; Save Query lines to the TEMP\DGXExecuteTemporarySQLCommand File ::
if not exist ".\TEMP" mkdir ".\TEMP"
ECHO. > TEMP\DGXExecuteTemporarySQLCommand.txt
::==================================================================================================================::

:: Log into the RLF Database
ECHO /* Log into the RLF Database */ >> TEMP/DGXExecuteTemporarySQLCommand.txt
ECHO USE RLFDB; >> TEMP\DGXExecuteTemporarySQLCommand.txt
ECHO. >> TEMP\DGXExecuteTemporarySQLCommand.txt

:: Drop All The RLF Tables
ECHO /* Drop All The RLF Tables */ >> TEMP/DGXExecuteTemporarySQLCommand.txt
ECHO DROP TABLE RLF_User_Information; >> TEMP/DGXExecuteTemporarySQLCommand.txt
ECHO DROP TABLE RLF_Images; >> TEMP/DGXExecuteTemporarySQLCommand.txt
ECHO DROP TABLE RLF_Accounts; >> TEMP/DGXExecuteTemporarySQLCommand.txt
ECHO. >> TEMP/DGXExecuteTemporarySQLCommand.txt

:: Drop The RLF Database
ECHO /* Drop The RLF Database */ >> TEMP/DGXExecuteTemporarySQLCommand.txt
ECHO DROP DATABASE RLFDB; >> TEMP/DGXExecuteTemporarySQLCommand.txt
ECHO. >> TEMP/DGXExecuteTemporarySQLCommand.txt

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