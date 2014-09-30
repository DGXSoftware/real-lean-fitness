<!--
GOAL: Simulates how a Successful/Cancellation Paypal Payment transaction would react 
and respond back to test how this project would react.
-->

<!DOCTYPE html>

<html>

<head>
		
<!-- Set the Title for the Website Page -->
<title>PayPal Registration Submit</title>
		
<!-- Set the Favicon for the Website page -->
<link rel='Shortcut Icon' type='image/ico' href='/Images/favicon.ico'/>
		
<!-- Set the Character Encoding for the Website page -->
<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1' />
		
<!-- Include the Stylesheet Files -->
<link rel='stylesheet' type='text/css' href='/CSS/RLFStyle.css' />
		
<!-- Include the jQuery Files -->
<script type='text/javascript' src="/JavaScript/JQuery/jquery.js"></script>
<!--
EXTERNAL jQuery Import
<script type = "text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
-->

</head>

<body>

<center>
<form id="SuccessForm" action="<%= request.getParameter("return") %>" method="post">
<input type="submit" value="Submit Success">
</form>

<br/><br/><br/>

<form id="Cancel" action="<%= request.getParameter("cancel_return") %>" method="post">
<input type="submit" value="Submit Cancel">
</form>
</center>

</body>

</html>