package umjdt.concepts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Logger;

import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;
import javax.transaction.xa.Xid;

import umjdt.joinpoints.TransJP;
import umjdt.util.BackgroundThread;
import umjdt.util.Status;
import umjdt.util.ThreadUtil;
import umjdt.util.Timestamp;
import umjdt.util.TransType;
import umjdt.util.TransactionThread;

import com.arjuna.ats.internal.jta.transaction.arjunacore.BaseTransaction;
import com.arjuna.ats.internal.jta.transaction.arjunacore.TransactionImple;

public class Transaction extends TransactionImple implements javax.transaction.Transaction, Serializable {
	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(this.getClass().getName());

	private Tid tid;
	private int status;
	private int timeout;
	private Timestamp timestamp;
	private int transactionType;
	private Transaction parentTransaction;
	private BackgroundThread thread;

	private TransactionManager manager;
	private UserTransaction user;

	private List<Operation> operations = new ArrayList<Operation>();
	private HashMap<Xid, Resource> resources;
	private Hashtable<Xid, SubTransaction> _ChildTransactions;
	private Hashtable<String, Thread> _childThreads;

	public Transaction() {
		super();
		initialization();
	}

	public Transaction(int _timeout) {
		super();
		initialization();
	}

	public Transaction(TransactionManager _manager) {
		super();
		this.manager = _manager;
		initialization();
	}

	public Transaction(UserTransaction _user) {
		super();
		this.user = _user;
		initialization();
	}

	/**
	 * @param timeout
	 */
	private void initialization() {
		// this.multiOperationMap = HashMultimap.create();
		this.resources = new HashMap<Xid, Resource>();
		this._ChildTransactions = new Hashtable<Xid, SubTransaction>();
		this._childThreads = new Hashtable<String, Thread>();
		this.timestamp = new Timestamp(timeout);
		addThread();
	}

	/**
	 * Register the current thread with the transaction. This operation is not
	 * affected by the state of the transaction.
	 */
	public boolean addThread() {
		return addThread(TransactionThread.currentTransaction());
	}

	public boolean addThread(Transaction _transaction) {
		if (_transaction != null) {
			TransactionThread.pushTransaction(this);
			return true;
		}
		return false;
	}

	/**
	 * Remove a child transaction.
	 */
	public final boolean removeChildTransaction(Transaction trans) {
		if (trans == null)
			return false;

		boolean result = false;

		criticalStart();

		synchronized (this) {
			if (_ChildTransactions != null) {
				_ChildTransactions.remove(trans.getTId());
				result = true;
			}
		}
		criticalEnd();
		return result;
	}

	/**
	 * @return the number of threads associated with this transaction.
	 * 
	 */
	public final int activeThreads() {
		if (_childThreads != null)
			return _childThreads.size();
		else
			return 0;
	}

	/**
	 * @return the TID that the transaction's intentions list will be saved
	 *         under.
	 */
	public Xid getSavingId() {
		return getTId();
	}

	@Override
	public String toString() {
		return new String("Transaction: " + getTId() + " status: "
				+ Status.stringForm(status));
	}

	/**
	 * The following function returns the TID of the top-level transaction. If
	 * this is the top-level transaction then it is equivalent to calling
	 * getId().
	 * 
	 */

	public final Xid topLevelActionId() {
		Transaction root = this;

		while (root.parent() != null)
			root = root.parent();

		return root.getTId();
	}

	/**
	 * @return a reference to the top-level transaction. If this is the
	 *         top-level transaction then a reference to itself will be
	 *         returned.
	 */

	public final Transaction topLevelTransaction() {
		Transaction root = this;

		while (root.parent() != null)
			root = root.parent();

		return root;
	}

	/**
	 * @return a reference to the parent Transaction
	 */

	public final Transaction parent() {
		if (transactionType == TransType.NESTED)
			return parentTransaction;
		else
			return null;
	}

	/**
	 * Add the current thread to the list of threads associated with this
	 * transaction.
	 */
	public final boolean addChildThread() {
		return addChildThread(Thread.currentThread());
	}

	/**
	 * Add the specified thread to the list of threads associated with this
	 * transaction.
	 */
	public final boolean addChildThread(Thread t) {
		if (t == null)
			return false;

		boolean result = false;

		criticalStart();

		synchronized (this) {
			if (status <= Status.STATUS_ROLLING_BACK) {
				if (_childThreads == null)
					_childThreads = new Hashtable<String, Thread>();
				_childThreads.put(ThreadUtil.getThreadId(t), t); // makes sure
																	// so we
																	// don't get
																	// duplicates

				result = true;
			}
		}
		criticalEnd();
		return result;
	}

	/**
	 * Remove a child thread. The current thread is removed.
	 */
	public final boolean removeChildThread() {
		return removeChildThread(ThreadUtil.getThreadId());
	}

	/**
	 * Defines the start of a critical region by setting the critical flag. If
	 * the signal handler is called the class variable abortAndExit is set. The
	 * value of this variable is checked in the corresponding operation to end
	 * the critical region.
	 */

	protected final void criticalStart() {
		// _lock.lock();
	}

	/**
	 * Defines the end of a critical region by resetting the critical flag. If
	 * the signal handler is called the class variable abortAndExit is set. The
	 * value of this variable is checked when ending the critical region.
	 */

	protected final void criticalEnd() {
		// _lock.unlock();
	}

	public final boolean removeChildThread(String threadId) {

		if (threadId == null)
			return false;

		boolean result = false;

		criticalStart();

		synchronized (this) {
			if (_childThreads != null) {
				_childThreads.remove(threadId);
				result = true;
			}
		}

		criticalEnd();

		return result;
	}

	/**
	 * Add a new child transaction to the transaction.
	 */
	public final boolean addChildTransaction(SubTransaction trans) {
		if (trans == null)
			return false;

		boolean result = false;
		criticalStart();
		synchronized (this) {
			/*
			 * Must be <= as we sometimes need to do processing during commit
			 * phase.
			 */

			if (status <= Status.STATUS_ROLLEDBACK) {
				if (_ChildTransactions == null)
					_ChildTransactions = new Hashtable<Xid, SubTransaction>();
				_ChildTransactions.put(trans.getTId(), trans);
				result = true;
			}
		}

		criticalEnd();
		return result;
	}

	public final boolean isAncestor(Xid ancestor) {
		boolean res = false;

		if (getTId().equals(ancestor)) /* actions are their own ancestors */
			res = true;
		else {
			if ((parentTransaction != null)
					&& (transactionType != TransType.TOP_LEVEL))
				res = parentTransaction.isAncestor(ancestor);
		}
		return res;
	}

	@Override
	public boolean equals(Object _obj) {
		Transaction tempTransaction = (Transaction) _obj;
		if (tempTransaction.getTId().equals(this.getTId()))
			return true;
		return false;
	}

	public final int typeOfTransaction() {
		return transactionType;
	}

	public Xid getTId() {
		return tid;
	}

	public void setTId(Tid _id) {
		this.tid = _id;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public List<Operation> getOperations() {
		return operations;
	}

	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public void setResources(HashMap resources) {
		this.resources = resources;
	}

	public int getTransactionType() {
		if (this.getChildTransactions() > 1)
			return TransType.NESTED;
		return TransType.FLAT;
	}

	public int getChildTransactions() {
		int number = this._ChildTransactions.size();
		return number;
	}

	public boolean occuredIn(TransJP _transjp) {
		boolean result = false;
		if (_transjp.getTid().equals(getTId())) {
			result = true;
		}
		return result;
	}

	public Tid getTid() {
		return tid;
	}

	public void setTid(Tid tid) {
		this.tid = tid;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Transaction getParentTransaction() {
		return parentTransaction;
	}

	public void setParentTransaction(Transaction parentTransaction) {
		this.parentTransaction = parentTransaction;
	}

	public TransactionManager getManager() {
		return manager;
	}

	public void setManager(TransactionManager manager) {
		this.manager = manager;
	}

	public UserTransaction getUser() {
		return user;
	}

	public void setUser(UserTransaction user) {
		this.user = user;
	}

	public Hashtable<Xid, SubTransaction> get_ChildTransactions() {
		return _ChildTransactions;
	}

	public void set_ChildTransactions(
			Hashtable<Xid, SubTransaction> _ChildTransactions) {
		this._ChildTransactions = _ChildTransactions;
	}

	public Hashtable<String, Thread> get_childThreads() {
		return _childThreads;
	}

	public void set_childThreads(Hashtable<String, Thread> _childThreads) {
		this._childThreads = _childThreads;
	}

	public void setTransactionType(int transactionType) {
		this.transactionType = transactionType;
	}

	public BackgroundThread getTransactionThread() {
		return thread;
	}

	public void setTransactionThread(BackgroundThread transactionThread) {
		this.thread = transactionThread;
	}

}