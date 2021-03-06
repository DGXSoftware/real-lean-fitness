<!--
GOAL: Allows the user to change their password.

PROPERTIES: Front-End Work / Back-End Work
1. User Page Display (HTML/CSS)
2. User Page Interactions (HTML/JavaScript)
3. User SQL Database Access (SQL)
*/
-->

<!-- Disable Cache -->
<% response.addHeader("Cache-Control","no-cache"); %> 

<%-- JSP Imports --%>
<%@ page import = "dgx.software.com.UtilityPackage.GlobalTools" %>



<%
// Display the main body if the user is not logged in, else forward the users to the Homepage
//if(GlobalTools.isUserCurrentlyLoggedIn(request,response)){
// Allow Everybody to see this page
boolean AlwaysTrue = true;
if(AlwaysTrue == true){
	
		// attempt to process a vote and display current results
		try {
		
		// Returns null if no session already exists 
		//HttpSession CurrentSession =  request.getSession(false);
			
		// Variables for Account session information
		//String SessionAccountID = (String) CurrentSession.getAttribute("AccountID");
		//String SessionUsername = (String) CurrentSession.getAttribute("Username");
		//String SessionFirstName = (String) CurrentSession.getAttribute("FirstName");
		//String SessionIsActivated = (String) CurrentSession.getAttribute("IsActivated");
		//String SessionIsVerified = (String) CurrentSession.getAttribute("IsVerified");
	
%>


<!-- START HTML RESPONSE -->
<!-- $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ -->


		<?xml version = '1.0'?>
		<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'>
		<html xmlns='http://www.w3.org/1999/xhtml'>
		
		<head>
		
		<!-- Set the Title for the Website Page -->
		<title>Newsletter Subscription</title>
		
		<!-- Set the Favicon for the Website page -->
		<link rel='Shortcut Icon' type='image/ico' href='/Images/favicon.ico'/>
		
		<!-- Set the Character Encoding for the Website page -->
		<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1' />
		
		<!-- Include the Stylesheet Files -->
		<link rel='stylesheet' type='text/css' href='/CSS/RLFStyle.css?<%= Math.random() %>' />
		
		<!-- Include the jQuery Files -->
		<script type='text/javascript' src="/JavaScript/JQuery/jquery.js"></script>
		<!--
		EXTERNAL jQuery Import
		<script type = "text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
		-->
		
		<!-- Include the JavaScript Files -->
		<script type='text/javascript' src='/JavaScript/Validation/GlobalFieldValidation.js' > </script>
		<script type='text/javascript' src='/JavaScript/FieldConvenience.js' > </script>
		
<script>

// Submits the Login Request
function submitForm() {

    // Validate the User Input before Submitting. Set it so it Alerts about the specific User
    // Input that is invalid. IF this method returns false, stop further execution and don't submit.

    //If the User Newsletter Action is not valid Alert so, and stop further processing
    if(!isValidDropDownFieldAlert('UserNewsletterAction','UserNewsletterActionIcon','',false)){ return false; }
    
    //If the User EMail is not valid Alert so, and stop further processing
    if(!isValidPasswordAlert('UserEMail','UserEMailIcon','',false)){ return false; }
    
        var jqxhr = $.ajax({
            type:       "POST",
            url:        "/NewsletterSubscriptionServlet",
            cache:      false,
            data:       $("form").serialize(),
                
            // Before load, notify the user that the request
            // may take awhile 
            beforeSend: function() {

            // Use AJAX to Inject the Sending Now HTML to the appropriate DIV
            $('#RequestForgotPasswordChangeFormFeedbackDiv').html("<div id='BeforeSendResults'><font color='blue' size='+2'><b> Sending Now! </b></font></div>");
            
                        },
                            
            // If user remains on page for the results,
            // show alert with results
            success:    function(data, status) {
			
            // Generate the specialized success message and forward
            var UserNewsletterAction = document.getElementById('UserNewsletterAction').value;
            var UserEMail = document.getElementById('UserEMail').value;
            var SuccessMessage = "The Newsletter subscription for " + UserEMail + " was successfully " + UserNewsletterAction + ".";
            
            // Forward to the Success URL
            window.location = "<%= GlobalTools.GTV_CountdownForwardMessage %>?SuccessMessage=" + SuccessMessage;
            
                     },
                     
            // If there is an error and the user hasn't yet closed the
            // browser, display the message. Otherwise it will come in
            // the email
            error:      function(xhr, textStatus, thrownError) {

            // Use AJAX to Inject the Error HTML to the appropriate DIV (DETAILED VERSION)
            // $('#RequestForgotPasswordChangeFormFeedbackDiv').html("<div id='ErrorResults'><font color='red' size='+1'><b>" + thrownError + " - </b> Error " + xhr.status + ":" + xhr.responseText + "</font></div>");

            // Use AJAX to Inject the Error HTML to the appropriate DIV (SHORT VERSION)
            $('#RequestForgotPasswordChangeFormFeedbackDiv').html("<div id='ErrorResults'><font color='red' size='1'><b>" + xhr.responseText + "</b></font></div>");

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
		<%
		// Print the Logged Out Menu Items
		GlobalTools.printPageMenuItems(out,"LoggedOut","");
		%>
		</ul>
		</div>
		<div id='content'>
		<div id='left'>
		<div class='post'>
		<h1>Newsletter Subscription</h1>
		
		<p align='left'>&#160;</p>
		
		<form id='RequestForgotPasswordChangeForm' name='RequestForgotPasswordChangeForm' method='post'>
		
		<!-- Login For Feedback Div -->
		<div id="RequestForgotPasswordChangeFormFeedbackDiv" name="RequestForgotPasswordChangeFormFeedbackDiv"></div>
		
		<!-- In_Newsletter CHAR(1) -->
		<div id="UserNewsletterActionDiv">
		<label id="UserNewsletterActionLabel" for="UserNewsletterAction">
		<p> Enable or Disable : </p>
		<select id="UserNewsletterAction" name="UserNewsletterAction" onChange="isValidDropDownField('UserNewsletterAction','UserNewsletterActionIcon','',false);" >
        <option value="" disabled selected style="display:none">Enable or Disable</option>
        <option value="Enabled">Enable</option>
        <option value="Disabled">Disable</option>
        </select>
        <img id="UserNewsletterActionIcon" src="/Images/Icons/Valid/Valid(16x16).png" style="visibility:hidden;" />
        </label>
        </div>
		
		<!-- Password VARCHAR(32) -->
        <label for="UserEMail">
        <p> E-Mail: </p>
        <input type="text" id="UserEMail" name="UserEMail" onKeyUp="isValidEMail('UserEMail','UserEMailIcon','',false);" 
        title='New Password' size='32' maxlength='32' />
        <img id="UserEMailIcon" src="/Images/Icons/Valid/Valid(16x16).png" style="visibility:hidden;" />
        <script>
		// Set the initial focus on the UserEMail Element
		document.getElementById('UserEMail').focus();
		</script>
        </label>
		
        <br />
		<br />
		<br />
        
        <!-- Change Password Button -->
		<input type='button' id='RequestForgotChangePasswordButton' name='RequestForgotChangePasswordButton' value='Request Newsletter Subscription' onClick="submitForm();" />
        
         <script>
     	// Use JQuery to execute JavaScript code as soon as the page loads
     	// This code focuses on automatically handling preloaded Query String Requests
      	$(document).ready(function(){
      		
      	<%
      	// User=Anonymous
      	String UserType = request.getParameter("UserType");
      	String UserEMail = request.getParameter("UserEMail");
      	%>
      	
      	    // If the User type is "AnonymousAdd" setup for an Anonymous Add
      	    // EX: http://localhost:8080/JSP/UserPages/Settings/NewsletterSubscription.jsp?UserType=AnonymousEnable
    		if("<%= UserType %>" == "AnonymousEnable"){
      		document.getElementById("UserNewsletterAction").selectedIndex = "1";
      		$('#UserNewsletterAction').hide();
      		$('#UserNewsletterActionLabel').hide();
    		}
      	    
      	    // If the User type is "UserNewsletterUnsubscribe" and a valid E-Mail was provided
      	    // Setup for a User Newsletter Unsubscribe
      	    // EX: http://localhost:8080/JSP/UserPages/Settings/NewsletterSubscription.jsp?UserType=UserDisable&UserEMail=DMGX@RLF.com
    		if("<%= UserType %>" == "UserDisable" && "<%= UserEMail %>" != ""){
      		document.getElementById("UserNewsletterAction").selectedIndex = "2";
      		document.getElementById("UserEMail").value = "<%= UserEMail %>";
      		submitForm();
    		}
      		
     	});
		</script>
        
		</form>
	    
	    </div>
		
		</div>
	    
	    <!-- DISABLED -->
	    <!--
		<div id='right'>
		<h1 class='Center_Text'>Username Change</h1>
		</div>
	    -->
	    
		<div class='clear'></div>
		</div>
		</div>
		
		<div id='footer'>
		<ul>
		<%
		// Print the Empty Menu Items
		GlobalTools.printPageMenuItems(out,"Empty","");
		%>
		</ul>
		<span>Copyright � 2014</span>
		</div>
		</div>
		</body>
		</html>

<!-- This scripts are unrelated to page loading  -->
<script>
      // Use JQuery to execute JavaScript code as soon as the page loads
      $(document).ready(function(){
    	  
        // As soon as the Role is changed, Remove the Index Drop Down Item which has an empty value of ('').
        $('#UserNewsletterAction').on('change', function() {
        	$("#UserNewsletterAction option[value='']").remove();
        });
        
      });
</script>

<!-- $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ -->
<!-- END HTML RESPONSE -->

<%
		}
		// if an exception occurs, return the error page
		catch (Exception EX) { EX.printStackTrace();		
		}
%>

<%
}else{

	
    // Since the Query String crednetials were invalid or expired
    // Return the user Home via a Cancel Countdown Forward Message
    String CancelMessage = "Invalid or expired password key. Please retry your request.";
    String CancelURL = GlobalTools.GTV_CountdownForwardMessage + "?CancelMessage="+CancelMessage+"";

    // Forward the User back to the Homepage
	response.sendRedirect(CancelURL);

}
%>
