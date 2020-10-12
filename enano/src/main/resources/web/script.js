/*Autores
Giancarlo Alvarado Sánchez	- 117230466
Greivin Rojas Hernández		- 402110725
Jasson Núñez Camacho		- 117570784
Josué Víquez Campos			- 117250099
*/
let editor;
	window.onload = function () {
		editor = CodeMirror.fromTextArea($("#clase")[0], {
			lineNumbers: true,
			lineWrapping: true,
		});
		editor.setSize(660,450);
	};

	async function getAutors(){
	try
	{
		let response = await fetch('http://localhost:9000/authors');
		printAutors(await response.json());
	}
	catch(err){ 
		console.log(err);
		alert("No se ha podido conectar con el servidor de servicios");
	}
   }

   function printAutors({project, course, instance, cycle, team:{members}}){
	$("#panelAutors").empty();
	$("#panelAutors").append("<div><strong>" + project + "</strong></div>");
	$("#panelAutors").append("<div><strong>" + course + " Paradigmas de programacion</strong></div>");
	$("#panelAutors").append("<div><strong>Grupo 01-1pm " + instance + " " + cycle + "</strong></div>");
	$("#panelAutors").append("<div><strong>Integrantes:</strong></div>");

     members.forEach(element => printAutor(element));
	 $('#about').modal('show');
   }

   
   function printAutor({firstName, surnames, id}){
    $("#panelAutors").append(
      $("<div class='row'><div class=' col-md-10 customTR punteado'>" + firstName + " " +  surnames + " " + id + "</div>")
    );
   }
   
    async function sendCode(){
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
   
   function clsClase(){
	   editor.setValue("");
   }
   
   function confirmaClsClase(){
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
   
   