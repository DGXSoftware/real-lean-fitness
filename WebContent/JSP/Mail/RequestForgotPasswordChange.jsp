<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%-- JSP Imports --%>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "dgx.software.com.UtilityPackage.MailTemplate" %>
<%@ page import = "dgx.software.com.UtilityPackage.GlobalTools" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Change Forgotten Password</title>
</head>

<body>

<h1>Change Forgotten Password</h1>


		<!-- START DYNAMIC HTML -->
	    <%
	    
        // Define the Table and Columns for this Page
    	String RLF_Accounts_Table = "RLF_Accounts";
    	String [] RLF_Accounts_Table_Columns = {"First_Name","Last_Name"};

    	// Get the User from the E-Mail !!!!!!!!!!!!!!!!!!!!!!!!
    	String SessionEMail = "";

	    
	    // String SpacedCurrentColumnName = CurrentColumnName.replaceAll("_", " ");
	    
	    // Get the "RLF_Accounts" Table Data and generate the dynamic Fields
	    ArrayList<ArrayList<String>> UserAccountsFieldValuePair = GlobalTools.getTableColumnAndValuePairViaSelectSQLQuery(request, response, RLF_Accounts_Table, SessionEMail, RLF_Accounts_Table_Columns);

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
        
</body>
</html>