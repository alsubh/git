package umjdt.util;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.NoSuchElementException;
import java.util.Stack;

import umjdt.concepts.Transaction;

	   /* This class maintains a mapping between a thread and its notion of the current
	   * transTransTransTransaction. Essentially this is a stack of transTransTransTransactions.*/
public class TransactionThread
{
      public static Transaction currentTransaction()
      {
       	TransactionThread.setup();
        Stack txs = (Stack) _threadList.get();
   
       if (txs != null && !txs.isEmpty())
       {
         return (Transaction) txs.peek();
       }
   
         return null;
      }
		    
	  public static void pushTransaction (Transaction a)
	  {
	     pushTransaction(a, true);
	  }
		        
	 /* By setting the register flag accordingly, information about the thread
	  * may not be propagated to the Transaction, i.e., only the thread's notion of
	  * current changes.*/         
		    
	 public static void pushTransaction (Transaction a, boolean register)
	 {
	    Thread t = Thread.currentThread();
	    Stack txs = (Stack) _threadList.get();
	    
	   if (txs == null)
	   {
	       txs = new Stack();
	       txs.push(a);
		   
	       _threadList.set(txs);
	   }
	   else
	       txs.push(a);
		    
	      if (register)
	           a.addChildThread(t);
	   }
	        
	 // Put back the entire hierarchy, removing whatever is already there.
	    public static void restoreTransactions (Transaction trans)
	    {
	       purgeTransactions();
		   
	       if (trans != null)
	       {
	       /*
	        * First get the hierarchy from the bottom up.
	        */
		   
	        java.util.Stack s = new java.util.Stack();
	        Transaction nextLevel = trans.parent();
		   
	        s.push(trans);
		   
	       while (nextLevel != null)
	       {
	         s.push(nextLevel);	   
		     nextLevel = nextLevel.parent();
		   }
		   
		   /*
		                * Now push the hierarchy onto the thread stack.
		                */
		   
		               try
		               {
		                   while (!s.empty())
		                   {
		                       pushTransaction((Transaction) s.pop());
		                   }
		               }
		               catch (Exception ex)
		               {
		               }
		           }
		       }
		   
		       public static Transaction popTransaction () throws EmptyStackException
		       {
		           return popTransaction(ThreadUtil.getThreadId(), true);
		       }
		  
		       public static Transaction popTransaction (boolean unregister)
		               throws EmptyStackException
		       {
		           return popTransaction(ThreadUtil.getThreadId(), unregister);
		       }
		   
		       public static Transaction popTransaction (String threadId)
		               throws EmptyStackException
		       {
		           return popTransaction(threadId, true);
		       }
		   
		       
		        /* By setting the unregister flag accordingly, information about the thread
		        * is not removed from the TransTransTransaction.
		        */
		   
		       public static Transaction popTransaction (String threadId, boolean unregister)
		               throws EmptyStackException
		       {
		           Stack txs = (Stack) _threadList.get();
		   
		           if (txs != null)
		           {
		               Transaction a = (Transaction) txs.pop();
		   
		               if ((a != null) && (unregister))
		               {
		                   a.removeChildThread(threadId);
		               }
		   
		               if (txs.size() == 0)
		               {
		                   _threadList.set(null);
		               }
		   
		               return a;
		           }
		   
		           return null;
		       }
		   
		       public static void purgeTransaction (Transaction trans)
		               throws NoSuchElementException
		       {
		           TransactionThread.purgeTransaction(trans, Thread.currentThread(), true);
		       }
		   
		       public static void purgeTransaction (Transaction trans, Thread t)
		               throws NoSuchElementException
		       {
		           TransactionThread.purgeTransaction(trans, t, true);
		       }
		   
		       public static void purgeTransaction (Transaction trans, Thread t, boolean unregister)
		               throws NoSuchElementException
		       {
		           if ((trans != null) && (unregister))
		           {
		               trans.removeChildThread(ThreadUtil.getThreadId(t));
		           }
		   
		           Stack txs = (Stack) _threadList.get();
		   
		           if (txs != null)
		           {
		               txs.removeElement(trans);
		   
		               if (txs.size() == 0)
		               {
		                   _threadList.set(null);
		               }
		           }
		       }
		   
		       public static void purgeTransactions ()
		       {
		           purgeTransactions(Thread.currentThread(), true);
		       }
		   
		       public static void purgeTransactions (Thread t)
		       {
		           purgeTransactions(t, true);
		       }
		   
		       public static void purgeTransactions (Thread t, boolean unregister)
		       {
		           Stack txs = (Stack) _threadList.get();
		   
		           _threadList.set(null);
		   
		           if (txs != null)
		           {
		               if (unregister)
		               {
		                   while (!txs.empty())
		                   {
		                       Transaction act = (Transaction) txs.pop();
		   
		                       if (act != null)
		                       {
		                           act.removeChildThread(ThreadUtil.getThreadId(t));
		                       }
		                   }
		               }
		           }
		       }
		   
		       
		        /* Add a per thread setup object to the global list. This should only
		        * happen before the transaction service really begins, or you risk having
		        * some threads see one view of the list that is different to other threads.
		        * @param s the setup to add.
		        */
		       
		       public static void addSetup (ThreadSetup s)
		       {
		           synchronized (_threadSetups)
		           {
		               _threadSetups.add(s);
		           }
		       }
		   
		       
		        /* Remove a per thread setup object to the global list. This should only
		        * happen after the transaction service really ends, or you risk having
		        * some threads see one view of the list that is different to other threads.
		        * 
		        * @param s the setup to add.
		        */
		       
		       public static boolean removeSetup (ThreadSetup s)
		       {
		           synchronized (_threadSetups)
		           {
		               return _threadSetups.remove(s);
		           }
		       }
		   
		       private static void setup ()
		       {
		           for (int i = 0; i < _threadSetups.size(); i++)
		           {
		               ThreadSetup s = (ThreadSetup) _threadSetups.get(i);
		   
		               if (s != null)
		                   s.setup();
		           }
		       }
		   
		       private static ThreadLocal _threadList = new ThreadLocal();
		  
		       private static ArrayList _threadSetups = new ArrayList();
		    }
