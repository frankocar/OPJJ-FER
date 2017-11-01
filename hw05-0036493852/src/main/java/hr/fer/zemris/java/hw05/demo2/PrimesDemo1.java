package hr.fer.zemris.java.hw05.demo2;

/**
 * A simple program to print 5 primes using {@link PrimesCollection}.
 * Requires no arguments.
 * 
 * @author Franko Car
 *
 */
public class PrimesDemo1 {

	/**
	 * Main method
	 * 
	 * @param args None required
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(5);
		for (Integer prime : primesCollection) {
			System.out.println("Got prime: " + prime);
		}
	}

}
