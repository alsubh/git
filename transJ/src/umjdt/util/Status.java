package umjdt.util;

import java.io.PrintWriter;

public class Status implements javax.transaction.Status {

	public static final int STATUS_LOCKED = 17;
	public static final int STATUS_RELEASED = 18;

	/**
	 * @return <code>String</code> representation of the status.
	 */

	public static String stringForm(int res) {
		switch (res) {
		case STATUS_LOCKED:
			return "Status.RUNNING";
		case STATUS_RELEASED:
			return "Status.PREPARING";
		default:
			return "Unknown";
		}
	}

	/**
	 * Print the status on the specified <code>PrintWriter</code>.
	 */

	public static void print(PrintWriter strm, int res) {
		strm.print(Status.stringForm(res));
	}
}