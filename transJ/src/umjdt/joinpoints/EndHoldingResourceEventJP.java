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
public class EndHoldingResourceEventJP {

	private Xid xid;
	private Transaction transaction;
	private Resource resource;
	private Timestamp timestamp;
	private int flag;
	private JoinPoint endResourceJP;

	public EndHoldingResourceEventJP() {
		timestamp = new Timestamp() ;
	}

	public EndHoldingResourceEventJP(Xid _xid) {
		timestamp = new Timestamp() ;
		this.setXid(_xid);
	}

	public EndHoldingResourceEventJP(Xid _xid, XAResource _xaresource)
			throws XAException {
		timestamp = new Timestamp() ;
		this.setXid(_xid);
		this.resource = new Resource(_xaresource, _xid);
	}

	public EndHoldingResourceEventJP(Transaction _transaction) {
		timestamp = new Timestamp() ;
		this.setTransaction(_transaction);
	}

	public JoinPoint getEndResourceJP() {
		return endResourceJP;
	}

	public void setEndResourceJP(JoinPoint endResourceJP) {
		this.endResourceJP = endResourceJP;
	}

	public Xid getXid() {
		return xid;
	}

	public void setXid(Xid xid) {
		this.xid = xid;
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

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}
}
