package hr.fer.zemris.java.tecaj.hw04.db;

/**
 * An interface defining a method to help filter the database entries
 * 
 * @author Franko Car
 *
 */
public interface IFilter {

	/**
	 * A method to check if a given record satisfies the requests
	 * 
	 * @param record {@link StudentRecord} to check
	 * @return Boolean value
	 */
	public boolean accepts(StudentRecord record);
	
}
