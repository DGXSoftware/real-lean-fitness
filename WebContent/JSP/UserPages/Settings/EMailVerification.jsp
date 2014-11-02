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
<%@ page import = "java.sql.Connection" %>
<%@ page import = "java.sql.DriverManager" %>
<%@ page import = "java.sql.ResultSetMetaData" %>
<%@ page import = "java.sql.Statement" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import = "java.sql.SQLException" %>
<%@ page import = "java.net.URLDecoder" %>
<%@ page import = "java.net.URLEncoder" %>
<%@ page import = "dgx.software.com.UtilityPackage.AESEncryption" %>
<%@ page import = "dgx.software.com.UtilityPackage.GlobalTools" %>

<%
// Display the main body if the user is not logged in, else forward the users to the Homepage
if(GlobalTools.isUserCurrentlyLoggedIn(request,response)){
	
		// attempt to process a vote and display current results
		try {
		
		// Returns null if no session already exists 
		HttpSession CurrentSession =  request.getSession(false);
			
		// Variables for Account session information
		//String SessionAccountID = (String) CurrentSession.getAttribute("AccountID");
		String SessionUsername = (String) CurrentSession.getAttribute("Username");
		String SessionFirstName = (String) CurrentSession.getAttribute("FirstName");
		String SessionIsActivated = (String) CurrentSession.getAttribute("IsActivated");
		String SessionIsVerified = (String) CurrentSession.getAttribute("IsVerified");

		// Proceed only if the current user is not verified
		if(SessionIsVerified.equals("N")){
		
			
		
			// Object to Decrypt the sensitive data
			AESEncryption AES = new AESEncryption();
			
			// EXAMPLE: http://localhost:8080?do=fpwdc&usr=oNh70K0CQQE375uT%2BXSibQ%3D%3D&key=WPgRqb2XuZH29hbhWhlFW5M8V912lxd%2Bb%2B56UiT%2BsKU%3D
			// Get the ActionValue
		    String ActionValue = (String) request.getParameter("do");
			
		    // Get the Encrypted and Encoded UserValue and Retrieve it Decoded
			String UserValue = (String) request.getParameter("usr");
			
			// Get the Encrypted and Encoded KeyValue and Retrieve it Decoded
			String KeyValue = (String) request.getParameter("key");
			
			// Set Null query string values to Empty Strings to avoid Null Pointer Exceptions
			if(ActionValue == null){ ActionValue = ""; }
			if(UserValue == null){ UserValue = ""; }
			if(KeyValue == null){ KeyValue = ""; }
			
			// Encode the UserValue and KeyValue
			String EncodedUser = URLEncoder.encode(UserValue, "UTF-8");
			String EncodedKey = URLEncoder.encode(KeyValue, "UTF-8");
			
			// Validate that all query string values are valid
			
			// If IsValidAction is not Empty then It's Valid
			boolean IsValidAction = false;
			if(!ActionValue.equals("")){ IsValidAction = true; }
		
			// If IsValidUser is not Empty then It's Valid
			boolean IsValidUser = false;
			if(!UserValue.equals("")){ IsValidUser = true; }
			
			// If IsValidUser is not Empty then It's Valid
			boolean IsValidKey = false;
			if(!KeyValue.equals("")){ IsValidKey = true; }

			
		if(IsValidAction == true && IsValidUser == true && IsValidKey == true){
%>


<!-- START E-MAIL VERIFICATION ACTIVATION RESPONSE -->
<!-- $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ -->

		<html>
			
		<head>
		
		<!-- Include the jQuery Files -->
		<script type='text/javascript' src="/JavaScript/JQuery/jquery.js"></script>
		<!--
		EXTERNAL jQuery Import
		<script type = "text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
		-->
			
			<script>

// Submits the submitEMailVerificationActivationForm Request
function submitEMailVerificationActivationForm() {

        var jqxhr = $.ajax({
            type:       "POST",
            url:        "/EMailVerificationServlet",
            cache:      false,
            data:       $("form").serialize(),
                
            // Before load, notify the user that the request
            // may take awhile 
            beforeSend: function() {
            
            // DO NOTHING
                        },
                            
            // If user remains on page for the results,
            // show alert with results
            success:    function(data, status) {

            <%
            // Since the E-Mail Verification was successful
            // Return the user Home via a Successful Countdown Forward Message
            // Plus log the user out so they can re-log with their new credentials
            String SuccessMessage = "You E-Mail was successfully verified! Please re-log to view your changes.";
            String SuccessURL = GlobalTools.GTV_CountdownForwardMessage + "?SuccessMessage="+SuccessMessage + "&RedirectURL=" + GlobalTools.GTV_Servlet_Logout;
            %>
                    
            // Forward to the Success URL
            window.location = "<%= SuccessURL %>";

                     },
                     
            // If there is an error and the user hasn't yet closed the
            // browser, display the message. Otherwise it will come in
            // the email
            error:      function(xhr, textStatus, thrownError) {

                <%
                // Since the E-Mail verification was unsuccessful
                // Return the user Home via a Successful Countdown Forward Message
                String CancelMessage = "Your E-Mail verification process failed. Please try again";
                String CancelMessageURL = GlobalTools.GTV_CountdownForwardMessage + "?CancelMessage="+CancelMessage+"";
                %>
                    
                // Forward to the Success URL
                window.location = "<%= CancelMessageURL %>";
                
            // Stop further processing
            return false;
                        }
                });

    }

</script>
			
					
		
		</head>
			
			<body>
			<!-- Automatically Submit a EMailVerificationServlet Request to send the Verification E-Mail -->
			<form name="EMailVerificationActivationForm" id="EMailVerificationActivationForm" method="post">
			<!-- Set the E-Mail Recipient -->
			<input type="hidden" id="SessionUsername" name="SessionUsername" value="<%= SessionUsername %>" />
			<!-- Set the Servlet Action -->
			<input type="hidden" id="EMailVerificationServletAction" name="EMailVerificationServletAction" value="EMailVerificationActivation" />
			<input type="hidden" id="ActionValue" name="ActionValue" value="<%= ActionValue %>" />
			<input type="hidden" id="UserValue" name="UserValue" value="<%= UserValue %>" />
			<input type="hidden" id="KeyValue" name="KeyValue" value="<%= KeyValue %>" />
			<script>
			$( document ).ready(function() {
			// As soon as the page loads submit the form
			submitEMailVerificationActivationForm();
			});
			</script>
			</form>
			</body>
			</html>
			
<!-- $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ -->
<!-- END E-MAIL VERIFICATION ACTIVATION RESPONSE -->

<%
		}else{
			// System.out.println("SEND E-Mail so they can verify!!!!");
			
			%>
			
<!-- START E-MAIL VERIFICATION SEND RESPONSE -->
<!-- $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ -->

		<html>
			
		<head>
		
		<!-- Include the jQuery Files -->
		<script type='text/javascript' src="/JavaScript/JQuery/jquery.js"></script>
		<!--
		EXTERNAL jQuery Import
		<script type = "text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
		-->
			
			<script>

// Submits the EMailVerificationServlet Request
function submitEMailVerificationSendForm() {

        var jqxhr = $.ajax({
            type:       "POST",
            url:        "/EMailVerificationServlet",
            cache:      false,
            data:       $("form").serialize(),
                
            // Before load, notify the user that the request
            // may take awhile 
            beforeSend: function() {
            
            // DO NOTHING
                        },
                            
            // If user remains on page for the results,
            // show alert with results
            success:    function(data, status) {

            <%
            // Since the Verification E-Mail was sent successfully
            // Return the user Home via a Successful Countdown Forward Message
            String SuccessMessage = "Please check your E-Mail for instructions to verify your E-Mail address.";
            String SuccessURL = GlobalTools.GTV_CountdownForwardMessage + "?SuccessMessage="+SuccessMessage+"";
            %>
                
            // Forward to the Success URL
            window.location = "<%= SuccessURL %>";
            
                     },
                     
            // If there is an error and the user hasn't yet closed the
            // browser, display the message. Otherwise it will come in
            // the email
            error:      function(xhr, textStatus, thrownError) {

                <%
                // Since the Verification E-Mail failed to send
                // Return the user Home via a Successful Countdown Forward Message
                String CancelMessage = "Failed to send instructions to verify your account. Please try again";
                String CancelMessageURL = GlobalTools.GTV_CountdownForwardMessage + "?CancelMessage="+CancelMessage+"";
                %>
                    
                // Forward to the Success URL
                window.location = "<%= CancelMessageURL %>";
                
            // Stop further processing
            return false;
                        }
                });

    }

</script>
			
					
		
		</head>
			
			<body>
			<!-- Automatically Submit a EMailVerificationServlet Request to send the Verification E-Mail -->
			<form name="EMailVerificationSendForm" id="EMailVerificationSendForm" method="post">
			<!-- Set the E-Mail Recipient -->
			<input type="hidden" id="SessionUsername" name="SessionUsername" value="<%= SessionUsername %>" />
			<!-- Set the Servlet Action -->
			<input type="hidden" id="EMailVerificationServletAction" name="EMailVerificationServletAction" value="EMailVerificationSend" />
			<script>
			$( document ).ready(function() {
			// As soon as the page loads submit the form
			submitEMailVerificationSendForm();
			});
			</script>
			</form>
			</body>
			</html>
			
<!-- $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ -->
<!-- END E-MAIL VERIFICATION SEND RESPONSE -->			
			<%
			
		}

%>
		
<%
		}else{
			
			// The current user is already Verified
		    // Return the user Home via a Success Countdown Forward Message
		    String SuccessMessage = "Your account E-Mail is already verified.";
		    String SuccessURL = GlobalTools.GTV_CountdownForwardMessage + "?SuccessMessage="+SuccessMessage+"";

		    // Forward the User back to the Homepage
			response.sendRedirect(SuccessURL);
			
		}
%>

<%
		}
		// if an exception occurs, return the error page
		catch (Exception EX) { EX.printStackTrace();		
		}
%>

<%
}else{

	
	// The current user does NOT have a session, therefore redirect them to the Homepage
    // Return the user Home via a Cancel Countdown Forward Message
    String CancelMessage = "Please login to verify your account.";
    String CancelURL = GlobalTools.GTV_CountdownForwardMessage + "?CancelMessage="+CancelMessage+"";

    // Forward the User back to the Homepage
	response.sendRedirect(CancelURL);
	
}
%>
