/**
 * 
 */
package TransactionJoinpointTracker;
import java.util.HashMap;

import umjdt.util.Status;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;

import com.arjuna.ats.txoj.LockStatus;

import umjdt.concepts.Resource;
import umjdt.concepts.Transaction;
import umjdt.joinpoints.DelistResourceEventJP;
import umjdt.joinpoints.EnlistResourceEventJP;
import umjdt.util.Timestamp;

/**
 * @author AnasAlsubh
 *
 */
public abstract aspect LockingJoinpointTracker extends TransactionTracker
{
	protected DelistResourceEventJP delistResourceEventJp=null;
	protected EnlistResourceEventJP enlistResourceEventJp=null;

	boolean around(Transaction _transaction, XAResource _resource): LockTransaction(_transaction, _resource)
	{
		boolean result = false;
		try 
		{
			result= proceed(_transaction, _resource);
			org.aspectj.lang.Signature  sig = thisJoinPoint.getSignature();
			Object[] args = thisJoinPoint.getArgs();
			enlistResourceEventJp= new EnlistResourceEventJP();
			enlistResourceEventJp.setTransaction(_transaction);
			enlistResourceEventJp.setTid(_transaction.getTid());
			enlistResourceEventJp.setResource(new Resource(_resource, _transaction.getTxId()));
			enlistResourceEventJp.setState(Status.STATUS_LOCKED);
			enlistResourceEventJp.setEnlistResourceTimestamp(new Timestamp());
			enlistResourceEventJp.setEnlistResourceJP(thisJoinPoint);
			LockResourceJoinPoint(enlistResourceEventJp);
			
		} 
		catch (XAException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	boolean around (Transaction _transaction): UnlockTransaction(_transaction)
	{
		boolean result= false;
		Resource resource = null; 
		int flags;
		try
		{
			result= proceed(_transaction);
			delistResourceEventJp = new DelistResourceEventJP();
			delistResourceEventJp.setDelistResourceJP(thisJoinPoint);
			delistResourceEventJp.setTransaction(_transaction);
			Object[] args = thisJoinPoint.getArgs(); 			
			if(thisJoinPoint.getTarget() instanceof Transaction)
			{
				
				_transaction = (Transaction) thisJoinPoint.getTarget();
			}
			
			for(Object o : args)
			{
				if(o instanceof XAResource)
				{
					resource = new Resource((XAResource) o, _transaction.getTid().getXid());
				}
				if(o instanceof Integer)
				{
					flags= (int)o;
				}
			}
			delistResourceEventJp.setResource(resource);
			delistResourceEventJp.setState(Status.STATUS_RELEASED);
			delistResourceEventJp.setDelistResourceTimestamp(new Timestamp());
			UnlockResourceJoinPoint(delistResourceEventJp);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		return result;
	}	
}
