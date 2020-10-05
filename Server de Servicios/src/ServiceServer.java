package com.enano.ServiceServer;

import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import java.io.IOException;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.BufferedReader;

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



public class ServiceServer extends RouterNanoHTTPD {
    static int PORT = 9000;
    static record Autor(String nombre){
        public String toString(){
               return String.format("{\"autor\":\"%s\"}", nombre);
        }
    }
	
		static public class CompileHandler extends GeneralHandler {
        @Override
        public Response post(
          UriResource uriResource, Map<String, String> urlParams, IHTTPSession session) {
            try{
            final HashMap<String, String> map = new HashMap<String, String>();
            session.parseBody(map);
            final String json = map.get("postData");
            InputStream stream = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
			System.out.println(json);
            Response x = newFixedLengthResponse(Response.Status.OK, NanoHTTPD.MIME_HTML, json);
			x.addHeader("Access-Control-Allow-Method", "DELETE, POST, GET, PUT");
			x.addHeader("Access-Control-Allow-Origin", "*");
			x.addHeader("Access-Control-Allow-Headers", "Content-Type, X-Requested-With");
			return x;
            }catch(Exception ex){}
            return newFixedLengthResponse(Response.Status.OK, NanoHTTPD.MIME_HTML, "Default Response");
        }
    }

 
	static public class AuthorsHandler extends DefaultHandler {
        /*List<Autor> autor = Arrays.asList(new Autor("Giancarlo Alvarado S&aacutenchez"), new Autor("Jos&eacute Ricardo Herrera Solano"), 
        new Autor("Josu&eacute V&iacutequez Campos"),new Autor("Greivin Rojas Hern&aacutendez"), new Autor("Jasson N&uacute&ntildeez Camacho"));*/
        
        @Override
        public String getText() {
            List<String> autor = new ArrayList<>();
           // Path path = Paths.get("src/autors.txt");
            Path path = Paths.get("src/Autors.json");
            try (Stream<String> lines = Files.lines(path)) {
               autor = lines.map(s -> s.toString()).collect(Collectors.toList());
            } catch (IOException ex) {
                System.out.format("*** error", ex);
            } 

             //autor.stream().map(Autor::toString).collect(Collectors.joining(",", "[", "]"));
             return autor.stream().reduce("", (acu, element) -> acu + element);
            
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
    }

    private final List<String> ALLOWED_SITES = Arrays.asList("same-site", "same-origin");
    @Override
    public Response serve(IHTTPSession session){
        var request_headers = session.getHeaders();
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
    
    public static void main(String[] args ) throws IOException {
        PORT = args.length == 0 ? 9000 : Integer.parseInt(args[0]);
        new ServiceServer(PORT);
    }
}

