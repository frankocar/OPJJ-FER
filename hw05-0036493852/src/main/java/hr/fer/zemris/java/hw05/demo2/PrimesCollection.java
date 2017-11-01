package hr.fer.zemris.java.hw05.demo2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A collection implementing {@link Iterable} that will return the
 * requested number of primes via its iterator
 * 
 * @author Franko Car
 *
 */
public class PrimesCollection implements Iterable<Integer>{

	/**
	 * Number of primes to be returned
	 */
	private int numberOfPrimes;
	
	/**
	 * A constructor, won't accept negative arguments
	 * 
	 * @param numberOfPrimes Number of primes that the collection should return
	 * @throws IllegalArgumentException If the requested number is less then 0
	 */
	public PrimesCollection(int numberOfPrimes) {
		if (numberOfPrimes < 0) {
			throw new IllegalArgumentException("Can't request negative number of integers");
		}
		
		this.numberOfPrimes = numberOfPrimes;
	}
	
	@Override
	public Iterator<Integer> iterator() {		
		return new IteratorImpl();
	}
	
	/**
	 * An implementation of {@link Iterator} for {@link PrimesCollection}
	 * 
	 * @author Franko Car
	 *
	 */
	private class IteratorImpl implements Iterator<Integer> {

		/**
		 * Next number in line to check
		 */
		private int nextNumber;
		/**
		 * Number of primes returned
		 */
		private int primesFound;
				
		/**
		 * A default constructor
		 */
		public IteratorImpl() {
			this.nextNumber = 2;
			this.primesFound = 0;
		}

		@Override
		public boolean hasNext() {
			return numberOfPrimes > primesFound;
		}

		@Override
		public Integer next() {
			if (!hasNext()) {
				throw new NoSuchElementException("Poduced all elements");
			}
			
			while (true) {
				if (isPrime(nextNumber++)) {
					primesFound++;
					return nextNumber - 1;
					
				}
			}
		}
		
		/**
		 * A method to check whether a given number is prime
		 * 
		 * @param n number to check
		 * @return true if prime, false otherwise
		 */
		private boolean isPrime(int n) {
			double root = Math.sqrt(n);
			
			for (int i = 2; i <= root; i++) {
				if (n % i == 0) {
					return false;
				}
			}
			
			return true;
		}
		
	}
	
}
