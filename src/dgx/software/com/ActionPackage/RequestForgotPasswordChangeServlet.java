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

import dgx.software.com.JavaBeanPackage.MailJavaBean;
import dgx.software.com.UtilityPackage.MailTemplate;
import dgx.software.com.UtilityPackage.GlobalTools;

//import dgx.software.com.UtilityPackage.GlobalMethods;

@SuppressWarnings("serial")
public class RequestForgotPasswordChangeServlet extends HttpServlet {
	
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

			// EXAMPLE: http://localhost:8080?do=fpwdc&usr=1000000000&key=965c21ae0dde31bc1c488b49ef08e93fbd1ab3db
			String ActionField = "do";
			String UserField = "usr";
			String KeyField = "key";
			
			String ActionValue = "fpwdc";
			String UserValue = "";
			String KeyValue = "965c21ae0dde31bc1c488b49ef08e93fbd1ab3db"; 
			
			// Variables for Account basic information
			String UserEMail = Request.getParameter("UserEMail");

			// SQL Query
			String SQLQuery = "SELECT Username FROM RLF_Accounts WHERE EMail='"+UserEMail+"';";

			// Get the SQLQueryOutput
			ResultSet SQLQueryOutput = SQLStatement.executeQuery(SQLQuery);
			
			// If we DO NOT Have an Empty Result Set, Work with it (Account Found).
			if(SQLQueryOutput.next()){
	

			// Variables for User Session
			// NOTE: Before Adding new variables make sure the SQLQuery retrieves it
			UserValue = SQLQueryOutput.getString("Username");
			
			// Reset the Pointer changed by the if statement above
			SQLQueryOutput.beforeFirst();
			
			// Close the ResultSet
			try {SQLQueryOutput.close();} catch (SQLException e) {e.printStackTrace();}
			
			// If no Username was retrieved, throw an exception
			if(UserValue.equals("")){
				throw new RuntimeException("This E-Mail does not exist in the system.");
			}
			
			// Send an E-Mail to the user with the PasswordChangeURL
			
			// EXAMPLE: http://localhost:8080?do=fpwdc&usr=1000000000&key=965c21ae0dde31bc1c488b49ef08e93fbd1ab3db
			// www.RealLeanFitness.com?do=fpwdc&usr=1000000000&key=965c21ae0dde31bc1c488b49ef08e93fbd1ab3db
			String PasswordChangeURL = "";
			
			PasswordChangeURL = PasswordChangeURL.concat("http://localhost:8080?");
			PasswordChangeURL = PasswordChangeURL.concat(ActionField + "=");
			PasswordChangeURL = PasswordChangeURL.concat(ActionValue + "&");
			PasswordChangeURL = PasswordChangeURL.concat(UserField + "=");
			PasswordChangeURL = PasswordChangeURL.concat(UserValue + "&");
			PasswordChangeURL = PasswordChangeURL.concat(KeyField + "=");
			PasswordChangeURL = PasswordChangeURL.concat(KeyValue + "");
			
			System.out.println("PasswordChangeURL = " + PasswordChangeURL);
			
			// Define the MailJavaBean object
			MailJavaBean PasswordMailJavaBean = new MailJavaBean();
			
			// Set the E-Mail Receiver
			//PasswordMailJavaBean.setEMailReceiver(UserEMail);
			PasswordMailJavaBean.setEMailReceiver("dmastagx@hotmail.com");
			
			//Set the E-Mail Header Subject
			PasswordMailJavaBean.setEMailSubject("RLF - Forgotten Password Change");
			
			// Set the E-Mail Body
			String CustomBodyHTML =
			"						" +
			"                       		<h1>Password Change:</h1>" +
					"                            <p>To reset your password, please visit the following page:</p>" +
					"							<a href='"+PasswordChangeURL+"'>"+PasswordChangeURL+"</a>" +
					"							 " +
					"                            <p>&nbsp;</p>" +
					"							<p>When you visit that page, you will be able to change your password. Upon a successful password change a confirmation E-Mail will be sent to you.</p>" +
					"                            <p>&nbsp;</p>" +
					"							<p>Your username is: "+UserValue+"</p>" +
					"                            <p>&nbsp;</p>" +
					"                            <p>To edit your profile, go to this page:</p>" +
					"							<a href='www.RealLeanFitness.com?do=editprofile'>www.RealLeanFitness.com?do=editprofile</a>" +
					"						    <p>&nbsp;</p>" +
					"                            <p>Thanks for helping us maintain the security of your account.</p>" +
					"							<p>&nbsp;</p>" +
					"                            <p>~ The RealLeanFitness Support Team</p>" +
					"							<a href='www.RealLeanFitness.com?do=contactsupport'>www.RealLeanFitness.com?do=contactsupport</a>" +
					"						";;
			
			String RLFHTMLEMail = "";
			RLFHTMLEMail = RLFHTMLEMail.concat(MailTemplate.EMailStart());
			RLFHTMLEMail = RLFHTMLEMail.concat(CustomBodyHTML);
			RLFHTMLEMail = RLFHTMLEMail.concat(MailTemplate.EMailEnd());

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
			String LoginErrorMessage = "The Password Change E-Mail failed to send. Please try again.";
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
