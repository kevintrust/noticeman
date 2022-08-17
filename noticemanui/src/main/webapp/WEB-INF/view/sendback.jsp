<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" class="no-js">
<head>
<title>Enviar correo electrónico certificado</title>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta name="Author" content="European Agency of Digital Trust">
<meta name="Application-name" content="Noticeman">
<meta name="Copyright" content="&copy; 2011 EAD Trust S.L.">
<meta name="Keywords" lang="es" content="Notificación, electrónica, certificada, correo, notificaciones, gestor, electrónico, certificado, timestamp, sello, tiempo" >
<meta name="Keywords" lang="en-us" content="timestamp, notice, electronic, signature" >
<meta name="Description" lang="es" content="Plataforma de envío y gestión de notificaciones electrónicas certificadas" >
<meta name="Description" lang="en-us" content="Electronic mail platform for management and sending" >

<link rel="stylesheet" href="css/client.css" type="text/css" media="all">

<link rel="stylesheet" href="css/alertbox.css" type="text/css" media="all">
<link rel="stylesheet" href="css/footer.css" type="text/css" media="all">
<link rel="stylesheet" href="css/send.css" type="text/css" media="all">
<link rel="stylesheet" href="css/notice.css" type="text/css" media="all">
<link rel="stylesheet" href="css/anytime.5.1.2.css" />

<script src="js/jquery-1.6.3.js"></script>
<script src="js/modernizr-1.7.min.js"></script> <!-- http://www.modernizr.com/ -->
<script  src="js/jquery.rsv.js"></script>
<script  src="js/send.js"></script>
<script src="js/anytime.5.1.2.js"></script>


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
<!-- aqui va el header -->
<%@include file="header.jsp" %>
<div class="separator" style="clear:both;"></div>
<!--  aqui va el submenu -->
<%@include file="submenu.jsp" %>


<article>
	<noscript><div class="error">Atención, no podrá enviar notificaciones si no habilita JavaScript.</div></noscript>
	<h1>Formulario de envio</h1>
	<section>
	<form id="rec_form" action="" method="post">
		<div id="counter">
			<p class="aux">Ud. dispone de <strong><span id="balance">266</span> notificaciones.</strong></p>
			<p id="count">1</p>
			<p class="aux">notificaciones serán consumidas en este envío.</p>
		</div>
		
		<div id="sender">
		<h3>Remitente</h3>
		
		<p>Nombre: Said</p>
		
		<p>Apellidos: Boubnane </p>
		
		<p>E-mail: said.boubnane@eadtrust.net</p>
		</div>
		
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
	
	<aside>
	<h4>Datos del remitente</h4>
	
	<p>Los datos del remitente se toman automaticamente del servidor.</p>

	<p>Comprueba que los datos que tenemos de tí son correctos. Recuerda que cuanta más información nos des, mejor.</p>
	
	</aside>
	
	<div id="rsvErrors"></div>
	
	<aside>
	<h4>Datos del destinatario</h4>
	
	<p>Rellene los datos del destinatario. Cuanta más información nos des, mayor capacidad probatoria tendrá la notificación.</p>
	</aside>
	
	<aside>
	<h4>Fecha</h4>
	
	<p>Este valor de fecha es orientativo. Cuando envíe la notificación, la sellaremos con la fecha real del envío sincronizada con el Real Observatorio de la Armada.</p>
	</aside>
	
	<div id="rsvErrors2"></div>
	
	<aside>
	<h4>Asunto y contenido de la notificación</h4>

	<p>El asunto debería describir adecuadamente el contenido: intente no utilizar simplemente "notificación".</p>
	
	<p>En el cuerpo del mensaje puede utilizar etiquetas HTML. Revise cuidadosamente el cuerpo del mensaje. Una vez lo envíe <strong>NO PODRÁ CAMBIARLO</strong>.</p>
	</aside>
	
	<aside>
	<h4>Adjuntos</h4>

	<p>Puede añadir un adjunto (en PDF) como complemento a la notificación. Son válidos sólo archivos en PDF.</p>
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
</article>

<div style="clear:both"></div><div id="separator" style="clear:both;"></div>

<!-- Aqui va el footer -->
<%@include file="footer.jsp" %>
</body>
</html>