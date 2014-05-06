/* 
GOAL: Prompts the user for information needed to create an event. Then forwards the request to the event creation process.

PROPERTIES: Front-End Work
1. User Page Display (HTML/CSS)
2. User Page Interactions (HTML/JavaScript)
*/

package dgx.software.com.ServletPackage;

import java.io.PrintWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")
public class EventHostServlet extends HttpServlet {
	
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
			
			// Write the HTML Successful Response
			writeHTMLSuccessResponse(Request, Response, CurrentSession, out);
			
/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ */
		/* END Servlet Response */
			
		} // end try
		// if database exception occurs, return error page
		catch (Exception EX) {
			EX.printStackTrace();		
			
			// Respond with an error message
			String ErrorMessage = "Error occurred. Try again later.";
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
	private void writeHTMLSuccessResponse(HttpServletRequest Request, HttpServletResponse Response, HttpSession CurrentSession, PrintWriter out){
		
		// START IF STATEMENT
		// Proceed ONLY if the user has a session
		if(CurrentSession != null){
		
		// Variables for Account session information
		//String SessionAccountID = (String) CurrentSession.getAttribute("AccountID");
		String SessionUsername = (String) CurrentSession.getAttribute("Username");
		
/* START HTML RESPONSE */
/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ */

		out.println("<?xml version = '1.0'?>");
		out.println("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'>");
		out.println("");
		out.println("<!-- The xmlns attribute is required in XHTML but it is invalid in HTML. -->");
		out.println("<!-- the namespace 'xmlns=http://www.w3.org/1999/xhtml' is default, and will be added to the <html> tag in XHTML even if you do not include it. -->");
		out.println("<html xmlns='http://www.w3.org/1999/xhtml'>");
		out.println("");
		out.println("<head>");
		out.println("");
		out.println("<!-- Set the Title for the Website Page -->");
		out.println("<title>Event Host Servlet</title>");
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
		out.println("<script language='javascript' type='text/javascript' src='/JavaScript/Validation/Event Create Page Validation.js' > </script>");
		out.println("<script language='javascript' type='text/javascript' src='/JavaScript/Drop-Down Menu Population.js' > </script>");
		out.println("");
		out.println("<!-- Include the Google Maps JavaScript API v3 — Places Library -->");
		out.println("<script type='text/javascript' src='http://maps.googleapis.com/maps/api/js?libraries=places&sensor=true'></script>");
		out.println("");
		out.println("<!-- START; Inclusion of JavaScript And CSS files for the Date And Time Picker-->");
		out.println("<!-- Include the CSS files -->");
		out.println("<link rel='stylesheet' media='all' type='text/css' href='/JavaScript/JQuery/Date and Time Picker/TimeAndDatePickerDisplay.css' />");
		out.println("<link rel='stylesheet' media='all' type='text/css' href='/JavaScript/JQuery/Date and Time Picker/jquery-ui.css' />");
		out.println("<link rel='stylesheet' media='all' type='text/css' href='/JavaScript/JQuery/Date and Time Picker/jquery-ui-timepicker-addon.css' />");
		out.println("<!-- Import EXTERNAL JQuery Library ");
		out.println("<script type='text/javascript' src='http://code.jquery.com/jquery-1.7.2.min.js'></script>");
		out.println("<script type='text/javascript' src='http://code.jquery.com/ui/1.8.21/jquery-ui.min.js'></script>");
		out.println("--><!-- Import INTERNAL JQuery Library -->");
		out.println("<script type='text/javascript' src='/JavaScript/JQuery/Date and Time Picker/jquery-1.7.2.min.js'></script>");
		out.println("<script type='text/javascript' src='/JavaScript/JQuery/Date and Time Picker/jquery-ui.min.js'></script>");
		out.println("<script type='text/javascript' src='/JavaScript/JQuery/Date and Time Picker/jquery-ui-timepicker-addon.js'></script>");
		out.println("<script type='text/javascript' src='/JavaScript/JQuery/Date and Time Picker/jquery-ui-sliderAccess.js'></script>");
		out.println("<!-- END; Inclusion of JavaScript And CSS files for the Date And Time Picker-->");
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
		out.println("<h1>Create Events</h1>");
		out.println("");
		out.println("<p align='left'>&#160;</p>");
		out.println("");
		out.println("");
		out.println("<!-- Create the Event Create Servlet Form  -->");
		out.println("<form action='/EventCreateServlet' method='GET' id='EventCreateForm' name='EventCreateForm' onsubmit='return validateEventCreateFormInput()'>");
		out.println("");
		out.println("<!-- Event_Name VARCHAR(32) NOT NULL -->");
		out.println("<p> Name: </p> <input type='text' id='EventCreateName' name='EventCreateName' title='Event Name' size='32' />");
		out.println("<br />");
		out.println("");
		out.println("<!-- Event_Category VARCHAR(32) -->");
		out.println(" <p> Category: </p>");
		out.println(" ");
		out.println("<!-- Create the EventCreateCategory Drop-Down Menu -->");
		out.println("<SELECT id ='EventCreateCategory' name = 'EventCreateCategory'></SELECT>");
		out.println("");
		out.println("<!-- Call the (populateEventCategoryMenu) method to populate the EventCreateCategory Drop-Down Menu-->");
		out.println("<script type='text/javascript'>populateEventCategoryMenu('EventCreateCategory');</script> ");
		out.println("");
		out.println("<!-- Event_Sub_Category VARCHAR(32) -->");
		out.println(" <p> Sub Category: </p>");
		out.println(" ");
		out.println("<!-- Create the EventCreateSubCategory Drop-Down Menu -->");
		out.println("<SELECT id ='EventCreateSubCategory' name = 'EventCreateSubCategory'></SELECT>");
		out.println("");
		out.println("<!-- Call the (populateEventSubCategoryMenu) method to populate the EventCreateSubCategory Drop-Down Menu-->");
		out.println("<script type='text/javascript'>populateEventSubCategoryMenu('EventCreateSubCategory');</script> ");
		out.println("");
		out.println("<!-- Event_Description TEXT -->");
		out.println("<p> Description: </p> <TEXTAREA id='EventCreateDescription' name='EventCreateDescription' rows='2' cols='50'></TEXTAREA>");
		out.println("<br />");
		out.println("");
		out.println("<!-- Event_Address VARCHAR(32) -->");
		out.println("<p> Address: </p> <input type='text' id='EventCreateAddress' name='EventCreateAddress' title='Event Address' size='64' value='' />");
		out.println("");
		out.println("");
		out.println("<!-- Enable the Address Auto Complete functionality for the Target TextField using the Google Maps JavaScript API v3 — Places Library  -->");
		out.println("<script type='text/javascript'>");
		out.println("var TargetTextField = document.getElementById('EventCreateAddress');");
		out.println("var AutoCompleteType = {types: ['geocode'], componentRestrictions: {country: 'US'}};");
		out.println("autocomplete = new google.maps.places.Autocomplete(TargetTextField, AutoCompleteType);");
		out.println("</script>");
		out.println("");
		out.println("<!-- Event_Country VARCHAR(32) -->");
		out.println("<p> Country: </p>");
		out.println(" ");
		out.println("<!-- Create the EventCreateCountry Drop-Down Menu -->");
		out.println("<SELECT id ='EventCreateCountry' name = 'EventCreateCountry' onchange='updateCountryMapRestriction(&quot;EventCreateCountry&quot;)'></SELECT>");
		out.println("");
		out.println("<!-- Call the (populateCountryMenu) method to populate the EventCreateCountry Drop-Down Menu-->");
		out.println("<script type='text/javascript'>populateCountryMenu('EventCreateCountry');</script> ");
		out.println("<br />");
		out.println("");
		out.println("<!-- Method updateCountryMapRestriction() to Update the 'componentRestrictions' country value for the Address Auto Complete functionality -->");
		out.println("<script type='text/javascript'>");
		out.println("function updateCountryMapRestriction(TargetDropDownMenuField){");
		out.println("var TargetDropDownMenuCurrentValue = document.getElementById(TargetDropDownMenuField).value;");
		out.println("var TargetTextField = document.getElementById('EventCreateAddress');");
		out.println("var AutoCompleteType = {types: ['geocode'], componentRestrictions: {country: TargetDropDownMenuCurrentValue}};");
		out.println("autocomplete = new google.maps.places.Autocomplete(TargetTextField, AutoCompleteType);");
		out.println("}");
		out.println("</script>");
		out.println("");
		out.println("<!-- Event_Start_Date_And_Time DATETIME -->");
		out.println("<p> Event Start: </p> <input type='text' id='EventCreateStartDateAndTime' name='EventCreateStartDateAndTime' title='Event Start Date And Time' size='32' value='' />");
		out.println("<br />");
		out.println("");
		out.println("<!-- Add the Calendar to the EventCreateStartDateAndTime; Enable 'AM/PM' set the Time Format to 'hh:mm TT' and set the separator to '@' -->");
		out.println("<script type='text/javascript'>");
		out.println("	$('#EventCreateStartDateAndTime').datetimepicker({");
		out.println("	dateFormat: 'mm/dd/yy',");
		out.println("	ampm: true,");
		out.println("	timeFormat: 'hh:mm TT',");
		out.println("	separator: ' @ '");
		out.println("});");
		out.println("</script>");
		out.println("");
		out.println("<!-- Event_End_Date_And_Time DATETIME -->");
		out.println("<p> Event End: </p> <input type='text' id='EventCreateEndDateAndTime' name='EventCreateEndDateAndTime' title='Event End Date And Time' size='32' value='' />");
		out.println("<br />");
		out.println("");
		out.println("<!-- Add the Calendar to the EventCreateEndDateAndTime; Enable 'AM/PM' set the Time Format to 'hh:mm TT' and set the separator to '@' -->");
		out.println("<script type='text/javascript'>");
		out.println("	$('#EventCreateEndDateAndTime').datetimepicker({");
		out.println("	dateFormat: 'mm/dd/yy',");
		out.println("	ampm: true,");
		out.println("	timeFormat: 'hh:mm TT',");
		out.println("	separator: ' @ '");
		out.println("});");
		out.println("</script>");
		out.println("");
		out.println("<!-- Event_TimeZone VARCHAR(32) -->");
		out.println(" <p> Event TimeZone: </p>");
		out.println(" ");
		out.println("<!-- Create the EventCreateTimeZone Drop-Down Menu -->");
		out.println("<SELECT id ='EventCreateTimeZone' name = 'EventCreateTimeZone'></SELECT>");
		out.println("");
		out.println("<!-- Call the (populateEventTimezoneMenu) method to populate the EventCreateTimeZone Drop-Down Menu-->");
		out.println("<script type='text/javascript'>populateEventTimezoneMenu('EventCreateTimeZone');</script> ");
		out.println("");
		out.println("");
		out.println("<br />");
		out.println("<br />");
		out.println("<br />");
		out.println("<br />");
		out.println(" ");
		out.println("<input type='submit' id='EventCreateSubmit' name='EventCreateSubmit' value = 'Create Event' />");
		out.println("");
		out.println("");
		out.println("</form>");
		out.println(" ");
		out.println("");
		out.println("</div>");
		out.println("");
		out.println("</div>");
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

		// Close the stream to complete the page
		out.close();
		
	} // END IF STATEMENT
}		
	
	// Returns an Error Message HTML Response
	private void writeHTMLErrorResponse(HttpServletRequest Request, HttpServletResponse Response,PrintWriter out, String ErrorMessage){
		
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
	
} // end class SurveyServlet
