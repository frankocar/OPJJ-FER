package hr.fer.zemris.java.tecaj.hw04.db;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw04.collections.SimpleHashtable;

/**
 * A class to store data read from a file. It keeps an indexed list
 * od {@link StudentRecord} objects and allows for O(1) access using JMBAG
 * as a key. 
 * 
 * Input file needs to be formated with one entry per line, and with one or
 * more tabs between separate values, given in order: JMBAG, lastName, firstName, finalGrade
 * 
 * @author Franko Car
 *
 */
public class StudentDatabase {

	/**
	 * List of StudentRecords
	 */
	private List<StudentRecord> records;
	/**
	 * A map keeping indexed references to student records
	 */
	private SimpleHashtable<String, StudentRecord> index;
		
	/**
	 * A constructor that will build a database from a given list of 
	 * correctly formatted strings
	 * 
	 * @param lines A list of strings
	 */
	public StudentDatabase(List<String> lines) {
		records = new ArrayList<>();
		index = new SimpleHashtable<>();
		
		for (int i = 0, n = lines.size(); i < n; i++) {
			String[] fields = lines.get(i).split("[^\\S ]"); //splits by all whitespace that isn't a simple space
			if (fields.length != 4) {
				System.out.printf("Invalid input line: %n  Line: %d - '%s'%n  Skipped%n", i + 1, lines.get(i));
				continue;
			}
			
			try {
				if (index.containsKey(fields[0])) {
					continue;
				}
				
				StudentRecord record = new StudentRecord(fields[0],	fields[1],fields[2], Integer.parseInt(fields[3]));
				records.add(record);
				index.put(fields[0], record);
			} catch (NumberFormatException ex) {
				System.out.printf("Invalid grade format '%s' in line %d. Skipped%n", fields[3], i + 1);
			} catch (IllegalArgumentException ex) {
				System.out.printf("Invalid argument given in line: %n  Line: %d - '%s'%n  Skipped.%n", i + 1, lines.get(i));
			}
			
			
		}
	}
	
	/**
	 * Get an entry in O(1) time when JMBAG is known
	 * 
	 * @param jmbag JMBAG of an entry
	 * @return Entry with a given JMBAG
	 */
	public StudentRecord forJMBAG(String jmbag) {
		StudentRecord record = index.get(jmbag);
		
		if (record == null) {
			return null;
		}
		
		return record;
	}
	
	/**
	 * A method that will return a list of filtered records
	 * according to the given {@link IFilter} implementation
	 * 
	 * @param filter Implementation of a filter strategy
	 * @return A list of filtered records
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> temp = new LinkedList<>();
		
		for (StudentRecord record : records) {
			if (filter.accepts(record)) {
				temp.add(record);
			}
		}
		
		return temp;
	}
	
	/**
	 * Returns the size of the database
	 * 
	 * @return int size
	 */
	public int size() {
		return records.size();
	}
	
	/**
	 * A getter for the list of recors
	 * 
	 * @return List<StudentRecords>
	 */
	public List<StudentRecord> getRecords() {
		return records;
	}
	
	
}
