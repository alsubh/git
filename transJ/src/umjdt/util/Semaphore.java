package umjdt.util;

import java.io.Serializable;

public class Semaphore implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	transient private boolean inUse;

	public Semaphore() {
		inUse = false;
	}

	/*** Acquire lock if not in use. Otherwise, wait till lock is available **/
	public synchronized void P() {
		while (inUse == true) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		inUse = true;
	}

	/*** Release lock and wake up sleep threads **/
	public synchronized void V() {
		inUse = false;
		notifyAll();
	}
}
