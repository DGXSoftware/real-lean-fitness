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

		// SQL Query
		String AccountSQLQuery = "SELECT * FROM RLF_Accounts;";
		
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

		}
%>


<!-- START HTML RESPONSE -->
<!-- $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ -->

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
				<p> <%=DisplayCoumnName%>: </p> <input type='text' id='<%=ColumnName%>' name='<%=ColumnName%>' title='<%=DisplayCoumnName%>' size='32' value='<%=CurrentValue%>' />
			<%
				}
				
				}
					} catch (SQLException e1) {e1.printStackTrace();}
			%>
		
		<!-- END DYNAMIC HTML -->

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
