package context;

import java.util.List;
import java.util.UUID;

import umjdt.concepts.Operation;
import umjdt.concepts.Resource;
import umjdt.joinpoints.TransJP;
import umjdt.util.BackgroundThread;
import umjdt.util.Participant;
import umjdt.util.Status;


public class TransactionContext extends Context
{
	private UUID tcid;
	private TransJP transactionJp;
	private List<Resource> resources;
	private List<Operation> operations;
	// Transaction Manager, Resource Manager, or coordinator
	private Participant participant;
	private Status status;
		
	// Thread of the joinpoints 
	private BackgroundThread transactionContextThread;
	
	public TransactionContext() 
	{
		super();
		this.setCid(UUID.randomUUID());
	}

	public TransactionContext(TransJP _transJp)
	{
		super(_transJp);
		this.setCid(UUID.randomUUID());
	}
	
	public TransactionContext(UUID _tcid, TransJP _transactionJp,
			List<Resource> _resources, List<Operation> _operations, Participant _participant)
	{
		super();
		this.setCid(UUID.randomUUID());
		this.setTransactionJp(_transactionJp);
		this.setResources(_resources);
		this.setOperations(_operations);
		this.setParticipant(_participant);
	}

	public UUID getCid() 
	{
		return tcid;
	}

	public void setCid(UUID cid) 
	{
		this.tcid = cid;
	}

	public TransJP getTransactionJp() 
	{
		return transactionJp;
	}

	public void setTransactionJp(TransJP transactionJp) 
	{
		this.transactionJp = transactionJp;
	}

	public List<Resource> getResources() 
	{
		return resources;
	}

	public void setResources(List<Resource> resources) 
	{
		this.resources = resources;
	}

	public Participant getParticipant() 
	{
		return participant;
	}

	public void setParticipant(Participant participant) 
	{
		this.participant = participant;
	}

	public List<Operation> getOperations() {
		return operations;
	}

	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}
	
	public BackgroundThread getTransactionContextThread() {
		return transactionContextThread;
	}

	public void setTransactionContextThread(BackgroundThread transactionContextThread) {
		this.transactionContextThread = transactionContextThread;
	}
	
	public boolean occurOn(TransJP _transactionJp)
	{
		boolean result= false;
		if(_transactionJp.equals(_transactionJp))
		{
			result= true;
		}
		return result;
	}
}
