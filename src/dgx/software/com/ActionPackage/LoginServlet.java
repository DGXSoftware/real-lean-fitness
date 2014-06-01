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
import javax.servlet.http.HttpSession;

//import dgx.software.com.UtilityPackage.GlobalMethods;

@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {
	
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

	// process "Post" requests from clients
	protected void doPost(HttpServletRequest Request, HttpServletResponse Response) throws ServletException, IOException {

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
			// Replaces "GlobalMethods.writeForwardHTMLErrorResponse(Request, Response, "/", UnavailableErrorMessage);"
			throw new RuntimeException(UnavailableErrorMessage);
			
		} // end catch
		
		// process "Post" requests from clients
		writeServletResponse(Request, Response);

	}
	
	// Create the Servlet Response
	public void writeServletResponse(HttpServletRequest Request, HttpServletResponse Response) throws IOException {

		/* START Servlet Response */
/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ */

		// attempt to process a vote and display current results
		try {

			// Variables for Account basic information
			String LoginUsername = Request.getParameter("LoginUsername");
			String LoginPassword = Request.getParameter("LoginPassword");

			// SQL Query
			String SQLQuery = "SELECT Account_ID,Username FROM RLFDB_Accounts WHERE Username='"+LoginUsername+"' AND Password=MD5('"+LoginPassword+"');";

			// Get the SQLQueryOutput
			ResultSet SQLQueryOutput = SQLStatement.executeQuery(SQLQuery);
			
			// If we DO NOT Have an Empty Result Set, Work with it (Account Found).
			if(SQLQueryOutput.next()){
	
			// Handle User log in below; Create Session and Redirect accordingly
				
			// Variables for User Session
			// NOTE: Before Adding new variables make sure the SQLQuery retrieves it
			String AccountID = SQLQueryOutput.getString("Account_ID");
			String Username = SQLQueryOutput.getString("Username");
			
			// Returns null if no session already exists 
			HttpSession CurrentSession =  Request.getSession(false);
			
			// Check if we have an existing Session
			if (CurrentSession == null) {
				
			// Create a new Session even if it Exists
			CurrentSession =  Request.getSession(true);
			
			// Set the time in seconds that a session should be saved before timing out.
			// A negative value specifies that the session should never time out.
			CurrentSession.setMaxInactiveInterval(900); // 900 Seconds = 15 Minutes
			
			}
			
			// Save the attributes to the Current Session
			if(AccountID != null) CurrentSession.setAttribute("AccountID", AccountID);
			if(Username != null) CurrentSession.setAttribute("Username", Username);

			// Reset the Pointer changed by the if statement above
			SQLQueryOutput.beforeFirst();
			
			// Close the ResultSet
			try {SQLQueryOutput.close();} catch (SQLException e) {e.printStackTrace();}
			
			// Write the HTML Successful Response
			// DISABLED;  Handled By AJAX Call
			// GlobalMethods.writeForwardHTMLSuccessResponse(Request, Response, "/UserProfileServlet", "");
			
			}else {
			
			// Close the ResultSet
			try {SQLQueryOutput.close();} catch (SQLException e) {e.printStackTrace();}
			
			// (Account NOT Found) Write the Error Response
			String LoginErrorMessage = "Your login information was not correct, please try again.";
			// Replaces "GlobalMethods.writeForwardHTMLErrorResponse(Request, Response, "/", LoginErrorMessage);"
			throw new RuntimeException(LoginErrorMessage);
		}
			
/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ */
		/* END Servlet Response */
			
		} // end try
		// if database exception occurs, return error page
		catch (SQLException SQLEX) {
			
			// Print the Stack Trace
			SQLEX.printStackTrace();		
			
			// Respond with an error message
			String UnknownErrorMessage = "Unknown Database error occurred. Please Try again later.";
			// Replaces "GlobalMethods.writeForwardHTMLErrorResponse(Request, Response, "/", UnknownErrorMessage);"
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
