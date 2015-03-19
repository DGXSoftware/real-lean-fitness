import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import dgx.software.com.UtilityPackage.AESEncryption;


public class GO {

	public static void main(String[]args) throws ParseException, UnsupportedEncodingException{
		
		

    	
    	//String GO = "DMGX";
    	AESEncryption AES = new AESEncryption();
    	AESEncryption AES2 = new AESEncryption();
    	//GO = "10/29/2014 02:17:30";
    	//String Encrypt = AES.getURLEncodedAESEncryption(GO);
    	//String Decrypt = AES.getURLDecodedAESDecryption(Encrypt);

    	System.out.println(AES.getURLDecodedAESDecryption("oNh70K0CQQE375uT%2BXSibQ%3D%3D"));
    	
        System.out.println(AES2.getURLDecodedAESDecryption("oNh70K0CQQE375uT+XSibQ=="));
    	
        
        
    	/*
    	System.out.println("GO = " + GO);
    	System.out.println("Encrypt = " + Encrypt);
    	System.out.println("Decrypt = " + Decrypt);
    	*/
    	
    	/*
    	if(isLinkExpired("10/28/2014 23:35:30")){
    		System.out.println("TRUE EXPIRED");
    	}else{
    		System.out.println("FALSE NOT EXPIRED");
    	}
    	*/
    	
    	
    	
	}

	/*
	// Compares a Start Date Vs. a Stop Date and if It's older than the specified hours mark it as expired (Calculates in 24 Hours)
	public static boolean isLinkExpired(String OldDateAndTime){
 
        // Get the Current Date
		Calendar c = Calendar.getInstance();
		SimpleDateFormat SDF = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		String NewDateAndTime = SDF.format(c.getTime());
        
		// Set how many hours would have to pass before the item is marked as Expired
		int HoursPassedToMarkAsExpired = 2;
		
		//HH converts hour in 24 hours format (0-23), day calculation
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
 
		Date d1 = null;
		Date d2 = null;
 
		try {
			d1 = format.parse(OldDateAndTime);
			d2 = format.parse(NewDateAndTime);
 
			//in milliseconds
			long diff = d2.getTime() - d1.getTime();
 
			//long diffSeconds = diff / 1000 % 60;
			//long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);
 
			//System.out.print(diffDays + " days, ");
			//System.out.print(diffHours + " hours, ");
			//System.out.print(diffMinutes + " minutes, ");
			//System.out.print(diffSeconds + " seconds.");
 
			// Set the URL as expired if It's over 1 day old
			if (diffDays > 0) { 
				//System.out.println("\nDays expired it"); 
				return true;
			}
			
			// Set the URL as expired if It's over the specified hours
			if (diffHours >= HoursPassedToMarkAsExpired) { 
				//System.out.println("\nHours expired it"); 
				return true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
		
	}
	*/
}
