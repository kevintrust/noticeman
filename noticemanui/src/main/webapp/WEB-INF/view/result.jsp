<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE HTML>
<html>
<head>
<title>Enviar correo electrónico certificado</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <meta http-equiv="Cache-Control"  content="no-cache, no-store, must-revalidate">
 <meta http-equiv="Pragma"  content ="no-cache">
 <meta http-equiv="Expires"  content="0">;
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

<script src="js/jquery-1.7.0.js"></script>
<script src="js/modernizr-1.7.min.js"></script> <!-- http://www.modernizr.com/ -->
<script  src="js/jquery.rsv.js"></script>
<script  src="js/send.js"></script>
<script src="js/anytime.5.1.2.js"></script>
<script src="js/multifile.js"></script>
<script src="js/js/jquery.plugin.js"></script>
<script src="js/js/jquery.keypad.js"></script>
<script src="js/js/jquery.keypad-es.js"></script>
<script src="js/js/jquery.keypad-en.js"></script>
<script src="js/js/jquery.keypad-fr.js"></script>
<link href="js/js/jquery.keypad.css" rel="stylesheet">
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
	        theme_advanced_buttons1 : "formatselect,fontsizeselect,bold,italic,underline,link,unlink,separator,justifyleft,justifycenter,justifyright,justifyfull,separator,bullist,numlist,separator,undo,redo",
	        theme_advanced_buttons2 : "",
	        theme_advanced_buttons3 : "",
	        theme_advanced_blockformats : "p,h1,h2,h3,h4,h5,h6",
	        fontsize_formats: "8pt 10pt 12pt 14pt 18pt 24pt 36pt",
	        formats :{
				 underline : {inline : 'u', exact : true}
	        }


		});
	</script> 

</head>
<head>
<body>
<!-- aqui va el header -->
<%@include file="header.jsp" %>
<div class="separator" style="clear:both;"></div>
<!--  aqui va el submenu -->
<%@include file="submenu.jsp" %>
${theModelKey}
<div style="clear:both"></div><div id="separator" style="clear:both;"></div>
<spring:message code="contact" />
<!-- Aqui va el footer -->
<%@include file="footer.jsp" %>
</body>
</html>