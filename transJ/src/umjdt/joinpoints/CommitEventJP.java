package umjdt.joinpoints;

import java.util.List;
import java.util.Timer;

import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

import org.aspectj.lang.JoinPoint;

import umjdt.concepts.Resource;
import umjdt.concepts.SubTransaction;
import umjdt.concepts.Tid;
import umjdt.concepts.Transaction;
import umjdt.util.BackgroundThread;
import umjdt.util.Timestamp;

public class CommitEventJP extends EndEventJP {
	private Timer timer;
	private Timestamp commitTimestamp;
	private JoinPoint commitJP;

	public CommitEventJP() {
		super();
		this.commitTimestamp = new Timestamp();
		if (super.getThread() != null) {
			super.getThread().stop();
		}
	}

	public CommitEventJP(Tid _tid) {
		super.setTid(_tid);
		this.commitTimestamp = new Timestamp() ;
		super.setEndDemarcate(this);
		if (super.getThread() != null) {
			super.getThread().stop();
		}
	}

	public CommitEventJP(Transaction _transaction) {
		super();
		super.setTransaction(_transaction);
		this.commitTimestamp = new Timestamp() ;
		super.setEndDemarcate(this);
		if (super.getThread() != null) {
			super.getThread().stop();
		}
	}

	public CommitEventJP(Tid _tid, umjdt.concepts.Transaction _transaction,
			TransactionManager _manager, UserTransaction _user, int _timeout,
			int _status, List<SubTransaction> transactionlist,
			List<Resource> resources, Timestamp _endTime,
			BackgroundThread _thread) {
		super(_tid, _transaction, _manager, _user, _timeout, _status,
				transactionlist, resources, _endTime, _thread);

		this.commitTimestamp = new Timestamp() ;
		super.setStatus(_status);
		super.setEndDemarcate(this);
		if (super.getThread() != null) {
			super.getThread().stop();
		}
	}

	@Override
	public Timer getTimer() {
		return timer;
	}

	@Override
	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public Timestamp getCommitTimestamp() {
		return commitTimestamp;
	}

	public void setCommitTimestamp(Timestamp commitTimestamp) {
		this.commitTimestamp = commitTimestamp;
	}

	public JoinPoint getCommitJP() {
		return commitJP;
	}

	public void setCommitJP(JoinPoint commitJP) {
		this.commitJP = commitJP;
	}
}
