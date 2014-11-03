/* 
GOAL: Logs into the Database to Insert/Create an user account.

PROPERTIES: Back-End Work
1. User SQL Database Access (SQL)
*/

package dgx.software.com.ActionPackage;

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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

//import dgx.software.com.UtilityPackage.GlobalMethods;

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
		catch (Exception EX) {
			
			// Print the Stack Trace
			EX.printStackTrace();
			
			String UnavailableErrorMessage = "The Database is Unavailable. Please Try again later.";
			// Replaces "GlobalMethods.writeForwardHTMLErrorResponse(Request, Response, GlobalTools.GTV_Homepage, UnavailableErrorMessage);"
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

/* START RLF_Accounts INSERT */
/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ */
	
			// Mandatory Variables for Account creation
			String Username = Request.getParameter("RegistrationUsername");
			String FirstName = Request.getParameter("RegistrationFirstName");
			String LastName = Request.getParameter("RegistrationLastName");
			String Password = Request.getParameter("RegistrationPassword");
			String EMail = Request.getParameter("RegistrationEMail");
			String Gender = Request.getParameter("RegistrationGender");
			String Date_Of_Birth = Request.getParameter("RegistrationBirthYear") + "-" + Request.getParameter("RegistrationBirthMonth") + "-" + Request.getParameter("RegistrationBirthDay");

			// If any of the Mandatory E-Mail Information is null throw an error
			if(
				Username == null ||
				FirstName == null ||
				LastName == null ||
				Password == null ||
				EMail == null ||
				Gender == null ||
				Date_Of_Birth == null
			){
				// Mandatory E-Mail Information Missing
				//String EMailErrorMessage = "The E-Mail failed to send. Please provide all Mandatory E-Mail Information.";
				throw new RuntimeException("Account creation failed. Please provide all Mandatory account Information.");
			}
			
			// Variables for Account creation Date
			/*
			Date CurrentDate = new Date();
			DateFormat DateFormat = new SimpleDateFormat("yyyy/MM/dd");
			DateFormat TimeFormat = new SimpleDateFormat("HH:mm:ss");
			String Account_Creation_Date = DateFormat.format(CurrentDate);
			String Account_Creation_Time = TimeFormat.format(CurrentDate);
			String Account_Creation_TimeZone = "EST";
			*/
			
			// Variables for Account creation Date
			String Account_Created_On = "NOW()";
			
			// Default Account Status Values
			char IsActivated = 'N';
			char IsVerified = 'N';
		
			// Create an Entry for the new account that was created in the RLF_Accounts Table.
			String InsertAccountSQLQuery = "INSERT INTO RLF_Accounts (" +
					"Username," +
					"First_Name," +
					"Last_Name," +
					"Password," +
					"EMail," +
					"Gender," +
					"Date_Of_Birth," +
					"Account_Created_On," +
					"Is_Activated," +
					"Is_Verified" +
					")" +
					"VALUES (" +
					"\""+Username+"\"," +
					"\""+FirstName+"\"," +
					"\""+LastName+"\"," +
					"MD5(\""+Password+"\")," +
					"\""+EMail+"\"," +
					"\""+Gender+"\"," +
					"\""+Date_Of_Birth+"\"," +
					""+Account_Created_On+"," +
					"\""+IsActivated+"\"," +
					"\""+IsVerified+"\"" +
					");" +
					"";
			
			// Create the Account
			SQLStatement.executeUpdate(InsertAccountSQLQuery);
			
/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ */
/* END RLF_Accounts INSERT */
			
/* START RLF_User_Information INSERT */
/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ */
			
			// Records the assigned Account ID for the Currently created Username.
			String AccountID = "";
			
			// Select the Account_ID From the Currently created Username.
			String AccountIDSQLQuery = "SELECT Account_ID FROM RLF_Accounts WHERE Username ='"+Username+"';";
			
			// Get the AccountIDSQLQuery ResultSet
			ResultSet UsernameSQLQueryOutput = SQLStatement.executeQuery(AccountIDSQLQuery);
			
			// If we DO NOT Have an Empty Result Set, Work with it.
			if(UsernameSQLQueryOutput.next()){
			
			// Set the assigned Account ID for the Currently created Username.
			AccountID = UsernameSQLQueryOutput.getString("Account_ID");

			}
			
			// Create an Entry for the new account that was created in the RLF_User_Information Table.
			String InsertUserInformationSQLQuery = "INSERT INTO RLF_User_Information (" +
					"Account_ID," +
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
					"\"\"" +
					");" +
					"";
			
			// Create the UserInformation Entry
			SQLStatement.executeUpdate(InsertUserInformationSQLQuery);
			
/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ */
/* END RLF_User_Information INSERT */

/* START RLF_Newsletters INSERT */
/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ */
			
			// Records the assigned E-Mail for the Currently created Username.
			String NewsletterEMail = "";
			
			// Decide wether or not the user's Newsletter subscription will be Active or Not
			char InNewsletter = 'Y';
			String SignUpForNewsletter = Request.getParameter("SignUpForNewsletter");
			if(SignUpForNewsletter != null){
			if(!SignUpForNewsletter.equals("")){InNewsletter = 'Y';}else{InNewsletter = 'N';}
			}else{InNewsletter = 'N';}
			
			// Has Account ("Y" will be the Fixed Value Here)
			char HasAccount = 'Y';
			
			// Select the E-Mail From the Currently created Username.
			String NewsletterEMailSQLQuery = "SELECT EMail FROM RLF_NewsLetters WHERE EMail ='"+EMail+"';";
			
			// Get the EMailSQLQueryOutput ResultSet
			ResultSet EMailSQLQueryOutput = SQLStatement.executeQuery(NewsletterEMailSQLQuery);
			
			// Check if this E-Mail record already exists in the RLF_NewsLetters Table
			if(EMailSQLQueryOutput.next()){

			// If it does, update it and mark it as existing in the accounts table
				
			// Set the assigned E-Mail for the Currently created Username.
			NewsletterEMail = EMailSQLQueryOutput.getString("EMail");

			// NewsLetter Update Query
			String UpdateNewsLetterSQLQuery = "UPDATE RLF_NewsLetters SET Full_Name='"+(FirstName + " " + LastName)+"', In_Newsletter='"+InNewsletter+"', Has_Account='"+HasAccount+"' WHERE EMail='"+NewsletterEMail+"';";
			
			// Update the NewsLetter Entry
			// 1 = Update Successful
			// 0 = Update Failed
			int SQLQueryResultsCode = SQLStatement.executeUpdate(UpdateNewsLetterSQLQuery);
			
			// If the Update SQL Query Failed to Update, throw an exception
			if(SQLQueryResultsCode == 0){
				throw new RuntimeException("Failed to update the account mark for your Newsletter subscription.");
			}
			
			}else{
			
			// If it doesn't, add the record and mark it as existing in the accounts table
			
			// Create an Entry for the new account that was created in the RLF_NewsLetters Table.
			String InsertNewsLetterSQLQuery = "INSERT INTO RLF_NewsLetters (" +
					"EMail," +
					"Full_Name," +
					"In_Newsletter," +
					"Subscribed_On," +
					"Has_Account" +
					")" +
					"VALUES(" +
					"\""+EMail+"\"," +
					"\""+(FirstName + " " + LastName)+"\"," +
					"\""+InNewsletter+"\"," +
					""+Account_Created_On+"," +
					"\""+HasAccount+"\"" +
					");" +
					"";

			// Create the NewsLetter Entry
			SQLStatement.executeUpdate(InsertNewsLetterSQLQuery);
				
			}

/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ */
/* END RLF_Newsletters INSERT */			
			
			// Close the ResultSet
			try {UsernameSQLQueryOutput.close();} catch (SQLException e) {e.printStackTrace();}
			
			// Write the HTML Successful Response
			// DISABLED;  Handled By AJAX Call
			// String RegistrationSuccessMessage = "Account created successfully!";
			//GlobalMethods.writeForwardHTMLSuccessResponse(Request, Response, GlobalTools.GTV_Homepage, RegistrationSuccessMessage);

/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ */
		/* END Servlet Response */
			
		} // end try
		catch (MySQLIntegrityConstraintViolationException SQLEX) {

			// Print the Stack Trace
			SQLEX.printStackTrace();		
			
			// Respond with an error message
			throw new RuntimeException(SQLEX.getMessage());
			
		}
		// if database exception occurs, return error page
		catch (SQLException SQLEX) {

			// Print the Stack Trace
			SQLEX.printStackTrace();
			
			// Respond with an error message
			String UnknownErrorMessage = "Unknown Database error occurred. Please Try again later.";
			
			// If It's a "Duplicate" Error Message get the proper error message
			if(SQLEX.getMessage().contains("Duplicate")){
				if(SQLEX.getMessage().contains("Username")){UnknownErrorMessage = "This Username is already taken. Please choose another one.";}
			    if(SQLEX.getMessage().contains("EMail")){UnknownErrorMessage = "This E-Mail address is already in use. Please supply a different E-Mail address.";}
			}
			
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
