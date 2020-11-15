@echo off
REM Autores
REM Giancarlo Alvarado Sánchez	- 117230466
REM Greivin Rojas Hernández		- 402110725
REM Jasson Núñez Camacho		- 117570784
REM Josué Víquez Campos			- 117250099
cd enano
cmd /c gradle
start gradle runServiceServer
start gradle runStaticServer
start swipl prolog\transpileServer.pl
