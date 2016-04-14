package homework.w17d1.trainingcatalog.person;

import javax.swing.JTable;

import homework.w17d1.trainingcatalog.Messenger;
import homework.w17d1.trainingcatalog.SiteManagerCatalogInterface;

public class SiteManager extends Person {

	private SiteManagerCatalogInterface siteManagerInterface;

	public SiteManager(String name, String email, SiteManagerCatalogInterface siteManager, Messenger messenger) {
		super(name, email, messenger);
		this.siteManagerInterface = siteManager;
	}

	public void printGrades(String string) {
		siteManagerInterface.printGrades(string);

	}

	public void printCatalog() {
		siteManagerInterface.printCatalog();
	}
	
	public void startTraining(Trainer trainer) {
		siteManagerInterface.startTraining(trainer);
	}
	
	public void addTrainee(Trainee trainee) {
		siteManagerInterface.addTrainee(trainee);
	}

	public void displayCatalog(JTable catalog) {
		siteManagerInterface.displayCatalog(catalog);
	}
}
