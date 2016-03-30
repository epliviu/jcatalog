
package ro.tm.siit.w10h;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * Catalog class implements TrainerCatalogInterface interface and model a grades
 * catalog for Scoala Informala de IT.
 * 
 * @author mcosma
 *
 */
final public class Catalog implements TrainerCatalogInterface, TraineeCatalogInterface {

	private Map<String, TraineeGrades> trainees = new HashMap<String, TraineeGrades>();
	private String name;
	private Messenger messenger;
	private Trainer trainer;
	private static Catalog singleInstance;
	private int gradesCount = 0; 
	/**
	 * @param name
	 *            the name of the training
	 * @param trainees
	 *            the trainees
	 */
	private Catalog(String name, Messenger messenger, Trainee... trainees) {
		super();
		this.name = name;
		TraineeCatalogInterface traineeCatalogInterface = this;
		for (Trainee t : trainees) {
			addTrainee(t, traineeCatalogInterface);
		}
		this.messenger = messenger;
	}
	/**
	 * Add a trainee to catalog
	 * @param trainee
	 * @param traineeCatalogInterface
	 * @throws IllegalStateException
	 */
	public void addTrainee(Trainee trainee, TraineeCatalogInterface traineeCatalogInterface) throws IllegalStateException{
		if(!canAddTrainee()){
			throw new IllegalStateException();
		}
		trainee.setTraineeCatalogInterface(traineeCatalogInterface);
		this.trainees.put(trainee.getName(), new TraineeGrades(trainee));
	}
	/**
	 * 
	 * @param name
	 * @param messenger
	 * @param trainees
	 * @return
	 */
	public static Catalog getInstance(String name, Messenger messenger, Trainee... trainees) {
		if (singleInstance == null) {
			singleInstance = new Catalog(name, messenger, trainees);
		}
		return singleInstance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see rro.tm.siit.w10h.TraineeCatalogInterface# addGrade(java.lang.String,
	 * int)
	 */
	public int getLastGrade(String name) throws IllegalStateException{
		TraineeGrades participant = find(name);
		if (participant != null && !participant.grades.isEmpty()) {
			return participant.grades.get(participant.grades.size() - 1);
		}
		throw new IllegalStateException();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ro.tm.siit.w10h.TrainerCatalogInterface# addGrade(java.lang.String,
	 * int)
	 */
	public void addGrade(String name, int grade, Trainer trainer) throws IllegalStateException{
		TraineeGrades participant = find(name);
		if(participant == null){
			throw new IllegalStateException();
		}
		if(trainingStarted()){
			throw new IllegalStateException();
		}
		// Inc grades
		gradesCount++;
		participant.addGrade(grade);
			messenger.sendMessage(participant.trainee.getMail(), "New grade:" + grade,
					"New grade from:" + trainer.getName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ro.tm.siit.w10h.SiteManagerCatalogInterface#
	 * printGrades(java.lang.String)
	 */
	public void printGrades(String name) {
		TraineeGrades participant = find(name);
		System.out.println(participant);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ro.tm.siit.w10h.TrainerCatalogInterface# getGrades(java.lang.String)
	 */
	public List<Integer> getGrades(String name) throws IllegalArgumentException {
		TraineeGrades participant = find(name);
		if (participant == null) {
			throw new IllegalArgumentException();
		}
		return participant.grades;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * 
	 * getTrainee(java.lang.String)
	 */
	public Trainee getTrainee(String name) throws IllegalArgumentException {
		TraineeGrades participant = find(name);
		if (participant == null) {
			throw new IllegalArgumentException();
		}
		return participant.trainee;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ro.tm.siit.w10h.SiteManagerCatalogInterface# printCatalog()
	 */
	public void printCatalog() {
		System.out.println("Catalog " + name + " has " + trainees.size() + " trainees");
		for (String key : trainees.keySet()) {
			TraineeGrades t = trainees.get(key);
			System.out.println(t.trainee.getName() + " " + t.getAvgGrade());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Catalog " + name + " has " + trainees.size() + " trainees";
	}

	/**
	 * searches for a Trainee with specified name
	 * 
	 * @param name
	 *            the name fo the trainee
	 * @return a Trainee object or null if not found
	 */
	private TraineeGrades find(String name) {
		if (trainees.containsKey(name)) {
			return trainees.get(name);
		}
		return null;
	}

	private class TraineeGrades {
		private Trainee trainee;
		private List<Integer> grades = new ArrayList<Integer>();

		public TraineeGrades(Trainee trainee) {
			super();
			this.trainee = trainee;
		}

		/**
		 * @param grade
		 *            adds a new grade to trainee
		 */
		public void addGrade(int grade) {
			this.grades.add(grade);
		}

		/**
		 * @return a float as average of the grades
		 */
		public float getAvgGrade() {
			int sum = 0;
			if (grades.size() == 0) {
				return sum;
			}
			for (int i : grades) {
				sum += i;
			}
			return ((float) sum) / grades.size();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			String out = trainee.getName() + " : ";
			for (int i : grades) {
				out += i + " ";
			}
			return out;
		}
	}

	/**
	 * Start training.
	 * 
	 * @param Trainer
	 *            trainer
	 */
	public void startTraining(Trainer trainer) throws IllegalStateException {
		if (trainingStarted()) {
			throw new IllegalStateException();
		}
		this.trainer = trainer;
	}

	/**
	 * Check if training started
	 * 
	 * @return boolean
	 */
	public boolean trainingStarted() {
		if (this.trainer == null) {
			return false;
		}
		return true;
	}
	/**
	 * Check if can be added an trainee
	 * @return boolean
	 */
	public boolean canAddTrainee(){
		if(!trainingStarted() && gradesCount == 0){
			return true;
		}
		return false;
	}
}
