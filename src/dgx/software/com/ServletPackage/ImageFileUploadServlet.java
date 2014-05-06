/* 
GOAL: Uploads Images to the System Disk Drive. Then it saves the Image reference in the table.

PROPERTIES: Back-End Work
1. User SQL Database Access (SQL)
2. User File Upload (Java File API)
*/

package dgx.software.com.ServletPackage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
 
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

 
@SuppressWarnings("serial")
public class ImageFileUploadServlet extends HttpServlet {
	
	// Declare Database Connection Variables
	private Connection connection;
	private Statement statement;
	
	// Declare the temporary Directory Variables
	private static final String TMP_DIR_PATH = "C:\\DMGX's\\Programming Projects\\Java\\Tomcat Server\\webapps\\RLF\\User Files\\Temporary User Files";
	private File tmpDir = null;

	//NOTE : init method only executes once during the life cycle of the Servlet
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		// Attempt database connection and create a Statement
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
			
		}
		// for any exception throw an UnavailableException to
		// indicate that the servlet is not currently available
		catch (Exception exception) {
			exception.printStackTrace();
			throw new UnavailableException(exception.getMessage());
		}
		
		// Initialize the Temporary File Folder
		tmpDir = new File(TMP_DIR_PATH);
		
		// Check if the Temporary File folder exists
		if(!tmpDir.isDirectory()) {
	
			// If the Temporary File folder DOES NOT exist, Create it.
			// Creates the directory named by this abstract pathname, including any necessary but nonexistent parent directories. Note that if this operation fails it may have succeeded in creating some of the necessary parent directories. 
			tmpDir.mkdirs();
			
			// If the Temporary File Folder does not Exist call out an exception
			if(!tmpDir.isDirectory()) {throw new ServletException(TMP_DIR_PATH + " is not a directory");}
		}
 
	}
	
	// process "Get" requests from clients
	protected void doGet(HttpServletRequest Request, HttpServletResponse Response) throws ServletException, IOException {
		writeServletResponse(false, Request, Response);
	}

	// process "Post" requests from clients
	protected void doPost(HttpServletRequest Request, HttpServletResponse Response) throws ServletException, IOException {
		writeServletResponse(false, Request, Response);
	}
 
	// Handle the Post requests for the Servlet
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
		
		// Variables for file Directory management
		String SessionAccountID = (String) CurrentSession.getAttribute("AccountID");
		String SessionUsername = (String) CurrentSession.getAttribute("Username");
		String DESTINATION_DIR_PATH ="/User Files";
		File destinationDir = null;
		
		// Create the Print Writer to print the HTML Page Response
		Response.setContentType("text/html");
		PrintWriter out = Response.getWriter();
		
		try{
			
		DiskFileItemFactory  fileItemFactory = new DiskFileItemFactory ();
		
		// Set the size threshold, above which content will be stored on disk.
		fileItemFactory.setSizeThreshold(5*1024*1024); // 5 MB
		
		// Set the temporary directory to store the uploaded files of size above threshold.
		fileItemFactory.setRepository(tmpDir);
 
		ServletFileUpload uploadHandler = new ServletFileUpload(fileItemFactory);
		
			// Parse the request
			List<?> items = uploadHandler.parseRequest(Request);
			Iterator<?> itr = items.iterator();
			
			// Concatenate the Username to the DESTINATION_DIR_PATH String
			if(!SessionUsername.equals("")){DESTINATION_DIR_PATH = DESTINATION_DIR_PATH.concat("/" + SessionUsername);}
	
			// Initialize the Destination File Folder
			String realPath = getServletContext().getRealPath(DESTINATION_DIR_PATH);
			destinationDir = new File(realPath);
			
			// Check if the Destination File folder exists
			if(!destinationDir.isDirectory()) {
				
				// If the Destination File folder DOES NOT exist, Create it.
				// Creates the directory named by this abstract pathname, including any necessary but nonexistent parent directories. Note that if this operation fails it may have succeeded in creating some of the necessary parent directories. 
				destinationDir.mkdirs();
				
				// If the Destination File Folder does not Exist call out an exception
				if(!destinationDir.isDirectory()) {throw new ServletException(DESTINATION_DIR_PATH + " is not a directory");}
			}
			
			
			
			while(itr.hasNext()) {
				FileItem item = (FileItem) itr.next();
				
				// Handle Uploaded files and Ignore Form Fields
				if(!item.isFormField()) {
					
					//Write file to the ultimate location.
					File file = new File(destinationDir,createMD5Hash(item.getName()));
					item.write(file);
		
					String Account_ID = SessionAccountID;
					String Image_Location = "http://" + Request.getServerName() +":"+ Request.getServerPort() + Request.getContextPath() + DESTINATION_DIR_PATH + "/" + file.getName();
					String Image_Caption = "";
					String Primary_Image = "1";

					// SQL Query
					String InsertSQLQuery = "INSERT INTO RLFDB_Images (" +
					"Account_ID," +
					"Image_Location," +
					"Image_Caption," +
					"Primary_Image" +
					")" +
					"VALUES (" +
					"\""+Account_ID+"\"," +
					"\""+Image_Location+"\"," +
					"\""+Image_Caption+"\"," +
					"\""+Primary_Image+"\"" +
					");" +
					"";
					
					
					// Mark all the current pictures from the RLFDB_Images Table as NOT Primary
					//String UpdateSQLQuery = "UPDATE RLFDB_Images SET Primary_Image='0' WHERE Primary_Image='1';";
					String UpdateSQLQuery = "UPDATE RLFDB_Images SET Primary_Image='0' WHERE Account_ID ='"+SessionAccountID+"' AND Primary_Image='1';";
					statement.executeUpdate(UpdateSQLQuery);
					
					// Insert the Entry for the new Image as Primary
					statement.executeUpdate(InsertSQLQuery);
					
				}
			}
			
			// Write the HTML Successful Response
			writeHTMLSuccessResponse(Request, Response, out, items);
		
		}catch(FileUploadException EX) {
			EX.printStackTrace();
			log("Error encountered while parsing the request.",EX);
			// Write the HTML Error Response	
			String ErrorMessage = "Error encountered while parsing the request.";
			writeHTMLErrorResponse(Request, Response, out, ErrorMessage);
		}catch(Exception EX) {
			EX.printStackTrace();
			log("Error encountered while uploading file.",EX);
			// Write the HTML Error Response	
			String ErrorMessage = "Error encountered while uploading file.";
			writeHTMLErrorResponse(Request, Response, out, ErrorMessage);
		}
 
	}
	
	
	// Returns a Successful HTML Response
	private void writeHTMLSuccessResponse(HttpServletRequest Request, HttpServletResponse Response, PrintWriter out, List <?> items){
		
/* START HTML RESPONSE */
/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ */

		out.println("<?xml version = '1.0'?>");
		out.println("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'>");
		out.println("<html xmlns='http://www.w3.org/1999/xhtml'>");
		out.println("<head>");
		out.println("<title>Image File Upload Servlet</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<script type='text/javascript'>");
		out.println("alert('File uploaded successfully!');");
		out.println("</script>");
		
		
		/*
		// Declare and Initialize the Iterator to review all the items
		Iterator <?> itr = items.iterator();
		
		while(itr.hasNext()) {
			
		FileItem item = (FileItem) itr.next();
		
		//Handle Form Fields.	 
		if(item.isFormField()) {
			
			out.println("<p>");
			out.println("Form Field Name = "+item.getFieldName()+", Form Field Value = "+item.getString());
			out.println("</p>");
			
		} else {
		
		out.println("<p>");
		//Handle Uploaded files.
		out.println("Field Name = "+item.getFieldName()+
			", File Name = "+item.getName()+
			", Content type = "+item.getContentType()+
			", File Size = "+item.getSize());
			}
		out.println("</p>");
		}
		*/
		
		out.println("<meta http-equiv='REFRESH' content='0;url=/UserProfileServlet'/>");
		out.println("</body>");
		out.println("</html>");
		
/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ */
/* END HTML RESPONSE */
		
		// Close the stream to complete the page
		out.close();
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
		out.println("<meta http-equiv='REFRESH' content='0;url=/UserProfileServlet'/>");
		out.println("</body>");
		out.println("</html>");

/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ */
/* END HTML RESPONSE */
		
		// Close the stream to complete the page
		out.close();
		
	}

	// Creates an MD5 Hash from a String
	public static String createMD5Hash(String ToMD5) {
		
        String FileExtention = ToMD5.substring(ToMD5.lastIndexOf("."),ToMD5.length()); 
		
		   try {
		        java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
		        byte[] array = md.digest(ToMD5.getBytes());
		        StringBuffer sb = new StringBuffer();
		        for (int i = 0; i < array.length; ++i) {
		          sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
		       }
		        return sb.toString() + FileExtention;
		    } catch (java.security.NoSuchAlgorithmException e) {
		    }
		    return null;
	}
}
