package dgx.software.com.UtilityPackage;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GlobalTools {

/**************************************************************************************************/
/* START GLOBAL VARIABLES */	
/**************************************************************************************************/
    
	// <%= GlobalTools.GTV_Homepage %>
	public static final String GTV_Homepage = "/";
	
	// <%= GlobalTools.GTV_UserProfile %>
	public static final String GTV_UserProfile = "/JSP/UserPages/UserProfile.jsp";
	
	// <%= GlobalTools.GTV_UserInformation %>
	public static final String GTV_UserInformation = "/JSP/UserPages/UserInformation.jsp";
	
	// <%= GlobalTools.GTV_ContactUs %>
	public static final String GTV_ContactUs = "/JSP/Mail/ContactUs.jsp";
	
	// <%= GlobalTools.GTV_PayPalRegistrationSubmit %>
	public static final String GTV_PayPalRegistrationSubmit = "/JSP/PayPal/PayPalRegistrationSubmit.jsp";
	
	
/**************************************************************************************************/
/* END GLOBAL VARIABLES */	
/**************************************************************************************************/
		
			
	
	
		/*************************************************************************************************
		NAME:        writeForwardHTMLSuccessResponse
		DESCRIPTION: Handle successful forwarding responses.
		PARAMETERS:  (HttpServletRequest Request, HttpServletResponse Response)
		RETURN:      VOID
		SIDE-EFFECT: If provided a "SuccessMessage" It will alert before forwarding.
		*************************************************************************************************/
		// Returns a Successful HTML Response and forward appropriately.
		public static void writeForwardHTMLSuccessResponse(HttpServletRequest Request, HttpServletResponse Response, String ForwardAddress, String SuccessMessage){

			// Create the PrintWriter to print the HTML Page Response
			// The response is sent to the client through the PrintWriter object
			// obtained from the HttpServletResponse object.
			// NOTE: If the response is binary data, such as an image, method
			// getOutputStream is used to obtain a reference to a
			// ServletOutputStream object.
			
			// set up response to client
			Response.setContentType("text/html");
			PrintWriter out = null;
			try {
				out = Response.getWriter();
			} catch (IOException e) {
				System.out.println("INTERNAL ERRROR: writeForwardHTMLSuccessResponse()");
				e.printStackTrace();
			}
			
			out.println("<?xml version = '1.0'?>");
			out.println("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'>");
			out.println("<html xmlns='http://www.w3.org/1999/xhtml'>");
			out.println("<head>");
			out.println("<title>Login Servlet</title>");
			out.println("</head>");
			out.println("<body>");
			
			// Alert if "SuccessMessage" is not Empty
			if(!SuccessMessage.equals("")){
			out.println("<script type='text/javascript'>");
			out.println("alert('"+SuccessMessage+"');");
			out.println("</script>");
			}
			
			out.println("<meta http-equiv='REFRESH' content='0;url="+ForwardAddress+"'/>");
			out.println("</body>");
			out.println("</html>");

			// Close the stream to complete the page
			out.close();
			
		}
		
		/*************************************************************************************************
		NAME:        writeHTMLErrorResponse
		DESCRIPTION: Handle failure forwarding responses.
		PARAMETERS:  (HttpServletRequest Request, HttpServletResponse Response)
		RETURN:      VOID
		SIDE-EFFECT: If provided an "ErrorMessage" It will alert before forwarding.
		*************************************************************************************************/
		
		// Returns an Error Message HTML Response and forward appropriately.
		public static void writeForwardHTMLErrorResponse(HttpServletRequest Request, HttpServletResponse Response,String ForwardAddress, String ErrorMessage){

			// Create the PrintWriter to print the HTML Page Response
			// The response is sent to the client through the PrintWriter object
			// obtained from the HttpServletResponse object.
			// NOTE: If the response is binary data, such as an image, method
			// getOutputStream is used to obtain a reference to a
			// ServletOutputStream object.
			
			// set up response to client
			Response.setContentType("text/html");
			PrintWriter out = null;
			try {
				out = Response.getWriter();
			} catch (IOException e) {
				System.out.println("INTERNAL ERRROR: writeForwardHTMLErrorResponse()");
				e.printStackTrace();
			}
			
			out.println("<?xml version = '1.0'?>");
			out.println("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'>");
			out.println("<html xmlns='http://www.w3.org/1999/xhtml'>");
			out.println("<head>");
			out.println("<title>Internal Error!</title>");
			out.println("</head>");
			out.println("<body>");
			
			// Alert if "ErrorMessage" is not Empty
			if(!ErrorMessage.equals("")){
			out.println("<script type='text/javascript'>");
			out.println("alert('"+ErrorMessage+"');");
			out.println("</script>");
			}
			
			out.println("<meta http-equiv='REFRESH' content='0;url="+ForwardAddress+"'/>");
			out.println("</body>");
			out.println("</html>");

			// Close the stream to complete the page
			out.close();
			
		}
	
		/*************************************************************************************************
		NAME:        writeHTMLNotificationResponse
		DESCRIPTION: Handle failure forwarding responses.
		PARAMETERS:  (HttpServletRequest Request, HttpServletResponse Response)
		RETURN:      VOID
		SIDE-EFFECT: If provided an "ErrorMessage" It will alert before forwarding.
		*************************************************************************************************/
		
		// Returns an Error Message HTML Response and forward appropriately.
		public static void writeHTMLNotificationResponse(HttpServletRequest Request, HttpServletResponse Response,String ForwardAddress, String NotificationMessage){

			// Create the PrintWriter to print the HTML Page Response
			// The response is sent to the client through the PrintWriter object
			// obtained from the HttpServletResponse object.
			// NOTE: If the response is binary data, such as an image, method
			// getOutputStream is used to obtain a reference to a
			// ServletOutputStream object.
			
			// set up response to client
			Response.setContentType("text/html");
			PrintWriter out = null;
			try {
				out = Response.getWriter();
			} catch (IOException e) {
				System.out.println("INTERNAL ERRROR: writeHTMLNotificationResponse()");
				e.printStackTrace();
			}
			
			out.println("<?xml version = '1.0'?>");
			out.println("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'>");
			out.println("<html xmlns='http://www.w3.org/1999/xhtml'>");
			out.println("<head>");
			out.println("<title>Internal Error!</title>");
			out.println("</head>");
			out.println("<body>");
			
			// Alert if "ErrorMessage" is not Empty
			if(!NotificationMessage.equals("")){
			out.println("<script type='text/javascript'>");
			out.println("alert('"+NotificationMessage+"');");
			out.println("</script>");
			}
			
			out.println("<meta http-equiv='REFRESH' content='0;url="+ForwardAddress+"'/>");
			out.println("</body>");
			out.println("</html>");

			// Close the stream to complete the page
			out.close();
			
		}
		
		// Returns a Successful HTML Response and forward appropriately.
		public static void writeForwardHTMLSuccessResponse(ServletConfig config){
			
			
			
		}

		
}
