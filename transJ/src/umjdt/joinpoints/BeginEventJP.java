package umjdt.joinpoints;

import java.util.TimerTask;
import java.util.logging.Level;

import javax.transaction.SystemException;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

import org.aspectj.lang.JoinPoint;

import umjdt.concepts.Tid;
import umjdt.concepts.Transaction;
import umjdt.util.BackgroundThread;
import umjdt.util.Constants;
import umjdt.util.Timestamp;

public class BeginEventJP extends TransJP {

	private TransactionManager manager;
	private UserTransaction user;

	private Timestamp beginTime;
	private int timeout;
	private Tid tid;
	private JoinPoint beginJP;

	public BeginEventJP() {
		super();
		setTimeout((int) Constants.TimeToWait);
		startThread();
	}

	public BeginEventJP(int _timeout) {
		super();
		super.setTimeout(_timeout);
		startThread();
	}

	public BeginEventJP(Tid _tid) {
		super(_tid);
		startThread();
	}

	public BeginEventJP(Transaction _transaction) {
		super(_transaction);
		startThread();
	}

	public BeginEventJP(TransJP _transjp) throws SystemException {
		super(_transjp);
		startThread();
	}

	public BeginEventJP(Tid _tid, Transaction _transaction,
			TransactionManager _manager, UserTransaction _user,
			BackgroundThread _thread, int _timeout) {
		super();
		this.tid = _tid;
		super.setTransaction(_transaction);
		this.manager = _manager;
		this.user = _user;
		this.setUser(_user);
		this.beginTime = new Timestamp() ;
		super.setBeginDemarcate(this);
		super.setThread(_thread);
		startThread();
	}

	private void startThread() {
		if (getThread() == null) {
			super.setThread(new BackgroundThread(Thread.currentThread()));
			super.getThread().start();
		} else {
			// Start new thread
			super.getThread().start();
		}
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

	public Timestamp getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Timestamp beginTime) {
		this.beginTime = beginTime;
	}

	@Override
	public int getTimeout() {
		return timeout;
	}

	@Override
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public Tid getTid() {
		return tid;
	}

	public void setTid(Tid tid) {
		this.tid = tid;
	}

	public JoinPoint getBeginJP() {
		return beginJP;
	}

	public void setBeginJP(JoinPoint beginJP) {
		this.beginJP = beginJP;
	}

	class BeginTask extends TimerTask {
		@Override
		public void run() {
			logger.log(Level.INFO, BeginTask.class.toString());
			System.out.println("Time's up!");
			// System.exit(0); //Stops everything
		}
	}
}
