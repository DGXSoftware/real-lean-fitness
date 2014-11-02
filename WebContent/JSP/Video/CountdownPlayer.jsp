<!--
GOAL: Temporarily displays a custom Good or Bad Message to the user.
Then after a few seconds it will forward their request to the Homepage.
-->

<%-- JSP Imports --%>
<%@ page import = "dgx.software.com.UtilityPackage.GlobalTools" %>

		<?xml version = '1.0'?>
		<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'>
		
		<!-- The xmlns attribute is required in XHTML but it is invalid in HTML. -->
		<!-- the namespace 'xmlns=http://www.w3.org/1999/xhtml' is default, and will be added to the <html> tag in XHTML even if you do not include it. -->
		<html xmlns='http://www.w3.org/1999/xhtml'>
		
		<head>
		
		<!-- Set the Title for the Website Page -->
		<title>Countdown Player</title>
		
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
		#success_title { font-size: 20px; color: green; text-align:center; }
		#cancel_title { font-size: 20px; color: red; text-align:center; }
		#BackButton { font-size: 20px; color: red; text-align:center; }
		</style>
		
		</head>

<body>
<div id="container">
	<div id="main">
		<div id="header"></div>
		<div id="nav">
			<ul>
			    <li><a href="<%= GlobalTools.GTV_Homepage %>">Home</a></li>
				<li><a href="#"></a></li>
				<li><a href="#"></a></li>
				<li><a href="#"></a></li>
				<li><a href="#"></a></li>
				<li><a href="#"></a></li>
			</ul>
		</div>
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
		<div id="cancel_title"><%= MessageToDisplay %></div>
		<%
	}
}

//If the Query String Variable SuccessMessage exists, get the value
if (request.getParameter("SuccessMessage") != null){
	MessageToDisplay = request.getParameter("SuccessMessage");
	
	// Only display this extra message if a CancelMessage was provided in the query string
	if(!MessageToDisplay.equals("")){
		%>
		<div id="success_title"><%= MessageToDisplay %></div>
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

#PlayerHeader {font-size:20pt; color:blue; font-family:Calibri; text-align:center;}

#CountdownMessage {font-size:20pt; color:blue; font-family:Calibri; text-align:center;}
#CountdownSeconds {font-size:25pt; color:Red; font-family:Calibri; text-align:center;}
#FastForwardButton {font-size:12pt; colo:black; font-family:Calibri; text-align:center;}
#StatusMessage {font-size:20pt; color:green; font-family:Calibri; text-align:center;}

#MyImageDiv {font-size:20pt; color:blue; font-family:Calibri; text-align:center; cursor: pointer; }
#MyButtonDiv {font-size:25pt; color:Red; font-family:Calibri; text-align:center;}

</style>

<div id="PlayerHeader">
<b id="StatusMessage">DEFAULT STATUS MESSAGE</b>
<b id="CountdownMessage">DEFAULT COUNTDOWN MESSAGE</b>
<span id="CountdownSeconds">1018</span>
</div>

<br/>
<br/>

<script>
//document.getElementById("CountdownMessage").innerHTML = "AAAAAAAA";
</script>

<div id="MyImageDiv">
<img id="MyImage" name="MyImage" onClick="ToggleCountdown();" src="" height="300" width="300" /> 
</div>

<script>

// TO DO
// 1. Pause Button - DONE
// How to handle refreshes

var ImageNameArray = [
"Bulbasaur" ,
"Ivysaur" ,
"Venusaur" ,
"Charmander" ,
"Charmeleon" ,
"Charizard" ,
"Squirtle" ,
"Wartortle" ,
"Blastoise" ,
"Pikachu" ,
"Raichu"
];

var ImageURLArray = [
"http://static.giantbomb.com/uploads/scale_small/13/135472/1891758-001bulbasaur.png" ,
"http://static.giantbomb.com/uploads/scale_small/13/135472/1891759-002ivysaur.png" ,
"http://static.giantbomb.com/uploads/scale_small/13/135472/1891760-003venusaur.png" ,
"http://static.giantbomb.com/uploads/scale_small/0/6087/2438704-1202149925_t.png" ,
"http://static.giantbomb.com/uploads/scale_small/13/135472/1891762-005charmeleon.png" ,
"http://static.giantbomb.com/uploads/scale_small/13/135472/1891763-006charizard.png" ,
"http://static.giantbomb.com/uploads/scale_small/13/135472/1891764-007squirtle.png" ,
"http://static.giantbomb.com/uploads/scale_small/13/135472/1891809-008wartortle.png" ,
"http://static.giantbomb.com/uploads/scale_small/13/135472/1891810-009blastoise.png" ,
"http://static.giantbomb.com/uploads/scale_small/0/6087/2437349-pikachu.png" ,
"http://static.giantbomb.com/uploads/scale_small/13/135472/1898248-026raichu.png"
];

var ImageTimeArray = [
5,10,5,10,5,10,5,10,5,10,1000
];

var ImageIndex = 0;

// Set Initial Values
document.getElementById("StatusMessage").innerHTML = "RUNNING: ";
document.getElementById("CountdownMessage").innerHTML = ImageNameArray[ImageIndex]  + " ";
document.getElementById("CountdownSeconds").innerHTML = ImageTimeArray[ImageIndex];
document.getElementById("MyImage").src = ImageURLArray[ImageIndex];

var IsRunning = true;

// Redirect to Homepage
$(document).ready(function () {
    window.setInterval(function () {
        var iTimeRemaining = $("#CountdownSeconds").html();
        iTimeRemaining = eval(iTimeRemaining);
        if (iTimeRemaining == 0) {
        	
        	// Update Values
        	ImageIndex += 1;
        	document.getElementById("CountdownMessage").innerHTML = ImageNameArray[ImageIndex] + " ";
        	document.getElementById("CountdownSeconds").innerHTML = ImageTimeArray[ImageIndex];
        	document.getElementById("MyImage").src = ImageURLArray[ImageIndex];
        	
        } else {
        	if(IsRunning === true){
            $("#CountdownSeconds").html(iTimeRemaining - 1);
        	}
        }
    }, 1000);
});

function ToggleCountdown(){
	 
	if(IsRunning === true){
		IsRunning = false;
		document.getElementById("MyButton").src = "http://www.clipartbest.com/cliparts/yco/e9a/ycoe9abMi.png";
		document.getElementById("StatusMessage").innerHTML = "STOPPED: ";
		document.getElementById("StatusMessage").style.color = "red";
		return false;
	}
	
	if(IsRunning === false){
		IsRunning = true;
		document.getElementById("MyButton").src = "http://theologygaming.com/wp-content/uploads/2014/08/Pause.png";
		document.getElementById("StatusMessage").innerHTML = "RUNNING: ";
		document.getElementById("StatusMessage").style.color = "green";
		return false;
	}
	
}


</script>

<br/>

<div id="MyButtonDiv">
<input type="image" src="http://theologygaming.com/wp-content/uploads/2014/08/Pause.png" 
name="MyButton" id="MyButton" value="Toggle Countdown" onClick="ToggleCountdown();" 
width="48" height="48"/>
</div>

<br/>

<!-- Fast Forward Button -->
<!--
<div id="FastForwardButton">
<input type="button" onclick="location.href ='<%= request.getParameter("RedirectURL") == null ? GlobalTools.GTV_Homepage : request.getParameter("RedirectURL") %>'" value="Please Click here if you do not wish to wait any longer." />
</div>
-->
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