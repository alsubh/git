/**
 * 
 */
package umjdt.joinpoints;

import java.util.Timer;

import org.aspectj.lang.JoinPoint;

import umjdt.concepts.Resource;
import umjdt.concepts.Tid;
import umjdt.concepts.Transaction;
import umjdt.util.AccessType;
import umjdt.util.Timestamp;

/**
 * @author AnasAlsubh
 * 
 */
public class EnlistResourceEventJP {

	private Timer timer;
	private Timestamp enlistResourceTimestamp;
	private JoinPoint enlistResourceJP;
	private Tid tid;
	private Resource resource;
	private Transaction transaction;
	private AccessType accessKind;
	private int state;

	public EnlistResourceEventJP() {
		enlistResourceTimestamp = new Timestamp() ;
	}

	public EnlistResourceEventJP(Tid _tid) {
		enlistResourceTimestamp = new Timestamp() ;
		this.tid = _tid;
	}

	public EnlistResourceEventJP(Transaction _transaction) {
		enlistResourceTimestamp = new Timestamp() ;
		this.setTransaction(_transaction);
	}

	public EnlistResourceEventJP(Tid _tid, Resource _resource) {
		enlistResourceTimestamp = new Timestamp() ;
		this.setTid(_tid);
		this.setResource(_resource);
	}

	public EnlistResourceEventJP(Tid _tid, Resource _resource,
			AccessType lockType) {
		enlistResourceTimestamp = new Timestamp() ;
		this.setTid(_tid);
		this.setResource(_resource);
		this.accessKind = lockType;
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public Timestamp getEnlistResourceTimestamp() {
		return enlistResourceTimestamp;
	}

	public void setEnlistResourceTimestamp(Timestamp enlistResourceTimestamp) {
		this.enlistResourceTimestamp = enlistResourceTimestamp;
	}

	public JoinPoint getEnlistResourceJP() {
		return enlistResourceJP;
	}

	public void setEnlistResourceJP(JoinPoint enlistResourceJP) {
		this.enlistResourceJP = enlistResourceJP;
	}

	public Tid getTid() {
		return tid;
	}

	public void setTid(Tid tid) {
		this.tid = tid;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
}
