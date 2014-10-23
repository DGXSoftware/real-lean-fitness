package dgx.software.com.JavaBeanPackage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserSQLOperationJavaBean {

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
			// Replaces "GlobalMethods.writeForwardHTMLErrorResponse(Request, Response, GlobalTools.GTV_Homepage, UnavailableErrorMessage);"
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
    
    // Print the Database Connection parameters
    public boolean activateAccount(HttpServletRequest Request, HttpServletResponse Response, String Account_ID_To_Activate) throws IOException{
    	/*
    	<!-- Activates the Account -->
    	<!-- 
    	SELECT Account_ID, Username, IsActivated FROM RLF_Accounts WHERE Username = '1995';
    	UPDATE RLF_Accounts SET IsActivated='Y' WHERE Username='1995';
    	SELECT Account_ID, Username, IsActivated FROM RLF_Accounts WHERE Username = '1995';
    	-->
    	*/

		/* START JavaBean Response */
/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ */

    	// SQLQuery = "UPDATE RLF_Accounts SET IsActivated='Y' WHERE Account_ID='"+Account_ID_To_Activate+"';";
		
    	
		// Assume that the Account ID was NOT Activated
		boolean AccountIDWasActivated = false;
		
			// Process the accoutn activation
			try {

				String ActivationSQLQuery = "UPDATE RLF_Accounts SET Is_Activated='Y' WHERE Account_ID='"+Account_ID_To_Activate+"';";

				// Execute the Activation
				SQLStatement.executeUpdate(ActivationSQLQuery);
				
				// SQL Query
				String SQLQuery = "SELECT Account_ID, Username, EMail, Is_Activated FROM RLF_Accounts WHERE Account_ID = '"+Account_ID_To_Activate+"';";
				
				// Get the SQLQueryOutput
				ResultSet SQLQueryOutput = SQLStatement.executeQuery(SQLQuery);
				
				// If we DO NOT Have an Empty Result Set, Work with it (Account Found).
				if(SQLQueryOutput.next()){
		
				// Get Username Account ID below and Redirect accordingly
					
				// Set the User information Variables
				//String AccountID = SQLQueryOutput.getString("Account_ID");
				String RegistrationUsername = SQLQueryOutput.getString("Username");
				String EMail = SQLQueryOutput.getString("EMail");
				String IsActivated = SQLQueryOutput.getString("Is_Activated");

				// Check if the User is already activated.
				// If the user is anything, but NOT Activated (IsActivated == N) then redirect them out of here
				// Only Proceed if (IsActivated == N)
				if(!IsActivated.equals("Y")){
					
					// Reset the Pointer changed by the if statement above
					SQLQueryOutput.beforeFirst();
					
					// Close the ResultSet
					try {SQLQueryOutput.close();} catch (SQLException e) {e.printStackTrace();}
					
					
					// E-Mail the user about the successful Activation.
					EMailUserActivation(EMail, RegistrationUsername);
					
					// Mark a Successful activation
					AccountIDWasActivated = true;
					return AccountIDWasActivated;
				}else{
					// This user is already Activated. Inform them so, and Forward back to the Homepage.
					//String CancelMessage = "User " + RegistrationUsername + " is already Activated.";
					//Response.sendRedirect(GlobalTools.GTV_PayPalForwardMessage + "?CancelMessage="+CancelMessage+"");
					// Mark a Bas activation
					AccountIDWasActivated = true;
					return AccountIDWasActivated;
				}
				
				}else {
				
				// Close the ResultSet
				try {SQLQueryOutput.close();} catch (SQLException e) {e.printStackTrace();}
				
				// This user does NOT exist. Inform them so, and Forward back to the Homepage.
				//String CancelMessage = "This User does not exist.";
				//Response.sendRedirect(GlobalTools.GTV_PayPalForwardMessage + "?CancelMessage="+CancelMessage+"");
			}
						
			} // end try
			// if database exception occurs, return error page
			catch (SQLException SQLEX) {
				
				// Print the Stack Trace
				SQLEX.printStackTrace();		
				
				// Respond with an error message
				//String UnknownErrorMessage = "Unknown Database error occurred. Please Try again later.";
				// Replaces "GlobalMethods.writeForwardHTMLErrorResponse(Request, Response, GlobalTools.GTV_Homepage, UnknownErrorMessage);"
				//throw new RuntimeException(UnknownErrorMessage);
				AccountIDWasActivated = false;
				return AccountIDWasActivated;
				
			}
		
			
    	return AccountIDWasActivated;
    	
/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ */
		/* END JavaBean Response */
    	
    }
	
    // Send the user his Activation E-Mail
    public void EMailUserActivation(String ActivationUserEMail, String RegistrationUsername){

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
		RegistrationUsername + ", " + "Your account was successfully activated." +
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
