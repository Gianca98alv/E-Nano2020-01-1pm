
async function sendCode(editor){
    $("#sendButton").prop('disabled',true);
     if(editor.getValue()!=""){
         try{
             let response = await fetch('http://localhost:9000/compile',
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
     }else{
         $("#empty").modal("show");
     }
        $("#sendButton").prop('disabled',false);

}

function printOutput(log){
    $("#consola").val(log);
}


function clsConsola(){
    $("#consola").val("");
}

function clsClase(editor){
    editor.setValue("");
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

export {sendCode, clsConsola, confirmaClsClase, confirmaClsConsola, clsClase}