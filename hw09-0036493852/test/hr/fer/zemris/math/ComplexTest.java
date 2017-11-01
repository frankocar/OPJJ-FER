package hr.fer.zemris.math;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import hr.fer.zemris.math.Complex;

public class ComplexTest {

	private static final double DELTA = 1e-6;
	private Complex c1;
	private Complex c2;
	private Complex c3;

	@Before
	public void setUp() {
		c1 = new Complex(1, 2);
		c2 = new Complex(5, 2);
		c3 = new Complex(42, -14);
	}

	@Test
	public void testConstructor() {
		Complex c = new Complex(1, 2);
		assertEquals(1, c.getReal(), DELTA);
		assertEquals(2, c.getImaginary(), DELTA);
	}

	@Test
	public void testAdd1() {
		Complex c = c1.add(c2);
		assertEquals(6, c.getReal(), DELTA);
		assertEquals(4, c.getImaginary(), DELTA);
	}

	@Test
	public void testAdd2() {
		Complex c = c1.add(c3);
		assertEquals(43, c.getReal(), DELTA);
		assertEquals(-12, c.getImaginary(), DELTA);
	}

	@Test
	public void testSub1() {
		Complex c = c1.sub(c2);
		assertEquals(-4, c.getReal(), DELTA);
		assertEquals(0, c.getImaginary(), DELTA);
	}

	@Test
	public void testSub2() {
		Complex c = c2.sub(c1);
		assertEquals(4, c.getReal(), DELTA);
		assertEquals(0, c.getImaginary(), DELTA);
	}

	@Test
	public void testModule1() {
		double expected = 2.236067977;
		assertEquals(expected, c1.module(), DELTA);
	}

	@Test
	public void testModule2() {
		double expected = 5.38516480713;
		assertEquals(expected, c2.module(), DELTA);
	}

	@Test
	public void testModule3() {
		double expected = 44.271887242;
		assertEquals(expected, c3.module(), DELTA);
	}

	@Test
	public void testNegate1() {
		Complex c = c1.negate();

		assertEquals(-1, c.getReal(), DELTA);
		assertEquals(-2, c.getImaginary(), DELTA);
	}

	@Test
	public void testNegate2() {
		Complex c = c2.negate();

		assertEquals(-5, c.getReal(), DELTA);
		assertEquals(-2, c.getImaginary(), DELTA);
	}

	@Test
	public void testNegate3() {
		Complex c = c3.negate();

		assertEquals(-42, c.getReal(), DELTA);
		assertEquals(14, c.getImaginary(), DELTA);
	}

	@Test
	public void testMultiply1() {
		Complex c = c1.multiply(c2);

		assertEquals(1, c.getReal(), DELTA);
		assertEquals(12, c.getImaginary(), DELTA);
	}

	@Test
	public void testMultiply2() {
		Complex c = c2.multiply(c2);

		assertEquals(21, c.getReal(), DELTA);
		assertEquals(20, c.getImaginary(), DELTA);
	}

	@Test
	public void testMultiply3() {
		Complex c = c3.multiply(c2);

		assertEquals(238, c.getReal(), DELTA);
		assertEquals(14, c.getImaginary(), DELTA);
	}

	@Test
	public void testDivide1() {
		Complex c = c2.divide(c3);

		assertEquals(0.0928571, c.getReal(), DELTA);
		assertEquals(0.0785714, c.getImaginary(), DELTA);
	}

	@Test
	public void testDivide2() {
		Complex c = c1.divide(c2);

		assertEquals(0.3103448, c.getReal(), DELTA);
		assertEquals(0.2758620, c.getImaginary(), DELTA);
	}

	@Test
	public void testDivide3() {
		Complex c = c3.divide(c2);

		assertEquals(6.2758620, c.getReal(), DELTA);
		assertEquals(-5.3103448, c.getImaginary(), DELTA);
	}

	@Test
	public void testPower1() {
		Complex c = c1.power(5);

		assertEquals(41, c.getReal(), DELTA);
		assertEquals(-38, c.getImaginary(), DELTA);
	}

	@Test
	public void testPower2() {
		Complex c = c1.power(6);

		assertEquals(117, c.getReal(), DELTA);
		assertEquals(44, c.getImaginary(), DELTA);
	}

	@Test
	public void testPower3() {
		Complex c = c3.power(3);

		assertEquals(49392, c.getReal(), DELTA);
		assertEquals(-71344, c.getImaginary(), DELTA);
	}

	@Test
	public void testRoots1() {
		List<Complex> roots = c1.root(5);

		for (Complex c : roots) {
			Complex tmp = c.power(5);

			assertEquals(tmp.getReal(), c1.getReal(), DELTA);
			assertEquals(tmp.getImaginary(), c1.getImaginary(), DELTA);
		}
	}

	@Test
	public void testRoots2() {
		List<Complex> roots = c2.root(5);

		for (Complex c : roots) {
			Complex tmp = c.power(5);

			assertEquals(tmp.getReal(), c2.getReal(), DELTA);
			assertEquals(tmp.getImaginary(), c2.getImaginary(), DELTA);
		}
	}

	@Test
	public void testRoots3() {
		List<Complex> roots = c3.root(5);

		for (Complex c : roots) {
			Complex tmp = c.power(5);

			assertEquals(tmp.getReal(), c3.getReal(), DELTA);
			assertEquals(tmp.getImaginary(), c3.getImaginary(), DELTA);
		}
	}

	@Test
	public void testRoots4() {
		List<Complex> roots = c3.root(10);

		for (Complex c : roots) {
			Complex tmp = c.power(10);

			assertEquals(tmp.getReal(), c3.getReal(), DELTA);
			assertEquals(tmp.getImaginary(), c3.getImaginary(), DELTA);
		}
	}

	@Test
	public void testToString1() {
		assertEquals("1,000+2,000i", c1.toString()); //may be different according to localization settings
	}
	
	@Test
	public void testToString2() {
		assertEquals("5,000+2,000i", c2.toString()); //may be different according to localization settings
	}
	
	@Test
	public void testToString3() {
		assertEquals("42,000-14,000i", c3.toString()); //may be different according to localization settings
	}

}
