package ro.tm.siit.w10h;

public interface FeedbackSender {
	/**
	 * Send feedback
	 * 
	 * @param person
	 * @throws Exception 
	 */
	void sendFeedback(Person person) throws IllegalArgumentException;
}
