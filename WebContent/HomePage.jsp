<!--
GOAL: Act as the main Homepage. Also allows for account Login and Registration.
-->

<!-- Disable Cache -->
<% response.addHeader("Cache-Control","no-cache"); %> 

<%-- JSP Imports --%>
<%@ page import = "dgx.software.com.UtilityPackage.GlobalTools" %>

<%
// Display the main body if the user is not logged in, else forward the users to their profile page
if(!(GlobalTools.isUserCurrentlyLoggedIn(request,response))){
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
		<link rel='stylesheet' type='text/css' href='/CSS/RLFStyle.css?<%= Math.random() %>' />
		
		<!-- Include the JavaScript Files -->
		<script type='text/javascript' src='/JavaScript/Validation/GlobalFieldValidation.js' > </script>
		<script type='text/javascript' src='/JavaScript/Validation/LoginPageValidation.js' > </script>
		<script type='text/javascript' src='/JavaScript/Validation/RegistrationPageValidation.js' > </script>
		<script type='text/javascript' src='/JavaScript/Drop-Down Menu Population.js' > </script>
		
		<!-- Include the jQuery Files -->
		<script type='text/javascript' src="/JavaScript/JQuery/jquery.js"></script>
		<!--
		EXTERNAL jQuery Import
		<script type = "text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
		-->

<style>

#RegistrationErrorFeedbackDiv { font-weight:bold; color:blue; font-size:125%; }
#SubRegistrationErrorFeedbackDiv { font-weight:bold; color:red; font-size:85%; list-style-position:inside;}

</style>

<script>
// START: ENABLE FIELD CONVENIENVE
$(document).ready(function() {
	
	// ENABLE: Highlight all Values for all Text Fields when focused
    $("input:text").focus(function() { $(this).select(); } );
	
    // ENABLE: Highlight all Values for all Password Fields when focused
    $("input:password").focus(function() { $(this).select(); } );
  	
    // ENABLE: Submit on Enter press For all Text Fields with an ID of pattern "Registration"
    // Use JQuery to gather all the matching "Registration*" elements
    $("[id^=Registration]").keypress(function (e) {
  	  if (e.which == 13) {
  	    submitRegistrationRequest();
  	    return false;
  	  }
  	});
  	
    // ENABLE: Submit on Enter press For all Text Fields with an ID of pattern "Login"
    // Use JQuery to gather all the matching "Login*" elements
    $("[id^=Login]").keypress(function (e) {
  	  if (e.which == 13) {
  	    submitLoginRequest();
  	    return false;
  	  }
  	});
    
});
//END: ENABLE FIELD CONVENIENVE
</script>

<script>

// Submits the Registration Request
function submitRegistrationRequest() {

	// Validate the User Input before Submitting. Set it so it Alerts about the specific User Input that is invalid. 
	// IF this method returns false, stop further execution and don't submit.
	if (validateFormOnSubmit("RegistrationFormFeedbackDiv") === false){ return false; }

// Validate the User Input before Submitting. Set it so it Alerts about the specific User Input that is invalid. 
// IF this method returns false, stop further execution and don't submit.

        var jqxhr = $.ajax({
            type:       "POST",
            url:        "/RegistrationServlet",
            cache:      false,
            data:       $("form").serialize(),
                
            // Before load, notify the user that the request may take a while 
            beforeSend: function() {

            // Use AJAX to Inject the Sending Now HTML to the appropriate DIV
            $('#RegistrationFormFeedbackDiv').html("<div id='BeforeSendResults'><font color='blue' size='+2'><b> Sending Now! </b></font></div>");
                
                        },
                            
            // If user remains on page for the results, show alert with results
            success:    function(data, status) {
            
            // Use AJAX to Inject the Success HTML to the appropriate DIV
            $('#RegistrationFormFeedbackDiv').html("<div id='SuccessResults'><font color='green' size='+2'><b> "+data+" </b></font></div>");
            	
            // Redirect to the appropriate page upon successful Registration
            //alert("Account created successfully!");
            window.location = "<%= GlobalTools.GTV_PayPalRegistrationSubmit %>" + "?" + "RegistrationUsername=" + document.getElementById("RegistrationUsername").value;

                     },
                     
           // If there is an error and the user hasn't yet closed the
           // browser, display the message. Otherwise it will come in the email
            error:      function(xhr, textStatus, thrownError) {

            // Use AJAX to Inject the Error HTML to the appropriate DIV (DETAILED VERSION)
            // $('#RegistrationFormFeedbackDiv').html("<div id='ErrorResults'><font color='red' size='+1'><b> " + thrownError + " - </b> Error " + xhr.status + ":" + xhr.responseText + "</font></div>");
                
            // Use AJAX to Inject the Error HTML to the appropriate DIV (SHORT VERSION)
            $('#RegistrationFormFeedbackDiv').html("<div id='ErrorResults'><font color='red' size='+1'><b> " + xhr.responseText + "</b></font></div>");
            	
            /* RETIRED */ 
            /*
            // Use AJAX to Inject the Error HTML to the appropriate DIV (DETAILED VERSION)
            // $('#RegistrationFormFeedbackDiv').html("<div id='ErrorResults'><font color='red' size='+1'><b>" + thrownError + " - </b> Error " + xhr.status + ":" + xhr.responseText + "</font></div>");

           // Use AJAX to Inject the Error HTML to the appropriate DIV (SHORT VERSION)
           //$('#RegistrationFormFeedbackDiv').html("<div id='ErrorResults'><font color='red' size='1'><b>" + xhr.responseText + "</b></font></div>");
           */
           
            // Stop further processing
            return false;
            
                        }
                });

    }


</script>


<script>

// Submits the Login Request
function submitLoginRequest() {

	// If the Login User Input was not validated successfully, stop further processing (Do not Submit) 
	if(validateLoginFormInput() === false){
		return false;
	}

// Validate the User Input before Submitting. Set it so it Alerts about the specific User Input that is invalid. 
// IF this method returns false, stop further execution and don't submit.

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

    		// Manage Local Storage to Remember or Forget Credentials
    		manageLocalStorageSubmit();
            	
            // Redirect to the appropriate page upon successful Login authentication
            window.location = "<%= GlobalTools.GTV_UserProfile %>";

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
		<li><a href='<%= GlobalTools.GTV_ContactUs %>'>Contact Us</a></li>
		</ul>
		</div>
		<div id='content'>
		<div id='left'>
		<div class='post'>
		<h1>Registration</h1>
		
		<p align='left'>&#160;</p>
		
		
<!-- Create the Registration Form  -->
<form id='RegistrationForm' name='RegistrationForm' method='post'>
		
		<!-- Summary Feedback DIV -->
		<div id="RegistrationFormFeedbackDiv"></div>

        <!-- First_Name VARCHAR(32) -->
		<label for="RegistrationFirstName">
		<p> First Name: </p>
        <input type="text" id="RegistrationFirstName" name="RegistrationFirstName" onKeyUp="isValidNameField('RegistrationFirstName','RegistrationFirstNameIcon','',false);" 
        title='Registration First Name' size='32' maxlength='32' />
        <img id="RegistrationFirstNameIcon" src="/Images/Icons/Valid/Valid(16x16).png" style="visibility:hidden;" />
        </label>

        <!-- Last_Name VARCHAR(32) -->
		<label for="RegistrationLastName">
		<p> Last Name: </p>
        <input type="text" id="RegistrationLastName" name="RegistrationLastName" onKeyUp="isValidNameField('RegistrationLastName','RegistrationLastNameIcon','',false);" 
        title='Registration Last Name' size='32' maxlength='32' />
        <img id="RegistrationLastNameIcon" src="/Images/Icons/Valid/Valid(16x16).png" style="visibility:hidden;" />
        </label>
		
		<br/>
		
		<!-- Username VARCHAR(32) NOT NULL -->
		<label for="RegistrationUsername">
		<p> Username: </p>
		<input type="text" id="RegistrationUsername" name="RegistrationUsername" onKeyUp="isValidUsername('RegistrationUsername','RegistrationUsernameIcon','',false);" 
		title='Registration Username' size='32' maxlength='32' />
		<img id="RegistrationUsernameIcon" src="/Images/Icons/Valid/Valid(16x16).png" style="visibility:hidden;" />
		</label>

		<br/>
		
		<!-- Password VARCHAR(32) -->
        <label for="RegistrationPassword">
        <p> Password: </p>
        <input type="password" id="RegistrationPassword" name="RegistrationPassword" onKeyUp="isValidPassword('RegistrationPassword','RegistrationPasswordIcon','',false);" 
        title='Registration Password' size='32' maxlength='32' />
        <img id="RegistrationPasswordIcon" src="/Images/Icons/Valid/Valid(16x16).png" style="visibility:hidden;" />
        </label>
		
		<br/>
		 
		<!-- EMail VARCHAR(255) -->
        <label for="RegistrationEMail">
        <p> E-Mail: </p>
        <input type="text" id="RegistrationEMail" name="RegistrationEMail" onKeyUp="isValidEMail('RegistrationEMail','RegistrationEMailIcon','',false); isValidConfirmation('RegistrationEMailConfirmation','RegistrationEMailConfirmationIcon','','RegistrationEMail',false);" 
        title='Registration E-Mail' size='48' maxlength='255' />
        <img id="RegistrationEMailIcon" src="/Images/Icons/Valid/Valid(16x16).png" style="visibility:hidden;" />
        </label>
		 

		<!-- EMail VARCHAR(255) -->
        <label for="RegistrationEMailConfirmation">
        <p> Confirm E-Mail: </p>
        <input type="text" id="RegistrationEMailConfirmation" name="RegistrationEMailConfirmation" onKeyUp="isValidConfirmation('RegistrationEMailConfirmation','RegistrationEMailConfirmationIcon','','RegistrationEMail',false);" 
        title='Registration E-Mail' size='48' maxlength='255' />
        <img id="RegistrationEMailConfirmationIcon" src="/Images/Icons/Valid/Valid(16x16).png" style="visibility:hidden;" />
        </label>
        
		<br />

		<!-- Gender VARCHAR(32) -->
		<label for="RegistrationGender">
		<p> Gender: </p>
		<select id="RegistrationGender" name="RegistrationGender" onChange="isValidDropDownField('RegistrationGender','RegistrationGenderIcon','',false);" >
        <option value="" disabled selected style="display:none">I am...</option>
        <option value="Female">Female</option>
        <option value="Male">Male</option>
        <option value="Other">Other</option>
        </select>
        <img id="RegistrationGenderIcon" src="/Images/Icons/Valid/Valid(16x16).png" style="visibility:hidden;" />
        </label>
		 
		<br />
		
		<!-- Date_Of_Birth DATE -->
		<label for="RegistrationBirthDateOfBirth">
		<p> Date of Birth: </p>
	    <select id ='RegistrationBirthDay' name='RegistrationBirthDay' onChange="isValidDropDownField('RegistrationBirthDay','RegistrationBirthDayIcon','',false);"></select> <b id='RegistrationBirthDivisor'>/</b>
		<select id ='RegistrationBirthMonth' name = 'RegistrationBirthMonth' onChange="isValidDropDownField('RegistrationBirthMonth','RegistrationBirthMonthIcon','',false);"></select> <b id='RegistrationBirthDivisor'>/</b>
		<select id ='RegistrationBirthYear' name = 'RegistrationBirthYear' onChange="isValidDropDownField('RegistrationBirthYear','RegistrationBirthYearIcon','',false);"></select>
		 
		<img id="RegistrationBirthDayIcon" src="/Images/Icons/Valid/Valid(16x16).png" style="visibility:hidden;" />
        <img id="RegistrationBirthMonthIcon" src="/Images/Icons/Valid/Valid(16x16).png" style="visibility:hidden;" />
        <img id="RegistrationBirthYearIcon" src="/Images/Icons/Valid/Valid(16x16).png" style="visibility:hidden;" />
        </label>
		
		<!-- Call the (populateDateMenu) to populate the RegistrationBirthDay, RegistrationBirthMonth, and RegistrationBirthYear Drop-Down Menu-->
		<script type='text/javascript'>populateDateMenu('RegistrationBirthDay', 'RegistrationBirthMonth', 'RegistrationBirthYear');</script> 
		 
		<br/>
		<br/>
		<br/>
		
		<label for="SignUpForNewsletter">
		<input type="checkbox" name="SignUpForNewsletter" id="SignUpForNewsletter">Sign up for our newsletter<br>
		<script>document.getElementById("SignUpForNewsletter").checked = true;</script>
		</label>
		
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
		<p> Username: </p> <input type='text' id='LoginUsername' name='LoginUsername' size='32' />
		<script>
		// Set the initial focus on the LoginUsername Element
		document.getElementById('LoginUsername').focus();
		</script>
		 
		<br />
		 
		<!-- Retrieve the Password -->
		<p> Password: </p> <input type='password' id='LoginPassword' name='LoginPassword' size='32' />
		 
		<p>&#160;</p>
		 
		<!-- Retrieve the Remember Me Choice -->
		<input type='checkbox' id='LoginRememberMe' name='LoginRememberMe' /> <b> Keep me logged in: </b>
		
		<!-- Redirects the user to the Account Retrieval Page -->
		<a href='<%= GlobalTools.GTV_Settings_RequestForgotPasswordChange %>' style='margin-left:5px;'>Can't access your account ?</a>
		 
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
        document.getElementById("RegistrationFirstName").value = "TestFirstName";
        document.getElementById("RegistrationLastName").value = "TestLastName";
        document.getElementById("RegistrationUsername").value = "TestUsername" + "<%= RandomNumber %>";
        document.getElementById("RegistrationPassword").value = "TestUsername" + "<%= RandomNumber %>";
        document.getElementById("RegistrationEMail").value = "DGX_" + "<%= RandomNumber %>" + "@RLF.com";
        document.getElementById("RegistrationEMailConfirmation").value = "DGX_" + "<%= RandomNumber %>" + "@RLF.com";
        document.getElementById("RegistrationGender").selectedIndex = "2";
        document.getElementById("RegistrationBirthDay").selectedIndex = "18";
        document.getElementById("RegistrationBirthMonth").selectedIndex = "10";
        document.getElementById("RegistrationBirthYear").selectedIndex = "28";
        
        // LOGIN TEST STUFF
        //document.getElementById("LoginUsername").value = "TestUsername9437";
        //document.getElementById("LoginPassword").value = "TestUsername9437";

      });
</script>
<!-- END TEST STUFF  -->

<!-- This scripts are unrelated to page loading  -->
<script>
      // Use JQuery to execute JavaScript code as soon as the page loads
      $(document).ready(function(){
    	  
        // As soon as the Role is changed, Remove the Index Drop Down Item which has an empty value of ('').
        $('#RegistrationGender').on('change', function() {
        	$("#RegistrationGender option[value='']").remove();
        });
        
        // As soon as the Role is changed, Remove the Index Drop Down Item which has an empty value of ('').
        $('#RegistrationBirthDay').on('change', function() {
        	$("#RegistrationBirthDay option[value='']").remove();
        });
        
        // As soon as the Role is changed, Remove the Index Drop Down Item which has an empty value of ('').
        $('#RegistrationBirthMonth').on('change', function() {
        	$("#RegistrationBirthMonth option[value='']").remove();
        });
        
        // As soon as the Role is changed, Remove the Index Drop Down Item which has an empty value of ('').
        $('#RegistrationBirthYear').on('change', function() {
        	$("#RegistrationBirthYear option[value='']").remove();
        });
        
      });
</script>

<!-- These scripts are unrelated to page loading  -->
<!-- Enable Local Storage -->
<script src="/JavaScript/LocalStorage.js?UserRememberMeCheckBoxID=LoginRememberMe&UserFieldRememberListIDs=LoginUsername;LoginPassword"></script>

<%
}else{

	// The current user has a session, therefore redirect them to their profile
	response.sendRedirect(GlobalTools.GTV_UserProfile);

}
%>