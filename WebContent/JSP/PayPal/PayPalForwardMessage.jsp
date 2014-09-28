
		<?xml version = '1.0'?>
		<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'>
		
		<!-- The xmlns attribute is required in XHTML but it is invalid in HTML. -->
		<!-- the namespace 'xmlns=http://www.w3.org/1999/xhtml' is default, and will be added to the <html> tag in XHTML even if you do not include it. -->
		<html xmlns='http://www.w3.org/1999/xhtml'>
		
		<head>
		
		<!-- Set the Title for the Website Page -->
		<title>Redirect</title>
		
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
		
		<style>
		#online_title { font-size: 20px; color: green; text-align:center; }
		#offline_title { font-size: 20px; color: red; text-align:center; }
		#BackButton { font-size: 20px; color: red; text-align:center; }
		</style>
		
		</head>

<body>
<div id="container">
	<div id="main">
		<div id="header"></div>

		<div id="content">

				<div class="post">
					<p align="left"></p>

<p>&#160;</p>
<p>&#160;</p>
<p>&#160;</p>
<%

//Get the Current CurrentRegistrationUsername
String MessageToDisplay = "";
boolean SkipCountdown = false;

// If the Query String Variable CancelMessage exists, get the value
if (request.getParameter("CancelMessage") != null){
	MessageToDisplay = request.getParameter("CancelMessage");
	
	// Only display this extra message if a CancelMessage was provided in the query string
	if(!MessageToDisplay.equals("")){
		%>
		<div id="offline_title"><%= MessageToDisplay %></div>
		<%
	}
}

//If the Query String Variable SuccessMessage exists, get the value
if (request.getParameter("SuccessMessage") != null){
	MessageToDisplay = request.getParameter("SuccessMessage");
	
	// Only display this extra message if a CancelMessage was provided in the query string
	if(!MessageToDisplay.equals("")){
		%>
		<div id="online_title"><%= MessageToDisplay %></div>
		<%
	}
}

//If the Query String Variable CancelCountdown exists, Skip the countdown
if (request.getParameter("SkipCountdown") != null){

	// Only display this extra message if a CancelMessage was provided in the query string
	if(!request.getParameter("SkipCountdown").equals("")){

	    // Skip the Countdown
		SkipCountdown = true;
   }
}

%>
<p>&#160;</p>

<%

// Skip the countdown only if it was set to true
if(SkipCountdown == false){
%>
<!-- START Countdown Redirect -->
<style>
#CountdownMessage {font-size:20pt; color:green; font-family:Calibri; text-align:center;}
#CountdownSeconds {font-size:25pt; color:Red; font-family:Calibri; text-align:center;}
#FastForwardButton {font-size:12pt; black; font-family:Calibri; text-align:center;}
</style>

<div id="CountdownMessage">You will be redirected in <span id="CountdownSeconds">5</span> seconds.</div>
<script>
// Redirect to Homepage
$(document).ready(function () {
    window.setInterval(function () {
        var iTimeRemaining = $("#CountdownSeconds").html();
        iTimeRemaining = eval(iTimeRemaining);
        if (iTimeRemaining == 0) {
            location.href = "/";
        } else {
            $("#CountdownSeconds").html(iTimeRemaining - 1);
        }
    }, 1000);
});

</script>

<br/>

<!-- Fast Forward Button -->
<div id="FastForwardButton">
<input type="button" onclick="location.href ='/'" value="Please Click here if you do not wish to wait any longer." />
</div>
<!-- END Countdown Redirect -->
<%
}
%>

<p>&#160;</p>
<p>&#160;</p>
<p>&#160;</p>
			
				</div>

			<div class="clear"></div>
		</div>
	</div>
	<div id="footer"><span>Copyright © 2014</span></div>
</div>
</body>
</html>