package dgx.DGXPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import javax.servlet.UnavailableException;

    
public class SQLQueryPractice {
   
    public static void main(String[]args) {
      
    	
    	
/*************************** START SELECT QUERY ****************************/    	
/*    	
    	// Execute a Select SQL Query
		String SelectSQLQuery = "SELECT * FROM RLF_Host_Events;";
		executeSelectSQLQuery(SelectSQLQuery);
*/
    	

/*
    	// Execute a Select SQL Query
		String CustomSQLQuery = "SELECT * FROM RLF_Host_Events;";
		executeCustomSQLQuery(CustomSQLQuery);
*/
		
/*************************** END SELECT QUERY ****************************/    	
    	
/*************************** START UPDATE QUERY ****************************/    	
   
/*	
		for(int i = 11571 ; i < 12571; i++){
		
		String UpdateSQLQuery = "INSERT INTO RLF_Host_Events ("+
					"Account_ID," +
					"Host_Username," +
					"Event_Name," +
					"Event_Category ," +
					"Event_Sub_Category," +
					"Event_Description," +
					"Event_Address," +
					"Event_Country," +
					"Event_Start_Date_And_Time," +
					"Event_End_Date_And_Time," +
					"Event_TimeZone," +
					"Date_Submitted," +
					"Time_Submitted" +
					")" +
					"VALUES(" +
					"1000000001," +
					"'Dalvis'," +
					"'Dalvis Birthday Bash!!!!!!'," +
					"'Celebration'," +
					"'Birthday Party'," +
					"'This is a party for my 25th birthday. Everyone is invited.'," +
					"'"+i+"'," +
					"'U.S'," +
					"'2011-10-18 21:30:00'," +
					"'2011-10-19 05:00:00'," +
					"'EST'," +
					"'2011-06-14'," +
					"'18:03:16'" +
					");" +
					"";
		
		//System.out.println(UpdateSQLQuery);
		
    	// Execute the Update SQL Query
		//executeUpdateSQLQuery(UpdateSQLQuery);
				
	}	

*/

/*************************** END UPDATE QUERY ****************************/    	
    			
		
    }
    
	// set up database connection and create SQL statement
	public static Statement getStatement() {
		
		// Database Connection Objects
		Connection connection = null;
		Statement statement = null;
		
	    // Database Access Information
	    String DriverName = "com.mysql.jdbc.Driver";
	    String DatabaseURL = "jdbc:mysql://localhost:10000/RLFDB";
	    String DatabaseUser = "DGXSoftware"; // The UserName of An Account in the SQL Server
	    String DatabasePassword = "DGXPassword"; // The Password of the Account in the SQL Server
	    
		
		// attempt database connection and create Statements
		try {
			// The config.getInitParameter() Parameters are variables 
			// in the web.xml file which are declared as "init-param" element  
			// These web.xml variables are used to connect to the database 
			Class.forName(DriverName);
			connection = DriverManager.getConnection(DatabaseURL,DatabaseUser, DatabasePassword);

			// create Statement to query database
			statement = connection.createStatement();
			
		} // end try
		// for any exception throw an UnavailableException to
		// indicate that the servlet is not currently available
		catch (Exception exception) {
			exception.printStackTrace();
			try {
				throw new UnavailableException(exception.getMessage());
			} catch (UnavailableException EX) {EX.printStackTrace();
			}
		} // end catch
		
		return statement;
		
	} // end method init

	
	// Executes an SQL Query which UPDATEs data
	public static void executeUpdateSQLQuery(String UpdateSQLQuery) {
		
    	// Declare and Initialize the Statement Object
    	Statement SQLStatement = getStatement();

		try {
		
		// Execute the UpdateSQLQuery
		SQLStatement.executeUpdate(UpdateSQLQuery);
			
		} catch (SQLException EX) {EX.printStackTrace();
		}
		
	}
	
	
	// Executes an SQL Query which SELECTs data
	public static void executeSelectSQLQuery(String SelectSQLQuery) {
		
    	// Declare and Initialize the Statement Object
    	Statement SQLStatement = getStatement();

		try {
		
		// Get the SQLQueryOutput
		ResultSet SQLQueryOutput = SQLStatement.executeQuery(SelectSQLQuery);
		
		// Get the ResultSetMetaData
	    ResultSetMetaData SQLQueryOutputMetaData = SQLQueryOutput.getMetaData();

	    // Go by each Row
		while (SQLQueryOutput.next()) {
			
			System.out.println(SQLQueryOutput.getRow() + ". =========================================================");
			
			// Go by each Column
			for(int i = 1 ; i < SQLQueryOutputMetaData.getColumnCount() + 1; i++){
			System.out.print(SQLQueryOutputMetaData.getColumnName(i) + " = ");
			System.out.println(SQLQueryOutput.getString(i));
			}
		}
		
		} catch (SQLException EX) {EX.printStackTrace();
		}
		
	}
	
	// Executes a Custom SQL Query
	public static void executeCustomSQLQuery(String SelectSQLQuery) {
		
// Use the date in the SQL Query to cut entries		
		
    	// Declare and Initialize the Statement Object
    	Statement SQLStatement = getStatement();

		try {
		
		// Get the SQLQueryOutput
		ResultSet SQLQueryOutput = SQLStatement.executeQuery(SelectSQLQuery);
		
		// Get the ResultSetMetaData
	    //ResultSetMetaData SQLQueryOutputMetaData = SQLQueryOutput.getMetaData();

	    ArrayList <MyEventNode> EventNodeList = new ArrayList <MyEventNode>();
	    
	    // Go by each Row
		while (SQLQueryOutput.next()) {
			
			//TO DO : CALCULATE THE DISTANCE TO THIS CURRENT EVENT HERE
			float MileDistanceFromOrigin = (float)Math.random()*100;
			
			EventNodeList.add(new MyEventNode(
					SQLQueryOutput.getInt("Event_ID"),
					SQLQueryOutput.getInt("Account_ID"),
					SQLQueryOutput.getString("Host_Username"),
					SQLQueryOutput.getString("Event_Name"),
					SQLQueryOutput.getString("Event_Category"),
					SQLQueryOutput.getString("Event_Sub_Category"),
					SQLQueryOutput.getString("Event_Description"),
					SQLQueryOutput.getString("Event_Address"),
					SQLQueryOutput.getString("Event_Country"),
					SQLQueryOutput.getString("Event_Start_Date_And_Time"),
					SQLQueryOutput.getString("Event_End_Date_And_Time"),
					SQLQueryOutput.getString("Event_TimeZone"),
					SQLQueryOutput.getString("Date_Submitted"),
					SQLQueryOutput.getString("Time_Submitted"),
					MileDistanceFromOrigin));
		}
		
		
		// Go by each Column
		for(int i = 0 ; i < EventNodeList.size(); i++){
		
		/*
		System.out.println(i + ". =========================================================");
		System.out.println(EventNodeList.get(i).Event_ID);
		System.out.println(EventNodeList.get(i).Account_ID);
		System.out.println(EventNodeList.get(i).Host_Username);
		System.out.println(EventNodeList.get(i).Event_Name);
		System.out.println(EventNodeList.get(i).Event_Category);
		System.out.println(EventNodeList.get(i).Event_Sub_Category);
		System.out.println(EventNodeList.get(i).Event_Description);
		System.out.println(EventNodeList.get(i).Event_Address);
		System.out.println(EventNodeList.get(i).Event_Country);
		System.out.println(EventNodeList.get(i).Event_Start_Date_And_Time);
		System.out.println(EventNodeList.get(i).Event_End_Date_And_Time);
		System.out.println(EventNodeList.get(i).Event_TimeZone);
		System.out.println(EventNodeList.get(i).Date_Submitted);
		System.out.println(EventNodeList.get(i).Time_Submitted);
		*/
		System.out.println(EventNodeList.get(i).MileDistanceFromOrigin);
		
		}
		
		// TO DO : SORT THE EVENT BY DISTANCE HERE
		Collections.sort(EventNodeList, new MyCustomComparator());

		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
	
		
		// Go by each Column
		for(int i = 0 ; i < EventNodeList.size(); i++){
		
		/*
		System.out.println(i + ". =========================================================");
		System.out.println(EventNodeList.get(i).Event_ID);
		System.out.println(EventNodeList.get(i).Account_ID);
		System.out.println(EventNodeList.get(i).Host_Username);
		System.out.println(EventNodeList.get(i).Event_Name);
		System.out.println(EventNodeList.get(i).Event_Category);
		System.out.println(EventNodeList.get(i).Event_Sub_Category);
		System.out.println(EventNodeList.get(i).Event_Description);
		System.out.println(EventNodeList.get(i).Event_Address);
		System.out.println(EventNodeList.get(i).Event_Country);
		System.out.println(EventNodeList.get(i).Event_Start_Date_And_Time);
		System.out.println(EventNodeList.get(i).Event_End_Date_And_Time);
		System.out.println(EventNodeList.get(i).Event_TimeZone);
		System.out.println(EventNodeList.get(i).Date_Submitted);
		System.out.println(EventNodeList.get(i).Time_Submitted);
		*/
		System.out.println(EventNodeList.get(i).MileDistanceFromOrigin);
		
		}
		
		
		} catch (SQLException EX) {EX.printStackTrace();}
		
	}
}

class MyEventNode{
	
	//Table RLF_Guest_Events
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

	
	public MyEventNode(int Event_ID,int Account_ID,String Host_Username,String Event_Name,
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
class MyCustomComparator implements Comparator <MyEventNode> {
	
	// Override: Compare to Sort from Smallest to Largest
	public int compare(MyEventNode EN1, MyEventNode EN2) {
		return EN1.MileDistanceFromOrigin.compareTo(EN2.MileDistanceFromOrigin);
	}

}
