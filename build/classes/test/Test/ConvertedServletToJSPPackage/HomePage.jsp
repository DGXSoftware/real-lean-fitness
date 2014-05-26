<!--
GOAL: Act as the main Homepage. Also allows for account Login and Registration.
-->

<%-- JSP Imports --%>
<%@ page import = "java.io.PrintWriter" %>
<%@ page import = "java.io.IOException" %>
<%@ page import = "javax.servlet.ServletConfig" %>
<%@ page import = "javax.servlet.ServletException" %>
<%@ page import = "javax.servlet.http.Cookie" %>
<%@ page import = "javax.servlet.http.HttpServlet" %>
<%@ page import = "javax.servlet.http.HttpServletRequest" %>
<%@ page import = "javax.servlet.http.HttpServletResponse" %>
<%@ page import = "javax.servlet.http.HttpSession" %>
<%@ page import = "dgx.software.com.UtilityPackage.UserSessionValidator" %>

<%
	// Verify that the user has Cookies enabled before allowing them to proceed
// DISABLED; Throws a "Cookies Disabled" Message wrongfully on first user attempt.
// BrowserValidator.Test4Cookies(request, response);

// Handle the User Session (Create or Follow up on existing Sessions)
UserSessionValidator.HandleUserSession(request, response);
%>

		<?xml version = '1.0'?>
		<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'>
		
		<!-- The xmlns attribute is required in XHTML but it is invalid in HTML. -->
		<!-- the namespace 'xmlns=http://www.w3.org/1999/xhtml' is default, and will be added to the <html> tag in XHTML even if you do not include it. -->
		<html xmlns='http://www.w3.org/1999/xhtml'>
		
		<head>
		
		<!-- Set the Title for the Website Page -->
		<title>RLF - Homepage</title>
		
		<!-- Set the Favicon for the Website page -->
		<link rel='Shortcut Icon' type='image/ico' href='/Images/favicon.ico'/>
		
		<!-- Set the Character Encoding for the Website page -->
		<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1' />
		
		<!-- Include the Stylesheet Files -->
		<link rel='stylesheet' type='text/css' href='/CSS/RLFStyle.css' />
		
		<!-- Include the JavaScript Files -->
		<script language='javascript' type='text/javascript' src='/JavaScript/Validation/Login Page Validation.js' > </script>
		<script language='javascript' type='text/javascript' src='/JavaScript/Validation/Registration Page Validation.js' > </script>
		<script language='javascript' type='text/javascript' src='/JavaScript/Drop-Down Menu Population.js' > </script>
		
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
		<h1>Registration</h1>
		
		<p align='left'>&#160;</p>
		
		
		 <!-- Create the Registration Form  -->
		<form action='/RegistrationServlet' method='get' id='RegistrationForm' name='RegistrationForm' onsubmit='return validateRegistrationFormInput()'>
		 
		<!-- Username VARCHAR(32) NOT NULL -->
		<p> Username: </p> <input type='text' id='RegistrationUsername' name='RegistrationUsername' title='Username' size='32' />
		<br />
		 
		<!-- Password VARCHAR(32) -->
		<p> Password: </p> <input type='password' id='RegistrationPassword' name='RegistrationPassword' title='Password' size='32' />
		<br />
		 
		<!-- EMail VARCHAR(64) -->
		<p> E-Mail: </p> <input type='text' id='RegistrationEMail' name='RegistrationEMail' title='E-Mail' size='48' />
		<br />
		 
		<p> Confirm E-Mail: </p> <input type='text' id='RegistrationEMailConfirmation' name='RegistrationEMailConfirmation' title='Confirm E-Mail' size='48' />
		<br />
		<br />
		 
		<!-- Gender VARCHAR(32) -->
		<!-- Insert mutually exclusive Radio Buttons -->
		<p> Gender: </p>
		<input type='radio' name='RegistrationGender' value='Female' />Female<br />
		<input type='radio' name='RegistrationGender' value='Male' />Male<br />
		 
		<br />
		 
		<!-- Date_Of_Birth DATE -->
		<p> Date of Birth: </p>
		 
		                <!-- Create the RegistrationBirthDay, RegistrationBirthMonth, and RegistrationBirthYear Drop-Down Menu -->
		                <SELECT id ='RegistrationBirthDay' name = 'RegistrationBirthDay'></SELECT> <b id='RegistrationBirthDivisor'>/</b>
		                <SELECT id ='RegistrationBirthMonth' name = 'RegistrationBirthMonth'></SELECT> <b id='RegistrationBirthDivisor'>/</b>
		                <SELECT id ='RegistrationBirthYear' name = 'RegistrationBirthYear'></SELECT>
		 
		                <!-- Call the (populateDateMenu) to populate the RegistrationBirthDay, RegistrationBirthMonth, and RegistrationBirthYear Drop-Down Menu-->
		                <script type='text/javascript'>populateDateMenu('RegistrationBirthDay', 'RegistrationBirthMonth', 'RegistrationBirthYear');</script> 
		 
		<br />
		<br />
		<br />
		 
		<input type='submit' id='RegistrationButton' name='RegistrationButton' value = 'Register' />
		 
		</form>
		 
		
		</div>
		
		</div>
		
		<div id='right'>
		
		<h1>Login</h1>
		
		<p align='left'>&#160;</p>
		
		<!-- Create the Login Form  -->
		<form action='/LoginServlet' method='get' id='LoginForm' name='LoginForm' onsubmit='return validateLoginFormInput()'>
		 
		<!-- Retrieve the Username -->
		<p> Username: </p> <input type='text' id='LoginUsername' name='LoginUsername' size='32' />
		<script type='text/javascript'>
		// Set the initial focus on the LoginUsername Element
		document.getElementById('LoginUsername').focus();
		</script>
		 
		<br />
		 
		<!-- Retrieve the Password -->
		<p> Password: </p> <input type='password' id='LoginPassword' name='LoginPassword' size='32' />
		 
		<br />
		 
		<!-- Retrieve the Remember Me Choice -->
		<input type='checkbox' id='LoginRememberMe' name='LoginRememberMe' /> <b> Keep me logged in: </b>
		
		<!-- Redirects the user to the Account Retrieval Page -->
		<a href='#' style='margin-left:5px;'>Can't access your account ?</a>
		 
		<br />
		<br />
		 
		 
		<input type='submit' id='LoginButton' name='LoginButton' value = 'Login' />
		 
		</form>
		
		
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
		<span>Copyright © 2012</span>
		</div>
		</div>
		</body>
		</html>

