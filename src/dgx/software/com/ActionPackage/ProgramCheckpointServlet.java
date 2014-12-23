/* 
GOAL: Logs the user into the system and creates a session.

PROPERTIES: Front-End Work / Back-End Work
1. User Page Display (HTML/CSS)
2. User Page Interactions (HTML/JavaScript)
3. User SQL Database Access (SQL)
*/

package dgx.software.com.ActionPackage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ProgramCheckpointServlet extends HttpServlet {
	
	// SQL Connection Objects
	private Connection SQLConnection;
	private Statement SQLStatement;
	private ServletConfig InitConfig;
	
	// Initialization Method
	public void init(ServletConfig config) throws ServletException {
		
		// Initialize the InitConfig variable
		InitConfig = config;
	}

	// process "Get" requests from clients
	protected void doGet(HttpServletRequest Request, HttpServletResponse Response) throws ServletException, IOException {
		
		// Call the doPost() Method
		doPost(Request, Response);
	}

	// Create the Servlet Response
	public void doPost(HttpServletRequest Request, HttpServletResponse Response) throws IOException {

		// attempt database connection and create Statements
		try {
			// The config.getInitParameter() Parameters are variables 
			// in the web.xml file which are declared as "init-param" element  
			// These web.xml variables are used to connect to the database 
			Class.forName(InitConfig.getInitParameter("DriverName"));
			SQLConnection = DriverManager.getConnection(
				InitConfig.getInitParameter("DatabaseURL"), 
				InitConfig.getInitParameter("DatabaseUser"), 
				InitConfig.getInitParameter("DatabasePassword"));

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

		// process "Post" requests from clients
		writeServletResponse(Request, Response);

	}
	
	// Create the Servlet Response
	public void writeServletResponse(HttpServletRequest Request, HttpServletResponse Response) throws IOException {

		/* RLF_Programs_CheckPoints Table Columns
			Account_ID INT NOT NULL,
			Last_Regimen_Name varchar(64),
			Last_Program_ID INT,
			Last_Program_ID_Saved_On DATETIME,
			Last_Program_ID_Percentage INT,
			Last_Exercise_ID INT,
			Last_Exercise_ID_Saved_On DATETIME,
			Random_Exercise_Key varchar(64),
		 */
		
		// ArrayList containing all Record update or insert Field/Value pairs
		ArrayList <String> SQLQueryUpdateChangeList = new ArrayList <String>();
		
		ArrayList <String> RLF_Programs_CheckPoints_Table_Columns = new ArrayList <String>();
		RLF_Programs_CheckPoints_Table_Columns.add("Account_ID");
		RLF_Programs_CheckPoints_Table_Columns.add("Last_Regimen_Name");
		RLF_Programs_CheckPoints_Table_Columns.add("Last_Program_ID");
		RLF_Programs_CheckPoints_Table_Columns.add("Last_Program_ID_Saved_On");
		RLF_Programs_CheckPoints_Table_Columns.add("Last_Program_ID_Percentage");
		RLF_Programs_CheckPoints_Table_Columns.add("Last_Exercise_ID");
		RLF_Programs_CheckPoints_Table_Columns.add("Last_Exercise_ID_Saved_On");
		RLF_Programs_CheckPoints_Table_Columns.add("Random_Exercise_Key");
		
		for(int i = 0 ; i < RLF_Programs_CheckPoints_Table_Columns.size(); i++){
		
		String TempValue = Request.getParameter(RLF_Programs_CheckPoints_Table_Columns.get(i));
			
		if(TempValue != null){
			SQLQueryUpdateChangeList.add(TempValue);
		}else{
			SQLQueryUpdateChangeList.add(null);
		}
		
		}
		
		// Variables for Program Checkpoint
		//String SessionAccountID = Request.getParameter("SessionAccountID");
		//String New_Exercise_ID = Request.getParameter("New_Exercise_ID");
		
		/* START rlf_programs_checkpoints SQL Queries */
/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ */
							
			try {
							
			// Select the Account_ID From the Current User.
			String ProgramCheckpointSQLQuery = "SELECT Account_ID FROM rlf_programs_checkpoints WHERE "+RLF_Programs_CheckPoints_Table_Columns.get(0)+" ='"+SQLQueryUpdateChangeList.get(0)+"';";
			
			// Get the LastExerciseIDSQLQueryOutput ResultSet
			ResultSet LastExerciseIDSQLQueryOutput = SQLStatement.executeQuery(ProgramCheckpointSQLQuery);
							
			// Check if this Account_ID record already exists in the rlf_programs_checkpoints Table
			if(LastExerciseIDSQLQueryOutput.next()){

			// If it does, simply update the existing record since it already exists in the Table

			
				// START : Program Checkpoint UPDATE Query
			///////////////////////////////////////////////////////////////////////////////////////////////////
				
			// Program Checkpoint Update Query
			String UpdateProgramCheckpointSQLQuery = "UPDATE rlf_programs_checkpoints ";
			
			// Skip updating Account_ID by making i = 1
			for(int i = 1 ; i < SQLQueryUpdateChangeList.size(); i++){
			
			// Add the Where Fields and Values for narrowing data
			if(i == 1) {UpdateProgramCheckpointSQLQuery = UpdateProgramCheckpointSQLQuery + "SET ";}
			
			// Add Non Null Items as SET for the SQL Query
			if(SQLQueryUpdateChangeList.get(i) != null){
			
			// If the current value contains Parentheses like "NOW()", It's an SQL method so concatenate without single quotes
			if(SQLQueryUpdateChangeList.get(i).contains("(") && SQLQueryUpdateChangeList.get(i).contains(")")){
			UpdateProgramCheckpointSQLQuery = UpdateProgramCheckpointSQLQuery + RLF_Programs_CheckPoints_Table_Columns.get(i) + "=" + SQLQueryUpdateChangeList.get(i) +"";
			}else{
				UpdateProgramCheckpointSQLQuery = UpdateProgramCheckpointSQLQuery + RLF_Programs_CheckPoints_Table_Columns.get(i) + "='" + SQLQueryUpdateChangeList.get(i) +"'";
			}
			
			// Add appropriate Commas to the SQL Query
			// Check if you're not the last null
			for(int k = i; k < SQLQueryUpdateChangeList.size(); k++){
			
			// Break if we're on the last run
			if((k + 1) == SQLQueryUpdateChangeList.size()){
				break;
			}
				
			if(SQLQueryUpdateChangeList.get(k + 1) != null){
			UpdateProgramCheckpointSQLQuery = UpdateProgramCheckpointSQLQuery + ", ";
			break;
			}
			
			}
			
			}
			
			}
			
			// Close the Query
			//UpdateProgramCheckpointSQLQuery = UpdateProgramCheckpointSQLQuery + ";";
			UpdateProgramCheckpointSQLQuery = UpdateProgramCheckpointSQLQuery + " WHERE "+RLF_Programs_CheckPoints_Table_Columns.get(0)+" ='"+SQLQueryUpdateChangeList.get(0)+"';";
			
			///////////////////////////////////////////////////////////////////////////////////////////////////
				// END : Program Checkpoint UPDATE Query
			
			// SAMPLE: Program Checkpoint Update Query
            //String UpdateProgramCheckpointSQLQuery = "UPDATE rlf_programs_checkpoints SET Last_Exercise_ID='"+New_Exercise_ID+"', Last_Exercise_ID_Saved_On="+Last_Exercise_ID_Saved_On+" WHERE Account_ID='"+SessionAccountID+"';";
			
			// Update the Program Checkpoint Entry
			// 1 = Update Successful
			// 0 = Update Failed
			int SQLQueryResultsCode = SQLStatement.executeUpdate(UpdateProgramCheckpointSQLQuery);
							
			// If the Update SQL Query Failed to Update, throw an exception
			if(SQLQueryResultsCode == 0){
			throw new RuntimeException("Failed to update your Last Exercise ID.");
			}
							
			}else{
							
			// If it doesn't, simply add a new record to the table
			
			
				// START : Program Checkpoint INSERT Query
			///////////////////////////////////////////////////////////////////////////////////////////////////
			
			// Create an Entry for the new account that was created in the rlf_programs_checkpoints Table.
			String InsertProgramCheckpointSQLQuery = "INSERT INTO rlf_programs_checkpoints (";
			
			// Skip updating Account_ID by making i = 1
			for(int i = 0 ; i < RLF_Programs_CheckPoints_Table_Columns.size(); i++){
			
			// Add Non Null Items as SET for the SQL Query
			if(SQLQueryUpdateChangeList.get(i) != null){
			
			// If the current value contains Parentheses like "NOW()", It's an SQL method so concatenate without single quotes
			if(SQLQueryUpdateChangeList.get(i).contains("(") && SQLQueryUpdateChangeList.get(i).contains(")")){
				InsertProgramCheckpointSQLQuery = InsertProgramCheckpointSQLQuery + RLF_Programs_CheckPoints_Table_Columns.get(i) + "";
			}else{
				InsertProgramCheckpointSQLQuery = InsertProgramCheckpointSQLQuery + RLF_Programs_CheckPoints_Table_Columns.get(i) + "";
			}
			
			// Add appropriate Commas to the SQL Query
			// Check if you're not the last null
			for(int k = i; k < SQLQueryUpdateChangeList.size(); k++){
			
			// Break if we're on the last run
			if((k + 1) == SQLQueryUpdateChangeList.size()){
				break;
			}
				
			if(SQLQueryUpdateChangeList.get(k + 1) != null){
				InsertProgramCheckpointSQLQuery = InsertProgramCheckpointSQLQuery + ", ";
			break;
			}
			
			}
			
			}
			
			}
			
			// Begin the Values Section of the INSERT Query
			InsertProgramCheckpointSQLQuery = InsertProgramCheckpointSQLQuery + ")VALUES(";
			
			// Skip updating Account_ID by making i = 1
			for(int i = 0 ; i < RLF_Programs_CheckPoints_Table_Columns.size(); i++){
			
			// Add Non Null Items as SET for the SQL Query
			if(SQLQueryUpdateChangeList.get(i) != null){
			
			// If the current value contains Parentheses like "NOW()", It's an SQL method so concatenate without single quotes
			if(SQLQueryUpdateChangeList.get(i).contains("(") && SQLQueryUpdateChangeList.get(i).contains(")")){
				InsertProgramCheckpointSQLQuery = InsertProgramCheckpointSQLQuery + SQLQueryUpdateChangeList.get(i) + "";
			}else{
				InsertProgramCheckpointSQLQuery = InsertProgramCheckpointSQLQuery + "'" + SQLQueryUpdateChangeList.get(i) + "'";
			}
			
			// Add appropriate Commas to the SQL Query
			// Check if you're not the last null
			for(int k = i; k < SQLQueryUpdateChangeList.size(); k++){
			
			// Break if we're on the last run
			if((k + 1) == SQLQueryUpdateChangeList.size()){
				break;
			}
				
			if(SQLQueryUpdateChangeList.get(k + 1) != null){
				InsertProgramCheckpointSQLQuery = InsertProgramCheckpointSQLQuery + ", ";
			break;
			}
			
			}
			
			}
			
			}
			
			// Close the Query
			InsertProgramCheckpointSQLQuery = InsertProgramCheckpointSQLQuery + ");";
			
			///////////////////////////////////////////////////////////////////////////////////////////////////
				// END : Program Checkpoint INSERT Query
			
			// SAMPLE: Program Checkpoint Insert Query
			/*
            String InsertProgramCheckpointSQLQuery = "INSERT INTO rlf_programs_checkpoints (" +
                            "Account_ID," +
                            "Last_Exercise_ID," +
                            "Last_Exercise_ID_Saved_On" +
                            ")" +
                            "VALUES(" +
                            "\""+SessionAccountID+"\"," +
                            "\""+New_Exercise_ID+"\"," +
                            ""+Last_Exercise_ID_Saved_On+"" +
                            ");" +
                            "";
			*/
			
			// Create the Table Entry
			SQLStatement.executeUpdate(InsertProgramCheckpointSQLQuery);
								
							}

/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ */
	/* END rlf_programs_checkpoints SQL Queries */	
				
				
				// Close the ResultSet
				try {LastExerciseIDSQLQueryOutput.close();} catch (SQLException e) {e.printStackTrace();}
			
		} // end try
		// if database exception occurs, return error page
		catch (SQLException SQLEX) {
			
			// Print the Stack Trace
			SQLEX.printStackTrace();		
			
			// Respond with an error message
			String UnknownErrorMessage = "Unknown Database error occurred. Please Try again later.";
			// Replaces "GlobalMethods.writeForwardHTMLErrorResponse(Request, Response, GlobalTools.GTV_Homepage, UnknownErrorMessage);"
			throw new RuntimeException(UnknownErrorMessage);
			
		}
	
	}

	// close SQL statements and database when servlet terminates
	public void destroy() {
		// attempt to close statements and database connection
		try {
			SQLStatement.close();
			SQLConnection.close();
		} // end try
		// handle database exceptions by returning error to client
		catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} // end catch
	} // end method destroy
	
}
