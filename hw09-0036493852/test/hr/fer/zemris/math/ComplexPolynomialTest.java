package hr.fer.zemris.math;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;

public class ComplexPolynomialTest {

	private static final double DELTA = 1e-6;
	private ComplexPolynomial p1;

	@Before
	public void setUp() {
		Complex c0 = new Complex(7, 2);
		Complex c1 = new Complex(2, 0);
		Complex c2 = new Complex(5, 0);
		Complex c3 = Complex.ONE;

		p1 = new ComplexPolynomial(c0, c1, c2, c3);
	}

	@Test
	public void testOrder() {
		assertEquals(3, p1.order());
	}

	@Test
	public void testDerivation() {
		p1 = p1.derive();
		assertEquals(2, p1.order());

		//(21+6i)z^2+4z+5
		Complex[] factors = p1.getFactors();
		assertEquals(3, factors.length);

		testNumber(new Complex(21, 6), factors[0]);
		testNumber(new Complex(4, 0), factors[1]);
		testNumber(new Complex(5, 0), factors[2]);
	}

	@Test
	public void testMultiply() {
		Complex c1 = new Complex(3, -1);
		Complex c2 = new Complex(0, 2);
		ComplexPolynomial p2 = new ComplexPolynomial(c1, c2);

		ComplexPolynomial result = p1.multiply(p2);
		assertEquals(4, result.order());

		Complex[] factors = result.getFactors();
		assertEquals(5, factors.length);

		testNumber(new Complex(23, -1), factors[0]);
		testNumber(new Complex(2, 12), factors[1]);
		testNumber(new Complex(15, -1), factors[2]);
		testNumber(new Complex(3, 9), factors[3]);
		testNumber(new Complex(0, 2), factors[4]);
	}

	@Test
	public void applyTest() {
		Complex c = p1.apply(new Complex(1, 3));
		testNumber(new Complex(-156, -151), c);
	}

	private void testNumber(Complex expected, Complex actual) {
		assertEquals(expected.getReal(), actual.getReal(), DELTA);
		assertEquals(expected.getImaginary(), actual.getImaginary(), DELTA);
	}

}
