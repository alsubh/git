/**
 * 
 */
package umjdt.joinpoints;

import javax.transaction.SystemException;

import org.aspectj.lang.JoinPoint;

import umjdt.concepts.Lock;
import umjdt.concepts.Resource;
import umjdt.concepts.Tid;
import umjdt.concepts.Transaction;
import umjdt.util.AccessType;

/**
 * @author AnasAlsubh
 * 
 */
public class LockingJP extends TransJP {
	private final Lock lock = null;
	private Resource resource = null;
	private AccessType lockType;
	private JoinPoint lockingJP;
	private int state;

	public LockingJP() {
		super();
	}

	public LockingJP(Tid _tid) {
		super(_tid);
	}

	public LockingJP(Transaction _transaction) {
		super(_transaction);
	}

	public LockingJP(Tid _tid, Resource _resource) {
		super();
		super.setTid(_tid);
		this.setResource(_resource);
	}

	public LockingJP(Tid _tid, Resource resource, AccessType lockType) {
		super();
		this.setTid(_tid);
		this.setResource(resource);
		this.setLockType(lockType);
	}

	public LockingJP(TransJP _transjp) throws SystemException {
		super(_transjp);
	}

	public Lock getLock() {
		return lock;
	}

	public JoinPoint getLockingJP() {
		return lockingJP;
	}

	public void setLockingJP(JoinPoint lockingJP) {
		this.lockingJP = lockingJP;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public AccessType getLockType() {
		return lockType;
	}

	public void setLockType(AccessType lockType) {
		this.lockType = lockType;
	}
}
