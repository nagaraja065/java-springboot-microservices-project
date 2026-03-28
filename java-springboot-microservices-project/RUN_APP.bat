@echo off
title Grand Spice Restaurant - Booking System
color 0A
cls
echo.
echo  =====================================================
echo    GRAND SPICE - Restaurant Booking System
echo  =====================================================
echo.
echo  [*] Starting server...
echo  [*] Please wait 10-15 seconds...
echo.
echo  Once started, open your browser at:
echo.
echo       ---^> http://localhost:8080 ^<---
echo.
echo  Login Details:
echo    Admin    : admin@restaurant.com  / admin123
echo    Customer : customer@gmail.com   / customer123
echo.
echo  Press Ctrl+C to STOP the server.
echo  =====================================================
echo.

set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-21.0.10.7-hotspot
set JAVA_EXE=%JAVA_HOME%\bin\java.exe

"%JAVA_EXE%" "-Dmaven.multiModuleProjectDirectory=%~dp0" -classpath "%~dp0.mvn\wrapper\maven-wrapper.jar" org.apache.maven.wrapper.MavenWrapperMain spring-boot:run

echo.
echo  [!] Server stopped.
pause
