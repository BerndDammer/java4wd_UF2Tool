call ..\..\SetEnv.bat
rem mvn clean package -Djavafx.platform=linux-monocle
mvn clean package -Djavafx.platform=linux-arm32-monocle
rem mvn clean package -Djavafx.platform=linux-aarch64
pause -----------------------pc-----------------------
