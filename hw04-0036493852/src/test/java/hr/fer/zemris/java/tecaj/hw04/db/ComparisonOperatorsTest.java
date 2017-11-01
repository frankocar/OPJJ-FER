package hr.fer.zemris.java.tecaj.hw04.db;

import static org.junit.Assert.*;

import org.junit.Test;

public class ComparisonOperatorsTest {

	@Test
	public void lessTest() {
		IComparisonOperator oper = ComparisonOperators.LESS;
		assertEquals(false, oper.satisfied("Ana", "Jasna"));
		assertEquals(true, oper.satisfied("Jasna", "Ana"));
	}
	
	@Test
	public void equalsTest() {
		IComparisonOperator oper = ComparisonOperators.EQUALS;
		assertEquals(true, oper.satisfied("Ana", "Ana"));
		assertEquals(false, oper.satisfied("Jasna", "Ana"));
	}
	
	@Test
	public void greaterTest() {
		IComparisonOperator oper = ComparisonOperators.GREATER;
		assertEquals(true, oper.satisfied("Ana", "Jasna"));
		assertEquals(false, oper.satisfied("Jasna", "Ana"));
	}
	
	@Test
	public void likeTest() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertEquals(false, oper.satisfied("Aba*", "Zagreb"));
		assertEquals(false, oper.satisfied("AA*AA", "AAA"));
		assertEquals(true, oper.satisfied("AA*AA", "AAAA"));
		assertEquals(true, oper.satisfied("Dar*o", "Darko"));
		assertEquals(true, oper.satisfied("Dark*", "Darko"));
		assertEquals(true, oper.satisfied("*arko", "Darko"));
		assertEquals(true, oper.satisfied("*ko", "Darko" ));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void multipleWildcardTest() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertEquals(false, oper.satisfied("Za*re*", "Zagreb"));
	}

}
