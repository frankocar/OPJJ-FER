package hr.fer.zemris.bf.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class UtilsTest {

	@Test
	public void indexToByteArrayTest() {
		assertArrayEquals(new byte[] {1, 1}, Util.indexToByteArray(3, 2));
	}
	
	@Test
	public void OversizedIndexToByteArrayTest() {
		assertArrayEquals(new byte[] {0, 0, 0, 0, 1, 1}, Util.indexToByteArray(3, 6));
	}
	
	@Test
	public void UndersizedIndexToByteArrayTest() {
		assertArrayEquals(new byte[] {0, 0, 1, 1}, Util.indexToByteArray(19, 4));
	}
	
	@Test
	public void NegativeIndexToByteArrayTest() {
		assertArrayEquals(new byte[] {1, 1, 1, 0}, Util.indexToByteArray(-2, 4));
	}
	
	@Test
	public void BigNegativeIndexToByteArrayTest() {
		assertArrayEquals(new byte[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 1, 1, 0}, Util.indexToByteArray(-698, 24));
	}
	
	@Test
	public void BigIndexToByteArrayTest() {
		assertArrayEquals(new byte[] {0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0}, Util.indexToByteArray(24528, 16));
	}

}
