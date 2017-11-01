package hr.fer.zemris.java.hw05.observer2;


/**
 * A class to wrap the value sent to the observer so it contains the
 * new value, old value and {@link IntegerStorage} that sends it 
 * 
 * @author Franko Car
 *
 */
public class IntegerStorageChange {

	/**
	 * {@link IntegerStorage} notifying about the change
	 */
	private IntegerStorage storage;
	/**
	 * Previous value stored
	 */
	private int oldValue;
	/**
	 * New value stored
	 */
	private int newValue;
	
	/**
	 * A constructor
	 * 
	 * @param storage {@link IntegerStorage} notifying about the change
	 * @param oldValue Previous value stored
	 * @param newValue New value stored
	 */
	public IntegerStorageChange(IntegerStorage storage, int oldValue, int newValue) {
		super();
		this.storage = storage;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	/**
	 * A getter
	 * 
	 * @return IntegerStorge
	 */
	public IntegerStorage getStorage() {
		return storage;
	}
	
	/**
	 * A getter
	 * 
	 * @return Old value
	 */
	public int getOldValue() {
		return oldValue;
	}
	
	/**
	 * A getter
	 * 
	 * @return New value
	 */
	public int getNewValue() {
		return newValue;
	}
	
}
