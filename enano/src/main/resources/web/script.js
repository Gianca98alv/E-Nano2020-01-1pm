/*Autores
Giancarlo Alvarado Sánchez	- 117230466
Greivin Rojas Hernández		- 402110725
Jasson Núñez Camacho		- 117570784
Josué Víquez Campos			- 117250099
*/
import {getAutors} from "./autors.js"; 
import {sendCode, clsConsola, confirmaClsClase, confirmaClsConsola, clsClase} from "./sendCode.js"; 

let editor;
let send;
	window.onload = function () {
		editor = CodeMirror.fromTextArea($("#clase")[0], {
			lineNumbers: true,
			lineWrapping: true,
		});
		editor.setSize(660,450);

		
		document.getElementById("clickme").addEventListener("click", getAutors);
		document.getElementById("sendButton").addEventListener("click", SendCode);
		document.getElementById("clsConsolaBoton").addEventListener("click", clsConsola);
		document.getElementById("confirmaClsClaseBoton").addEventListener("click", ConfirmaClsClase);
		document.getElementById("confirmaClsConsolaBoton").addEventListener("click", confirmaClsConsola);
		document.getElementById("clsClaseBoton").addEventListener("click", ClsClase);
	
	};

	function SendCode() {
		sendCode(editor);
	}

	function ConfirmaClsClase() {
		confirmaClsClase(editor);
	}

	function ClsClase() {
		clsClase(editor);
	}
    
  
   

   