package hr.fer.zemris.java.hw05.observer1;

/**
 * An implementation of {@link IntegerStorageObserver} that will print out the
 * number of times IntegerStorage to which it's registered to has changed since
 * registration.
 * 
 * @author Franko Car
 *
 */
public class ChangeCounter implements IntegerStorageObserver {

	/**
	 * A counter to keep track of number of changes
	 */
	private int counter;
	
	/**
	 * Default constructor
	 */
	public ChangeCounter() {
		counter = 0;
	}
	
	@Override
	public void valueChanged(IntegerStorage istorage) {
		System.out.printf("Number of value changes since tracking: %d%n", ++counter);

	}

}
