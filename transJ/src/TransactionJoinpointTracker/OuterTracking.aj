/**
 * 
 */
package TransactionJoinpointTracker;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.SystemException;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

import com.arjuna.ats.arjuna.common.Uid;
import com.arjuna.ats.internal.jta.transaction.arjunacore.TransactionImple;

import umjdt.concepts.Resource;
import umjdt.concepts.SubTransaction;
import umjdt.concepts.Tid;
import umjdt.concepts.Transaction;
import umjdt.joinpoints.AbortEventJP;
import umjdt.joinpoints.BeginEventJP;
import umjdt.joinpoints.CommitEventJP;
import umjdt.joinpoints.EndEventJP;
import umjdt.joinpoints.transaction.OuterTransactionJP;
import umjdt.util.Status;
import umjdt.util.Timestamp;

import java.util.Hashtable;
import java.util.Map;

import org.apache.log4j.Logger;

import com.arjuna.ats.internal.jta.xa.TxInfo;



/**
 * @author AnasAlsubh
 *
 */
public aspect OuterTracking extends TransactionTracker
{
	protected BeginEventJP beginEventJp=null;
	protected CommitEventJP commitjp=null;
	protected AbortEventJP abortjp=null;
	//TODO: put the Begin and End in the outerTransactionJp
	//TODO: send the data thorugh this oterJoinpoint to begin and end JP
	//TODO: No context information 
	protected OuterTransactionJP outerTransactionJP=null;
	
	before() : OpenTransaction()
	{
		//No context information
		beginEventJp = new BeginEventJP();
		beginEventJp.setBeginJP(thisJoinPoint);
		beginEventJp.setBeginTime(new Timestamp());
	}
	
	after() throws SystemException: CommitTransaction()
	{
		Object target= thisJoinPoint.getTarget();
		//System.out.println(target.getClass());
		Object[] args= thisJoinPoint.getArgs();
		//System.out.println(args.getClass());
		Object _this = thisJoinPoint.getThis();
		//System.out.println(_this.getClass());

		commitjp = new CommitEventJP();
		commitjp.setCommitJP(thisJoinPoint);
		commitjp.setStatus(Status.STATUS_COMMITTED);
		
		contexinfo(commitjp, target);
	}
	
	after(): AbortTransaction()
	{
		Object target= thisJoinPoint.getTarget();
		//System.out.println(target.getClass());
		Object[] args= thisJoinPoint.getArgs();
		//System.out.println(args.getClass());
		Object _this = thisJoinPoint.getThis();
		//System.out.println(_this.getClass());
		try 
		{
			//Transaction transaction = (Transaction) TransactionImple.getTransaction(_tid.getUid());
			abortjp = new AbortEventJP();
			abortjp.setAbortJP(thisJoinPoint);
			abortjp.setStatus(Status.STATUS_ROLLEDBACK);
			contexinfo(abortjp, target);
		} 
		catch (SystemException e) 
		{
			e.printStackTrace();
		}
	}
	
	private void contexinfo(EndEventJP _endJP, Object _target) throws SystemException 
	{
		if(_target !=null)
		{
			if(_target instanceof TransactionManager)
			{
				_endJP.setManager((TransactionManager)_target);
				//transaction currently associated with thread.
				transaction = (Transaction)_endJP.getManager().getTransaction();
			}
			else if(_target instanceof UserTransaction)
			{
				_endJP.setUser((UserTransaction)_target);
				transaction = (Transaction) TransactionImple.getTransaction();
			}
		} 
		_endJP.getTid().setXid(transaction.getTxId());
		Xid xid= transaction.getTxId();
		//System.out.println(_"TxId= "+ transaction.getTxId());
		int status= transaction.getStatus();
		int timeout = transaction.getTimeout();
		
		List<Resource> resourceList= new ArrayList<>();		
		if(transaction.getResources().size() > 0)
		{
			for(XAResource xares : transaction.getResources().keySet())
			{
				Resource res;
				try 
				{
					res = new Resource(xares);
					res.setXid(transaction.getResources().get(xares).xid());
					res.setState(transaction.getResources().get(xares).getState());// TMFAIL for abort and TMSUCESS for commit
					resourceList.add(res);
				}
				catch (XAException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		List<SubTransaction> subtransactions= new ArrayList<>();
		if(TransactionImple.getTransactions().size() > 0)
		{
			for(Uid _uid : TransactionImple.getTransactions().keySet())
			{
				SubTransaction sub= new SubTransaction(new Tid(xid, _uid));
				Xid tid= sub.getTid();
				sub = (SubTransaction)TransactionImple.getTransactions().get(_uid);
				sub.setStatus(sub.getStatus());
				sub.setTimeout(sub.getTimeout());	
				subtransactions.add(sub);
			}
		}
		
		if(_endJP instanceof CommitEventJP)
			passContextInfo(commitjp, _target, transaction, subtransactions, xid, resourceList, status, timeout);
		else
			passContextInfo(abortjp, _target, transaction, subtransactions, xid, resourceList, status, timeout);
	}

	private void passContextInfo(CommitEventJP commitJP, Object _target,
			TransactionImple transaction, List<SubTransaction> transactions,
			Xid xid, List<Resource> resourceList, int status, int timeout) 
	{
		CommitEventJP commiteventJp= commitJP;
		
		commiteventJp.setStatus(Status.STATUS_COMMITTING);
		commiteventJp.setTimeout(timeout);
		commiteventJp.setResources(resourceList);
		commiteventJp.setTid(new Tid(xid));
		commiteventJp.setTransactions(transactions);
		commiteventJp.setTransaction(transaction);
		if((_target !=null) && (_target.getClass().equals(TransactionManager.class)))
			commiteventJp.setManager((TransactionManager)_target);
		
		//Commit(commiteventJp);
		CommitJoinPoint(commiteventJp);
	}
	
	private void passContextInfo(AbortEventJP abortJP, Object _target,
			TransactionImple transaction, List<SubTransaction> transactions,
			Xid xid, List<Resource> resourceList, int status, int timeout) 
	{
		AbortEventJP aborteventJp= abortJP;
		
		aborteventJp.setStatus(Status.STATUS_ROLLING_BACK);
		aborteventJp.setTimeout(timeout);
		aborteventJp.setResources(resourceList);
		aborteventJp.setTid(new Tid(xid));
		aborteventJp.setTransactions(transactions);
		aborteventJp.setTransaction((umjdt.concepts.Transaction) transaction);
		if((_target !=null) && (_target.getClass().equals(TransactionManager.class)))
			aborteventJp.setManager((TransactionManager)_target);
		
		//Abort(aborteventJp);
		AbortJoinPoint(aborteventJp);
	}
}
