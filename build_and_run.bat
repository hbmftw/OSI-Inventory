@echo off
chcp 65001 >nul
cd /d "%~dp0"
setlocal enabledelayedexpansion
if not exist bin mkdir bin
if exist sources.txt del /f /q sources.txt
for /R src %%f in (*.java) do (
  set "p=%%f"
  set "p=!p:\=/!"
  echo "!p!">>sources.txt
)
"C:\Users\hbmft\AppData\Local\Programs\Eclipse Adoptium\jdk-17.0.7.7-hotspot\bin\javac.exe" -d bin -cp "src/lib/*" @sources.txt
if errorlevel 1 (
  echo Compilation failed.
  pause
  exit /b 1
)

rem Copy resource files into bin so they are available on classpath
xcopy /E /Y /I "src\resources" "bin\resources" >nul
"C:\Users\hbmft\AppData\Local\Programs\Eclipse Adoptium\jdk-17.0.7.7-hotspot\bin\java.exe" -cp "bin;src\lib\*" ui.VisualGUI
pause
