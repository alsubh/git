/**
 * 
 */
package TransactionJoinpointTracker;

import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

import umjdt.concepts.Resource;
import umjdt.joinpoints.AbortResourceEventJP;
import umjdt.joinpoints.CommitResourceEventJP;
import umjdt.joinpoints.EndHoldingResourceEventJP;
import umjdt.joinpoints.StartHoldingResourceEventJP;


/**
 * @author AnasAlsubh
 *
 */
public aspect ResourceHoldingJoinpointTracker
{
	/**
	 * The resource manager is responsible for associating the global transaction
	 * to all work performed on its data between the start and end method invocations.
	 * @param xid: A global transaction identifier to be associated with the resource.
	 * @param target (resource)
	 * @param flags One of TMSUCCESS, TMFAIL, or TMSUSPEND.
	 */
	private StartHoldingResourceEventJP startHoldEventjp= null;
	private EndHoldingResourceEventJP endHoldEventjp =null;
	
	private CommitResourceEventJP commitResourceEventjp= null;
	private AbortResourceEventJP abortResourceEventjp = null;
	
	/*
	 * Starts work on behalf of a transaction branch specified in xid.
	 * If TMJOIN is specified, the start applies to joining a transaction previously seen by the RM.
	 * @param xid A global transaction identifier to be associated with the resource.
	 * @param flags One of TMNOFLAGS, TMJOIN, or TMRESUME.
	 */
	pointcut StartHoldingResource(Xid xid, XAResource resource): 
		execution(* javax..XAResource+.start(..)) && target(resource) && args(xid);
		//javax.transaction.xa.XAResource+
	
	/**
	 * Ends the work performed on behalf of a transaction branch. 
	 * The resource manager disassociates the XA resource from the transaction branch specified and lets the transaction complete.
	 * @param xid
	 * @param resource
	 */
	pointcut EndHoldingResource(XAResource _resource): 
		execution(* javax..XAResource+.end(..)) && target(_resource);
	
	void around(Xid _xid, XAResource _resource): StartHoldingResource(_xid, _resource)
	{
		try
		{
			proceed(_xid, _resource);
			startHoldEventjp = new StartHoldingResourceEventJP(_xid, _resource);
			StartResourceJoinPoint(startHoldEventjp);
		} 
		catch (XAException e) {
			e.printStackTrace();
		}
	}
	
	void around(XAResource _resource) throws XAException: EndHoldingResource(_resource)
	{
		proceed(_resource);
		endHoldEventjp = new EndHoldingResourceEventJP();
		endHoldEventjp.setEndResourceJP(thisJoinPoint);
		for(Object o : thisJoinPoint.getArgs())
		{
			if(o instanceof XAResource)
			{
				endHoldEventjp.setResource(new Resource(_resource));
			}
			else if(o instanceof Integer)
			{
				endHoldEventjp.setFlag((int)o);
			}
		}
		
		EndResourceJoinPoint(endHoldEventjp);
	}
		
	// Hold
    public void StartResourceJoinPoint(StartHoldingResourceEventJP _startResourceJp)
    {}

    // UnHold
    public void EndResourceJoinPoint(EndHoldingResourceEventJP _endResourceJp)
    {}
    
    
    /***
     * Commits the global transaction specified by xid.
     * @param xid A global transaction identifier
     * @pram boolean (onePhase If true, the resource manager should use a one-phase commit protocol to commit the work done on behalf of xid.)
     * @param resource(target)
     */
    pointcut CommitResource(Xid xid, XAResource resource): execution(* javax.transaction.xa.XAResource+.commit(..)) && args(xid) && target(resource);
    /**
     * Informs the resource manager to roll back work done on 
     * behalf of a transaction branch.
     * @param xid
     * @param resource
     */
    pointcut AbortResource(Xid xid): execution(* javax.transaction.xa.XAResource+.rollback(..)) && args(xid) && target(XAResource);

    void around(Xid _xid, XAResource _resource): CommitResource(_xid, _resource){
    	try 
    	{
    		proceed(_xid, _resource);
			commitResourceEventjp= new  CommitResourceEventJP(_xid, _resource);
			commitResourceEventjp.setStatus(javax.transaction.Status.STATUS_COMMITTED);
			commitResourceEventjp.setCommitResourceJP(thisJoinPoint);
			CommitResourceJoinPoint(commitResourceEventjp);
		}
    	catch (XAException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    void around(Xid _xid) throws XAException: AbortResource(_xid){
    	proceed(_xid);
    	abortResourceEventjp = new AbortResourceEventJP();
		abortResourceEventjp.setAbortResourceJP(thisJoinPoint);
		commitResourceEventjp.setStatus(javax.transaction.Status.STATUS_ROLLEDBACK);

		for(Object o : thisJoinPoint.getArgs())
		{
			if(o instanceof XAResource)
			{
				abortResourceEventjp.setResource(new Resource((XAResource)thisJoinPoint.getTarget(), _xid));
			}
		}
	
		AbortResourceJoinPoint(abortResourceEventjp);
    }
    
    public void CommitResourceJoinPoint(CommitResourceEventJP _commitResourceJp){}

    public void AbortResourceJoinPoint(AbortResourceEventJP _abortResourceJp){}	
}
