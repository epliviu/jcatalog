package ro.tm.siit.w10h;
/**
 * 
 * @author nicolicioiul
 *
 */
public enum FeedbackType {
	/**
	 * Feedback for grade 10,9
	 */
	feedback1("Good boy, greate grade!"),
	/**
	 * Feedback for grade 8,7
	 */	
	feedback2("Ups, your grade is between 8 and 7."),
	/**
	 * Feedback for grade 7, 6, 5, 4, 3, 2, 1 ...
	 */	
	feedback3("Ups, bad boy, your grade is under 7!");
	private String message;
	private String subject = "Feedback message";
	FeedbackType(String message){
		this.message = message;
	}
	/**
	 * 
	 * @return subject
	 */
	public String getSubject(){
		return subject;
	}

	/**
	 * 
	 * @return message
	 */
	public String getMessage(){
		return message;
	}
}
