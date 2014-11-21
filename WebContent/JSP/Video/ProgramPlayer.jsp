<!--
GOAL: RLF Program Player
-->

<%-- JSP Imports --%>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "dgx.software.com.UtilityPackage.GlobalTools" %>

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

		<%

		String SessionAccountID = "1000000004";

		%>
		
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
<div id="container">
	<div id="main">
		<div id="header"></div>
		<div id="nav">
			<ul>
			    <li><a href="<%= GlobalTools.GTV_Homepage %>">Home</a></li>
				<li><a href="#"></a></li>
				<li><a href="#"></a></li>
				<li><a href="#"></a></li>
				<li><a href="#"></a></li>
				<li><a href="#"></a></li>
			</ul>
		</div>
		<div id="content">

				<div class="post">
					<p align="left"></p>

<p>&#160;</p>
<p>&#160;</p>
<p>&#160;</p>
<%

//Get the Current CurrentRegistrationUsername
String MessageToDisplay = "";
boolean SkipCountdown = false;

// If the Query String Variable CancelMessage exists, get the value
if (request.getParameter("CancelMessage") != null){
	MessageToDisplay = request.getParameter("CancelMessage");
	
	// Only display this extra message if a CancelMessage was provided in the query string
	if(!MessageToDisplay.equals("")){
		%>
		<div id="cancel_title"><%= MessageToDisplay %></div>
		<%
	}
}

//If the Query String Variable SuccessMessage exists, get the value
if (request.getParameter("SuccessMessage") != null){
	MessageToDisplay = request.getParameter("SuccessMessage");
	
	// Only display this extra message if a CancelMessage was provided in the query string
	if(!MessageToDisplay.equals("")){
		%>
		<div id="success_title"><%= MessageToDisplay %></div>
		<%
	}
}

//If the Query String Variable CancelCountdown exists, Skip the countdown
if (request.getParameter("SkipCountdown") != null){

	// Only display this extra message if a CancelMessage was provided in the query string
	if(!request.getParameter("SkipCountdown").equals("")){

	    // Skip the Countdown
		SkipCountdown = true;
   }
}

%>
<p>&#160;</p>

<%

// Skip the countdown only if it was set to true
if(SkipCountdown == false){
%>
<!-- START Countdown Redirect -->
<style>

#PlayerHeader {font-size:20pt; color:blue; font-family:Calibri; text-align:center;}

#CountdownMessage {font-size:20pt; color:blue; font-family:calibri; text-align:center;}
#CountdownSeconds {font-size:25pt; color:Red; font-family:calibri; text-align:center;}
#FastForwardButton {font-size:12pt; color:black; font-family:calibri; text-align:center;}
#StatusMessage {font-size:20pt; color:green; font-family:calibri; text-align:center;}

#MyImageDiv {font-size:20pt; color:blue; font-family:calibri; text-align:center; cursor: pointer; }
#StartStopToggleButtonDiv {font-size:25pt; color:Red; font-family:calibri; text-align:center;}

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

<div id="MyImageDiv">
<img id="MyImage" name="MyImage" onClick="ToggleCountdown();" src="" height="300" width="300" /> 
</div>

<!-- ************************************************************************************** -->
<!-- ************************************************************************************** -->
<!-- ************************************************************************************** -->
<!-- START DYNAMIC JAVA CODE -->
<!-- ************************************************************************************** -->
<!-- ************************************************************************************** -->
<!-- ************************************************************************************** -->

<%
//Decide the Current Program Index (Can be any index from the program)
String CurrentProgramIndex = "1";

//Get the Current User's Last_Exercise_ID if any
String Last_Exercise_ID = GlobalTools.getSingleTableCellData(request, response,"rlf_programs_checkpoints","Last_Exercise_ID", "Account_ID", SessionAccountID);

// Get the CurrentProgramNameFieldValuePair
String CurrentProgramNameTable = "RLF_Programs_Exercises";
String [] CurrentProgramNameColumns = {"Program_Name"};
String CurrentProgramColumnName = "Exercise_ID";
String CurrentProgramColumnValue = CurrentProgramIndex;
ArrayList<ArrayList<String>> CurrentProgramNameFieldValuePair = GlobalTools.getTableColumnAndValuePairData(request, response, CurrentProgramNameTable, CurrentProgramColumnName, CurrentProgramColumnValue, CurrentProgramNameColumns);



// Get the CompleteProgramNameFieldValuePair
String CompleteProgramNameTable = "RLF_Programs_Exercises";
String [] CompleteProgramNameColumns = {"Exercise_ID","Program_Name","Exercise_Type","Exercise_Name","Time_In_Seconds","Equipment_List","Demonstration_URL","Description"};
String CompleteProgramColumnName = "Program_Name"; 
String CompleteProgramColumnValue = CurrentProgramNameFieldValuePair.get(1).get(0);
ArrayList<ArrayList<String>> CompleteProgramNameFieldValuePair = GlobalTools.getTableColumnAndValuePairData(request, response, CompleteProgramNameTable, CompleteProgramColumnName, CompleteProgramColumnValue, CompleteProgramNameColumns);


// Declare and get the LongRandomKey for randomizing the 2D Array List
Long LongRandomKey = null;
// Get the Random Key from the Database for this user (If Any)
String LongRandomDatabaseKey = GlobalTools.getSingleTableCellData(request, response,"rlf_programs_checkpoints","Random_Exercise_Key", "Account_ID", SessionAccountID);
if(LongRandomDatabaseKey != null && !LongRandomDatabaseKey.equals("")){
	//System.out.println("Make Key from the Database!");
	LongRandomKey = Long.parseLong(LongRandomDatabaseKey);
}else{
	//System.out.println("Make Key from scratch!");
	LongRandomKey = System.nanoTime();
	// Save the new Random Key to the Database for this user
	
	GlobalTools.setSingleTableCellData(request, response,"rlf_programs_checkpoints","Account_ID",SessionAccountID,"Random_Exercise_Key",LongRandomKey.toString(),"Account_ID",SessionAccountID);
			
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
document.getElementById("MyImage").src = JS_Demonstration_URL_Array[CurrentImageIndex];

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
        	document.getElementById("MyImage").src = JS_Demonstration_URL_Array[CurrentImageIndex];
        	
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

function ToggleCountdown(){
	 
	if(IsRunning === true){
		IsRunning = false;
		document.getElementById("StartStopToggleButton").src = "http://www.clipartbest.com/cliparts/yco/e9a/ycoe9abMi.png";
		document.getElementById("StatusMessage").innerHTML = "PAUSED: ";
		document.getElementById("StatusMessage").style.color = "red";
		return false;
	}
	
	if(IsRunning === false){
		IsRunning = true;
		document.getElementById("StartStopToggleButton").src = "http://theologygaming.com/wp-content/uploads/2014/08/Pause.png";
		document.getElementById("StatusMessage").innerHTML = "RUNNING: ";
		document.getElementById("StatusMessage").style.color = "green";
		return false;
	}
	
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

<div id="StartStopToggleButtonDiv">
<input type="image" src="http://theologygaming.com/wp-content/uploads/2014/08/Pause.png" 
name="StartStopToggleButton" id="StartStopToggleButton" value="Toggle Countdown" onClick="ToggleCountdown();" 
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
<!-- END Countdown Redirect -->
<%
}
%>

<p>&#160;</p>
<p>&#160;</p>
<p>&#160;</p>
			
				</div>

			<div class="clear"></div>
		</div>
	</div>
	<div id="footer"><span>Copyright © 2014</span></div>
</div>
</body>
</html>