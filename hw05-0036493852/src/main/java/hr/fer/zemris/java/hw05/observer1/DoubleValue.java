package hr.fer.zemris.java.hw05.observer1;

/**
 * An implementation of {@link IntegerStorageObserver} that will print out the
 * double of latest value of {@link IntegerStorage} it's registered to. It will
 * unregister itself after a number of changes, a number which is given in 
 * a constructor.
 * 
 * @author Franko Car
 *
 */
public class DoubleValue implements IntegerStorageObserver {

	/**
	 * Number of times to print the value
	 */
	private int number;
	
	/**
	 * A constructor
	 * 
	 * @param number Number of changes before unregistering
	 */
	public DoubleValue(int number) {
		this.number = number;
	}
	
	@Override
	public void valueChanged(IntegerStorage istorage) {		
		System.out.printf("Double value: %d%n", 2 * istorage.getValue());
		number--;
		
		if (number == 0) {
			istorage.removeObserver(this);
		}
	}

}
