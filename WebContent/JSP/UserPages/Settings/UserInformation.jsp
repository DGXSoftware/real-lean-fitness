<!--
GOAL: Allows the user to edit their Information.

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
<%@ page import = "java.util.ArrayList" %>
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
	
/* *********************************************************************************** */
/* START RETRIEVAL OF SAVED INFORMATION */
/* *********************************************************************************** */
		
        // Define the Table and Columns for this Page
    	String RLF_User_Information_Table = "RLF_User_Information";
    	String [] RLF_User_Information_Table_Columns = {"Location_Address","Location_City","Location_State","Location_ZipCode","Location_Country"};
        
		// Retrieve the SaveInformationButton Value
		String SaveInformationButton = request.getParameter("SaveInformationButton");
        
		// Check if the Servlet was accessed through the button press of SaveInformationButton
		// If the Servlet was accessed through the Button, Update the Values
	    if(SaveInformationButton != null){
	    	
	    	//Execute a Select SQL Query To get the RLF_Accounts_Table_Columns
	    	ArrayList <String> RLF_User_Information_Table_Values = new ArrayList<String>();
	    	String UpdateUserInformationQuery = "UPDATE "+RLF_User_Information_Table+" SET";
	    	
	    	for(int i = 0 ; i < RLF_User_Information_Table_Columns.length; i++){

	    		// Retrieve the Query String Data and Save it
	    		RLF_User_Information_Table_Values.add(request.getParameter(RLF_User_Information_Table_Columns[i]));
	    		
	    		// Set Empty Value for Null Strings
	    		if(RLF_User_Information_Table_Values.get(i) == null){RLF_User_Information_Table_Values.set(i, "");}
	    	
	    		UpdateUserInformationQuery = UpdateUserInformationQuery.concat(" "+RLF_User_Information_Table_Columns[i]+"=\""+RLF_User_Information_Table_Values.get(i)+"\"");
				
	    		// Add a Comma to all Query "SET" Field/Values except for the last one
				if(i < (RLF_User_Information_Table_Columns.length-1) ){
					UpdateUserInformationQuery = UpdateUserInformationQuery.concat(",");
				}

	    	}

			UpdateUserInformationQuery = UpdateUserInformationQuery.concat(" WHERE Account_ID=\""+SessionAccountID+"\"");
			UpdateUserInformationQuery = UpdateUserInformationQuery.concat(";");
			UpdateUserInformationQuery = UpdateUserInformationQuery.concat("");

			// Write the HTML Notification Response
			String ProfileUpdateMessage = "Profile information successfully updated!";
			GlobalTools.writeHTMLNotificationResponse(request, response, GlobalTools.GTV_Settings_UserInformation, ProfileUpdateMessage);
			
			// Update the User Information
			SQLStatement.executeUpdate(UpdateUserInformationQuery);
		
	    }

/* *********************************************************************************** */
/* END RETRIEVAL OF SAVED INFORMATION */
/* *********************************************************************************** */

%>


<!-- START HTML RESPONSE -->
<!-- $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ -->

		<?xml version = '1.0'?>
		<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'>
		<html xmlns='http://www.w3.org/1999/xhtml'>
		
		<head>
		
		<!-- Set the Title for the Website Page -->
		<title>Information</title>
		
		<!-- Set the Favicon for the Website page -->
		<link rel='Shortcut Icon' type='image/ico' href='/Images/favicon.ico'/>
		
		<!-- Set the Character Encoding for the Website page -->
		<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1' />
		
		<!-- Include the Stylesheet Files -->
		<link rel='stylesheet' type='text/css' href='/CSS/RLFStyle.css' />
		
		<!-- Include the JavaScript Files -->
		<script type='text/javascript' src='/JavaScript/Validation/GlobalFieldValidation.js' > </script>
		
		</head>
		
		<body>
		
        <%
        // Display a Fixed DIV that reminds non activated users how to activate.
        GlobalTools.displayActivationMessage(out, SessionUsername, SessionIsActivated);
	    %>
		
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
		<h1>Edit Profile Information</h1>
		
		<p align='left'>&#160;</p>
		
		<form action='' method='post' id='UserInformationForm' name='UserInformationForm'>
		
		<!-- START DYNAMIC HTML -->
	    <%
        
	    // Get the "RLF_User_Information" Table Data and generate the dynamic Fields
	    ArrayList<ArrayList<String>> UserInformationFieldValuePair = GlobalTools.getTableColumnAndValuePairViaAccountID(request, response, RLF_User_Information_Table, SessionAccountID, RLF_User_Information_Table_Columns);

	    for(int i = 0 ; i < UserInformationFieldValuePair.get(0).size(); i++){
	    
    		// Get the Current SQL Result Values
    		String CurrentColumnName = UserInformationFieldValuePair.get(0).get(i);
    		String UserDisplayCurrentColumnName = CurrentColumnName.replaceAll("_", " ");
    		String CurrentValue = UserInformationFieldValuePair.get(1).get(i);
	    	
	    %>	
				<p> <%=UserDisplayCurrentColumnName%>: </p> <input type='text' id='<%=CurrentColumnName%>' name='<%=CurrentColumnName%>' title='<%=UserDisplayCurrentColumnName%>' size='32' value='<%=CurrentValue%>' />
		<%
		}
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
	}catch (Exception sqlException) {
	sqlException.printStackTrace();		
	}
%>

<%
}else{

	// The current user does NOT have a session, therefore redirect them to the Homepage
	response.sendRedirect(GlobalTools.GTV_Homepage);

}
%>