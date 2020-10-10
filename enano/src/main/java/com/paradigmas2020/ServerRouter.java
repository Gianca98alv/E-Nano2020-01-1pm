package com.paradigmas2020;

import java.io.IOException;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.FileInputStream;


import java.lang.StringBuilder;
import java.io.File;
import java.util.Scanner;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.router.RouterNanoHTTPD;
import static fi.iki.elonen.NanoHTTPD.Response;

import static fi.iki.elonen.NanoHTTPD.MIME_PLAINTEXT;
import static fi.iki.elonen.NanoHTTPD.SOCKET_READ_TIMEOUT;

import fi.iki.elonen.router.RouterNanoHTTPD.DefaultHandler;



public class ServerRouter extends RouterNanoHTTPD {
    static record Autor(String nombre){
        public String toString(){
               return String.format("{\"autor\":\"%s\"}", nombre);
        }
    }
	
	
	static public class AuthorsHandler extends DefaultHandler {
        List<Autor> autor = Arrays.asList(new Autor("Giancarlo Alvarado S&aacutenchez"), new Autor("Jos&eacute Ricardo Herrera Solano"), 
		new Autor("Josu&eacute V&iacutequez Campos"),new Autor("Greivin Rojas Hernandez"), new Autor("Jasson N&uacute&ntildeez Camacho"));
        @Override
        public String getText() {
            return autor.stream()
                       .map(Autor::toString)
                       .collect(Collectors.joining(",", "[", "]"));
            
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
  
    static public class HomeHandler extends DefaultHandler {
        @Override
        public String getText() {
			StringBuilder data=new StringBuilder();
               try {
				File myObj = new File("src/main/resources/web/index.html");
				Scanner myReader = new Scanner(myObj);
				while (myReader.hasNextLine()) {
					data.append(myReader.nextLine());
				}
				myReader.close();
				return data.toString();
			} catch (Exception e) {
				return "Error";
			}
        }
        @Override
        public String getMimeType() {
            return "text/html";
        }
        @Override
        public Response.IStatus getStatus() {
            return Response.Status.OK;
        }
    }
	
	static public class StylesHandler extends DefaultHandler {
		@Override
        public String getText() {
			StringBuilder data=new StringBuilder();
               try {
				File myObj = new File("src/main/resources/web/styles.css");
				Scanner myReader = new Scanner(myObj);
				while (myReader.hasNextLine()) {
					data.append(myReader.nextLine());
				}
				myReader.close();
				return data.toString();
			} catch (Exception e) {
				return "Error";
			}
        }
        @Override
        public String getMimeType() {
            return "text/css";
        }
        @Override
        public Response.IStatus getStatus() {
            return Response.Status.OK;
        }
    }
	
	
	static public class LindedTextHandler extends DefaultHandler {
		@Override
        public String getText() {
			StringBuilder data=new StringBuilder();
               try {
				File myObj = new File("src/main/resources/web/jquery-linedtextarea.js");
				Scanner myReader = new Scanner(myObj);
				while (myReader.hasNextLine()) {
					data.append(myReader.nextLine());
				}
				myReader.close();
				return data.toString();
			} catch (Exception e) {
				return "Error";
			}
        }
        @Override
        public String getMimeType() {
            return "text/javascript";
        }
        @Override
        public Response.IStatus getStatus() {
            return Response.Status.OK;
        }
    }
	
	
	
    
    public ServerRouter(int port) throws IOException {
        super(port);
        addMappings();
        start(SOCKET_READ_TIMEOUT, false);
        System.out.format("*** Router running on port %d ***%n", port);
    }
 
    @Override
    public void addMappings() {
        addRoute("/", HomeHandler.class);
		addRoute("/index", HomeHandler.class);
		addRoute("/styles.css", StylesHandler.class);
		addRoute("/jquery-linedtextarea.js", LindedTextHandler.class);
		addRoute("/authors", AuthorsHandler.class);
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
	
	public static int getPort(){
		try{
			FileInputStream file = new FileInputStream("src/main/resources/ports.properties");
			Properties prop = new Properties();
			prop.load(file);
			return Integer.valueOf(prop.getProperty("staticPort"));
		}catch(Exception ex){return 9090;}
	}
    
    public static void main(String[] args ) throws IOException {
        new ServerRouter(getPort());
    }
}