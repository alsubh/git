package umjdt.concepts;

import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

public class Resource {

	private XAResource xaresource;
	private int state;
	private Xid xid;
	private Thread thread;

	public Resource(XAResource _resource) throws XAException {
		this.xaresource = _resource;
	}

	public Resource(XAResource _resource, Xid _xid) throws XAException {
		this.xaresource = _resource;
		this.xid = _xid;
	}

	public Resource(XAResource _resource, Xid _xid, int _state)
			throws XAException {
		this.xaresource = _resource;
		this.xid = _xid;
		this.state = _state;
	}

	public Resource(XAResource _resource, Xid _xid, Thread _thread, int _state)
			throws XAException {
		this.xaresource = _resource;
		this.xid = _xid;
		this.thread = _thread;
		this.state = _state;
	}

	public XAResource getXaresource() {
		return xaresource;
	}

	public void setXaresource(XAResource xaresource) {
		this.xaresource = xaresource;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Xid getXid() {
		return xid;
	}

	public void setXid(Xid xid) {
		this.xid = xid;
	}

	public Thread getThread() {
		return thread;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}
}