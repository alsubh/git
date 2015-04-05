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
public class CommitResourceEventJP {

	private JoinPoint commitResourceJP;
	private Resource resource;
	private Xid xid;
	private final Timestamp timestamp;
	private int status;
	private boolean onePhase;

	public CommitResourceEventJP() {
		timestamp = new Timestamp() ;
	}

	public CommitResourceEventJP(Xid _xid) {
		timestamp = new Timestamp() ;
		this.xid = _xid;
	}

	public CommitResourceEventJP(Xid _xid, XAResource _resource)
			throws XAException {
		timestamp = new Timestamp() ;
		this.xid = _xid;
		this.resource = new Resource(_resource, _xid);
	}

	public JoinPoint getCommitResourceJP() {
		return commitResourceJP;
	}

	public void setCommitResourceJP(JoinPoint commitResourceJP) {
		this.commitResourceJP = commitResourceJP;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public Xid getXid() {
		return xid;
	}

	public void setXid(Xid xid) {
		this.xid = xid;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public boolean isOnePhase() {
		return onePhase;
	}

	public void setOnePhase(boolean onePhase) {
		this.onePhase = onePhase;
	}
}
