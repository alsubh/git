package umjdt.concepts;

import java.io.Serializable;
import java.lang.reflect.Method;
import umjdt.util.AccessType;
import umjdt.util.IdNumber;
import umjdt.util.Timestamp;

public class Operation implements Cloneable, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name = "";
    private IdNumber operationNr; // keep unique id for all kinds operation in any thread
	private AccessType type;
	private Method method;
	private Thread thread;
	private Timestamp timestamp;
	
	public Operation()
	{
		this.setOperationNr(IdNumber.Create());
		setThread(Thread.currentThread());
		setTimestamp(new Timestamp());
	}
	
	public Operation(Tid _transactionId)
	{
		setOperationNr(IdNumber.Create(_transactionId));
		setThread(Thread.currentThread());
		setTimestamp(new Timestamp());
	}
	
	public Operation(Tid _transactionId, Method _method)
	{
		setOperationNr(IdNumber.Create(_transactionId));
		setMethod(_method);
		setThread(Thread.currentThread());
		setTimestamp(new Timestamp());
	}
		
	public Operation(Tid _transactionId, short _opNmr)
	{
		setOperationNr(IdNumber.Create(_transactionId, _opNmr));
		setThread(Thread.currentThread());
		setTimestamp(new Timestamp());
	}

	public Operation(Tid _transactionId, String _name, AccessType _type)
	{
		setOperationNr(IdNumber.Create(_transactionId));
		this.setName(_name);
		this.setType(_type);
		setThread(Thread.currentThread());
		setTimestamp(new Timestamp());
	}
	
	public String getName(){
		return this.name;
	}

	public AccessType getType() {
		return type;
	}

	public void setType(AccessType _type) {
		this.type = _type;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public IdNumber getOperationNr() {
		return operationNr;
	}

	public void setOperationNr(IdNumber operationNr) {
		this.operationNr = operationNr;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}

	public Thread getThread() {
		return thread;
	}
	
	public void enlistOperationTo(Transaction _transaction){
		if(this.getThread() == null)
		{
			setThread(Thread.currentThread());
		}
		_transaction.getOperations().add(this);
	}
	// in the context aspect, the thread of operation will be a list of the transaction thread

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

}