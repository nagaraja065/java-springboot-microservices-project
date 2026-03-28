@echo off
cd /d C:\Users\User\OneDrive\Desktop\viru\java_02
"C:\Program Files\Eclipse Adoptium\jdk-21.0.10.7-hotspot\bin\java.exe" -Dmaven.multiModuleProjectDirectory=C:\Users\User\OneDrive\Desktop\viru\java_02 -classpath .mvn\wrapper\maven-wrapper.jar org.apache.maven.wrapper.MavenWrapperMain spring-boot:run
pause
