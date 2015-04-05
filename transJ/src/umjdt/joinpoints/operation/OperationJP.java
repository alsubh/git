package umjdt.joinpoints.operation;

import java.lang.management.LockInfo;

import test.operationNum;
import umjdt.concepts.Lock;
import umjdt.concepts.Operation;
import umjdt.concepts.Resource;
import umjdt.concepts.Tid;
import umjdt.joinpoints.TransJP;
import umjdt.util.AccessType;
import umjdt.util.Timestamp;

public class OperationJP extends TransJP
{
	private Tid tid;
	private Operation operation;
	private Object [] arguments;
	private AccessType accessType;
	private Timestamp timestamp;
	private Lock lock; // type of the lock 
	private Resource resource; // path of the resource
	
	
	
	public OperationJP()
	{
		super();
	}
	
	public OperationJP(TransJP _transjp)
	{
		super();
		this.tid= _transjp.getTid();
		setTid(getTid());
		setTransaction(_transjp.getTransaction());
		setBeginDemarcate(_transjp.getBeginDemarcate());
		setEndDemarcate(_transjp.getEndDemarcate());
		setThread(_transjp.getThread());
		setStatus(_transjp.getStatus());
		setTimeout(_transjp.getTimeout());
		this.timestamp = new Timestamp();
		setTimestamp(timestamp);
	}
	
	public OperationJP(Tid _tid, Operation _operation)
	{
		super(_tid);
		this.setOperation(_operation);
		this.timestamp = new Timestamp();
	}
	
	public OperationJP(Tid tid, Operation operation, Object[] arguments, AccessType accessType) 
	{
		super();
		this.tid = tid;
		this.operation = operation;
		this.arguments = arguments;
		this.accessType = accessType;
		this.timestamp = new Timestamp();
	}

	public OperationJP(Tid tid, Operation operation, Object[] arguments, AccessType accessType, Resource resource) 
	{
		super();
		this.tid = tid;
		this.operation = operation;
		this.arguments = arguments;
		this.accessType = accessType;
		this.resource = resource;
		this.timestamp = new Timestamp();
	}
	
	public Tid getTid() {
		return tid;
	}

	public void setTid(Tid tid) {
		this.tid = tid;
	}

	public Object[] getArguments() {
		return arguments;
	}

	public void setArguments(Object[] arguments) {
		this.arguments = arguments;
	}

	public AccessType getAccessType() {
		return accessType;
	}

	public void setAccessType(AccessType accessType) {
		this.accessType = accessType;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public Lock getLock() {
		return lock;
	}

	public void setLock(Lock lock) {
		this.lock = lock;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
}
