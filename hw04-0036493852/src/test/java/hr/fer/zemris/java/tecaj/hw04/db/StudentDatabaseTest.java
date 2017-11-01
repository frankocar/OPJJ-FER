package hr.fer.zemris.java.tecaj.hw04.db;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class StudentDatabaseTest {

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
	public void buildTest() {
		if (db.size() != 63) {
			fail("Size should be 63, was " + db.size());
		}
	}
	
	@Test
	public void indexedAccessTest() {
		assertEquals("Dean", db.forJMBAG("0000000009").getLastName());
	}
	
	@Test
	public void complexNameTest() {
		assertEquals("Glavinić Pecotić", db.forJMBAG("0000000015").getLastName());
	}
	
	@Test
	public void invalidIndexTest() {
		assertEquals(null, db.forJMBAG("000009"));
	}
	
	@Test
	public void trueFilter() {
		List<StudentRecord> temp = db.filter((x) -> true);
		assertEquals(db.size(), temp.size());
	}
	
	@Test
	public void falseFilter() {
		List<StudentRecord> temp = db.filter((x) -> false);
		assertEquals(0, temp.size());
	}

}
