package dgx.software.com.UtilityPackage;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

public class GlobalTools {

/**************************************************************************************************/
/* START GLOBAL VARIABLES */	
/**************************************************************************************************/
    
	// <%= GlobalTools.GTV_Homepage %>
	public static final String GTV_Homepage = "/";
	
	// <%= GlobalTools.GTV_UserProfile %>
	public static final String GTV_UserProfile = "/JSP/UserPages/UserProfile.jsp";
	
	// <%= GlobalTools.GTV_UserSettings %>
	public static final String GTV_UserSettings = "/JSP/UserPages/UserSettings.jsp";
	
	// <%= GlobalTools.GTV_Settings_UserAccount %>
	public static final String GTV_Settings_UserAccount = "/JSP/UserPages/Settings/UserAccount.jsp";
	
	// <%= GlobalTools.GTV_Settings_UserInformation %>
	public static final String GTV_Settings_UserInformation = "/JSP/UserPages/Settings/UserInformation.jsp";
	
	// <%= GlobalTools.GTV_Settings_PasswordChange %>
	public static final String GTV_Settings_PasswordChange = "/JSP/UserPages/Settings/PasswordChange.jsp";
	
	// <%= GlobalTools.GTV_Settings_ForgotPasswordChange %>
	public static final String GTV_Settings_ForgotPasswordChange = "/JSP/UserPages/Settings/ForgotPasswordChange.jsp";
	
	// <%= GlobalTools.GTV_Settings_RequestForgotPasswordChange %>
	public static final String GTV_Settings_RequestForgotPasswordChange = "/JSP/UserPages/Settings/RequestForgotPasswordChange.jsp";
	
	// <%= GlobalTools.GTV_Settings_NewsletterSubscription %>
	public static final String GTV_Settings_NewsletterSubscription = "/JSP/UserPages/Settings/NewsletterSubscription.jsp";
	
	// <%= GlobalTools.GTV_Settings_EMailVerification %>
	public static final String GTV_Settings_EMailVerification = "/JSP/UserPages/Settings/EMailVerification.jsp";
    
	// <%= GlobalTools.GTV_ContactUs %>
	public static final String GTV_ContactUs = "/JSP/Mail/ContactUs.jsp";
	
	// <%= GlobalTools.GTV_PayPalRegistrationSubmit %>
	public static final String GTV_PayPalRegistrationSubmit = "/JSP/PayPal/PayPalRegistrationSubmit.jsp";
    
	// <%= GlobalTools.GTV_CountdownForwardMessage %>
	public static final String GTV_CountdownForwardMessage = "/JSP/Tools/CountdownForwardMessage.jsp";
	
	// <%= GlobalTools.GTV_GettingStarted %>
	public static final String GTV_RLFService_GettingStarted= "/JSP/RLFService/GettingStarted.jsp";
	
	// <%= GlobalTools.GTV_RLFService_RLFRegimen %>
	public static final String GTV_RLFService_RLFRegimen = "/JSP/RLFService/Workout/RLFRegimen.jsp";
	
	// <%= GlobalTools.GTV_RLFService_RLFPlayer %>
	public static final String GTV_RLFService_RLFPlayer = "/JSP/RLFService/Workout/RLFPlayer.jsp";
	
	// <%= GlobalTools.GTV_RLFService_RLFStats %>
	public static final String GTV_RLFService_RLFWorkoutStatusReport = "/JSP/RLFService/Workout/RLFWorkoutStatusReport.jsp";
    
	// <%= GlobalTools.GTV_RLFService_RLFMealManager %>
	public static final String GTV_RLFService_RLFMealManager = "/JSP/RLFService/Meal/RLFMealManager.jsp";
	
	// <%= GlobalTools.GTV_Error_QueryStringReader %>
	public static final String GTV_Error_QueryStringReader = "/JSP/Error/QueryStringReader.jsp";
	
	// <%= GlobalTools.GTV_Servlet_Logout %>
	public static final String GTV_Servlet_Logout = "/LogOutServlet";
    

/**************************************************************************************************/
/* END GLOBAL VARIABLES */	
/**************************************************************************************************/
	
	// Declare the SQL Connection Objects
	private static Connection SQLConnection;
	private static Statement SQLStatement;
	
	// Declare the Database Connection Variables
	private static String DriverName = null;
    private static String DatabaseURL = null;
    private static String DatabaseUser = null;
    private static String DatabasePassword = null;
    
    // Declare the servletContextProperties
    private static java.util.Properties _servletContextProperties = new java.util.Properties();
    
    // NOTE: (This is being treated like an Init method)
    // Set the servletContextProperties
    public static void setServletContextProperties(java.util.Properties servletContextProperties) {
    	
    	// Set the servletContextProperties
        _servletContextProperties = servletContextProperties;
        
        // Get the web.xml attributes from the servletContextProperties
        DriverName = _servletContextProperties.getProperty("DriverName");
        DatabaseURL = _servletContextProperties.getProperty("DatabaseURL");
        DatabaseUser = _servletContextProperties.getProperty("DatabaseUser");
        DatabasePassword = _servletContextProperties.getProperty("DatabasePassword");
        
		// attempt database connection and create Statements
		try {
			// The config.getInitParameter() Parameters are variables 
			// in the web.xml file which are declared as "init-param" element  
			// These web.xml variables are used to connect to the database 
			Class.forName(DriverName);
			SQLConnection = DriverManager.getConnection(DatabaseURL,DatabaseUser,DatabasePassword);

			// create Statement to query database
			SQLStatement = SQLConnection.createStatement();
			
		} // end try
		// for any exception throw an UnavailableException to
		// indicate that the servlet is not currently available
		catch (Exception EX) {
            
			// Print the Stack Trace
			EX.printStackTrace();
			
			// (Database is Unavailable) Write the Error Response
			String UnavailableErrorMessage = "The Database is Unavailable. Please Try again later.";
			// Replaces "GlobalMethods.writeForwardHTMLErrorResponse(Request, Response, GlobalTools.GTV_Homepage, UnavailableErrorMessage);"
			throw new RuntimeException(UnavailableErrorMessage);
			
		} // end catch
        
    }
	
	
	/*************************************************************************************************
	NAME:        isUserCurrentlyLoggedIn
	DESCRIPTION: Lets you know if the current user is logged in by reading the active session.
	PARAMETERS:  (HttpServletRequest Request, HttpServletResponse Response)
	RETURN:      VOID
	SIDE-EFFECT: If provided a "SuccessMessage" It will alert before forwarding.
	*************************************************************************************************/
	// Returns a Successful HTML Response and forward appropriately.
	public static boolean isUserCurrentlyLoggedIn(HttpServletRequest Request, HttpServletResponse Response){

	// Assume we don't have a Session Account ID
	String SessionAccountID = "";

	// Returns null if no session already exists 
	HttpSession CurrentSession =  Request.getSession(false);

	// If we have a session attempt to retrieve the SessionAccountID
	if (CurrentSession != null) {SessionAccountID = (String) CurrentSession.getAttribute("AccountID");}

	// If attempts to retrieve the SessionAccountID returned null, make it an Empty String Object for operations
	if (SessionAccountID == null) {SessionAccountID = "";}

	// Check weather or not the user is logged Currently logged in
	if(SessionAccountID.equals("")){
	// User is NOT Currently Logged In
	return false;
	}else{
	// User is Currently Logged In
	return true;
	}

	}
	
	/*************************************************************************************************
	NAME:        displayUserStatusMessage
	DESCRIPTION: Displays a Fixed DIV that reminds non activated users how to activate.
	PARAMETERS:  (JspWriter out, String SessionUsername, String SessionIsActivated)
	RETURN:      VOID
	SIDE-EFFECT: NONE.
	*************************************************************************************************/
	// Returns a Successful HTML Response and forward appropriately.
	public static void displayUserStatusMessage(JspWriter out, String SessionUsername, String SessionIsActivated, String SessionIsVerified) throws IOException{
		
		// START Activation HTML Response
		out.println("");
		out.println("<style>");
		out.println("");
		out.println(".ActivationFixedMessage {");
		out.println("top: auto;");
		out.println("left: auto;");
		out.println("max-height: 100%;");
		out.println("width: 100%;");
		out.println("overflow-y: auto;");
		out.println("color: black;");
		out.println("background-color: orange;");
		out.println("border: solid 1px red;");
		out.println("padding: 2px 5px;");
		out.println("margin: auto;");
		out.println("text-align: center;");
		out.println("position: relative;");
		out.println("}");
		out.println("");
		out.println("</style>");
		out.println("");
        
		// If SessionIsActivated is 'N' for No, then show the message below.
		if(SessionIsActivated.equals("N")){
		
		// If the user is Not activated, point them to the Account Activation Site
		String AccountActivationURL = GlobalTools.GTV_PayPalRegistrationSubmit + "?" + "RegistrationUsername=" + SessionUsername;
		
		out.println("<div class='ActivationFixedMessage'>");
		out.println("<p>This account is not activated. Please <a href='" + AccountActivationURL + "'>Click here</a> to activate your account.</p>");
		out.println("</div>");
	    
	    }
		// END Activation HTML Response

		// START Verification HTML Response
		out.println("");
		out.println("<style>");
		out.println("");
		out.println(".VerificationFixedMessage {");
		out.println("top: auto;");
		out.println("left: auto;");
		out.println("max-height: 100%;");
		out.println("width: 100%;");
		out.println("overflow-y: auto;");
		out.println("color: black;");
		out.println("background-color:cyan;");
		out.println("border: solid 1px red;");
		out.println("padding: 2px 5px;");
		out.println("margin: auto;");
		out.println("text-align: center;");
		out.println("position: relative;");
		out.println("}");
		out.println("");
		out.println("</style>");
		out.println("");
        
		// If SessionIsActivated is 'N' for No, then show the message below.
		if(SessionIsVerified.equals("N")){
		
		// If the user is Not activated, point them to the Account Activation Site
		String AccountActivationURL = GlobalTools.GTV_Settings_EMailVerification;
			
		out.println("<div class='VerificationFixedMessage'>");
		out.println("<p>Your E-Mail is not verified. Please <a href='" + AccountActivationURL + "'>Click here</a> to verify your E-Mail.</p>");
		out.println("</div>");
	    
	    }
		// END Verification HTML Response
		
		
		
		
		
		
		
		// Close the stream to complete the page
		//out.close();
		
	}	
	
	/*************************************************************************************************
	NAME:        getTableColumnAndValuePairViaAccountID
	DESCRIPTION: Gets all the Column Names and Values from a specific Table using
	a desired AccountID. It allows you to choose the columns via an Array.
	Once the 2D ArrayList<ArrayList<String>> is retrieved the data is retrieved as following
	Index 0 = Column Names
	Index 1 = Values
	PARAMETERS:  (HttpServletRequest Request, HttpServletResponse Response, String TableName, String SessionAccountID, String [] Table_Column_Ignore)
	RETURN:      ArrayList<ArrayList<String>>
	SIDE-EFFECT: NONE.
	*************************************************************************************************/
	public static ArrayList<ArrayList<String>> getTableColumnAndValuePairViaAccountID(HttpServletRequest Request, HttpServletResponse Response, String TableName, String SessionAccountID, String [] Table_Column_Add) throws SQLException {
		
		// Temporary ArrayList Variables
		ArrayList<ArrayList<String>> Temporary_ColumnValuePair = new ArrayList<ArrayList<String>>();
		ArrayList <String> Temporary_Column_List = new ArrayList<String>();
		ArrayList <String> Temporary_Values_List = new ArrayList<String>();

		// Generate the SQL Select Query
		// NOTE: Where clause strings are not case sensitive
		String ColumnsToSelect = "";
		for(int i = 0; i < Table_Column_Add.length; i++){
			ColumnsToSelect = ColumnsToSelect.concat(Table_Column_Add[i]);
			if(i < (Table_Column_Add.length -1)){ ColumnsToSelect = ColumnsToSelect.concat(" ,"); }
		}
		String SelectSQLQuery = "SELECT "+ColumnsToSelect+" FROM "+TableName+" WHERE Account_ID="+SessionAccountID+";";
		
		// Get the SQLQueryOutput
		ResultSet SQLQueryResultSetOutput = SQLStatement.executeQuery(SelectSQLQuery);
		
		// If we DO NOT Have an Empty Result Set, Work with it and send a successful HTML Response
		if(SQLQueryResultSetOutput.next()){
		
		// Reset the Pointer changed by the if statement above
			SQLQueryResultSetOutput.beforeFirst();
		
		// Records the ResultSetMetaData
		ResultSetMetaData SQLQueryOutputMetaData = null;
		
		try {
	
	        // Get the ResultSetMetaData
		    SQLQueryOutputMetaData = SQLQueryResultSetOutput.getMetaData();
	
		} catch (SQLException sqlException) {
	        sqlException.printStackTrace();		

		}
		
    	try {
        
    	// Go over the Rows
    	while (SQLQueryResultSetOutput.next()) {

    	// Go over the Columns
    	// NOTE : i = 2 because we want to skip the first item (Account_ID)
    	for(int i = 1 ; i < SQLQueryOutputMetaData.getColumnCount() + 1; i++){
    		
    		// Get the Current SQL Result Values
    		String CurrentColumnName = SQLQueryOutputMetaData.getColumnName(i);
    		String CurrentValue = SQLQueryResultSetOutput.getString(i);
            
    		// Add the current Column and Value
    		Temporary_Column_List.add(CurrentColumnName);
    		Temporary_Values_List.add(CurrentValue);
    		
		}
			
		}
			} catch (SQLException SQLEX) {
					SQLEX.printStackTrace();
				}
			}

		// Add the Columns and Values List to the ColumnValue Pair List
		Temporary_ColumnValuePair.add(Temporary_Column_List);
		Temporary_ColumnValuePair.add(Temporary_Values_List);
		
		return Temporary_ColumnValuePair;
		
	}
	
	/*************************************************************************************************
	NAME:        getTableColumnAndValuePairViaExerciseID
	DESCRIPTION: Gets all the Column Names and Values from a specific Table using
	a desired UniqueColumnName and UniqueColumnValue. It allows you to choose the columns via an Array.
	Once the 2D ArrayList<ArrayList<String>> is retrieved the data is retrieved as following
	Index 0 = Column Names
	Index 1 = Values
	PARAMETERS:  (HttpServletRequest Request, HttpServletResponse Response, String TableName, String UniqueColumnName, String UniqueColumnValue, String [] Table_Column_Add)
	RETURN:      ArrayList<ArrayList<String>>
	SIDE-EFFECT: NONE.
	*************************************************************************************************/
	public static ArrayList<ArrayList<String>> getTableColumnAndValuePairData(HttpServletRequest Request, HttpServletResponse Response, String TableName, String UniqueColumnName, String UniqueColumnValue, String [] Table_Column_Add) throws SQLException {
		
		// Temporary ArrayList Variables
		ArrayList<ArrayList<String>> Temporary_ColumnValuePair = new ArrayList<ArrayList<String>>();
		ArrayList <String> Temporary_Column_List = new ArrayList<String>();
		ArrayList <String> Temporary_Values_List = new ArrayList<String>();

		// Generate the SQL Select Query
		// NOTE: Where clause strings are not case sensitive
		String ColumnsToSelect = "";
		for(int i = 0; i < Table_Column_Add.length; i++){
			ColumnsToSelect = ColumnsToSelect.concat(Table_Column_Add[i]);
			if(i < (Table_Column_Add.length -1)){ ColumnsToSelect = ColumnsToSelect.concat(" ,"); }
		}
		String SelectSQLQuery = "SELECT "+ColumnsToSelect+" FROM "+TableName+" WHERE "+UniqueColumnName+"='"+UniqueColumnValue+"';";
		
		// Get the SQLQueryOutput
		ResultSet SQLQueryResultSetOutput = SQLStatement.executeQuery(SelectSQLQuery);
		
		// If we DO NOT Have an Empty Result Set, Work with it and send a successful HTML Response
		if(SQLQueryResultSetOutput.next()){
		
		// Reset the Pointer changed by the if statement above
			SQLQueryResultSetOutput.beforeFirst();
		
		// Records the ResultSetMetaData
		ResultSetMetaData SQLQueryOutputMetaData = null;
		
		try {
	
	        // Get the ResultSetMetaData
		    SQLQueryOutputMetaData = SQLQueryResultSetOutput.getMetaData();
	
		} catch (SQLException sqlException) {
	        sqlException.printStackTrace();		

		}
		
    	try {
        
    	// Go over the Rows
    	while (SQLQueryResultSetOutput.next()) {

    	// Go over the Columns
    	// NOTE : i = 2 because we want to skip the first item (Account_ID)
    	for(int i = 1 ; i < SQLQueryOutputMetaData.getColumnCount() + 1; i++){
    		
    		// Get the Current SQL Result Values
    		String CurrentColumnName = SQLQueryOutputMetaData.getColumnName(i);
    		String CurrentValue = SQLQueryResultSetOutput.getString(i);
            
    		// Add the current Column and Value
    		Temporary_Column_List.add(CurrentColumnName);
    		Temporary_Values_List.add(CurrentValue);
    		
		}
			
		}
			} catch (SQLException SQLEX) {
					SQLEX.printStackTrace();
				}
			}

		// Add the Columns and Values List to the ColumnValue Pair List
		Temporary_ColumnValuePair.add(Temporary_Column_List);
		Temporary_ColumnValuePair.add(Temporary_Values_List);
		
		return Temporary_ColumnValuePair;
		
	}
	
	/*************************************************************************************************
	NAME:        getSingleTableCellData
	DESCRIPTION: Gets Data from a specific table
	PARAMETERS:  (HttpServletRequest Request, HttpServletResponse Response, String SessionAccountID)
	RETURN:      void
	SIDE-EFFECT: NONE.
	*************************************************************************************************/
	public static String getSingleTableCellData(HttpServletRequest Request, HttpServletResponse Response, String TableName, String ColumnToSelect, String WhereColumnName, String WhereColumnValue) throws SQLException {
		
		// Records the assigned PreviousLastColumnToSelectValue from the Table from the Current User.
		String PreviousLastColumnToSelectValue = "";
		
		// Select the ColumnToSelect From the Current User.
		String ColumnToSelectSQLQuery = "SELECT "+ColumnToSelect+" FROM "+TableName+" WHERE "+WhereColumnName+" ='"+WhereColumnValue+"';";
		
		// Get the ColumnToSelectSQLQueryOutput ResultSet
		ResultSet ColumnToSelectSQLQueryOutput = SQLStatement.executeQuery(ColumnToSelectSQLQuery);
		
		// Check if this ColumnToSelect record already exists in the Table
		if(ColumnToSelectSQLQueryOutput.next()){

		// If it does, simply return the value
					
		// Get the Table PreviousLastColumnToSelectValue for the Current User.
		PreviousLastColumnToSelectValue = ColumnToSelectSQLQueryOutput.getString(ColumnToSelect);
		
		// Close the ResultSet
		try {ColumnToSelectSQLQueryOutput.close();} catch (SQLException e) {e.printStackTrace();}
		
		return PreviousLastColumnToSelectValue;
		
		}else{
			// If no data was recovered from the Database return null
			return null;
		}

	}
	
	/*************************************************************************************************
	NAME:        setSingleTableCellData
	DESCRIPTION: Sets Data for a specific table
	PARAMETERS:  (HttpServletRequest Request, HttpServletResponse Response, String SessionAccountID, String New_Exercise_ID)
	RETURN:      void
	SIDE-EFFECT: NONE.
	*************************************************************************************************/
	public static void setSingleTableCellData(HttpServletRequest Request, HttpServletResponse Response, 
			String TableName,
			String UniqueKeyName,
			String UniqueKeyValue,
			String SetColumnName,
			String SetColumnValue,
			String WhereColumnName,
			String WhereColumnValue) 
			throws SQLException {
		
					
		// Select the Exercise_ID From the Current User.
		String ProgramCheckpointSQLQuery = "SELECT "+SetColumnName+" FROM "+TableName+" WHERE "+WhereColumnName+"='"+WhereColumnValue+"';";
		
		// Get the LastExerciseIDSQLQueryOutput ResultSet
		ResultSet LastExerciseIDSQLQueryOutput = SQLStatement.executeQuery(ProgramCheckpointSQLQuery);
					
		// Check if this Exercise_ID record already exists in the rlf_programs_checkpoints Table
		if(LastExerciseIDSQLQueryOutput.next()){

		// If it does, simply update the existing record since it already exists in the Table

		// Program Checkpoint Update Query
		String UpdateProgramCheckpointSQLQuery = "UPDATE "+TableName+" SET "+SetColumnName+"='"+SetColumnValue+"' WHERE "+WhereColumnName+"='"+WhereColumnValue+"';";
		
		// Update the Program Checkpoint Entry
		// 1 = Update Successful
		// 0 = Update Failed
		int SQLQueryResultsCode = SQLStatement.executeUpdate(UpdateProgramCheckpointSQLQuery);
					
		// If the Update SQL Query Failed to Update, throw an exception
		if(SQLQueryResultsCode == 0){
		throw new RuntimeException("Failed to update the account mark for your Newsletter subscription.");
		}
					
		}else{
					
		// If it doesn't, simply add a new record to the table
		
		// Create an Entry for the new account that was created in the Table.
		String InsertProgramCheckpointSQLQuery = "INSERT INTO "+TableName+" (" +
				""+UniqueKeyName+"," +
				""+SetColumnName+"" +
				")" +
				"VALUES(" +
				"\""+UniqueKeyValue+"\"," +
				"\""+SetColumnValue+"\"" +
				");" +
				"";

		// Create the Table Entry
		SQLStatement.executeUpdate(InsertProgramCheckpointSQLQuery);
						
					}

		// Close the ResultSet
		try {LastExerciseIDSQLQueryOutput.close();} catch (SQLException e) {e.printStackTrace();}
		
	}
		
	/*************************************************************************************************
	NAME:        randomize2DArrayList
	DESCRIPTION: Randomize all Nodes of a 2D ArrayList using a Long data type Key to keep the randomizing consistency.
	PARAMETERS:  (ArrayList <ArrayList<String>> Master2DXMLRequestArrayList, long LongKey)
	RETURN:      void
	SIDE-EFFECT: NONE.
	*************************************************************************************************/
	public static void randomize2DArrayList(ArrayList <ArrayList<String>> Master2DXMLRequestArrayList, long LongKey){
		
		// Randomize all Nodes from the 2D Array List
		for(int i = 0 ; i < Master2DXMLRequestArrayList.size(); i++){
		Collections.shuffle(Master2DXMLRequestArrayList.get(i), new Random(LongKey));
		}
		
	}
	
	/*************************************************************************************************
	NAME:        shiftSequential2DArrayListElements
	DESCRIPTION: Goes through a 2D ArrayList and couples Elements that should be next to each 
	other delimited by the "LeftEndsWithDelimiter" and "RightEndsWithDelimiter" of a "ElementShiftLeaderColumn".
	PARAMETERS: (ArrayList <ArrayList<String>> Master2DXMLRequestArrayList, int ElementShiftLeaderColumn, String LeftEndsWithDelimiter, String RightEndsWithDelimiter)
	RETURN:      void
	SIDE-EFFECT: NONE.
	*************************************************************************************************/
	public static void shiftSequential2DArrayListElements(ArrayList <ArrayList<String>> Master2DXMLRequestArrayList, int ElementShiftLeaderColumn, String LeftEndsWithDelimiter, String RightEndsWithDelimiter){
		
		// Temporary Alteration for "ElementShiftLeaderColumn" to avoid running over the same shifted columns
		String RightTemporaryLeaderColumnAlteration = "RDGX";
		String LeftTemporaryLeaderColumnAlteration = "LDGX";
		
		// Iterate through the 2D Array List until All Right Versions are removed (RightEndsWithDelimiter).
		while(true){
		
			// Assume that the 2D Array has no Right Versions (RightEndsWithDelimiter)
			boolean RightVersionExists = false;
			
			// Look through the 2D Array using the "ElementShiftLeaderColumn"
			for(int i = 0 ; i < Master2DXMLRequestArrayList.get(ElementShiftLeaderColumn).size(); i++){
				
			// Get the current "ElementShiftLeaderColumn" value
			String CurrentValue = Master2DXMLRequestArrayList.get(ElementShiftLeaderColumn).get(i);
			
			// If at any point the current "ElementShiftLeaderColumn" element value ends with (RightEndsWithDelimiter) 
			// Shift all the 2D Array List column indexes to the right of the (LeftEndsWithDelimiter)
			if(CurrentValue.endsWith(RightEndsWithDelimiter)){
				
				// Since CurrentValue.endsWith(RightEndsWithDelimiter) Mark RightVersionExists as True
				RightVersionExists = true;
				
				// Get the current Element's new Index to add it there and Old Index to remove it from
				int ShiftAddIndex = Master2DXMLRequestArrayList.get(ElementShiftLeaderColumn).indexOf(CurrentValue.replaceAll(RightEndsWithDelimiter, LeftEndsWithDelimiter)) + 1;
				
				// find when to keep as i or when to do i-1 when choosing an Index to remove
				int ShiftRemoveIndex = 0;
				if(ShiftAddIndex > i){
					ShiftRemoveIndex = i;
				}else{
					ShiftRemoveIndex = i + 1;
				}
				
				// use the ShiftAddIndex and ShiftRemoveIndex to shift all 2D Array Elements in accordance of the Shift led by the "ElementShiftLeaderColumn"
				for(int k = 0; k < Master2DXMLRequestArrayList.size(); k++){
				
				// If we are shifting the "ElementShiftLeaderColumn" Alter the Shifted value by adding the "TemporaryLeaderColumnAlteration" so we can skip it in future iterations
				if(k == ElementShiftLeaderColumn){
					
					// Right Temporary Leader Column Alteration
					Master2DXMLRequestArrayList.get(k).add(ShiftAddIndex, Master2DXMLRequestArrayList.get(k).get(i) + RightTemporaryLeaderColumnAlteration);
					
					// Left Temporary Leader Column Alteration
					Master2DXMLRequestArrayList.get(k).set(ShiftAddIndex - 1, Master2DXMLRequestArrayList.get(k).get(ShiftAddIndex - 1) + LeftTemporaryLeaderColumnAlteration);
					
				}else{
					// Else, Shift as Usual for all other columns
					Master2DXMLRequestArrayList.get(k).add(ShiftAddIndex, Master2DXMLRequestArrayList.get(k).get(i));
				}
				// Remove the old Current Value which we just re-added to complete the current value Shift
				Master2DXMLRequestArrayList.get(k).remove(ShiftRemoveIndex);
				}
				
				// Break out of the for loop to continue going forward searching for new Right Versions (RightEndsWithDelimiter)
				break;
				
			}
			
		}

		// If at Any point we don't encounter any new Right Versions (RightEndsWithDelimiter) undo any "ElementShiftLeaderColumn" alterations and exit the while loop
		if(RightVersionExists == false){
			
			// Remove all Temporary Leader Column Alterations
			for(int i = 0 ; i < Master2DXMLRequestArrayList.get(ElementShiftLeaderColumn).size(); i++){
				
				// Right Temporary Leader Column Alteration
				Master2DXMLRequestArrayList.get(ElementShiftLeaderColumn).set(i, Master2DXMLRequestArrayList.get(ElementShiftLeaderColumn).get(i).replaceAll(RightTemporaryLeaderColumnAlteration, ""));
				
				// Left Temporary Leader Column Alteration
				Master2DXMLRequestArrayList.get(ElementShiftLeaderColumn).set(i, Master2DXMLRequestArrayList.get(ElementShiftLeaderColumn).get(i).replaceAll(LeftTemporaryLeaderColumnAlteration, ""));
			}
			
			break;
			}
		
		}
		
	}
	
		/*************************************************************************************************
		NAME:        writeForwardHTMLSuccessResponse
		DESCRIPTION: Handle successful forwarding responses.
		PARAMETERS:  (HttpServletRequest Request, HttpServletResponse Response)
		RETURN:      VOID
		SIDE-EFFECT: If provided a "SuccessMessage" It will alert before forwarding.
		*************************************************************************************************/
		// Returns a Successful HTML Response and forward appropriately.
		public static void writeForwardHTMLSuccessResponse(HttpServletRequest Request, HttpServletResponse Response, String ForwardAddress, String SuccessMessage){

			// Create the PrintWriter to print the HTML Page Response
			// The response is sent to the client through the PrintWriter object
			// obtained from the HttpServletResponse object.
			// NOTE: If the response is binary data, such as an image, method
			// getOutputStream is used to obtain a reference to a
			// ServletOutputStream object.
			
			// set up response to client
			Response.setContentType("text/html");
			PrintWriter out = null;
			try {
				out = Response.getWriter();
			} catch (IOException e) {
				System.out.println("INTERNAL ERRROR: writeForwardHTMLSuccessResponse()");
				e.printStackTrace();
			}
			
			out.println("<?xml version = '1.0'?>");
			out.println("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'>");
			out.println("<html xmlns='http://www.w3.org/1999/xhtml'>");
			out.println("<head>");
			out.println("<title>Login Servlet</title>");
			out.println("</head>");
			out.println("<body>");
			
			// Alert if "SuccessMessage" is not Empty
			if(!SuccessMessage.equals("")){
			out.println("<script type='text/javascript'>");
			out.println("alert('"+SuccessMessage+"');");
			out.println("</script>");
			}
			
			out.println("<meta http-equiv='REFRESH' content='0;url="+ForwardAddress+"'/>");
			out.println("</body>");
			out.println("</html>");

			// Close the stream to complete the page
			out.close();
			
		}
		
		/*************************************************************************************************
		NAME:        writeHTMLErrorResponse
		DESCRIPTION: Handle failure forwarding responses.
		PARAMETERS:  (HttpServletRequest Request, HttpServletResponse Response)
		RETURN:      VOID
		SIDE-EFFECT: If provided an "ErrorMessage" It will alert before forwarding.
		*************************************************************************************************/
		
		// Returns an Error Message HTML Response and forward appropriately.
		public static void writeForwardHTMLErrorResponse(HttpServletRequest Request, HttpServletResponse Response,String ForwardAddress, String ErrorMessage){

			// Create the PrintWriter to print the HTML Page Response
			// The response is sent to the client through the PrintWriter object
			// obtained from the HttpServletResponse object.
			// NOTE: If the response is binary data, such as an image, method
			// getOutputStream is used to obtain a reference to a
			// ServletOutputStream object.
			
			// set up response to client
			Response.setContentType("text/html");
			PrintWriter out = null;
			try {
				out = Response.getWriter();
			} catch (IOException e) {
				System.out.println("INTERNAL ERRROR: writeForwardHTMLErrorResponse()");
				e.printStackTrace();
			}
			
			out.println("<?xml version = '1.0'?>");
			out.println("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'>");
			out.println("<html xmlns='http://www.w3.org/1999/xhtml'>");
			out.println("<head>");
			out.println("<title>Internal Error!</title>");
			out.println("</head>");
			out.println("<body>");
			
			// Alert if "ErrorMessage" is not Empty
			if(!ErrorMessage.equals("")){
			out.println("<script type='text/javascript'>");
			out.println("alert('"+ErrorMessage+"');");
			out.println("</script>");
			}
			
			out.println("<meta http-equiv='REFRESH' content='0;url="+ForwardAddress+"'/>");
			out.println("</body>");
			out.println("</html>");

			// Close the stream to complete the page
			out.close();
			
		}
	
		/*************************************************************************************************
		NAME:        writeHTMLNotificationResponse
		DESCRIPTION: Handle failure forwarding responses.
		PARAMETERS:  (HttpServletRequest Request, HttpServletResponse Response)
		RETURN:      VOID
		SIDE-EFFECT: If provided an "ErrorMessage" It will alert before forwarding.
		*************************************************************************************************/
		
		// Returns an Error Message HTML Response and forward appropriately.
		public static void writeHTMLNotificationResponse(HttpServletRequest Request, HttpServletResponse Response,String ForwardAddress, String NotificationMessage){

			// Create the PrintWriter to print the HTML Page Response
			// The response is sent to the client through the PrintWriter object
			// obtained from the HttpServletResponse object.
			// NOTE: If the response is binary data, such as an image, method
			// getOutputStream is used to obtain a reference to a
			// ServletOutputStream object.
			
			// set up response to client
			Response.setContentType("text/html");
			PrintWriter out = null;
			try {
				out = Response.getWriter();
			} catch (IOException e) {
				System.out.println("INTERNAL ERRROR: writeHTMLNotificationResponse()");
				e.printStackTrace();
			}
			
			out.println("<?xml version = '1.0'?>");
			out.println("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'>");
			out.println("<html xmlns='http://www.w3.org/1999/xhtml'>");
			out.println("<head>");
			out.println("<title>Internal Error!</title>");
			out.println("</head>");
			out.println("<body>");
			
			// Alert if "ErrorMessage" is not Empty
			if(!NotificationMessage.equals("")){
			out.println("<script type='text/javascript'>");
			out.println("alert('"+NotificationMessage+"');");
			out.println("</script>");
			}
			
			out.println("<meta http-equiv='REFRESH' content='0;url="+ForwardAddress+"'/>");
			out.println("</body>");
			out.println("</html>");

			// Close the stream to complete the page
			out.close();
			
		}
		
		/*************************************************************************************************
		NAME:        printPageMenuItems
		DESCRIPTION: Dynamically prints the Menu Item for various Page Types.
		PARAMETERS:  (JspWriter out, String PageType, String SessionFirstName)
		RETURN:      VOID
		SIDE-EFFECT: NONE.
		*************************************************************************************************/
		public static void printPageMenuItems(JspWriter out, String PageType, String SessionFirstName) throws IOException{
		
			/*
			<ul>
			<%
			// Print the Home Menu Items
			GlobalTools.printPageMenuItems(out,"Home","");
			%>
			</ul>
			*/
			if(PageType.equals("Home")){
			out.println("<li><a href='#'></a></li>");
			out.println("<li><a href='#'></a></li>");
			out.println("<li><a href='#'></a></li>");
			out.println("<li><a href='#'></a></li>");
			out.println("<li><a href='"+GlobalTools.GTV_Settings_NewsletterSubscription+"?UserType=AnonymousEnable'>Newsletter</a></li>");
			out.println("<li><a href='"+GlobalTools.GTV_ContactUs+"'>Contact Us</a></li>");
			}
			
			/*
			<ul>
			<%
			// Print the Logged In Menu Items
			GlobalTools.printPageMenuItems(out,"LoggedIn",SessionFirstName);
			%>
			</ul>
			*/
			if(PageType.equals("LoggedIn")){
			out.println("<li><a href='"+GlobalTools.GTV_UserProfile+"'>"+SessionFirstName+"</a></li>");
			out.println("<li><a href='"+GlobalTools.GTV_RLFService_GettingStarted+"'>Getting Started</a></li>");
			out.println("<li><a href='"+GlobalTools.GTV_RLFService_RLFRegimen+"'>Workout Regimen</a></li>");
			out.println("<li><a href='"+GlobalTools.GTV_RLFService_RLFMealManager+"'>Meal Manager</a></li>");
			out.println("<li><a href='"+GlobalTools.GTV_UserSettings+"'>Settings</a></li>");
			out.println("<li><a href='/LogOutServlet'>Log Out</a></li>");
			}
			
			/*
			<ul>
			<%
			// Print the Logged Out Menu Items
			GlobalTools.printPageMenuItems(out,"LoggedOut","");
			%>
			</ul>
			*/
			if(PageType.equals("LoggedOut")){
			out.println("<li><a href='"+GlobalTools.GTV_Homepage+"'>Home</a></li>");
			out.println("<li><a href='#'></a></li>");
			out.println("<li><a href='#'></a></li>");
			out.println("<li><a href='#'></a></li>");
			out.println("<li><a href='#'></a></li>");
			out.println("<li><a href='#'></a></li>");
			}
			
			/*
			<ul>
			<%
			// Print the Empty Menu Items
			GlobalTools.printPageMenuItems(out,"Empty","");
			%>
			</ul>
			*/
			if(PageType.equals("Empty")){
			out.println("<li><a href='#'></a></li>");
			out.println("<li><a href='#'></a></li>");
			out.println("<li><a href='#'></a></li>");
			out.println("<li><a href='#'></a></li>");
			out.println("<li><a href='#'></a></li>");
			out.println("<li><a href='#'></a></li>");
			}
			
		}
			
		/*************************************************************************************************
		NAME:        isSameDay
		DESCRIPTION: Compares an Old Date Vs. a New Date and lets you know if they are from different days of the year (Calculates in 365 Days)
		PARAMETERS: (String OldDateEvent, String NewDateEvent)
		RETURN:      VOID
		SIDE-EFFECT: NONE.
		*************************************************************************************************/
		public static boolean isSameDay(String OldDateEvent, String NewDateEvent) throws ParseException{
			
			//HH converts hour in 24 hours format (0-23), day calculation
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			// Get the Calendar Object for the OldDateEvent
			Date OldEventDateObject = format.parse(OldDateEvent);
			Calendar OldEventCalendarObject = Calendar.getInstance();
			OldEventCalendarObject.setTime(OldEventDateObject);
			
			// Get the Calendar Object for the NewDateEvent
			Date NewEventDateObject = format.parse(NewDateEvent);
			Calendar NewEventCalendarObject = Calendar.getInstance();
			NewEventCalendarObject.setTime(NewEventDateObject);
			
			// Get the Unique Date ID OldDayOfYearPlusYear and NewDayOfYearPlusYear for comparison
			String OldDayOfYearPlusYear = OldEventCalendarObject.get(Calendar.YEAR) + "-" + OldEventCalendarObject.get(Calendar.YEAR);
			String NewDayOfYearPlusYear = NewEventCalendarObject.get(Calendar.YEAR) + "-" + NewEventCalendarObject.get(Calendar.YEAR);
			
			// Compare the Old Date Vs. the New Date and return if It's a New Day
			if (OldDayOfYearPlusYear.equals(NewDayOfYearPlusYear)) {
				   //System.out.println("It's the same day");
				   return true;
			   }else{
				   //System.out.println("It's NOT the same day");
				   return false;
			   }
			
		}
		
		/*************************************************************************************************
		NAME:        isLinkExpired
		DESCRIPTION: Compares a Start Date Vs. a Stop Date and if It's older than the specified hours mark it as expired (Calculates in 24 Hours)
		Also allow the user to set how many hours would have to pass before the item is marked as Expired
		PARAMETERS: (String OldDateAndTime, int HoursPassedToMarkAsExpired)
		RETURN:      VOID
		SIDE-EFFECT: NONE.
		*************************************************************************************************/
		public static boolean isLinkExpired(String OldDateAndTime, int HoursPassedToMarkAsExpired){
	 
	        // Get the Current Date
			// NOTE: "HH" converts hour in 24 hours format (0-23), day calculation
			Calendar c = Calendar.getInstance();
			SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String NewDateAndTime = SDF.format(c.getTime());
			
			// Declare the OldDateAndTime and NewDateAndTime Date Objects
			Date OldDateObject = null;
			Date NewDateObject = null;
	 
			try {
				OldDateObject = SDF.parse(OldDateAndTime);
				NewDateObject = SDF.parse(NewDateAndTime);
	 
				//in milliseconds
				long diff = NewDateObject.getTime() - OldDateObject.getTime();
	 
				//long diffSeconds = diff / 1000 % 60;
				//long diffMinutes = diff / (60 * 1000) % 60;
				long diffHours = diff / (60 * 60 * 1000) % 24;
				long diffDays = diff / (24 * 60 * 60 * 1000);
	 
				//System.out.print(diffDays + " days, ");
				//System.out.print(diffHours + " hours, ");
				//System.out.print(diffMinutes + " minutes, ");
				//System.out.print(diffSeconds + " seconds.");
	 
				// Set the URL as expired if It's over 1 day old
				if (diffDays > 0) { 
					//System.out.println("\nDays expired it"); 
					return true;
				}
				
				// Set the URL as expired if It's over the specified hours
				if (diffHours >= HoursPassedToMarkAsExpired) { 
					//System.out.println("\nHours expired it"); 
					return true;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
			
		}

		/*************************************************************************************************
		NAME:        updateGenericTableCellData (INCOMPLETE BETA!!!!!!!!!!!!!!!)
		DESCRIPTION: Gets all the Column Names and Values from a specific Table using
		a desired UniqueColumnName and UniqueColumnValue. It allows you to choose the columns via an Array.
		Once the 2D ArrayList<ArrayList<String>> is retrieved the data is retrieved as following
		Index 0 = Column Names
		Index 1 = Values
		PARAMETERS:  (HttpServletRequest Request, HttpServletResponse Response, String TableName, String UniqueColumnName, String UniqueColumnValue, String [] Table_Column_Add)
		RETURN:      ArrayList<ArrayList<String>>
		SIDE-EFFECT: NONE.
		*************************************************************************************************/
		public static void updateGenericTableCellData(
		HttpServletRequest Request, HttpServletResponse Response, 
		String TableName, 
		String WhereColumnName,
		String WhereColumnValue,
		String UpdateColumnName,
		String UpdateColumnValue,
		String [] Table_Column_Add) 
		throws SQLException {
			
			// Variables for Account Exercise CheckPoint Entries

			// TableName = rlf_programs_checkpoints
			// WhereColumnName = Account_ID
			// WhereColumnValue = 1000000006
			// UpdateColumnName = Last_Exercise_ID // MAKE ADAPT ARRAY
			// UpdateColumnValue = 18 // MAKE ADAPT ARRAY
			
			String Last_Exercise_ID_Saved_On = "NOW()";
			
	/* START RLF_Newsletters INSERT */
	/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ */
						
			// Select the Exercise_ID From the Current User.
			//String ProgramCheckpointSQLQuery = "SELECT Last_Exercise_ID FROM rlf_programs_checkpoints WHERE Account_ID ='"+UniqueColumnValue+"';";
			String ProgramCheckpointSQLQuery = "SELECT "+UpdateColumnName+" FROM "+TableName+" WHERE "+WhereColumnName+" ='"+WhereColumnValue+"';";
			
			// Get the LastExerciseIDSQLQueryOutput ResultSet
			ResultSet LastExerciseIDSQLQueryOutput = SQLStatement.executeQuery(ProgramCheckpointSQLQuery);
						
			// Check if this Exercise_ID record already exists in the rlf_programs_checkpoints Table
			if(LastExerciseIDSQLQueryOutput.next()){

			// If it does, simply update the existing record since it already exists in the Table

			// Program Checkpoint Update Query
			String UpdateProgramCheckpointSQLQuery = "UPDATE "+TableName+" SET "+UpdateColumnName+"='"+UpdateColumnValue+"', Last_Exercise_ID_Saved_On="+Last_Exercise_ID_Saved_On+" WHERE "+WhereColumnName+"='"+WhereColumnValue+"';";
			
			// Update the Program Checkpoint Entry
			// 1 = Update Successful
			// 0 = Update Failed
			int SQLQueryResultsCode = SQLStatement.executeUpdate(UpdateProgramCheckpointSQLQuery);
						
			// If the Update SQL Query Failed to Update, throw an exception
			if(SQLQueryResultsCode == 0){
			throw new RuntimeException("Failed to update the account mark for your Newsletter subscription.");
			}
						
			}else{
						
			// If it doesn't, simply add a new record to the table
			
			// Create an Entry for the new account that was created in the RLF_NewsLetters Table.
			String InsertProgramCheckpointSQLQuery = "INSERT INTO rlf_programs_checkpoints (" +
					"Account_ID," +
					"Last_Exercise_ID," +
					"Last_Exercise_ID_Saved_On" +
					")" +
					"VALUES(" +
					"\""+WhereColumnValue+"\"," +
					"\""+UpdateColumnValue+"\"," +
					""+Last_Exercise_ID_Saved_On+"" +
					");" +
					"";

			// Create the NewsLetter Entry
			SQLStatement.executeUpdate(InsertProgramCheckpointSQLQuery);
							
						}

	/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ */
	/* END RLF_Newsletters INSERT */	
			
			
			// Close the ResultSet
			try {LastExerciseIDSQLQueryOutput.close();} catch (SQLException e) {e.printStackTrace();}
			
		}
		
		public static void testGlobalTools(){
			System.out.println("IT WORKS !!!!!!!!!!!!!!");
		}
				
}
