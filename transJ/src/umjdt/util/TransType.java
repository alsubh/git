package umjdt.util;

import java.io.PrintWriter;
import java.io.Serializable;

public class TransType implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int TOP_LEVEL = 0;
	public static final int NESTED = 1;
    public static final int FLAT = 2;

    public static String stringForm (int type)
    {
	        switch (type)
	        {
	        case TOP_LEVEL:
	            return "TransactionType.TOP_LEVEL";
	        case NESTED:
	            return "TransactionType.NESTED";
	        case FLAT:
	            return "TransactionType.FLAT";
	        default:
	            return "Unknown";
	        }
	    }

	public static void print (PrintWriter strm, int res)
	{
	     strm.print(stringForm(res));
	}
}