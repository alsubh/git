package umjdt.util;

public class ThreadUtil
{
	// The ID associated with the thread.
    
    private static final ThreadLocal THREAD_ID = new ThreadLocal() ;
    
     //The thread id counter.
    
    private static long id ;
    
    
     // Get the string ID for the current thread.
     //@return The thread id

    public static String getThreadId()
    {
    return getThreadId(Thread.currentThread()) ;
    }
    
    
     // Get the string ID for the specified thread.
     // @param thread The thread.
     // @return The thread id
     
    public static String getThreadId(final Thread thread)
    {
    final Object id = THREAD_ID.get() ;
    if (id != null)
    {
        return (String)id ;
    }
    
    final String newId = getNextId() ;
    THREAD_ID.set(newId) ;
    return newId ;
    }
    
    
     // Get the next thread id to use.
     // @return The next thread id.
     
    private static synchronized String getNextId()
    {
  	  return "TSThread:" + Long.toHexString(++id) ;
    }
}
