@echo off
REM Autores
REM Giancarlo Alvarado Sánchez	- 117230466
REM Greivin Rojas Hernández		- 402110725
REM Jasson Núñez Camacho		- 117570784
REM Josué Víquez Campos			- 117250099
cd enano
cmd /c mvn compile
start mvn exec:java -Dexec.mainClass="com.paradigmas2020.ServerRouter"
start mvn exec:java -Dexec.mainClass="com.paradigmas2020.ServiceServer"
start swipl prolog\transpileServer.pl