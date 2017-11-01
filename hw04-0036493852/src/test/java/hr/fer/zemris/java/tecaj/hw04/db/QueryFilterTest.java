package hr.fer.zemris.java.tecaj.hw04.db;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class QueryFilterTest {

	private StudentDatabase db;
	
	@Before
	public void setUp() {
		List<String> lines = null;
		
		try {
			lines = Files.readAllLines(
				Paths.get("./database.txt"),
				StandardCharsets.UTF_8
			);
		} catch (IOException e) {
			System.out.println("Couln't read input file. Terminating.");
			System.exit(1);
		}
		
		db = new StudentDatabase(lines);
	}
	
	@Test
	public void directQueryTest() {
		QueryParser parser = new QueryParser("jmbag=\"0000000015\"");
		if (parser.isDirectQuery()) {
			StudentRecord r = db.forJMBAG(parser.getQueriedJMBAG());
			assertEquals("Kristijan", r.getFirstName());
			assertEquals("Glavinić Pecotić", r.getLastName());
			assertEquals(4, r.getFinalGrade());
		} else {
			fail("Was a direct query");
		}
	}
	
	@Test
	public void simpleIndirectQueryTest() {
		QueryParser parser = new QueryParser("			lastName    LIKE \"Bo*i\"");
		if (parser.isDirectQuery()) {
			fail("Wasn't a direct query");
		} else {
			for (StudentRecord r : db.filter(new QueryFilter(parser.getQuery()))) {
				assertEquals("Marin", r.getFirstName());
				assertEquals("Božić", r.getLastName());
				assertEquals(5, r.getFinalGrade());;
			}
		}
	}
	
	@Test
	public void indirectQueryTest() {
		QueryParser parser = new QueryParser("			lastName    LIKE \"B*\" aNd firstName>=\"P\"");
		if (parser.isDirectQuery()) {
			fail("Wasn't a direct query");
		} else {
			for (StudentRecord r : db.filter(new QueryFilter(parser.getQuery()))) {
				assertEquals("Petra", r.getFirstName());
				assertEquals("Bakamović", r.getLastName());
				assertEquals(3, r.getFinalGrade());;
			}
		}
	}

}
