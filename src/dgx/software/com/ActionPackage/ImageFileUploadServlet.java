/* 
GOAL: Uploads Images to the System Disk Drive. Then it saves the Image reference in the table.

PROPERTIES: Back-End Work
1. User SQL Database Access (SQL)
2. User File Upload (Java File API)
*/

package dgx.software.com.ActionPackage;

import java.io.File;
import java.io.IOException;
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

import dgx.software.com.UtilityPackage.GlobalTools;

 
@SuppressWarnings("serial")
public class ImageFileUploadServlet extends HttpServlet {
	
	// Declare Database Connection Variables
	private Connection SQLConnection;
	private Statement SQLStatement;
	private ServletConfig InitConfig;
	
	// Declare the temporary Directory Variables
	private static final String TMP_DIR_PATH = "C:\\RealLeanFitness\\User Files\\Temporary User Files";
	private File tmpDir = null;

	//NOTE : init method only executes once during the life cycle of the Servlet
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		// Initialize the InitConfig variable
		InitConfig = config;
		
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
		
		// Call the doPost() Method
		doPost(Request, Response);
	}

	// process "Post" requests from clients
	protected void doPost(HttpServletRequest Request, HttpServletResponse Response) throws ServletException, IOException {
		
		// attempt database connection and create Statements
		try {
			// The config.getInitParameter() Parameters are variables 
			// in the web.xml file which are declared as "init-param" element  
			// These web.xml variables are used to connect to the database 
			Class.forName(InitConfig.getInitParameter("DriverName"));
			SQLConnection = DriverManager.getConnection(
				InitConfig.getInitParameter("DatabaseURL"), 
				InitConfig.getInitParameter("DatabaseUser"), 
				InitConfig.getInitParameter("DatabasePassword"));

			// create Statement to query database
			SQLStatement = SQLConnection.createStatement();
			
		} // end try
		// for any exception throw an UnavailableException to
		// indicate that the servlet is not currently available
		catch (Exception exception) {
			exception.printStackTrace();
			String UnavailableErrorMessage = "The Database is Unavailable. Please Try again later.";
			GlobalTools.writeForwardHTMLErrorResponse(Request, Response, GlobalTools.GTV_Homepage, UnavailableErrorMessage);
			throw new UnavailableException(exception.getMessage());
			
		} // end catch
		
		writeServletResponse(Request, Response);
	}
 
	// Handle the Post requests for the Servlet
	public void writeServletResponse(HttpServletRequest Request, HttpServletResponse Response) throws IOException {
		
		// Returns null if no session already exists 
		HttpSession CurrentSession =  Request.getSession(false);
		
		// Check if we have an existing Session
		if (CurrentSession == null) {
		
			try {
				// If the user does not have a session redirect them back to the Session Writer Servlet
				Response.sendRedirect("/UserSessionValidator");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		// Variables for file Directory management
		String SessionAccountID = (String) CurrentSession.getAttribute("AccountID");
		String SessionUsername = (String) CurrentSession.getAttribute("Username");
		String DESTINATION_DIR_PATH ="/User Files";
		File destinationDir = null;

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
					//System.out.println("File Uploaded to Directory = " + file);
					item.write(file);
		
					String Account_ID = SessionAccountID;
					String Image_Location = "http://" + Request.getServerName() +":"+ Request.getServerPort() + Request.getContextPath() + DESTINATION_DIR_PATH + "/" + file.getName();
					String Image_Caption = "";
					String Primary_Image = "1";

					// SQL Query
					String InsertSQLQuery = "INSERT INTO RLF_Images (" +
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
					
					
					// Mark all the current pictures from the RLF_Images Table as NOT Primary
					//String UpdateSQLQuery = "UPDATE RLF_Images SET Primary_Image='0' WHERE Primary_Image='1';";
					String UpdateSQLQuery = "UPDATE RLF_Images SET Primary_Image='0' WHERE Account_ID ='"+SessionAccountID+"' AND Primary_Image='1';";
					SQLStatement.executeUpdate(UpdateSQLQuery);
					
					// Insert the Entry for the new Image as Primary
					SQLStatement.executeUpdate(InsertSQLQuery);
					
				}
			}

			// Write the HTML Successful Response
			String SuccessfulFileUploadMessage = "Image uploaded successfully!";
			SuccessfulFileUploadMessage = "";
			GlobalTools.writeForwardHTMLSuccessResponse(Request, Response, GlobalTools.GTV_UserProfile, SuccessfulFileUploadMessage);
			
		}catch(FileUploadException EX) {
			EX.printStackTrace();
			log("Error encountered while parsing the request.",EX);
			
			// Write the HTML Error Response
			String RequestParseErrorMessage = "Error encountered while parsing the request.";
			GlobalTools.writeForwardHTMLErrorResponse(Request, Response, GlobalTools.GTV_UserProfile, RequestParseErrorMessage);
			
		}catch(Exception EX) {
			EX.printStackTrace();
			log("Error encountered while uploading file.",EX);

			// Write the HTML Error Response
			String FileUploadErrorMessage = "Error encountered while uploading file.";
			GlobalTools.writeForwardHTMLErrorResponse(Request, Response, GlobalTools.GTV_UserProfile, FileUploadErrorMessage);
			
		}
 
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
