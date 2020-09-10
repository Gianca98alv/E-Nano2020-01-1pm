@echo off
set PORT=%1
if "%PORT%"=="" set PORT=9000
set WEB_DIR=web
echo Using port=%PORT%
python -m http.server %PORT% --directory %WEB%