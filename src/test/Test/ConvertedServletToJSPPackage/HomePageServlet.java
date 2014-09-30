/* 
GOAL: Act as the main Homepage. Also allows for account Login and Registration.

PROPERTIES: Front-End Work
1. User Page Display (HTML/CSS)
2. User Page Interactions (HTML/JavaScript)
*/

package test.Test.ConvertedServletToJSPPackage;

import java.io.PrintWriter;
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")
public class HomePageServlet extends HttpServlet {
	
	// Init Method
	public void init(ServletConfig config) throws ServletException {
		
		System.out.println("");
		System.out.println("");
		System.out.println("********************* RLF START *********************");
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("");
	}

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
			
		// Create a new Session even if it Exists
		CurrentSession =  Request.getSession(true);
		
		// Set the time in seconds that a session should be saved before timing out.
		// A negative value specifies that the session should never time out.
		CurrentSession.setMaxInactiveInterval(900); // 900 Seconds = 15 Minutes
		
		}
		
		// set up response to client
		Response.setContentType("text/html");
		PrintWriter out = Response.getWriter();

		/* START Servlet Response */
/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ */

		try {
			
			// Retrieve the SessionAccountID
			String SessionAccountID = (String) CurrentSession.getAttribute("AccountID");
	
			// If the user is logged in, Redirect accordingly
			if(SessionAccountID != null){
				
				Response.sendRedirect("/UserProfileServlet");
				
			}
			
			// Write the HTML Successful Response
			writeHTMLSuccessResponse(Request, Response, out);
		
			
/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ */
		/* END Servlet Response */
			
		} // end try
		// if am exception occurs, return error page
		catch (Exception EX) {
			EX.printStackTrace();
		} // end catch
	
	}
	
	// Returns a Successful HTML Response
	private void writeHTMLSuccessResponse(HttpServletRequest Request, HttpServletResponse Response, PrintWriter out){

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
		out.println("<title>RLF - Homepage</title>");
		out.println("");
		out.println("<!-- Set the Favicon for the Website page -->");
		out.println("<link rel='Shortcut Icon' type='image/ico' href='/Images/favicon.ico'/>");
		out.println("");
		out.println("<!-- Set the Character Encoding for the Website page -->");
		out.println("<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1' />");
		out.println("");
		out.println("<!-- Include the Stylesheet Files -->");
		out.println("<link rel='stylesheet' type='text/css' href='/CSS/RLFStyle.css' />");
		out.println("");
		out.println("<!-- Include the JavaScript Files -->");
		out.println("<script language='javascript' type='text/javascript' src='/JavaScript/Validation/LoginPageValidation.js' > </script>");
		out.println("<script language='javascript' type='text/javascript' src='/JavaScript/Validation/Registration Page Validation.js' > </script>");
		out.println("<script language='javascript' type='text/javascript' src='/JavaScript/Drop-Down Menu Population.js' > </script>");
		out.println("");
		out.println("</head>");
		out.println("");
		out.println("<body>");
		out.println("<div id='container'>");
		out.println("<div id='main'>");
		out.println("<div id='header'></div>");
		out.println("<div id='nav'>");
		out.println("<ul>");
		out.println("<li><a href='#'></a></li>");
		out.println("<li><a href='#'></a></li>");
		out.println("<li><a href='#'></a></li>");
		out.println("<li><a href='#'></a></li>");
		out.println("<li><a href='#'></a></li>");
		out.println("<li><a href='#'></a></li>");
		out.println("</ul>");
		out.println("</div>");
		out.println("<div id='content'>");
		out.println("<div id='left'>");
		out.println("<div class='post'>");
		out.println("<h1>Registration</h1>");
		out.println("");
		out.println("<p align='left'>&#160;</p>");
		out.println("");
		out.println("");
		out.println(" <!-- Create the Registration Form  -->");
		out.println("<form action='/RegistrationServlet' method='GET' id='RegistrationForm' name='RegistrationForm' onsubmit='return validateRegistrationFormInput()'>");
		out.println(" ");
		out.println("<!-- Username VARCHAR(32) NOT NULL -->");
		out.println("<p> Username: </p> <input type='text' id='RegistrationUsername' name='RegistrationUsername' title='Username' size='32' />");
		out.println("<br />");
		out.println(" ");
		out.println("<!-- Password VARCHAR(32) -->");
		out.println("<p> Password: </p> <input type='password' id='RegistrationPassword' name='RegistrationPassword' title='Password' size='32' />");
		out.println("<br />");
		out.println(" ");
		out.println("<!-- EMail VARCHAR(64) -->");
		out.println("<p> E-Mail: </p> <input type='text' id='RegistrationEMail' name='RegistrationEMail' title='E-Mail' size='48' />");
		out.println("<br />");
		out.println(" ");
		out.println("<p> Confirm E-Mail: </p> <input type='text' id='RegistrationEMailConfirmation' name='RegistrationEMailConfirmation' title='Confirm E-Mail' size='48' />");
		out.println("<br />");
		out.println("<br />");
		out.println(" ");
		out.println("<!-- Gender VARCHAR(32) -->");
		out.println("<!-- Insert mutually exclusive Radio Buttons -->");
		out.println("<p> Gender: </p>");
		out.println("<input type='radio' name='RegistrationGender' value='Female' />Female<br />");
		out.println("<input type='radio' name='RegistrationGender' value='Male' />Male<br />");
		out.println(" ");
		out.println("<br />");
		out.println(" ");
		out.println("<!-- Date_Of_Birth DATE -->");
		out.println("<p> Date of Birth: </p>");
		out.println(" ");
		out.println("                <!-- Create the RegistrationBirthDay, RegistrationBirthMonth, and RegistrationBirthYear Drop-Down Menu -->");
		out.println("                <SELECT id ='RegistrationBirthDay' name = 'RegistrationBirthDay'></SELECT> <b id='RegistrationBirthDivisor'>/</b>");
		out.println("                <SELECT id ='RegistrationBirthMonth' name = 'RegistrationBirthMonth'></SELECT> <b id='RegistrationBirthDivisor'>/</b>");
		out.println("                <SELECT id ='RegistrationBirthYear' name = 'RegistrationBirthYear'></SELECT>");
		out.println(" ");
		out.println("                <!-- Call the (populateDateMenu) to populate the RegistrationBirthDay, RegistrationBirthMonth, and RegistrationBirthYear Drop-Down Menu-->");
		out.println("                <script type='text/javascript'>populateDateMenu('RegistrationBirthDay', 'RegistrationBirthMonth', 'RegistrationBirthYear');</script> ");
		out.println(" ");
		out.println("<br />");
		out.println("<br />");
		out.println("<br />");
		out.println(" ");
		out.println("<input type='submit' id='RegistrationButton' name='RegistrationButton' value = 'Register' />");
		out.println(" ");
		out.println("</form>");
		out.println(" ");
		out.println("");
		out.println("</div>");
		out.println("");
		out.println("</div>");
		out.println("");
		out.println("<div id='right'>");
		out.println("");
		out.println("<h1>Login</h1>");
		out.println("");
		out.println("<p align='left'>&#160;</p>");
		out.println("");
		out.println("<!-- Create the Login Form  -->");
		out.println("<form action='/LoginServlet' method='GET' id='LoginForm' name='LoginForm' onsubmit='return validateLoginFormInput()'>");
		out.println(" ");
		out.println("<!-- Retrieve the Username -->");
		out.println("<p> Username: </p> <input type='text' id='LoginUsername' name='LoginUsername' size='32' />");
		out.println("<script type='text/javascript'>");
		out.println("// Set the initial focus on the LoginUsername Element");
		out.println("document.getElementById('LoginUsername').focus();");
		out.println("</script>");
		out.println(" ");
		out.println("<br />");
		out.println(" ");
		out.println("<!-- Retrieve the Password -->");
		out.println("<p> Password: </p> <input type='password' id='LoginPassword' name='LoginPassword' size='32' />");
		out.println(" ");
		out.println("<br />");
		out.println(" ");
		out.println("<!-- Retrieve the Remember Me Choice -->");
		out.println("<input type='checkbox' id='LoginRememberMe' name='LoginRememberMe' /> <b> Keep me logged in: </b>");
		out.println("");
		out.println("<!-- Redirects the user to the Account Retrieval Page -->");
		out.println("<a href='#' style='margin-left:5px;'>Can't access your account ?</a>");
		out.println(" ");
		out.println("<br />");
		out.println("<br />");
		out.println(" ");
		out.println(" ");
		out.println("<input type='submit' id='LoginButton' name='LoginButton' value = 'Login' />");
		out.println(" ");
		out.println("</form>");
		out.println("");
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
	}
	

	// Test if Cookies are enabled and redirect accordingly
	public void Test4Cookies(HttpServletRequest Request, HttpServletResponse Response) {

		// Create the Variables needed to Test if Cookies are Enabled or Disabled
		final Cookie DummyCookie = new Cookie("DummyCookie", "DummyCookie");
		final String DummyCookieParameter = "DummyCookieParameter";
		final String DummyCookieValue = "DummyCookieValue";
		final String DisabledCookiesURL = "https://login.live.com/cookiesDisabled.srf";

		// Check If we don't have the TestCookieValue (TestCookieParameter == null) in the query string.
		if (Request.getParameter(DummyCookieParameter) == null) {

			// Add the DummyCookie to the Response
			Response.addCookie(DummyCookie);
			
			try {
			
			// Redirect the Response to this page along with the TestCookieParameter and TestCookieValue in the query string.
			Response.sendRedirect(Request.getRequestURI() + "?" + DummyCookieParameter + "=" + DummyCookieValue);
			
			}catch (IOException e) {e.printStackTrace();}

		} else {
			
			try {
			
			// Since we have the TestCookieValue (TestCookieParameter != null) in the query string, check whether we have any cookies.
			if (Request.getCookies() == null) {

				// Cookies Disabled; Cookie was NOT Received (Request.getCookies() == null).
				Response.sendRedirect(DisabledCookiesURL);

			}

			}catch (IOException e) {e.printStackTrace();}
		}

	}
	
}
