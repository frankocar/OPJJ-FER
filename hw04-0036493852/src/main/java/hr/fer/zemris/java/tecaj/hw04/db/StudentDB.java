package hr.fer.zemris.java.tecaj.hw04.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A program used to create a database and interface with it.
 * It can accept a path to the database file described in
 * {@link StudentDatabase} as a command line argument or use
 * a default database.txt located in project root.
 * 
 * @author Franko Car
 *
 */
public class StudentDB {

	/**
	 * A main method that starts the program
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		String path = "./database.txt";
		
		if (args.length == 1) {
			path = args[0];
		}
		
		StudentDatabase db = new StudentDatabase(getDataFromFile(path));
		Scanner sc = new Scanner(System.in);
		
		System.out.printf("Using database file '%s'.%n", path);
		while (true) {
			System.out.print("> ");
			String input = sc.nextLine().trim();
			
			if (input.toLowerCase().equals("exit")) {
				System.out.println("Goodbye!");
				break;
			}
			
			if (!input.startsWith("query")) {
				System.out.println("Invalid command, must start with query");
				System.out.println();
				continue;
			}			
			
			QueryParser parser;
			List<StudentRecord> filteredRecords;
			try {
				parser = new QueryParser(input.substring(5)); //substring to remove 'query'
				filteredRecords = findFilteredRecords(db, parser);
			} catch (IllegalArgumentException | NullPointerException e) {
				System.out.println("Error: " + e.getMessage());
				System.out.println();
				continue;
			}
			
			if (filteredRecords.size() > 0) {
				printTable(filteredRecords);
			}
			
			System.out.println("Records selected: " + filteredRecords.size());
			System.out.println();
		}
		
		sc.close();
				
	}
	
	/**
	 * A method that will print out a table with given records
	 * 
	 * @param filteredRecords List of records to print
	 */
	private static void printTable(List<StudentRecord> filteredRecords) {
		
		int[] maxWidth = {0,0,0,0};
		
		for (StudentRecord record : filteredRecords) {			
			maxWidth[0] = Math.max(maxWidth[0], record.getJmbag().length());
			maxWidth[1] = Math.max(maxWidth[1], record.getLastName().length());
			maxWidth[2] = Math.max(maxWidth[2], record.getFirstName().length());
			maxWidth[3] = Math.max(maxWidth[3], Integer.toString(record.getFinalGrade()).length());
		}
		
		printHeader(maxWidth);
		
		for (StudentRecord record : filteredRecords) {
			StringBuilder sb = new StringBuilder();
			sb.append('|');
			sb.append(String.format(" %-" + maxWidth[0] + "s |", record.getJmbag()));
			sb.append(String.format(" %-" + maxWidth[1] + "s |", record.getLastName()));
			sb.append(String.format(" %-" + maxWidth[2] + "s |", record.getFirstName()));
			sb.append(String.format(" %-" + maxWidth[3] + "s |", record.getFinalGrade()));
			System.out.println(sb.toString());
		}
		
		printHeader(maxWidth);
		
		
	}
	
	

	/**
	 * A method that will print out first and last lines of the table.
	 * 
	 * @param maxWidth Array of integers containing the required width of every column
	 */
	private static void printHeader(int[] maxWidth) {
		System.out.print('+');
		for (int max : maxWidth) {
			for (int i = -1; i <= max; i++) {
				System.out.print('=');
			}
			System.out.print('+');
		}
		System.out.println();
		
	}

	/**
	 * A method that fill find records that satisfy the query and return them as a list
	 * 
	 * @param db Database to search
	 * @param parser Parser for a query
	 * @return List of records
	 */
	private static List<StudentRecord> findFilteredRecords(StudentDatabase db, QueryParser parser) {
		List<StudentRecord> filtered;
		
		if (parser.isDirectQuery()) {
			filtered = new ArrayList<>();
			StudentRecord temp = db.forJMBAG(parser.getQueriedJMBAG());
			if (temp != null) {
				filtered.add(temp);
			}
			System.out.println("Using index for record retrieval.");
			return filtered;
		}
		
		return db.filter(new QueryFilter(parser.getQuery()));
		
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
			System.out.println("Couln't read input file. Make sure that you input "
					+ "the path to the database file as an argumet or to store it "
					+ "as database.txt in project root.");
			System.exit(1);
		}
		
		return lines;
	}
	
}
