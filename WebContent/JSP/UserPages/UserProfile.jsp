<!--
GOAL: User Profile Homepage.

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
	
	
/* *********************************************************************************** */
/* START JSP DATABASE CONNECTION */
/* *********************************************************************************** */

	Class.forName(application.getInitParameter("DriverName"));

	final Connection SQLConnection = DriverManager.getConnection(
	application.getInitParameter("DatabaseURL"), 
	application.getInitParameter("DatabaseUser"), 
	application.getInitParameter("DatabasePassword"));

	final Statement SQLStatement = SQLConnection.createStatement();
		
/* *********************************************************************************** */
/* END JSP DATABASE CONNECTION */
/* *********************************************************************************** */	

	// attempt to process a vote and display current results
	try {
	
	// Returns null if no session already exists 
	HttpSession CurrentSession =  request.getSession(false);
		
	// Variables for Account session information
	String SessionAccountID = (String) CurrentSession.getAttribute("AccountID");
	String SessionUsername = (String) CurrentSession.getAttribute("Username");
	String SessionFirstName = (String) CurrentSession.getAttribute("FirstName");
	String SessionIsActivated = (String) CurrentSession.getAttribute("IsActivated");
	String SessionIsVerified = (String) CurrentSession.getAttribute("IsVerified");
		
	// SQL Query
	String AccountSQLQuery = "SELECT * FROM RLF_Accounts WHERE Account_ID="+SessionAccountID+";";

	// Get the SQLQueryOutput
	ResultSet AccountSQLQueryOutput = SQLStatement.executeQuery(AccountSQLQuery);
	
	// If we DO NOT Have an Empty Result Set, Work with it and send a successful HTML Response
	if(AccountSQLQueryOutput.next()){

	// Reset the Pointer changed by the if statement above
	AccountSQLQueryOutput.beforeFirst();
	
	// Records the ResultSetMetaData
	ResultSetMetaData SQLQueryOutputMetaData = null;
	
	try {
		
		// Get the ResultSetMetaData
	    SQLQueryOutputMetaData = AccountSQLQueryOutput.getMetaData();
		
	} catch (SQLException sqlException) {
		sqlException.printStackTrace();		
		
		// Respond with an error message
		String UnknownErrorMessage = "Unknown Database error occurred. Please Try again later.";
		GlobalTools.writeForwardHTMLErrorResponse(request, response, GlobalTools.GTV_Homepage, UnknownErrorMessage);
		
	}
%>


<!-- START HTML RESPONSE  -->
<!-- $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ -->

		<?xml version = '1.0'?>
		<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'>
		<html xmlns='http://www.w3.org/1999/xhtml'>
		
		<head>
		
		<!-- Set the Title for the Website Page -->
		<title>Profile</title>
		
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
		<h1>Profile Information</h1>
		
		<p align='left'>&#160;</p>
		
		<!-- START DYNAMIC USER INFORMATION HTML -->
		
		<%
					try {	
					
					// Go over the Rows
					while (AccountSQLQueryOutput.next()) {
						
					// Go over the Columns
					for(int i = 1 ; i < SQLQueryOutputMetaData.getColumnCount() + 1 ; i++){
						
						String ColumnName = SQLQueryOutputMetaData.getColumnName(i);
						String CurrentValue = AccountSQLQueryOutput.getString(i);
						
						// If the current value is empty set it as N/A
						if(CurrentValue.equals("")){CurrentValue = "N/A";};
				%>		
				<p><b><%=ColumnName%> = </b><span><%=CurrentValue%></span></p>
		 <%
		 	}
		 	
		 	}
		 		} catch (SQLException e1) {e1.printStackTrace();}
		 %>
		<!-- END DYNAMIC USER INFORMATION HTML -->
		
		</div>
		
		</div>
		
		
		<div id='right'>
		<h1 class='Center_Text'>Profile Picture</h1>
		
		<!-- START DYNAMIC IMAGE UPLOAD HTML -->
		<%
			    // SQL Query
				String ProfileImageSQLQuery = "SELECT * FROM RLF_Images WHERE ACCOUNT_ID = '"+SessionAccountID+"' AND Primary_Image= '1';";

				// Get the SQLQueryOutput
				ResultSet ProfileImageSQLQueryOutput = SQLStatement.executeQuery(ProfileImageSQLQuery);
				
				// If we DO NOT Have an Empty Result Set, Work with it.
				if(ProfileImageSQLQueryOutput.next()){
				
				// Set the User Image retrieved from the Database
				String UserProfileImageLocation = ProfileImageSQLQueryOutput.getString("Image_Location");
		%>
				<img border='0' src='<%=UserProfileImageLocation%>' class='Center_Image' id='UserProfilePrimaryPicture' name='UserProfilePrimaryPicture' alt='Profile Picture' width='230' height='230' />
		<%
			    // Close the ResultSet
				try {AccountSQLQueryOutput.close();} catch (SQLException e) {e.printStackTrace();}
			
				
				}else{
				
				// Set the Default Profile Image
				String DefaultProfileImageLocation = "/Images/Default Profile Picture.jpg";
		%>
		<img border='0' src='<%=DefaultProfileImageLocation%>' class='Center_Image' id='UserProfilePrimaryPicture' name='UserProfilePrimaryPicture' alt='Profile Picture' width='230' height='230' />
		<%
			    // Close the ResultSet
				try {AccountSQLQueryOutput.close();} catch (SQLException e) {e.printStackTrace();}
			
				}
		%>

		<!-- END DYNAMIC IMAGE UPLOAD HTML -->
		
		<form action='/ImageFileUploadServlet' enctype='multipart/form-data' method='post'> 
		
		<br/>
		<input type='file' id='UserProfilePictureBrowse' name='UserProfilePictureBrowse' />
		<br/>
		
		<input type='submit' id='UploadUserProfilePictureButton' name='UploadUserProfilePictureButton' value = 'Upload Profile Picture' />
		
		</form>
		
		</div>

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


<!-- $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ -->
<!-- END HTML RESPONSE  -->

<%
	// Close the ResultSet
		try {AccountSQLQueryOutput.close();} catch (SQLException e) {e.printStackTrace();}
		
	}else {
		
		// Close the ResultSet
		try {AccountSQLQueryOutput.close();} catch (SQLException e) {e.printStackTrace();}
		
	// Write the HTML Error Response	
	String NoProfileErrorMessage = "Unable to retrieve your profile homepage! Please Try again.";
	GlobalTools.writeForwardHTMLErrorResponse(request, response, GlobalTools.GTV_Homepage, NoProfileErrorMessage);
			
		}
		
		} // end try
		
		// if an exception occurs, return the error page
		catch (Exception sqlException) {
	sqlException.printStackTrace();		
	
	// Respond with an error message
	String UnknownErrorMessage = "Unknown Database error occurred. Please Try again later.";
	GlobalTools.writeForwardHTMLErrorResponse(request, response, GlobalTools.GTV_Homepage, UnknownErrorMessage);
	
		} // end catch
%>

<%
}else{

	// The current user does NOT have a session, therefore redirect them to the Homepage
	response.sendRedirect(GlobalTools.GTV_Homepage);

}
%>