<% response.addHeader("Cache-Control","no-cache"); %> 

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

// Assume we don't have a Session Account ID
String SessionAccountID = "";

// Returns null if no session already exists 
HttpSession CurrentSession =  request.getSession(false);

// If we have a session attempt to retrieve the SessionAccountID
if (CurrentSession != null) {SessionAccountID = (String) CurrentSession.getAttribute("AccountID");}

// If attempts to retrieve the SessionAccountID returned null, make it an Empty String Object for operations
if (SessionAccountID == null) {SessionAccountID = "";}

// Display the main body if the user is not logged in, else forward the users to their profile page
if(SessionAccountID.equals("")){
%>

		<?xml version = '1.0'?>
		<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'>
		
		<!-- The xmlns attribute is required in XHTML but it is invalid in HTML. -->
		<!-- the namespace 'xmlns=http://www.w3.org/1999/xhtml' is default, and will be added to the <html> tag in XHTML even if you do not include it. -->
		<html xmlns='http://www.w3.org/1999/xhtml'>
		
		<head>
		
		<!-- Set the Title for the Website Page -->
		<title>Real Lean Fitness</title>
		
		<!-- Set the Favicon for the Website page -->
		<link rel='Shortcut Icon' type='image/ico' href='/Images/favicon.ico'/>
		
		<!-- Set the Character Encoding for the Website page -->
		<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1' />
		
		<!-- Include the Stylesheet Files -->
		<link rel='stylesheet' type='text/css' href='/CSS/RLFStyle.css' />
		
		<!-- Include the JavaScript Files -->
		<script type='text/javascript' src='/JavaScript/Validation/Login Page Validation.js' > </script>
		<script type='text/javascript' src='/JavaScript/Validation/Registration Page Validation.js' > </script>
		<script type='text/javascript' src='/JavaScript/Drop-Down Menu Population.js' > </script>
		
		<!-- Include the jQuery Files -->
		<script type='text/javascript' src="/JavaScript/JQuery/jquery.js"></script>
		<!--
		EXTERNAL jQuery Import
		<script type = "text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
		-->


<script>

//Handle User Submit for Registration once the user presses Enter on a Registration Field
function checkIfEnteredPressedForRegistration(event){
	
	    // If Entered was pressed Submit the Registration Form
	    if (event.which == 13 || event.keyCode == 13) {
	    	submitRegistrationRequest();
	            //code to execute here
	            return false;
	        }

}

// Submits the Registration Request
function submitRegistrationRequest() {

	// If the Registration User Input was not validated successfully, stop further processing (Do not Submit) 
	if(validateRegistrationFormInput() === false){
		return false;
	}

// Validate the User Input before Submitting. Set it so it Alerts about the specific User Input that is invalid. 
// IF this method returns false, stop further execution and don't submit.
//if (validateUserInput("MyFormFeedback") === false){ return false; }


        var jqxhr = $.ajax({
            type:       "POST",
            url:        "/RegistrationServlet",
            cache:      false,
            data:       $("form").serialize(),
                
            // Before load, notify the user that the request
            // may take awhile 
            beforeSend: function() {

            // DO NOTHING FOR NOW
            
                        },
                            
            // If user remains on page for the results,
            // show alert with results
            success:    function(data, status) {
            	
            // Redirect to the appropriate page upon successful Registration
            //alert("Account created successfully!");
            window.location = "/JSP/PayPal/PayPalRegistrationSubmit.jsp" + "?" + "RegistrationUsername=" + document.getElementById("RegistrationUsername").value;

                     },
                     
            // If there is an error and the user hasn't yet closed the
            // browser, display the message. Otherwise it will come in
            // the email
            error:      function(xhr, textStatus, thrownError) {

            // Use AJAX to Inject the Error HTML to the appropriate DIV (DETAILED VERSION)
            // $('#RegistrationFormFeedbackDiv').html("<div id='ErrorResults'><font color='red' size='+1'><b>" + thrownError + " - </b> Error " + xhr.status + ":" + xhr.responseText + "</font></div>");

           // Use AJAX to Inject the Error HTML to the appropriate DIV (SHORT VERSION)
           $('#RegistrationFormFeedbackDiv').html("<div id='ErrorResults'><font color='red' size='1'><b>" + xhr.responseText + "</b></font></div>");

            // Stop further processing
            return false;
                        }
                });

    }


</script>


<script>

//Handle User Submit for Login once the user presses Enter on a Login Field
function checkIfEnteredPressedForLogin(event){
	
	    // If Entered was pressed Submit the Login Form
	    if (event.which == 13 || event.keyCode == 13) {
	    	submitLoginRequest();
	            //code to execute here
	            return false;
	        }

}

// Submits the Login Request
function submitLoginRequest() {

	// If the Login User Input was not validated successfully, stop further processing (Do not Submit) 
	if(validateLoginFormInput() === false){
		return false;
	}

// Validate the User Input before Submitting. Set it so it Alerts about the specific User Input that is invalid. 
// IF this method returns false, stop further execution and don't submit.
//if (validateUserInput("MyFormFeedback") === false){ return false; }


        var jqxhr = $.ajax({
            type:       "POST",
            url:        "/LoginServlet",
            cache:      false,
            data:       $("form").serialize(),
                
            // Before load, notify the user that the request
            // may take awhile 
            beforeSend: function() {

            // DO NOTHING FOR NOW
            
                        },
                            
            // If user remains on page for the results,
            // show alert with results
            success:    function(data, status) {

            // Redirect to the appropriate page upon successful Login authentication
            window.location = "/UserProfileServlet";

                     },
                     
            // If there is an error and the user hasn't yet closed the
            // browser, display the message. Otherwise it will come in
            // the email
            error:      function(xhr, textStatus, thrownError) {

            // Use AJAX to Inject the Error HTML to the appropriate DIV (DETAILED VERSION)
            // $('#LoginFormFeedbackDiv').html("<div id='ErrorResults'><font color='red' size='+1'><b>" + thrownError + " - </b> Error " + xhr.status + ":" + xhr.responseText + "</font></div>");

           // Use AJAX to Inject the Error HTML to the appropriate DIV (SHORT VERSION)
           $('#LoginFormFeedbackDiv').html("<div id='ErrorResults'><font color='red' size='1'><b>" + xhr.responseText + "</b></font></div>");

            // Stop further processing
            return false;
                        }
                });

    }


</script>
		
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
		<li><a href='/JSP/Mail/ContactUs.jsp'>Contact Us</a></li>
		</ul>
		</div>
		<div id='content'>
		<div id='left'>
		<div class='post'>
		<h1>Registration</h1>
		
		<p align='left'>&#160;</p>
		
		
		 <!-- Create the Registration Form  -->
		<form id='RegistrationForm' name='RegistrationForm' method='post'>
		 
		<!-- Registration For Feedback Div -->
		<div id="RegistrationFormFeedbackDiv" name="RegistrationFormFeedbackDiv"></div>
		 
		<!-- Username VARCHAR(32) NOT NULL -->
		<p> Username: </p> <input type='text' id='RegistrationUsername' name='RegistrationUsername' title='Username' size='32' />
		<br/>
		 
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
		<input type='radio' id='RegistrationGender' name='RegistrationGender' value='Female' />Female<br />
		<input type='radio' id='RegistrationGender'name='RegistrationGender' value='Male' />Male<br />
		 
		<br />
		 
		<!-- Date_Of_Birth DATE -->
		<p> Date of Birth: </p>
		 
		                <!-- Create the RegistrationBirthDay, RegistrationBirthMonth, and RegistrationBirthYear Drop-Down Menu -->
		                <select id ='RegistrationBirthDay' name = 'RegistrationBirthDay'></select> <b id='RegistrationBirthDivisor'>/</b>
		                <select id ='RegistrationBirthMonth' name = 'RegistrationBirthMonth'></select> <b id='RegistrationBirthDivisor'>/</b>
		                <select id ='RegistrationBirthYear' name = 'RegistrationBirthYear'></select>
		 
		                <!-- Call the (populateDateMenu) to populate the RegistrationBirthDay, RegistrationBirthMonth, and RegistrationBirthYear Drop-Down Menu-->
		                <script type='text/javascript'>populateDateMenu('RegistrationBirthDay', 'RegistrationBirthMonth', 'RegistrationBirthYear');</script> 
		 
		<br />
		<br />
		<br />
		 
		<!-- Registration Button -->
		<input type='button' id='RegistrationButton' name='RegistrationButton' value='Register' onClick="submitRegistrationRequest();" />

		
		</form>
		 
		
		</div>
		
		</div>
		
		<div id='right'>
		
		<h1>Login</h1>
		
		<p align='left'>&#160;</p>
		
		<!-- Create the Login Form  -->
		<form id='LoginForm' name='LoginForm' method='post'>
		
		<!-- Login For Feedback Div -->
		<div id="LoginFormFeedbackDiv" name="LoginFormFeedbackDiv"></div>
		
		<!-- Retrieve the Username -->
		<p> Username: </p> <input type='text' id='LoginUsername' name='LoginUsername' size='32' onkeypress="return checkIfEnteredPressedForLogin(event);" />
		<script type='text/javascript'>
		// Set the initial focus on the LoginUsername Element
		document.getElementById('LoginUsername').focus();
		</script>
		 
		<br />
		 
		<!-- Retrieve the Password -->
		<p> Password: </p> <input type='password' id='LoginPassword' name='LoginPassword' size='32' onkeypress="return checkIfEnteredPressedForLogin(event);" />
		 
		<p>&#160;</p>
		 
		<!-- Retrieve the Remember Me Choice -->
		<input type='checkbox' id='LoginRememberMe' name='LoginRememberMe' /> <b> Keep me logged in: </b>
		
		<!-- Redirects the user to the Account Retrieval Page -->
		<a href='#' style='margin-left:5px;'>Can't access your account ?</a>
		 
		<br />
		<br />
		 
		<!-- Login Button -->
        <input type='button' id='LoginButton' name='LoginButton' value='Login' onClick="submitLoginRequest();" /> 
		 
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
		<span>Copyright © 2014</span>
		</div>
		</div>
		</body>
		</html>

<!-- START TEST STUFF  -->
<%
// Get a Random Number From 1 to 100,000
int Min = 1;
int Max = 100000;
int Range = (Max - Min) + 1;     
int RandomNumber = (int)(Math.random() * Range) + Min;
%>
<script>
      // Use JQuery to execute JavaScript code as soon as the page loads
      $(document).ready(function(){
    	  
        // REGISTRATION TEST STUFF
        document.getElementById("RegistrationUsername").value = "<%= RandomNumber %>";
        document.getElementById("RegistrationPassword").value = "<%= RandomNumber %>";
        document.getElementById("RegistrationEMail").value = "<%= RandomNumber %>";
        document.getElementById("RegistrationEMailConfirmation").value = "<%= RandomNumber %>";
        document.getElementById("RegistrationGender").checked = true;
        document.getElementById("RegistrationBirthDay").selectedIndex = 3;
        document.getElementById("RegistrationBirthMonth").selectedIndex = 3;
        document.getElementById("RegistrationBirthYear").selectedIndex = 3;
      });
</script>
<!-- END TEST STUFF  -->

<%
}else{

	// The current user has a session, therefore redirect them to their profile
	response.sendRedirect("/UserProfileServlet");

}
%>