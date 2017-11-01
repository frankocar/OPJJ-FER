package hr.fer.zemris.math;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

public class ComplexRootedPolynomialTest {

	private static final double DELTA = 1e-6;
	private ComplexRootedPolynomial p;

	@Before
	public void setUp() {
		Complex c1 = new Complex(1, 2);
		Complex c2 = new Complex(3, 1);
		Complex c3 = new Complex(-1, 2);

		p = new ComplexRootedPolynomial(c1, c2, c3);
	}

	@Test
	public void testToCmplexPolynomial() {
		ComplexPolynomial p1 = p.toComplexPolynom();
		assertNotNull(p1);

		assertEquals(3, p1.order());
		Complex[] factors = p1.getFactors();
		testNumber(Complex.ONE, factors[0]);
		testNumber(new Complex(-3, -5), factors[1]);
		testNumber(new Complex(-9, 12), factors[2]);
		testNumber(new Complex(15, 5), factors[3]);

	}

	@Test
	public void testApply() {
		Complex c = new Complex(1, 2);
		Complex c1 = p.apply(c);

		testNumber(Complex.ZERO, c1);
	}

	@Test
	public void testIndexOfClosest() {
		int index = p.indexOfClosestRootFor(Complex.ONE, 0);
		assertEquals(-1, index);
	}

	@Test
	public void testIndexOfClosest2() {
		int index = p.indexOfClosestRootFor(Complex.ONE, 2.5);
		assertEquals(1, index);
	}

	private void testNumber(Complex expected, Complex actual) {
		assertEquals(expected.getReal(), actual.getReal(), DELTA);
		assertEquals(expected.getImaginary(), actual.getImaginary(), DELTA);
	}

}
