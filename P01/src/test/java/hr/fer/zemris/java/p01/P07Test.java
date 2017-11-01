package hr.fer.zemris.java.p01;

import static org.junit.Assert.*;

import org.junit.Test;
//import org.junit.Assert;

public class P07Test {

	@Test
	public void prazanStringNulaAova() {
		int n = P07.broji("");
		assertEquals(0, n);
	}

	@Test
	public void samoSlovoA() {
		int n = P07.broji("A");
		assertEquals(1, n);
	}
	
	@Test
	public void samoSlovaA() {
		int n = P07.broji("AAAA");
		assertEquals(4, n);
	}
	
	@Test
	public void viseAAAliBezB() {
		int n = P07.broji("GAAGAGA");
		assertEquals(4, n);
	}
	
	@Test
	public void viseAAAiB() {
		int n = P07.broji("GAABAAGA");
		assertEquals(4, n);
	}
}
