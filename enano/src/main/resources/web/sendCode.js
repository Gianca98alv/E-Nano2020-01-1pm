/*Autores
Giancarlo Alvarado Sánchez	- 117230466
Greivin Rojas Hernández		- 402110725
Jasson Núñez Camacho		- 117570784
Josué Víquez Campos			- 117250099
*/
function checkTerm(){
	let a = $("#fileName").val();
	if(a.slice(-1)=='.'){
		$("#fileName").val(a+"no");
	}else if(a.slice(-2)=='.n'){
		$("#fileName").val(a+"o");
	}else if(a.slice(-3)!='.no'){
		$("#fileName").val(a+".no");
	}
}

function getName(){
	checkTerm();
	let input;
	if($("#fileName").val()==""){
		input = "File.no";
	}else{
		input = $("#fileName").val();
	}
	let nameRegex = /[a-zA-Z]+.no/g;
	let nombre = input.match(nameRegex);
	return nombre[0];
}


function sendCode(editor){
	/*TODO verificar que termine en .no*/
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
             printOutput(await response.text());
         }catch(error){
             console.log(error);
             alert("No se ha podido conectar con el servidor de servicios");
         }

}

async function renameFile(){
	try{
        let response = await fetch('http://localhost:8000/renameFile',
        {method:"POST",
        headers:{
		'Accept': '*/*',
        'Sec-Fetch-Site': 'same-site',
        'Access-Control-Request-Headers':'Content-Type'
        },
        body:getName()});
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
    $("#consola").val(log);
	renameFile();
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

async function run(){
     
    try{
        let response = await fetch('http://localhost:8000/run',
        {method:"POST",
            headers:{
            'Accept': '*/*',
            'Sec-Fetch-Site': 'same-site',
            'Access-Control-Request-Headers':'Content-Type'
        },
        body:"Main"});
			$("#evaluacion").val(await response.text());
		}catch(error){
			console.log(error);
			alert("No se ha podido conectar con el servidor de servicios");
		}

}

export {doSendCode,sendCode, clsConsola, confirmaClsClase, confirmaClsConsola, clsClase,clsEvaluacion, enableSend, run}