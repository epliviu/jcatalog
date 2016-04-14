/**
 * 
 */
package homework.w17d1.trainingcatalog.messenger;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import homework.w17d1.trainingcatalog.Messenger;

/**
 * @author mco
 *
 */
public class EmailMessenger implements Messenger {
	
	private static EmailMessenger instance;

	private Session session;

	private EmailMessenger() {
		super();

		final String username = "user@gmail.com";
		final String password = "password";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
	}
	
	public static EmailMessenger getInstance() {
		if (instance == null) {
			instance = new EmailMessenger();
		}
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ro.tm.siit.homework.w11d2.trainingcatalog.Messenger#sendMessage(String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public boolean sendMessage(String emailAddress, String subject, String text) {
		try {
			Message email = new MimeMessage(session);
			email.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailAddress));
			email.setSubject(subject);
			email.setText(text);
			Transport.send(email);
		} catch (MessagingException e) {
			return false;
		}
		return true;
	}

}
