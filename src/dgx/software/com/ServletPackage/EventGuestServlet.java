/* 
GOAL: Logs into the Database to retrieve the events. It then displays them to the user.

PROPERTIES: Front-End Work / Back-End Work
1. User Page Display (HTML/CSS)
2. User Page Interactions (HTML/JavaScript)
3. User SQL Database Access (SQL)
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")
public class EventGuestServlet extends HttpServlet {
	
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
		
		// attempt to process a vote and display current results
		try {
		
		// Variables for Account session information
		//String SessionAccountID = (String) CurrentSession.getAttribute("AccountID");
		String SessionUsername = (String) CurrentSession.getAttribute("Username");
			
		// SQL Query
		String EventGuestSQLQuery = "SELECT * FROM RLFDB_Host_Events;";
		
		// Get the SQLQueryOutput
		ResultSet EventGuestQueryOutput = statement.executeQuery(EventGuestSQLQuery);
		
		// If we DO NOT Have an Empty Result Set, Work with it and send a successful HTML Response
		if(EventGuestQueryOutput.next()){

		// Reset the Pointer changed by the if statement above
		EventGuestQueryOutput.beforeFirst();
		
/*		
		// Records the ResultSetMetaData
		ResultSetMetaData SQLQueryOutputMetaData = null;
		
		try {
			
			// Get the ResultSetMetaData
		    SQLQueryOutputMetaData = EventHostQueryOutput.getMetaData();
			
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();		
			
			// Respond with an error message
			String ErrorMessage = "Database error occurred. Try again later.";
			writeHTMLErrorResponse(Request, Response, out, ErrorMessage);
		}
*/		
		
		
// START: GATHER HOST EVENTS
		
		// Declare the EventNode ArrayList
	    ArrayList <EventNode> EventNodeList = null;
		
		try {
			
		    EventNodeList = new ArrayList <EventNode>();
		    
		    // Save the EventGuestQueryOutput results into an EventNode ArrayList
			while (EventGuestQueryOutput.next()) {
				
				//TO DO : CALCULATE THE DISTANCE TO THIS CURRENT EVENT HERE
				float MileDistanceFromOrigin = (float)Math.random()*100;
				
				EventNodeList.add(new EventNode(
						EventGuestQueryOutput.getInt("Event_ID"),
						EventGuestQueryOutput.getInt("Account_ID"),
						EventGuestQueryOutput.getString("Host_Username"),
						EventGuestQueryOutput.getString("Event_Name"),
						EventGuestQueryOutput.getString("Event_Category"),
						EventGuestQueryOutput.getString("Event_Sub_Category"),
						EventGuestQueryOutput.getString("Event_Description"),
						EventGuestQueryOutput.getString("Event_Address"),
						EventGuestQueryOutput.getString("Event_Country"),
						EventGuestQueryOutput.getString("Event_Start_Date_And_Time"),
						EventGuestQueryOutput.getString("Event_End_Date_And_Time"),
						EventGuestQueryOutput.getString("Event_TimeZone"),
						EventGuestQueryOutput.getString("Date_Submitted"),
						EventGuestQueryOutput.getString("Time_Submitted"),
						MileDistanceFromOrigin));
			}
			
			
			// SORT THE EVENT BY DISTANCE HERE
			Collections.sort(EventNodeList, new CustomComparator());

			} catch (SQLException EX) {EX.printStackTrace();}
		
		
		

// END: GATHER HOST EVENTS

		
		
		
		
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
		out.println("<title>Event Guest Servlet</title>");
		out.println("");
		out.println("<!-- Set the Favicon for the Website page -->");
		out.println("<link rel='Shortcut Icon' type='image/ico' href='/Images/favicon.ico'/>");
		out.println("");
		out.println("<!-- Set the Character Encoding for the Website page -->");
		out.println("<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1' />");
		out.println("");
		out.println("<!-- Include the Stylesheet Files -->");
		out.println("<link rel='stylesheet' type='text/css' href='/CSS/ENM Style.css' />");
		out.println("<link rel='stylesheet' type='text/css' href='/CSS/Event Results/Event Results.css'/>");
		out.println("");
		out.println("<!-- Include the JavaScript Files -->");
		out.println("<script language='javascript' type='text/javascript' src='/JavaScript/Validation/Event Join Page Validation.js' > </script>");
		out.println("<script language='javascript' type='text/javascript' src='/JavaScript/Drop-Down Menu Population.js'></script>");
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
		out.println("<h1>Join Events</h1>");
		out.println("");
		out.println("<p align='left'>&#160;</p>");
		out.println("");
		out.println("");
		out.println("");
		out.println("");
		out.println("");
		out.println("");
		out.println("");
		out.println("");
		out.println("");
		out.println("");
		out.println("");
		out.println("");
		out.println("<!-- START the Event Results Form  -->");
		out.println("<div class='EventResultsBody'>");
		out.println("<div class='EventResultsBodyContainer'>");
		out.println("");
		out.println("");
		out.println("<!-- START SEARCH BAR HERE -->");
		out.println("<div class='EventSearchSection'>");
		out.println("");
		out.println("<!-- TABS -->");
		out.println("<center>");
		out.println("<div class='EventSearchTabBar'>");
		out.println("<div class='EventSearchSelectedTab'><center><span class='EventSearchTabTitle'>Basic Search</span></center></div>");
		out.println("<div class='EventSearchAvailableTab'><center><span class='EventSearchTabTitle'><a class='EventSearchAvailableTabLink' href='' >Advanced Search</a></span></center></div>");
		out.println("</div>");
		out.println("</center>");
		out.println("");
		out.println("<!-- START ADVANCED SEARCH ROW -->");
		out.println("<!-- START DYNAMIC HTML -->");
		out.println("<div class='EventSearchRow'>");
		out.println("<center>");
		out.println("<form action='/EventGuestServlet' method='GET' id='EventJoinForm' name='EventJoinForm' onsubmit='return validateEventJoinFormInput()'>");
		out.println("<table class='EventSearchSectionTable'>");
		out.println("<tr>");
		out.println("");
		out.println("<td class='EventSearchSectionTableData' valign='middle' width='60'><span class='EventSearchPromptText'>1.</span>");
		out.println("<td class='EventSearchSectionTableData'><input class='EventSearchSectionTitle' type='text' name='EventSearchOrganizer' id='EventSearchOrganizer' value='' size='8' maxlength='40' onchange='resetZipcode();'></td>");
		out.println("<td class='EventSearchSectionTableData' valign='middle' width='60'><span class='EventSearchPromptText'>2.</span>");
		out.println("<td class='EventSearchSectionTableData'><input class='EventSearchSectionTitle' type='text' name='EventSearchEventID' id='EventSearchEventID' value='' size='8' maxlength='40' onchange='resetZipcode();'></td>");
		out.println("<td class='EventSearchSectionTableData' valign='middle' width='60'><span class='EventSearchPromptText'>3.</span>");
		out.println("<td class='EventSearchSectionTableData'><input class='EventSearchSectionTitle' type='text' name='EventSearchEventName' id='EventSearchEventName' value='' size='8' maxlength='40' onchange='resetZipcode();'></td>");
		out.println("<td class='EventSearchSectionTableData' valign='middle' width='60'><span class='EventSearchPromptText'>4.</span>");
		out.println("<td class='EventSearchSectionTableData'><input class='EventSearchSectionTitle' type='text' name='EventSearchEventDescription' id='EventSearchEventDescription' value='' size='8' maxlength='40' onchange='resetZipcode();'></td>");
		out.println("<td class='EventSearchSectionTableData' valign='middle' width='60'><span class='EventSearchPromptText'>5.</span>");
		out.println("<td class='EventSearchSectionTableData'><input class='EventSearchSectionTitle' type='text' name='EventSearchEventStartDateAndTime' id='EventSearchEventStartDateAndTime' value='' size='8' maxlength='40' onchange='resetZipcode();'></td>");
		out.println("<td class='EventSearchSectionTableData' valign='middle' width='60'><span class='EventSearchPromptText'>6.</span>");
		out.println("<td class='EventSearchSectionTableData'><input class='EventSearchSectionTitle' type='text' name='EventSearchEventEndDateAndTime' id='EventSearchEventEndDateAndTime' value='' size='8' maxlength='40' onchange='resetZipcode();'></td>");
		out.println("");
		out.println("</tr>");
		out.println("</table>");
		out.println("</center>");
		out.println("</div>");
		out.println("<!-- END DYNAMIC HTML -->");
		out.println("<!-- END ADVANCED SEARCH ROW -->");
		out.println("");
		out.println("");
		out.println("<!-- START BASIC SEARCH ROW -->");
		out.println("");
		out.println("<div class='EventSearchRow'><center>");
		out.println("<table class='EventSearchSectionTable'>");
		out.println("<tr>");
		out.println("");
		out.println("<!-- Create and Populate the EventSearchCategoryMenu Drop-Down Menu -->");
		out.println("<td class='EventSearchSectionTableData'>");
		out.println("<select class='EventSearchSectionCategoryDropDownMenu' id='EventSearchCategoryMenu' NAME='EventSearchCategoryMenu' size='1'></select>");
		out.println("<script type='text/javascript'>populateEventCategoryMenu('EventSearchCategoryMenu');</script> ");
		out.println("</td>");
		out.println("");
		out.println("<!-- Create and Populate the EventSearchSubCategoryMenu Drop-Down Menu -->");
		out.println("<td class='EventSearchSectionTableData'>");
		out.println("<select class='EventSearchSectionSubCategoryDropDownMenu' id='EventSearchSubCategoryMenu' NAME='EventSearchSubCategoryMenu' size='1'></select>");
		out.println("<script type='text/javascript'>populateEventSubCategoryMenu('EventSearchSubCategoryMenu');</script> ");
		out.println("</td>");
		out.println("");
		out.println("<!-- Create and Populate the EventSearchCountryMenu Drop-Down Menu -->");
		out.println("<td class='EventSearchSectionTableData' valign='middle'>");
		out.println("<select class='EventSearchSectionCountryDropDownMenu' id='EventSearchCountryMenu' name='EventSearchCountryMenu' onchange='getstates(this);resetZipcode();resetCity()' size='1' cols='15'></select>");
		out.println("<script type='text/javascript'>populateCountryMenu('EventSearchCountryMenu');</script> ");
		out.println("</td>");
		out.println("");
		out.println("<!-- Create the EventSearchAddress -->");
		out.println("<td class='EventSearchSectionTableData'><center><span class='EventSearchPromptText'>City&nbsp;/&nbsp;Zip Code&nbsp;</span></center></td>");
		out.println("<td class='EventSearchSectionTableData'><input class='EventSearchSectionTitle' type='text' name='EventSearchAddress' id='EventSearchAddress' value='' size='8' maxlength='40' onchange='resetZipcode();'></td>");
		out.println("");
		out.println("");
		out.println("<!-- Create and Populate the EventSearchMilesMenu Drop-Down Menu -->");
		out.println("<td class='EventSearchSectionTableData'>");
		out.println("<select class='EventSearchSectionMilesDropDownMenu' id='EventSearchMilesMenu' name='EventSearchMilesMenu'></select>");
		out.println("<script type='text/javascript'>populateEventMilesMenu('EventSearchMilesMenu',5);</script> ");
		out.println("</td>");
		out.println("");
		out.println("");
		out.println("<!-- Create the EventSearchSortMenu -->");
		out.println("<td class='EventSearchSectionTableData'><span class='EventSearchPromptText'>Sort</span></td>");
		out.println("<td class='EventSearchSectionTableData'>");
		out.println("<select class='EventSearchSectionSortDropDownMenu' id='EventSearchSortMenu' name='EventSearchSortMenu'>");
		out.println("<option value=0 SELECTED>Newest Events</option>");
		out.println("<option value=1 >Oldest Events</option>");
		out.println("</select>");
		out.println("</td>");
		out.println("");
		out.println("<!-- BUTTON -->");
		out.println("<td class='EventSearchSectionTableData' align='center' valign='middle' width='6%'>");
		out.println("<input type='submit' class='EventSearchSubmitButton' name='cmdSearch' value='Search'>  ");
		out.println("</td>");
		out.println("");
		out.println("");
		out.println("</tr>");
		out.println("");
		out.println("");
		out.println("<tr>");
		out.println("<td class='EventSearchSectionTableData' colspan='12'>");
		out.println("<center>");
		out.println("<!-- START ERROR MESSAGE -->");
		out.println("<!-- START DYNAMIC HTML -->");
		out.println("<!--");
		out.println("<span class='EventSearchErrorText'><strong>You can only filter images if you enter a valid city and postal or zip code.</strong></span>");
		out.println("-->");
		out.println("<!-- START DYNAMIC HTML -->");
		out.println("<!-- END ERROR MESSAGE -->");
		out.println("</center>");
		out.println("</td>");
		out.println("</tr>");
		out.println("");
		out.println("");
		out.println("</table> ");
		out.println("</form>");
		out.println("</center>");
		out.println("</div>");
		out.println("");
		out.println("<!-- END BASIC SEARCH ROW -->");
		out.println(" ");
		out.println(" ");
		out.println("<center></span><br /></center>");
		out.println("");
		out.println("</div>");
		out.println("<!-- END SEARCH BAR HERE -->");
		out.println("");
		out.println("");
		out.println("<!-- START Search overview -->");
		out.println("<div class='EventResultsSection'>");
		out.println("<span class='EventResultsNotice'>SEARCH RESULTS</span>");
		
		out.println("<!-- START DYNAMIC HTML -->");
		
		// Event Display variables for calculations
		int CurrentEventPageToDisplay = 1;
		int MaxEventsDisplayedPerPage = 10;

		// Retrieve the Desired Event Page To Display from the Query String
		if(Request.getParameter("EventPageToDisplay") != null){
			CurrentEventPageToDisplay =Integer.parseInt(Request.getParameter("EventPageToDisplay"));
			
		}
			
		// Calculate the Start and End Index for the Current Page Event Display
		int EventDisplayStartIndex = (CurrentEventPageToDisplay - 1) * MaxEventsDisplayedPerPage;
		int EventDisplayEndIndex = CurrentEventPageToDisplay * MaxEventsDisplayedPerPage;
		
		// Don't allow the EventDisplayEndIndex to exceed the max number of Events
		if(EventDisplayEndIndex > EventNodeList.size()) EventDisplayEndIndex = EventNodeList.size();
		
		// Print the Result Overview
		out.println("<strong class='EventResultsNoticeFooter'> - Results "+(EventDisplayStartIndex + 1)+" to "+EventDisplayEndIndex+" out of "+EventNodeList.size()+" results are shown below.</strong>");

		out.println("<!-- END DYNAMIC HTML -->");
		
		out.println("<!-- END Search overview -->");
		out.println("");
		out.println("<!-- START USER LIST HERE -->");
		out.println("<!-- START DYNAMIC HTML -->");
		
		// Generate all the events for this Page
		for(int i = EventDisplayStartIndex ; i < EventDisplayEndIndex; i++){
			
			// Display the current Event Card
			out.println("<!-- Event 1 -->");
			out.println("<div class='EventCard'>");
			out.println("<div class='EventCardContainer'>");
			out.println("<div class='EventCardImageArea'>");
			out.println("<a href='#' class=''>");
			
			// Place the Event Main Image
			//out.println("<img class='EventCardImageSize' src='http://www.kidsmathgamesonline.com/images/pictures/numbers120/number10.jpg' border='0' alt=''/>");
			out.println("<img class='EventCardImageSize' src='http://www.ridgewells.com/wp-content/themes/ridgewells/images/recognitions/2.jpg' border='0' alt=''/>");
			
			out.println("</a>");
			out.println("</div>");
			out.println("<div class='EventCardDescriptionArea'>");
			out.println("<div class='EventCardHeadlineArea'>");
			out.println("<span class='EventCardHeadlineAreaTitle'>"+ EventNodeList.get(i).Event_Name +"</span>");
			out.println("<span class='EventCardHeadlineAreaFooter'>&nbsp;&nbsp;&nbsp;&nbsp;"+ EventNodeList.get(i).Event_Address +"</span>");
			out.println("</div>");
			out.println("<span class='EventCardDescriptionAreaText'>"+ EventNodeList.get(i).Event_Description +" (Limit to 200 Characters)</span>");
			out.println("<br/>");
			out.println("<div class='EventCardAboutArea'>");
			out.println("<a class='EventUsernameLink' href='#'>"+ EventNodeList.get(i).Host_Username +"</a>");
			out.println("<span class='EventUsernameLinkFooter'>&nbsp;"+ EventNodeList.get(i).Event_Start_Date_And_Time +"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+ EventNodeList.get(i).Event_End_Date_And_Time +"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+ EventNodeList.get(i).Event_TimeZone +"&nbsp;&nbsp;&nbsp;</span>");
			out.println("<a class='EventJoinLink' href='#' onclick=''>1. Join this event.</a>");
			out.println("</div></div></div></div>");
			out.println("");
			
		}
		
		out.println("<!-- END DYNAMIC HTML -->");
		out.println("<!-- END USER LIST HERE -->");
		out.println("");
		out.println("<!-- START PAGE BROWSING HERE -->");
		out.println("<center>");
		out.println("<!-- START DYNAMIC HTML -->");
		
		// Record the Max and Minimum Display Page Possible
		int MinimumDisplayPagePossible = 1;
		int MaximumDisplayPagePossible = EventNodeList.size() / MaxEventsDisplayedPerPage;
		if(EventNodeList.size() % MaxEventsDisplayedPerPage > 0) MaximumDisplayPagePossible += 1;
		
		// Print the First Page Button
		if(CurrentEventPageToDisplay <= MinimumDisplayPagePossible){
		out.println("<a class='EventDisabledPageLink'>First</a>");
		}else{
		out.println("<a class='EventEnabledPageLink' href='/EventGuestServlet?EventPageToDisplay="+MinimumDisplayPagePossible+"'>First</a>");
		}
		
		// Print the Previous Page Button
		if(CurrentEventPageToDisplay <= MinimumDisplayPagePossible){
		out.println("<a class='EventDisabledPageLink'>Previous</a>");
		}else{
		out.println("<a class='EventEnabledPageLink' href='/EventGuestServlet?EventPageToDisplay="+(CurrentEventPageToDisplay - 1)+"'>Previous</a>");
		}

		// Declare the Max Browse Pages
		int MaxBrowsePages = 20;
		
		// Declare and Calculate the values for the Numbered page buttons
		int PageBrowsingStartIndex = CurrentEventPageToDisplay - (MaxBrowsePages / 2);
		int PageBrowsingEndIndex = CurrentEventPageToDisplay + (MaxBrowsePages / 2);
		if (PageBrowsingStartIndex <= 0){ PageBrowsingStartIndex = 1;
		PageBrowsingEndIndex += (MaxBrowsePages / 2) - CurrentEventPageToDisplay;
		}
		// Don't allow the PageBrowsingEndIndex to exceed the MaximumDisplayPagePossible
		if(PageBrowsingEndIndex > MaximumDisplayPagePossible) PageBrowsingEndIndex = MaximumDisplayPagePossible;

		
		// Print the Numbered page buttons
		for(int i = PageBrowsingStartIndex; i < PageBrowsingEndIndex + 1; i++ ){

		if(CurrentEventPageToDisplay != i){
			//out.println("<a class='EventEnabledPageLink' href='#'>"+i+"</a>");
			out.println("<a class='EventEnabledPageLink' href='/EventGuestServlet?EventPageToDisplay="+i+"'>"+i+"</a>");	
		}else{
			out.println("<a class='EventDisabledPageLink'>"+i+"</a>");
			}
		}
		
		// Print the Next Page Button
		//if(EventDisplayEndIndex >= EventNodeList.size()){
		if(CurrentEventPageToDisplay >= MaximumDisplayPagePossible){
		out.println("<a class='EventDisabledPageLink'>Next</a>");
		}else{
		out.println("<a class='EventEnabledPageLink' href='/EventGuestServlet?EventPageToDisplay="+(CurrentEventPageToDisplay + 1)+"'>Next</a>");
		}
		
		// Print the Last Page Button
		//if(EventDisplayEndIndex >= EventNodeList.size()){
		if(CurrentEventPageToDisplay >= MaximumDisplayPagePossible){
		out.println("<a class='EventDisabledPageLink'>Last</a>");
		}else{
		out.println("<a class='EventEnabledPageLink' href='/EventGuestServlet?EventPageToDisplay="+MaximumDisplayPagePossible+"'>Last</a>");
		}
		
		out.println("<!-- END DYNAMIC HTML -->");
		out.println("</center>");
		out.println("<!-- END PAGE BROWSING HERE -->");
		out.println("");
		out.println("</div>");
		out.println("<br />");
		out.println("");
		out.println("<!-- END PAGE BROWSING HERE -->");
		out.println("");
		out.println("");
		out.println("");
		out.println("</div>");
		out.println("</div>");
		out.println("<!-- END the Event Results Form  -->");
		out.println("");
		out.println("");
		out.println("");
		out.println("");
		out.println("");
		out.println("");
		out.println("");
		out.println("");
		out.println("");
		out.println("");
		out.println("");
		out.println("");
		out.println("");
		out.println("");
		out.println("");
		out.println("</div>");
		out.println("</div>");
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
		try {EventGuestQueryOutput.close();} catch (SQLException e) {e.printStackTrace();}
					
		// Close the stream to complete the page
		out.close();
				
		}else {
		// Write the HTML Error Response	
		String ErrorMessage = "Unable to retrieve Event! Please Try again.";
		writeHTMLErrorResponse(Request, Response, out, ErrorMessage);
					
		// Close the ResultSet
		try {EventGuestQueryOutput.close();} catch (SQLException e) {e.printStackTrace();}
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





// Class to represent the Events
class EventNode{
	
	//Table RLFDB_Guest_Events
	int Event_ID = -1;
	int Account_ID = -1;
	String Host_Username = "";
	String Event_Name = "";
	String Event_Category = "";
	String Event_Sub_Category = "";
	String Event_Description = "";
	String Event_Address = "";
	String Event_Country = "";
	
	String Event_Start_Date_And_Time = "";
	String Event_End_Date_And_Time = "";
	String Event_TimeZone = "";
	String Date_Submitted = "";
	String Time_Submitted = "";
	
	Float MileDistanceFromOrigin = -1f;

	
	public EventNode(int Event_ID,int Account_ID,String Host_Username,String Event_Name,
			String Event_Category,String Event_Sub_Category,
			String Event_Description,String Event_Address,String Event_Country,
			String Event_Start_Date_And_Time,String Event_End_Date_And_Time,
			String Event_TimeZone, String Date_Submitted, String Time_Submitted,
			Float MileDistanceFromOrigin) {
		
		
		this.Event_ID = Event_ID;
		this.Account_ID = Account_ID;
		this.Host_Username = Host_Username;
		this.Event_Name = Event_Name;
		this.Event_Category = Event_Category;
		this.Event_Sub_Category = Event_Sub_Category;
		this.Event_Description = Event_Description;
		this.Event_Address = Event_Address;
		this.Event_Country = Event_Country;
		
		// Convert the Event time into the format used by MySQL
		String DateCurrentFormat = "yyyy-MM-dd HH:mm:ss";
		String DateDesiredFormat = "MM/dd/yyyy @ hh:mm aa";
		this.Event_Start_Date_And_Time = convertTimeFormat(Event_Start_Date_And_Time,DateCurrentFormat,DateDesiredFormat);
		this.Event_End_Date_And_Time = convertTimeFormat(Event_End_Date_And_Time,DateCurrentFormat,DateDesiredFormat);
		
		this.Event_TimeZone = Event_TimeZone;
		this.Date_Submitted = Date_Submitted;
		this.Time_Submitted = Time_Submitted;
		
		this.MileDistanceFromOrigin = MileDistanceFromOrigin;
		
	}
	
	/*
	Event_ID INT NOT NULL AUTO_INCREMENT,
	Account_ID INT NOT NULL,
	Host_Username VARCHAR(32),
	Event_Name VARCHAR(32),
	Event_Category VARCHAR(32),
	Event_Sub_Category VARCHAR(32),
	Event_Description TEXT,
	Event_Address VARCHAR(128),
	Event_Country VARCHAR(32),
	Event_Start_Date_And_Time DATETIME,
	Event_End_Date_And_Time DATETIME,
	Event_TimeZone VARCHAR(32),
	Date_Submitted DATE,
	Time_Submitted TIME,
	*/
	
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
	
}

//Comparator class to handle ArrayList sorting
class CustomComparator implements Comparator <EventNode> {
	
	// Override: Compare to Sort from Smallest to Largest
	public int compare(EventNode EN1, EventNode EN2) {
		return EN1.MileDistanceFromOrigin.compareTo(EN2.MileDistanceFromOrigin);
	}

}
