/**
 * 
 */
package homework.w17d1.trainingcatalog;

import homework.w17d1.trainingcatalog.catalog.Catalog;
import homework.w17d1.trainingcatalog.messenger.SimpleMessenger;
import homework.w17d1.trainingcatalog.person.SiteManager;
import homework.w17d1.trainingcatalog.person.Trainee;
import homework.w17d1.trainingcatalog.person.Trainer;

/**
 * TrainingCatalogApp class is an application for managing the catalog at Scoala
 * Informala de IT. It offers support for trainers and site managers.
 * 
 * @author mcosma
 *
 */
public class TrainingCatalogApp {

	/**
	 * demo code to show how can Catalog can be used by trainers or site
	 * managers
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Messenger messenger = SimpleMessenger.getInstance();
		Catalog catalog = new Catalog("Java S2", messenger);

		TraineeCatalogInterface traineeInterface = catalog;
		Trainee florin = new Trainee("florin", "", messenger, traineeInterface);
		Trainee radu = new Trainee("radu", "", messenger, traineeInterface);
		Trainee andrei = new Trainee("andrei", "", messenger, traineeInterface);
		Trainee ciprian = new Trainee("ciprian", "", messenger, traineeInterface);

		SiteManagerCatalogInterface siteManagerInterface = catalog;
		SiteManager siteManager = new SiteManager("diana", "diana@gmail.com", siteManagerInterface, messenger);

		siteManager.addTrainee(florin);
		siteManager.addTrainee(radu);
		siteManager.addTrainee(andrei);
		siteManager.addTrainee(ciprian);

		TrainerCatalogInterface trainerInterface = catalog;
		Trainer trainer = new Trainer("madalin", "madalin.cosma@gmail.com", trainerInterface, messenger);

		try {
			trainer.addGrade("florin", 10);
		} catch (IllegalStateException e) {
			System.out.println(e.getMessage());
		}

		try {
			siteManager.printGrades("florin");
		} catch (IllegalStateException e) {
			System.out.println(e.getMessage());
		}

		siteManager.startTraining(trainer);

		work(trainer);

		work(siteManager);

		ciprian.sendFeedback(trainer);
		radu.sendFeedback(siteManager);
		siteManager.sendFeedback(trainer);

		try {
			radu.sendFeedback(ciprian);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}

		try {
			trainer.sendFeedback(trainer);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}

		Trainee daniel = new Trainee("daniel", "", messenger, traineeInterface);

		try {
			siteManager.addTrainee(daniel);
		} catch (IllegalStateException e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * the trainer works with the catalog
	 * 
	 * @param trainer
	 */
	private static void work(Trainer trainer) {
		trainer.addGrade("florin", 10);
		trainer.addGrade("florin", 9);

		try {
			trainer.addGrade("dana", 4);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}

		trainer.addGrade("radu", 9);
		trainer.addGrade("radu", 10);

		trainer.addGrade("andrei", 9);
		trainer.addGrade("florin", 9);

		try {
			trainer.addGrade("florin", 11);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}

		trainer.addGrade("ciprian", 8);
		trainer.addGrade("ciprian", 9);

		trainer.printGrades("radu");

		trainer.addGrade("radu", 10);
		trainer.printCatalog();
	}

	/**
	 * the site manager works with the catalog
	 * 
	 * @param siteManager
	 */
	private static void work(SiteManager siteManager) {
		siteManager.printGrades("radu");
		siteManager.printCatalog();
	}

}
