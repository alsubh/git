/**
 * 
 */
package umjdt.joinpoints;

import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

import org.aspectj.lang.JoinPoint;

import umjdt.concepts.Resource;
import umjdt.concepts.Transaction;
import umjdt.util.Timestamp;

/**
 * @author AnasAlsubh
 * 
 */
public class StartHoldingResourceEventJP {

	private Xid xid;
	private Transaction transaction;
	private Resource resource;
	private Timestamp timestamp;
	private JoinPoint startResourceJP;

	public StartHoldingResourceEventJP() {
		setTimestamp(new Timestamp());
	}

	public StartHoldingResourceEventJP(Xid _xid) {
		setTimestamp(new Timestamp());
		this.setXid(_xid);
	}

	public StartHoldingResourceEventJP(Xid _xid, XAResource _xaresource)
			throws XAException {
		setTimestamp(new Timestamp());
		this.setXid(_xid);
		this.resource = new Resource(_xaresource, _xid);
	}

	public StartHoldingResourceEventJP(Transaction _transaction) {
		setTimestamp(new Timestamp());
		this.transaction = _transaction;
	}

	public JoinPoint getStartResourceJP() {
		return startResourceJP;
	}

	public void setStartResourceJP(JoinPoint startResourceJP) {
		this.startResourceJP = startResourceJP;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

	public Xid getXid() {
		return xid;
	}

	public void setXid(Xid xid) {
		this.xid = xid;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
}
