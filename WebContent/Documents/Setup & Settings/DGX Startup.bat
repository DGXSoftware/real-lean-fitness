
:: Open a File ::
call "C:\DMGX's\Programming Projects\Java\Tomcat Server\bin\startup.bat"

:: Time out ::
TIMEOUT 3

:: Open a Folder ::
%SystemRoot%\explorer.exe "C:\DMGX's\Programming Projects\Java\Tomcat Server\webapps"

TIMEOUT 3

:: Open your internet browser with the following URL ::
start "C:\Program Files (x86)\Mozilla Firefox\" "firefox.exe" "http://24.228.100.92:9000/HomePageServlet"

exit

