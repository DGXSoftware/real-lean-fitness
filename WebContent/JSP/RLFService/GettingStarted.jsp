<!--
GOAL: Instructs the user on getting started with the application.

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
		<title>Getting Started</title>
		
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
		<!-- <script type='text/javascript' src='/JavaScript/FieldConvenience.js' > </script> -->
		
		</head>
		
		<body>

        <%
        // Display a Fixed DIV that reminds non activated users how to activate and how to verify their account.
        GlobalTools.displayUserStatusMessage(out, SessionUsername, SessionIsActivated, SessionIsVerified);
	    %>

		<div id='container'>
		<div id='main'>
		<div id='header'></div>
		<div id='nav'>
		<ul>
		<%
		// Print the Logged In Menu Items
		GlobalTools.printPageMenuItems(out,"LoggedIn",SessionFirstName);
		%>
		</ul>
		</div>
		<div id='content'>
		<div id='left'>
		<div class='post'>
		<h1>Getting Started</h1>
		
		<p align='left'>&#160;</p>
		
		<form action='' method='post' id='UserInformationForm' name='UserInformationForm'>
		 
		<p><b><a href="<%= GlobalTools.GTV_RLFService_RLFRegimen %>">Step 1: Visit the Workout Manager</a></b><span> - Choose between increasing performance or getting lean.</span></p>
		<p><b><a href="<%= GlobalTools.GTV_RLFService_RLFMealManager%>">Step 2: Learn about your Meal Plan</a></b><span> - Find out about the right stuff to be eating.</span></p>
		<p><b><a href="<%= GlobalTools.GTV_RLFService_RLFWorkoutStatusReport %>">Step 3: Check your progress</a></b><span> - Learn about all your progress so far.</span></p>

		</form>
		
		</div>
		
		</div>
		
		<!--
		<div id='right'>
		<h1 class='Center_Text'>Profile Picture</h1>
		<p><b>RIGHT SIDE DISABLED</b></p>
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