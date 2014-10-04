<!--
GOAL: Allows the user to edit their Information.

PROPERTIES: Front-End Work / Back-End Work
1. User Page Display (HTML/CSS)
2. User Page Interactions (HTML/JavaScript)
3. User SQL Database Access (SQL)
*/
-->

<%-- JSP Imports --%>
<%@ page import = "java.io.PrintWriter" %>
<%@ page import = "java.io.PrintWriter" %>
<%@ page import = "java.io.IOException" %>
<%@ page import = "java.sql.Connection" %>
<%@ page import = "java.sql.DriverManager" %>
<%@ page import = "java.sql.ResultSetMetaData" %>
<%@ page import = "java.sql.Statement" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import = "java.sql.SQLException" %>

<%@ page import = "javax.servlet.ServletConfig" %>
<%@ page import = "javax.servlet.ServletException" %>
<%@ page import = "javax.servlet.UnavailableException" %>
<%@ page import = "javax.servlet.http.HttpServlet" %>
<%@ page import = "javax.servlet.http.HttpServletRequest" %>
<%@ page import = "javax.servlet.http.HttpServletResponse" %>
<%@ page import = "javax.servlet.http.HttpSession" %>

<%@ page import = "dgx.software.com.UtilityPackage.GlobalMethods" %>

<%

// Assume we don't have a Session Account ID
String SessionAccountID = "";

// Returns null if no session already exists 
HttpSession CurrentSession =  request.getSession(false);

// If we have a session attempt to retrieve the SessionAccountID
if (CurrentSession != null) {SessionAccountID = (String) CurrentSession.getAttribute("AccountID");}

// If attempts to retrieve the SessionAccountID returned null, make it an Empty String Object for operations
if (SessionAccountID == null) {SessionAccountID = "";}

// Display the main body if the user is not logged in, else forward the users to the Homepage
if(!(SessionAccountID.equals(""))){
	
	
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

	
	// Retrieve the SaveInformationButton Value
	String SaveInformationButton = request.getParameter("SaveInformationButton");

	// Check if the Servlet was accessed through the button press of SaveInformationButton
	// If the Servlet was accessed through the Button, Update the Values
    if(SaveInformationButton != null){
    	
    	// Retrieve the Query String Data
		String First_Name = request.getParameter("First_Name");
		String Middle_Name = request.getParameter("Middle_Name");
		String Last_Name = request.getParameter("Last_Name");
		String Location_Address = request.getParameter("Location_Address");
		String Location_City = request.getParameter("Location_City");
		String Location_State = request.getParameter("Location_State");
		String Location_ZipCode = request.getParameter("Location_ZipCode");
		String Location_Country = request.getParameter("Location_Country");
		
		// Set Empty Spaces to Null Strings
		if(First_Name == null)First_Name = "";
		if(Middle_Name == null)Middle_Name = "";
		if(Last_Name == null)Last_Name = "";
		if(Location_Address == null)Location_Address = "";
		if(Location_City == null)Location_City = "";
		if(Location_State == null)Location_State = "";
		if(Location_ZipCode == null)Location_ZipCode = "";
		if(Location_Country == null)Location_Country = "";
		
		// Create the UpdateUserInformationQuery
		String UpdateUserInformationQuery = "UPDATE RLF_User_Information SET " +
		"First_Name=\""+First_Name+"\"," +
		"Middle_Name=\""+Middle_Name+"\"," +
		"Last_Name=\""+Last_Name+"\"," +
		"Location_Address=\""+Location_Address+"\"," +
		"Location_City=\""+Location_City+"\"," +
		"Location_State=\""+Location_State+"\"," +
		"Location_ZipCode=\""+Location_ZipCode+"\"," +
		"Location_Country=\""+Location_Country+"\" " +
		"WHERE Account_ID=\""+SessionAccountID+"\"" +
		";" +
		"";
    	
		// Write the HTML Notification Response
		String ProfileUpdateMessage = "Profile information successfully updated!";
		GlobalMethods.writeHTMLNotificationResponse(request, response, "/JSP/UserPages/UserInformation.jsp", ProfileUpdateMessage);
		
		// Update the User Information
		SQLStatement.executeUpdate(UpdateUserInformationQuery);
	
    }

		// attempt to process a vote and display current results
		try {
		
		// Variables for Account session information
		//String SessionAccountID = (String) CurrentSession.getAttribute("AccountID");
		String SessionUsername = (String) CurrentSession.getAttribute("Username");
		String SessionFirstName = (String) CurrentSession.getAttribute("FirstName");
		String SessionIsActivated = (String) CurrentSession.getAttribute("IsActivated");
			
		// SQL Query
		String AccountSQLQuery = "SELECT * FROM RLF_User_Information WHERE Account_ID="+SessionAccountID+";";
		
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
			GlobalMethods.writeForwardHTMLErrorResponse(request, response, "/", UnknownErrorMessage);
			
		}
	
%>


<!-- START HTML RESPONSE -->
<!-- $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ -->

		<?xml version = '1.0'?>
		<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'>
		<html xmlns='http://www.w3.org/1999/xhtml'>
		
		<head>
		
		<!-- Set the Title for the Website Page -->
		<title>User Profile Servlet</title>
		
		<!-- Set the Favicon for the Website page -->
		<link rel='Shortcut Icon' type='image/ico' href='/Images/favicon.ico'/>
		
		<!-- Set the Character Encoding for the Website page -->
		<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1' />
		
		<!-- Include the Stylesheet Files -->
		<link rel='stylesheet' type='text/css' href='/CSS/RLFStyle.css' />
		
		<!-- Include the JavaScript Files -->
		<script language='javascript' type='text/javascript' src='/JavaScript/Validation/UserInformationPageValidation.js' > </script>
		
		<style>
		
		.FixedMessage {
		top: auto;
		left: auto;
		max-height: 100%;
		width: 100%;
		overflow-y: auto;
		color: black;
		background-color: white;
		border: solid 1px red;
		padding: 2px 5px;
		margin: auto;
		text-align: center;
		position: relative;
		}
		
		</style>
		
		</head>
		
		<body>
		
       <%
	    // If SessionIsActivated is 'N' for No, then show the message below.
	    if(SessionIsActivated.equals("N")){
	    
	    // If the user is Not activated, point them to the Account Activation Site
	    String AccountActivationURL = "/JSP/PayPal/PayPalRegistrationSubmit.jsp" + "?" + "RegistrationUsername=" + SessionUsername;
        %>
	    <div class='FixedMessage'>
	    <p>This account is not activated. Please <a href='<%= AccountActivationURL %>'>Click here</a> to activate your account.</p>
	    </div>
	    <%
	    }
		%>
		
		<div id='container'>
		<div id='main'>
		<div id='header'></div>
		<div id='nav'>
		<ul>
		<!-- START DYNAMIC HTML -->
		<li><a href='/JSP/UserPages/UserProfile.jsp'><%= SessionFirstName %></a></li>
		<!-- END DYNAMIC HTML -->
		<li><a href='#'></a></li>
		<li><a href='#'></a></li>
		<li><a href='#'></a></li>
		<li><a href='/JSP/UserPages/UserInformation.jsp'>Edit Profile</a></li>
		<li><a href='/LogOutServlet'>Log Out</a></li>
		</ul>
		</div>
		<div id='content'>
		<div id='left'>
		<div class='post'>
		<h1>Edit Profile Information</h1>
		
		<p align='left'>&#160;</p>
		
		<form action='' method='post' id='UserInformationForm' name='UserInformationForm'>
		
		<!-- START DYNAMIC HTML -->
	    <%
		try {
		
			// Go over the Rows
			while (AccountSQLQueryOutput.next()) {
				
			// Go over the Columns
			// NOTE : i = 2 because we want to skip the first item (Account_ID)
			for(int i = 2 ; i < SQLQueryOutputMetaData.getColumnCount() + 1 ; i++){
				
				String ColumnName = SQLQueryOutputMetaData.getColumnName(i);
				String DisplayCoumnName = ColumnName.replaceAll("_", " ");
				String CurrentValue = AccountSQLQueryOutput.getString(i);
				
				// If the current value is empty set it as N/A
				//if(CurrentValue.equals("")){CurrentValue = "N/A";};
			%>	
				<p> <%= DisplayCoumnName %>: </p> <input type='text' id='<%= ColumnName %>' name='<%= ColumnName %>' title='<%= DisplayCoumnName %>' size='32' value='<%= CurrentValue %>' />
			<%	
				
			}
			
			}
		} catch (SQLException e1) {e1.printStackTrace();}
		%>
		
		<!-- END DYNAMIC HTML -->
		
		<br />
		<br />
		<br />
		 
		<input type='submit' id='SaveInformationButton' name='SaveInformationButton' value = 'Save Information' />
		 
		</form>
		
		</div>
		
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

		// Close the ResultSet
		try {AccountSQLQueryOutput.close();} catch (SQLException e) {e.printStackTrace();}
		
	}else {
		
		// Close the ResultSet
		try {AccountSQLQueryOutput.close();} catch (SQLException e) {e.printStackTrace();}
		
			// Write the HTML Error Response	
			String NoProfileErrorMessage = "Unable to retrieve your profile homepage! Please Try again.";
			GlobalMethods.writeForwardHTMLErrorResponse(request, response, "/", NoProfileErrorMessage);
					
		}
		
		} // end try
		
		// if an exception occurs, return the error page
		catch (Exception sqlException) {
			sqlException.printStackTrace();		
			
			// Respond with an error message
			String UnknownErrorMessage = "Unknown Database error occurred. Please Try again later.";
			GlobalMethods.writeForwardHTMLErrorResponse(request, response, "/", UnknownErrorMessage);
			
		} // end catch

%>

<%
}else{

	// The current user does NOT have a session, therefore redirect them to the Homepage
	response.sendRedirect("/");

}
%>