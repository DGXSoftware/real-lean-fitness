<?xml version = "1.0" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	
	<!-- Set the Title for the Website Page -->
	<title>Contact Us</title>
		
    <!-- Set the Favicon for the Website page -->
	<link rel='Shortcut Icon' type='image/ico' href='/Images/favicon.ico'/>
		
	<!-- Set the Character Encoding for the Website page -->
	<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1' />
		
	<!-- Include the Stylesheet Files -->
	<link rel='stylesheet' type='text/css' href='/CSS/Mail/ContactUs.css' />
	
	<!-- Include the Contact Me JavaScript File -->
	<script type='text/javascript' src='/JavaScript/Mail/ContactUs.js' > </script>
		
</head>

<body onLoad="getRandomCaptcha();">
<div id="container">
	<div id="main">
		<div id="header"></div>
		<div id="nav">
			<ul>
				<li><a href="#"></a></li>
				<li><a href="#"></a></li>
				<li><a href="#"></a></li>
				<li><a href="#"></a></li>
				<li><a href="#"></a></li>
				<li><a href="/">Home</a></li>
			</ul>
		</div>
		<div id="content">
			<div id="left">
				<div class="post">
					<h1>Contact Me</h1>

	<p align="left">&#160;</p>
					

<div id="input">

 <!-- Create the Contact me form -->
<form action="/JSP/Mail/SendEMail.jsp" method="post" id="MyContactMeForm" name="MyContactMeForm" onsubmit="return validateContactMeFormInput()">

 <!-- Set the E-Mail Recipient -->
<input type="hidden" id="Recipient" name="Recipient" value="RealLeanFitness@GMail.com" />

<b>I would love to hear from you!&nbsp; Fill out the form and I'll get back to you as soon as I can.</b>

<br />
<br />
<br />

 <!-- Get the user's Name -->
<span style="font-weight: bold;">Your Name</span>
<br />
<input type="text" id="SenderName" name="SenderName" size="30" />
<br />
<br />
 
 <!-- Get the user's E-Mail Address -->
<span style="font-weight: bold;">Your E-Mail Address</span>
<br />
<input type="text" id="SenderEMail" name="SenderEMail" size="30" />
<br />
<br />

 <!-- Get the user's Phone Number -->
<span style="font-weight: bold;">Phone Number (optional)</span>
<br />
<input type ="text" id="SenderPhoneNumber" name="SenderPhoneNumber" size="30" />
<br />
<br />

 <!-- Get the user's Availability --> 
<span style="font-weight: bold;">Best time to call you ?</span>
<br />
<input type="text" id="SenderAvailability" name="SenderAvailability" size="30" />
<br />
<br />

 <!-- Get the user's Request --> 
<span style="font-weight: bold;">How can I help ?</span>
<br /> 
<TEXTAREA id="SenderMessage" name="SenderMessage" rows="10" cols="50"></TEXTAREA>
 
<br />
<br />
<br />

 <!-- Get the user's File Attachment -->
<span style="font-weight: bold;">Attachment (optional)</span>
<br />
<input type="file" id="SenderFileAttachment" name="SenderFileAttachment" size="30" />
  <!-- Set the E-Mail SenderFileAttachment -->
<!-- <input type="hidden" id="SenderFileAttachment" name="SenderFileAttachment" value="C:/Images/image.jpg" /> -->

<br />
<br />
<br />

 <!-- Warn the user about the case sensitivity of the Captcha -->
<span style="font-weight: bold;">Case sensitive Captcha</span>
<br />
<br />

 <!-- Display the Captcha Code -->
<table id="CaptchaTable" name="CaptchaTable" >
<tr>
<td style = "background-color: black;" >
<b id="CaptchaDisplay" name="CaptchaDisplay" value="" style = "color: cyan;"></b>
</td>
</tr>
</table>

<!-- This Button Re-Calculates the Captcha -->
<input type="button" value="Refresh Captcha" onClick="getRandomCaptcha();">


<br />
<br />



 <!-- Get the user's Captcha Input -->
<span style="font-weight: bold;">Enter the Captcha Code</span>
<br />
<input type ="text" id="CaptchaCodeUserInput" name="CaptchaCodeUserInput" size="10" />

<br />
<br />
<br />
<br />
 
 <CENTER><input type = "submit" id="MyContactMeSubmitButton" name="MyContactMeSubmitButton" value = "Submit Form" /></CENTER>
 
 </FORM>
 
 
 
 </div>
					
					
				</div>
				<div class="post">
				<h1></h1>
				</div>
			</div>
			<div class="clear"></div>
		</div>
	</div>
	<div id="footer">
		<ul>
			<li><a href="index.jsp">Home</a></li> | 
			<li><a href="About Me.jsp">About Me</a></li> | 
			<li><a href="Resume.jsp">Resume</a></li> | 
			<li><a href="Applications.jsp">Applications</a></li> | 
			<li><a href="Contact Me.jsp">Contact Me</a></li> | 
			<li><a href="Links.jsp">Links</a></li>
		</ul>
		<span>Copyright © 2011</span>
	</div>
</div>
</body>
</html>
