/**
 * 
 */
package ro.tm.siit.w10h;

import java.util.List;

/**
 * @author nicolicioiul
 *
 */
public class TrainerApp {
	/**
	 * demo code to show how can Catalog can be used by trainers or site
	 * managers
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Messenger messenger = SimpleMessenger.getInstance();
		
		Catalog catalog =  Catalog.getInstance("Java S2", messenger,
				new Trainee("florin", "nicolicioiu.liviu3@enode.ro", messenger),
				new Trainee("radu", "nicolicioiu.liviu1@enode.ro", messenger),
				new Trainee("andrei", "nicolicioiu.liviu3@enode.ro", messenger),
				new Trainee("ciprian", "nicolicioiu.liviu2@enode.ro", messenger));
		
		TrainerCatalogInterface trainerCatalog = catalog;
		Trainer trainer = new Trainer("madalin", "nicolicioiu.liviu2@enode.ro", messenger);
		
		SiteManager siteManager = new SiteManager("madalin", "nicolicioiu.liviu2@enode.ro", messenger);
		work(trainerCatalog, trainer);

		SiteManagerCatalogInterface siteManagerinterface = catalog;
		work(siteManagerinterface);
		// Send feedback for all grades for radu.
		List<Integer> grades = catalog.getGrades("radu");
		if (grades != null) {
			Trainee trainee = catalog.getTrainee("radu");
			for (int grade : grades) {
				try{
					trainee.sendFeedback(grade, trainer);
				}catch(Exception e){
					System.out.println("Exception:"+e.getMessage());
					e.printStackTrace();
				}
			}
		}
		// Send feedback for last grade.
		try{
			catalog.getTrainee("radu").sendFeedbackLastGrade(trainer);
			catalog.getTrainee("radu").sendFeedbackLastGrade(catalog.getTrainee("radu"));
			catalog.getTrainee("radu").sendFeedbackLastGrade(siteManager);
		}catch(Exception e){
			System.out.println("Exception:"+e.getMessage());
			e.printStackTrace();
		}
	}
	/**
	 * the trainer works with the catalog
	 * 
	 * @param trainer
	 */
	private static void work(TrainerCatalogInterface catalog, Trainer trainer) {
		catalog.addGrade("florin", 10, trainer);
		catalog.addGrade("florin", 9, trainer);

		catalog.addGrade("radu", 9, trainer);
		catalog.addGrade("radu", 10, trainer);

		catalog.addGrade("andrei", 9, trainer);
		catalog.addGrade("florin", 9, trainer);

		catalog.addGrade("ciprian", 8, trainer);
		catalog.addGrade("ciprian", 9, trainer);

		catalog.printGrades("radu");

		catalog.addGrade("radu", 10, trainer);

	}

	/**
	 * the site manager works with the catalog
	 * 
	 * @param siteManager
	 */
	private static void work(SiteManagerCatalogInterface siteManager) {
		siteManager.printGrades("radu");
		siteManager.printCatalog();
	}

}
