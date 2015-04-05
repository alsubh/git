package umjdt.concepts;

import java.io.Serializable;

import javax.transaction.xa.Xid;

import com.arjuna.ats.arjuna.common.Uid;
import com.arjuna.ats.jta.xa.XidImple;
//import com.arjuna.ats.internal.jta.xa.XID;


public class Tid extends XidImple implements Xid, Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Xid xid;	
	private Uid uid;
	
	public Tid()
	{
		super();
		this.xid= new XidImple();
		this.uid= new Uid();
		setUid(uid);
		setXid(xid);
	}
	
	public Tid(Xid _xid, Uid _uid)
	{
		super();
		setUid(uid);
		setXid(xid);
	}
	
	public Tid(Xid _xid)
	{
		super(_xid);
		setXid(new XidImple(_xid));
	}
	
	public Tid(Uid _uid)
	{
		super();
		setUid(new Uid(_uid));
	}
	
	public void setXid(Xid _xid) 
	{
		this.xid = _xid;
	}
	
	public Xid getXid()
	{
		return xid;
	}

	public Uid getUid() {
		return uid;
	}

	public void setUid(Uid _uid) {
		this.uid = _uid;
	}
}
