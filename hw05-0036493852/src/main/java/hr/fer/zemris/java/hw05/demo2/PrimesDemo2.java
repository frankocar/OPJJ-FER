package hr.fer.zemris.java.hw05.demo2;
/**
 * A simple program to print a cartesian product of 2 prime 
 * collections using {@link PrimesCollection}.
 * Requires no arguments.
 * 
 * @author Franko Car
 *
 */
public class PrimesDemo2 {

	/**
	 * Main method
	 * 
	 * @param args None required
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(2);
		for (Integer prime : primesCollection) {
			for (Integer prime2 : primesCollection) {
				System.out.println("Got prime pair: " + prime + ", " + prime2);
			}
		}

	}

}
