package ro.tm.siit.w10h;

import java.io.IOException;

public interface StorageInterface {
	public boolean writeObject(String variable, Object value) throws IOException;
	public boolean readObject(String variable) throws IOException;
}
