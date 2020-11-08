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

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static fi.iki.elonen.NanoHTTPD.MIME_PLAINTEXT;
import static fi.iki.elonen.NanoHTTPD.SOCKET_READ_TIMEOUT;

import fi.iki.elonen.router.RouterNanoHTTPD.DefaultHandler;

/*Autores
Giancarlo Alvarado Sánchez	- 117230466
Greivin Rojas Hernández		- 402110725
Jasson Núñez Camacho		- 117570784
Josué Víquez Campos			- 117250099
*/

public class ServerRouter extends RouterNanoHTTPD {
  
    static public class HomeHandler extends DefaultHandler {
        @Override
        public String getText() {
			StringBuilder data=new StringBuilder();
            Path path = Paths.get("src/main/resources/web/index.html");
            try (Stream<String> lines = Files.lines(path)) {
                lines.map(s -> s.toString()).forEach(l -> data.append(l));

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
               
            Path path = Paths.get("src/main/resources/web/styles.css");
            try (Stream<String> lines = Files.lines(path)) {
                lines.map(s -> s.toString()).forEach(l -> data.append(l));

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
                          
            Path path = Paths.get("src/main/resources/web/script.js");
            try (Stream<String> lines = Files.lines(path)) {
                lines.map(s -> s.toString()).forEach(l -> data.append(l));

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

    static public class AutorsModuleHandler extends DefaultHandler {
		@Override
        public String getText() {
			StringBuilder data=new StringBuilder();
                          
            Path path = Paths.get("src/main/resources/web/autors.js");
            try (Stream<String> lines = Files.lines(path)) {
                lines.map(s -> s.toString()).forEach(l -> data.append(l));

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

    static public class SendCodeModuleHandler extends DefaultHandler {
		@Override
        public String getText() {
			StringBuilder data=new StringBuilder();
                          
            Path path = Paths.get("src/main/resources/web/sendCode.js");
            try (Stream<String> lines = Files.lines(path)) {
                lines.map(s -> s.toString()).forEach(l -> data.append(l));

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
        addRoute("/script.js", LindedTextHandler.class);
        addRoute("/autors.js", AutorsModuleHandler.class);
        addRoute("/sendCode.js", SendCodeModuleHandler.class);
        
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