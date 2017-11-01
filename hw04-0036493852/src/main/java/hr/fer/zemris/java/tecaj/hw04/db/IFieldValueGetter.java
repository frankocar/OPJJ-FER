package hr.fer.zemris.java.tecaj.hw04.db;

/**
 * An interface defining a strategy to get the appropriate
 * value
 *  
 * @author Franko Car
 *
 */
public interface IFieldValueGetter {

	/**
	 * A strategy to get the requested field from a given {@link StudentRecord}
	 * 
	 * @param record StudentRecord to get value from
	 * @return Requested data
	 */
	public String get(StudentRecord record);
	
}
