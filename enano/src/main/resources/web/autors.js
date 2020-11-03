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

function printAutors({project, course, instance, cycle, members}){
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

   export {getAutors}