/* 
GOAL: Sends an E-Mail to the specified Recipient

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
public class SendEMailServlet extends HttpServlet {
	
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

			String EMailReceiver = Request.getParameter("Recipient");
			String EMailSubject = Request.getParameter("SenderSubject");
			
			String SenderFirstName = Request.getParameter("SenderFirstName");
			String SenderLastName = Request.getParameter("SenderLastName");
			String SenderEMail = Request.getParameter("SenderEMail");
			String SenderMessage = Request.getParameter("SenderMessage");
			// DISABLED: String SenderFileAttachment = Request.getParameter("SenderFileAttachment");
			
			// Define the MailJavaBean object
			MailJavaBean PasswordMailJavaBean = new MailJavaBean();
			
			// Set the E-Mail Receiver
			PasswordMailJavaBean.setEMailReceiver(EMailReceiver);
			
			//Set the E-Mail Header Subject
			PasswordMailJavaBean.setEMailSubject(EMailSubject);
			
			// Set the E-Mail Body
			String CustomHTMLBody =
			"<h2 style='font-size: 18px; font-family: Arial, sans-serif; color: #000;'>"+EMailSubject+":</h2>" +
			"<p>&nbsp;</p>" +
			"<p style='font-size: 14; font-family: Arial, sans-serif; color: #000;'><b>Sender First Name:</b> "+SenderFirstName+"</p>" +
			"<p>&nbsp;</p>" +
			"<p style='font-size: 14; font-family: Arial, sans-serif; color: #000;'><b>Sender Last Name:</b> "+SenderLastName+"</p>" +
			"<p>&nbsp;</p>" +
			"<p style='font-size: 14; font-family: Arial, sans-serif; color: #000;'><b>Sender EMail:</b> "+SenderEMail+"</p>" +
			"<p>&nbsp;</p>" +
			"<p style='font-size: 14; font-family: Arial, sans-serif; color: #000;'><b>Sender Message:</b> "+SenderMessage+"</p>" +
			"<p>&nbsp;</p>" +
			"<p style='font-size: 14; font-family: Arial, sans-serif; color: #000;'>~ The RealLeanFitness Support Team</p>" +
			"";
			
			// Generate the Complete HTML E-Mail
			String RLFHTMLEMail = "";
			//RLFHTMLEMail = MailTemplate.getCompleteHTMLEMail(CustomHTMLBody, UserEMail);
			RLFHTMLEMail = MailTemplate.getCompleteHTMLEMail(CustomHTMLBody, null);

			//RLFHTMLEMail = RLFHTMLEMail.concat(MailTemplate.GoogleEMail());
			
			//Set the E-Mail Body Message
			PasswordMailJavaBean.setEMailBody(RLFHTMLEMail);

			// Set the File Attachment
			//PasswordMailJavaBean.setAttachment(SenderFileAttachment);
			
			// Send the E-Mail
			PasswordMailJavaBean.sendEMail(Response);
					
			}catch (Exception SQLEX) {
			
			// (Account NOT Found) Write the Error Response
			String LoginErrorMessage = "The E-Mail failed to send. Please try again.";
			// Replaces "GlobalMethods.writeForwardHTMLErrorResponse(Request, Response, GlobalTools.GTV_Homepage, LoginErrorMessage);"
			throw new RuntimeException(LoginErrorMessage);
		}
			
/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ */
		/* END Servlet Response */
	
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
