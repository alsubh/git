package baseaspect;

import org.apache.log4j.Logger;

import umjdt.joinpoints.AbortEventJP;
import umjdt.joinpoints.BeginEventJP;
import umjdt.joinpoints.CommitEventJP;
import umjdt.joinpoints.DelistResourceEventJP;
import umjdt.joinpoints.EnlistResourceEventJP;
import umjdt.joinpoints.LockingJP;

import TransactionJoinpointTracker.TransactionTracker;


public abstract aspect TransactionAspect 
{
	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(TransactionAspect.class);
	
	public pointcut TransactionBegin(BeginEventJP _beginJp) : 
		within(TransationTracker) && 
		execution(void TransactionTracker.BeginJoinPoint(..)) && args(_beginJp, ..);

	public pointcut TransactionCommit(CommitEventJP _commitJp) : 
		within(TransationTracker) && 
		execution(void TransactionTracker.CommitJoinPoint(..)) && args(_commitJp, ..);
	
	public pointcut TransactionAbort(AbortEventJP _abortJp) : 
		within(TransationTracker) && 
		execution(void TransactionTracker.AbortJoinPoint(..)) && args(_abortJp, ..);

	public pointcut TransactionLock(EnlistResourceEventJP _enlistJp) : 
		within(TransationTracker) && 
		execution(void TransactionTracker.LockJoinPoint(..)) && args(_enlistJp, ..);
	
	public pointcut TransactionUnlock(DelistResourceEventJP _delistJp) : 
		within(TransationTracker) && 
		execution(void TransactionTracker.UnlockJoinPoint(..)) && args(_delistJp, ..);
}