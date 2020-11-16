/*Autores
Giancarlo Alvarado Sánchez	- 117230466
Greivin Rojas Hernández		- 402110725
Jasson Núñez Camacho		- 117570784
Josué Víquez Campos			- 117250099
*/

package com.paradigmas2020;

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

class Compiler {

	public String compile(String name){
		File clase = new File("clases\\"+name+".java");
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		DiagnosticCollector< JavaFileObject > diagsCollector = new DiagnosticCollector<>();
		Locale locale = null;
		Charset charset = null;
		String outdir = "clases";
		String optionsString = String.format("-d %s", outdir);
		try {
			var fileManager = compiler.getStandardFileManager( diagsCollector, locale, charset );
			var sources = fileManager.getJavaFileObjectsFromFiles(Arrays.asList( clase ) );
	 
			Writer writer = new PrintWriter(System.err);
		
			Iterable<String> options = Arrays.asList(optionsString.split(" "));
			Iterable<String> annotations = null;
			var compileTask = compiler.getTask( writer, 
												fileManager, 
												diagsCollector, 
												options, 
												annotations, 
												sources );
			compileTask.call();
		} catch(Exception e){
			return "Se ha producido un error al intentar compilar: "+e;
		}
		String result="";
		if (diagsCollector.getDiagnostics().size() == 0){
			result= "La compilacion fue exitosa";
		}else{
		var dianostics = diagsCollector.getDiagnostics();
		result = dianostics.stream()
		.map(element -> linesFormat(element.getLineNumber(), element.getMessage( locale ), element.getSource().getName()))
		.reduce("", (acu, element) -> acu + element);
		}

	return result;
	}

	public String linesFormat(long pos , String message, String sourceName){

		String location = pos >= 0 ? String.format("Line: %d", pos) : "Unavailable:";
			return String.format("%s %s in source '%s' \n", location, message, sourceName);
	}
	
	
	public String writeCode(String code) throws Exception{
		File clase = new File("clases/newFile.java");
		clase.createNewFile();
		FileWriter writer = new FileWriter(clase);
		writer.write(code);
		writer.close();
		String name = getClassName("clases/newFile.java");
		File className = new File(name);
		className.delete();
		clase.renameTo(className);
		return name;	
	}
	
	public String getClassName(String path) throws Exception{
		File clase = new File(path);
		Scanner reader = new Scanner(clase);
		String name = reader.tokens().reduce("",(acc,line)->
			{if(line.equals("class")&&acc.equals("")){
				acc=line;
				}else if(acc.equals("class")){
					acc=line;} return acc;});
		reader.close();
		if(name.charAt(name.length()-1)=='{'){
			name = name.substring(0, name.length() - 1);
		}
		return "clases/"+name+".java";
	}

    public String run(String name){
            String text = "";
			try {

				Process proc = Runtime.getRuntime().exec("java -cp clases "  + name);
		
				InputStream errin = proc.getErrorStream();
				InputStream in = proc.getInputStream(); 
				BufferedReader errorOutput = new BufferedReader(new InputStreamReader(errin));
				BufferedReader output = new BufferedReader(new InputStreamReader(in));
				
				try {
					/*while ((line1 = errorOutput.readLine()) != null || 
						   (line2 = output.readLine()) != null) {                   
						if(line1 != null) text += line1;
						if(line2 != null) text += line2;               
					}*///end while

					text += errorOutput.lines().reduce("", (acu, element) -> acu + element);
					text += output.lines().reduce("", (acu, element) -> acu + element+"$$");
					errorOutput.close();
					output.close();
				} catch(Exception ex){}//end catc
				//result = proc.waitFor();
			} catch (IOException e) {
				System.err.println("IOException raised: " + e.getMessage());
			}

            return text;
        }
            
    }