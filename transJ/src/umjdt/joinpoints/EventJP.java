package umjdt.joinpoints;

import java.util.Timer;
import umjdt.concepts.Tid;
import umjdt.util.Timestamp;

public class EventJP 
{
	private String eventType;
	private int timeout;
	private Timer timer;
	private Timestamp minTime = new Timestamp();
	private Timestamp maxTime = new Timestamp();
	private Timestamp localTime = new Timestamp();

	public EventJP()
	{
	}
				
	public Timestamp getMinTime() 
	{
		return minTime;
	}
	
	public void setMinTime(Timestamp minTime) 
	{
		this.minTime = minTime;
	}
	
	public Timestamp getMaxTime() 
	{
		return maxTime;
	}
	
	public void setMaxTime(Timestamp maxTime) 
	{
		this.maxTime = maxTime;
	}
	
	public boolean threadEventHappensBefore(EventJP e)
	{
		if(e.getLocalTime().compareTo(this.getLocalTime()) > 0)
					return true;
		return false;
	}
	
	public Timestamp getLocalTime() 
	{
		return localTime;
	}
	
	public void setLocalTime(Timestamp localTime) 
	{
		this.localTime = localTime;
	}
	
	public int getTimeout() 
	{
		return timeout;
	}
	public void setTimeout(int timeout) 
	{
		this.timeout = timeout;
	}

	public Timer getTimer() 
	{
		return timer;
	}
	public void setTimer(Timer timer)
	{
		this.timer = timer;
	}

	public String getEventType() 
	{
		return eventType;
	}

	public void setEventType(String eventType)
	{
		this.eventType = eventType;
	}
}
