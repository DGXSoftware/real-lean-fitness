<!--
GOAL: RLF Program Player.
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
	String Primary_Program_Name = (String) CurrentSession.getAttribute("Primary_Program_Name");
	
%>

<%

		// TEST STUFF
		SessionAccountID = "1000000006";
		SessionFirstName = "DMGX";
		Primary_Program_Name = "Back and Chest Boost";

		// Only proceed if we have a valid program name to Follow
		if(Primary_Program_Name != null){

%>


<!-- ************************************************************************************** -->
<!-- ************************************************************************************** -->
<!-- ************************************************************************************** -->
<!-- START DYNAMIC JAVA CODE -->
<!-- ************************************************************************************** -->
<!-- ************************************************************************************** -->
<!-- ************************************************************************************** -->

<%
//Get the Current User's Last_Exercise_ID if any
String Last_Exercise_ID = GlobalTools.getSingleTableCellData(request, response,"RLF_Programs_Checkpoints","Last_Exercise_ID", "Account_ID", SessionAccountID);

// Get the CompleteProgramNameFieldValuePair
String CompleteProgramNameTable = "RLF_Programs_Exercises";
String [] CompleteProgramNameColumns = {"Exercise_ID","Program_Name","Exercise_Type","Exercise_Name","Time_In_Seconds","Equipment_List","Demonstration_URL","Description"};
String CompleteProgramColumnName = "Program_Name"; 
String CompleteProgramColumnValue = Primary_Program_Name; // Get the Current Program Name
ArrayList<ArrayList<String>> CompleteProgramNameFieldValuePair = GlobalTools.getTableColumnAndValuePairData(request, response, CompleteProgramNameTable, CompleteProgramColumnName, CompleteProgramColumnValue, CompleteProgramNameColumns);

// Declare and get the LongRandomKey for randomizing the 2D Array List
Long LongRandomKey = null;
// Get the Random Key from the Database for this user (If Any)
String LongRandomDatabaseKey = GlobalTools.getSingleTableCellData(request, response,"RLF_Programs_Checkpoints","Random_Exercise_Key", "Account_ID", SessionAccountID);
if(LongRandomDatabaseKey != null && !LongRandomDatabaseKey.equals("")){
	//System.out.println("Make Key from the Database!");
	LongRandomKey = Long.parseLong(LongRandomDatabaseKey);
}else{
	//System.out.println("Make Key from scratch!");
	LongRandomKey = System.nanoTime();
	// Save the new Random Key to the Database for this user
	
	GlobalTools.setSingleTableCellData(request, response,"RLF_Programs_Checkpoints","Account_ID",SessionAccountID,"Random_Exercise_Key",LongRandomKey.toString(),"Account_ID",SessionAccountID);
			
}

// Randomize the CompleteProgramNameFieldValuePair 2D ArrayList
GlobalTools.randomize2DArrayList(CompleteProgramNameFieldValuePair, LongRandomKey);

// Pair up all Left/Right elements for the CompleteProgramNameFieldValuePair 2D ArrayList
GlobalTools.shiftSequential2DArrayListElements(CompleteProgramNameFieldValuePair, 1, " Left", " Right");

/* 
//Print all Exercise Names
for(int i = 0 ; i < CompleteProgramNameFieldValuePair.get(1).size(); i++){
	if(CompleteProgramNameFieldValuePair.get(0).get(i).equals("Exercise_Name")){
		System.out.println(CompleteProgramNameFieldValuePair.get(1).get(i));
	}
}
*/
		
%>

<!-- ************************************************************************************** -->
<!-- ************************************************************************************** -->
<!-- ************************************************************************************** -->
<!-- END DYNAMIC JAVA CODE -->
<!-- ************************************************************************************** -->
<!-- ************************************************************************************** -->
<!-- ************************************************************************************** -->


<!-- START HTML RESPONSE  -->
<!-- $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ -->

		<?xml version = '1.0'?>
		<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'>
		
		<!-- The xmlns attribute is required in XHTML but it is invalid in HTML. -->
		<!-- the namespace 'xmlns=http://www.w3.org/1999/xhtml' is default, and will be added to the <html> tag in XHTML even if you do not include it. -->
		<html xmlns='http://www.w3.org/1999/xhtml'>
		
		<head>
		
		<!-- Set the Title for the Website Page -->
		<title>RLF Program Player</title>
		
		<!-- Set the Favicon for the Website page -->
		<link rel='Shortcut Icon' type='image/ico' href='/Images/favicon.ico'/>
		
		<!-- Set the Character Encoding for the Website page -->
		<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1' />
		
		<!-- Include the Stylesheet Files -->
		<link rel='stylesheet' type='text/css' href='/CSS/RLFStyle.css' />
		
		<!-- Include the jQuery Files -->
		<script type='text/javascript' src="/JavaScript/JQuery/jquery.js"></script>
		<!--
		EXTERNAL jQuery Import
		<script type = "text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
		-->
		
		<style>
		#success_title { font-size: 20px; color: green; text-align:center; }
		#cancel_title { font-size: 20px; color: red; text-align:center; }
		#BackButton { font-size: 20px; color: red; text-align:center; }
		</style>
		
		<script>
		
// Toggle between displaying the RLFPlayerBody or RLFResultsBody
function PlayerResultsBodyToggle(){
	if ($('#RLFPlayerBody').is(':visible')){
		$("#RLFPlayerBody").hide();
		$("#RLFResultsBody").show();
	}else{
		$("#RLFResultsBody").hide();
		$("#RLFPlayerBody").show();
	}	
}
		
//Submits the Request
function submitProgramCheckpointUpdate(New_Exercise_ID_Value) {
	
		// Submit via AJAX and pass the current SessionAccountID and New_Exercise_ID_Value
        var jqxhr = $.ajax({
            type:       "POST",
            url:        "/ProgramCheckpointServlet",
            cache:      false,
            data:       $("#ProgramCheckpointForm").serialize()+"&SessionAccountID=<%= SessionAccountID %>&New_Exercise_ID="+New_Exercise_ID_Value+"",
                
            // Before load, notify the user that the request may take a while 
            beforeSend: function() {
            	
                        },
                            
            // If user remains on page for the results, show alert with results
            success:    function(data, status) {

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
<div id="RLFPlayerBody"> <!-- Start the RLF Player Body -->
<div id="container">
	<div id="main">
		<div id="header"></div>
		<div id="nav">
			<ul>
			<%
			// Print the Logged In Menu Items
			GlobalTools.printPageMenuItems(out,"LoggedIn",SessionFirstName);
			%>
			</ul>
		</div>
		<div id="content">

				<div class="post">
					<p align="left"></p>

<p>&#160;</p>
<p>&#160;</p>
<p>&#160;</p>
<p>&#160;</p>

<!-- START Countdown Redirect -->
<style>

#PlayerHeader {font-size:20pt; color:blue; font-family:Calibri; text-align:center;}

#CountdownMessage {font-size:20pt; color:blue; font-family:calibri; text-align:center;}
#CountdownSeconds {font-size:25pt; color:Red; font-family:calibri; text-align:center;}
#FastForwardButton {font-size:12pt; color:black; font-family:calibri; text-align:center;}
#StatusMessage {font-size:20pt; color:green; font-family:calibri; text-align:center;}

#PlayerDemonstrationDiv {font-size:20pt; color:blue; font-family:calibri; text-align:center; cursor: pointer; }
#PlayerControlsDiv {font-size:25pt; color:Red; font-family:calibri; text-align:center;}

</style>

<div id="PlayerHeader">
<b id="StatusMessage">DEFAULT STATUS MESSAGE</b>
<b id="CountdownMessage">DEFAULT COUNTDOWN MESSAGE</b>
<span id="CountdownSeconds">1018</span>
</div>

<br/>
<br/>

<script>
//document.getElementById("CountdownMessage").innerHTML = "AAAAAAAA";
</script>

<div id="PlayerDemonstrationDiv">
<img id="PlayerDemonstration" name="PlayerDemonstration" onClick="PlayerStartStopToggle();" src="" height="300" width="300" /> 
</div>

<!-- ************************************************************************************** -->
<!-- ************************************************************************************** -->
<!-- ************************************************************************************** -->
<!-- START DYNAMIC JAVA CODE -->
<!-- ************************************************************************************** -->
<!-- ************************************************************************************** -->
<!-- ************************************************************************************** -->

<script>
// Declare the JavaScript Program Arrays
var JS_Exercise_ID_Array = []; // Exercise_ID
var JS_Program_Name_Array = []; // Program_Name
var JS_Exercise_Type_Array = []; // Exercise_Type
var JS_Exercise_Name_Array = []; // Exercise_Name
var JS_Time_In_Seconds_Array = []; // Time_In_Seconds
var JS_Equipment_List_Array = []; // Equipment_List
var JS_Demonstration_URL_Array = []; // Demonstration_URL
var JS_Description_Array = []; // Description
</script>
<%

//Declare the ExerciseID IndexArrayList
ArrayList<String> ExerciseIDIndexArrayList = new ArrayList<String>();

//Generate all the JavaScript Arrays and the ExerciseID IndexArrayList
for(int i = 0 ; i < CompleteProgramNameFieldValuePair.get(0).size(); i++){
    
	// Get the Current SQL Result Values
	String CurrentColumnName = CompleteProgramNameFieldValuePair.get(0).get(i);
	//String UserDisplayCurrentColumnName = CurrentColumnName.replaceAll("_", " ");
	String CurrentValue = CompleteProgramNameFieldValuePair.get(1).get(i);
	
	// Generate the JS_Exercise_ID_Array
	if(CurrentColumnName.equals("Exercise_ID")){
		ExerciseIDIndexArrayList.add(CurrentValue);
		%>
		<script>
		JS_Exercise_ID_Array.push("<%= CurrentValue %>");
		</script>
		<%
		continue;
	}
	
	// Generate the JS_Program_Name_Array
	if(CurrentColumnName.equals("Program_Name")){}
	
	// Generate the JS_Exercise_Type_Array
	if(CurrentColumnName.equals("Exercise_Type")){
		%>
		<script>
		JS_Exercise_Type_Array.push("<%= CurrentValue %>");
		</script>
		<%
		continue;
	}
	
	// Generate the JS_Exercise_Name_Array
	if(CurrentColumnName.equals("Exercise_Name")){
		%>
		<script>
		JS_Exercise_Name_Array.push("<%= CurrentValue %>");
		</script>
		<%
		continue;
	}
	
	// Generate the JS_Time_In_Seconds_Array
	if(CurrentColumnName.equals("Time_In_Seconds")){
		%>
		<script>
		JS_Time_In_Seconds_Array.push(<%= Integer.parseInt(CurrentValue) %>);
		</script>
		<%
		continue;
	}	
	
	// Generate the JS_Equipment_List_Array
	if(CurrentColumnName.equals("Equipment_List")){}
	
	// Generate the JS_Demonstration_URL_Array
	if(CurrentColumnName.equals("Demonstration_URL")){
		%>
		<script>
		JS_Demonstration_URL_Array.push("<%= CurrentValue %>");
		</script>
		<%
		continue;
	}
	
	// Generate the JS_Description_Array
	if(CurrentColumnName.equals("Description")){}

}

//Calculate the Checkpoint Skips
int CheckpointSkips = 0;

//If the Last User Program Index Checkpoint is null, then set it to the program's initial Exercise_ID
if(Last_Exercise_ID == null){
	// If the Database returned null, set it to the default program's Exercise_ID
	Last_Exercise_ID = ExerciseIDIndexArrayList.get(0);
	
}else{

//Figure out how much to skip
for(int i = 0 ; i < ExerciseIDIndexArrayList.size(); i++){
	if(ExerciseIDIndexArrayList.get(i).equals(Last_Exercise_ID)){
		CheckpointSkips = i;
		break;
	}
}

}


%>
<script>
// Set the Current User CheckPoint
var CheckPointImageIndex = <%= CheckpointSkips %>;
</script>
<%

%>

<!-- ************************************************************************************** -->
<!-- ************************************************************************************** -->
<!-- ************************************************************************************** -->
<!-- END DYNAMIC JAVA CODE -->
<!-- ************************************************************************************** -->
<!-- ************************************************************************************** -->
<!-- ************************************************************************************** -->

<script>

// Set Image CheckPoints Here
var CurrentImageIndex = 0;
CurrentImageIndex = CurrentImageIndex + CheckPointImageIndex;

// Set Initial Values
document.getElementById("StatusMessage").innerHTML = "RUNNING: ";
document.getElementById("CountdownMessage").innerHTML = JS_Exercise_Name_Array[CurrentImageIndex]  + " ";
document.getElementById("CountdownSeconds").innerHTML = JS_Time_In_Seconds_Array[CurrentImageIndex];
document.getElementById("PlayerDemonstration").src = JS_Demonstration_URL_Array[CurrentImageIndex];

// Set the PLayer as running
var IsRunning = true;

// Redirect to Homepage
$(document).ready(function () {
	var refreshId = window.setInterval(function () {
        var iTimeRemaining = $("#CountdownSeconds").html();
        iTimeRemaining = eval(iTimeRemaining);
        if (iTimeRemaining == 0) {

        	// Increase the Image Index
        	CurrentImageIndex += 1;
        	
        	// Act upon showing the last Array Iteration
        	if(CurrentImageIndex >= JS_Exercise_Name_Array.length){
    			
    			// Break out of window.setInterval
    			clearInterval(refreshId);
        	}else{
        	
        	// Update the Current Demonstration
        	document.getElementById("CountdownMessage").innerHTML = JS_Exercise_Name_Array[CurrentImageIndex] + " ";
        	document.getElementById("CountdownSeconds").innerHTML = JS_Time_In_Seconds_Array[CurrentImageIndex];
        	document.getElementById("PlayerDemonstration").src = JS_Demonstration_URL_Array[CurrentImageIndex];
        	
        	// Save the New Exercise_ID Value
        	submitProgramCheckpointUpdate(JS_Exercise_ID_Array[CurrentImageIndex]);
        	
        	}
        	
        } else {
        	if(IsRunning === true){
        	
        	// Update the Time Countdown
            $("#CountdownSeconds").html(iTimeRemaining - 1);
            
			// Update the % Bar
			updatePercentageBar();
            
        	}
        }
    }, 1000);
});

//Starts and Stops the Player
function PlayerStartStopToggle(){
	 
	if(IsRunning === true){
		IsRunning = false;
		document.getElementById("PlayerStartStopToggleButton").src = "http://dgxsoftware.com/RLF/JSP/RLFService/Pictures/PlayButton.png";
		document.getElementById("StatusMessage").innerHTML = "PAUSED: ";
		document.getElementById("StatusMessage").style.color = "red";
		return false;
	}
	
	if(IsRunning === false){
		IsRunning = true;
		document.getElementById("PlayerStartStopToggleButton").src = "http://dgxsoftware.com/RLF/JSP/RLFService/Pictures/PauseButton.png";
		document.getElementById("StatusMessage").innerHTML = "RUNNING: ";
		document.getElementById("StatusMessage").style.color = "green";
		return false;
	}
	
}


//Starts and Stops the Player
function PlayerRewind(){
	
	//alert("CurrentImageIndex = " + CurrentImageIndex);
	
	//CheckPointImageIndex = CheckPointImageIndex - 1;
	
	// Save the New Exercise_ID Value
	//submitProgramCheckpointUpdate(JS_Exercise_ID_Array[CurrentImageIndex - 1]);
	
	//CurrentImageIndex = CurrentImageIndex - 1;
	
	
	//location.reload(); 
	//PlayerRefresh();
}

//Starts and Stops the Player
function PlayerForward(){
	
	//alert("Fast FOrward Bruuuuh!");
	
}

//Starts and Stops the Player
function PlayerRefresh(){
	
	//alert("Refresh Bruuuuh!");
	
	//clearInterval(refreshId);
	
	//$("#RLFPlayerBody").load("#RLFPlayerBody");
}

</script>

<script>

// Set the Max Percentage Bar Time to calculate the Bar % Reach to Max
var MaxProgramTime = 0;
for(var i = 0 ; i < JS_Time_In_Seconds_Array.length; i++){
	MaxProgramTime = MaxProgramTime + JS_Time_In_Seconds_Array[i];
}

//Set the Elapsed Time to calculate the Bar % Progress, and Apply the CheckPoint Time
var ElapsedProgramTime = 0;
for(var i = 0 ; i < CheckPointImageIndex; i++){
	ElapsedProgramTime = ElapsedProgramTime + JS_Time_In_Seconds_Array[i];
}

// Update the PercentageBar
function updatePercentageBar() {
	
	// Increase Elapsed Time by a Second
	ElapsedProgramTime = ElapsedProgramTime + 1;
	
	// Get the Current Bar % and Update the Graphical Bar
    var percentage = Math.round((ElapsedProgramTime/MaxProgramTime)*100);
      if (percentage <= 100) {
        $('#PercentageBarInnerDiv').css("width", percentage + "%");
		$('#PercentageBarInnerText').text(percentage + "%");
      }else{
    	  //alert("Percentage is over 100% at = " + percentage "%");
      }
      
}
</script>

<br/>

<div id="PlayerControlsDiv">

<!-- PlayerStartStopToggleButton -->
<input type="image" src="http://dgxsoftware.com/RLF/JSP/RLFService/Pictures/RewindButton.png" 
name="RewindButton" id="RewindButton" value="Rewind" onClick="PlayerRewind();" 
width="48" height="48"/>

<!-- PlayerStartStopToggleButton -->
<input type="image" src="http://dgxsoftware.com/RLF/JSP/RLFService/Pictures/PauseButton.png" 
name="PlayerStartStopToggleButton" id="PlayerStartStopToggleButton" value="Start / Stop" onClick="PlayerStartStopToggle();" 
width="48" height="48"/>

<!-- PlayerStartStopToggleButton -->
<input type="image" src="http://dgxsoftware.com/RLF/JSP/RLFService/Pictures/ForwardButton.png" 
name="ForwardButton" id="ForwardButton" value="Forward" onClick="PlayerForward();" 
width="48" height="48"/>

</div>

<br/>
<br/>

<style>
.centered { text-align: center; }
</style>
<form id="PercentageBar" class="centered">
<div id="PercentageBarOuterDiv" style="width: 300px; height: 20px; z-index: 1; position: relative; border: 1px solid grey; border-radius: 5px; -moz-border-radius: 5px;">
<div id="PercentageBarInnerDiv" style="width: 0%; height: 100%; z-index: 2; float:left; background-color: lightblue;"></div>
<div id="PercentageBarInnerText" style="width: 100%; height: 100%; z-index: 3; position: absolute; top: 0; left: 0; color: black; font-weight: bold; text-align: center;">0%</div>
</div>
</form>

<!-- Fast Forward Button -->
<!--
<div id="FastForwardButton">
<input type="button" onclick="location.href ='<%= request.getParameter("RedirectURL") == null ? GlobalTools.GTV_Homepage : request.getParameter("RedirectURL") %>'" value="Please Click here if you do not wish to wait any longer." />
</div>
-->

<p>&#160;</p>
<p>&#160;</p>
<p>&#160;</p>
			
				</div>

			<div class="clear"></div>
		</div>
	</div>
	<div id="footer">
	<ul>
	<%
	// Print the Empty Menu Items
	GlobalTools.printPageMenuItems(out,"Empty","");
	%>
	</ul>
	<span>Copyright © 2014</span>
	</div>
</div>

</div> <!-- End the RLF Player Body -->

<div id="RLFResultsBody" style="display:none"> <!-- Start the RLF Result Body -->
<h1>RESULTS</h1>
</div> <!-- End the RLF Result Body -->
</body>

<!-- This scripts are unrelated to page loading  -->
<script>
      // Use JQuery to execute JavaScript code as soon as the page loads
      $(document).ready(function(){
    	  
    	  // TEST STUFF
    	  // PlayerResultsBodyToggle();
    	  //$("#RLFResultsBody").show();
    	  //$("#RLFPlayerBody").show();
    	  
      });
</script>

</html>


<!-- $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ -->
<!-- END HTML RESPONSE  -->

<%

	}else{
	
    // Since the Query String credentials were invalid or expired
    // Return the user Home via a Cancel Countdown Forward Message
    String CancelMessage = "Invalid Program. Please use the Workout Manager.";
    String CancelURL = GlobalTools.GTV_CountdownForwardMessage + "?CancelMessage="+CancelMessage+"";

    // Forward the User back to the Homepage
	response.sendRedirect(CancelURL);
	
	}

		// End Try; if an exception occurs, return the error page
		}catch (Exception EX) {EX.printStackTrace();} // end catch
%>

<%

}else{

	// The current user does NOT have a session, therefore redirect them to the Homepage
	response.sendRedirect(GlobalTools.GTV_Homepage);

}

%>