package hr.fer.zemris.java.hw16.trazilica;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utilities to work with documents and vector calculations used in the search engine
 * 
 * @author Franko Car
 *
 */
public class Utils {

	/**
	 * Parses words from given file and uses them to populate a vocabulary given as a 
	 * function argument
	 * 
	 * @param file File to parse
	 * @param voc Vocabulary to use
	 * @return List of words found
	 * @throws IOException if an I/O error occurs reading from the file or a malformed or unmappable byte sequence is read
	 */
	public static List<String> parseFile(Path file, Vocabulary voc) throws IOException {
		List<String> words = new LinkedList<>();
		List<String> lines = Files.readAllLines(file);
		
		for (String line : lines) {
			words.addAll(parseLine(line, voc));
		}
		
		return words;
	}
	
	/**
	 * Returns the list of words found in a single line, only looks for letters
	 * and splits on any other character. Skips the stop words found in the 
	 * vocabulary.
	 * 
	 * @param line Line to parse
	 * @param voc Vocabulary to use
	 * @return List of words found
	 */
	private static List<String> parseLine(String line, Vocabulary voc) {
		line = line.toLowerCase();
		String[] data = line.split("[^\\p{L}]+");
		return Arrays.stream(data).filter(s -> (s != null && s.length() > 0 && !voc.getStopWords().contains(s)))
				.collect(Collectors.toList());
	}
	
	/**
	 * Generates a document from a query
	 * 
	 * @param query Query string
	 * @param voc Vocabulary to use
	 * @return Generated document
	 */
	public static Document generateQueryDoc(String query, Vocabulary voc) {
		Document doc = new Document(parseLine(query, voc), voc);
		doc.calculateTfIdf();
		
		return doc;
	}
	
	/**
	 * Calculates the similarity of two documents
	 * 
	 * @param doc1 First document
	 * @param doc2 Second document
	 * @return double value of their similarity
	 */
	public static double calculateSimilarity(Document doc1, Document doc2) {
		double[] vector1 = doc1.getTfIdfVector();
		double[] vector2 = doc2.getTfIdfVector();
		
		double dotProduct = dotProduct(vector1, vector2);
		
		double norm1 = norm(vector1);
		double norm2 = norm(vector2);
		
		return dotProduct / (norm1 * norm2);
	}

	/**
	 * Calculates the vector norm
	 * 
	 * @param vector Array of doubles representing a vector
	 * @return double value of a norm
	 */
	private static double norm(double[] vector) {
		double sumOfSquares = 0;
		
		for (double x : vector) {
			sumOfSquares += x * x;
		}
		
		return Math.sqrt(sumOfSquares);
	}

	/**
	 * Calculates the dot product of two vectors
	 * 
	 * @param vector1 Array of doubles representing the first vector
	 * @param vector2 Array of doubles representing the second vector
	 * @return dot product of given vectors
	 * 
	 * @throws IllegalArgumentException if two vectors aren't of the same dimensions
	 */
	private static double dotProduct(double[] vector1, double[] vector2) {
		if (vector1.length != vector2.length) {
			throw new IllegalArgumentException("Can't multiply vectors that aren't of the same dimension");
		}
				
		double product = 0;
		
		for (int i = 0; i < vector1.length; i++) {
			product += vector1[i] * vector2[i];
		}
		
		return product;
	}
	
}
