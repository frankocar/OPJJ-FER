package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.Assert.*;

import java.util.EmptyStackException;

import org.junit.Test;

public class ObjectMultistackTest {

	private static final double DELTA = 1E-6;
	
	@Test
	public void test() {
		ObjectMultistack stack = new ObjectMultistack();
		
		stack.push("Numbers", new ValueWrapper(1));
		assertEquals(false, stack.isEmpty("Numbers"));
		assertEquals(true, stack.isEmpty("Letters"));
		
		assertEquals(Integer.valueOf(1), (Integer) stack.peek("Numbers").getValue());
		assertEquals(false, stack.isEmpty("Numbers"));
		
		assertEquals(Integer.valueOf(1), (Integer) stack.pop("Numbers").getValue());
		assertEquals(true, stack.isEmpty("Numbers"));
		
		for (int i = 1; i <= 5; i++) {
			stack.push("Numbers", new ValueWrapper(i));			
		}
		
		for (int i = 5; i >= 1; i--) {
			assertEquals(Integer.valueOf(i), stack.pop("Numbers").getValue());
		}
		
	}
	
	@Test(expected=EmptyStackException.class)
	public void emptyStackPop() {
		ObjectMultistack stack = new ObjectMultistack();		
		stack.pop("Hello?");
	}
	
	@Test(expected=EmptyStackException.class)
	public void emptyStackPeek() {
		ObjectMultistack stack = new ObjectMultistack();		
		stack.peek("Hello?");
	}
	
	@Test(expected=IllegalArgumentException.class) 
	public void addNullName() {
		ObjectMultistack stack = new ObjectMultistack();		
		stack.push(null, new ValueWrapper(1));
	}
	
	@Test(expected=IllegalArgumentException.class) 
	public void addNullValue() {
		ObjectMultistack stack = new ObjectMultistack();		
		stack.push("Name", null);
	}
	
	@Test
	public void additionTest() {
		ObjectMultistack stack = new ObjectMultistack();
		
		stack.push("Numbers", new ValueWrapper(12));
		stack.push("Numbers", new ValueWrapper(8));
		
		ValueWrapper number = stack.pop("Numbers");
		stack.peek("Numbers").add(number.getValue());
		assertEquals(Integer.valueOf(20), (Integer) stack.pop("Numbers").getValue());
		assertEquals(true, stack.isEmpty("Numbers"));
	}
	
	@Test
	public void subtractionTest() {
		ObjectMultistack stack = new ObjectMultistack();
		
		stack.push("Numbers", new ValueWrapper(12));
		stack.push("Numbers", new ValueWrapper(8));		
		
		ValueWrapper number = stack.pop("Numbers");
		stack.peek("Numbers").subtract(number.getValue());
		assertEquals(Integer.valueOf(4), (Integer) stack.pop("Numbers").getValue());
		assertEquals(true, stack.isEmpty("Numbers"));
	}
	
	@Test
	public void multiplicationTest() {
		ObjectMultistack stack = new ObjectMultistack();
		
		stack.push("Numbers", new ValueWrapper(12));
		stack.push("Numbers", new ValueWrapper(4));		
		
		ValueWrapper number = stack.pop("Numbers");
		stack.peek("Numbers").multiply(number.getValue());
		assertEquals(Integer.valueOf(48), (Integer) stack.pop("Numbers").getValue());
		assertEquals(true, stack.isEmpty("Numbers"));
		
		
		stack.push("Numbers", new ValueWrapper(12.2));
		stack.push("Numbers", new ValueWrapper("4"));		
		
		number = stack.pop("Numbers");
		stack.peek("Numbers").multiply(number.getValue());
		assertEquals(48.8, (Double) stack.pop("Numbers").getValue(), DELTA);
		assertEquals(true, stack.isEmpty("Numbers"));
	}
	
	@Test
	public void divisionTest() {
		ObjectMultistack stack = new ObjectMultistack();
		
		stack.push("Numbers", new ValueWrapper(12));
		stack.push("Numbers", new ValueWrapper(4));		
		
		ValueWrapper number = stack.pop("Numbers");
		stack.peek("Numbers").divide(number.getValue());
		assertEquals(Integer.valueOf(3), (Integer) stack.pop("Numbers").getValue());
		assertEquals(true, stack.isEmpty("Numbers"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void divisionWithZeroTest() {
		ObjectMultistack stack = new ObjectMultistack();
		
		stack.push("Numbers", new ValueWrapper(12));
		stack.push("Numbers", new ValueWrapper(null));		
		
		ValueWrapper number = stack.pop("Numbers");
		stack.peek("Numbers").divide(number.getValue());
	}
	
	@Test
	public void nullAdditionTest() {
		ObjectMultistack stack = new ObjectMultistack();
		
		stack.push("Numbers", new ValueWrapper(null));
		stack.push("Numbers", new ValueWrapper(8));
		
		ValueWrapper number = stack.pop("Numbers");
		stack.peek("Numbers").add(number.getValue());
		assertEquals(Integer.valueOf(8), (Integer) stack.pop("Numbers").getValue());
		assertEquals(true, stack.isEmpty("Numbers"));
	}
	
	@Test
	public void multiipleStacks() {
		ObjectMultistack stack = new ObjectMultistack();
		
		stack.push("First", new ValueWrapper(null));
		stack.push("Second", new ValueWrapper(8));
	
		stack.peek("First").add(stack.pop("Second").getValue());
		assertEquals(Integer.valueOf(8), (Integer) stack.peek("First").getValue());
		assertEquals(true, stack.isEmpty("Second"));
		assertEquals(false, stack.isEmpty("First"));
	}
	
	@Test
	public void givenExample() {
		ObjectMultistack stack = new ObjectMultistack();
		
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		stack.push("year", year);
		assertEquals(Integer.valueOf(2000), (Integer) stack.peek("year").getValue());
		
		ValueWrapper price = new ValueWrapper(200.51);
		stack.push("price", price);
		assertEquals(200.51, (Double) stack.peek("price").getValue(), DELTA);
		
		stack.push("year", new ValueWrapper(Integer.valueOf(1900)));
		assertEquals(Integer.valueOf(1900), (Integer) stack.peek("year").getValue());
		
		stack.peek("year").setValue(((Integer) stack.peek("year").getValue()).intValue() + 50);
		assertEquals(Integer.valueOf(1950), (Integer) stack.peek("year").getValue());
		
		stack.pop("year");
		assertEquals(Integer.valueOf(2000), (Integer) stack.peek("year").getValue());
		
		stack.peek("year").add("5");
		assertEquals(Integer.valueOf(2005), (Integer) stack.peek("year").getValue());
		
		stack.peek("year").add(5);
		assertEquals(Integer.valueOf(2010), (Integer) stack.peek("year").getValue());
		
		stack.peek("year").add(5.0);
		assertEquals(2015.0, (Double) stack.peek("year").getValue(), DELTA);
	}

}
