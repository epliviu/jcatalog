/**
 * 
 */
package ro.tm.siit.w10h;

/**
 * @author nicolicioiul
 *
 */
abstract class Person implements FeedbackSender {
	private String name;
	private String mail;
	protected Messenger messenger;

	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public Person(String name, String mail, Messenger messenger) {
		this.name = name;
		this.mail = mail;
		this.messenger = messenger;
	}

	/**
	 * Name getter
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Mail getter
	 * 
	 * @return mail
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * Implement
	 * 
	 * @param person
	 */
	public void sendFeedback(Person person) throws IllegalArgumentException {
		if (this.equals(person)) {
			throw new IllegalArgumentException("You cannot send message to your self!");
		}
		if (person instanceof Trainee) {
			throw new IllegalArgumentException("You cannot send message to trainee!");
		}
		messenger.sendMessage(person.getMail(), "Feedback", "My Note is not nice...");
	}
}
