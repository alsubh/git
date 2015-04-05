package umjdt.util;

public class BackgroundThread implements Runnable 
{
	private Thread thisThread;
	
	public BackgroundThread(){}
	
	public BackgroundThread (String name)
	{
		thisThread = new Thread(name);
	}
	
	public BackgroundThread (Thread t)
	{
		this.thisThread = new Thread();
	}

	public Timestamp getTimestamp()
	{
		return new Timestamp();
	}
			
	@Override
	public void run() 
	{		
	}
	
	public void start()
	{
		getThisThread().start();
	}
	
	public void stop()
	{
		getThisThread().stop();
	}
	
	public Thread getThisThread()
	{
		return thisThread;
	}
	public void setThisThread(Thread thisThread) 
	{
		this.thisThread = thisThread;
	}
}