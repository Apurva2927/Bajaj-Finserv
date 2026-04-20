@echo off
setlocal enabledelayedexpansion

@REM Find Maven installation
if exist "%ProgramFiles%\apache-maven-3.9.6\bin\mvn.cmd" (
  set MAVEN_CMD="%ProgramFiles%\apache-maven-3.9.6\bin\mvn.cmd"
  goto runMaven
)

if exist "%ProgramFiles(x86)%\apache-maven-3.9.6\bin\mvn.cmd" (
  set MAVEN_CMD="%ProgramFiles(x86)%\apache-maven-3.9.6\bin\mvn.cmd"
  goto runMaven
)

@REM Download Maven if not found
echo Downloading Maven...
powershell -Command "iwr -Uri 'https://archive.apache.org/dist/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.zip' -OutFile 'maven.zip'; Expand-Archive -Path 'maven.zip' -DestinationPath 'C:\' -Force; rm maven.zip"
set MAVEN_CMD="C:\apache-maven-3.9.6\bin\mvn.cmd"

:runMaven
%MAVEN_CMD% %*
endlocal
