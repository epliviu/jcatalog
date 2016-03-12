/**
 * 
 */
package ro.tm.siit.w10h;

import java.util.ArrayList;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author nicolicioiul
 *
 */
public class EmailMessenger implements Messenger {
	private Session session;

	/**
	 * Implement sending message
	 * 
	 * @param from
	 * @param to
	 * @param message
	 * @return boolean
	 */
	public boolean sendMessage(String mail, String subject, String message){
		try {
			Message mimeMessage = new MimeMessage(session);
			mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail));
			mimeMessage.setSubject(subject);
			mimeMessage.setText(message);
			Transport.send(mimeMessage);
			return true;
		} catch (Exception e) {
			System.out.println("Error send email:" + e.getMessage());
			return false;
		}
	}

	/**
	 * open resource
	 * 
	 * @return boolean
	 */
	public boolean connect() {
		try {
			final String username = "nicolicioiu.liviu@enode.ro";
			final String password = "****";

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
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Destructor
	 */
	public void finalize() {
		try {
			if (session != null) {
				session.getTransport().close();
			}
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
