/**
 * 
 */
package test;


import javax.transaction.SystemException;
import javax.transaction.TransactionManager;

import com.arjuna.ats.arjuna.AtomicAction;

import TransactionJoinpointTracker.TransactionTracker;
import umjdt.util.Timestamp;

/**
 * @author AnasAlsubh
 *
 */
public aspect testAspect extends TransactionTracker
{
	pointcut testBeginTransaction(): call(* javax..Transaction*+.begin(..));
	pointcut testCommitTransaction(): call(* javax..Transaction*+.commit(..));
	
	pointcut testBegin(): call(* com..AtomicAction*+.begin(..));
	pointcut testCommit(): call(* com..*+.commit(..));
	pointcut testAbort(): call(* com..*+.abort(..));
	pointcut testRollback(): call(* com..*+.rollback(..));
	
	pointcut testLock(): call(* com..*+.setlock(..));
	pointcut testUnlock(): call(* com..*+.releaselock(..));
	
	Timestamp time= new Timestamp();
	int actionNumber =0;

	
	void around(): testBeginTransaction()
	{
		TransactionManager ac =null;
		Object thiss= null;
		Object sing= null;
		
		if(thisJoinPoint.getTarget().toString() !=null)
		{
			 ac = (TransactionManager)thisJoinPoint.getTarget();
		}
		
		if(thisJoinPoint.getThis() !=null)
		{
			thiss= thisJoinPoint.getThis();
		}
		
		if(thisJoinPoint.getSignature() !=null)
		{
			sing= thisJoinPoint.getSignature();
		}
		
		System.out.println("Before Transaction Begin "+ actionNumber + ": "+ ac + " This is " + thiss + " Singature = " + sing +  " Time =" + time.getLocalTime());
		proceed();
		System.out.println("After Transaction Begin "+ actionNumber + ": "+ ac + " This is " + thiss + " Singature = " + sing +  " Time =" + time.getLocalTime());
		
		if(thisJoinPoint.getTarget() instanceof TransactionManager)
		{
			try {
				System.out.println("Current Transaction is "+ ((TransactionManager)thisJoinPoint.getTarget()).getTransaction().toString());
				
				System.out.println("Status of Current Transaction is "+ umjdt.util.Status.stringForm(((TransactionManager)thisJoinPoint.getTarget()).getTransaction().getStatus()));
			} catch (SystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("\n");
	}
	
	void around(): testCommitTransaction()
	{
		TransactionManager ac =null;
		Object thiss= null;
		Object sing= null;
		
		if(thisJoinPoint.getTarget().toString() !=null)
		{
			 ac = (TransactionManager)thisJoinPoint.getTarget();
		}
		
		if(thisJoinPoint.getThis() !=null)
		{
			thiss= thisJoinPoint.getThis();
		}
		
		if(thisJoinPoint.getSignature() !=null)
		{
			sing= thisJoinPoint.getSignature();
		}
		
		System.out.println("Before Transaction Commit "+ actionNumber + ": "+ ac + " This is " + thiss + " Singature = " + sing +  " Time =" + time.getLocalTime());
		
		if(thisJoinPoint.getTarget() instanceof TransactionManager)
		{
			try {
				System.out.println("Current Transaction is "+ ((TransactionManager)thisJoinPoint.getTarget()).getTransaction().toString());
				
				System.out.println("Status of Current Transaction is "+ umjdt.util.Status.stringForm(((TransactionManager)thisJoinPoint.getTarget()).getTransaction().getStatus()));
			} catch (SystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		proceed();
		System.out.println("After Transaction Commit "+ actionNumber + ": "+ ac + " This is " + thiss + " Singature = " + sing +  " Time =" + time.getLocalTime());
		System.out.println("\n");
	}
	
	int around(): testBegin()
	{
		actionNumber = actionNumber +1;
		
		AtomicAction ac =null;
		Object thiss= null;
		Object sing= null;
		
		if(thisJoinPoint.getTarget().toString() !=null)
		{
			 ac = (AtomicAction)thisJoinPoint.getTarget();
		}
		
		if(thisJoinPoint.getThis() !=null)
		{
			thiss= thisJoinPoint.getThis();
		}
		
		if(thisJoinPoint.getSignature() !=null)
		{
			sing= thisJoinPoint.getSignature();
		}
		
		System.out.println("Before Begin "+ actionNumber + ": "+ ac + " This is " + thiss + " Singature = " + sing +  " Time =" + time.getLocalTime());
		int i = proceed();
		System.out.println("After begin "+ actionNumber +":"+ thisJoinPoint.getTarget() + " This is" + thisJoinPoint.getThis() + " Singature = " + thisJoinPoint.getSignature() + "Time =" + time.getLocalTime()+  " Result of Action " + actionNumber +":" + i);		
		return i;
	}
	
	int around(): testCommit()
	{
		AtomicAction ac =null;
		Object thiss= null;
		Object sing= null;
		
		if(thisJoinPoint.getTarget().toString() !=null)
		{
			 ac = (AtomicAction)thisJoinPoint.getTarget();
		}
		
		if(thisJoinPoint.getThis() !=null)
		{
			thiss= thisJoinPoint.getThis();
		}
		
		if(thisJoinPoint.getSignature() !=null)
		{
			sing= thisJoinPoint.getSignature();
		}
		
		System.out.println("Before commit "+ actionNumber + ": "+ ac + " This is " + thiss + " Singature = " + sing +  " Time =" + time.getLocalTime());
		int i = proceed();
		
		System.out.println("After commit "+ actionNumber +": "+ thisJoinPoint.getTarget() + " This is " + thisJoinPoint.getThis() + " Singature = " + thisJoinPoint.getSignature() +  "Time =" + time.getLocalTime() + "Result of Action " + actionNumber +":" + i);
		System.out.println("\n");
		return i;
	}

	
	int around(): testAbort()
	{
		
		AtomicAction ac =null;
		Object thiss= null;
		Object sing= null;
		
		if(thisJoinPoint.getTarget().toString() !=null)
		{
			 ac = (AtomicAction)thisJoinPoint.getTarget();
		}
		
		if(thisJoinPoint.getThis() !=null)
		{
			thiss= thisJoinPoint.getThis();
		}
		
		if(thisJoinPoint.getSignature() !=null)
		{
			sing= thisJoinPoint.getSignature();
		}
		
		System.out.println("Before Abort "+ actionNumber + ": "+ ac + " This is " + thiss + " Singature = " + sing +  " Time =" + time.getLocalTime());
		int i = proceed();
		System.out.println("After Abort "+ actionNumber +": "+ thisJoinPoint.getTarget() + "This is " + thisJoinPoint.getThis() + " Singature = " + thisJoinPoint.getSignature() + "Time =" + time.getLocalTime()+  " Result of Action " + actionNumber +": " + i);
		System.out.println("\n");
		return i;
	}
	
	int around(): testLock()
	{
		Object ac =null;
		Object thiss= null;
		Object sing= null;
		
		if(thisJoinPoint.getTarget().toString() !=null)
		{
			 ac = thisJoinPoint.getTarget();
		}
		
		if(thisJoinPoint.getThis() !=null)
		{
			thiss= thisJoinPoint.getThis();
		}
		
		if(thisJoinPoint.getSignature() !=null)
		{
			sing= thisJoinPoint.getSignature();
		}
		
		System.out.println("Before Set Lock "+ actionNumber + ": "+ ac + " This is " + thiss + " Singature = " + sing +  " Time =" + time.getLocalTime());
		int i = proceed();
		System.out.println("After Set Lock "+ actionNumber +": "+ thisJoinPoint.getTarget() + " This is " + thisJoinPoint.getThis() + " Singature = " + thisJoinPoint.getSignature() + "Time =" + time.getLocalTime()+  " Result of Action " + actionNumber +": " + i);
		return i;
	}
}
