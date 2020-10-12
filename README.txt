# E-Nano2020-01-1pm
Proyecto del grupo 01-1pm para el curso de Paradigmas de la Programación. NRC: 50059.  
Integrantes: 
Giancarlo Alvarado Sánchez	- 117230466
Greivin Rojas Hernández		- 402110725
Jasson Núñez Camacho		- 117570784
Josué Víquez Campos		- 117250099

Link de github: https://github.com/Gianca98alv/E-Nano2020-01-1pm.git (Repositorio privado)

Todos los comando se corren en cmd estando dentro del directorio del proyecto (enano)

=========================================
Para compilar
=========================================

mvn compile

=========================================
Para correr server de servicios
=========================================

mvn exec:java -Dexec.mainClass="com.paradigmas2020.ServiceServer"
Por default corre en el puerto 9000

=========================================
Para correr server de contenido estático
=========================================

mvn exec:java -Dexec.mainClass="com.paradigmas2020.ServerRouter"
Por default corre en el puerto 9090

=========================================
Ruta del .properties con los números de puerto
=========================================

E-Nano2020-01-1pm\enano\src\main\resources\ports.properties

=========================================
URIs de acceso
=========================================

Página principal
http://localhost:9090/
http://localhost:9090/index





