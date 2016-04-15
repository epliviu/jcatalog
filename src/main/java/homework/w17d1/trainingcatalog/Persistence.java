/**
 * 
 */
package homework.w17d1.trainingcatalog;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import homework.w17d1.trainingcatalog.catalog.Catalog;

/**
 * @author mco
 *
 */
public class Persistence {
	private Path file = Paths.get("catalog.data");	

	public void saveCatalog(Catalog catalog) {
		try (OutputStream os = Files.newOutputStream(file); 
				ObjectOutputStream oos = new ObjectOutputStream(os)) {
			oos.writeObject(catalog);
		} catch (IOException e) {
			System.out.println("problem saving catalog data " + e.getMessage());
		}
	}

	public Catalog loadCatalog() {
		Catalog catalog = null;
		if (Files.exists(file)) {
			try (InputStream is = Files.newInputStream(file); 
					ObjectInputStream ois = new ObjectInputStream(is)) {
				catalog = (Catalog) ois.readObject();
			} catch (IOException e) {
				System.out.println("problem loading catalog data " + e.getMessage());
			} catch (ClassNotFoundException e) {
				System.out.println("problem loading catalog data " + e.getMessage());
			}
		}
		// if is null, create a new one.
		if(catalog == null){
			System.out.println("new one");
			catalog = new Catalog("");
		}
		catalog = new Catalog("");
		return catalog;
	}

}
