/**
 * 
 */
package umjdt.joinpoints;

import org.aspectj.lang.JoinPoint;

import umjdt.concepts.Tid;
import umjdt.concepts.Transaction;
import umjdt.util.Timestamp;

/**
 * @author AnasAlsubh
 * 
 */
public class BeforeCompletionJP {
	private Tid tid;
	private Transaction transaction;
	private JoinPoint beforeCompletionJp;
	private Timestamp timestamp;

	public BeforeCompletionJP() {
		setTimestamp(new Timestamp() );
	}

	public BeforeCompletionJP(Tid _tid) {
		this.setTid(_tid);
		setTimestamp(new Timestamp() );
	}

	public BeforeCompletionJP(Transaction _transaction) {
		this.setTransaction(_transaction);
		setTimestamp(new Timestamp() );
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
		return beforeCompletionJp;
	}

	public void setAfterCompletionJp(JoinPoint afterCompletionJp) {
		this.beforeCompletionJp = afterCompletionJp;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
}
