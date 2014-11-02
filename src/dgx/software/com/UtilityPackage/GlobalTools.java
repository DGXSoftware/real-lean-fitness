package dgx.software.com.UtilityPackage;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

public class GlobalTools {

/**************************************************************************************************/
/* START GLOBAL VARIABLES */	
/**************************************************************************************************/
    
	// <%= GlobalTools.GTV_Homepage %>
	public static final String GTV_Homepage = "/";
	
	// <%= GlobalTools.GTV_UserProfile %>
	public static final String GTV_UserProfile = "/JSP/UserPages/UserProfile.jsp";
	
	// <%= GlobalTools.GTV_UserSettings %>
	public static final String GTV_UserSettings = "/JSP/UserPages/UserSettings.jsp";
	
	// <%= GlobalTools.GTV_Settings_UserAccount %>
	public static final String GTV_Settings_UserAccount = "/JSP/UserPages/Settings/UserAccount.jsp";
	
	// <%= GlobalTools.GTV_Settings_UserInformation %>
	public static final String GTV_Settings_UserInformation = "/JSP/UserPages/Settings/UserInformation.jsp";
	
	// <%= GlobalTools.GTV_Settings_PasswordChange %>
	public static final String GTV_Settings_PasswordChange = "/JSP/UserPages/Settings/PasswordChange.jsp";
	
	// <%= GlobalTools.GTV_Settings_ForgotPasswordChange %>
	public static final String GTV_Settings_ForgotPasswordChange = "/JSP/UserPages/Settings/ForgotPasswordChange.jsp";
	
	// <%= GlobalTools.GTV_Settings_RequestForgotPasswordChange %>
	public static final String GTV_Settings_RequestForgotPasswordChange = "/JSP/UserPages/Settings/RequestForgotPasswordChange.jsp";
	
	// <%= GlobalTools.GTV_Settings_NewsletterSubscription %>
	public static final String GTV_Settings_NewsletterSubscription = "/JSP/UserPages/Settings/NewsletterSubscription.jsp";
	
	// <%= GlobalTools.GTV_Settings_EMailVerification %>
	public static final String GTV_Settings_EMailVerification = "/JSP/UserPages/Settings/EMailVerification.jsp";
    
	// <%= GlobalTools.GTV_ContactUs %>
	public static final String GTV_ContactUs = "/JSP/Mail/ContactUs.jsp";
	
	// <%= GlobalTools.GTV_PayPalRegistrationSubmit %>
	public static final String GTV_PayPalRegistrationSubmit = "/JSP/PayPal/PayPalRegistrationSubmit.jsp";
    
	// <%= GlobalTools.GTV_CountdownForwardMessage %>
	public static final String GTV_CountdownForwardMessage = "/JSP/Tools/CountdownForwardMessage.jsp";
	
	// <%= GlobalTools.GTV_Error_QueryStringReader %>
	public static final String GTV_Error_QueryStringReader = "/JSP/Error/QueryStringReader.jsp";
    
	// <%= GlobalTools.GTV_Servlet_Logout %>
	public static final String GTV_Servlet_Logout = "/LogOutServlet";
    

/**************************************************************************************************/
/* END GLOBAL VARIABLES */	
/**************************************************************************************************/
	
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
	
	
	/*************************************************************************************************
	NAME:        isUserCurrentlyLoggedIn
	DESCRIPTION: Lets you know if the current user is logged in by reading the active session.
	PARAMETERS:  (HttpServletRequest Request, HttpServletResponse Response)
	RETURN:      VOID
	SIDE-EFFECT: If provided a "SuccessMessage" It will alert before forwarding.
	*************************************************************************************************/
	// Returns a Successful HTML Response and forward appropriately.
	public static boolean isUserCurrentlyLoggedIn(HttpServletRequest Request, HttpServletResponse Response){

	// Assume we don't have a Session Account ID
	String SessionAccountID = "";

	// Returns null if no session already exists 
	HttpSession CurrentSession =  Request.getSession(false);

	// If we have a session attempt to retrieve the SessionAccountID
	if (CurrentSession != null) {SessionAccountID = (String) CurrentSession.getAttribute("AccountID");}

	// If attempts to retrieve the SessionAccountID returned null, make it an Empty String Object for operations
	if (SessionAccountID == null) {SessionAccountID = "";}

	// Check weather or not the user is logged Currently logged in
	if(SessionAccountID.equals("")){
	// User is NOT Currently Logged In
	return false;
	}else{
	// User is Currently Logged In
	return true;
	}

	}
	
	/*************************************************************************************************
	NAME:        displayUserStatusMessage
	DESCRIPTION: Displays a Fixed DIV that reminds non activated users how to activate.
	PARAMETERS:  (JspWriter out, String SessionUsername, String SessionIsActivated)
	RETURN:      VOID
	SIDE-EFFECT: NONE.
	*************************************************************************************************/
	// Returns a Successful HTML Response and forward appropriately.
	public static void displayUserStatusMessage(JspWriter out, String SessionUsername, String SessionIsActivated, String SessionIsVerified) throws IOException{
		
		// START Activation HTML Response
		out.println("");
		out.println("<style>");
		out.println("");
		out.println(".ActivationFixedMessage {");
		out.println("top: auto;");
		out.println("left: auto;");
		out.println("max-height: 100%;");
		out.println("width: 100%;");
		out.println("overflow-y: auto;");
		out.println("color: black;");
		out.println("background-color: orange;");
		out.println("border: solid 1px red;");
		out.println("padding: 2px 5px;");
		out.println("margin: auto;");
		out.println("text-align: center;");
		out.println("position: relative;");
		out.println("}");
		out.println("");
		out.println("</style>");
		out.println("");
        
		// If SessionIsActivated is 'N' for No, then show the message below.
		if(SessionIsActivated.equals("N")){
		
		// If the user is Not activated, point them to the Account Activation Site
		String AccountActivationURL = GlobalTools.GTV_PayPalRegistrationSubmit + "?" + "RegistrationUsername=" + SessionUsername;
		
		out.println("<div class='ActivationFixedMessage'>");
		out.println("<p>This account is not activated. Please <a href='" + AccountActivationURL + "'>Click here</a> to activate your account.</p>");
		out.println("</div>");
	    
	    }
		// END Activation HTML Response

		// START Verification HTML Response
		out.println("");
		out.println("<style>");
		out.println("");
		out.println(".VerificationFixedMessage {");
		out.println("top: auto;");
		out.println("left: auto;");
		out.println("max-height: 100%;");
		out.println("width: 100%;");
		out.println("overflow-y: auto;");
		out.println("color: black;");
		out.println("background-color:cyan;");
		out.println("border: solid 1px red;");
		out.println("padding: 2px 5px;");
		out.println("margin: auto;");
		out.println("text-align: center;");
		out.println("position: relative;");
		out.println("}");
		out.println("");
		out.println("</style>");
		out.println("");
        
		// If SessionIsActivated is 'N' for No, then show the message below.
		if(SessionIsVerified.equals("N")){
		
		// If the user is Not activated, point them to the Account Activation Site
		String AccountActivationURL = GlobalTools.GTV_Settings_EMailVerification;
			
		out.println("<div class='VerificationFixedMessage'>");
		out.println("<p>Your E-Mail is not verified. Please <a href='" + AccountActivationURL + "'>Click here</a> to verify your E-Mail.</p>");
		out.println("</div>");
	    
	    }
		// END Verification HTML Response
		
		
		
		
		
		
		
		// Close the stream to complete the page
		//out.close();
		
	}	
	
	/*************************************************************************************************
	NAME:        getTableColumnAndValuePairViaAccountID
	DESCRIPTION: Gets all the Column Names and Values from a specific Table using
	a desired AccountID. It allows you to choose the columns via an Array.
	Once the 2D ArrayList<ArrayList<String>> is retrieved the data is retrieved as following
	Index 0 = Column Names
	Index 1 = Values
	PARAMETERS:  (HttpServletRequest Request, HttpServletResponse Response, String TableName, String SessionAccountID, String [] Table_Column_Ignore)
	RETURN:      ArrayList<ArrayList<String>>
	SIDE-EFFECT: NONE.
	*************************************************************************************************/
	public static ArrayList<ArrayList<String>> getTableColumnAndValuePairViaAccountID(HttpServletRequest Request, HttpServletResponse Response, String TableName, String SessionAccountID, String [] Table_Column_Add) throws SQLException {
		
		// Temporary ArrayList Variables
		ArrayList<ArrayList<String>> Temporary_ColumnValuePair = new ArrayList<ArrayList<String>>();
		ArrayList <String> Temporary_Column_List = new ArrayList<String>();
		ArrayList <String> Temporary_Values_List = new ArrayList<String>();

		// Generate the SQL Select Query
		// NOTE: Where clause strings are not case sensitive
		String ColumnsToSelect = "";
		for(int i = 0; i < Table_Column_Add.length; i++){
			ColumnsToSelect = ColumnsToSelect.concat(Table_Column_Add[i]);
			if(i < (Table_Column_Add.length -1)){ ColumnsToSelect = ColumnsToSelect.concat(" ,"); }
		}
		String SelectSQLQuery = "SELECT "+ColumnsToSelect+" FROM "+TableName+" WHERE Account_ID="+SessionAccountID+";";
		
		// Get the SQLQueryOutput
		ResultSet SQLQueryResultSetOutput = SQLStatement.executeQuery(SelectSQLQuery);
		
		// If we DO NOT Have an Empty Result Set, Work with it and send a successful HTML Response
		if(SQLQueryResultSetOutput.next()){
		
		// Reset the Pointer changed by the if statement above
			SQLQueryResultSetOutput.beforeFirst();
		
		// Records the ResultSetMetaData
		ResultSetMetaData SQLQueryOutputMetaData = null;
		
		try {
	
	        // Get the ResultSetMetaData
		    SQLQueryOutputMetaData = SQLQueryResultSetOutput.getMetaData();
	
		} catch (SQLException sqlException) {
	        sqlException.printStackTrace();		

		}
		
    	try {
        
    	// Go over the Rows
    	while (SQLQueryResultSetOutput.next()) {

    	// Go over the Columns
    	// NOTE : i = 2 because we want to skip the first item (Account_ID)
    	for(int i = 1 ; i < SQLQueryOutputMetaData.getColumnCount() + 1; i++){
    		
    		// Get the Current SQL Result Values
    		String CurrentColumnName = SQLQueryOutputMetaData.getColumnName(i);
    		String CurrentValue = SQLQueryResultSetOutput.getString(i);
            
    		// Add the current Column and Value
    		Temporary_Column_List.add(CurrentColumnName);
    		Temporary_Values_List.add(CurrentValue);
    		
		}
			
		}
			} catch (SQLException SQLEX) {
					SQLEX.printStackTrace();
				}
			}

		// Add the Columns and Values List to the ColumnValue Pair List
		Temporary_ColumnValuePair.add(Temporary_Column_List);
		Temporary_ColumnValuePair.add(Temporary_Values_List);
		
		return Temporary_ColumnValuePair;
		
	}
	
		/*************************************************************************************************
		NAME:        writeForwardHTMLSuccessResponse
		DESCRIPTION: Handle successful forwarding responses.
		PARAMETERS:  (HttpServletRequest Request, HttpServletResponse Response)
		RETURN:      VOID
		SIDE-EFFECT: If provided a "SuccessMessage" It will alert before forwarding.
		*************************************************************************************************/
		// Returns a Successful HTML Response and forward appropriately.
		public static void writeForwardHTMLSuccessResponse(HttpServletRequest Request, HttpServletResponse Response, String ForwardAddress, String SuccessMessage){

			// Create the PrintWriter to print the HTML Page Response
			// The response is sent to the client through the PrintWriter object
			// obtained from the HttpServletResponse object.
			// NOTE: If the response is binary data, such as an image, method
			// getOutputStream is used to obtain a reference to a
			// ServletOutputStream object.
			
			// set up response to client
			Response.setContentType("text/html");
			PrintWriter out = null;
			try {
				out = Response.getWriter();
			} catch (IOException e) {
				System.out.println("INTERNAL ERRROR: writeForwardHTMLSuccessResponse()");
				e.printStackTrace();
			}
			
			out.println("<?xml version = '1.0'?>");
			out.println("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'>");
			out.println("<html xmlns='http://www.w3.org/1999/xhtml'>");
			out.println("<head>");
			out.println("<title>Login Servlet</title>");
			out.println("</head>");
			out.println("<body>");
			
			// Alert if "SuccessMessage" is not Empty
			if(!SuccessMessage.equals("")){
			out.println("<script type='text/javascript'>");
			out.println("alert('"+SuccessMessage+"');");
			out.println("</script>");
			}
			
			out.println("<meta http-equiv='REFRESH' content='0;url="+ForwardAddress+"'/>");
			out.println("</body>");
			out.println("</html>");

			// Close the stream to complete the page
			out.close();
			
		}
		
		/*************************************************************************************************
		NAME:        writeHTMLErrorResponse
		DESCRIPTION: Handle failure forwarding responses.
		PARAMETERS:  (HttpServletRequest Request, HttpServletResponse Response)
		RETURN:      VOID
		SIDE-EFFECT: If provided an "ErrorMessage" It will alert before forwarding.
		*************************************************************************************************/
		
		// Returns an Error Message HTML Response and forward appropriately.
		public static void writeForwardHTMLErrorResponse(HttpServletRequest Request, HttpServletResponse Response,String ForwardAddress, String ErrorMessage){

			// Create the PrintWriter to print the HTML Page Response
			// The response is sent to the client through the PrintWriter object
			// obtained from the HttpServletResponse object.
			// NOTE: If the response is binary data, such as an image, method
			// getOutputStream is used to obtain a reference to a
			// ServletOutputStream object.
			
			// set up response to client
			Response.setContentType("text/html");
			PrintWriter out = null;
			try {
				out = Response.getWriter();
			} catch (IOException e) {
				System.out.println("INTERNAL ERRROR: writeForwardHTMLErrorResponse()");
				e.printStackTrace();
			}
			
			out.println("<?xml version = '1.0'?>");
			out.println("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'>");
			out.println("<html xmlns='http://www.w3.org/1999/xhtml'>");
			out.println("<head>");
			out.println("<title>Internal Error!</title>");
			out.println("</head>");
			out.println("<body>");
			
			// Alert if "ErrorMessage" is not Empty
			if(!ErrorMessage.equals("")){
			out.println("<script type='text/javascript'>");
			out.println("alert('"+ErrorMessage+"');");
			out.println("</script>");
			}
			
			out.println("<meta http-equiv='REFRESH' content='0;url="+ForwardAddress+"'/>");
			out.println("</body>");
			out.println("</html>");

			// Close the stream to complete the page
			out.close();
			
		}
	
		/*************************************************************************************************
		NAME:        writeHTMLNotificationResponse
		DESCRIPTION: Handle failure forwarding responses.
		PARAMETERS:  (HttpServletRequest Request, HttpServletResponse Response)
		RETURN:      VOID
		SIDE-EFFECT: If provided an "ErrorMessage" It will alert before forwarding.
		*************************************************************************************************/
		
		// Returns an Error Message HTML Response and forward appropriately.
		public static void writeHTMLNotificationResponse(HttpServletRequest Request, HttpServletResponse Response,String ForwardAddress, String NotificationMessage){

			// Create the PrintWriter to print the HTML Page Response
			// The response is sent to the client through the PrintWriter object
			// obtained from the HttpServletResponse object.
			// NOTE: If the response is binary data, such as an image, method
			// getOutputStream is used to obtain a reference to a
			// ServletOutputStream object.
			
			// set up response to client
			Response.setContentType("text/html");
			PrintWriter out = null;
			try {
				out = Response.getWriter();
			} catch (IOException e) {
				System.out.println("INTERNAL ERRROR: writeHTMLNotificationResponse()");
				e.printStackTrace();
			}
			
			out.println("<?xml version = '1.0'?>");
			out.println("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'>");
			out.println("<html xmlns='http://www.w3.org/1999/xhtml'>");
			out.println("<head>");
			out.println("<title>Internal Error!</title>");
			out.println("</head>");
			out.println("<body>");
			
			// Alert if "ErrorMessage" is not Empty
			if(!NotificationMessage.equals("")){
			out.println("<script type='text/javascript'>");
			out.println("alert('"+NotificationMessage+"');");
			out.println("</script>");
			}
			
			out.println("<meta http-equiv='REFRESH' content='0;url="+ForwardAddress+"'/>");
			out.println("</body>");
			out.println("</html>");

			// Close the stream to complete the page
			out.close();
			
		}
		
		// Returns a Successful HTML Response and forward appropriately.
		public static void writeForwardHTMLSuccessResponse(ServletConfig config){
	
		}
		
		// Compares a Start Date Vs. a Stop Date and if It's older than the specified hours mark it as expired (Calculates in 24 Hours)
		public static boolean isLinkExpired(String OldDateAndTime){
	 
	        // Get the Current Date
			Calendar c = Calendar.getInstance();
			SimpleDateFormat SDF = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			String NewDateAndTime = SDF.format(c.getTime());
	        
			// Set how many hours would have to pass before the item is marked as Expired
			int HoursPassedToMarkAsExpired = 2;
			
			//HH converts hour in 24 hours format (0-23), day calculation
			SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	 
			Date d1 = null;
			Date d2 = null;
	 
			try {
				d1 = format.parse(OldDateAndTime);
				d2 = format.parse(NewDateAndTime);
	 
				//in milliseconds
				long diff = d2.getTime() - d1.getTime();
	 
				//long diffSeconds = diff / 1000 % 60;
				//long diffMinutes = diff / (60 * 1000) % 60;
				long diffHours = diff / (60 * 60 * 1000) % 24;
				long diffDays = diff / (24 * 60 * 60 * 1000);
	 
				//System.out.print(diffDays + " days, ");
				//System.out.print(diffHours + " hours, ");
				//System.out.print(diffMinutes + " minutes, ");
				//System.out.print(diffSeconds + " seconds.");
	 
				// Set the URL as expired if It's over 1 day old
				if (diffDays > 0) { 
					//System.out.println("\nDays expired it"); 
					return true;
				}
				
				// Set the URL as expired if It's over the specified hours
				if (diffHours >= HoursPassedToMarkAsExpired) { 
					//System.out.println("\nHours expired it"); 
					return true;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
			
		}
		
}
