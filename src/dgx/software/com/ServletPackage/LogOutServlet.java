/* 
GOAL: Logs the user out of the system and terminates the session.

PROPERTIES: Back-End Work
*/

package dgx.software.com.ServletPackage;

import java.io.PrintWriter;
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")
public class LogOutServlet extends HttpServlet {
	
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

		// Returns null if no session already exists 
		HttpSession CurrentSession =  Request.getSession(false);
		
		// Check if we have an existing Session
		if (CurrentSession == null) {
		
			try {
				// If the user does not have a session redirect them back to the Session Writer Servlet
				Response.sendRedirect("/");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		// Set up response to client
		Response.setContentType("text/html");
		PrintWriter out = Response.getWriter();

		/* START Servlet Response */
/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ */

		// Attempt to Log the user out
		try {
			
			// Log Out the User
			CurrentSession.invalidate();
			
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
		out.println("<html xmlns='http://www.w3.org/1999/xhtml'>");
		out.println("<head>");
		out.println("<title>Log Out Servlet</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<script type='text/javascript'>");
		out.println("alert('You have successfully logged out!');");
		out.println("</script>");
		out.println("<meta http-equiv='REFRESH' content='0;url=/'/>");
		out.println("</body>");
		out.println("</html>");
		
		
/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ */
/* END HTML RESPONSE */
		
		// Close the stream to complete the page
		out.close();
	}
	
}
