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

public class AbortEventJP extends EndEventJP {
	private Timer timer;
	private Timestamp abortTimestamp;
	private JoinPoint abortJP;

	public AbortEventJP() {
		super();
		this.abortTimestamp = new Timestamp();
		if (super.getThread() != null) {
			super.getThread().stop();
		}
	}

	public AbortEventJP(Tid _tid) {
		super.setTid(_tid);
		this.abortTimestamp = new Timestamp() ;
		super.setEndDemarcate(this);
		if (super.getThread() != null) {
			super.getThread().stop();
		}
	}

	public AbortEventJP(Transaction _transaction) {
		super();
		super.setTransaction(_transaction);
		this.abortTimestamp = new Timestamp() ;
		super.setEndDemarcate(this);
		if (super.getThread() != null) {
			super.getThread().stop();
		}
	}

	public AbortEventJP(Tid _tid, umjdt.concepts.Transaction _transaction,
			TransactionManager _manager, UserTransaction _user, int _timeout,
			int _status, List<SubTransaction> transactionlist,
			List<Resource> resources, Timestamp _endTime,
			BackgroundThread _thread) {
		super(_tid, _transaction, _manager, _user, _timeout, _status,
				transactionlist, resources, _endTime, _thread);

		this.abortTimestamp = new Timestamp() ;
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

	public Timestamp getabortTimestamp() {
		return abortTimestamp;
	}

	public void setabortTimestamp(Timestamp abortTimestamp) {
		this.abortTimestamp = abortTimestamp;
	}

	public JoinPoint getAbortJP() {
		return abortJP;
	}

	public void setAbortJP(JoinPoint abortJP) {
		this.abortJP = abortJP;
	}
}
