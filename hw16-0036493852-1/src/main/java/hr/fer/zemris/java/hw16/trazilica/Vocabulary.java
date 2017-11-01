package hr.fer.zemris.java.hw16.trazilica;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Stores the vocabulary data and calculates IDF vectors for documents. Also stores
 * the list of all documents that have been analyzed.
 * 
 * @author Franko Car
 *
 */
public class Vocabulary {

	/**
	 * A set of stop words that are ignored when building a dictionary
	 */
	private Set<String> stopWords;
	/**
	 * Set of all words used
	 */
	private Set<String> vocabulary;
	
	/**
	 * IDF vector calculated for each document
	 */
	private Map<String, Double> idf;
	/**
	 * List of documents that should be searchable
	 */
	private List<Document> documents;
	
	/**
	 * Marks when the vocabulary is fully built
	 */
	private boolean vocabularyBuilt = false;
	
	/**
	 * A constructor
	 * 
	 * @param stopWordsData Path to the list of stop words
	 * @param documentRoot Path to the directory that has to be searched
	 */
	public Vocabulary(Path stopWordsData, Path documentRoot) {
		addStopwords(stopWordsData);
		addWords(documentRoot);
		
		calculateIdf();
		calculateTfIdfForDocuments();
	}

	/**
	 * Populates the TF-IDF value for each document in a list
	 * 
	 * @throws IllegalAccessError if the vocabulary hasn't been built yet
	 */
	private void calculateTfIdfForDocuments() {
		if (!vocabularyBuilt) {
			throw new IllegalAccessError("Vocabulary not yet built");
		} 
		
		for (Document doc : documents) {
			doc.calculateTfIdf();
		}		
	}

	/**
	 * Calculates the IDF value for each document
	 */
	private void calculateIdf() {
		idf = new HashMap<>();
		for (String word : vocabulary) {
			int wordFreq = getWordFreq(word);
			double idfValue = Math.log(documents.size() / (double) wordFreq);
			idf.put(word, idfValue);
		}		
	}

	/**
	 * Searches all documents for occurrences of a given word and stores the data
	 * 
	 * @param word String to look for
	 * @return int number of documents where the word is found
	 */
	private int getWordFreq(String word) {
		int freq = 0;
		for (Document doc : documents) {
			if (doc.containsWord(word)) {
				freq++;
			}
		}
		return freq;
	}

	/**
	 * Adds the words from all files to the vocabulary and populates
	 * the list of documents
	 * 
	 * @param documentRoot
	 */
	private void addWords(Path documentRoot) {
		vocabulary = new LinkedHashSet<>();
		documents = new LinkedList<>();
		try {
			Files.walkFileTree(documentRoot, new Walker());
			vocabularyBuilt = true;
		} catch (IOException ex) {
			System.out.println("Couldn't read input files. Terminating...");
			System.exit(1);
		}		
	}

	/**
	 * Populates the set of stop words
	 * 
	 * @param stopWordsData Path to the list of stop words
	 */
	private void addStopwords(Path stopWordsData)  {
		try {
			List<String> words = Files.readAllLines(stopWordsData);
			stopWords = new HashSet<>(words);		
		} catch (IOException e) {
			System.out.println("Couldn't read stop word input file. Terminating...");
			System.exit(1);
		}
	}
	
	/**
	 * Gets the IDF value for a given word
	 * 
	 * @param word for which the IDF value is requested for
	 * @return double IDF value for a given word
	 * 
	 * @throws IllegalAccessError if the vocabulary hasn't been built yet
	 * @throws IllegalArgumentException if the word isn't found in the vocabulary
	 */
	public double getIdf(String word) {
		if (!vocabularyBuilt) {
			throw new IllegalAccessError("Vocabulary not yet built");
		} 
		
		if (!idf.containsKey(word)) {
			throw new IllegalArgumentException("Word not contained in the vocabulary: '" + word + "'!");
		} 
		
		return idf.get(word);		
	}
	
	/**
	 * Returns a set with stop words
	 * 
	 * @return Set of Strings with stop words
	 */
	public Set<String> getStopWords() {
		return stopWords;
	}

	/**
	 * Sets a set of stop words
	 * 
	 * @param stopWords collection of stop words
	 */
	public void setStopWords(Collection<String> stopWords) {
		this.stopWords = new LinkedHashSet<>(stopWords);
	}

	/**
	 * Returns the vocabulary
	 * 
	 * @return Set of strings found in a vocabulary
	 */
	public Set<String> getVocabulary() {
		return vocabulary;
	}

	/**
	 * Returns the map of IDF values mapped to words
	 * 
	 * @return Map<String, Double> with IDF values
	 */
	public Map<String, Double> getIdf() {
		return idf;
	}

	/**
	 * Returns the list of documents
	 * 
	 * @return List of all documents
	 */
	public List<Document> getDocuments() {
		return documents;
	}

	/**
	 * Checks whether the vocabulary has been built
	 * 
	 * @return
	 */
	public boolean isVocabularyBuilt() {
		return vocabularyBuilt;
	}

	/**
	 * File visitor implementation that will read every document and build a vocabulary
	 * with the words found in them.
	 * 
	 * @author Franko Car
	 *
	 */
	private class Walker extends SimpleFileVisitor<Path> {
		
		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			List<String> words = Utils.parseFile(file, Vocabulary.this);
			
			Document doc = new Document(words, Vocabulary.this, file);
			vocabulary.addAll(words);
			documents.add(doc);
			
			return FileVisitResult.CONTINUE;
		}	
	}
	
}
