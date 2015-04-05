/**
 * 
 */
package umjdt.util;

/**
 * @author AnasR
 *
 */
public interface Lockable 
{
	/** acquires a lock (if not already acquired) and locks it using the specified accessKind */
	public void getLock(AccessType accessKind);

	/** releases the lock with the specified accessKind */
	public void releaseLock(AccessType accessKind);
	
}
