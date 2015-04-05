/**
 * 
 */
package test;

import java.util.ArrayList;

import com.arjuna.ats.arjuna.AtomicAction;
import com.arjuna.ats.txoj.Lock;
import com.arjuna.ats.txoj.LockManager;
import com.arjuna.ats.txoj.LockMode;
import com.arjuna.ats.txoj.LockResult;


/**
 * @author AnasAlsubh
 *
 */
public class NestedTransactionExample extends LockManager{
	
	int [] elements;
	int highestIndex;
	public boolean set (int index, int value)
	{
	    boolean result = false;
	    AtomicAction A = new AtomicAction();

	    A.begin();

	    // We need to set a WRITE lock as we want to modify the state.

	    if (setlock(new Lock(LockMode.WRITE), 0) == LockResult.GRANTED)
		{
		    elements[index] = value;
		    if ((value > 0) && (index > highestIndex))
			highestIndex = index;
		    A.commit(true);
		    result = true;
		}
	    else
		A.abort();

	    return result;
	}

	
	public int get (int index)  // assume -1 means error
	{
	    AtomicAction A = new AtomicAction();

	    A.begin();

	    // We only need a READ lock as the state is unchanged.

	    if (setlock(new Lock(LockMode.READ), 0) == LockResult.GRANTED)
		{
		    A.commit(true);

		    return elements[index];
		}
	    else
		A.abort();

	    return -1;
	}

}
