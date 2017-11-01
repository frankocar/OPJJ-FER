package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.Assert.*;

import org.junit.Test;

public class ValueWrapperTest {

	private static final double DELTA = 1E-6;
	
	@Test
	public void nullTest() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.add(v2.getValue());
		
		assertEquals(0, v1.getValue());
		assertEquals(null, v2.getValue());
	}
	
	@Test
	public void doubleIntegerTest() {
		ValueWrapper v1 = new ValueWrapper("1.2E1");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		v1.add(v2.getValue()); // v1 now stores Double(13); v2 still stores Integer(1).
		
		assertEquals(13.0, (Double) v1.getValue(), DELTA);
		assertEquals(Integer.valueOf(1), (Integer) v2.getValue());
	}
	
	@Test
	public void twoIntegersTest() {
		ValueWrapper v1 = new ValueWrapper("12");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		v1.add(v2.getValue()); // v1 now stores Integer(13); v2 still stores Integer(1).
		
		assertEquals(Integer.valueOf(13), (Integer) v1.getValue());
		assertEquals(Integer.valueOf(1), (Integer) v2.getValue());
	}
	
	@Test(expected=RuntimeException.class)
	public void stringAndNumber() {
		ValueWrapper v1 = new ValueWrapper("Ankica");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		v1.add(v2.getValue()); // throws RuntimeException
	}
	
	@Test
	public void compare1Test() {
		ValueWrapper v1 = new ValueWrapper(0);
		ValueWrapper v2 = new ValueWrapper(5);
		assert(v1.numCompare(v2.getValue()) < 0);
	}
	
	@Test
	public void compare2Test() {
		ValueWrapper v1 = new ValueWrapper(5);
		ValueWrapper v2 = new ValueWrapper(0);
		assert(v1.numCompare(v2.getValue()) > 0);
	}
	
	@Test
	public void compare3Test() {
		ValueWrapper v1 = new ValueWrapper("5.14");
		ValueWrapper v2 = new ValueWrapper(5.68);
		assert(v1.numCompare(v2.getValue()) < 0);
	}
	
	@Test
	public void compare4Test() {
		ValueWrapper v1 = new ValueWrapper(0.15);
		ValueWrapper v2 = new ValueWrapper(5);
		assert(v1.numCompare(v2.getValue()) < 0);
	}
	
	@Test
	public void compare5Test() {
		ValueWrapper v1 = new ValueWrapper(0);
		ValueWrapper v2 = new ValueWrapper(0);
		assert(v1.numCompare(v2.getValue()) == 0);
	}
	
	@Test
	public void multiplyTest() {
		ValueWrapper v1 = new ValueWrapper("12");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(4));
		v1.multiply(v2.getValue());
		
		assertEquals(Integer.valueOf(48), (Integer) v1.getValue());
		assertEquals(Integer.valueOf(4), (Integer) v2.getValue());
	}
	
	@Test
	public void multiplyDoubleTest() {
		ValueWrapper v1 = new ValueWrapper("12.2");
		ValueWrapper v2 = new ValueWrapper(4.4);
		v1.multiply(v2.getValue());
		
		assertEquals(53.68, (Double) v1.getValue(), DELTA);
		assertEquals(4.4, (Double) v2.getValue(), DELTA);
	}
	
	@Test
	public void multiplyBothTest() {
		ValueWrapper v1 = new ValueWrapper(12.8);
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(4));
		v1.multiply(v2.getValue());
		
		assertEquals(51.2, (Double) v1.getValue(), DELTA);
		assertEquals(Integer.valueOf(4), (Integer) v2.getValue());
	}
	
	@Test
	public void multiplyNullTest() {
		ValueWrapper v1 = new ValueWrapper(12.8);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.multiply(v2.getValue());
		
		assertEquals(0, (Double) v1.getValue(), DELTA);
		assertEquals(null, (Integer) v2.getValue());
	}
	
	@Test
	public void divideTest() {
		ValueWrapper v1 = new ValueWrapper("12");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(4));
		v1.divide(v2.getValue());
		
		assertEquals(Integer.valueOf(3), (Integer) v1.getValue());
		assertEquals(Integer.valueOf(4), (Integer) v2.getValue());
	}
	
	@Test
	public void divideDoubleTest() {
		ValueWrapper v1 = new ValueWrapper("15.4");
		ValueWrapper v2 = new ValueWrapper(4.4);
		v1.divide(v2.getValue());
		
		assertEquals(3.5, (Double) v1.getValue(), DELTA);
		assertEquals(4.4, (Double) v2.getValue(), DELTA);
	}
	
	@Test
	public void divideBothTest() {
		ValueWrapper v1 = new ValueWrapper(12.8);
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(4));
		v1.divide(v2.getValue());
		
		assertEquals(3.2, (Double) v1.getValue(), DELTA);
		assertEquals(Integer.valueOf(4), (Integer) v2.getValue());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void divideZero1Test() {
		ValueWrapper v1 = new ValueWrapper(23);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.divide(v2.getValue());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void divideZero2Test() {
		ValueWrapper v1 = new ValueWrapper(12.8);
		ValueWrapper v2 = new ValueWrapper(0);
		v1.divide(v2.getValue());
	}
	
	@Test
	public void subtract1Test() {
		ValueWrapper v1 = new ValueWrapper("1.2E1");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		v1.subtract(v2.getValue());
		
		assertEquals(11.0, (Double) v1.getValue(), DELTA);
		assertEquals(Integer.valueOf(1), (Integer) v2.getValue());
	}
	
	@Test
	public void subtract2Test() {
		ValueWrapper v1 = new ValueWrapper("-4");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		v1.subtract(v2.getValue());
		
		assertEquals(Integer.valueOf(-5), (Integer) v1.getValue());
		assertEquals(Integer.valueOf(1), (Integer) v2.getValue());
	}
	
	

}
