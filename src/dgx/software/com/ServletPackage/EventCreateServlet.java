/* 
GOAL: Logs into the Database to Insert/Create Events.

PROPERTIES: Back-End Work
1. User SQL Database Access (SQL)
*/

package dgx.software.com.ServletPackage;

import java.io.PrintWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")
public class EventCreateServlet extends HttpServlet {
	
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
		writeServletResponse(false, Request, Response);
	}

	// process "Post" requests from clients
	protected void doPost(HttpServletRequest Request, HttpServletResponse Response) throws ServletException, IOException {
		writeServletResponse(false, Request, Response);
	}
	
	// Create the Servlet Response
	public void writeServletResponse(boolean writeToFile, HttpServletRequest Request, HttpServletResponse Response) throws IOException {
	
		// Returns null if no session already exists 
		HttpSession CurrentSession =  Request.getSession(false);
		
		// Check if we have an existing Session
		if (CurrentSession == null) {
		
			try {
				// If the user does not have a session redirect them back to the Session Writer Servlet
				Response.sendRedirect("/BrowserValidationServlet");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
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
			
			// Variables for Account session information
			String SessionAccountID = (String) CurrentSession.getAttribute("AccountID");
			String SessionUsername = (String) CurrentSession.getAttribute("Username");
			
			// Variables for Event information
			String Account_ID = SessionAccountID;
			String Host_Username = SessionUsername;
			String Event_Name = Request.getParameter("EventCreateName");
			String Event_Category  = Request.getParameter("EventCreateCategory");
			String Event_Sub_Category = Request.getParameter("EventCreateSubCategory");
			String Event_Description = Request.getParameter("EventCreateDescription");
			String Event_Address = Request.getParameter("EventCreateAddress");
			String Event_Country = Request.getParameter("EventCreateCountry");
			String Event_Start_Date_And_Time = Request.getParameter("EventCreateStartDateAndTime");
			String Event_End_Date_And_Time = Request.getParameter("EventCreateEndDateAndTime");
			String Event_TimeZone = Request.getParameter("EventCreateTimeZone");
			
			// Convert the Event time into the format used by MySQL
			String DateCurrentFormat = "MM/dd/yyyy @ hh:mm aa";
			String DateDesiredFormat = "yyyy-MM-dd HH:mm:ss";
			Event_Start_Date_And_Time = convertTimeFormat(Event_Start_Date_And_Time,DateCurrentFormat,DateDesiredFormat);
			Event_End_Date_And_Time = convertTimeFormat(Event_End_Date_And_Time,DateCurrentFormat,DateDesiredFormat);
			
			// Variables for Event creation Date
			DateFormat DateFormat = new SimpleDateFormat("yyyy/MM/dd");
			DateFormat TimeFormat = new SimpleDateFormat("HH:mm:ss");
			Date CurrentDate = new Date();
			String Event_Creation_Date = DateFormat.format(CurrentDate);
			String Event_Creation_Time = TimeFormat.format(CurrentDate);
			
			// Create an Entry for the RLFDB_Host_Events Table.
			String AccountSQLQuery = "INSERT INTO RLFDB_Host_Events (" +
					"Account_ID," +
					"Host_Username," +
					"Event_Name," +
					"Event_Category ," +
					"Event_Sub_Category," +
					"Event_Description," +
					"Event_Address," +
					"Event_Country," +
					"Event_Start_Date_And_Time," +
					"Event_End_Date_And_Time," +
					"Event_TimeZone," +
					"Date_Submitted," +
					"Time_Submitted" +
					")" +
					"VALUES(" +
					"\""+Account_ID+"\"," +
					"\""+Host_Username+"\"," +
					"\""+Event_Name+"\"," +
					"\""+Event_Category+"\"," +
					"\""+Event_Sub_Category+"\"," +
					"\""+Event_Description+"\"," +
					"\""+Event_Address+"\"," +
					"\""+Event_Country+"\"," +
					"\""+Event_Start_Date_And_Time+"\"," +
					"\""+Event_End_Date_And_Time+"\"," +
					"\""+Event_TimeZone+"\"," +
					"\""+Event_Creation_Date+"\"," +
					"\""+Event_Creation_Time+"\"" +
					");" +
					"";
			
			System.out.println("We Executed the following...");
			System.out.println(AccountSQLQuery);

			// Create the Account
			statement.executeUpdate(AccountSQLQuery);
		
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
		out.println("<title>Event Create Servlet</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<script type='text/javascript'>");
		out.println("alert('Event created successfully!');");
		out.println("</script>");
		out.println("<meta http-equiv='REFRESH' content='0;url=/BrowserValidationServlet'/>");
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
		out.println("<meta http-equiv='REFRESH' content='0;url=/BrowserValidationServlet'/>");
		out.println("</body>");
		out.println("</html>");

/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ */
/* END HTML RESPONSE */
		
		// Close the stream to complete the page
		out.close();
		
	}
	
	// Converts any Date and Time from one format to another
	public String convertTimeFormat(String DateToConvert, String DateCurrentFormat, String DateDesiredFormat){
		
		// Declare and Initialize the Date and Time along with the formats
		String GivenDate = DateToConvert;
		DateFormat CurrentFormat = new SimpleDateFormat(DateCurrentFormat);
		DateFormat DesiredFormat = new SimpleDateFormat(DateDesiredFormat);
		
		// Declare an Empty Date object
		Date DateObject = null;

		// Record the date with the current format on the DateObject
		try{
			DateObject = CurrentFormat.parse(GivenDate);
		}catch ( ParseException e ){ e.printStackTrace();}
		
		// Try to convert the DateObject's Current Format into the Desired Format
		if( DateObject != null ){
		    String formattedDate = DesiredFormat.format(DateObject);
		    return formattedDate;
		}
		// If the conversion failed return null and send out an error alert
		else{
			System.err.println("Method convertTimeFormat() Failed to convert the Date and Time!");
			System.err.println("From \"" + GivenDate + "\" with format \"" + DateCurrentFormat + "\" To format \"" + DateDesiredFormat + "\"");
			return null;
		}
		
	}
	
} // end class SurveyServlet
