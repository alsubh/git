/**
 * 
 */
package umjdt.util;

import java.io.Serializable;

/**
 * @author AnasR
 *
 */
public interface Lock extends Serializable 
{
	public void lock(AccessType _accessKind);
	public void unlock(AccessType _accessKind);
}
