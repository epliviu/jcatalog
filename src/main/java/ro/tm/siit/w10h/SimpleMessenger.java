/**
 * 
 */
package ro.tm.siit.w10h;

/**
 * @author nicolicioiul
 *
 */
public class SimpleMessenger implements Messenger {
	/**
	 * Lock the constructor
	 */
	private SimpleMessenger() {

	}

	/**
	 * get instance
	 * 
	 * @return messenger
	 */
	public static SimpleMessenger getInstance() {
		SimpleMessenger messenger = new SimpleMessenger();
		return messenger;
	}

	/**
	 * Implement sending message
	 * 
	 * @param mail
	 *            String
	 * @param subject
	 *            String
	 * @param message
	 *            String
	 * @return boolean
	 */
	public boolean sendMessage(String mail, String subject, String message) {
		System.out.println("Send simple messenger message:");
		System.out.println("To:" + mail);
		System.out.println("Subject:" + subject);
		System.out.println("Message:" + message);
		return true;
	}
}
