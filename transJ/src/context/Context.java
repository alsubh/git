package context;

import java.util.UUID;

import umjdt.joinpoints.TransJP;
import umjdt.util.BackgroundThread;


public class Context 
{
	private UUID contextID;
	private TransJP transJp;
	
	private BackgroundThread contextThread;
	
	public Context()
	{
		initialization();
	}
	
	public Context(TransJP _transJp)
	{
		setTransJp(_transJp);
		initialization();
	}
	
	private void initialization() 
	{
		contextID = UUID.randomUUID();
	}

	public BackgroundThread getContextThread() 
	{
		return contextThread;
	}

	public void setContextThread(BackgroundThread contextThread) 
	{
		this.contextThread = contextThread;
	}

	public TransJP getTransJp() 
	{
		return transJp;
	}

	public void setTransJp(TransJP transJp) 
	{
		this.transJp = transJp;
	}
	
	public boolean occurOn(TransJP _transJp)
	{
		boolean result= false;
		
		return result;
	}
}
