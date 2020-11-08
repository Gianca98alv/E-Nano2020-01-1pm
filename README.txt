# E-Nano2020-01-1pm
Proyecto del grupo 01-1pm para el curso de Paradigmas de la Programación. NRC: 50059.  
Integrantes: 
Giancarlo Alvarado Sánchez	- 117230466
Greivin Rojas Hernández		- 402110725
Jasson Núñez Camacho		- 117570784
Josué Víquez Campos		- 117250099

Link de github: https://github.com/Gianca98alv/E-Nano2020-01-1pm.git (Repositorio privado)

**********************************************************
			Servidores
**********************************************************
---Servicios		(puerto 9000)
---Estatico		(puerto 9090)
---Transpiler		(puerto 8080)

*********************************************************
			Ejecución
*********************************************************

A) --Se pueden levantar los tres de manera automática ejecutando
     el archivo .bat correspondiente a la herramienta de build deseada 
     "start(Gradle).bat" o "start(Maven).bat"

B) --Se puede levantar cada uno por aparte con a los siguientes comandos:
     (*Se debe posicionar en la carpeta "enano")
	=====================================================
				Maven
	=====================================================
	---Build
	mvn compile

	---Estático
	mvn exec:java -Dexec.mainClass="com.paradigmas2020.ServerRouter"
	
	---Servicios
	mvn exec:java -Dexec.mainClass="com.paradigmas2020.ServiceServer"
		
	======================================================
				Gradle
	======================================================
	---Build
	gradle
	
	---Estático
	gradle runStaticServer
	
	---Servicios
	gradle runServicesServer
	
	==================================================================
				Servidor de Prolog
	==================================================================
	---Posicionado en la carpeta llamada "prolog"
	swipl transpileServer.pl
	
	---Posicionado en la carpeta "enano"
	swipl prolog\transpileServer.pl
	
	
**************************************************************
	Ruta del .properties con los números de puerto
**************************************************************

E-Nano2020-01-1pm\enano\src\main\resources\ports.properties

**************************************************************
			URIs de acceso
**************************************************************

Página principal
http://localhost:9090/
http://localhost:9090/index

*************************************************************
			Base de datos
*************************************************************
--Cluster de MongoDB
--nombre de la base de datos: E-nano
--Coleccion: Autores
--link: mongodb+srv://nanouser:nanouser@cluster0.hiuli.azure.mongodb.net/test




