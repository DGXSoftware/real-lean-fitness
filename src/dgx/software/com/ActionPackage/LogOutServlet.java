/* 
GOAL: Logs the user out of the system and terminates the session.

PROPERTIES: Back-End Work
*/

package dgx.software.com.ActionPackage;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dgx.software.com.UtilityPackage.GlobalTools;

@SuppressWarnings("serial")
public class LogOutServlet extends HttpServlet {
	
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

		// Returns null if no session already exists 
		HttpSession CurrentSession =  Request.getSession(false);
		
		// Check if we have an existing Session
		if (CurrentSession == null) {
		
			try {
				// If the user does not have a session redirect them back to the Session Writer Servlet
				Response.sendRedirect(GlobalTools.GTV_Homepage);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}

		/* START Servlet Response */
/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ */

		// Attempt to Log the user out
		try {
			
			// Log Out the User
			CurrentSession.invalidate();
			
			// Write the HTML Successful Response
			String LogOutMessage = "You have successfully logged out!";
			LogOutMessage = "";
			GlobalTools.writeForwardHTMLSuccessResponse(Request, Response, GlobalTools.GTV_Homepage, LogOutMessage);
			
		
			
/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ */
		/* END Servlet Response */
			
		} // end try
		// if am exception occurs, return error page
		catch (Exception EX) {
			EX.printStackTrace();
		} // end catch
	
	}

}
