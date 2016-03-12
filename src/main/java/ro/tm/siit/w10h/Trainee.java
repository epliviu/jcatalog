/**
 * 
 */
package ro.tm.siit.w10h;

import java.util.ArrayList;

/**
 * @author nicolicioiul
 *
 */
public class Trainee extends Person {
	private TraineeCatalogInterface traineCatalogInterface;
	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public Trainee(String name, String mail, Messenger messenger) {
		super(name, mail, messenger);
	}
	/**
	 *  @param traineCatalogInterface
	 */
	public void setTraineeCatalogInterface(TraineeCatalogInterface traineCatalogInterface){
		this.traineCatalogInterface = traineCatalogInterface;
	}
	/**
	 * Implement feedback for a grade
	 * 
	 * @param grade
	 * @param messenger
	 * @param person
	 * @param siteManageraskForGradeFeedback
	 * 
	 */
	public void sendFeedback(int grade, Person trainer) throws IllegalArgumentException{
		try{
			if(this.equals(trainer)){
				throw new IllegalArgumentException("You cannot send message to your self!");
			}
			if(grade > 10 || grade <= 0){
				throw new IllegalArgumentException("Invalid grade!");
			}
			String subject = "";
			String message = "";
			if (grade > 8) {
				subject = FeedbackType.feedback1.getSubject();
				message = FeedbackType.feedback1.getMessage();
			} else if (grade > 6) {
				subject = FeedbackType.feedback2.getSubject();
				message = FeedbackType.feedback2.getMessage();
			} else if (grade < 7) {
				subject = FeedbackType.feedback3.getSubject();
				message = FeedbackType.feedback3.getMessage();
			}
			if (subject != "" && message != "") {
				messenger.sendMessage(trainer.getMail(), subject, message);
			}else{
				throw new IllegalArgumentException("Undefined message feedback grade!");
			}
		}catch (IllegalArgumentException e){
			System.out.println("Exception: "+ e.getMessage());
		}
	}

	/**
	 * Implement feedback for a grade
	 * 
	 * @param grade
	 * @param messenger
	 * @param person
	 * @param siteManageraskForGradeFeedback
	 * 
	 */
	public void sendFeedbackLastGrade(Person trainer) throws IllegalArgumentException{
		try{
			if(this.traineCatalogInterface == null ){
				throw new IllegalArgumentException("Invalid access catalog interface!");
			}
			int grade = this.traineCatalogInterface.getLastGrade(this.getName());
			this.sendFeedback(grade, trainer);
		}catch (IllegalArgumentException e){
			System.out.println("Exception: "+ e.getMessage());
		}
	}
}
