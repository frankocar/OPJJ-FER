package hr.fer.zemris.java.custom.collections;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class LinkedListIndexedCollectionTest {

private LinkedListIndexedCollection col;
	
	@Before
	public void setUp() throws Exception {
		col = new LinkedListIndexedCollection();

		for(int i = 0; i < 20; i++) {
			col.add(i);
		}		
	}
	
	@Test
	public void sizeTest() {
		//checks if setup method worked and tests size
		if(col.size() != 20) {
			fail(String.format("Size should be 20, it's %d", col.size()));
		}
	}
	
	@Test
	public void isEmptyTest() {
		if(col.isEmpty()) {
			fail("Collection shouldn't be empty");
		}
		
		LinkedListIndexedCollection test = new LinkedListIndexedCollection();
		
		if(!test.isEmpty()) {
			fail("Collection should be empty");
		}
	}
	
	@Test
	public void containsTest() {
		if(!col.contains(3)) {
			fail("Collection should contain 3");
		}
		
		if(!col.contains(0)) {
			fail("Collection should contain 0");
		}
		
		if(col.contains(24)) {
			fail("Collection shouldn't contain 24");
		}
		
		if(col.contains(-24)) {
			fail("Collection shouldn't contain -24");
		}
		
		if(col.contains(null)) {
			fail("Collection shouldn't contain null");
		}
	}
	
	@Test
	public void addTest() {		
		col.add(20);
		
		if(!col.contains(20)) {
			fail("20 should have been added");
		}
		
		try {
			col.add(null);
			fail("Exception should have been thrown");
		} catch (IllegalArgumentException ex) {			
			if(col.contains(null)) {
				fail("'null' shouldn't be in a collection");
			}
		}		
	}
	
	@Test
	public void removeThatReturnsBooleanTest() {
		col.add(50);
		
		if(!col.contains(50)) {
			fail("50 wasn't added");
		}
		
		if(col.remove(Integer.valueOf(50))) { //make sure to send number as an object to use the right method
			if(col.contains(50)) {
				fail("50 should have been removed");
			}
		}
		
		if(col.remove(Integer.valueOf(0))) { //make sure to send number as an object to use the right method
			if(col.contains(0)) {
				fail("0 should have been removed");
			}
		}
		
		if(col.remove(Integer.valueOf(40))) { 
			fail("Collection didn't even contain 40");
		}

	}
	
	@Test
	public void toArrayTest() {
		Object[] array = col.toArray();
		
		if(array.length != col.size()) {
			fail("Array and collection should be the same size");
		}
		
		for(int i = 0; i < array.length; i++) {
			if(col.get(i) != array[i]) {
				fail("Array is not the same as the collection");
			}
		}
		
		array = new LinkedListIndexedCollection().toArray();
		if(array == null) {
			fail("Array mustn't be null");
		}
	}
	
	@Test
	public void forEachTest() {		
		Integer[] test = new Integer[col.size()];
		
		class TestProcessor extends Processor {
			private int i = 0;
			
			@Override
			public void process(Object value) {
				test[i] = (Integer) value;
				i++;
			}
		}
		
		Processor processor = new TestProcessor();
		col.forEach(processor);
		
		for (int i = 0; i < test.length; i++) {
			if (col.get(i) != test[i]) {
				fail("New array is not the same as the collection");
			}
		}
	}
	
	@Test
	public void addAllTest() {
		LinkedListIndexedCollection col2 = new LinkedListIndexedCollection();
		for (int i = 20; i < 40; i++) {
			col2.add(i);
		}
		
		col.addAll(col2);
		
		for(int i = 0; i < 40; i++) {
			if ((int)col.get(i) != i) {
				fail("addAll didn't work");
			}
		}
	}
	
	@Test
	public void clearTest() {
		col.clear();
		
		if(!col.isEmpty()) {
			fail("Collection should be empty");
		}
	}
	
	@Test
	public void getTest() {		
		for(int i = 0; i < 20; i++) {
			if ((int)col.get(i) != i) {
				fail(String.format("Number %d should be at index %d", i, i));
			}
		}
		
		try {
			col.get(col.size());
			fail("Exception should have been thrown");
		} catch(IndexOutOfBoundsException ex) {			
		}
		
		try {
			col.get(-1);
			fail("Exception should have been thrown");
		} catch(IndexOutOfBoundsException ex) {			
		}
	}
	
	@Test
	public void insertTest() {
		try {
			col.insert(null, 5);
			fail("Exception should have been thrown");
		} catch (IllegalArgumentException ex) {
		}
		
		try {
			col.insert(5, 25);
			fail("Exception should have been thrown");
		} catch (IndexOutOfBoundsException ex) {
		}
		
		col.insert(25, 5);
		
		if((int)col.get(5) != 25) {
			fail("Insertion failed");
		}
		
		if((int)col.get(6) != 5) {
			fail("Shift right failed");
		}
		
		if(col.size() != 21 && (int)col.get(20) != 19) {
			fail("Insertion failed");
		}
	}
	
	@Test
	public void indexOfTest() {
		if(col.indexOf(30) >= 0) {
			fail("30 shouldn't have been found");
		}
		
		if(col.indexOf(15) != 15) {
			fail("15 should have been at index 15");
		}
		
		col.clear();
		
		if(col.indexOf(0) >= 0) {
			fail("List should be empty");
		}
	}
	
	@Test
	public void removeThatRemovesIndexTest() {
		try {
			col.remove(-1);
			fail("Exception should have been thrown");
		} catch (IndexOutOfBoundsException ex) {
		}
		
		try {
			col.remove(col.size());
			fail("Exception should have been thrown");
		} catch (IndexOutOfBoundsException ex) {
		}
				
		col.remove(10);
		if((int)col.get(10) == 10) {
			fail("10 was not removed");
		}		
		
		if((int)col.get(10) != 11) {
			fail("Array wasn't shifted");
		}
		
		if((int)col.get(9) != 9) {
			fail("Array wasn't shifted properly");
		}
		
		if(col.size() == 20) {
			fail("Size didn't change");
		}
		
		col.clear();
		
		
	}

}
