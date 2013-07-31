@echo off
CLS

ECHO Installing proprietary dependencies
PAUSE

mvn -e install:install-file -Dfile=./lib/ojdbc6.jar -DgroupId=com.oracle -DartifactId=ojdbc6 -Dversion=6.0 -Dpackaging=jar && ECHO - complete! && PAUSE