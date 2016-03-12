/**
 * 
 */
package ro.tm.siit.w10h;

/**
 * @author nicolicioiul
 *
 */
public class SiteManager extends Person {
	/**
	 * Constructor
	 * 
	 * @param name
	 * @param mail
	 * @param Catalog
	 */
	public SiteManager(String name, String mail, Messenger messenger) {
		super(name, mail, messenger);
	}
	/**
	 * Print catalog grades from Site manager
	 */
	public void printGrades(SiteManagerCatalogInterface catalog, String name){
		catalog.printGrades(name);
	}

	/**
	 * Print from Site manager
	 */
	public void printCatalog(SiteManagerCatalogInterface catalog){
		catalog.printCatalog();
	}
}
