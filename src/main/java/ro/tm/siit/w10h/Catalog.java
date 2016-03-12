
package ro.tm.siit.w10h;

/**
 * Catalog class implements TrainerCatalogInterface interface and model a grades
 * catalog for Scoala Informala de IT.
 * 
 * @author mcosma
 *
 */
public class Catalog implements TrainerCatalogInterface, TraineeCatalogInterface{

	private TraineeGrades[] trainees = new TraineeGrades[15];
	private int traineesCount;
	private String name;
	private Messenger messenger;

	/**
	 * @param name
	 *            the name of the training
	 * @param trainees
	 *            the trainees
	 */
	public Catalog(String name, Messenger messenger, Trainee... trainees) {
		super();
		this.name = name;
		TraineeCatalogInterface traineeCatalogInterface = this;
		for (Trainee t : trainees) {
			t.setTraineeCatalogInterface(traineeCatalogInterface);
			this.trainees[traineesCount++] = new TraineeGrades(t);
		}
		this.messenger = messenger;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see rro.tm.siit.w10h.TraineeCatalogInterface#
	 * addGrade(java.lang.String, int)
	 */
	public int getLastGrade(String name){
		TraineeGrades participant = find(name);
		if(participant.gradeCount > 0){
			return participant.grades[participant.gradeCount-1];
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see ro.tm.siit.w10h.TrainerCatalogInterface#
	 * addGrade(java.lang.String, int)
	 */
	public void addGrade(String name, int grade, Trainer trainer) {
		TraineeGrades participant = find(name);
		participant.addGrade(grade);
		try{
			messenger.sendMessage(participant.trainee.getMail(), "New grade:" + grade,
					"New grade from:" + trainer.getName());
		}catch(Exception e){
			
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ro.tm.siit.w10h.SiteManagerCatalogInterface#
	 * printGrades(java.lang.String)
	 */
	public void printGrades(String name) {
		TraineeGrades participant = find(name);
		System.out.println(participant);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ro.tm.siit.w10h.TrainerCatalogInterface#
	 * getGrades(java.lang.String)
	 */
	public int[] getGrades(String name) {
		TraineeGrades participant = find(name);
		int[] completedGrades = new int[participant.gradeCount];
		for(int i=0;i<participant.gradeCount;i++){
			completedGrades[i] = participant.grades[i];
		}
		return completedGrades;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * 
	 * getTrainee(java.lang.String)
	 */
	public Trainee getTrainee(String name) {
		TraineeGrades participant = find(name);
		return participant.trainee;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ro.tm.siit.w10h.SiteManagerCatalogInterface#
	 * printCatalog()
	 */
	public void printCatalog() {
		System.out.println("Catalog " + name + " has " + trainees.length + " trainees");
		for (TraineeGrades t : trainees) {
			if (t != null) {
				System.out.println(t.trainee.getName() + " " + t.getAvgGrade());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Catalog " + name + " has " + trainees.length + " trainees";
	}

	/**
	 * searches for a Trainee with specified name
	 * 
	 * @param name
	 *            the name fo the trainee
	 * @return a Trainee object or null if not found
	 */
	private TraineeGrades find(String name) {
		for (TraineeGrades t : trainees) {
			if (t.trainee.getName().equals(name)) {
				return t;
			}
		}
		return null;
	}

	private class TraineeGrades {
		private Trainee trainee;
		private int[] grades = new int[10];
		private int gradeCount = 0;

		public TraineeGrades(Trainee trainee) {
			super();
			this.trainee = trainee;
		}

		/**
		 * @param grade
		 *            adds a new grade to trainee
		 */
		public void addGrade(int grade) {
			this.grades[gradeCount++] = grade;
		}

		/**
		 * @return a float as average of the grades
		 */
		public float getAvgGrade() {
			int sum = 0;
			for (int i = 0; i < gradeCount; i++) {
				sum += grades[i];
			}
			return ((float) sum) / gradeCount;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			String out = trainee.getName() + " : ";
			for (int i = 0; i < gradeCount; i++) {
				out += grades[i] + " ";
			}
			return out;
		}
	}

}
