<!--
GOAL: Sample Template of Most Pages
-->

<!-- START HTML RESPONSE -->
<!-- $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ -->

		<?xml version = '1.0'?>
		<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'>
		<html xmlns='http://www.w3.org/1999/xhtml'>
		
		<head>
		
		<!-- Set the Title for the Website Page -->
		<title>PageTemplate]</title>
		
		<!-- Set the Favicon for the Website page -->
		<link rel='Shortcut Icon' type='image/ico' href='/Images/favicon.ico'/>
		
		<!-- Set the Character Encoding for the Website page -->
		<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1' />
		
		<!-- Include the Stylesheet Files -->
		<link rel='stylesheet' type='text/css' href='/CSS/RLFStyle.css' />
		
		<!-- Include the JavaScript Files -->
		<script language='javascript' type='text/javascript' src='/JavaScript/Validation/UserInformationPageValidation.js' > </script>
		
		</head>
		
		<body>

		<div id='container'>
		<div id='main'>
		<div id='header'></div>
		<div id='nav'>
		<ul>
		<li><a href='#'></a></li>
		<li><a href='#'></a></li>
		<li><a href='#'></a></li>
		<li><a href='#'></a></li>
		<li><a href='#'></a></li>
		<li><a href='#'></a></li>
		</ul>
		</div>
		<div id='content'>
		<div id='left'>
		<div class='post'>
		<h1>Edit Settings</h1>
		
		<p align='left'>&#160;</p>
		
		<form action='' method='post' id='UserInformationForm' name='UserInformationForm'>
		 
		<p><b>LEFT SIDE</b></p>
		
		<!-- First_Name VARCHAR(32) -->
		<label for="RegistrationFirstName">
		<p> First Name: </p>
        <input type="text" id="RegistrationFirstName" name="RegistrationFirstName" onKeyUp="isValidNameField('RegistrationFirstName','RegistrationFirstNameIcon','',false);" 
        title='Registration First Name' size='32' maxlength='32' />
        <img id="RegistrationFirstNameIcon" src="/Images/Icons/Valid/Valid(16x16).png" style="visibility:hidden;" />
        </label>
		
		<!-- Login Button -->
        <input type='button' id='LoginButton' name='LoginButton' value='Login' onClick="submitLoginRequest();" /> 
		
		</form>
		
		</div>
		
		</div>
		
		
		<div id='right'>
		<h1 class='Center_Text'>Profile Picture</h1>
		<p><b>RIGHT SIDE</b></p>
		
        <!-- Last_Name VARCHAR(32) -->
		<label for="RegistrationLastName">
		<p> Last Name: </p>
        <input type="text" id="RegistrationLastName" name="RegistrationLastName" onKeyUp="isValidNameField('RegistrationLastName','RegistrationLastNameIcon','',false);" 
        title='Registration Last Name' size='32' maxlength='32' />
        <img id="RegistrationLastNameIcon" src="/Images/Icons/Valid/Valid(16x16).png" style="visibility:hidden;" />
        </label>
		
		</div>
		
		
		<div class='clear'></div>
		</div>
		</div>
		
		<div id='footer'>
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


<!-- $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ -->
<!-- END HTML RESPONSE -->
