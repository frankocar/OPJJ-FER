package hr.fer.zemris.java.hw05.demo2;

import static org.junit.Assert.assertArrayEquals;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;

public class PrimesCollectionTest {

	@Test
	public void simpleTest() {
		int[] expectedResults = {
				2, 3, 5, 7, 11, 13, 17, 19, 23, 29,
				31, 37, 41, 43, 47, 53, 59, 61, 67, 71,
				73, 79,	83, 89, 97, 101, 103, 107, 109, 113,
				127, 131, 137, 139, 149, 151, 157, 163, 167, 173,
				179, 181, 191, 193, 197, 199, 211, 223, 227, 229,
				233, 239, 241, 251, 257, 263, 269, 271, 277, 281,
				283, 293, 307, 311,	313, 317, 331, 337, 347, 349,
				353, 359, 367, 373, 379, 383, 389, 397, 401, 409,
				419, 421, 431, 433, 439, 443, 449, 457, 461, 463,
				467, 479, 487, 491, 499, 503, 509, 521, 523, 541
				};
		int[] results = new int[100];
		
		PrimesCollection primesCollection = new PrimesCollection(100);
		
		int i = 0;		
		for (int prime : primesCollection) { //used for-each form to test iteration
			results[i] = prime;
			i++;
		}
		
		assertArrayEquals(expectedResults, results);
	}
	
	@Test(expected=NoSuchElementException.class)
	public void exceptionTest() {
		PrimesCollection primesCollection = new PrimesCollection(1);
		Iterator<Integer> it = primesCollection.iterator();
		it.next();
		it.next();

	}

}
