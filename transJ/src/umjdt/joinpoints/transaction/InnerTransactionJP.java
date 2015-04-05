package umjdt.joinpoints.transaction;

import javax.transaction.SystemException;

import umjdt.concepts.Tid;
import umjdt.concepts.Transaction;
import umjdt.joinpoints.AbortEventJP;
import umjdt.joinpoints.BeforeCompletionJP;
import umjdt.joinpoints.BeginEventJP;
import umjdt.joinpoints.CommitEventJP;
import umjdt.joinpoints.TransJP;
import umjdt.util.BackgroundThread;

public class InnerTransactionJP extends TransJP {

	private BeginEventJP beginJp;
	private CommitEventJP commitJp;
	private AbortEventJP abortJp;
	private BeforeCompletionJP beforeCompletionjp;
	private int status;

	public InnerTransactionJP() {
		super.setTransaction(getTransaction());
		super.setTid(getTid());
		super.setStatus(getStatus());
	}

	public InnerTransactionJP(Tid _tid, Transaction _transaction,
			BackgroundThread _thread) {
		super.setTransaction(_transaction);
		super.setTid(_tid);
		super.setThread(_thread);
	}

	public InnerTransactionJP(Tid _tid, Transaction _transaction,
			BackgroundThread _thread, BeginEventJP _beginJp,
			CommitEventJP _commitJp) {
		super.setTransaction(_transaction);
		super.setTid(_tid);
		super.setThread(_thread);
		this.beginJp = _beginJp;
		this.commitJp = _commitJp;
		super.setBeginDemarcate(_beginJp);
		super.setEndDemarcate(_commitJp);
	}

	public InnerTransactionJP(Tid _tid, Transaction _transaction,
			BackgroundThread _thread, BeginEventJP _beginJp,
			AbortEventJP _abortJp) {
		super.setTransaction(_transaction);
		super.setTid(_tid);
		super.setThread(_thread);
		this.beginJp = _beginJp;
		this.abortJp = _abortJp;
		super.setBeginDemarcate(_beginJp);
		super.setEndDemarcate(_abortJp);
	}

	public InnerTransactionJP(TransJP _transJp) throws SystemException {
		super(_transJp);
		super.setTransaction(_transJp.getTransaction());
		super.setTid(_transJp.getTid());
		super.setThread(_transJp.getThread());
		this.beginJp = _transJp.getBeginDemarcate();
		if (_transJp.getEndDemarcate().getClass().equals(AbortEventJP.class))
			this.commitJp = (CommitEventJP) _transJp.getEndDemarcate();
		else if (_transJp.getEndDemarcate().getClass()
				.equals(AbortEventJP.class))
			this.abortJp = (AbortEventJP) _transJp.getEndDemarcate();
	}

	public BeginEventJP getBeginJp() {
		return beginJp;
	}

	public void setBeginJp(BeginEventJP beginJp) {
		this.beginJp = beginJp;
	}

	public CommitEventJP getCommitJp() {
		return commitJp;
	}

	public void setCommitJp(CommitEventJP commitJp) {
		this.commitJp = commitJp;
	}

	public AbortEventJP getAbortJp() {
		return abortJp;
	}

	public void setAbortJp(AbortEventJP abortJp) {
		this.abortJp = abortJp;
	}

	@Override
	public int getStatus() {
		return status;
	}

	@Override
	public void setStatus(int status) {
		this.status = status;
	}

	public BeforeCompletionJP getBeforeCompletionjp() {
		return beforeCompletionjp;
	}

	public void setBeforeCompletionjp(BeforeCompletionJP beforeCompletionjp) {
		this.beforeCompletionjp = beforeCompletionjp;
	}
}
