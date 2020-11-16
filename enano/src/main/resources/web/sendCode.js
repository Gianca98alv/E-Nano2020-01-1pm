/*Autores
Giancarlo Alvarado Sánchez	- 117230466
Greivin Rojas Hernández		- 402110725
Jasson Núñez Camacho		- 117570784
Josué Víquez Campos			- 117250099
*/

function getName(){
	let input;
	if($("#fileName").val()==""){
		input = "File";
	}else{
		input = $("#fileName").val();
	}
	let nombre = input.match("[A-za-z1-9]+");
	return nombre[0];
}


function sendCode(editor){
	if(editor.getValue()!=""){
		disableSend();
		checkName(getName(),editor);
	}else{
        $("#empty").modal("show");
	}
}

async function checkName(name,editor){
	
	try{
        let response = await fetch('http://localhost:8000/checkForFile',
        {method:"POST",
        headers:{
        'Accept': '*/*',
        'Sec-Fetch-Site': 'same-site',
        'Access-Control-Request-Headers':'Content-Type'
        },
        body:name});
		doCheck(editor,new String(await response.text()));
    }catch(error){
        console.log(error);
        alert("No se ha podido conectar con el servidor de servicios");
	}
}

function doCheck(editor,string){
	if(string.length<9){
		confirmarRemplazo();
	}else
		doSendCode(editor);
}

async function doSendCode(editor){
     
         try{
             let response = await fetch('http://localhost:8000/newFile',
             {method:"POST",
                 headers:{
                 'Accept': '*/*',
                 'Sec-Fetch-Site': 'same-site',
                 'Access-Control-Request-Headers':'Content-Type'
             },
             body:editor.getValue()});
			 renameFile(await response);
         }catch(error){
             console.log(error);
             alert("No se ha podido conectar con el servidor de servicios");
         }

}

async function renameFile(r){
	try{
        let response = await fetch('http://localhost:8000/renameFile',
        {method:"POST",
        headers:{
		'Accept': '*/*',
        'Sec-Fetch-Site': 'same-site',
        'Access-Control-Request-Headers':'Content-Type'
        },
        body:getName()});
		printOutput(await response.text());
    }catch(error){
        console.log(error);
        alert("No se ha podido conectar con el servidor de servicios");
    }
	enableSend();
}

function enableSend(){
	$("#fileName").prop( "disabled", false );
    $("#sendButton").prop('disabled',false);
}

function disableSend(){
	$("#fileName").prop( "disabled", true );
    $("#sendButton").prop('disabled',true);
}


function printOutput(log){
	let vec=log.split(',');
	console.log(log);
    $("#consola").val(log);
}


function clsConsola(){
    $("#consola").val("");
}

function clsClase(editor){
    editor.setValue("");
}

function clsEvaluacion(){
    $("#evaluacion").val("");
}

function confirmaClsClase(editor){
    if(editor.getValue()!=""){
        $("#clsClase").modal("show");
    }else
        $("#empty").modal("show");
}

function confirmaClsConsola(){
    if($("#consola").val()!=""){
        $("#clsConsola").modal("show");
    }else
        $("#empty").modal("show");
}

function confirmarRemplazo(){
	$("#remplazarArchivo").modal("show");
}

async function run(name){
     
    try{
        let response = await fetch('http://localhost:8000/run',
        {method:"POST",
            headers:{
            'Accept': '*/*',
            'Sec-Fetch-Site': 'same-site',
            'Access-Control-Request-Headers':'Content-Type'
        },
        body:name});
		writeOutput(await response.text());
		}catch(error){
			console.log(error);
			alert("No se ha podido conectar con el servidor de servicios");
		}

}

function writeOutput(text){
	text = text.substr(1,text.length-2);
	let results = text.split("$$");
	results.forEach(e=>addResult(e));
}

function addResult(e){
	if(e!=""){
		let val = $("#evaluacion").val();
		$("#evaluacion").val(val+e+'\n');
	}
}

function evalEvent(e){
	let content = $("#evaluacion").val();
	let key = e.charCode || e.keyCode || 0;
	if(key === 13 ){
		let lastLine = content.substr(content.lastIndexOf("\n")+1);
		if(lastLine=="cls"){
			clsEvaluacion();
		}else if (lastLine.match("[A-za-z1-9]+(.main[(][)]){1}")){
			run(lastLine.substr(0,lastLine.length-7));
		}
	}
}











export {doSendCode,sendCode, clsConsola, confirmaClsClase, confirmaClsConsola, clsClase,clsEvaluacion, enableSend,evalEvent,run}