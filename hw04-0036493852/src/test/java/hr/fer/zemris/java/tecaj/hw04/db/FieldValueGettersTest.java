package hr.fer.zemris.java.tecaj.hw04.db;

import static org.junit.Assert.*;

import org.junit.Test;

public class FieldValueGettersTest {
	
	private StudentRecord testRecord = new StudentRecord("0036493852", "Car", "Franko", 5);
	
	@Test
	public void firstNameTest() {
		assertEquals("Franko", FieldValueGetters.FIRST_NAME.get(testRecord));
	}
	
	@Test
	public void lastNameTest() {
		assertEquals("Car", FieldValueGetters.LAST_NAME.get(testRecord));
	}
	
	@Test
	public void jmbagTest() {
		assertEquals("0036493852", FieldValueGetters.JMBAG.get(testRecord));
	}

}
