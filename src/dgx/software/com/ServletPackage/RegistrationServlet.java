/* 
GOAL: Logs into the Database to Insert/Create an user account.

PROPERTIES: Back-End Work
1. User SQL Database Access (SQL)
*/

package dgx.software.com.ServletPackage;

import java.io.PrintWriter;
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

import dgx.software.com.UtilityPackage.BrowserValidator;

@SuppressWarnings("serial")
public class RegistrationServlet extends HttpServlet {
	
	private Connection connection;
	private Statement statement;

	// set up database connection and create SQL statement
	public void init(ServletConfig config) throws ServletException {
		
		// attempt database connection and create Statements
		try {
			// The config.getInitParameter() Parameters are variables 
			// in the web.xml file which are declared as "init-param" element  
			// These web.xml variables are used to connect to the database 
			Class.forName(config.getInitParameter("DriverName"));
			connection = DriverManager.getConnection(
				config.getInitParameter("DatabaseURL"), 
				config.getInitParameter("DatabaseUser"), 
				config.getInitParameter("DatabasePassword"));

			// create Statement to query database
			statement = connection.createStatement();
			
		} // end try
		// for any exception throw an UnavailableException to
		// indicate that the servlet is not currently available
		catch (Exception exception) {
			exception.printStackTrace();
			throw new UnavailableException(exception.getMessage());
		} // end catch
	} // end method init

	// process "Get" requests from clients
	protected void doGet(HttpServletRequest Request, HttpServletResponse Response) throws ServletException, IOException {
		
		// Call the doPost() Method
		doPost(Request, Response);
	}

	// process "Post" requests from clients
	protected void doPost(HttpServletRequest Request, HttpServletResponse Response) throws ServletException, IOException {
		
		// Figure out if Cookies are Enabled
		boolean CookiesEnabled = BrowserValidator.areCookiesEnabled(Request, Response);
		
		// If Cookies are Disabled forward the user to "/JSP/JSPErrorPages/CookiesDisabled.jsp"
		if(CookiesEnabled == false){
			final String DisabledCookiesURL = "/JSP/JSPErrorPages/CookiesDisabled.jsp";
			Response.sendRedirect(DisabledCookiesURL);
		} else {
		
		// If Cookies are Enabled proceed accordingly
		// process "Post" requests from clients
		writeServletResponse(false, Request, Response);
		
		}
	}
	
	// Create the Servlet Response
	public void writeServletResponse(boolean writeToFile, HttpServletRequest Request, HttpServletResponse Response) throws IOException {
	
		// Create the Printwriter to print the HTML Page Response
		// The response is sent to the client through the PrintWriter object
		// obtained from the HttpServletResponse object.
		// NOTE: If the response is binary data, such as an image, method
		// getOutputStream is used to obtain a reference to a
		// ServletOutputStream object.

		// set up response to client
		Response.setContentType("text/html");
		PrintWriter out = Response.getWriter();
		
/*		
		ENMFileWriter MainFileWriter = null;
		if (writeToFile == true) {
			// Write To File
			MainFileWriter = new ENMFileWriter(null, "", "Registration Handler Page.html");
			
			// Delete the Previous File
			MainFileWriter.deleteFile();
																		
		} else {
			// Write To Servlet
			MainFileWriter = new ENMFileWriter(Response.getWriter(), "", "");
		}
*/

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
			statement.executeUpdate(AccountSQLQuery);
		
			// Records the assigned Account ID for the Currently created Username.
			String AccountID = "";
			
			// Select the Account_ID From the Currently created Username.
			String AccountIDSQLQuery = "SELECT Account_ID FROM RLFDB_Accounts WHERE Username ='"+Username+"';";
			
			// Get the AccountIDSQLQuery ResultSet
			ResultSet UsernameSQLQueryOutput = statement.executeQuery(AccountIDSQLQuery);
			
			// If we DO NOT Have an Empty Result Set, Work with it.
			if(UsernameSQLQueryOutput.next()){
			
			// Set the assigned Account ID for the Currently created Username.
			AccountID = UsernameSQLQueryOutput.getString("Account_ID");
			
			// Close the ResultSet
			UsernameSQLQueryOutput.close();
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
			statement.executeUpdate(UserInformationSQLQuery);
			
			// Write the Successful HTML Response
			writeHTMLSuccessResponse(Request, Response, out);
			
/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ */
		/* END Servlet Response */
			
		} // end try
		// if database exception occurs, return error page
		catch (SQLException sqlException) {
			sqlException.printStackTrace();		
			
			// Respond with an error message
			String ErrorMessage = "Database error occurred. Try again later.";
			writeHTMLErrorResponse(Request, Response, out, ErrorMessage);
			
		} // end catch
	
	}

	// close SQL statements and database when servlet terminates
	public void destroy() {
		// attempt to close statements and database connection
		try {
			statement.close();
			connection.close();
		} // end try
		// handle database exceptions by returning error to client
		catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} // end catch
	} // end method destroy
	
	
	// Returns a Successful HTML Response
	private void writeHTMLSuccessResponse(HttpServletRequest Request, HttpServletResponse Response,PrintWriter out){
		
/* START HTML RESPONSE */
/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ */

		out.println("<?xml version = '1.0'?>");
		out.println("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'>");
		out.println("<html xmlns='http://www.w3.org/1999/xhtml'>");
		out.println("<head>");
		out.println("<title>Registration Servlet</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<script type='text/javascript'>");
		out.println("alert('Account created successfully!');");
		out.println("</script>");
		out.println("<meta http-equiv='REFRESH' content='0;url=/'/>");
		out.println("</body>");
		out.println("</html>");

/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ */
/* END HTML RESPONSE */
		
		// Close the stream to complete the page
		out.close();
	}
	
	// Returns an Error Message HTML Response
	private void writeHTMLErrorResponse(HttpServletRequest Request, HttpServletResponse Response, PrintWriter out, String ErrorMessage){
		
/* START HTML RESPONSE */
/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ */

		
		out.println("<?xml version = '1.0'?>");
		out.println("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'>");
		out.println("<html xmlns='http://www.w3.org/1999/xhtml'>");
		out.println("<head>");
		out.println("<title>Internal Error!</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<script type='text/javascript'>");
		out.println("<!-- START DYNAMIC HTML -->");
		out.println("alert('"+ErrorMessage+"');");
		out.println("<!-- END DYNAMIC HTML -->");
		out.println("</script>");
		out.println("<meta http-equiv='REFRESH' content='0;url=/'/>");
		out.println("</body>");
		out.println("</html>");

/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ */
/* END HTML RESPONSE */
		
		// Close the stream to complete the page
		out.close();
		
	}
	
} // end class SurveyServlet
