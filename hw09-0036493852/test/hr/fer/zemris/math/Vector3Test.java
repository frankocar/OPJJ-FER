package hr.fer.zemris.math;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class Vector3Test {

	private static final double DELTA = 1e-6;
	private Vector3 v1;
	private Vector3 v2;
	private Vector3 v3;

	@Before
	public void setUp() {
		v1 = new Vector3(1, 0, 0);
		v2 = new Vector3(0, 1, 0);
		v3 = new Vector3(5, -10, 15);
	}

	@Test
	public void testConstructor() {
		assertArrayEquals(new double[] {5, -10, 15}, v3.toArray(), DELTA);
	}
	
	@Test
	public void testNorm1() {
		assertEquals(1, v1.norm(), DELTA);
	}
	
	@Test
	public void testNorm2() {
		assertEquals(1, v2.norm(), DELTA);
	}
	
	@Test
	public void testNorm3() {
		assertEquals(18.70828693, v3.norm(), DELTA);
	}
	
	@Test
	public void testNormalized1() {
		testVector(new Vector3(1, 0, 0), v1.normalized());
	}
	
	@Test
	public void testNormalized2() {
		testVector(new Vector3(0, 1, 0), v2.normalized());
	}
	
	@Test
	public void testNormalized3() {
		testVector(new Vector3(0.2672612419, -0.5345224839, 0.8017837259), v3.normalized());
	}
	
	@Test
	public void testAdd1() {
		Vector3 result = v1.add(v2);
		testVector(new Vector3(1, 1, 0), result);
	}
	
	@Test
	public void testAdd2() {
		Vector3 result = v2.add(v3);
		testVector(new Vector3(5, -9, 15), result);
	}
	
	@Test
	public void testSub1() {
		Vector3 result = v1.sub(v2);
		testVector(new Vector3(1, -1, 0), result);
	}
	
	@Test
	public void testSub2() {
		Vector3 result = v2.sub(v3);
		testVector(new Vector3(-5, 11, -15), result);
	}
	
	@Test
	public void testDot1() {
		double result = v1.dot(v2);
		assertEquals(0, result, DELTA);
	}
	
	@Test
	public void testDot2() {
		double result = v2.dot(v3);
		assertEquals(-10, result, DELTA);
	}
	
	@Test
	public void testCross1() {
		Vector3 result = v1.cross(v2);
		testVector(new Vector3(0, 0, 1), result);
	}
	
	@Test
	public void testCross2() {
		Vector3 result = v2.cross(v3);
		testVector(new Vector3(15, 0, -5), result);
	}
	
	@Test
	public void testScale1() {
		Vector3 result = v1.scale(5);
		testVector(new Vector3(5, 0, 0), result);
	}
	
	@Test
	public void testScale2() {
		Vector3 result = v3.scale(0.5);
		testVector(new Vector3(2.5, -5, 7.5), result);
	}
	
	@Test
	public void testAngle1() {
		double result = v1.cosAngle(v2);
		assertEquals(0, result, DELTA);
	}
	
	@Test
	public void testAngle2() {
		double result = v2.cosAngle(v3);
		assertEquals(0.5345224838, result, DELTA);
	}
	
	@Test
	public void toStringTest() {
		assertEquals("(5,000000, -10,000000, 15,000000)", v3.toString()); //may be different according to localization settings
	}
	
	private void testVector(Vector3 expected, Vector3 actual) {
		assertArrayEquals(expected.toArray(), actual.toArray(), DELTA);
	}
	

}
