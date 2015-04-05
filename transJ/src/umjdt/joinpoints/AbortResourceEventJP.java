/**
 * 
 */
package umjdt.joinpoints;

import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

import org.aspectj.lang.JoinPoint;

import umjdt.concepts.Resource;
import umjdt.util.Timestamp;

/**
 * @author AnasAlsubh
 * 
 */
public class AbortResourceEventJP {

	private JoinPoint abortResourceJP;
	private Resource resource;
	private Xid xid;
	private int status;
	private final Timestamp timestamp;

	public AbortResourceEventJP() {
		timestamp = new Timestamp() ;
	}

	public AbortResourceEventJP(Xid _xid) {
		timestamp = new Timestamp() ;
		this.setXid(_xid);
	}

	public AbortResourceEventJP(Xid _xid, XAResource _resource)
			throws XAException {
		timestamp = new Timestamp() ;
		this.setXid(_xid);
		this.setResource(new Resource(_resource, _xid));
	}

	public JoinPoint getAbortResourceJP() {
		return abortResourceJP;
	}

	public void setAbortResourceJP(JoinPoint abortResourceJP) {
		this.abortResourceJP = abortResourceJP;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Xid getXid() {
		return xid;
	}

	public void setXid(Xid xid) {
		this.xid = xid;
	}
}
