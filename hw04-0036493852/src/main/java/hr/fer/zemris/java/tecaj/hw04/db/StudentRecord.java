package hr.fer.zemris.java.tecaj.hw04.db;

/**
 * A class to store individual students records
 * 
 * @author Franko Car
 *
 */
public class StudentRecord {

	/**
	 * JMBAG of a student
	 */
	private String jmbag;
	/**
	 * Students last name
	 */
	private String lastName;
	/**
	 * Students first name
	 */
	private String firstName;
	/**
	 * Students final grade
	 */
	private int finalGrade;	
	
	/**
	 * A constructor
	 * 
	 * @param jmbag JMBAG
	 * @param lastName Last Name
	 * @param firstName First Name
	 * @param finalGrade Final Grade
	 * @throws IllegalStateException if a given value is null or the grade is out of range
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
		super();
		
		if (jmbag == null) {
			throw new IllegalArgumentException("JMBAG can't be null");
		}
		
		if (lastName == null) {
			throw new IllegalArgumentException("Last name can't be null");
		}
		
		if (firstName == null) {
			throw new IllegalArgumentException("First name can't be null");
		}
		
		if (finalGrade < 1 || finalGrade > 5) {
			throw new IllegalArgumentException("Final grade must be between 1 and 5, was '" + finalGrade + "'.");
		}
		
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}

	/**
	 * A getter for JMBAG
	 * 
	 * @return JMBAG
	 */
	public String getJmbag() {
		return jmbag;
	}
	
	/**
	 * A getter for first name
	 * 
	 * @return first name
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * A getter for last name
	 * 
	 * @return last name
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * A getter for final grade
	 * 
	 * @return final grade
	 */
	public int getFinalGrade() {
		return finalGrade;
	}
	
	/**
	 * A setter for last name
	 * 
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * A setter for first name
	 * 
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * A setter for final grade
	 * 
	 * @param finalGrade
	 */
	public void setFinalGrade(int finalGrade) {
		this.finalGrade = finalGrade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "[jmbag=" + jmbag +
				", lastName=" + lastName +
				", firstName=" + firstName +
				", finalGrade="	+ finalGrade + "]";
	}	
	
	
	
}
