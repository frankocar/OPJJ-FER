package hr.fer.zemris.java.tecaj.hw04.db;

import static org.junit.Assert.*;

import org.junit.Test;

public class QueryParserTest {

	@Test
	public void firstExampleTest() {
		QueryParser qp1 = new QueryParser(" jmbag =\"0123456789\" ");
		
		assertEquals(true, qp1.isDirectQuery());
		assertEquals("0123456789", qp1.getQueriedJMBAG());
		assertEquals(1, qp1.getQuery().size());
	}
	
	@Test(expected=IllegalStateException.class)
	public void secondExampleTest() {		
		QueryParser qp2 = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");		
		
		System.out.println(qp2.getQueriedJMBAG()); // would throw!
	}
	
	@Test
	public void complexQueryTest() {		
		QueryParser qp2 = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");	
		
		assertEquals(false, qp2.isDirectQuery());
		assertEquals(2, qp2.getQuery().size());
	}

}
