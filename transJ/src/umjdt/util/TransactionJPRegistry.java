package umjdt.util;

import java.util.Hashtable;

import umjdt.concepts.Tid;
import context.Context;

public class TransactionJPRegistry
{
	
	private static Hashtable<Tid, Context> contextRegistry = new Hashtable<>();
	
	public Context lookup(Tid cid)
	{
		Context result= new Context();
		return result;
	}

}
