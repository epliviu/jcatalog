/**
 * 
 */
package ro.tm.siit.w10h;

/**
 * @author nicolicioiul
 *
 */
class Trainer extends Person {
	/**
	 * Constructor
	 * 
	 * @param name
	 * @param catalog
	 * @param messenger
	 */
	public Trainer(String name, String mail, Messenger messenger) {
		super(name, mail, messenger);
	}
	/**
	 * Add grade using trainer instance
	 * @param catalog
	 * @param student
	 * @param grade
	 */
	public void addGrade(TrainerCatalogInterface catalog, String student, int grade){
		catalog.addGrade(student, grade, this);
	}
}
