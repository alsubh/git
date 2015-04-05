package umjdt.concepts;

import java.io.Serializable;

import umjdt.util.IdNumber;


public class SubTransaction extends Transaction implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int timeout;
	private IdNumber subTid;
		
	public SubTransaction(Tid _tid)
	{
		super();
		subTid = IdNumber.Create(_tid);
		setSubTid(subTid);
	}
	
	public SubTransaction(Tid _tid, int _timeout)
	{
		super(_timeout);
		this.timeout=_timeout;
		subTid = IdNumber.Create(_tid);
		setSubTid(subTid);
	}

	public int getTimeout() 
	{
		return timeout;
	}

	public void setTimeout(int timeout) 
	{
		this.timeout = timeout;
	}
	
	public IdNumber getSubTid() 
	{
		return subTid;
	}

	public void setSubTid(IdNumber subTid) 
	{
		this.subTid = subTid;
	}

	public static long getSerialversionuid() 
	{
		return serialVersionUID;
	}
}
