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


</body>
</html>