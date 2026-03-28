

@REM ----------------------------------------------------------------------------
@REM Licensed to the Apache Software Foundation (ASF)
@REM Maven Wrapper startup batch script
@REM ----------------------------------------------------------------------------
@IF "%__MVNW_ARG0_NAME__%"=="" (SET "MVN_CMD=mvn.cmd") ELSE (SET "MVN_CMD=%__MVNW_ARG0_NAME__%")
@SET MAVEN_PROJECTBASEDIR=%MAVEN_BASEDIR%
@IF NOT "%MAVEN_PROJECTBASEDIR%"=="" GOTO endDetectBaseDir

@SET EXEC_DIR=%CD%
@SET "WD=%EXEC_DIR%"
@SET MAVEN_PROJECTBASEDIR=%WD%
@:endDetectBaseDir

@SET MVNW_USERNAME=%MVNW_USERNAME%
@SET MVNW_PASSWORD=%MVNW_PASSWORD%

@SET WRAPPER_JAR="%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.jar"
@SET WRAPPER_LAUNCHER=org.apache.maven.wrapper.MavenWrapperMain

@SET WRAPPER_URL="https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.2.0/maven-wrapper-3.2.0.jar"

@IF EXIST %WRAPPER_JAR% (
    @SET DOWNLOAD_URL=
) ELSE (
    @echo Downloading Maven Wrapper...
    @IF NOT "%MVNW_REPOURL%" == "" SET WRAPPER_URL="%MVNW_REPOURL%/org/apache/maven/wrapper/maven-wrapper/3.2.0/maven-wrapper-3.2.0.jar"
    @powershell -Command "&{"^
        "$webclient = new-object System.Net.WebClient;"^
        "if (-not ([string]::IsNullOrEmpty('%MVNW_USERNAME%') -and [string]::IsNullOrEmpty('%MVNW_PASSWORD%'))) {"^
        "$webclient.Credentials = new-object System.Net.NetworkCredential('%MVNW_USERNAME%', '%MVNW_PASSWORD%');"^
        "}"^
        "[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; $webclient.DownloadFile('%WRAPPER_URL%', \"%WRAPPER_JAR%\")"^
        "}"
    IF "%ERRORLEVEL%"=="0" @GOTO :EXECUTE
    @echo "WARNING: Failed to download maven-wrapper.jar"
    @GOTO :ERROR
)

:EXECUTE
@FOR /F "usebackq tokens=1,2 delims==" %%A IN ("%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.properties") DO (
    @IF "%%A"=="distributionUrl" SET "DISTRIBUTION_URL=%%B"
)

@SET JAVA_HOME_VAR=%JAVA_HOME%
@IF "%JAVA_HOME_VAR%"=="" (
   @echo ERROR: JAVA_HOME not set. Please set JAVA_HOME to your JDK directory.
   @GOTO :ERROR
)

@SET JAVA_EXE="%JAVA_HOME_VAR%\bin\java.exe"
@IF NOT EXIST %JAVA_EXE% (
   @echo ERROR: Java not found at %JAVA_EXE%
   @GOTO :ERROR
)

@SET MAVEN_HOME=%USERPROFILE%\.m2\wrapper\dists
@IF NOT EXIST "%MAVEN_HOME%" MKDIR "%MAVEN_HOME%"

@%JAVA_EXE% -classpath %WRAPPER_JAR% "-Dmaven.multiModuleProjectDirectory=%MAVEN_PROJECTBASEDIR%" %WRAPPER_LAUNCHER% %MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.properties %*
@SET MVNW_EXITCODE=%ERRORLEVEL%
@GOTO :FINISH

:ERROR
@SET MVNW_EXITCODE=1

:FINISH
@EXIT /B %MVNW_EXITCODE%
