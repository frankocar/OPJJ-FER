package hr.fer.zemris.java.tecaj.hw04.db;

/**
 * A class that will return the proper strategy implementation for a requested getter
 * 
 * @author Franko Car
 *
 */
public class FieldValueGetters {

	/**
	 * A strategy to get the first name
	 */
	public static final IFieldValueGetter FIRST_NAME = ((record) -> record.getFirstName());
	/**
	 * A strategy to get the last name
	 */
	public static final IFieldValueGetter LAST_NAME = ((record) -> record.getLastName());
	/**
	 * A strategy to get JMBAG
	 */
	public static final IFieldValueGetter JMBAG = ((record) -> record.getJmbag());
	
}
