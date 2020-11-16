package com.paradigmas2020;

/*Autores
Giancarlo Alvarado Sánchez	- 117230466
Greivin Rojas Hernández		- 402110725
Jasson Núñez Camacho		- 117570784
Josué Víquez Campos			- 117250099
*/

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.io.File;
import java.io.FileWriter;   
import java.util.Scanner;
import java.util.Locale;
import java.nio.charset.Charset;
import javax.tools.*;
import java.io.PrintStream;
import java.io.Writer;
import java.io.PrintWriter;
import java.util.Arrays;

import java.io.IOException;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.StringBuilder;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.router.RouterNanoHTTPD;
import static fi.iki.elonen.NanoHTTPD.Response;

import static fi.iki.elonen.NanoHTTPD.MIME_PLAINTEXT;
import static fi.iki.elonen.NanoHTTPD.SOCKET_READ_TIMEOUT;

import fi.iki.elonen.router.RouterNanoHTTPD.DefaultHandler;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoClientURI;
import org.bson.Document;



public class ServiceServer extends RouterNanoHTTPD {
		
    static public class CompileHandler extends GeneralHandler {
        @Override
        public Response post(
          UriResource uriResource, Map<String, String> urlParams, IHTTPSession session) {
            try{
				final HashMap<String, String> map = new HashMap<String, String>();
				session.parseBody(map);
				final String json = map.get("postData");
				InputStream stream = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
				Compiler compiler = new Compiler();
				Response response;
				response = newFixedLengthResponse(Response.Status.OK, NanoHTTPD.MIME_HTML, compiler.compile(json));
				return response;
            }catch(Exception ex){
				return newFixedLengthResponse(Response.Status.OK, NanoHTTPD.MIME_HTML, "El codigo ingresado no corresponde a 	una clase java");
			}
        }
		
		
	}
	
	static public class ExecuterHandler extends GeneralHandler{
		@Override
        public Response post(
          UriResource uriResource, Map<String, String> urlParams, IHTTPSession session) {
            try{
				final HashMap<String, String> map = new HashMap<String, String>();
				session.parseBody(map);
				final String json = map.get("postData");
				InputStream stream = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
				Compiler compiler = new Compiler();
				Response response;
				response = newFixedLengthResponse(Response.Status.OK, NanoHTTPD.MIME_HTML, compiler.run(json));
				return response;
            }catch(Exception ex){
            return newFixedLengthResponse(Response.Status.OK, NanoHTTPD.MIME_HTML, "Se ha producido un error en el servidor");
			}
        }
    }
    
    static public class TranspileCodeHandler extends GeneralHandler{
    @Override
    public Response post(
    UriResource uriResource, Map<String, String> urlParams, IHTTPSession session) {
        try{
            final HashMap<String, String> map = new HashMap<String, String>();
            session.parseBody(map);
            final String json = map.get("postData");
            InputStream stream = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
            Compiler compiler = new Compiler();
            Response response;
            response = newFixedLengthResponse(Response.Status.OK, NanoHTTPD.MIME_HTML, compiler.readTranspileFile(json));
            return response;
        }catch(Exception ex){
        return newFixedLengthResponse(Response.Status.OK, NanoHTTPD.MIME_HTML, "Se ha producido un error en el servidor");
        }
    }
}

	
 
	static public class AuthorsHandler extends DefaultHandler {
        
        @Override
        public String getText() {
			MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb+srv://nanouser:nanouser@cluster0.hiuli.azure.mongodb.net/<dbname>?retryWrites=true&w=majority"));
			MongoDatabase database = mongoClient.getDatabase("E-nano");
			MongoCollection<Document> collection = database.getCollection("Autores");
			MongoCursor<Document> cursor = collection.find().iterator();		
			String s="";
			try {
				s=cursor.next().toJson();
			}catch(Exception ex){}
			cursor.close();
			return s;
			/*
			ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            List<String> autor = new ArrayList<>();
           // Path path = Paths.get("src/autors.txt");
            Path path = Paths.get("src/main/resources/Autors.json");
            try (Stream<String> lines = Files.lines(path)) {
               autor = lines.map(s -> s.toString()).collect(Collectors.toList());
            } catch (IOException ex) {
                System.out.format("*** error", ex);
            } 

             //autor.stream().map(Autor::toString).collect(Collectors.joining(",", "[", "]"));
             return autor.stream().reduce("", (acu, element) -> acu + element
			 */
        }
        
 
        @Override
        public String getMimeType() {
            return "application/json";
        }
     
        @Override
        public Response.IStatus getStatus() {
            return Response.Status.OK;
        }
	}
	

	
    
    public ServiceServer(int port) throws IOException {
        super(port);
        addMappings();
        start(SOCKET_READ_TIMEOUT, false);
        System.out.format("*** Router running on port %d ***%n", port);
    }
 
    @Override
    public void addMappings() {
		addRoute("/authors", AuthorsHandler.class);
		addRoute("/compile", CompileHandler.class);
        addRoute("/run",ExecuterHandler.class);
        addRoute("/transpileCode",TranspileCodeHandler.class);
    }

    private final List<String> ALLOWED_SITES = Arrays.asList("same-site", "same-origin");
    @Override
    public Response serve(IHTTPSession session){
        Map<String,String> request_headers = session.getHeaders();
        System.out.format("** ServerRouter:server request header= %s ***%n", request_headers);

        String origin = "none";
        boolean cors_allowed = request_headers != null && 
        "cors".equals(request_headers.get("sec-fetch-mode")) &&
        ALLOWED_SITES.indexOf(request_headers.get("sec-fetch-site")) >= 0
        && (origin = request_headers.get("origin")) != null;

        Response response = super.serve(session);

        if(cors_allowed){
            response.addHeader("Access-Control-Allow-Origin", origin);
        }

        return response;
    }
	
	public static int getPort(){
		try{
			FileInputStream file = new FileInputStream("src/main/resources/ports.properties");
			Properties prop = new Properties();
			prop.load(file);
			return Integer.valueOf(prop.getProperty("servicePort"));
		}catch(Exception ex){return 9000;}
	}
    
    public static void main(String[] args ) throws IOException {
        new ServiceServer(getPort());	
    }
}