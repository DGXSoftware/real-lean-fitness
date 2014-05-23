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
					<h1>Contact Us</h1>

	<p align="left">&#160;</p>
					

<div id="input">

 <!-- Create the Contact me form -->
<form action="/JSP/Mail/SendEMail.jsp" method="post" id="MyContactMeForm" name="MyContactMeForm" onsubmit="return validateContactMeFormInput()">

 <!-- Set the E-Mail Recipient -->
<input type="hidden" id="Recipient" name="Recipient" value="RealLeanFitness@GMail.com" />

<b>We would love to hear from you!&nbsp; Fill out the form below and we'll get back to you as soon possible.</b>

<br />
<br />
<br />

 <!-- Get the user's First Name -->
<span style="font-weight: bold;">First Name*</span>
<br />
<input type="text" id="SenderFirstName" name="SenderFirstName" size="30" />
<br />
<br />

 <!-- Get the user's Last Name -->
<span style="font-weight: bold;">Last Name</span>
<br />
<input type="text" id="SenderLastName" name="SenderLastName" size="30" />
<br />
<br />
 
 <!-- Get the user's E-Mail Address -->
<span style="font-weight: bold;">Your E-Mail Address*</span>
<br />
<input type="text" id="SenderEMail" name="SenderEMail" size="30" />
<br />
<br />

 <!-- Get the user's Subject -->
<span style="font-weight: bold;">Subject*</span>
<br />
<input type="text" id="SenderSubject" name="SenderSubject" size="30" />
<br />
<br />

 <!-- Get the user's Message --> 
<span style="font-weight: bold;">Message*</span>
<br /> 
<textarea id="SenderMessage" name="SenderMessage" rows="10" cols="50"></textarea>

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
		<li><a href='#'></a></li>
		<li><a href='#'></a></li>
		<li><a href='#'></a></li>
		<li><a href='#'></a></li>
		<li><a href='#'></a></li>
		<li><a href='#'></a></li>
		</ul>
		<span>Copyright © 2014</span>
	</div>
</div>
</body>
</html>
