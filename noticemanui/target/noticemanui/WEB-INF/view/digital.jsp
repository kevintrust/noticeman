<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>jQuery Keypad</title>
<link href="js/js/jquery.keypad.css" rel="stylesheet">
<style>
body > iframe { display: none; }
#inlineKeypad { width: 10em; }
</style>
<script src="js/jquery-1.7.0.js"></script>
<script src="js/modernizr-1.7.min.js"></script> <!-- http://www.modernizr.com/ -->

<script src="js/js/jquery.plugin.js"></script>
<script src="js/js/jquery.keypad.js"></script>
<script src="js/js/jquery.keypad-es.js"></script>
<script>
$(function () {
	$('#defaultKeypad').keypad($.keypad.regionalOptions['es']);
	$('#inlineKeypad').keypad({onClose: function() {
		alert($(this).val());
	}});
});
</script>
</head>
<body>
<h1>jQuery Keypad Basics</h1>
<p>This page demonstrates the very basics of the
	<a href="http://keith-wood.name/keypad.html">jQuery Keypad plugin</a>.
	It contains the minimum requirements for using the plugin and
	can be used as the basis for your own experimentation.</p>
<p>For more detail see the <a href="http://keith-wood.name/keypadRef.html">documentation reference</a> page.</p>
<p><input type="text" id="defaultKeypad"></p>
<div id="inlineKeypad"></div>
<script>
var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
</script>
<script>
var pageTracker = _gat._getTracker("UA-4715900-1");
pageTracker._initData();
pageTracker._trackPageview();
</script>
</body>
</html>
