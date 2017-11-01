package hr.fer.zemris.java.hw02;

import static org.junit.Assert.*;

import org.junit.Test;

public class ComplexNumberTest {
	
	private final static double DELTA = 1E-6; 

	@Test
	public void constructorTest() {
		ComplexNumber c = new ComplexNumber(4.56, -2.45);
		if(c.getReal() != 4.56 && c.getImaginary() != -2.45) {
			fail("Number wasnt constructed proprerly");
		}
	}
	
	@Test
	public void getRealTest() {
		ComplexNumber c = new ComplexNumber(3.1415, 3.1415);
		assertEquals(3.1415, c.getReal(), DELTA);
	}
	
	@Test
	public void getImaginaryTest() {
		ComplexNumber c = new ComplexNumber(3.1415, 3.1415);
		assertEquals(3.1415, c.getImaginary(), DELTA);
	}
	
	@Test
	public void fromRealTest() {
		ComplexNumber c = ComplexNumber.fromReal(3.1415);
		if(c.getReal() != 3.1415 && c.getImaginary() != 0) {
			fail("fromReal() failed");
		}
	}
	
	@Test
	public void fromImaginaryTest() {
		ComplexNumber c = ComplexNumber.fromImaginary(3.1415);
		if(c.getReal() != 0 && c.getImaginary() != 3.1415) {
			fail("fromImaginary() failed");
		}
	}
	
	@Test
	public void fromMagnitudeAndAngleTest() {
		ComplexNumber c = ComplexNumber.fromMagnitudeAndAngle(12, 0.45);
		assertEquals(10.80536523, c.getReal(), DELTA);
		assertEquals(5.219586409, c.getImaginary(), DELTA);
		
		try {
			c = ComplexNumber.fromMagnitudeAndAngle(-4, 0.45);
			fail("An exception should have ben thrown");
		} catch(IllegalArgumentException ex) {
		}
		
		c = ComplexNumber.fromMagnitudeAndAngle(4, -56);
		assertEquals(3.412880431, c.getReal(), DELTA);
		assertEquals(2.086204008, c.getImaginary(), DELTA);
	}
	
	@Test
	public void parseTest() {
		ComplexNumber c = ComplexNumber.parse("3");
		if(c.getReal() != 3 && c.getImaginary() != 0) {
			fail("parse failed, should have been 3+0i");
		}
		
		c = ComplexNumber.parse("3.51");
		if(c.getReal() != 3.51 && c.getImaginary() != 0) {
			fail("parse failed, should have been 3.51+0i");
		}
		
		c = ComplexNumber.parse("-3.17");
		if(c.getReal() != -3.17 && c.getImaginary() != 0) {
			fail("parse failed, should have been -3.17+0i");
		}
		
		c = ComplexNumber.parse("-2.71i");
		if(c.getReal() != 0 && c.getImaginary() != -2.71) {
			fail("parse failed, should have been 0-2.71i");
		}
		
		c = ComplexNumber.parse("i");
		if(c.getReal() != 0 && c.getImaginary() != 1) {
			fail("parse failed, should have been 0+1i");
		}
		
		c = ComplexNumber.parse("1");
		if(c.getReal() != 1 && c.getImaginary() != 0) {
			fail("parse failed, should have been 1+0i");
		}
		
		c = ComplexNumber.parse("-2.71-3.15i");
		if(c.getReal() != -2.71 && c.getImaginary() != -3.15) {
			fail("parse failed, should have been -2.71-3.15i");
		}
		
		c = ComplexNumber.parse("- 2.71 - 3.15 i");
		if(c.getReal() != -2.71 && c.getImaginary() != -3.15) {
			fail("parse failed, should have been -2.71-3.15i");
		}
		
		c = ComplexNumber.parse("-8i+3.1415");
		if(c.getReal() != 3.1415 && c.getImaginary() != -8) {
			fail("parse failed, should have been 3.1415-8i");
		}
		
		try {
			c = ComplexNumber.parse("ff+gdh");
			fail("An exception should have been thrown");
		} catch(IllegalArgumentException ex) {
		}
		
		try {
			c = ComplexNumber.parse("");
			fail("An exception should have been thrown");
		} catch(IllegalArgumentException ex) {
		}	
	}
	
	@Test
	public void getMagnitudeTest() {
		ComplexNumber c = new ComplexNumber(5, 5);
		assertEquals(7.071067812, c.getMagnitude(), DELTA);		
		
		c = new ComplexNumber(-3.1415, 65);
		assertEquals(65.07587128, c.getMagnitude(), DELTA);
		
		c = new ComplexNumber(18, -5);
		assertEquals(18.68154169, c.getMagnitude(), DELTA);
	}
	
	@Test
	public void getAngleTest() {
		ComplexNumber c = new ComplexNumber(5, 5);
		assertEquals(0.7853981634, c.getAngle(), DELTA);		
		
		c = new ComplexNumber(-3.1415, 65);
		assertEquals(1.619089517, c.getAngle(), DELTA);
		
		c = new ComplexNumber(18, -5);
		assertEquals(-0.2709468503, c.getAngle(), DELTA);
	}
	
	@Test
	public void addTest() {
		ComplexNumber c1 = new ComplexNumber(3.1415, -3.1415);
		ComplexNumber c2 = new ComplexNumber(-2.71828, 2.71828);
		ComplexNumber c3 = c1.add(c2);
		
		assertEquals(0.42322, c3.getReal(), DELTA);
		assertEquals(-0.42322, c3.getImaginary(), DELTA);
	}
	
	@Test
	public void subTest() {
		ComplexNumber c1 = new ComplexNumber(3.1415, -3.1415);
		ComplexNumber c2 = new ComplexNumber(-2.71828, 2.71828);
		ComplexNumber c3 = c1.sub(c2);
		
		assertEquals(5.85978, c3.getReal(), DELTA);
		assertEquals(-5.85978, c3.getImaginary(), DELTA);
	}
	
	@Test
	public void mulTest() {
		ComplexNumber c1 = new ComplexNumber(3.1415, -3.1415);
		ComplexNumber c2 = new ComplexNumber(-2.71828, 2.71828);
		ComplexNumber c3 = c1.mul(c2);
		
		assertEquals(0, c3.getReal(), DELTA);
		assertEquals(17.07895324, c3.getImaginary(), DELTA);
		
		c1 = new ComplexNumber(2.4, -4.2);
		c2 = new ComplexNumber(-3.6, 6.3);
		c3 = c1.mul(c2);
		
		assertEquals(17.82, c3.getReal(), DELTA);
		assertEquals(30.24, c3.getImaginary(), DELTA);
	}
	
	@Test
	public void divTest() {
		ComplexNumber c1 = new ComplexNumber(3.1415, -3.1415);
		ComplexNumber c2 = new ComplexNumber(-2.71828, 2.71828);
		ComplexNumber c3 = c1.div(c2);
		
		assertEquals(-1.155694041, c3.getReal(), DELTA);
		assertEquals(0, c3.getImaginary(), DELTA);
		
		c1 = new ComplexNumber(2.4, -4.2);
		c2 = new ComplexNumber(-3.6, 6.3);
		c3 = c1.div(c2);
		
		assertEquals(-0.66666666, c3.getReal(), DELTA);
		assertEquals(0, c3.getImaginary(), DELTA);
		
		c1 = new ComplexNumber(2, 2);
		c2 = new ComplexNumber(5, -5);
		c3 = c1.div(c2);
		
		assertEquals(0, c3.getReal(), DELTA);
		assertEquals(0.4, c3.getImaginary(), DELTA);
		
		try {
			c1 = new ComplexNumber(2.4, -4.2);
			c2 = new ComplexNumber(0, 0);
			c3 = c1.div(c2);
			fail("An exception should have been thrown");
		} catch(IllegalArgumentException ex) {
		}
	}
	
	@Test 
	public void powerTest() {
		ComplexNumber c1 = new ComplexNumber(2, 2);		
		ComplexNumber c2 = c1.power(3);
		assertEquals(-16, c2.getReal(), DELTA);
		assertEquals(16, c2.getImaginary(), DELTA);
		
		c1 = new ComplexNumber(2, 2);		
		c2 = c1.power(0);
		assertEquals(1, c2.getReal(), DELTA);
		assertEquals(0, c2.getImaginary(), DELTA);
		
		c1 = new ComplexNumber(2.463, -4.643);		
		c2 = c1.power(2);
		assertEquals(-15.49108, c2.getReal(), DELTA);
		assertEquals(-22.871418, c2.getImaginary(), DELTA);
		
		try {
			c2 = c1.power(-1);
			fail("An exception should have been thrown");
		} catch(IllegalArgumentException ex) {
		}
	}
	
	@Test
	public void rootTest() {
		ComplexNumber c1 = new ComplexNumber(2, 2);		
		ComplexNumber[] c2 = c1.root(2);
		assertEquals(1.5537739, c2[0].getReal(), DELTA);
		assertEquals(0.6435942, c2[0].getImaginary(), DELTA);
				
		c1 = new ComplexNumber(2.463, -4.643);		
		c2 = c1.root(3);
		assertEquals(3, c2.length);
		assertEquals(1.626576850, c2[0].getReal(), DELTA);
		assertEquals(-0.61414691, c2[0].getImaginary(), DELTA);		
		assertEquals(-0.281422, c2[1].getReal(), DELTA*10);
		assertEquals(1.71573, c2[1].getImaginary(), DELTA*10);		
		assertEquals(-1.34516, c2[2].getReal(), DELTA*10);
		assertEquals(-1.10158, c2[2].getImaginary(), DELTA*10);
		
		try {
			c2 = c1.root(0);
			fail("An exception should have been thrown");
		} catch(IllegalArgumentException ex) {
		}
	}
	
	@Test
	public void toStringTest() {
		ComplexNumber c = new ComplexNumber(3.1415, -2.71828);
		
		if(!c.toString().equals(String.format("%.3f%.3fi", 3.1415, -2.71828))) {
			fail(String.format("Value should be '%.3f%.3fi', its '%s'", 3.1415, -2.71828, c.toString()));
		}
		
		c = new ComplexNumber(3.1415, 2.71828);
		
		if(!c.toString().equals(String.format("%.3f+%.3fi", 3.1415, 2.71828))) {
			fail(String.format("Value should be '%.3f+%.3fi', its '%s'", 3.1415, 2.71828, c.toString()));
		}
		
	}

}
