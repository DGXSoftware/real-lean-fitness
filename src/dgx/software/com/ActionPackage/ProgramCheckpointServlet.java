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

		// Variables for Program Checkpoint
		String SessionAccountID = Request.getParameter("SessionAccountID");;
		String New_PID = Request.getParameter("New_PID");;
		
		/* START RLF_Program_CheckPoints INSERT */
/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ */
							
			try {
		
			// Variables for last save Date
			String Last_PID_Saved_On = "NOW()";
							
			// Select the PID From the Current User.
			String ProgramCheckpointSQLQuery = "SELECT Last_PID FROM RLF_Program_CheckPoints WHERE Account_ID ='"+SessionAccountID+"';";
				
			// Get the LastPIDSQLQueryOutput ResultSet
			ResultSet LastPIDSQLQueryOutput = SQLStatement.executeQuery(ProgramCheckpointSQLQuery);
							
			// Check if this PID record already exists in the RLF_Program_CheckPoints Table
			if(LastPIDSQLQueryOutput.next()){

			// If it does, simply update the existing record since it already exists in the Table

			// Program Checkpoint Update Query
			String UpdateProgramCheckpointSQLQuery = "UPDATE RLF_Program_CheckPoints SET Last_PID='"+New_PID+"', Last_PID_Saved_On="+Last_PID_Saved_On+" WHERE Account_ID='"+SessionAccountID+"';";
				
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
			String InsertProgramCheckpointSQLQuery = "INSERT INTO RLF_Program_CheckPoints (" +
					"Account_ID," +
					"Last_PID," +
					"Last_PID_Saved_On" +
					")" +
					"VALUES(" +
					"\""+SessionAccountID+"\"," +
					"\""+New_PID+"\"," +
					""+Last_PID_Saved_On+"" +
					");" +
					"";

			// Create the NewsLetter Entry
			SQLStatement.executeUpdate(InsertProgramCheckpointSQLQuery);
								
							}

/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ */
	/* END RLF_Program_CheckPoints INSERT */	
				
				
				// Close the ResultSet
				try {LastPIDSQLQueryOutput.close();} catch (SQLException e) {e.printStackTrace();}
			
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
