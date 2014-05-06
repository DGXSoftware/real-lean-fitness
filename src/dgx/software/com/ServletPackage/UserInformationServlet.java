/* 
GOAL: Displays personal user information and allows for updates to the SQL table.

PROPERTIES: Front-End Work / Back-End Work
1. User Page Display (HTML/CSS)
2. User Page Interactions (HTML/JavaScript)
3. User SQL Database Access (SQL)
*/

package dgx.software.com.ServletPackage;

// A Web-based survey that uses JDBC from a servlet.

import java.io.PrintWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")
public class UserInformationServlet extends HttpServlet {
	
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
		
		// Set up response to client
		Response.setContentType("text/html");
		PrintWriter out = Response.getWriter();

		/* START Servlet Response */
/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ */

		// attempt to process a vote and display current results
		try {
			
			// Retrieve the SessionAccountID
			String SessionAccountID = (String) CurrentSession.getAttribute("AccountID");
			
			// Retrieve the SaveInformationButton Value
			String SaveInformationButton = Request.getParameter("SaveInformationButton");

			// Check if the Servlet was accessed through the button press of SaveInformationButton
			// If the Servlet was accessed through the Button, Update the Values
		    if(SaveInformationButton != null){
		    	
		    	// Retrieve the Query String Data
				String First_Name = Request.getParameter("First_Name");
				String Middle_Name = Request.getParameter("Middle_Name");
				String Last_Name = Request.getParameter("Last_Name");
				String Location_Address = Request.getParameter("Location_Address");
				String Location_City = Request.getParameter("Location_City");
				String Location_State = Request.getParameter("Location_State");
				String Location_ZipCode = Request.getParameter("Location_ZipCode");
				String Location_Country = Request.getParameter("Location_Country");
				
				// Set Empty Spaces to Null Strings
				if(First_Name == null)First_Name = "";
				if(Middle_Name == null)Middle_Name = "";
				if(Last_Name == null)Last_Name = "";
				if(Location_Address == null)Location_Address = "";
				if(Location_City == null)Location_City = "";
				if(Location_State == null)Location_State = "";
				if(Location_ZipCode == null)Location_ZipCode = "";
				if(Location_Country == null)Location_Country = "";
				
				// Create the UpdateUserInformationQuery
				String UpdateUserInformationQuery = "UPDATE RLFDB_User_Information SET " +
				"First_Name=\""+First_Name+"\"," +
				"Middle_Name=\""+Middle_Name+"\"," +
				"Last_Name=\""+Last_Name+"\"," +
				"Location_Address=\""+Location_Address+"\"," +
				"Location_City=\""+Location_City+"\"," +
				"Location_State=\""+Location_State+"\"," +
				"Location_ZipCode=\""+Location_ZipCode+"\"," +
				"Location_Country=\""+Location_Country+"\" " +
				"WHERE Account_ID=\""+SessionAccountID+"\"" +
				";" +
				"";
		    	
				// Write the HTML Notification Response
				writeHTMLNotificationResponse(Request, Response, out, "Profile information successfully updated!");
				
				// Update the User Information
				statement.executeUpdate(UpdateUserInformationQuery);
			
		    }
		    
		    
			
			// Write the HTML Successful Response
			writeHTMLSuccessResponse(Request, Response,CurrentSession, out);
		
			
/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ */
		/* END Servlet Response */
			
		} // end try
		// if am exception occurs, return error page
		catch (Exception EX) {
			EX.printStackTrace();
		} // end catch
	
	}
	
	// Returns a Successful HTML Response
	private void writeHTMLSuccessResponse(HttpServletRequest Request, HttpServletResponse Response, HttpSession CurrentSession, PrintWriter out){
		
		// START IF STATEMENT
		// Proceed ONLY if the user has a session
		if(CurrentSession != null){
		
		// attempt to process a vote and display current results
		try {
		
		// Variables for Account session information
		String SessionAccountID = (String) CurrentSession.getAttribute("AccountID");
		String SessionUsername = (String) CurrentSession.getAttribute("Username");
			
		// SQL Query
		String AccountSQLQuery = "SELECT * FROM RLFDB_User_Information WHERE Account_ID="+SessionAccountID+";";
		
		// Get the SQLQueryOutput
		ResultSet AccountSQLQueryOutput = statement.executeQuery(AccountSQLQuery);
		
		// If we DO NOT Have an Empty Result Set, Work with it and send a successful HTML Response
		if(AccountSQLQueryOutput.next()){

		// Reset the Pointer changed by the if statement above
		AccountSQLQueryOutput.beforeFirst();
		
		// Records the ResultSetMetaData
		ResultSetMetaData SQLQueryOutputMetaData = null;
		
		try {
			
			// Get the ResultSetMetaData
		    SQLQueryOutputMetaData = AccountSQLQueryOutput.getMetaData();
			
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();		
			
			// Respond with an error message
			String ErrorMessage = "Database error occurred. Try again later.";
			writeHTMLErrorResponse(Request, Response, out, ErrorMessage);
		}

/* START HTML RESPONSE */
/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ */

		out.println("<?xml version = '1.0'?>");
		out.println("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'>");
		out.println("<html xmlns='http://www.w3.org/1999/xhtml'>");
		out.println("");
		out.println("<head>");
		out.println("");
		out.println("<!-- Set the Title for the Website Page -->");
		out.println("<title>User Profile Servlet</title>");
		out.println("");
		out.println("<!-- Set the Favicon for the Website page -->");
		out.println("<link rel='Shortcut Icon' type='image/ico' href='/Images/favicon.ico'/>");
		out.println("");
		out.println("<!-- Set the Character Encoding for the Website page -->");
		out.println("<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1' />");
		out.println("");
		out.println("<!-- Include the Stylesheet Files -->");
		out.println("<link rel='stylesheet' type='text/css' href='/CSS/ENM Style.css' />");
		out.println("");
		out.println("<!-- Include the JavaScript Files -->");
		out.println("<script language='javascript' type='text/javascript' src='/JavaScript/Validation/User Information Page Validation.js' > </script>");
		out.println("");
		out.println("</head>");
		out.println("");
		out.println("<body>");
		out.println("<div id='container'>");
		out.println("<div id='main'>");
		out.println("<div id='header'></div>");
		out.println("<div id='nav'>");
		out.println("<ul>");
		out.println("<!-- START DYNAMIC HTML -->");
		out.println("<li><a href='/UserProfileServlet'>"+SessionUsername+"</a></li>");
		out.println("<!-- END DYNAMIC HTML -->");
		out.println("<li><a href='/EventHostServlet'>Create Event</a></li>");
		out.println("<li><a href='/EventGuestServlet'>Join Event</a></li>");
		out.println("<li><a href='#'></a></li>");
		out.println("<li><a href='/UserInformationServlet'>Edit Profile</a></li>");
		out.println("<li><a href='/LogOutServlet'>Log Out</a></li>");
		out.println("</ul>");
		out.println("</div>");
		out.println("<div id='content'>");
		out.println("<div id='left'>");
		out.println("<div class='post'>");
		out.println("<h1>Profile Information</h1>");
		out.println("");
		out.println("<p align='left'>&#160;</p>");
		out.println("");
		out.println("<form action='' method='GET' id='UserInformationForm' name='UserInformationForm'>");
		out.println("");
		out.println("<!-- START DYNAMIC HTML -->");
	
		try {
		
			// Go over the Rows
			while (AccountSQLQueryOutput.next()) {
				
			// Go over the Columns
			// NOTE : i = 2 because we want to skip the first item (Account_ID)
			for(int i = 2 ; i < SQLQueryOutputMetaData.getColumnCount() + 1 ; i++){
				
				String ColumnName = SQLQueryOutputMetaData.getColumnName(i);
				String DisplayCoumnName = ColumnName.replaceAll("_", " ");
				String CurrentValue = AccountSQLQueryOutput.getString(i);
				
				// If the current value is empty set it as N/A
				//if(CurrentValue.equals("")){CurrentValue = "N/A";};
				
				out.println("<p> "+DisplayCoumnName+": </p> <input type='text' id='"+ColumnName+"' name='"+ColumnName+"' title='"+DisplayCoumnName+"' size='32' value='"+CurrentValue+"' />");
				
				
			}
			
			}
		} catch (SQLException e1) {e1.printStackTrace();}
		
		
		out.println("<!-- END DYNAMIC HTML -->");
		out.println("");
		out.println("<br />");
		out.println("<br />");
		out.println("<br />");
		out.println(" ");
		out.println("<input type='submit' id='SaveInformationButton' name='SaveInformationButton' value = 'Save Information' />");
		out.println(" ");
		out.println("</form>");
		out.println("");
		out.println("</div>");
		out.println("");
		out.println("</div>");
		out.println("");
		out.println("");
		out.println("<div class='clear'></div>");
		out.println("</div>");
		out.println("</div>");
		out.println("");
		out.println("<div id='footer'>");
		out.println("<ul>");
		out.println("<li><a href='#'></a></li>");
		out.println("<li><a href='#'></a></li>");
		out.println("<li><a href='#'></a></li>");
		out.println("<li><a href='#'></a></li>");
		out.println("<li><a href='#'></a></li>");
		out.println("<li><a href='#'></a></li>");
		out.println("</ul>");
		out.println("<span>Copyright © 2012</span>");
		out.println("</div>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");


/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ */
/* END HTML RESPONSE */
		
		// Close the ResultSet
		try {AccountSQLQueryOutput.close();} catch (SQLException e) {e.printStackTrace();}
			
		// Close the stream to complete the page
		out.close();
		
	}else {
			// Write the HTML Error Response	
			String ErrorMessage = "Unable to retrieve profile! Please Try again.";
			writeHTMLErrorResponse(Request, Response, out, ErrorMessage);
			
			// Close the ResultSet
			try {AccountSQLQueryOutput.close();} catch (SQLException e) {e.printStackTrace();}
		}
		
		} // end try
		// if database exception occurs, return error page
		catch (SQLException sqlException) {
			sqlException.printStackTrace();		
			
			// Respond with an error message
			String ErrorMessage = "Database error occurred. Try again later.";
			writeHTMLErrorResponse(Request, Response, out, ErrorMessage);
			
		} // end catch
		
	} // END IF STATEMENT
	
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
		out.println("<!-- START DYNAMIC HTML -->");
		out.println("<p>"+ErrorMessage+"</p>");
		out.println("<!-- END DYNAMIC HTML -->");
		out.println("</body>");
		out.println("</html>");

/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ */
/* END HTML RESPONSE */
		
		// Close the stream to complete the page
		out.close();
		
	}
	
	// Returns an HTML Notification Message Response
	private void writeHTMLNotificationResponse(HttpServletRequest Request, HttpServletResponse Response, PrintWriter out, String NotificationMessage){
		
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
		out.println("<!-- START DYNAMIC HTML -->");
		out.println("alert('"+NotificationMessage+"');");
		out.println("<!-- END DYNAMIC HTML -->");
		out.println("</script>");
		out.println("<meta http-equiv='REFRESH' content='0;url=/UserInformationServlet'/>");
		out.println("</body>");
		out.println("</html>");

/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ */
/* END HTML RESPONSE */
		
		// Close the stream to complete the page
		out.close();
		
	}
	
}
