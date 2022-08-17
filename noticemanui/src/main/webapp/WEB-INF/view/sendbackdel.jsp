
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" class=" js flexbox canvas canvastext webgl no-touch geolocation postmessage no-websqldatabase indexeddb hashchange history draganddrop websockets rgba hsla multiplebgs backgroundsize borderimage borderradius boxshadow textshadow opacity cssanimations csscolumns cssgradients no-cssreflections csstransforms csstransforms3d csstransitions fontface video audio localstorage sessionstorage webworkers applicationcache svg inlinesvg smil svgclippaths">
<head>
<title>Enviar correo electrónico certificado</title>
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta name="Author" content="European Agency of Digital Trust">
<meta name="Application-name" content="Noticeman">
<meta name="Copyright" content="© 2011 EAD Trust S.L.">
<meta name="Keywords" content="Notificación, electrónica, certificada, correo, notificaciones, gestor, electrónico, certificado, timestamp, sello, tiempo" lang="es">
<meta name="Keywords" content="timestamp, notice, electronic, signature" lang="en-us">
<meta name="Description" content="Plataforma de envío y gestión de notificaciones electrónicas certificadas" lang="es">
<meta name="Description" content="Electronic mail platform for management and sending" lang="en-us">

<link rel="stylesheet" href="css/client.css" type="text/css" media="all">

<link rel="stylesheet" href="css/alertbox.css" type="text/css" media="all">
<link rel="stylesheet" href="css/footer.css" type="text/css" media="all">
<link rel="stylesheet" href="css/send.css" type="text/css" media="all">
<link rel="stylesheet" href="css/notice.css" type="text/css" media="all">
<link rel="stylesheet" href="css/anytime.5.1.2.css" />

<script src="css/ga.js" async="" type="text/javascript"></script>
<script src="css/jquery-1.js"></script>
<script src="css/modernizr-1.js"></script> <!-- http://www.modernizr.com/ -->
<script src="css/jquery.js"></script>
<script src="css/send.js"></script>
<script src="css/anytime.5.1.2.js"></script>
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-25081654-1']);
  _gaq.push(['_setDomainName', 'none']);
  _gaq.push(['_setAllowLinker', true]);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>
  <script type="text/javascript">
	function changeutf8(number){
		doc = document.getElementById("idfileutf"+number);
		doc2 = document.getElementById("idfile"+number);
		doc.value = doc2.value;
	}
  </script>
  <script>
  AnyTime.picker( "field1",
		    { format: "%W, %M %D in the Year %z %E", firstDOW: 1 } );
		  $("#field2").AnyTime_picker(
		    { format: "%H:%i", labelTitle: "Hora",
		      labelHour: "Hora", labelMinute: "Minuto" } );
  </script>
<link href="css/ui.css" rel="stylesheet">
	<script type="text/javascript" src="js/tiny_mce/tiny_mce.js" ></script>
	<script type="text/javascript" >
		tinyMCE.init({
	        mode : "textareas",
	        theme : "advanced",
	        skin : "default",
	        plugins : "paste",
	        theme_advanced_toolbar_location : "top",
	        theme_advanced_buttons1 : "formatselect,bold,italic,underline,link,unlink,separator,justifyleft,justifycenter,justifyright,justifyfull,separator,bullist,numlist,separator,undo,redo",
	        theme_advanced_buttons2 : "",
	        theme_advanced_buttons3 : "",
	        theme_advanced_blockformats : "p,h1,h2,h3,h4,h5,h6",
	        formats :{
				 underline : {inline : 'u', exact : true}
	        }


		});
	</script> 


</head>


  <body>
  <article>
  
	<noscript><div class="error">Atención, no podrá enviar notificaciones si no habilita JavaScript.</div></noscript>
	<h1>Formulario de envio</h1>
	<section>
	<form id="rec_form" action="" method="post">
		
	
		<h3>Añadir Destinatario</h3> 

	
		<p class="name" style="float:left; width: 160px; margin:  0 10px 0 0;">
			<input class="" style="width: 140px;" name="name" id="name" type="text">
			<label for="name">Nombre completo</label>
		</p>
	
		<p class="email" style="float:left; width: 210px; margin: 0;">
			<input class="" name="email" id="email" type="text">
			<label for="email">E-mail</label>
		</p>
		<select class="recipient" style="float:left; width: 55px; height: 33px" id="recipient" name="recipient">
			<option value="0" selected="selected">to</option>
			<option value="1">cc</option>
			<option value="2">bcc</option>
		</select>
		<input class="submit" id="add" value="Añadir" type="submit">
		
		<div style="clear:both"></div>
		<h3>Lista de destinatarios</h3>
		<h4>To:</h4>
		<p id="showto"></p>
		<h4>Cc:</h4>
		<p id="showcc"></p>
		<h4>Bcc:</h4>
		<p id="showbcc"></p>
		<div style="clear:both"></div>
			</form>	
	
	<form action="otramas" method="post" accept-charset="utf-8" name="formtst" id="form" enctype="multipart/form-data">
			<div id="sender">
		<h3>Remitente</h3>
		</div>
		<p class="mailsender" style="float:left; width: 210px; margin:  0 10px 0 0;">
			<input class="" style="width: 140px;" name="mailsender" id="mailsender" type="text">
			<label for="mailsender">E-mail</label>
		</p>
		<br /><br /></br>		
		<h3>Notificación</h3>
		
		<input name="receivers" id="receivers" type="hidden">
		
		<p class="subject">
			<input name="subject" id="subject" class="subject" type="text">
			<label for="subject">Asunto</label>
					</p>
	
		<p class="text">
			<textarea name="content" cols="40" rows="10" ></textarea></p>
	    <h3>Adjuntos</h3>
		<p class="attach" id="attachments">
			<input type="button" onclick=addFileBrowser() value="Añadir adjunto" style="width: 110px;fontsize: big">			
		</p>
		
	<p class="reply">
			<select name="response" id="response">
				<option value="normal" selected="selected">No permitir respuesta</option>
				<option value="longresponse">Permitir respuesta desarrollada</option>
				<option value="acceptornot">Acepto/No acepto</option>
				<option value="receivedagree">Recibido, no conforme</option>
			</select>
		<input type="text"  name = "scheduled" id="scheduled" value="" /><br/>
		<p class="submit" style="height:40px">
			<input style="float: right;" value="Enviar" onmouseup="tinyMCE.triggerSave();" type="submit">
		</p>
	</form>	
	</section>	
	
	
	
	
	
	
</article>
	<aside>
	<h4>Datos del destinatario</h4>
	
	<p>Rellene los datos del destinatario y seleccione el recipiente asociado en el desplegable (Destinatario (To), En copia (Cc) y En copia oculta (Bcc)). Cuanta más información nos de, mayor capacidad probatoria tendrá la notificación.</p>
	</aside>
	<div id="rsvErrors"></div>
	<aside>
	<h4>Asunto y contenido de la notificación</h4>

	<p>El asunto debería describir adecuadamente el contenido: intente no utilizar simplemente "notificación".</p>
	
	<p>En el cuerpo del mensaje puede utilizar etiquetas HTML. Revise cuidadosamente el cuerpo del mensaje. Una vez lo envíe <strong>NO PODRÁ CAMBIARLO</strong>.</p>
	</aside>
		<aside>
	<h4>Adjuntos</h4>

	<p>Puede añadir varios adjuntos como complemento a la notificación. Pulse en el botón Añadir adjunto cada vez que desee agregar uno.</p>
	</aside>
		<aside>
	<h4>Tipos de respuesta</h4>

	<p>Puede elegir que el destinatario de la notificación le responda, eligiendo entre varios tipos de respuesta:</p>
	
	<ul>
		<li>Respuesta desarrollada: deja un espacio libre para que el receptor responda libremente.</li>
		<li>Acepto/no acepto: ofrece dos botones que envían una respuesta positiva o negativa comodamente.</li>
		<li>Recibido, no conforme: el receptor puede declarar la no conformidad con el contenido de la notificación.</li>
	</ul>
	
	</aside>
	<div style="width: 236px;" id="rsvErrors2"></div>
	
  </body>
</html>