package test.org.json;

//SOURCE: https://developers.google.com/maps/documentation/javascript/places

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class AGoogleDistanceMatrixAPITest {
	
public static void main(String[]args) throws JSONException{
	
	// Get the expected Distance and Time for Driving to multiple locations in JSON Format
	String JSONURL = "http://maps.googleapis.com/maps/api/distancematrix/json?origins=4640+Bronx+Boulevard+Bronx+New+York+NY+10470&destinations=2000+Corporate+Dr+Orangeburg+NY+10962|465+Willis+Avenue+Bronx+New+York+NY|23+Vermilyea+Avenue,+New+York,+NY&mode=driving&language=en-EN&units=imperial&sensor=false";
	String FullJSONString = getWebsiteSourceCode(JSONURL);
	//String FullJSONString = "{   \"destination_addresses\" : [      \"2000 Corporate Dr, Orangeburg, NY 10962, USA\",      \"465 Willis Ave, Bronx, NY 10455, USA\"   ],   \"origin_addresses\" : [ \"4640 Bronx Blvd, Bronx, NY 10470, USA\" ],   \"rows\" : [      {         \"elements\" : [            {               \"distance\" : {                  \"text\" : \"27.9 mi\",                  \"value\" : 44880               },               \"duration\" : {                  \"text\" : \"41 mins\",                  \"value\" : 2439               },               \"status\" : \"OK\"            },            {               \"distance\" : {                  \"text\" : \"10.2 mi\",                  \"value\" : 16469               },               \"duration\" : {                  \"text\" : \"17 mins\",                  \"value\" : 1006               },               \"status\" : \"OK\"            }         ]      }   ],   \"status\" : \"OK\"}";
	System.out.println(FullJSONString.toString());
	
	
	JSONObject [] ElementsJSONArray = getJSONObjectArray(FullJSONString, new String[] {"rows","elements"});
	String [] OriginAddress = getJSONStringValueArray(FullJSONString, "origin_addresses");
	String [] DestinationAddresses = getJSONStringValueArray(FullJSONString, "destination_addresses");


	JSONObject TemporaryJSONObject = null;
		
		for(int i = 0; i < ElementsJSONArray.length; i++){
			
			TemporaryJSONObject = new JSONObject(ElementsJSONArray[i].get("duration").toString());
			String TemporaryDuration = TemporaryJSONObject.getString("text");
			
			TemporaryJSONObject = new JSONObject(ElementsJSONArray[i].get("distance").toString());
			String TemporaryDistance = TemporaryJSONObject.getString("text");
		
			System.out.println("=====================================================================================");
			System.out.println("The distance between " + OriginAddress[0] + " and " + DestinationAddresses[i]);
			System.out.println("is " + TemporaryDistance + " miles. It will take " + TemporaryDuration + " to get there by Driving.");
	
		}

}

// Returns the desired JSONStringValue from a specified JSONStringName within a JSON File
public static String getJSONStringValue(String FullJSONString, String JSONStringName) {

	// Declare a default JSONStringValue
	String JSONStringValue = "";

	try {
		// Get the JSONStringValue by using the FullJSONString and JSONStringName
		JSONStringValue = new JSONObject(FullJSONString).get(JSONStringName).toString();
	} catch (JSONException EX) {EX.printStackTrace();}

	// Return the desired JSONStringValue
	return JSONStringValue;
	
}

// Returns a String Array from a specified Array within a JSON File
public static String [] getJSONStringValueArray(String FullJSONString, String JSONArrayName) {

	// Method Variables
	JSONObject TemporaryJSONObject = null;
	JSONArray TemporaryJSONObjectList = null;
	String [] TemporaryJSONStringArray = null;
	
	try {
	
	// Declare the default TemporaryJSONObject
	TemporaryJSONObject = new JSONObject(FullJSONString);
		
	// Extract the JSONArrayName From the TemporaryJSONObject
	TemporaryJSONObjectList = TemporaryJSONObject.getJSONArray(JSONArrayName);
	
	// Convert the TemporaryJSONObjectList to the TemporaryJSONStringArray
	TemporaryJSONStringArray = new String [TemporaryJSONObjectList.length()];
	for(int i = 0; i < TemporaryJSONStringArray.length; i++){
		TemporaryJSONStringArray[i] = TemporaryJSONObjectList.get(i).toString();

	}
	} catch (JSONException EX) {EX.printStackTrace();}
	
	// Return the TemporaryJSONStringArray
	return TemporaryJSONStringArray;
}

// Retrieves a JSONObject Array; You can specify the depth of the array within the JSON File by providing a JSONArrayHierarchy String Array
public static JSONObject [] getJSONObjectArray(String FullJSONString, String [] JSONArrayHierarchy) {
	
	// Method Variables
	JSONObject TemporaryJSONObject = null;
	JSONArray TemporaryJSONObjectList = null;
	JSONObject [] TemporaryJSONObjectArray = null;
	
	try{
		
	// Declare the default TemporaryJSONObject
	TemporaryJSONObject = new JSONObject(FullJSONString);
	
	// Break down the TemporaryJSONObjectList to the lowest point dictated by the JSONArrayHierarchy
	for(int i = 0 ; i < JSONArrayHierarchy.length; i++){
	TemporaryJSONObjectList = TemporaryJSONObject.getJSONArray(JSONArrayHierarchy[i]);
	TemporaryJSONObject = new JSONObject(TemporaryJSONObjectList.get(i).toString());
	}	
	
	// Convert the TemporaryJSONObjectList to the TemporaryJSONObjectArray
	TemporaryJSONObjectArray = new JSONObject[TemporaryJSONObjectList.length()];
	for(int i = 0; i < TemporaryJSONObjectArray.length; i++){
		TemporaryJSONObjectArray[i] = new JSONObject(TemporaryJSONObjectList.get(i).toString());
	}
	} catch (JSONException EX) {EX.printStackTrace();}
	
	// Return the TemporaryJSONObjectArray
	return TemporaryJSONObjectArray;
}

//This method retrieves the Source Code of a Website 
public static String getWebsiteSourceCode(String UserWebSite){

	//Variables to retrieve the Content from the URL
	URL UserWebFile = null;
	BufferedReader MyBufferedReader = null;
	String WebFileSource = "";
	String TemporaryWebFileData = "";

   try {

 	  // Initialize the URL Object
 	  UserWebFile = new URL(UserWebSite);
 	  
 	 // Convert the InputStreamReader from the URL Object into a BufferedReader
      // Buffering the stream makes the reading faster;
 	  MyBufferedReader = new BufferedReader(new InputStreamReader(UserWebFile.openStream()));
 	  
      // Read each line of the input stream, and Concatenate each line to the WebFileData String
      while ((TemporaryWebFileData = MyBufferedReader.readLine()) != null) {

     	 // NOTE: TemporaryWebFileData is one line of text; readLine() strips the newline character(s)
     	 WebFileSource = WebFileSource.concat(TemporaryWebFileData);
      }

   } catch (MalformedURLException mue) {

      System.out.println("A MalformedURLException occurred.");
      mue.printStackTrace();
      System.exit(1);

   } catch (IOException ioe) {

      System.out.println("An IOException occurred.");
      ioe.printStackTrace();
      System.exit(1);

   } finally {
 	  
 	  // Try to properly close the BufferedReader Object
      try {
          // Close the BufferedReader Object
          MyBufferedReader.close();
      } catch (IOException EX) {EX.printStackTrace();}

   } 
	
   return WebFileSource;
}

}

/**


:SOURCE:
https://developers.google.com/maps/documentation/distancematrix/

:XML FORMAT; Get the expected Distance and Time taken for Driving From Home to Work:
http://maps.googleapis.com/maps/api/distancematrix/xml?origins=4640+Bronx+Boulevard+Bronx+New+York+NY+10470&destinations=2000+Corporate+Dr+Orangeburg+NY+10962&mode=driving&language=en-EN&units=imperial&sensor=false

:XML FORMAT; Get the expected Distance and Time taken for Driving From Home to Work and MGG's house:
http://maps.googleapis.com/maps/api/distancematrix/xml?origins=4640+Bronx+Boulevard+Bronx+New+York+NY+10470&destinations=2000+Corporate+Dr+Orangeburg+NY+10962|465+Willis+Avenue+Bronx+New+York+NY&mode=driving&language=en-EN&units=imperial&sensor=false

:JSON FORMAT; Get the expected Distance and Time taken for Driving From Home to Work:
http://maps.googleapis.com/maps/api/distancematrix/json?origins=4640+Bronx+Boulevard+Bronx+New+York+NY+10470&destinations=2000+Corporate+Dr+Orangeburg+NY+10962&mode=driving&language=en-EN&units=imperial&sensor=false

:JSON FORMAT; Get the expected Distance and Time taken for Driving From Home to Work and MGG's house:
http://maps.googleapis.com/maps/api/distancematrix/json?origins=4640+Bronx+Boulevard+Bronx+New+York+NY+10470&destinations=2000+Corporate+Dr+Orangeburg+NY+10962|465+Willis+Avenue+Bronx+New+York+NY&mode=driving&language=en-EN&units=imperial&sensor=false


*/
