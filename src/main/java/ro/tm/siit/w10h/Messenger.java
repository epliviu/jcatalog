package ro.tm.siit.w10h;

public interface Messenger {
	boolean sendMessage(String mail, String subject, String message) throws IllegalArgumentException;
}
