Todos los comando se corren en cmd estando dentro del directorio del proyecto (enano)

=========================================
Para compilar
=========================================

mvn compile

=========================================
Para correr server de servicios
=========================================

mvn exec:java -Dexec.mainClass="com.paradigmas2020.ServiceServer"

=========================================
Para correr server de contenido estático
=========================================

mvn exec:java -Dexec.mainClass="com.paradigmas2020.ServerRouter"

=========================================
Si es necesario hacer clean
=========================================

mvn clean verify

=========================================
Otras consideraciones
=========================================
Para el compile
Como en esta prueba aún se usa record, se añadió --enable-preview en los argumentos 
de los plugin maven-compiler-plugin y maven-surefire-plugin 

Para el run
El comando mvn exec: java corre con un JDK dado dentro del contexto de maven el cual por defecto no 
contiene --enable-preview
Por lo que, se añadió en el directorio raíz del proyecto la carpeta .mvn la cual contiene un archivo 
jvm.config que a su vez contiene en texto --enable-preview el cual se pasa como argumento en el run

