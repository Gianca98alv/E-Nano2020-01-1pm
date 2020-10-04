(function($) {
	$.fn.linedtextarea = function(options) {
		var opts = $.extend({}, $.fn.linedtextarea.defaults, options);
		var fillOutLines = function(codeLines, h, lineNo){
			while ( (codeLines.height() - h ) <= 0 ){
				if ( lineNo == opts.selectedLine )
					codeLines.append("<div class='lineno lineselect'>" + lineNo + "</div>");
				else
					codeLines.append("<div class='lineno'>" + lineNo + "</div>");
				
				lineNo++;
			}
			return lineNo;
		};
		return this.each(function() {
			var lineNo = 1;
			var textarea = $(this);
			
			textarea.attr("wrap", "off");
			textarea.css({resize:'none'});
			var originalTextAreaWidth	= textarea.outerWidth();

			
			textarea.wrap("<div class='linedtextarea'></div>");
			var linedTextAreaDiv	= textarea.parent().wrap("<div class='linedwrap' style='width:" + originalTextAreaWidth + "px'></div>");
			var linedWrapDiv 			= linedTextAreaDiv.parent();
			
			linedWrapDiv.prepend("<div class='lines' style='width:50px'></div>");
			
			var linesDiv	= linedWrapDiv.find(".lines");
			linesDiv.height( textarea.height() + 6 );
			
			
		
			linesDiv.append( "<div class='codelines'></div>" );
			var codeLinesDiv	= linesDiv.find(".codelines");
			lineNo = fillOutLines( codeLinesDiv, linesDiv.height(), 1 );

			if ( opts.selectedLine != -1 && !isNaN(opts.selectedLine) ){
				var fontSize = parseInt( textarea.height() / (lineNo-2) );
				var position = parseInt( fontSize * opts.selectedLine ) - (textarea.height()/2);
				textarea[0].scrollTop = position;
			}

	
			var sidebarWidth					= linesDiv.outerWidth();
			var paddingHorizontal 		= parseInt( linedWrapDiv.css("border-left-width") ) + parseInt( linedWrapDiv.css("border-right-width") ) + parseInt( linedWrapDiv.css("padding-left") ) + parseInt( linedWrapDiv.css("padding-right") );
			var linedWrapDivNewWidth 	= originalTextAreaWidth - paddingHorizontal;
			var textareaNewWidth			= originalTextAreaWidth - sidebarWidth - paddingHorizontal - 20;

			textarea.width( textareaNewWidth );
			linedWrapDiv.width( linedWrapDivNewWidth );
			
			textarea.scroll( function(tn){
				var domTextArea		= $(this)[0];
				var scrollTop 		= domTextArea.scrollTop;
				var clientHeight 	= domTextArea.clientHeight;
				codeLinesDiv.css( {'margin-top': (-1*scrollTop) + "px"} );
				lineNo = fillOutLines( codeLinesDiv, scrollTop + clientHeight, lineNo );
			});

			textarea.resize( function(tn){
				var domTextArea	= $(this)[0];
				linesDiv.height( domTextArea.clientHeight + 6 );
			});

		});
	};

  $.fn.linedtextarea.defaults = {
  	selectedLine: -1,
  	selectedClass: 'lineselect'
  };
})(jQuery);
   
   /*function getAutors(){
    fetch('http://localhost:9000/authors')
    .then( response => response.json())
    .then(json => printAutors(json))
      .catch(err => console.log(err));
   }*/

   async function getAutors()
   {
	try
	{
		let response = await fetch('http://localhost:9000/authors');
		printAutors(await response.json());
	}
	catch(err)
	{ console.log(err)
	}
   }

   function printAutors({project, course, instance, cycle, team:{members}}){
	$("#panelAutors").empty();
	$("#panelAutors").append("<div><strong>" + project + "</strong></div>");
	$("#panelAutors").append("<div><strong>" + course + " Paradigmas de programacion</strong></div>");
	$("#panelAutors").append("<div><strong>Grupo 01-1pm " + instance + " " + cycle + "</strong></div>");
	$("#panelAutors").append("<div><strong>Integrantes:</strong></div>");

     members.forEach(element => printAutor(element));
   }

   
   function printAutor({firstName, surnames, id}){
    $("#panelAutors").append(
      $("<div class='row'><div class=' col-md-10 customTR punteado'>" + firstName + " " +  surnames + " " + id + "</div>")
    );
   }