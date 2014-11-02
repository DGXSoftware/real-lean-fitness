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
	
%>


<!-- START HTML RESPONSE -->
<!-- $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ -->


		<?xml version = '1.0'?>
		<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'>
		<html xmlns='http://www.w3.org/1999/xhtml'>
		
		<head>
		
		<!-- Set the Title for the Website Page -->
		<title>Password Change</title>
		
		<!-- Set the Favicon for the Website page -->
		<link rel='Shortcut Icon' type='image/ico' href='/Images/favicon.ico'/>
		
		<!-- Set the Character Encoding for the Website page -->
		<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1' />
		
		<!-- Include the Stylesheet Files -->
		<link rel='stylesheet' type='text/css' href='/CSS/RLFStyle.css' />
		
		<!-- Include the JavaScript Files -->
		<script language='javascript' type='text/javascript' src='/JavaScript/Validation/UserInformationPageValidation.js' > </script>
		
		<script>
		function submitPasswordChange(){
			alert("CHANGE THE PASSWORD!!!!!!!!");
		}
		</script>
		
		</head>
		
		<body>

		<div id='container'>
		<div id='main'>
		<div id='header'></div>
		<div id='nav'>
		<ul>
		<li><a href='<%= GlobalTools.GTV_UserProfile %>'><%=SessionFirstName%></a></li>
		<li><a href='#'></a></li>
		<li><a href='#'></a></li>
		<li><a href='#'></a></li>
		<li><a href='<%= GlobalTools.GTV_UserSettings %>'>Settings</a></li>
		<li><a href='/LogOutServlet'>Log Out</a></li>
		</ul>
		</div>
		<div id='content'>
		<div id='left'>
		<div class='post'>
		<h1>Change Password</h1>
		
		<p align='left'>&#160;</p>
		
		<form action='' method='post' id='PasswordChangeForm' name='PasswordChangeForm'>
		
		<!-- Password VARCHAR(32) -->
        <label for="OldPassword">
        <p> Old Password: </p>
        <input type="text" id="OldPassword" name="OldPassword" onKeyUp="isValidEMail('OldPassword','OldPasswordIcon','',false); isValidConfirmation('ConfirmationOldPassword','ConfirmationOldPasswordIcon','','OldPassword',false);" 
        title='Old Password' size='32' maxlength='32' />
        <img id="OldPasswordIcon" src="/Images/Icons/Valid/Valid(16x16).png" style="visibility:hidden;" />
        </label>
		 
		<!-- Password VARCHAR(32) -->
        <label for="ConfirmationOldPassword">
        <p> Confirm Old Password: </p>
        <input type="text" id="ConfirmationOldPassword" name="ConfirmationOldPassword" onKeyUp="isValidConfirmation('ConfirmationOldPassword','ConfirmationOldPasswordIcon','','OldPassword',false);" 
        title='Confirm Old Password' size='32' maxlength='32' />
        <img id="ConfirmationOldPasswordIcon" src="/Images/Icons/Valid/Valid(16x16).png" style="visibility:hidden;" />
        </label>
        
        <!-- Password VARCHAR(32) -->
        <label for="NewPassword">
        <p> New Password: </p>
        <input type="text" id="NewPassword" name="NewPassword" onKeyUp="isValidEMail('NewPassword','NewPasswordIcon','',false); isValidConfirmation('ConfirmationNewPassword','ConfirmationNewPasswordIcon','','NewPassword',false);" 
        title='New Password' size='32' maxlength='32' />
        <img id="NewPasswordIcon" src="/Images/Icons/Valid/Valid(16x16).png" style="visibility:hidden;" />
        </label>
        
        <br />
		<br />
		<br />
        
        <!-- Change Password Button -->
		<input type='button' id='ChangePasswordButton' name='ChangePasswordButton' value='Change Password' onClick="submitPasswordChange();" />
        
		</form>
	    
	    </div>
		
		</div>
	    
		<div id='right'>
		<h1 class='Center_Text'>Username Change</h1>
		
		<form action='' method='post' id='UsernameChangeForm' name='UsernameChangeForm'>
		
		<!-- Username VARCHAR(32) -->
        <label for="OldUsername">
        <p> Old Username: </p>
        <input type="text" id="OldUsername" name="OldUsername" onKeyUp="isValidEMail('OldUsername','OldUsernameIcon','',false); isValidConfirmation('ConfirmationOldUsername','ConfirmationOldUsernameIcon','','OldUsername',false);" 
        title='Old Username' size='32' maxlength='32' />
        <img id="OldUsernameIcon" src="/Images/Icons/Valid/Valid(16x16).png" style="visibility:hidden;" />
        </label>
		 
		<!-- Username VARCHAR(32) -->
        <label for="ConfirmationOldUsername">
        <p> Confirm Old Username: </p>
        <input type="text" id="ConfirmationOldUsername" name="ConfirmationOldUsername" onKeyUp="isValidConfirmation('ConfirmationOldUsername','ConfirmationOldUsernameIcon','','OldUsername',false);" 
        title='Confirm Old Username' size='32' maxlength='32' />
        <img id="ConfirmationOldUsernameIcon" src="/Images/Icons/Valid/Valid(16x16).png" style="visibility:hidden;" />
        </label>
        
        <!-- Username VARCHAR(32) -->
        <label for="NewUsername">
        <p> New Username: </p>
        <input type="text" id="NewUsername" name="NewUsername" onKeyUp="isValidEMail('NewUsername','NewUsernameIcon','',false); isValidConfirmation('ConfirmationNewUsername','ConfirmationNewUsernameIcon','','NewUsername',false);" 
        title='New Username' size='32' maxlength='32' />
        <img id="NewUsernameIcon" src="/Images/Icons/Valid/Valid(16x16).png" style="visibility:hidden;" />
        </label>
        
        <br />
		<br />
		<br />
        
        <!-- Change Username Button -->
		<input type='button' id='ChangeUsernameButton' name='ChangeUsernameButton' value='Change Username' onClick="submitUsernameChange();" />
        
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

	// The current user does NOT have a session, therefore redirect them to the Homepage
	response.sendRedirect(GlobalTools.GTV_Homepage);

}
%>
