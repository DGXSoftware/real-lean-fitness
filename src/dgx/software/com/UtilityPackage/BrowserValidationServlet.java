package dgx.software.com.UtilityPackage;

// GOAL: Validate the client's browser to make sure cookies are enabled
/*
package UselessPackage;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class BrowserValidationServlet extends HttpServlet {
	
	// Init Method
	public void init(ServletConfig config) throws ServletException {
		
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

		// Check for Disabled Cookies
		Test4Cookies(Request,Response);
		
	}

	// Test if Cookies are enabled and redirect accordingly
	public void Test4Cookies(HttpServletRequest Request, HttpServletResponse Response) {

		// Create the Variables needed to Test if Cookies are Enabled or Disabled
		final Cookie DummyCookie = new Cookie("DummyCookie", "DummyCookie");
		final String DummyCookieParameter = "DummyCookieParameter";
		final String DummyCookieValue = "DummyCookieValue";
		final String EnabledCookiesURL = "/";
		final String DisabledCookiesURL = "/JSP/JSPErrorPages/CookiesDisabled.jsp";

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

			}else{
				
				// Cookies Enabled; Cookie were Received (Request.getCookies() != null).
				Response.sendRedirect(EnabledCookiesURL);
				
			}

			}catch (IOException e) {e.printStackTrace();}
		}

	}
	
}
*/
