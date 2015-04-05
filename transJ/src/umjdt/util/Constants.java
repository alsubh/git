package umjdt.util;

import java.io.FileInputStream;
import java.util.*;
import java.util.Properties;

public class Constants 
{

	static Properties props = new Properties();
	 public static long TimeToWait = 0;
	 
	 public static void loadProperties()
		{
			String path = "C:\\Users\\AnasAlsubh\\Documents\\TransJ\\tool\\transaction-aspect\\src\\umjdt\\util\\";
		       try {
		    	  props.load(new FileInputStream(path+"constants.properties")); 
			} catch (Exception e) {
				e.printStackTrace();
			}			
				TimeToWait = Long.parseLong(props.getProperty("TimeToWait"));	            
		}
}