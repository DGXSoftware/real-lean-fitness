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
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dgx.software.com.JavaBeanPackage.MailJavaBean;
import dgx.software.com.UtilityPackage.MailTemplate;
import dgx.software.com.UtilityPackage.AESEncryption;
import dgx.software.com.UtilityPackage.GlobalTools;

@SuppressWarnings("serial")
public class NewsletterSubscriptionServlet extends HttpServlet {
	
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

		// Get the Servlet Action
		String UserNewsletterAction = Request.getParameter("UserNewsletterAction");
		
		// Throw an Error if no Servlet Action was provided
		if(UserNewsletterAction == null){
			throw new RuntimeException("Please provide a valid Newsletter Subscription Servlet Action.");
		}

		// Act according to the Servlet Action or throw an Error if an invalid Servlet Action was provided
		if(UserNewsletterAction.equals("Enabled")){
			// Process the "Post" E-Mail Verification Activation requests from the clients
			writeServletResponseEnable(Request, Response);
		}else if(UserNewsletterAction.equals("Disabled")){
			// Process the "Post" E-Mail Verification Send requests from the clients
			writeServletResponseDisable(Request, Response);
		}else{
			throw new RuntimeException("Please choose a valid Newsletter Subscription Servlet Action.");
		}

	}
	
	// Create the Servlet Response
	public void writeServletResponseEnable(HttpServletRequest Request, HttpServletResponse Response) throws IOException {

		/* START Servlet Response */
/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ */

		// attempt to process a vote and display current results
		try {

			// E-Mail obtained by the user Input
			String UserEMail = Request.getParameter("UserEMail");
			
			// If no user E-Mail was received, throw an exception
			if(UserEMail == null){
				throw new RuntimeException("Failed to enable your Newsletter subscription. Please provide an E-Mail.");
			}
			
			// E-Mail obtained from the RLF_NewsLetters Table
			String NewsletterEMail = "";
			
			// Newsletter subscription will ALWAYS be "Y" (Enabled) For this Method
			char InNewsletter = 'Y';
			
			// Select the E-Mail From the Currently created Username.
			String NewsletterEMailSQLQuery = "SELECT EMail FROM RLF_NewsLetters WHERE EMail ='"+UserEMail+"';";
			
			// Get the EMailSQLQueryOutput ResultSet
			ResultSet EMailSQLQueryOutput = SQLStatement.executeQuery(NewsletterEMailSQLQuery);
			
			// Check if this E-Mail record already exists in the RLF_NewsLetters Table
			if(EMailSQLQueryOutput.next()){

			// If it does, update it and mark In_Newsletter as Enabled "Y"
				
			// Set the assigned E-Mail for the Currently created Username.
			NewsletterEMail = EMailSQLQueryOutput.getString("EMail");

			// NewsLetter Update Query
			String UpdateNewsLetterSQLQuery = "UPDATE RLF_NewsLetters SET In_Newsletter='"+InNewsletter+"' WHERE EMail='"+NewsletterEMail+"';";
			
			// Update the NewsLetter Entry
			// 1 = Update Successful
			// 0 = Update Failed
			int SQLQueryResultsCode = SQLStatement.executeUpdate(UpdateNewsLetterSQLQuery);
			
			// If the Update SQL Query Failed to Update, throw an exception
			if(SQLQueryResultsCode == 0){
				throw new RuntimeException("Failed to update the account mark for your Newsletter subscription.");
			}
			
			}else{
			
			// If it doesn't, add the record and mark it as Not existing in the accounts table
			
			// Variables for Account creation Date
			String Account_Created_On = "NOW()";
				
			// Has Account ("N" will be the Fixed Value Here)
			char HasAccount = 'N';
				
			// Create an Entry for the new account that was created in the RLF_NewsLetters Table.
			String InsertNewsLetterSQLQuery = "INSERT INTO RLF_NewsLetters (" +
					"EMail," +
					"In_Newsletter," +
					"Subscribed_On," +
					"Has_Account" +
					")" +
					"VALUES(" +
					"\""+UserEMail+"\"," +
					"\""+InNewsletter+"\"," +
					""+Account_Created_On+"," +
					"\""+HasAccount+"\"" +
					");" +
					"";
			
			// Create the NewsLetter Entry
			SQLStatement.executeUpdate(InsertNewsLetterSQLQuery);
				
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
			// Replaces "GlobalMethods.writeForwardHTMLErrorResponse(Request, Response, GlobalTools.GTV_Homepage, UnknownErrorMessage);"
			throw new RuntimeException(UnknownErrorMessage);
			
		}
	
	}
	
	// Create the Servlet Response
	public void writeServletResponseDisable(HttpServletRequest Request, HttpServletResponse Response) throws IOException {

		/* START Servlet Response */
/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ */

		// attempt to process a vote and display current results
		try {

			// E-Mail obtained by the user Input
			String UserEMail = Request.getParameter("UserEMail");
			
			// If no user E-Mail was received, throw an exception
			if(UserEMail == null){
				throw new RuntimeException("Failed to disable your Newsletter subscription. Please provide an E-Mail.");
			}
			
			// E-Mail obtained from the RLF_NewsLetters Table
			String NewsletterEMail = "";
			
			// Newsletter subscription will ALWAYS be "N" (Disabled) For this Method
			char InNewsletter = 'N';
			
			// Select the E-Mail From the Currently created Username.
			String NewsletterEMailSQLQuery = "SELECT EMail FROM RLF_NewsLetters WHERE EMail ='"+UserEMail+"';";
			
			// Get the EMailSQLQueryOutput ResultSet
			ResultSet EMailSQLQueryOutput = SQLStatement.executeQuery(NewsletterEMailSQLQuery);
			
			// Check if this E-Mail record already exists in the RLF_NewsLetters Table
			if(EMailSQLQueryOutput.next()){

			// If it does, update it and mark In_Newsletter as Disabled "N"
				
			// Set the assigned E-Mail for the Currently created Username.
			NewsletterEMail = EMailSQLQueryOutput.getString("EMail");

			// NewsLetter Update Query
			String UpdateNewsLetterSQLQuery = "UPDATE RLF_NewsLetters SET In_Newsletter='"+InNewsletter+"' WHERE EMail='"+NewsletterEMail+"';";
			
			// Update the NewsLetter Entry
			// 1 = Update Successful
			// 0 = Update Failed
			int SQLQueryResultsCode = SQLStatement.executeUpdate(UpdateNewsLetterSQLQuery);
			
			// If the Update SQL Query Failed to Update, throw an exception
			if(SQLQueryResultsCode == 0){
				throw new RuntimeException("Failed to update the account mark for your Newsletter subscription.");
			}
			
			}else{
			
			// If it doesn't, add the record and mark it as Not existing in the accounts table
			
			// Variables for Account creation Date
			String Account_Created_On = "NOW()";
				
			// Has Account ("N" will be the Fixed Value Here)
			char HasAccount = 'N';
				
			// Create an Entry for the new account that was created in the RLF_NewsLetters Table.
			String InsertNewsLetterSQLQuery = "INSERT INTO RLF_NewsLetters (" +
					"EMail," +
					"In_Newsletter," +
					"Subscribed_On," +
					"Has_Account" +
					")" +
					"VALUES(" +
					"\""+UserEMail+"\"," +
					"\""+InNewsletter+"\"," +
					""+Account_Created_On+"," +
					"\""+HasAccount+"\"" +
					");" +
					"";

			// Create the NewsLetter Entry
			SQLStatement.executeUpdate(InsertNewsLetterSQLQuery);
				
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
