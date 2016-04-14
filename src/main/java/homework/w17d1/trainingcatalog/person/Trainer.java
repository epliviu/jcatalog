/**
 * 
 */
package homework.w17d1.trainingcatalog.person;

import javax.swing.JTable;

import homework.w17d1.trainingcatalog.Messenger;
import homework.w17d1.trainingcatalog.TrainerCatalogInterface;

/**
 * @author mco
 *
 */
public class Trainer extends Person {

	private TrainerCatalogInterface trainerInterface;

	public Trainer(String name, String email, TrainerCatalogInterface trainer, Messenger messenger) {
		super(name, email, messenger);
		this.trainerInterface = trainer;
	}

	public void addGrade(String string, int grade) {
		trainerInterface.addGrade(string, grade);
	}

	public void printGrades(String string) {
		trainerInterface.printGrades(string);

	}

	public void printCatalog() {
		trainerInterface.printCatalog();
	}

	public void displayCatalog(JTable catalog) {
		trainerInterface.displayCatalog(catalog);
	}

}
