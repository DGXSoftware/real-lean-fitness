<!--
GOAL: Manages the User's Programs
-->

<!-- Disable Cache -->
<% response.addHeader("Cache-Control","no-cache"); %> 

<%-- JSP Imports --%>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "dgx.software.com.UtilityPackage.GlobalTools" %>

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
		
		function submitForm(){
			
			alert("GO!");
			
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
		<h1>Workout Manager</h1>
		
		<p align='left'>&#160;</p>
		
		<form action='' method='get' id='RLFRegimenForm' name='RLFRegimenForm'>
		 
<%

// TO DO
//1. Read Last Program ID
//2. If Null or Empty set it to assign the default value of 1
//3. If Value is found figure out if the day has changed, If so add 1 to the ID, If not leave alone
//3. Get and Pass the Program Name of the Last Program ID from the DB to the player
//4. After Session check, check If the Primary_Program_Name is NULL
// If It's Null  Do the Database Work and set the Primary_Program_Name
// If It's Not Null, check if the Day has changed
// If the Day has changed then do the DB Work to get the  to set Primary_Program_Name
// If the day has not changed then don't do anything

/*
//Get the Current User's Last_Exercise_ID if any
String Last_Program_ID = GlobalTools.getSingleTableCellData(request, response,"RLF_Programs_CheckPoints","Last_Program_ID", "Account_ID", SessionAccountID);


//Get the CompleteProgramNameFieldValuePair
String CompleteProgramNameTable = "RLF_Programs_Strategies";
String [] CompleteProgramNameColumns = {"Program_ID","Program_Regimen","Primary_Program_Name","Secondary_Program_Name"};
String CompleteProgramColumnName = "Program_ID"; 
String CompleteProgramColumnValue = Last_Program_ID;
ArrayList<ArrayList<String>> CompleteProgramNameFieldValuePair = GlobalTools.getTableColumnAndValuePairData(request, response, CompleteProgramNameTable, CompleteProgramColumnName, CompleteProgramColumnValue, CompleteProgramNameColumns);


//Declare the ExerciseID IndexArrayList
ArrayList<String> ExerciseIDIndexArrayList = new ArrayList<String>();

//Generate all the JavaScript Arrays and the ExerciseID IndexArrayList
for(int i = 0 ; i < CompleteProgramNameFieldValuePair.get(0).size(); i++){
  
	// Get the Current SQL Result Values
	String CurrentColumnName = CompleteProgramNameFieldValuePair.get(0).get(i);
	//String UserDisplayCurrentColumnName = CurrentColumnName.replaceAll("_", " ");
	String CurrentValue = CompleteProgramNameFieldValuePair.get(1).get(i);
	
	// Generate the JS_Exercise_ID_Array
	if(CurrentColumnName.equals("Primary_Program_Name")){
		ExerciseIDIndexArrayList.add(CurrentValue);
		%>
		<script>
		JS_Exercise_ID_Array.push("<%= CurrentValue %>");
		</script>
		<%
		continue;
	}

}

//Calculate the Checkpoint Skips
int CheckpointSkips = 0;

//If the Last User Program Index Checkpoint is null, then set it to the program's initial Exercise_ID
if(Last_Program_ID == null){
	// If the Database returned null, set it to the default program's Exercise_ID
	Last_Program_ID = ExerciseIDIndexArrayList.get(0);
	
}else{

//Figure out how much to skip
for(int i = 0 ; i < ExerciseIDIndexArrayList.size(); i++){
	if(ExerciseIDIndexArrayList.get(i).equals(Last_Program_ID)){
		CheckpointSkips = i;
		break;
	}
}

}


//Decide the Current Program Index (Can be any index from the program)
String CurrentProgramIndex = "1";

//Get the CurrentProgramNameFieldValuePair
String CurrentProgramNameTable = "RLF_Programs_Exercises";
String [] CurrentProgramNameColumns = {"Program_Name"};
String CurrentProgramColumnName = "Exercise_ID";
String CurrentProgramColumnValue = CurrentProgramIndex;
ArrayList<ArrayList<String>> CurrentProgramNameFieldValuePair = GlobalTools.getTableColumnAndValuePairData(request, response, CurrentProgramNameTable, CurrentProgramColumnName, CurrentProgramColumnValue, CurrentProgramNameColumns);
*/

String Primary_Program_Name = "Back and Chest Boost";
CurrentSession.setAttribute("Primary_Program_Name", Primary_Program_Name);

%>

		<p><b><a href="<%= GlobalTools.GTV_RLFService_RLFPlayer %>"><%= Primary_Program_Name %></a></b><span> - Begin your daily workout for the day.</span></p>

		<br/>
		<br/>
		<br/>
		<br/>

        <!-- Pass the Target Username with the Form Submit -->
        <input type="hidden" name="ProgramForToday" value="<%= Primary_Program_Name %>" />

        <!-- Change Username Button -->
		<input type='button' id='RLFRegimenButton' name='RLFRegimenButton' value='Submit' onClick="submitForm(document.RLFRegimenForm);" />
        

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