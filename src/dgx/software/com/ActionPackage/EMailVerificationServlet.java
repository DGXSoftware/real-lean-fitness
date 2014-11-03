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
public class EMailVerificationServlet extends HttpServlet {
	
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
		String EMailVerificationServletAction = Request.getParameter("EMailVerificationServletAction");

		// Throw an Error if no Servlet Action was provided
		if(EMailVerificationServletAction == null){
			throw new RuntimeException("Please provide a valid EMail Verification Servlet Action.");
		}

		// Act according to the Servlet Action or throw an Error if an invalid Servlet Action was provided
		if(EMailVerificationServletAction.equals("EMailVerificationActivation")){
			// Process the "Post" E-Mail Verification Activation requests from the clients
			writeServletResponseActivation(Request, Response);
		}else if(EMailVerificationServletAction.equals("EMailVerificationSend")){
			// Process the "Post" E-Mail Verification Send requests from the clients
			writeServletResponseSend(Request, Response);
		}else{
			throw new RuntimeException("Please choose a valid EMail Verification Servlet Action.");
		}

	}
	
	// Create the Servlet Response
	public void writeServletResponseActivation(HttpServletRequest Request, HttpServletResponse Response) throws IOException {

		/* START Servlet Response */
/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ */

		// attempt to process a vote and display current results
		try {

			// Variables for Account basic information
			String ActionValue = Request.getParameter("ActionValue");
			String UserValue = Request.getParameter("UserValue");
			String KeyValue = Request.getParameter("KeyValue"); 
			
			// If the Old Password is not equals to the Confirmation Old Password, throw an exception
			if(ActionValue == null || UserValue == null|| KeyValue == null){
				throw new RuntimeException("Invalid E-Mail credentials to verify. Please provide valid E-Mail credentials.");
			}
			
			// Encrypt the values
			// Object to Decrypt the sensitive data
			AESEncryption AES = new AESEncryption();
			String DecryptedUserValue = AES.getAESDecryption(UserValue);
			String DecryptedKeyValue = AES.getAESDecryption(KeyValue);
			
			// SQL Query
			String SQLQuery = "UPDATE RLF_Accounts SET Is_Verified='Y' WHERE Username='"+DecryptedUserValue+"' AND EMail='"+DecryptedKeyValue+"';";
			
			// Execute the Update SQL Query
			// 1 = Update Successful
			// 0 = Update Failed
			int SQLQueryResultsCode = SQLStatement.executeUpdate(SQLQuery);
			
			// If the Update SQL Query Failed to Update, throw an exception
			if(SQLQueryResultsCode == 0){
				throw new RuntimeException("Your E-Mail verification information was not correct. Your E-Mail failed to verify.");
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
	public void writeServletResponseSend(HttpServletRequest Request, HttpServletResponse Response) throws IOException {

		/* START Servlet Response */
/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ */

		// attempt to process a vote and display current results
		try {
			
			// EXAMPLE: http://localhost:8080?do=fpwdc&usr=1000000000&key=965c21ae0dde31bc1c488b49ef08e93fbd1ab3db
			// Define the Query String Field Names
			String ActionField = "do";
			String UserField = "usr";
			String KeyField = "key";
			
			// Define the Query String Field Values
			String ActionValue = "vfy";
			String UserValue = Request.getParameter("SessionUsername");
			String KeyValue = ""; 
			
			// SQL Query
			String SQLQuery = "SELECT EMail FROM RLF_Accounts WHERE Username='"+UserValue+"';";

			// Get the SQLQueryOutput
			ResultSet SQLQueryOutput = SQLStatement.executeQuery(SQLQuery);
			
			// If we DO NOT Have an Empty Result Set, Work with it (Account Found).
			if(SQLQueryOutput.next()){
            
			// Variables for User Session
			// NOTE: Before Adding new variables make sure the SQLQuery retrieves it
			KeyValue = SQLQueryOutput.getString("EMail");
			
			// Reset the Pointer changed by the if statement above
			SQLQueryOutput.beforeFirst();
			
			// Close the ResultSet
			try {SQLQueryOutput.close();} catch (SQLException e) {e.printStackTrace();}
			
			// If no E-Mail was retrieved, throw an exception
			if(KeyValue.equals("")){
				throw new RuntimeException("This Username does not exist in the system.");
			}
			
			// Send an E-Mail to the user with the EMailVerifyURL
			
			// Get the Hostname
			// EXAMPLE: 
			// http://localhost:8080?do=fpwdc&usr=1000000000&key=965c21ae0dde31bc1c488b49ef08e93fbd1ab3db
			// www.RealLeanFitness.com?do=fpwdc&usr=1000000000&key=965c21ae0dde31bc1c488b49ef08e93fbd1ab3db
			String CurrentHostName = "http://" + Request.getServerName() +":"+ Request.getServerPort() + Request.getContextPath();
         
			// Encrypt the sensitive data
			AESEncryption AES = new AESEncryption();
			String EncryptedUserValue = AES.getURLEncodedAESEncryption(UserValue);
			String EncryptedKeyValue = AES.getURLEncodedAESEncryption(KeyValue);
            
			// Generate the Full Password Change URL
			String EMailVerifyURL = "";
			EMailVerifyURL = EMailVerifyURL.concat(CurrentHostName + "?");
			EMailVerifyURL = EMailVerifyURL.concat(ActionField + "=");
			EMailVerifyURL = EMailVerifyURL.concat(ActionValue + "&");
			EMailVerifyURL = EMailVerifyURL.concat(UserField + "=");
			EMailVerifyURL = EMailVerifyURL.concat(EncryptedUserValue + "&");
			EMailVerifyURL = EMailVerifyURL.concat(KeyField + "=");
			EMailVerifyURL = EMailVerifyURL.concat(EncryptedKeyValue + "");
			
			//System.out.println("EMailVerifyURL = " + EMailVerifyURL);
			
			// Define the MailJavaBean object
			MailJavaBean PasswordMailJavaBean = new MailJavaBean();
			
			// Set the E-Mail Receiver
			PasswordMailJavaBean.setEMailReceiver(KeyValue);
			
			//Set the E-Mail Header Subject
			PasswordMailJavaBean.setEMailSubject("Your E-Mail verification for Real Lean Fitness");
			
			// Set the E-Mail Body
			String CustomHTMLBody =
			"<h2 style='font-size: 18px; font-family: Arial, sans-serif; color: #000;'>E-Mail Verification:</h2>" +
			"<p>&nbsp;</p>" +
			"<p style='font-size: 14; font-family: Arial, sans-serif; color: #000;'>To verify your E-Mail, please visit the following page:</p>" +
			"<a href='"+EMailVerifyURL+"'>"+EMailVerifyURL+"</a>" +
			"<p>&nbsp;</p>" +
			"<p style='font-size: 14; font-family: Arial, sans-serif; color: #000;'>When you visit that page, your E-Mail will be automatically verified. Upon a successful E-Mail verification an E-Mail confirmation will be sent to you.</p>" +
			"<p>&nbsp;</p>" +
			"<p style='font-size: 14; font-family: Arial, sans-serif; color: #000;'>Your username is: <b>"+UserValue+"</b></p>" +
			"<p>&nbsp;</p>" +
			"<p style='font-size: 14; font-family: Arial, sans-serif; color: #000;'>To edit your profile, go to this page:</p>" +
			"<a href='www.RealLeanFitness.com?do=editprofile'>www.RealLeanFitness.com?do=editprofile</a>" +
			"<p>&nbsp;</p>" +
			"<p style='font-size: 14; font-family: Arial, sans-serif; color: #000;'>Thanks for helping us maintain the security of your account.</p>" +
			"<p>&nbsp;</p>" +
			"<p style='font-size: 14; font-family: Arial, sans-serif; color: #000;'>~ The RealLeanFitness Support Team</p>" +
			"<a href='www.RealLeanFitness.com?do=contactsupport'>www.RealLeanFitness.com?do=contactsupport</a></p>";
			
			// Generate the Complete HTML E-Mail
			String RLFHTMLEMail = "";
			//RLFHTMLEMail = MailTemplate.getCompleteHTMLEMail(CustomHTMLBody, UserEMail);
			RLFHTMLEMail = MailTemplate.getCompleteHTMLEMail(Request, Response, CustomHTMLBody, null);

			//RLFHTMLEMail = RLFHTMLEMail.concat(MailTemplate.GoogleEMail());
			
			//Set the E-Mail Body Message
			PasswordMailJavaBean.setEMailBody(RLFHTMLEMail);

			// Set the File Attachment
			//PasswordMailJavaBean.setAttachment(request.getParameter( "SenderFileAttachment" ));
			
			// Send the E-Mail
			PasswordMailJavaBean.sendEMail(Response);
					
			}else {
			
			// Close the ResultSet
			try {SQLQueryOutput.close();} catch (SQLException e) {e.printStackTrace();}
			
			// (Account NOT Found) Write the Error Response
			String LoginErrorMessage = "This is not an active Username in our system. Please provide an active Username.";
			// Replaces "GlobalMethods.writeForwardHTMLErrorResponse(Request, Response, GlobalTools.GTV_Homepage, LoginErrorMessage);"
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
