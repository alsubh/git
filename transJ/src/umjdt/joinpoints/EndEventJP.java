package umjdt.joinpoints;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.logging.Level;

import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

import org.aspectj.lang.JoinPoint;

import umjdt.concepts.Resource;
import umjdt.concepts.SubTransaction;
import umjdt.concepts.Tid;
import umjdt.concepts.Transaction;
import umjdt.util.BackgroundThread;
import umjdt.util.Constants;
import umjdt.util.Timestamp;

public class EndEventJP extends TransJP {
	private TransactionManager manager;
	private UserTransaction user;
	private Timestamp endTime;
	private int timeout;
	private int status;
	private List<SubTransaction> subtransactions;
	private List<Resource> resources = new ArrayList<Resource>();
	private JoinPoint endJP;

	public EndEventJP() {
		super();
		setTimeout((int) Constants.TimeToWait);
		super.getThread().stop();
	}

	public EndEventJP(Tid _tid) {
		super();
		super.setTid(_tid);
		super.getThread().stop();
	}

	public EndEventJP(Tid _tid, Transaction _transaction,
			TransactionManager _manager, UserTransaction _user, int _timeout,
			int _status, List<SubTransaction> transactionlist,
			List<Resource> resources, Timestamp _endTime,
			BackgroundThread _thread) {
		super.setTransaction(_transaction);
		super.setTid(_tid);
		this.manager = _manager;
		this.user = _user;
		this.timeout = _timeout;
		this.status = _status;
		this.subtransactions = transactionlist;
		this.resources = resources;
		this.endTime = _endTime;

		if (_thread != null) {
			super.setThread(_thread);
			super.getThread().stop();
		} else {
			Thread.currentThread().stop();
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

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	@Override
	public int getTimeout() {
		return timeout;
	}

	@Override
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	@Override
	public int getStatus() {
		return status;
	}

	@Override
	public void setStatus(int status) {
		this.status = status;
	}

	public List<SubTransaction> getTransactions() {
		return subtransactions;
	}

	public void setTransactions(List<SubTransaction> _subtransactions) {
		this.subtransactions = _subtransactions;
	}

	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}

	public JoinPoint getEndJP() {
		return endJP;
	}

	public void setEndJP(JoinPoint endJP) {
		this.endJP = endJP;
	}

	class BeginTask extends TimerTask {
		@Override
		public void run() {
			logger.log(Level.INFO, BeginTask.class.toString());
			System.out.println("End Transaction!");
			// System.exit(0); //Stops everything
		}
	}
}