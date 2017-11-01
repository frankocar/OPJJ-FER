package hr.fer.zemris.java.hw16.trazilica;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Console application that searches the content of all text files found in 
 * a given directory and in its subdirectories. It takes in one argument, a path
 * to the directory in which to search. It has the following functions:
 * 
 *  query [searchQuery] - seaches for a given query
 *  results - prints out the last set of results
 *  type [resultIndex] - prints the content of a result on a given index
 *  exit - exits the application
 * 
 * @author Franko Car
 *
 */
public class Konzola {
	
	/**
	 * Path to the list of stop word
	 */
	private static final String STOP_WORDS_PATH = "./src/main/resources/hrvatski_stoprijeci.txt";
	/**
	 * List of results
	 */
	private static List<Result> results;
	/**
	 * Vocabulary built and used
	 */
	private static Vocabulary voc;

	/**
	 * Main method
	 * 
	 * @param args none required
	 */
	public static void main(String[] args) {
		
		if (args.length != 1) {
			System.out.println("One argument expected, document root directory");
			System.exit(1);
		}
		
		String documentRoot = args[0];
		
		Path docPath = Paths.get(documentRoot);
		Path stopPath = Paths.get(STOP_WORDS_PATH);
		
		if (!Files.isDirectory(docPath)) {
			System.out.println("Given path is not a directory");
		}
		
		voc = new Vocabulary(stopPath, docPath);
		
		System.out.println("Dictonary size is: " + voc.getVocabulary().size() + " words.");
		
		Scanner sc = new Scanner(System.in);
		
		while (true) {
			System.out.print("> ");
			String input = sc.nextLine().trim().toLowerCase();
			
			if (input.startsWith("query")) {
				queryCommand(input.split("\\s+", 2)[1]);
			} else if (input.startsWith("type")) {
				try {
					typeCommand(input.split("\\s+", 2)[1]);
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("Command 'type' must be followed by an integer value, the index of a desired result");
				}
			} else if (input.startsWith("results")) {
				printResults();
			} else if (input.equals("exit")) {
				break;
			} else {
				System.out.println("Unsupported command");
			}
		}
		sc.close();
	}
	
	/**
	 * Prints the contents of a result on a given index
	 * 
	 * @param string String that should contain an integer, the index of a result
	 */
	private static void typeCommand(String string) {
		if (results == null) {
			System.out.println("You must enter a query first");
			return;
		}	
		
		int index = 0;
		try {
			index = Integer.parseInt(string.trim());
		} catch (NumberFormatException ex) {
			System.out.println("Command 'type' must be followed by an integer value, index of a desired result");
			return;
		}
		
		Document doc = null; 
		
		try {
			 doc = results.get(index).doc;
		} catch (IndexOutOfBoundsException ex) {
			System.out.println("Result with a requested index doesn't exist.");
			return;
		}
		
		try (BufferedReader reader = Files.newBufferedReader(doc.getPath(), Charset.forName("UTF-8"))) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException ex) {
			System.out.println("File not readable using the UTF-8 charset: " + ex.getMessage());
		}
		
	}

	/**
	 * Executes the query command
	 * 
	 * @param string User input
	 */
	private static void queryCommand(String string) {
		Document queryDoc = Utils.generateQueryDoc(string, voc);
		generateResultSet(queryDoc);
		
		System.out.println("Query is: " + queryDoc.getWordFrequency().keySet());
		System.out.println("Best 10 results: ");
		
		printResults();
		
	}

	/**
	 * Prints the best results
	 */
	private static void printResults() {		
		if (results == null) {
			System.out.println("You must enter a query first");
			return;
		}	
		
		if (results.isEmpty()) {
			System.out.println("No results found");
			return;
		}
		
		for (int i = 0, n = results.size(); i < 10 && i < n; i++) {
			System.out.printf("    [%2d](%.4f) %s%n", i, results.get(i).similarity, results.get(i).doc.getPath().toAbsolutePath());
		}
	}

	/**
	 * Generates the results in comparison to the given document
	 * 
	 * @param queryDocument A document generated from a query
	 */
	private static void generateResultSet(Document queryDocument) {
		results = new ArrayList<>();
		for (Document doc : voc.getDocuments()) {
			double similarity = Utils.calculateSimilarity(queryDocument, doc);
			if (similarity <= 0 || Double.isNaN(similarity)) {
				continue;
			}
			
			results.add(new Result(doc, similarity));
		}
		
		Collections.sort(results);
	}
	
	/**
	 * A class that stores the similarity to every document in comparison 
	 * to users query. Implements Comparable in respect to results.
	 * 
	 * @author Franko Car
	 *
	 */
	private static class Result implements Comparable<Result> {
		/**
		 * Document
		 */
		private Document doc;
		/**
		 * Similarity to the query
		 */
		private double similarity;
		
		/**
		 * A constructor
		 * 
		 * @param doc Document to store
 		 * @param similarity double value
		 */
		public Result(Document doc, double similarity) {
			this.doc = doc;
			this.similarity = similarity;
		}

		@Override
		public int compareTo(Result other) {
			int result = Double.compare(other.similarity, similarity);
			if (result != 0) {
				return result;
			}
			return other.doc.getPath().toString().compareTo(this.doc.getPath().toString());			
		}
		
		
	}
	
}
