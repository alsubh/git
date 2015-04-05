package test;

import java.util.UUID;

import umjdt.util.IdNumber;

import com.arjuna.ats.arjuna.common.Uid;
import com.arjuna.ats.internal.jta.xa.XID;
import com.arjuna.ats.jta.xa.XidImple;

public class operationNum {

	IdNumber n;
	
	public void test1(){
	
		XID s= new XID();
		UUID ss=UUID.randomUUID();
		Uid uid= Uid.maxUid();
		System.out.println(uid.getBytes());
		XidImple x = new XidImple(uid);
		
		n = IdNumber.Create();
		System.out.println(n.LocalTransactionId+ ", " +n.getSeqNumber() + ", " +n.transactionId);
		
		n = IdNumber.Create();
		System.out.println(n.LocalTransactionId+ ", " + n.getSeqNumber() + ", " +n.transactionId);
			
		System.out.println(x.getGlobalTransactionId());
		System.out.println(x.getFormatId());
		System.out.println(x.getBranchQualifier());
		System.out.println(x.getTransactionUid());
		System.out.println(x.getXID());
		System.out.println("\n");
	}
	
	public static void main(String[] args) {
		operationNum op= new operationNum();
		op.test1();
		op.test1();
		op.test1();
	}
}
