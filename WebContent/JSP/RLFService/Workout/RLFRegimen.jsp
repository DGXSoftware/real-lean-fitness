<!--
GOAL: Manages the User's Programs
-->

<!-- Disable Cache -->
<% response.addHeader("Cache-Control","no-cache"); %> 

<%-- JSP Imports --%>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "dgx.software.com.UtilityPackage.GlobalTools" %>
<%@ page import = "java.text.SimpleDateFormat" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.util.Calendar" %>

<%
// Display the main body if the user is not logged in, else forward the users to the Homepage
if(GlobalTools.isUserCurrentlyLoggedIn(request,response)){
	
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
	
%>

		<%
		// Get the Current Date
		// NOTE: "HH" converts hour in 24 hours format (0-23), day calculation
		Calendar RegimenCalendar = Calendar.getInstance();
		
		// Get Today's Date
		SimpleDateFormat RegimenDateFormat = new SimpleDateFormat("MM/dd/yyyy");
		String TodaysDate = RegimenDateFormat.format(RegimenCalendar.getTime());
		
		// Get Today's Date and Time
		SimpleDateFormat RegimenDateAndTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String TodaysDateAndTime = RegimenDateAndTimeFormat.format(RegimenCalendar.getTime());
		%>


<!-- START HTML RESPONSE -->
<!-- $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ -->

		<?xml version = '1.0'?>
		<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'>
		<html xmlns='http://www.w3.org/1999/xhtml'>
		
		<head>
		
		<!-- Set the Title for the Website Page -->
		<title>Workout Manager</title>
		
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
		
		<script>
		
		//Submits the Request
		function submitProgramCheckpointUpdate(Last_Regimen_Name, Last_Program_ID, Last_Program_ID_Saved_On, Last_Program_ID_Percentage) {

			// Get the Last_Regimen_Name value by using the Dropdown Menu ID
			var DropdownElementID = document.getElementById(Last_Regimen_Name);
			var Last_Regimen_Name = DropdownElementID.options[DropdownElementID.selectedIndex].value;
			
				// Submit via AJAX and pass the request for the current SessionAccountID
		        var jqxhr = $.ajax({
		            type:       "POST",
		            url:        "/ProgramCheckpointServlet",
		            cache:      false,
		            data:       $("#ProgramCheckpointForm").serialize()+"&Account_ID=<%= SessionAccountID %>&Last_Regimen_Name="+Last_Regimen_Name+"&Last_Program_ID="+Last_Program_ID+"&Last_Program_ID_Saved_On="+Last_Program_ID_Saved_On+"&Last_Program_ID_Percentage="+Last_Program_ID_Percentage+"",
		            
		            // Before load, notify the user that the request may take a while 
		            beforeSend: function() {
		            	
		                        },
		                            
		            // If user remains on page for the results, show alert with results
		            success:    function(data, status) {
					
		            // Since the Workout Regimen was chosen successfully
		            // Return the user back to this same page via a Successful Countdown Forward Message
		            var SuccessMessage = "You are now ready to begin your "+Last_Regimen_Name+" workout Regimen. Good Luck!";
		            var SuccessURL = "<%= GlobalTools.GTV_CountdownForwardMessage %>" + "?SuccessMessage=" + SuccessMessage + "&RedirectURL=" + "<%= GlobalTools.GTV_RLFService_RLFRegimen %>";
		                
		            // Forward to the Success URL
		            window.location = SuccessURL;
		                
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
		<h1>Workout Manager - <%= TodaysDate %></h1>
		
		<p align='left'>&#160;</p>
		
<%
	
	// Get the following Initial Regimen values from the RLF_Programs_CheckPoints Table
	//Last_Regimen_Name varchar(64),
	//Last_Program_ID INT,
	//Last_Program_ID_Saved_On DATETIME,
	String Last_Regimen_Name = GlobalTools.getSingleTableCellData(request, response,"RLF_Programs_CheckPoints","Last_Regimen_Name", "Account_ID", SessionAccountID);
	String Last_Program_ID = GlobalTools.getSingleTableCellData(request, response,"RLF_Programs_CheckPoints","Last_Program_ID", "Account_ID", SessionAccountID);
	String Last_Program_ID_Saved_On = GlobalTools.getSingleTableCellData(request, response,"RLF_Programs_CheckPoints","Last_Program_ID_Saved_On", "Account_ID", SessionAccountID);

	// TEST
	//Last_Regimen_Name = "Real Performance";
	//Last_Program_ID = "1";
	//Last_Program_ID_Saved_On = TodaysDateAndTime;
	
	//TO DO
	//1. Have this set the inital Regimen values - (DONE)
	//2. Have the Player record the % in the Database everytime It's updated - (DONE)
	//3. Only move to new Program if Date moved up and previous program % is higher than 0%
	//4. When you move to the new program, record the the previous percentage and Program name a new database (For statistics)
	//5. Create a apage that uses this new table's data to show statistics for all programs
	
	// If any of the Initial Regimen values are null, send the user to an alternative form to set these values
	if( Last_Regimen_Name != null && Last_Program_ID != null && Last_Program_ID_Saved_On != null ){
		
%>
		
		<form action='' method='get' id='RLFRegimenForm' name='RLFRegimenForm'>
		 
<%

//Get the Current Program Percentage
String Last_Program_ID_Percentage = GlobalTools.getSingleTableCellData(request, response,"RLF_Programs_CheckPoints","Last_Program_ID_Percentage", "Account_ID", SessionAccountID);

// PROBLEM
// If we arrive on a new day, but the percentage was 0 and we don't update the sequence
//we can test drive the current sequence, get a percentage and then come back and be promoted

//SOLUTION
// If the percentage is = 0 overwrite the Last_Program_ID_Saved_On with TodaysDateAndTime and write this value to the DB's Last_Program_ID_Saved_On column.
// This will make it so that the new Last_Program_ID_Saved_On date will be the date that the program was actually ran.


//If the date has changed, and if the Last_Program_ID_Percentage is not = 0, then 
if(Last_Program_ID_Percentage.equals("0")){

	//APPLY SOLUTION
	//
	Last_Program_ID_Saved_On = TodaysDateAndTime;
	
}




//Get the Current User's Last_Program_Sequence_Number using the last Program ID
String Last_Program_Sequence_Number = GlobalTools.getSingleTableCellData(request, response,"RLF_Programs_Strategies","Program_Sequence_Number", "Program_ID", Last_Program_ID);

//Check if Last_Program_ID_Saved_On and TodaysDateAndTime are on the same day
if(GlobalTools.isNewDayAfterOldDay(TodaysDateAndTime, Last_Program_ID_Saved_On)){
	// NEW DAY: Update the Last_Program_Sequence_Number

	// Add 1 to the Last_Program_Sequence_Number
	Last_Program_Sequence_Number = "" + (Integer.parseInt(Last_Program_Sequence_Number) + 1);
	
// IF WE ARE PAST THE LAST "Program_Sequence_Number" Possible which is currently 91 , set it back to the starting point
if(Last_Program_Sequence_Number.equals("92")){
	Last_Program_Sequence_Number = "1";
}

}else{
	// SAME DAY OR PREVIOUS DAY: Do Nothing!
	//System.out.println("New Day comes Before or is same as old day!");
}

//Where Regimen and New Program Order column
//Get the Primary and Secondary Program Names
String [] WhereColumnFields = {"Program_Regimen", "Program_Sequence_Number"};
String [] WhereColumnValues = {Last_Regimen_Name, Last_Program_Sequence_Number};
String Primary_Program_Name = GlobalTools.getSingleTableCellDataMultipleWhere(request, response,"RLF_Programs_Strategies","Primary_Program_Name", WhereColumnFields, WhereColumnValues);
String Secondary_Program_Name = GlobalTools.getSingleTableCellDataMultipleWhere(request, response,"RLF_Programs_Strategies","Secondary_Program_Name", WhereColumnFields, WhereColumnValues);

System.out.println("-------------------------------------------");
System.out.println("Last_Regimen_Name = " + Last_Regimen_Name);
System.out.println("Primary_Program_Name = " + Primary_Program_Name);
System.out.println("Secondary_Program_Name = " + Secondary_Program_Name);
System.out.println("-------------------------------------------");

CurrentSession.setAttribute("Primary_Program_Name", Primary_Program_Name);
CurrentSession.setAttribute("Secondary_Program_Name", Secondary_Program_Name);

%>

<%

// Display the % Color in green if the user achieved 100%
String PercentageColor = "red";
if(Last_Program_ID_Percentage.equals("100")){
PercentageColor = "green";
}
%>

		<h4><%= Last_Regimen_Name %></h2>
		<br/>
		<div>
		<p><a href="<%= GlobalTools.GTV_RLFService_RLFPlayer %>"><%= Primary_Program_Name %></a></p>
		<span style="color:<%= PercentageColor %>"><%= Last_Program_ID_Percentage %>% Completed </span>
		<span> - Click to begin your daily workout for the day.</span>
		</div>

		<br/>
		<br/>
		<br/>
		<br/>
		
		</form>
		
	<%
	}else{

		//If the Last_Program_ID is null, then set it to the default value of 1
		//if(Last_Program_ID == null){ Last_Program_ID = "1"; }
		
		//If the Last_Program_ID is null, then set it to the default value of today's date and time
		//if(Last_Program_ID_Saved_On == null){ Last_Program_ID_Saved_On = TodaysDateAndTime; }

		
%>

	<!-- Form to set initial Regimen Data -->
	<form action='' method='get' id='RLFRegimenSetupForm' name='RLFRegimenSetupForm'>
	 
	 	<!-- Last_Regimen_Name varchar(64) -->
		<div id="UserRegimenChoiceDiv">
		<label id="UserRegimenChoiceLabel" for="UserRegimenChoice">
		<p> Choose a Workout Regimen : </p>
		<select id="UserRegimenChoice" name="UserRegimenChoice" onChange="isValidDropDownField('UserRegimenChoice','UserRegimenChoiceIcon','',false);" >
        <option value="" disabled selected style="display:none">Workout Regimen</option>
        <option value="Real Lean">Real Lean</option>
        <option value="Real Performance">Real Performance</option>
        </select>
        <img id="UserRegimenChoiceIcon" src="/Images/Icons/Valid/Valid(16x16).png" style="visibility:hidden;" />
        </label>
        </div>
	
	<script>
      // Use JQuery to execute JavaScript code as soon as the page loads
      $(document).ready(function(){
    	  
        // As soon as the Role is changed, Remove the Index Drop Down Item which has an empty value of ('').
        $('#UserRegimenChoice').on('change', function() {
        	$("#UserRegimenChoice option[value='']").remove();
        });
        
      });
</script>
	
    <br />
    <br />
    <br />
	
    <!-- Change Username Button -->
	<input type='button' id='RLFRegimenSetupButton' name='RLFRegimenSetupButton' value='Submit' onClick="submitProgramCheckpointUpdate('UserRegimenChoice','1','NOW()','0');" />
    

	</form>
	
	<%
	}
	%>
		
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
