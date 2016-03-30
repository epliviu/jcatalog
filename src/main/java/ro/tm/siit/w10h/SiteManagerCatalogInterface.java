package ro.tm.siit.w10h;

public interface SiteManagerCatalogInterface {
	/**
	 * prints to console all grades of a Trainee identified by name
	 * 
	 * @param name
	 *            the name of the trainee
	 */
	void printGrades(String name);

	/**
	 * prints to console all trainees with their average grade
	 */
	void printCatalog();
	/**
	 * Start training.
	 */
	void startTraining(Trainer trainer);
}
