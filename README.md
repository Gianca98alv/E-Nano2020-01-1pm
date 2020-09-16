# E-Nano2020-01-1pm
Proyecto del grupo 01-1pm para el curso de Paradigmas de la Programación. NRC: 50059.  
Integrantes: 
Giancarlo Alvarado Sánchez 		-- ID: 117230466 
José Ricardo Herrera Solano 	-- ID: 402400213
Josué Víquez Campos 			-- ID: 117250099
Greivin Rojas Hernández 		-- ID: 402110725 
Jasson Núñez Camacho 			-- ID: 117570784

Pasos a seguir para probarlo:

1. Levantar server de contenido estático (por defecto en puerto 9090).
  1.1. En cmd, posicionarse dentro de la carpeta llamada "Server Estatico".
  1.2. Ejecutar el siguiente comando para el build:   build.bat src\ServerRouter.java
  1.3. Ejecutar el siguiente comando para el run:     run com.enano.StaticServer.ServerRouter
  
2. Levantar server de servicios (autores)(por defecto en puerto 9000).
  1.1. En cmd, posicionarse dentro de la carpeta llamada "Server de Servicios".
  1.2. Ejecutar el siguiente comando para el build:   build.bat src\ServiceServer.java
  1.3. Ejecutar el siguiente comando para el run:     run com.enano.ServiceServer.ServiceServer
  
3. Visitar la siguiente dirección en el navegador:    http://localhost:9090/
   Siendo 9090 el puerto del server de contenido estático
