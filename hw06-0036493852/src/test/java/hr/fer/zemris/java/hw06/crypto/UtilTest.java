package hr.fer.zemris.java.hw06.crypto;

import static org.junit.Assert.*;

import java.nio.charset.StandardCharsets;

import org.junit.Test;

import hr.fer.zemirs.java.hw06.crypto.Util;

public class UtilTest {

	@Test
	public void hextobyteTest() {
		assertArrayEquals(new byte[] {1, -82, 34}, Util.hextobyte("01aE22"));
	}
	
	@Test
	public void bytetohexTest() {
		assertEquals("01ae22", Util.bytetohex(new byte[] {1, -82, 34}));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void invalidString() {
		Util.hextobyte("213d3");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void invalidHex() {
		Util.hextobyte("213g23");
	}
	
	@Test
	public void stringTest() {
		assertEquals("Franko", new String(Util.hextobyte("4672616e6b6f"), StandardCharsets.UTF_8));
	}
	
	@Test
	public void zeroLengthByteToHex() {
		assertEquals("", Util.bytetohex(new byte[0]));
	}
	
	@Test
	public void zeroLengthHexToByte() {
		assertEquals(0, Util.hextobyte("").length);
	}
	
	
	

}
