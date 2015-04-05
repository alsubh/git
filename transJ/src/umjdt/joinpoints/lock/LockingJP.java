package umjdt.joinpoints.lock;

import javax.transaction.SystemException;

import umjdt.concepts.Lock;
import umjdt.concepts.Operation;
import umjdt.concepts.Resource;
import umjdt.concepts.Tid;
import umjdt.concepts.Transaction;
import umjdt.joinpoints.EndHoldingResourceEventJP;
import umjdt.joinpoints.StartHoldingResourceEventJP;
import umjdt.joinpoints.TransJP;
import umjdt.util.AccessType;

public class LockingJP extends TransJP {
	private Tid tid;
	private Lock lock;
	private Resource resource;
	private Operation operation;
	private AccessType lockType;
	private StartHoldingResourceEventJP startHoldingResourceEventJP;
	private EndHoldingResourceEventJP endHoldingResourceEventJP;

	public LockingJP(Tid _tid) {
		super(_tid);
	}

	public LockingJP(Transaction _transaction) {
		super(_transaction);
	}

	public LockingJP(TransJP _transjp) throws SystemException {
		super(_transjp);
	}

	public LockingJP(Lock _lock, Tid _tid, Resource _resource,
			AccessType _lockType) {
		super();
		this.lock = _lock;
		this.tid = _tid;
		this.resource = _resource;
		this.lockType = _lockType;
	}

	public LockingJP(Tid _tid, AccessType _lockType,
			StartHoldingResourceEventJP _startHoldingResourceEventJP,
			EndHoldingResourceEventJP _endHoldingResourceEventJP) {
		super();
		this.tid = _tid;
		this.lockType = _lockType;
		this.startHoldingResourceEventJP = _startHoldingResourceEventJP;
		this.endHoldingResourceEventJP = _endHoldingResourceEventJP;
	}

	public Lock get_lock() {
		return lock;
	}

	public void set_lock(Lock _lock) {
		this.lock = _lock;
	}

	@Override
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

	public AccessType getLockType() {
		return lockType;
	}

	public void setLockType(AccessType lockType) {
		this.lockType = lockType;
	}

	public StartHoldingResourceEventJP getStartHoldingResourceEventJP() {
		return startHoldingResourceEventJP;
	}

	public void setStartHoldingResourceEventJP(
			StartHoldingResourceEventJP startHoldingResourceEventJP) {
		this.startHoldingResourceEventJP = startHoldingResourceEventJP;
	}

	public EndHoldingResourceEventJP getEndHoldingResourceEventJP() {
		return endHoldingResourceEventJP;
	}

	public void setEndHoldingResourceEventJP(
			EndHoldingResourceEventJP endHoldingResourceEventJP) {
		this.endHoldingResourceEventJP = endHoldingResourceEventJP;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}
}
