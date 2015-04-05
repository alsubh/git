package umjdt.util;

import java.io.Serializable;

import umjdt.concepts.Tid;

public class IdNumber implements Comparable, Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static short nextSeqNumber = 1;  // Start with operation #1
    public static IdNumber Empty;
    public static Tid LocalTransactionId; // Local transaction Id -- set once when the operation joins the transaction application
    public Tid transactionId;
    public short SeqOperationNumber;
    
    public static IdNumber Create() {
    	IdNumber result = new IdNumber();
        result.transactionId = LocalTransactionId;
        result.SeqOperationNumber = GetNextSeqNumber();
        return result;
    }
    
    public static IdNumber Create(Tid _transactionId)
    {
    	IdNumber result = new IdNumber();
        result.transactionId = _transactionId;
        result.SeqOperationNumber = GetNextSeqNumber();
        return result;
    }

    public static IdNumber Create(Tid _transactionId, short _operationSeqNur) {
    	IdNumber result = new IdNumber();
        result.transactionId = _transactionId;
        result.SeqOperationNumber = _operationSeqNur;
        return result;
    }

    private IdNumber() {
    }

    @Override
    public String toString() {
        return transactionId + "." + SeqOperationNumber;
    }

    public boolean Equals(Object obj) {
        boolean tag = false;
        int result = Compare(this, (IdNumber) obj);

        if (result > 0) {
            tag = false;
        } else if (result < 0) {
            tag = false;
        } else if (result == 0) {
            tag = true;
        }
        return tag;
    }

    public static int Compare(IdNumber a, IdNumber b) {
        int result = 0;

        if (!(a == b)) {
            if (((Object) a == null) && ((Object) b != null)) {
                result = -1;
            } else if (((Object) a != null) && ((Object) b == null)) {
                result = 1;
            } else {
                if (!a.transactionId.equals(b.transactionId)) {// Operation for different transactions. 
                    result = -1;
                } else if (a.transactionId.equals(b.transactionId)) {// operations for the transaction.
                    result = 1;
                } else if (a.SeqOperationNumber < b.SeqOperationNumber) { // the operation a is happened before the operation b
                    result = 2;
                } else if (a.SeqOperationNumber > b.SeqOperationNumber) { //the operation a is happened after the operation b
                    result = 3;
                }
            }
        }
        return result;
    }
    
    public static int CompareOperationsOn(IdNumber a, IdNumber b) {
        int result = 0;

        if (!(a == b)) {
            if (((Object) a == null) && ((Object) b != null)) {
                result = -1;
            } else if (((Object) a != null) && ((Object) b == null)) {
                result = 1;
            } else
            	{
            		if (a.transactionId.equals(b.transactionId)) {// operations on the same transaction.
            		}
	                    if (a.SeqOperationNumber < b.SeqOperationNumber) 
	                    { // the operation a is happened before the operation b
	                    	result = 2;
	                    } 
	                    else if (a.SeqOperationNumber > b.SeqOperationNumber) 
	                    { //the operation a is happened after the operation b
	                    	result = 3;
	                    }
            	}
        }
        return result;
    }

    public static boolean operatorEqual(IdNumber a, IdNumber b) {
        return (Compare(a, b) == 0);
    }

    public static boolean operatorNotEqual(IdNumber a, IdNumber b) {
        return (Compare(a, b) != 0);
    }

    public static boolean operatorLessThan(IdNumber a, IdNumber b) {
        return (Compare(a, b) < 0);
    }

    public static boolean operatorGreaterThan(IdNumber a, IdNumber b) {
        return (Compare(a, b) > 0);
    }

    public static boolean operatorLessThankOrEqual(IdNumber a, IdNumber b) {
        return (Compare(a, b) <= 0);
    }

    public static boolean operatorGreaterThanOrEqual(IdNumber a, IdNumber b) {
        return (Compare(a, b) >= 0);
    }

    public int CompareTo(Object obj) {
        return Compare(this, (IdNumber) obj);
    }

    public static IdNumber getEmpty() {
        return new IdNumber();
    }

    public static void setEmpty(IdNumber empty) {
        Empty = empty;
    }

    public short getSeqNumber() {
        return SeqOperationNumber;
    }

    public void setSeqNumber(short seqNumber) {
        SeqOperationNumber = seqNumber;
    }

    private static short GetNextSeqNumber() {
        if (nextSeqNumber == Short.MAX_VALUE) {
            nextSeqNumber = 1;
        }
        return nextSeqNumber++;
    }

    @Override
    public int compareTo(Object obj) {
        return Compare(this, (IdNumber) obj);
    }
}