
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
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import javax.servlet.UnavailableException;

    
public class Short_RLF_SQLQueryPractice {
   
    public static void main(String[]args) throws SQLException {

/*************************** START TEST SELECT QUERY ****************************/    	

/*
// Execute a Select SQL Query
String [] RLF_Accounts_Table_Column_Ignore = {"Account_ID","Username"};
ArrayList <String> RLF_Accounts_Table_Columns = getTableColumnsViaSelectSQLQuery("RLF_Accounts", RLF_Accounts_Table_Column_Ignore);

for(int i = 0 ; i < RLF_Accounts_Table_Columns.size(); i++){
	System.out.println(i + " " + RLF_Accounts_Table_Columns.get(i));
}
*/


    	

// Parameter SessionAccountID
String SessionAccountID = "1000000010";
String [] SessionAccountID_Column_Add = {"Account_ID","Location_Address","Location_City"};
ArrayList<ArrayList<String>> FieldValuePair = getTableColumnAndValuePairViaSelectSQLQuery("RLF_User_Information", SessionAccountID, SessionAccountID_Column_Add);



System.out.println("==================================");
for(int i = 0; i < FieldValuePair.get(0).size(); i++){
	System.out.println(i + ". " + FieldValuePair.get(0).get(i));
}
System.out.println("==================================");

System.out.println("----------------------------------");
for(int i = 0; i < FieldValuePair.get(1).size(); i++){
	System.out.println(i + ". " + FieldValuePair.get(1).get(i));
}
System.out.println("----------------------------------");



}
    
    
    
	// set up database connection and create SQL statement
	public static Statement getStatement() {
		
		// Database Connection Objects
		Connection connection = null;
		Statement statement = null;
		
	    // Database Access Information
	    String DriverName = "com.mysql.jdbc.Driver";
	    String DatabaseURL = "jdbc:mysql://localhost:10000/RLFDB";
	    String DatabaseUser = "RLFSoftware"; // The UserName of An Account in the SQL Server
	    String DatabasePassword = "RLFPassword"; // The Password of the Account in the SQL Server
	    
		
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

/*
	// Executes an SQL Query which SELECTS data
	public static ArrayList<String> getTableColumnsViaSelectSQLQuery(String TableName, String [] Table_Column_Ignore) {
		
		// Temporary Table_Columns ArrayList
		ArrayList <String> Temporary_Table_Columns = new ArrayList<String>();
		ArrayList <String> Temporary_Table_Column_Ignore_List = new ArrayList<String>(Arrays.asList(Table_Column_Ignore));
		
		// SQL Select Query
		String SelectSQLQuery = "SELECT column_name FROM information_schema.columns WHERE table_name = '"+TableName +"';";
		
    	// Declare and Initialize the Statement Object
    	Statement SQLStatement = getStatement();

		try {
		
		// Get the SQLQueryOutput
		ResultSet SQLQueryResultSetOutput = SQLStatement.executeQuery(SelectSQLQuery);
		
		// Get the ResultSetMetaData
	    ResultSetMetaData SQLQueryOutputMetaData = SQLQueryResultSetOutput.getMetaData();

	    // Go by each Row
		while (SQLQueryResultSetOutput.next()) {
			
			//System.out.println(SQLQueryOutput.getRow() + ". =========================================================");
			
			// Go by each Column
			for(int i = 1 ; i < SQLQueryOutputMetaData.getColumnCount() + 1; i++){
			//System.out.print(SQLQueryOutputMetaData.getColumnName(i) + " = ");
			//System.out.println(SQLQueryOutput.getString(i));
				
				// Get the Current SQL Result Values
				String CurrentColumnName = SQLQueryResultSetOutput.getString(i);
				
	    		// Only Add the Current Column and Value, if the CurrentColumn is not marked as Ignore
	    		if(!Temporary_Table_Column_Ignore_List.contains(CurrentColumnName)){
	    			Temporary_Table_Columns.add(SQLQueryResultSetOutput.getString(i));
	    		}
				
			}
		}
		
		} catch (SQLException EX) {
			EX.printStackTrace();
		}

		return Temporary_Table_Columns;
		
	}

*/
	// Executes an SQL Query which SELECTS data
	public static ArrayList<ArrayList<String>> getTableColumnAndValuePairViaSelectSQLQuery(String TableName, String SessionAccountID, String [] Table_Column_Add) throws SQLException {
		
		// Temporary ArrayList Variables
		ArrayList<ArrayList<String>> Temporary_ColumnValuePair = new ArrayList<ArrayList<String>>();
		ArrayList <String> Temporary_Column_List = new ArrayList<String>();
		ArrayList <String> Temporary_Values_List = new ArrayList<String>();

		// Generate the SQL Select Query
		String ColumnsToSelect = "";
		for(int i = 0; i < Table_Column_Add.length; i++){
			ColumnsToSelect = ColumnsToSelect.concat(Table_Column_Add[i]);
			if(i < (Table_Column_Add.length -1)){ ColumnsToSelect = ColumnsToSelect.concat(" ,"); }
		}
		String SelectSQLQuery = "SELECT "+ColumnsToSelect+" FROM "+TableName+" WHERE Account_ID="+SessionAccountID+";";
		
    	// Declare and Initialize the Statement Object
    	Statement SQLStatement = getStatement();
		
		// Get the SQLQueryOutput
		ResultSet SQLQueryResultSetOutput = SQLStatement.executeQuery(SelectSQLQuery);
		
		// If we DO NOT Have an Empty Result Set, Work with it and send a successful HTML Response
		if(SQLQueryResultSetOutput.next()){
		
		// Reset the Pointer changed by the if statement above
			SQLQueryResultSetOutput.beforeFirst();
		
		// Records the ResultSetMetaData
		ResultSetMetaData SQLQueryOutputMetaData = null;
		
		try {
	
	        // Get the ResultSetMetaData
		    SQLQueryOutputMetaData = SQLQueryResultSetOutput.getMetaData();
	
		} catch (SQLException sqlException) {
	        sqlException.printStackTrace();		

		}
		
    	try {
        
    	// Go over the Rows
    	while (SQLQueryResultSetOutput.next()) {

    	// Go over the Columns
    	// NOTE : i = 2 because we want to skip the first item (Account_ID)
    	for(int i = 1 ; i < SQLQueryOutputMetaData.getColumnCount() + 1; i++){
    		
    		// Get the Current SQL Result Values
    		String CurrentColumnName = SQLQueryOutputMetaData.getColumnName(i);
    		String CurrentValue = SQLQueryResultSetOutput.getString(i);
            
    		// Add the current Column and Value
    		Temporary_Column_List.add(CurrentColumnName);
    		Temporary_Values_List.add(CurrentValue);
	        
		}
			
		}
			} catch (SQLException SQLEX) {
					SQLEX.printStackTrace();
				}
			}

		// Add the Columns and Values List to the ColumnValue Pair List
		Temporary_ColumnValuePair.add(Temporary_Column_List);
		Temporary_ColumnValuePair.add(Temporary_Values_List);
		
		return Temporary_ColumnValuePair;
		
	}
	
}

