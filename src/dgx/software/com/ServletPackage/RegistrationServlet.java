/* 
GOAL: Logs into the Database to Insert/Create an user account.

PROPERTIES: Back-End Work
1. User SQL Database Access (SQL)
*/

package dgx.software.com.ServletPackage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dgx.software.com.UtilityPackage.GlobalMethods;

@SuppressWarnings("serial")
public class RegistrationServlet extends HttpServlet {
	
	private Connection SQLConnection;
	private Statement SQLStatement;
	private ServletConfig InitConfig;

	// set up database connection and create SQL statement
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
		catch (Exception exception) {
			exception.printStackTrace();
			String UnavailableErrorMessage = "The Database is Unavailable. Please Try again later.";
			GlobalMethods.writeForwardHTMLErrorResponse(Request, Response, "/", UnavailableErrorMessage);
			throw new UnavailableException(exception.getMessage());
			
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
			String Username = Request.getParameter("RegistrationUsername");
			String Password = Request.getParameter("RegistrationPassword");
			String EMail = Request.getParameter("RegistrationEMail");
			String Gender = Request.getParameter("RegistrationGender");
			String Date_Of_Birth = Request.getParameter("RegistrationBirthYear") + "-" + Request.getParameter("RegistrationBirthMonth") + "-" + Request.getParameter("RegistrationBirthDay");

			// Variables for Account creation Date
			Date CurrentDate = new Date();
			DateFormat DateFormat = new SimpleDateFormat("yyyy/MM/dd");
			DateFormat TimeFormat = new SimpleDateFormat("HH:mm:ss");
			String Account_Creation_Date = DateFormat.format(CurrentDate);
			String Account_Creation_Time = TimeFormat.format(CurrentDate);
			String Account_Creation_TimeZone = "EST";
		
			// Create an Entry for the new account that was created in the RLFDB_Accounts Table.
			String AccountSQLQuery = "INSERT INTO RLFDB_Accounts (" +
					"Username," +
					"Password," +
					"EMail," +
					"Gender," +
					"Date_Of_Birth," +
					"Account_Creation_Date," +
					"Account_Creation_Time," +
					"Account_Creation_TimeZone" +
					")" +
					"VALUES (" +
					"\""+Username+"\"," +
					"MD5(\""+Password+"\")," +
					"\""+EMail+"\"," +
					"\""+Gender+"\"," +
					"\""+Date_Of_Birth+"\"," +
					"\""+Account_Creation_Date+"\"," +
					"\""+Account_Creation_Time+"\"," +
					"\""+Account_Creation_TimeZone+"\"" +
					");" +
					"";
			
			// Create the Account
			SQLStatement.executeUpdate(AccountSQLQuery);
		
			// Records the assigned Account ID for the Currently created Username.
			String AccountID = "";
			
			// Select the Account_ID From the Currently created Username.
			String AccountIDSQLQuery = "SELECT Account_ID FROM RLFDB_Accounts WHERE Username ='"+Username+"';";
			
			// Get the AccountIDSQLQuery ResultSet
			ResultSet UsernameSQLQueryOutput = SQLStatement.executeQuery(AccountIDSQLQuery);
			
			// If we DO NOT Have an Empty Result Set, Work with it.
			if(UsernameSQLQueryOutput.next()){
			
			// Set the assigned Account ID for the Currently created Username.
			AccountID = UsernameSQLQueryOutput.getString("Account_ID");

			}
			
			// Create an Entry for the new account that was created in the RLFDB_User_Information Table.
			String UserInformationSQLQuery = "INSERT INTO RLFDB_User_Information (" +
					"Account_ID," +
					"First_Name," +
					"Middle_Name," +
					"Last_Name," +
					"Location_Address," +
					"Location_City," +
					"Location_State," +
					"Location_ZipCode," +
					"Location_Country" +
					")" +
					"VALUES(" +
					"\""+AccountID+"\"," +
					"\"\"," +
					"\"\"," +
					"\"\"," +
					"\"\"," +
					"\"\"," +
					"\"\"," +
					"\"\"," +
					"\"\"" +
					");" +
					"";
			
			// Create the UserInformation Entry
			SQLStatement.executeUpdate(UserInformationSQLQuery);
			
			// Close the ResultSet
			try {UsernameSQLQueryOutput.close();} catch (SQLException e) {e.printStackTrace();}
			
			// Write the HTML Successful Response
			String RegistrationSuccessMessage = "Account created successfully!";
			GlobalMethods.writeForwardHTMLSuccessResponse(Request, Response, "/", RegistrationSuccessMessage);

/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ */
		/* END Servlet Response */
			
		} // end try
		// if database exception occurs, return error page
		catch (SQLException sqlException) {
			sqlException.printStackTrace();		
			
			// Respond with an error message
			String UnknownErrorMessage = "Unknown Database error occurred. Please Try again later.";
			GlobalMethods.writeForwardHTMLErrorResponse(Request, Response, "/", UnknownErrorMessage);
			
		} // end catch
	
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
