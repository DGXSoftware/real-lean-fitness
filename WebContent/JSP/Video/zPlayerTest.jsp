<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%-- JSP Imports --%>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "dgx.software.com.UtilityPackage.GlobalTools" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Player Test</title>
</head>
<body>

<%

String SessionAccountID = "1000000005";


%>


<!-- ************************************************************************************** -->
<!-- START DYNAMIC JAVA CODE -->
<!-- ************************************************************************************** -->
<%

// Decide the Current Program Index (Can be any index from the program)
String CurrentProgramIndex = "1";

//Get the Current User's Last_PID if any
String Last_PID = GlobalTools.getProgramCheckpointTableCellData(request, response, SessionAccountID);

// Get the CurrentProgramNameFieldValuePair
String CurrentProgramNameTable = "RLF_Programs_Pokemon";
String [] CurrentProgramNameColumns = {"Program"};
String CurrentProgramColumnName = "PID";
String CurrentProgramColumnValue = CurrentProgramIndex;
ArrayList<ArrayList<String>> CurrentProgramNameFieldValuePair = GlobalTools.getTableColumnAndValuePairData(request, response, CurrentProgramNameTable, CurrentProgramColumnName, CurrentProgramColumnValue, CurrentProgramNameColumns);



// Get the CompleteProgramNameFieldValuePair
String CompleteProgramNameTable = "RLF_Programs_Pokemon";
String [] CompleteProgramNameColumns = {"PID","Program","Exercise_Name","Time_Seconds","Equipment","Demonstration","Description"};
String CompleteProgramColumnName = "Program"; 
String CompleteProgramColumnValue = CurrentProgramNameFieldValuePair.get(1).get(0);
ArrayList<ArrayList<String>> CompleteProgramNameFieldValuePair = GlobalTools.getTableColumnAndValuePairData(request, response, CompleteProgramNameTable, CompleteProgramColumnName, CompleteProgramColumnValue, CompleteProgramNameColumns);


%>
<script>
// Declare the JavaScript Program Arrays
var JS_PID_Array = []; // PID
var JS_Program_Array = []; // Program
var JS_Exercise_Name_Array = []; // Exercise_Name
var JS_Time_Seconds_Array = []; // Time_Seconds
var JS_Equipment_Array = []; // Equipment
var JS_Demonstration_Array = []; // Demonstration
var JS_Description_Array = []; // Description
</script>
<%

// Declare the PID IndexArrayList
ArrayList<String> PIDIndexArrayList = new ArrayList<String>();

// Generate all the JavaScript Arrays and the PID IndexArrayList
for(int i = 0 ; i < CompleteProgramNameFieldValuePair.get(0).size(); i++){
    
	// Get the Current SQL Result Values
	String CurrentColumnName = CompleteProgramNameFieldValuePair.get(0).get(i);
	//String UserDisplayCurrentColumnName = CurrentColumnName.replaceAll("_", " ");
	String CurrentValue = CompleteProgramNameFieldValuePair.get(1).get(i);
	
	// Generate the JS_PID_Array
	if(CurrentColumnName.equals("PID")){
		PIDIndexArrayList.add(CurrentValue);
		%>
		<script>
		JS_PID_Array.push("<%= CurrentValue %>");
		</script>
		<%
		continue;
	}
	
	// Generate the JS_Program_Array
	if(CurrentColumnName.equals("Program")){}
	
	// Generate the JS_Exercise_Name_Array
	if(CurrentColumnName.equals("Exercise_Name")){
		%>
		<script>
		JS_Exercise_Name_Array.push("<%= CurrentValue %>");
		</script>
		<%
		continue;
	}
	
	// Generate the JS_Time_Seconds_Array
	if(CurrentColumnName.equals("Time_Seconds")){
		%>
		<script>
		JS_Time_Seconds_Array.push("<%= CurrentValue %>");
		</script>
		<%
		continue;
	}	
	
	// Generate the JS_Equipment_Array
	if(CurrentColumnName.equals("Equipment")){}
	
	// Generate the JS_Demonstration_Array
	if(CurrentColumnName.equals("Demonstration")){
		%>
		<script>
		JS_Demonstration_Array.push("<%= CurrentValue %>");
		</script>
		<%
		continue;
	}
	
	// Generate the JS_Description_Array
	if(CurrentColumnName.equals("Description")){}

}

// Calculate the Checkpoint Skips
int CheckpointSkips = 0;

//If the Last User Program Index Checkpoint is null, then set it to the program's initial PID
if(Last_PID == null){
	// If the Database returned null, set it to the default program's PID
	Last_PID = PIDIndexArrayList.get(0);
	
}else{

//Figure out how much to skip
for(int i = 0 ; i < PIDIndexArrayList.size(); i++){
	if(PIDIndexArrayList.get(i).equals(Last_PID)){
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

<script>

for(var i = 0; i < JS_Exercise_Name_Array.length; i++){
	//alert(JS_Exercise_Name_Array[i]);
}

</script>

<!-- ************************************************************************************** -->
<!-- END DYNAMIC JAVA CODE -->
<!-- ************************************************************************************** -->


</body>
</html>