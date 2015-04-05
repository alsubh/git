package umjdt.joinpoints;

import java.util.logging.Logger;

import javax.transaction.SystemException;
import javax.transaction.TransactionManager;
import javax.transaction.xa.Xid;

import com.arjuna.ats.internal.jta.transaction.arjunacore.*;
import com.arjuna.ats.jta.UserTransaction;

import umjdt.concepts.Tid;
import umjdt.util.BackgroundThread;
import context.Context;

public class TransJP extends EventJP {
	Logger logger = Logger.getLogger(TransJP.class.toString());

	//private TID tid;
	private Tid tid;
	private int status;
	private BeginEventJP beginDemarcate;
	private EndEventJP endDemarcate;
	private BackgroundThread thread;
	private Object manager;
	private TransactionImple transaction;
	

	public TransJP() {
		super();
		this.thread = new BackgroundThread(Thread.currentThread());
	}
	
	public TransJP(Object manager) throws SystemException { //userTransaction or TransactionManager
		super();
		this.thread = new BackgroundThread(Thread.currentThread());
		this.setManager(manager);
	}

	public TransJP(Tid _tid) {
		super();
		this.tid = _tid;
		this.thread = new BackgroundThread(Thread.currentThread());
	}

	public TransJP(TransactionImple _transaction) {
		this.transaction = _transaction;
		this.tid = (Tid) _transaction.getTxId();
		this.thread = new BackgroundThread(Thread.currentThread());
	}

	public TransJP(TransJP _transjp) throws SystemException {
		//this.transaction = _transjp.getTransaction();
		//this.tid = transaction.getTid();
		this.status = transaction.getStatus();
		this.thread = _transjp.getThread();
	}

	public boolean occurredIn(Context _context, TransJP _tJP) {
		boolean result = false;
		if (_context.getTransJp().equals(_tJP)) {
			result = true;
		}
		return result;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public BeginEventJP getBeginDemarcate() {
		return beginDemarcate;
	}

	public void setBeginDemarcate(BeginEventJP beginDemarcate) {
		this.beginDemarcate = beginDemarcate;
	}

	public EndEventJP getEndDemarcate() {
		return endDemarcate;
	}

	public void setEndDemarcate(EndEventJP endDemarcate) {
		this.endDemarcate = endDemarcate;
	}

	public BackgroundThread getThread() {
		return thread;
	}

	public void setThread(BackgroundThread thread) {
		this.thread = thread;
	}

	
	public Tid getTid() {
		return tid;
	}

	public void setTid(Tid tid) {
		this.tid = tid;
	}
	
	/**
	 * @return the transaction
	 */
	public TransactionImple getTransaction() {
		return transaction;
	}

	/**
	 * @param transaction the transaction to set
	 */
	public void setTransaction(TransactionImple transaction) {
		this.transaction = transaction;
	}

	/**
	 * @return the manager
	 */
	public Object getManager() {
		return manager;
	}

	/**
	 * @param manager the manager to set
	 * @throws SystemException 
	 */
	public void setManager(Object manager) throws SystemException {
		if(manager instanceof TransactionManager)
		{
			this.manager = (TransactionManager) manager;
			this.setTransaction((TransactionImple) (((TransactionManager) manager).getTransaction()));
		}
		else if (manager instanceof UserTransaction)
		{
			this.manager = (UserTransaction) manager;
			this.transaction= null;
			
		}
	}
}
