/**
 * 
 */
package umjdt.joinpoints;

import java.util.Timer;

import org.aspectj.lang.JoinPoint;

import umjdt.concepts.Tid;
import umjdt.concepts.Transaction;
import umjdt.util.Timestamp;

/**
 * @author AnasAlsubh
 * 
 */
public class AfterCompletionJP {

	private Tid tid;
	private Transaction transaction;
	private JoinPoint afterCompletionJp;
	private int status;
	private final Timestamp timestamp;
	private Timer timer;

	public AfterCompletionJP() {
		timestamp = new Timestamp() ;
	}

	public AfterCompletionJP(Tid _tid) {
		this.setTid(_tid);
		timestamp = new Timestamp() ;
	}

	public AfterCompletionJP(Transaction _transaction) {
		this.setTransaction(_transaction);
		timestamp = new Timestamp() ;
	}

	public Tid getTid() {
		return tid;
	}

	public void setTid(Tid tid) {
		this.tid = tid;
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

	public JoinPoint getAfterCompletionJp() {
		return afterCompletionJp;
	}

	public void setAfterCompletionJp(JoinPoint afterCompletionJp) {
		this.afterCompletionJp = afterCompletionJp;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}
}
