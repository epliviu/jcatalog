package ro.tm.siit.w10h;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SerializableStorage implements StorageInterface {

	@Override
	public boolean writeObject(String variable, Object object) throws IOException {
		// TODO Auto-generated method stub
		if (object instanceof java.io.Serializable) {
			Path path = Paths.get("stored." + object.getClass() + ".serializable");
			// use buffering
			OutputStream file = new FileOutputStream(path.toFile());
			OutputStream buffer = new BufferedOutputStream(file);
			ObjectOutput output = new ObjectOutputStream(buffer);
			try {
				output.writeObject(object);
			} finally {
				output.close();
			}
		}
		return true;
	}
	
	@Override
	public boolean readObject(String variable) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

}
