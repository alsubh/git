/**
 * 
 */
package aspectUtil;

import java.util.concurrent.locks.ReadWriteLock;

import annotation.LockableClass;
import umjdt.concepts.Lock;
import umjdt.util.*;

/**
 * @author AnasR
 *
 */
public aspect LockableAspect 
{	
	private Lock Lockable.myLock = new Lock();

	public void Lockable.getLock(AccessType accessKind) 
	{
		if (this.myLock == null) 
		{
			LockableClass lockableClassAnnotation = this.getClass().getAnnotation(LockableClass.class);
			Class<? extends ReadWriteLock> lockClass = lockableClassAnnotation.value();
			try 
			{
				this.myLock = (Lock) lockClass.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.myLock.lock(accessKind);
	}
	
	public void Lockable.releaseLock(AccessType accessKind) 
	{
		this.myLock.unlock(accessKind);
	}
}