/*Autores
Giancarlo Alvarado Sánchez	- 117230466
Greivin Rojas Hernández		- 402110725
Jasson Núñez Camacho		- 117570784
Josué Víquez Campos			- 117250099
*/

import {getAutors} from "./autors.js"; 
import {doSendCode, sendCode, clsConsola, confirmaClsClase, confirmaClsConsola, clsClase, clsEvaluacion,enableSend,evalEvent, run} from "./sendCode.js"; 

let editor;
let send;
	window.onload = function () {
		editor = CodeMirror.fromTextArea($("#clase")[0], {
			lineNumbers: true,
			lineWrapping: true,
		});
		editor.setSize(660,475);
		
		document.getElementById("noRemplazarBtn").addEventListener("click", EnableSend);
		document.getElementById("remplazarArchivoBtn").addEventListener("click", DoSendCode);
		document.getElementById("clickme").addEventListener("click", getAutors);
		document.getElementById("sendButton").addEventListener("click", SendCode);
		document.getElementById("clsConsolaBoton").addEventListener("click", clsConsola);
		document.getElementById("confirmaClsClaseBoton").addEventListener("click", ConfirmaClsClase);
		document.getElementById("confirmaClsConsolaBoton").addEventListener("click", confirmaClsConsola);
		document.getElementById("clsClaseBoton").addEventListener("click", ClsClase);
		document.getElementById("confirmaClsEvalBoton").addEventListener("click", clsEvaluacion);
		document.getElementById("evaluacion").addEventListener("keydown", evalEvent);
	};
	
	function EnableSend(){
		enableSend();
	}
	
	function DoSendCode(){
		doSendCode(editor);
	}

	function SendCode() {
		sendCode(editor);
	}

	function ConfirmaClsClase() {
		confirmaClsClase(editor);
	}

	function ClsClase() {
		clsClase(editor);
	}
	
	function ClsEvaluacion(){
		clsEvaluacion();
	}

    
	
   

   