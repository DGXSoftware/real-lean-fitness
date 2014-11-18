<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%-- JSP Imports --%>
<%@ page import = "dgx.software.com.UtilityPackage.GlobalTools" %>

<%

String SessionAccountID = "1000000005";

// String SessionAccountID, String New_PID
//GlobalTools.setProgramCheckpointTableCellData(request, response, SessionAccountID,"90");



%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update Checkpoint</title>

		<!-- Include the jQuery Files -->
		<script type='text/javascript' src="/JavaScript/JQuery/jquery.js"></script>
		<!--
		EXTERNAL jQuery Import
		<script type = "text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
		-->

<script>

//Submits the Request
function submitProgramCheckpointUpdate(New_PID_Value) {
	
		// Submit via AJAX and pass the current SessionAccountID and New_PID_Value
        var jqxhr = $.ajax({
            type:       "POST",
            url:        "/ProgramCheckpointServlet",
            cache:      false,
            data:       $("#ProgramCheckpointForm").serialize()+"&SessionAccountID=<%= SessionAccountID %>&New_PID="+New_PID_Value+"",
                
            // Before load, notify the user that the request may take a while 
            beforeSend: function() {
            	
                        },
                            
            // If user remains on page for the results, show alert with results
            success:    function(data, status) {

            	alert("GOOD");
            	
                     },
                     
           // If there is an error and the user hasn't yet closed the
           // browser, display the message. Otherwise it will come in the email
            error:      function(xhr, textStatus, thrownError) {

            // Stop further processing
            return false;
            
                        }
                });

    }


</script>

</head>
<body>

		<form id='ProgramCheckpointForm' name='ProgramCheckpointForm' method='post'>
        
        <br />
		<br />
		<br />
        
        <!-- Pass the Target AccountID with the Form Submit
        <input type="hidden" name="SessionAccountID" value="<%= SessionAccountID %>" />
        -->
        
        <!-- Pass the Target AccountID with the Form Submit
        <input type="hidden" name="New_PID" id="New_PID" value="" />
        -->
        
        <!-- Change Password Button -->
		<input type='button' id='ChangePasswordButton' name='ChangePasswordButton' value='Submit It' 
		onClick="submitProgramCheckpointUpdate(23);" />
        
		</form>

</body>
</html>