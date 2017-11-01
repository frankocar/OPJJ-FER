package hr.fer.zemris.java.hw05.observer2;

/**
 * A simple program that will test {@link IntegerStorage} ability
 * to store and notify observers. Requires no arguments.
 * 
 * @author Franko Car
 *
 */
public class ObserverExample {
	
	/**
	 * A method to start the program
	 * 
	 * @param args None required
	 */
	public static void main(String[] args) {
		
		IntegerStorage istorage = new IntegerStorage(20);
		
		IntegerStorageObserver observer = new SquareValue();
		
		istorage.addObserver(observer);
		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue(1));
		istorage.addObserver(new DoubleValue(2));
		istorage.addObserver(new DoubleValue(2));
		
		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);
		
//		istorage.removeObserver(observer);
		
		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);
	}
}