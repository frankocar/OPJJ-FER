package hr.fer.zemris.java.hw04.collections;

import static org.junit.Assert.*;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import hr.fer.zemris.java.hw04.collections.SimpleHashtable.TableEntry;

public class SimpleHashtableTest {

	private SimpleHashtable<String, Integer> examMarks;
	
	@Before
	public void setUp() {
		examMarks = new SimpleHashtable<>(2);
		
		examMarks.put("Ivana", Integer.valueOf(2));
		examMarks.put("Ante", Integer.valueOf(2));
		examMarks.put("Jasna", Integer.valueOf(2));
		examMarks.put("Kristina", Integer.valueOf(5));
	}
	
	@Test
	public void setupAndPutTest() {		
		assertEquals(4, examMarks.size());
		assertEquals((int) 2, (int) examMarks.get("Ivana"));

		if (!examMarks.containsKey("Jasna")) {
			fail("'Jasna? should be in the collection");
		}
		
		if (!examMarks.containsValue(5)) {
			fail("Value '5' should be contained");
		}
	}
	
	@Test
	public void overwriteTest() {
		assertEquals((int) 2, (int) examMarks.get("Ivana")); 
		examMarks.put("Ivana", Integer.valueOf(5));
		assertEquals((int) 5, (int) examMarks.get("Ivana")); 
	}
	
	@Test
	public void removeTest() {
		if (!examMarks.containsKey("Ante")) {
			fail("'Ante' should be present");
		}
		assertEquals(4, examMarks.size());
		
		examMarks.remove("Ante");
		
		if (examMarks.containsKey("Ante")) {
			fail("'Ante' shouldn't be present anymore");
		}
		assertEquals(3, examMarks.size());
	}
	
	@Test
	public void getEmptyTest() {
		assertEquals(null, examMarks.get("Štefica"));
	}
	
	@Test(expected=ConcurrentModificationException.class)
	public void concurrentModificationTest() {
		Iterator<TableEntry<String, Integer>> it = examMarks.iterator();
		while (it.hasNext()) {
			it.next();
			examMarks.remove("Ante");			
		}
	}
	
	@Test
	public void iteratorRemoveAll() {
		assertEquals(4, examMarks.size());
		
		Iterator<TableEntry<String, Integer>> it = examMarks.iterator();
		while (it.hasNext()) {
			it.next();
			it.remove();			
		}
		
		assertEquals(0, examMarks.size());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void putNull() {
		examMarks.put(null, 0);
	}
	
	@Test
	public void clearTest() {
		examMarks.clear();
		assertEquals(0, examMarks.size());
	}

	@Test(expected=NoSuchElementException.class)
	public void iteratingTooMuchTest() {
		Iterator<TableEntry<String, Integer>> it = examMarks.iterator();
		for (int i = 0; i <= 5; i++) {
			it.next();
		}
	}
	
	@Test(expected=IllegalStateException.class)
	public void iteratorRemoveTwiceTest() {
		Iterator<TableEntry<String, Integer>> it = examMarks.iterator();
		it.next();
		it.remove();
		it.remove();
	}

}
