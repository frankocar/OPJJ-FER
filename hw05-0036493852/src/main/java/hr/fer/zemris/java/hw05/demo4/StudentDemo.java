package hr.fer.zemris.java.hw05.demo4;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A sample program that will read the list of students from "./studenti.txt" file
 * parse them as {@link StudentRecord} object, and then sort them in various ways.
 * 
 * @author Franko Car
 *
 */
public class StudentDemo {
	
	/**
	 * A main method
	 * 
	 * @param args None required
	 */
	public static void main(String[] args) {
		List<String> lines;
		List<StudentRecord> records = null;
		
		try {
			lines = getDataFromFile("./studenti.txt");
			records = convert(lines);
		} catch (IllegalArgumentException ex) {
			System.out.println("Error: " + ex.getMessage());
			System.exit(1);
		}
		
		System.out.println("Number of students with 25 or more points: " + vratiBodovaViseOd25(records));
		
		System.out.println("Number of students with final grade 5: " + vratiBrojOdlikasa(records));
		
		System.out.println();
		System.out.println("Students with final grade 5: ");
		vratiListuOdlikasa(records).forEach(System.out::println);
		
		System.out.println();
		System.out.println("Sorted students with final grade 5: ");
		vratiSortiranuListuOdlikasa(records).forEach(System.out::println);	
		
		System.out.println();
		System.out.println("Failed JMBAGs: ");
		vratiPopisNepolozenih(records).forEach(System.out::println);
		
		System.out.println();
		System.out.println("Students sorted by grades: ");
		for (Map.Entry<Integer, List<StudentRecord>> entry : razvrstajStudentePoOcjenama(records).entrySet()) {
			entry.getValue().forEach(System.out::println);		
		}
		
		System.out.println();
		System.out.println("Number of students with each grade:");
		for (Map.Entry<Integer, Integer> entry : vratiBrojStudenataPoOcjenama(records).entrySet()) {
			System.out.printf("Grade: %d  Number of students: %d%n", entry.getKey(), entry.getValue());
		}
		
		System.out.println();
		System.out.println("Students sorted by fail/pass:");
		for (Map.Entry<Boolean, List<StudentRecord>> entry : razvrstajProlazPad(records).entrySet()) {
			entry.getValue().forEach(s -> System.out.printf("%s %6s |%n", s, entry.getKey() ? "passed" : "failed"));
			
		}
	}
	
	/**
	 * A method to return the number of students with the total score higer than 25
	 * 
	 * @param records
	 * @return Long number of students
	 */
	private static long vratiBodovaViseOd25(List<StudentRecord> records) {
		return records.
				stream().
				filter(s -> (s.getTotalScore()) > 25).
				count();
	}
	
	/**
	 * A method to return the number of students with the final grade 5
	 * 
	 * @param records
	 * @return Long number of students
	 */
	private static long vratiBrojOdlikasa(List<StudentRecord> records) {
		return records.
				stream().
				filter(s -> s.getFinalGrade() == 5).
				count();
	}
	
	/**
	 * A method to return the sorted list of best students
	 * 
	 * @param records
	 * @return List of students with grade 5
	 */
	private static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> records) {
		return records.
				stream().
				filter(s -> s.getFinalGrade() == 5).
				collect(Collectors.toList());
	}
	
	/**
	 * A method to return the sorted list of best students
	 * 
	 * @param records
	 * @return Sorted list of students with grade 5
	 */
	private static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records) {
		return records.
				stream().
				filter(s -> s.getFinalGrade() == 5).
				sorted((s1, s2) -> Double.compare(s2.getTotalScore(), s1.getTotalScore())).
				collect(Collectors.toList());
	}
	
	/**
	 * A method to return the list of failed JMBAGs
	 * 
	 * @param records
	 * @return List of strings
	 */
	private static List<String> vratiPopisNepolozenih(List<StudentRecord> records) {
		return records.
				stream().
				filter(s -> s.getFinalGrade() == 1).
				sorted((s1, s2) -> s1.getJmbag().compareTo(s2.getJmbag())).
				map(s -> s.getJmbag()).
				collect(Collectors.toList());
	}
	
	/**
	 * A method to sort students by grades
	 * 
	 * @param records
	 * @return Map with an integer key and a list value
	 */
	private static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> records) {
		return records.
				stream().
				collect(Collectors.groupingBy(StudentRecord::getFinalGrade));
	}
	
	/**
	 * A method to calculate the number of students with each grade
	 * 
	 * @param records
	 * @return Map with an integer key and an integer value
	 */
	private static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> records) {
		return records.
				stream().
				collect(Collectors.toMap(
							StudentRecord::getFinalGrade, 
							v -> 1, 
							(k, v) -> k = k + 1)
						);
	}
	
	/**
	 * A method to sort by pass/fail
	 * 
	 * @param records
	 * @return Map with a boolean key and a list value
	 */
	private static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> records) {
		return records.
				stream().
				collect(Collectors.partitioningBy(s -> s.getFinalGrade() >= 2));
	}
	
	
	/**
	 * A method to parse a list of strings to list of StudentRecord objects
	 * 
	 * @param lines List of strings
	 * @return List of StudentRecord objects
	 */
	private static List<StudentRecord> convert(List<String> lines) {
		List<StudentRecord> records = new ArrayList<>();
		
		for (String line : lines) {
			String[] fields = line.split("[^\\S ]");
			
			if (line.isEmpty()) {
				continue;
			}
			
			if (fields.length != 7) {
				throw new IllegalArgumentException(String.format("Invalid entry - unexpected number of elements:%n    '%s'%n", line));
			}
			
			try {
				records.add(new StudentRecord(
						fields[0],
						fields[1],
						fields[2],
						Double.parseDouble(fields[3]),
						Double.parseDouble(fields[4]),
						Double.parseDouble(fields[5]),
						Integer.parseInt(fields[6])
						));				
			} catch (NumberFormatException ex) {
				throw new IllegalArgumentException(String.format("Invalid entry - number format is incorrect:%n    '%s'%n", line), ex);
			}
		}
		
		return records;
	}

	/**
	 * A method that will read the file at the given path and return 
	 * its lines as a list
	 * 
	 * @param path Path of a file
	 * @return List of lines of a file
	 */
	private static List<String> getDataFromFile(String path) {
		List<String> lines = null;
		
		try {
			lines = Files.readAllLines(
				Paths.get(path),
				StandardCharsets.UTF_8
			);
		} catch (IOException e) {
			throw new IllegalArgumentException("Couldn't read input file.", e);
		}
		
		return lines;
	}
	
}
