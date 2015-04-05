/**
 * 
 */
package utilities;

/**
 * @author AnasAlsubh
 *
 */

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class ConvertTime {
	  
    public static Date ConvertMillsToDate(long mills)
    {
       //Converting milliseconds to Date using java.util.Date
       //current time in milliseconds
       long currentDateTime = mills;
       //creating Date from millisecond
       Date currentDate = new Date(currentDateTime);
       //printing value of Date
      // System.out.println("current Date: " + currentDate);
       DateFormat df = new SimpleDateFormat("dd:MM:yy:HH:mm:ss");
       //formatted value of current Date
      // System.out.println("Milliseconds to Date: " + df.format(currentDate));
            
       return currentDate;
    }
    
    public static long convertDateToMills( Date date)
    {
    	//System.out.println("Date is : " + date);
        long mills= date.getTime();
        return mills;
    }
}