var targets = 0;
var balance = 0;
var mails = 0;
var files = 0;
// a custom onComplete handler to submit de form
function submitForm() {
	document.forms["form"].submit();
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
	elem = document.getElementById('phone' + num);
	form.removeChild(elem);
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
	var phone = $("#phone").val();
	
	mails++;
	//Limpiamos los campos
	$("#name").val('');
	$("#email").val('');
	$("#phone").val('');

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
	input.name = input.id = 'phone'+mails;
	input.value = phone;
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
	$('<span class="mail" id="mail'+mails+'">' + name + ' <em>' + email + '</em> </span>').hide().data('elem', input.value)
	.appendTo('#show'+input.value).fadeIn().click(delReceiver);
	
	//$('<span class="mail" id="mail'+mails+'">' + name + ' <em>' + email + '</em></span>').hide()
	//.appendTo('#show'+input.value).fadeIn().click(delReceiver);
	targetBalance();
	return false;
}

function targetBalance () {
	
	targets = $("#showto span").length + $("#showcc span").length + $("#showbcc span").length;
	var cost = Math.ceil(targets/2);
	
	$("#count").html(cost);
}
function delFileBrowser(num){
	var attach = document.getElementById("attachments");
	var filebrowser  = document.getElementById('file'+num);
	attach.removeChild(filebrowser);
	var delbr = document.getElementById('br'+num);
	attach.removeChild(delbr);
	var delbutton = document.getElementById('removeFile'+num);
	attach.removeChild(delbutton);
	files--;
}
function addFileBrowser(){
	files++;
	var attach = document.getElementById("attachments");
	var input = document.createElement('input');
	input.name = input.id = 'file'+files;
	input.type = 'file';
	var fordel = input.id.slice(4);
	var br = document.createElement('br');
	br.id = 'br'+files;
	
	attach.appendChild(br);
	attach.appendChild(input);
	
	input = document.createElement('input');
	input.name = input.id = 'removeFile'+files;
	input.type = 'button';
	input.onclick = function(){
		delFileBrowser(fordel);
	};

	input.value = 'Borrar fichero';
	attach.appendChild(input);
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
function changeResponse(){
	var select = document.getElementById("response");
	if(select.value == "normal"){
		$("#divCheckLimitResponse").fadeOut();
		$("#divLimitResponse").fadeOut();
		$("#checkLimitResponse").prop('checked',false);
		$("#limitresponse").prop('value','');
	}
	else
		$("#divCheckLimitResponse").fadeIn();
}
function changeCheckResponse(){
	var elem = document.getElementById("checkLimitResponse");
	if(elem.checked)
		$("#divLimitResponse").fadeIn();
	else{
		$("#divLimitResponse").fadeOut();
		$("#limitresponse").prop('value','');
	}

}
function changeCheckAccept(){
	var elem = document.getElementById("checkLimitAccept");
	if(elem.checked)
		$("#divLimitAccept").fadeIn();
	else{
		$("#divLimitAccept").fadeOut();
		$("#acceptlimit").prop('value','');
	}
}

function funclimitresponse(){

	//var limitresp = document.getElementById("limitresponse");
	//var check = document.getElementById("checkLimitResponse");
	
	if($('#checkLimitResponse')[0].checked == false){
		return true;
	}
	else{
		var val = $("#limitresponse").val();
		if((val < 0) || (val > 720))
			return [[limitresponse, "El límite para las horas de respuesta debe estar comprendido entre 0 y 720 ambos inclusive."]];
		else return true;
	}

		
}
function funclimitaccept(){
	if($('#checkLimitAccept')[0].checked == false)
		return true;
	else{
		var val = $("#acceptlimit").val();
		if((val < 1)||(val>720))
			return [[acceptlimit, "El límite para las horas de aceptación total debe estar comprendido entre 1 y 720 ambos inclusive."]];
		else return true;
	}
}
function funccheckdestinations(){
	var totrayelements = 0;
	$('.mail').each(function(){
		if($(this).data('elem') === "to")
			totrayelements++;
		
	});
	var receivers = document.getElementById("receivers");
	if(totrayelements>0)
		return true;
	else 
		return [[receivers,"Añada al menos un destinatario en la bandeja To:"]]
}

function linksprocess(){
	var elem = document.getElementById("checkprocesslinks");
	var form = document.forms['formtst'];
	var linkprocact;

	linkprocact = document.getElementById("processlinks");
	if(elem.checked){
		if(!linkprocact){
			var input =document.createElement('input');	
			input.type = 'hidden';
			input.name = input.id = 'processlinks';
			form.appendChild(input);
		}	
	}
	else{
		if(linkprocact)
			form.removeChild(linkprocact);	
	}
}
function deldate(){
	var elem = document.getElementById("scheduled");
	elem.value = "";
}
$("#show").ready(targetBalance);

$(".mail").live('click',delReceiver);


// check form validation
$(document).ready(
	function() {
		var isMobile = {
			    Android: function() {
			        return navigator.userAgent.match(/Android/i);
			    },
			    BlackBerry: function() {
			        return navigator.userAgent.match(/BlackBerry/i);
			    },
			    iOS: function() {
			        return navigator.userAgent.match(/iPhone|iPad|iPod/i);
			    },
			    Opera: function() {
			        return navigator.userAgent.match(/Opera Mini/i);
			    },
			    Windows: function() {
			        return navigator.userAgent.match(/IEMobile/i);
			    },
			    any: function() {
			        return (isMobile.Android() || isMobile.BlackBerry() || isMobile.iOS() || isMobile.Opera() || isMobile.Windows());
			    }
		};
		AnyTime.picker( "scheduled",
			    { format: "%d/%m/%Y %H:%i", firstDOW: 1, labelHour: "Hora", labelMinute: "Minuto", labelDayOfMonth: "Dia", labelMonth: "Mes", labelYear: "Año", monthAbbreviations: ['Ene','Feb','Mar','Abr','May','Jun','Jul','Ago','Sep','Oct','Nov','Dic'], 
		  dayAbbreviations: ['Dom', 'Lun', 'Mar', 'Mie', 'Jue', 'Vie', 'Sab'] , labelTitle: "Seleccione Fecha y Hora"});
		balance = $("#balance").html();
		if(!isMobile.any()){
			$('#limitresponse').keypad({keypadClass: 'flatKeypad', 
				prompt: '', closeText: 'X', clearText: '«', backText: '‹-'});
			$('#acceptlimit').keypad({keypadClass: 'flatKeypad', 
				prompt: '', closeText: 'X', clearText: '«', backText: '‹-'});
		}
		$("#divCheckLimitResponse").hide();
		$("#divLimitResponse").hide();
		$("#divLimitAccept").hide();
		$("#response").val("normal");
		$("#checkLimitResponse").prop('checked',false);
		$("#checkLimitAccept").prop('checked',false);
		$("#limitresponse").prop('value','');
		$("#limitccept").prop('value','');
		$("#form").RSV(
			{
				onCompleteHandler : submitForm,
				displayType : "display-html",
				errorTargetElementId : "rsvErrors2",
				errorFieldClass : "errorField",
				errorTextIntro : "Por favor, corrija los siguientes errores antes de proseguir:",
				rules : [
						"required,subject,Introduzca un asunto para la notificación.",
						"required,content,Es necesario algo que notificar.",
						"required,mailsender,Introduzca el correo del remitente.",
						"reg_exp,mailsender,^[a-zA-Z0-9\\+_\\-]+[\\.a-zA-Z0-9\\+_\\-\\.]*@[a-zA-Z0-9\\-][\\.a-zA-Z0-9\\-]+\\.[a-zA-Z][a-zA-Z]+$, Introduzca un correo electrónico válido en el remitente.",
						"function,funclimitresponse",
						"function,funclimitaccept",
						"function,funccheckdestinations"
						//"range<=4,reply_type,Introduzca un tipo de respuesta válido."
						//"required,receivers,Añada al menos un destinatario en la bandeja to." 
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
						"required,phone,Introduzca el teléfono del destinatario.",
						"reg_exp,email,^[a-zA-Z0-9\\+_\\-]+[\\.a-zA-Z0-9\\+_\\-\\.]*@[a-zA-Z0-9\\-][\\.a-zA-Z0-9\\-]+\\.[a-zA-Z][a-zA-Z]+$, Introduzca un correo electrónico válido.",
						"reg_exp,phone,^[6-7][0-9]{8}$,Introduzca los nueve dígitos del movil sin prefijos ni otros símbolos."
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
