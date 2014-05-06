package dgx.software.com.UtilityPackage;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

public class BrowserValidator {

  /*************************************************************************************************
  NAME:        HandleUserSession
  DESCRIPTION: Verify if the user has an active session and then proceed accordingly.
  PARAMETERS:  (HttpServletRequest Request, HttpServletResponse Response)
  RETURN:      VOID
  SIDE-EFFECT: None
  *************************************************************************************************/
  // Test if Cookies are enabled and redirect to the DisabledCookiesURL if Cookies are Disabled
  public static void HandleUserSession(HttpServletRequest Request, HttpServletResponse Response) {

		// Returns null if no session already exists 
		HttpSession CurrentSession =  Request.getSession(false);
		
		// Check if we have an existing Session
		if (CurrentSession == null) {
			
		// Create a new session if a session does not exist for this user
		CurrentSession =  Request.getSession(true);
		
		// Set the time in seconds that a session should be saved before timing out.
		// A negative value specifies that the session should never time out.
		CurrentSession.setMaxInactiveInterval(900); // 900 Seconds = 15 Minutes
		
		}
		
		try {
			
			// Retrieve the SessionAccountID
			String SessionAccountID = (String) CurrentSession.getAttribute("AccountID");
	
			// If the user is logged in, Redirect accordingly
			if(SessionAccountID != null){
				
				// Redirect accordingly
				Response.sendRedirect("/UserProfileServlet");
				
			}
			
		}
		
		// If an exception occurs, return the error stack trace
		catch (Exception EX) {
			EX.printStackTrace();
		}
	
  }
	
  /*************************************************************************************************
  NAME:        Test4Cookies
  DESCRIPTION: Tests the browser for Cookies. If the browser has cookies enabled it will allow
               the page to proceed. If not, it will redirect to the DisabledCookiesURL.
  PARAMETERS:  (HttpServletRequest Request, HttpServletResponse Response)
  RETURN:      VOID
  SIDE-EFFECT: None
  *************************************************************************************************/
  // Test if Cookies are enabled and redirect to the DisabledCookiesURL if Cookies are Disabled
  public static void Test4Cookies(HttpServletRequest Request, HttpServletResponse Response) {

		// Create the Variables needed to Test if Cookies are Enabled or Disabled
		final Cookie DummyCookie = new Cookie("DummyCookie", "DummyCookie");
		final String DisabledCookiesURL = "/JSP/JSPErrorPages/CookiesDisabled.jsp";
		
		// Assume and Check that the user has Cookies Disabled (Request.getCookies() == null)
		// If "Request.getCookies()" is not null proceed without adding a DummyCookie to test
		if (Request.getCookies() == null) {
		
			try {
			
            // Add the DummyCookie to the Response
			Response.addCookie(DummyCookie);
            
			// Check whether we have any cookies from what was added above,
			// If not (Request.getCookies() == null) then redirect to the DisabledCookiesURL
			if (Request.getCookies() == null) {

				// Cookies Disabled; Cookie was NOT Received (Request.getCookies() == null).
				Response.sendRedirect(DisabledCookiesURL);
            
			}

			}catch (IOException e) {e.printStackTrace();}
	
			
		}
  }
	
  /*************************************************************************************************
  NAME:        areCookiesEnabled
  DESCRIPTION: Tests the browser for Cookies. If the browser has cookies enabled it will return "True"
               If not, it will return "False".
  PARAMETERS:  (HttpServletRequest Request, HttpServletResponse Response)
  RETURN:      boolean
  SIDE-EFFECT: None
  *************************************************************************************************/
  // Test if Cookies are enabled and redirect to the DisabledCookiesURL if Cookies are Disabled
  public static boolean areCookiesEnabled(HttpServletRequest Request, HttpServletResponse Response) {

		// Create the Variables needed to Test if Cookies are Enabled or Disabled
		final Cookie DummyCookie = new Cookie("DummyCookie", "DummyCookie");
		
		// Assume and Check that the user has Cookies Disabled (Request.getCookies() == null)
		// If "Request.getCookies()" is not null proceed without adding a DummyCookie to test
		if (Request.getCookies() == null) {
		
            // Add the DummyCookie to the Response
			Response.addCookie(DummyCookie);
            
			// Check whether we have any cookies from what was added above,
			// If not (Request.getCookies() == null) then redirect to the DisabledCookiesURL
			if (Request.getCookies() == null) {

				// Cookies Disabled; Cookie was NOT Received (Request.getCookies() == null)
				// Return False (Cookies Disabled)
				return false;
			}
	
		}
		
		// Return True (Cookies Enabled)
		return true;
  } 
}
