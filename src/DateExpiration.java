import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateExpiration {

	public static void main(String[]args) throws ParseException{
    	
		// Get the Old Date
		String OldDate = "2014-11-17 14:29:38";
		
		// Get the Current Date
		Calendar c = Calendar.getInstance();
		SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String NewDate = SDF.format(c.getTime());
		
		boolean LinkIsExpired = isLinkExpired(OldDate, NewDate, 2);
		
		if(LinkIsExpired == true){
			System.out.println("\nLink is expired");
		}else{
			System.out.println("\nLink is NOT expired");
		}
		
		

		
		// Get the Event Date
		String OldDateEvent = "2014-11-02 13:06:15";
		String NewDateEvent = "2014-12-04 13:06:15";

		// Compare the Old Date Vs. the New Date and return if It's a New Day
		if (areSameDay(OldDateEvent, NewDateEvent)) {
			   System.out.println("It's the same day");
		   }else{
			   System.out.println("It's NOT the same day");
		   }
		
	}
	
	// Compares an Old Date Vs. a New Date and lets you know if they are from different days of the year (Calculates in 365 Days)
	public static boolean areSameDay(String OldDateEvent, String NewDateEvent) throws ParseException{
		
		//HH converts hour in 24 hours format (0-23), day calculation
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		// Get the Calendar Object for the OldDateEvent
		Date OldEventDateObject = format.parse(OldDateEvent);
		Calendar OldEventCalendarObject = Calendar.getInstance();
		OldEventCalendarObject.setTime(OldEventDateObject);
		
		// Get the Calendar Object for the NewDateEvent
		Date NewEventDateObject = format.parse(NewDateEvent);
		Calendar NewEventCalendarObject = Calendar.getInstance();
		NewEventCalendarObject.setTime(NewEventDateObject);
		
		// Get the Unique Date ID OldDayOfYearPlusYear and NewDayOfYearPlusYear for comparison
		String OldDayOfYearPlusYear = OldEventCalendarObject.get(Calendar.DAY_OF_YEAR) + "-" + OldEventCalendarObject.get(Calendar.YEAR);
		String NewDayOfYearPlusYear = NewEventCalendarObject.get(Calendar.DAY_OF_YEAR) + "-" + NewEventCalendarObject.get(Calendar.YEAR);
		
		System.out.println("OldDayOfYearPlusYear = " + OldDayOfYearPlusYear);
		System.out.println("NewDayOfYearPlusYear = " + NewDayOfYearPlusYear);
		
		// Compare the Old Date Vs. the New Date and return if It's a New Day
		if (OldDayOfYearPlusYear.equals(NewDayOfYearPlusYear)) {
			   //System.out.println("It's the same day");
			   return true;
		   }else{
			   //System.out.println("It's NOT the same day");
			   return false;
		   }
		
	}
	
	// Compares a Start Date Vs. a Stop Date and if It's older than the specified hours mark it as expired (Calculates in 24 Hours)
	public static boolean isLinkExpired(String dateStart, String dateStop, int HoursPassedToMarkAsExpired){
		
		//HH converts hour in 24 hours format (0-23), day calculation
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 
		Date d1 = null;
		Date d2 = null;
 
		try {
			d1 = format.parse(dateStart);
			d2 = format.parse(dateStop);
 
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
				return true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
		
	}
	
}
