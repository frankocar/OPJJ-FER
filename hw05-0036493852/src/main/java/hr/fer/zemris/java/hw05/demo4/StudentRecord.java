package hr.fer.zemris.java.hw05.demo4;

/**
 * A class to store individual student records
 * 
 * @author Franko Car
 *
 */
public class StudentRecord {

	/**
	 * Students JMBAG
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
	 * Students midterm score
	 */
	private double midtermScore;
	/**
	 * Students final score
	 */
	private double finalScore;
	/**
	 * Students laboratory score
	 */
	private double labScore;
	/**
	 * Students final grade
	 */
	private int finalGrade;
	
	/**
	 * A constructor
	 * 
	 * @param jmbag JMBAG
	 * @param lastName Last name
	 * @param firstName First Name
	 * @param midtermScore Midterm score
	 * @param finalScore Final score
	 * @param labScore Laboratory score
	 * @param finalGrade Final Grade
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, double midtermScore, double finalScore,
			double labScore, int finalGrade) {
		super();
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.midtermScore = midtermScore;
		this.finalScore = finalScore;
		this.labScore = labScore;
		this.finalGrade = finalGrade;
	}

	/**
	 * A getter
	 * 
	 * @return JMBAG
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * A getter
	 * 
	 * @return Last name
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * A getter
	 * 
	 * @return First name
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * A getter
	 * 
	 * @return Midterm score
	 */
	public double getMidtermScore() {
		return midtermScore;
	}

	/**
	 * A getter
	 * 
	 * @return Final score
	 */
	public double getFinalScore() {
		return finalScore;
	}

	/**
	 * A getter
	 * 
	 * @return Laboratory score
	 */
	public double getLabScore() {
		return labScore;
	}
	
	/**
	 * A getter
	 * 
	 * @return Final score
	 */
	public int getFinalGrade() {
		return finalGrade;
	}
	
	/**
	 * A method to calculate the total score(midterm+final+lab)
	 * 
	 * @return Total score
	 */
	public double getTotalScore() {
		return midtermScore + finalScore + labScore;
	}

	@Override
	public String toString() {
		return String.format(
				"| %10s | %10s | %6s | %5.2f | %5.2f | %5.2f | %5.2f | %d |",
				jmbag,
				lastName,
				firstName,
				midtermScore,
				finalScore,
				labScore,
				getTotalScore(),
				finalGrade
				);
	}
	
	
	
	
	
	

}
