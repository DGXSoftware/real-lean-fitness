/*
package dgx.software.com.UtilityPackage;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")
public class UserSessionValidator extends HttpServlet {

	// Init Method
	public void init(ServletConfig config) throws ServletException {
		
	}

	// process "Get" requests from clients
	protected void doGet(HttpServletRequest Request, HttpServletResponse Response) throws ServletException, IOException {

		// Call the doPost() Method
		doPost(Request, Response);
	}

	// process "Post" requests from clients
	protected void doPost(HttpServletRequest Request, HttpServletResponse Response) throws ServletException, IOException {
		writeServletResponse(Request, Response);
	}
	
	// Create the Servlet Response
	public void writeServletResponse(HttpServletRequest Request, HttpServletResponse Response) throws IOException {

		// Check for Disabled Cookies
		HandleUserSession(Request,Response);
		
	}
*/	
  /*************************************************************************************************
  NAME:        HandleUserSession
  DESCRIPTION: Verify if the user has an active session and then proceed accordingly.
  PARAMETERS:  (HttpServletRequest Request, HttpServletResponse Response)
  RETURN:      VOID
  SIDE-EFFECT: None
  *************************************************************************************************/
/*  // Test if Cookies are enabled and redirect to the DisabledCookiesURL if Cookies are Disabled
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
				Response.sendRedirect(GlobalTools.GTV_UserProfile);
				
			}
			
		}
		
		// If an exception occurs, return the error stack trace
		catch (Exception EX) {
			EX.printStackTrace();
		}
	
  }
	
}
*/