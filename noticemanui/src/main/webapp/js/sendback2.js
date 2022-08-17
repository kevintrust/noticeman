var targets = 0;
var balance = 0;
var mails = 0;
var files = 0;
var multi_selector;
// a custom onComplete handler to submit de form
function submitForm() {
	var receivers = '';
	//Todo check if #show está vacio, si lo está poner el puntero y mostrar error
	$('.mail').each(function(){
		receivers = receivers + $(this).html();
	});
	$('#receivers').val(receivers);
	document.forms["form"].submit();
	return false;
}

function file_or_checkbox()
{
	file = document.getElementById("file_upload");
	checkbox = document.getElementById("no_attach");
	if (checkbox.checked || (file.value.length>0))
		return true;
	else
		return [[file, "No ha adjuntado ningún archivo."]];
}

function delReceiver() {
	var form = document.forms['formtst'];
	var num = this.id.slice(4);
	var elem = document.getElementById('name'+ num);
	form.removeChild(elem);
	elem = document.getElementById('address' + num);
	form.removeChild(elem);
	elem = document.getElementById('recipient' + num);
	form.removeChild(elem);
	mails--;
	$(this).fadeOut('slow', function() {
		$(this).remove();
		targetBalance();
		//Remove tb elimina los parametros de jQuery.data
	});
}

function addReceiver() {
	//Cargamos los valores de los campos del formulario ya validado
	var name = $("#name").val();
	var email = $("#email").val();
	
	mails++;
	//Limpiamos los campos
	$("#name").val('');
	$("#email").val('');

    var form = document.forms['formtst'];
	var input =document.createElement('input');
	
	input.type = 'hidden';
	input.name = input.id = 'name' + mails;
	input.value= name;
	form.appendChild(input);
	input = document.createElement('input');
	input.type = 'hidden';
	input.name = input.id = 'address' + mails;
	input.value = email;
	form.appendChild(input);
	input = document.createElement('input');
	input.type = 'hidden';
	input.name = input.id = 'recipient'+mails;
	var recipient = document.getElementById('recipient');
	switch(recipient.value){
	case "0":
			input.value = "to";
			break;
	case "1":
			input.value = "cc";
			break;
	case "2":
			input.value = "bcc";
			break;
	}
	form.appendChild(input);
	//Creamos los botones que borran la dirección con un callback para eliminarlos
	$('<span class="mail" id="mail'+mails+'">' + name + ' <em>' + email + '</em> <em>' + input.value + '</em></span>').hide().data('elem', name + '|' + email + ';')
			.appendTo('#show'+input.value).fadeIn().click(delReceiver);
	targetBalance();
	return false;
}

function targetBalance () {
	
	targets = $("#show span").length;
	var cost = Math.ceil(targets/2);
	
	$("#count").html(cost);
}
function performClick() {
			var elem = document.getElementById('fileprov');
		    multi_selector = new MultiSelector( document.getElementById( 'files_list' ));
			multi_selector.addElement(elem); 

			if(elem && document.createEvent) {
	   			var evt = document.createEvent("MouseEvents");
	   			evt.initEvent("click", true, false);
	   			elem.dispatchEvent(evt);
	   		}
}
$("#show").ready(targetBalance);

$(".mail").live('click',delReceiver);


// check form validation
$(document).ready(
	function() {
		AnyTime.picker( "scheduled",
			    { format: "%d/%m/%Y %H:%i", firstDOW: 1, labelHour: "Hora", labelMinute: "Minuto", labelDayOfMonth: "Dia", labelMonth: "Mes", labelYear: "Año", monthAbbreviations: ['Ene','Feb','Mar','Abr','May','Jun','Jul','Ago','Sep','Oct','Nov','Dic'], 
		  dayAbbreviations: ['Dom', 'Lun', 'Mar', 'Mie', 'Jue', 'Vie', 'Sab'] , labelTitle: "Seleccione Fecha y Hora"});
		balance = $("#balance").html();
		
		$("#form").RSV(
			{
				onCompleteHandler : submitForm,
				displayType : "display-html",
				errorTargetElementId : "rsvErrors2",
				errorFieldClass : "errorField",
				errorTextIntro : "Por favor, corrija los siguientes errores antes de proseguir:",
				rules : [
						"required,subject,Introduzca un asunto para la notificación.",
						"required,content,Es necesario algo que notificar."
						//"range<=4,reply_type,Introduzca un tipo de respuesta válido."
						//"required,receivers,Añada al menos un destinatario." 
						]
			});
		$("#rec_form").RSV(
			{
				onCompleteHandler : addReceiver,
				displayType : "display-html",
				errorTargetElementId : "rsvErrors",
				errorFieldClass : "errorField",
				errorTextIntro : "Por favor, corrija los siguientes errores antes de proseguir:",
				rules : [
						"required,name,Introduzca el nombre del destinatario.",
						"required,email,Introduzca el correo del destinatario.",
						"reg_exp,email,^[a-zA-Z0-9\\+_\\-]+[\\.a-zA-Z0-9\\+_\\-\\.]*@[a-zA-Z0-9\\-][\\.a-zA-Z0-9\\-]+\\.[a-zA-Z][a-zA-Z]+$, Introduzca un correo electrónico válido."
						]
			}
		);

		$("aside").css('width', ($('article').width() - $('section').width() - 114 ) + 'px');
		$("#rsvErrors").css('width', ($('article').width() - $('section').width() - 114 ) + 'px');
		$("#rsvErrors2").css('width', ($('article').width() - $('section').width() - 114 ) + 'px');
		$(window).resize( 
				function(){
					$("aside").css('width', ($('article').width() - $('section').width() - 114 ) + 'px');
					$("#rsvErrors").css('width', ($('article').width() - $('section').width() - 114 ) + 'px');
					$("#rsvErrors2").css('width', ($('article').width() - $('section').width() - 114 ) + 'px');
				}
			);

	}
);
