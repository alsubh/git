package umjdt.concepts;

import umjdt.util.Semaphore;

public class Lock implements umjdt.util.Lock {

	private static final long serialVersionUID = 1L;

	private Tid tid;
	private Thread owner;
	private boolean reenterant;
	// number of transactions
	transient private int numberOfReaders;
	// synchronizing access to resources
	private Semaphore readerSemaphore;
	// synchronizing Read/Write access to a resource
	private Semaphore readWriteSemaphore;
	// holders of the respective locks
	transient private String readLockHolder, writeLockHolder, updateLockHolder;
	// the name of the method on which a lock was obtained
	transient private String method;

	public Lock() {
		semaphore();
	}

	public Lock(Tid _tid) {
		this.tid = _tid;
		semaphore();
	}

	public Lock(Tid _tid, Thread _owner) {
		this.setTid(_tid);
		this.owner = _owner;
		semaphore();
	}

	public void semaphore() {
		numberOfReaders = 0;

		readerSemaphore = new Semaphore();
		readWriteSemaphore = new Semaphore();

		readLockHolder = "#";
		writeLockHolder = "#";
		updateLockHolder = "#";
		method = "#";
	}

	public void releaseReadLock() {
		readerSemaphore.P();
		numberOfReaders = numberOfReaders - 1;

		if (numberOfReaders == 0) {
			readLockHolder = "";
			method = "";
			readWriteSemaphore.V();
			// System.out.println("------------------Someone released the read lock!-----------");
		}

		readerSemaphore.V();
	}

	public void getWriteLock(String holder, String name) {
		readWriteSemaphore.P();
		// System.out.println("------------------Someone got the write lock!-----------");
		writeLockHolder = holder;
		method = name;
	}

	public void releaseWriteLock() {
		writeLockHolder = "";
		method = "";

		readWriteSemaphore.V();
		// System.out.println("------------------Someone released the write lock!-----------");

	}

	public void getUpdateLock(String holder, String name) {
		readWriteSemaphore.P();
		updateLockHolder = holder;
		method = name;
	}

	public void releaseUpdateLock() {
		updateLockHolder = "";
		method = "";

		readWriteSemaphore.V();

	}

	public String getReadLockHolder() {
		return readLockHolder;
	}

	public String getWriteLockHolder() {
		return writeLockHolder;
	}

	public String getUpdateLockHolder() {
		return updateLockHolder;
	}

	public String getMethodName() {
		return method;
	}

	public Thread getOwner() {
		return owner;
	}

	public void setOwner(Thread owner) {
		this.owner = owner;
	}

	public boolean isReenterant() {
		return reenterant;
	}

	public void setReenterant(boolean reenterant) {
		this.reenterant = reenterant;
	}

	public Tid getTid() {
		return tid;
	}

	public void setTid(Tid tid) {
		this.tid = tid;
	}
}
