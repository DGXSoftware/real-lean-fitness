<!--
GOAL: Allows the user to edit their Account Information.

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
		String SessionIsVerified = (String) CurrentSession.getAttribute("IsVerified");
	
/* *********************************************************************************** */
/* START RETRIEVAL OF SAVED INFORMATION */
/* *********************************************************************************** */
		
        // Define the Table and Columns for this Page
    	String RLF_Accounts_Table = "RLF_Accounts";
    	String [] RLF_Accounts_Table_Columns = {"First_Name","Last_Name"};

		// Retrieve the SaveInformationButton Value
		String SaveInformationButton = request.getParameter("FormWasSubmitted");

		// Check if the Servlet was accessed through the button press of SaveInformationButton
		// If the Servlet was accessed through the Button, Update the Values
	    if(SaveInformationButton != null){
	    	
	    	//Execute a Select SQL Query To get the RLF_Accounts_Table_Columns
	    	ArrayList <String> RLF_Accounts_Table_Values = new ArrayList<String>();
	    	String UpdateUserAccountQuery = "UPDATE "+RLF_Accounts_Table+" SET";
	    	
	    	for(int i = 0 ; i < RLF_Accounts_Table_Columns.length; i++){

	    		// Retrieve the Query String Data and Save it
	    		RLF_Accounts_Table_Values.add(request.getParameter(RLF_Accounts_Table_Columns[i]));
	    		
	    		// Set Empty Value for Null Strings
	    		if(RLF_Accounts_Table_Values.get(i) == null){RLF_Accounts_Table_Values.set(i, "");}
	    	
	    		UpdateUserAccountQuery = UpdateUserAccountQuery.concat(" "+RLF_Accounts_Table_Columns[i]+"=\""+RLF_Accounts_Table_Values.get(i)+"\"");
				
	    		// Add a Comma to all Query "SET" Field/Values except for the last one
				if(i < (RLF_Accounts_Table_Columns.length-1) ){
					UpdateUserAccountQuery = UpdateUserAccountQuery.concat(",");
				}

	    	}

	    	UpdateUserAccountQuery = UpdateUserAccountQuery.concat(" WHERE Account_ID=\""+SessionAccountID+"\"");
			UpdateUserAccountQuery = UpdateUserAccountQuery.concat(";");
			UpdateUserAccountQuery = UpdateUserAccountQuery.concat("");

			// Write the HTML Notification Response
			String ProfileUpdateMessage = "Profile Account information successfully updated!";
			GlobalTools.writeHTMLNotificationResponse(request, response, GlobalTools.GTV_Settings_UserAccount, ProfileUpdateMessage);
			
			// Update the User Account Information
			SQLStatement.executeUpdate(UpdateUserAccountQuery);
		
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
		<title>Account</title>
		
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
		// Submits the Request
        function submitForm() {
		
			// Mark the Form as Submitted
        	document.getElementById('FormWasSubmitted').value = 'YES';
			
        	// Submit the Form
        	var FormID = document.getElementById('UserAccountForm');
        	FormID.submit();
		
		}
	    </script>
		
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
		<h1>Edit Account Information</h1>
		
		<p align='left'>&#160;</p>
		
		<form action='' method='post' id='UserAccountForm' name='UserAccountForm'>
		
		<!-- START DYNAMIC HTML -->
	    <%
	    
	    // Get the "RLF_Accounts" Table Data and generate the dynamic Fields
	    ArrayList<ArrayList<String>> UserAccountsFieldValuePair = GlobalTools.getTableColumnAndValuePairViaAccountID(request, response, RLF_Accounts_Table, SessionAccountID, RLF_Accounts_Table_Columns);

	    for(int i = 0 ; i < UserAccountsFieldValuePair.get(0).size(); i++){
	    
    		// Get the Current SQL Result Values
    		String CurrentColumnName = UserAccountsFieldValuePair.get(0).get(i);
    		String UserDisplayCurrentColumnName = CurrentColumnName.replaceAll("_", " ");
    		String CurrentValue = UserAccountsFieldValuePair.get(1).get(i);
	    	
	    %>	
				<p> <%=UserDisplayCurrentColumnName%>: </p> <input type='text' id='<%=CurrentColumnName%>' name='<%=CurrentColumnName%>' title='<%=UserDisplayCurrentColumnName%>' size='32' value='<%=CurrentValue%>' />
		<%
		}
		%>
		<!-- END DYNAMIC HTML -->
		
		<br />
		<br />
		<br />
		
		<input type='hidden' name='FormWasSubmitted' id='FormWasSubmitted' />
		<input type='button' id='SaveInformationButton' name='SaveInformationButton' value = 'Update Information' onClick='submitForm();'/>
		 
		</form>
		
		</div>
		
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