package hr.fer.zemris.java.hw05.observer2;

/**
 * An implementation of {@link IntegerStorageObserver} that will print out the
 * square of latest value of {@link IntegerStorage} it's registered to.
 * 
 * @author Franko Car
 *
 */
public class SquareValue implements IntegerStorageObserver {

	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		System.out.printf("Provided new value: %d, square is %d%n", istorage.getNewValue(), istorage.getNewValue() * istorage.getNewValue());

	}

}
