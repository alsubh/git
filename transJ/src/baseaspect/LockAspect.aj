/**
 * 
 */
package baseaspect;

import umjdt.concepts.*;
import umjdt.util.AccessType;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author AnasR
 *
 */
aspect LockAspect 
{

	transient public ReadWriteLock Lock.readWriteLock = new ReentrantReadWriteLock();

	public void Lock.lock(AccessType accessKind) 
	{
		if (accessKind == AccessType.READ) 
		{
			readWriteLock.readLock().lock();
		}
		else 
		{
			readWriteLock.writeLock().lock();
		}
	}
	
	public void Lock.unlock(AccessType accessKind)
	{
		if (accessKind == AccessType.READ)
		{
			readWriteLock.readLock().unlock();
		}
		else 
		{
			readWriteLock.writeLock().unlock();
		}
	}	
}