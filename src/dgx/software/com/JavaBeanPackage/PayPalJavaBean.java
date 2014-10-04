package dgx.software.com.JavaBeanPackage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PayPalJavaBean {

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
			// Replaces "GlobalMethods.writeForwardHTMLErrorResponse(Request, Response, "/", UnavailableErrorMessage);"
			throw new RuntimeException(UnavailableErrorMessage);
			
		} // end catch
        
    }
	
    // Print the Database Connection parameters
    public void printContextParameters(){
    	System.out.println("DriverName = " + DriverName);
    	System.out.println("DatabaseURL = " + DatabaseURL);
    	System.out.println("DatabaseUser = " + DatabaseUser);
    	System.out.println("DatabasePassword = " + DatabasePassword);
    }

    public String getUserAccountIDAndEMailUserActivation(HttpServletRequest Request, HttpServletResponse Response, String Username) throws IOException{
    	
		/* START JavaBean Response */
/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ */

		
    	// Declare the Account ID To Return
		String AccountIDToReturn = "";
		
			// attempt to process a vote and display current results
			try {

				// RegistrationUsername to retrieve Account ID
				String RegistrationUsername = Username;
				
				// SQL Query
				String SQLQuery = "SELECT Account_ID, EMail, IsActivated FROM RLF_Accounts WHERE Username = '"+RegistrationUsername+"';";

				// Get the SQLQueryOutput
				ResultSet SQLQueryOutput = SQLStatement.executeQuery(SQLQuery);
				
				// If we DO NOT Have an Empty Result Set, Work with it (Account Found).
				if(SQLQueryOutput.next()){
		
				// Get Username Account ID below and Redirect accordingly
					
				// Set the User information Variables
				String AccountID = SQLQueryOutput.getString("Account_ID");
				String EMail = SQLQueryOutput.getString("EMail");
				String IsActivated = SQLQueryOutput.getString("IsActivated");

				// Check if the User is already activated.
				// If the user is anything, but NOT Activated (IsActivated == N) then redirect them out of here
				// Only Proceed if (IsActivated == N)
				if(!IsActivated.equals("N")){
					// This user is already Activated. Inform them so, and Forward back to the Homepage.
					String CancelMessage = "User " + RegistrationUsername + " is already Activated.";
					Response.sendRedirect("/JSP/PayPal/PayPalForwardMessage.jsp?CancelMessage="+CancelMessage+"");
				}else {
					
					//System.out.println("User " + RegistrationUsername + " is NOT Activated. Proceed with activation steps."); 
					
					// Get the pieces for the Account Activation URL
					String getScheme = Request.getScheme().toString(); // http
					String getServerName = Request.getServerName().toString(); // localhost
					String getServerPort = ":" + Request.getServerPort(); // 8080
					String PayPalSubmitPage = "/JSP/PayPal/PayPalRegistrationSubmit.jsp";
					String PayPalSubmitPageQueryString = "?RegistrationUsername=" + RegistrationUsername;

					// If we are NOT on a Test Environment with "localhost" as Server Name
					// Remove the port as it won't be needed in a real envrionment
					if(!getServerName.equals("localhost")){getServerPort = "";}
					
					// Generate the Activation URL
					String PayPalAccountActivationURL = getScheme + "://" + getServerName + getServerPort + PayPalSubmitPage + PayPalSubmitPageQueryString;
					
					// Send the Activation E-Mail to the user
					EMailUserActivation(EMail, PayPalAccountActivationURL);
					
					//Set the AccountIDToReturn
					AccountIDToReturn = AccountID;
				}
				
				// NOTE: Even after Redirecting the code below is still Executed
				
				// Reset the Pointer changed by the if statement above
				SQLQueryOutput.beforeFirst();
				
				// Close the ResultSet
				try {SQLQueryOutput.close();} catch (SQLException e) {e.printStackTrace();}
				
				// Write the HTML Successful Response
				// DISABLED;  Handled By AJAX Call
				// GlobalMethods.writeForwardHTMLSuccessResponse(Request, Response, GlobalTools.GTV_UserProfile, "");
				
				}else {
				
				// Close the ResultSet
				try {SQLQueryOutput.close();} catch (SQLException e) {e.printStackTrace();}
				
				// This user does NOT exist. Inform them so, and Forward back to the Homepage.
				String CancelMessage = "User " + RegistrationUsername + " does not exist.";
				Response.sendRedirect("/JSP/PayPal/PayPalForwardMessage.jsp?CancelMessage="+CancelMessage+"");
			}
						
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
		
			
    	return AccountIDToReturn;
    	
/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ */
		/* END JavaBean Response */
		
    	
    }
    
    // Send the user his Activation E-Mail
    public void EMailUserActivation(String ActivationUserEMail, String PayPalAccountActivationURL){

		// Declare and Configure the MailJavaBean to send the user an E-Mail
		MailJavaBean PayPalMailJavaBean = new MailJavaBean();
		
		// Set the E-Mail Receiver
		PayPalMailJavaBean.setEMailReceiver(ActivationUserEMail);

		// Set the E-Mail Body
		String ContactMeFormUserInput =
		"<h3>" +
		"<p>" +
		"<b>Sender First Name: </b>" +
		"Dalvis" +
		"</p>" +
		"<p>" +
		"<b>Sender Last Name: </b>" +
		"Gomez" +
		"</p>" +
		"<p>" +
		"<b>Sender E-Mail: </b>" +
		"RealLeanFitness@GMail.com" +
		"</p>" +
		"<p>" +
		"<b>SenderMessage: </b>" +
		"Your account Activation URL is " + PayPalAccountActivationURL +
		"</p>" +
		"<br/><br/>" +
		"<p> ~ Real Lean Fitness Mailing System</p>" +
		"</h3>";

		//Set the E-Mail Header Subject
		PayPalMailJavaBean.setEMailSubject("Real Lean Fitness Activation");

		//Set the E-Mail Body Message
		PayPalMailJavaBean.setEMailBody(ContactMeFormUserInput);

		// Set the File Attachment (NOT WORKING, NOR NEEDED HERE)
		//MyMailJavaBean.setAttachment(request.getParameter( "SenderFileAttachment" ));

		// Send the E-Mail
		//PayPalMailJavaBean.sendEMail();
		
    }
    
}