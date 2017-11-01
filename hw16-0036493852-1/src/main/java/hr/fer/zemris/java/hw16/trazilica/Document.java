package hr.fer.zemris.java.hw16.trazilica;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Stores the document and its data, such as word frequencies and TF-IDF vector
 * 
 * @author Franko Car
 *
 */
public class Document {

	/**
	 * Path to the document
	 */
	private Path path;
	/**
	 * TF-IDF vector
	 */
	private double[] tfIdfVector;
	/**
	 * Map that maps the number of occurrences to each word
	 */
	private Map<String, Long> wordFrequency;
	/**
	 * Vocabulary to use
	 */
	private Vocabulary voc;
	
	/**
	 * A constructor
	 * 
	 * @param words List of word found in a document
	 * @param voc Vocabulary used
	 */
	public Document(List<String> words, Vocabulary voc) {
		wordFrequency = Collections
				.unmodifiableMap(words.stream().collect(Collectors.groupingBy(x -> x, Collectors.counting())));
		this.voc = voc;		
	}
	
	/**
	 * A constructor
	 * 
	 * @param words List of word found in a document
	 * @param voc Vocabulary used
	 * @param path Path to the document
	 */
	public Document(List<String> words, Vocabulary voc, Path path) {
		this(words, voc);
		this.path = path;
	}
	
	/**
	 * Calculates the TF-IDF vector if one isn't already calculated
	 */
	public void calculateTfIdf() {
		if (tfIdfVector != null) {
			return;
		}
		
		tfIdfVector = new double[voc.getVocabulary().size()];
		
		int pos = 0;
		for (String word : voc.getVocabulary()) {
			double tfIdfValue = wordFrequency.getOrDefault(word, 0L) * voc.getIdf(word);
			tfIdfVector[pos++] = tfIdfValue;
		}
	}
	
	/**
	 * Checks if the document contains a given word
	 * 
	 * @param word to check
	 * @return true if found, false othewise
	 */
	public boolean containsWord(String word) {
		long freq = wordFrequency.containsKey(word) ? wordFrequency.get(word) : 0;
		return freq > 0;
	}

	/**
	 * Returns document path. May be null if a document doens't have one (such as one generated from a query)
	 * 
	 * @return Path path
	 */
	public Path getPath() {
		return path;
	}

	/**
	 * Sets the document path
	 *  
	 * @param path new path
	 */
	public void setPath(Path path) {
		this.path = path;
	}

	/**
	 * Return a TF-IDF vector
	 * 
	 * @return double array representing a TF-IDF vector
	 */
	public double[] getTfIdfVector() {
		return Arrays.copyOf(tfIdfVector, tfIdfVector.length);
	}

	/**
	 * Returns a map that maps number of occurrences to each word
	 * 
	 * @return Map of word-occurrence frequency pairs
	 */
	public Map<String, Long> getWordFrequency() {
		return Collections.unmodifiableMap(wordFrequency);
	}

	/**
	 * Returns the vocabulary
	 * 
	 * @return Vocabulary vocabulary
	 */
	public Vocabulary getVoc() {
		return voc;
	}
	
	
	
}
