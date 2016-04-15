/**
 * 
 */
package homework.w17d1.trainingcatalog.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import homework.w17d1.trainingcatalog.Status;
import homework.w17d1.trainingcatalog.Messenger;
import homework.w17d1.trainingcatalog.SiteManagerCatalogInterface;
import homework.w17d1.trainingcatalog.TraineeCatalogInterface;
import homework.w17d1.trainingcatalog.TrainerCatalogInterface;
import homework.w17d1.trainingcatalog.person.Trainee;
import homework.w17d1.trainingcatalog.person.Trainer;

/**
 * Catalog class implements TrainerCatalogInterface interface and model a grades
 * catalog for Scoala Informala de IT.
 * 
 * @author mcosma
 *
 */
public final class Catalog
		implements SiteManagerCatalogInterface, TrainerCatalogInterface, TraineeCatalogInterface, Serializable {


	private Map<Trainee, List<Integer>> trainees = new HashMap<Trainee, List<Integer>>();
	private String name;
	private transient Messenger messenger;
	private Trainer trainer;
	private Status status = Status.CREATED;

	/**
	 * @param name
	 *            the name of the training
	 * @param trainees
	 *            the trainees
	 */
	public Catalog(String name, Messenger messenger, Trainee... trainees) {
		super();
		this.name = name;
		for (Trainee t : trainees) {
			this.trainees.put(t, new ArrayList<Integer>());
		}
		this.messenger = messenger;
	}

	/**
	 * @param name
	 *            the name of the training
	 */
	public Catalog(String name) {
		super();
		this.name = name;
	}
	/**
	 * set messenger
	 * @param messenger
	 */
	public void setMessenger(Messenger messenger) {
		this.messenger = messenger;
		for (Trainee t : trainees.keySet()) {
			t.setMessenger(messenger);
		}
	}

	/**
	 * adds a Trainee to catalog
	 * 
	 * @param t
	 *            the trainee
	 */
	public void addTrainee(Trainee t) {
		if (status != Status.CREATED) {
			throw new IllegalStateException("training already started");
		}
		this.trainees.put(t, new ArrayList<Integer>());
	}
	@Override
	public void startTraining(Trainer trainer) {
		if (status != Status.CREATED) {
			throw new IllegalStateException("training cannot be started");
		}
		this.status = Status.STARTED;
		this.trainer = trainer;
	}

	@Override
	public void stopTraining() {
		if (status != Status.STARTED) {
			throw new IllegalStateException("training cannot be finished");
		}
		this.status = Status.FINISHED;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ro.tm.siit.homework.w10d2.trainingcatalog.TrainerCatalogInterface#
	 * addGrade(java.lang.String, int)
	 */
	public void addGrade(String name, int grade) {
		if (status != Status.STARTED) {
			throw new IllegalStateException("training not started");
		}
		if (grade < 0 || grade > 10) {
			throw new IllegalArgumentException("grade is not allowed: " + grade);
		}
		Trainee participant = find(name);
		List<Integer> list = trainees.get(participant);
		list.add(grade);
		messenger.sendMessage(participant.getEmail(), "New grade from " + trainer.getName(),
				"Your new grade is " + grade);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ro.tm.siit.homework.w10d2.trainingcatalog.SiteManagerCatalogInterface#
	 * printGrades(java.lang.String)
	 */
	public void printGrades(String name) {
		Trainee participant = find(name);
		System.out.println(getTraineeGrades(participant));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ro.tm.siit.homework.w10d2.trainingcatalog.SiteManagerCatalogInterface#
	 * printCatalog()
	 */
	public void printCatalog() {
		System.out.println("Catalog " + name + " has " + trainees.size() + " trainees");
		for (Trainee trainee : trainees.keySet()) {
			System.out.println(trainee.getName() + " " + getAvgGrade(trainee));
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ro.tm.siit.homework.w10d2.trainingcatalog.SiteManagerCatalogInterface#
	 * displayCatalog(JTable)
	 */
	public void displayCatalog(JTable catalog) {
		DefaultTableModel model = (DefaultTableModel) catalog.getModel();
		model.setColumnCount(0);
		model.setRowCount(0);
		model.addColumn("Name");
		model.addColumn("Mail");
		model.addColumn("Avg Grade");
		int row = 0;
		for (Trainee trainee : trainees.keySet()) {
			Object[] rowData = new Object[] { trainee.getName(), trainee.getEmail(), getAvgGrade(trainee) };
			model.addRow(rowData);
			row++;
		}
		model.fireTableStructureChanged();

	}

	@Override
	public int getLastGrade(String name) {
		Trainee participant = find(name);
		List<Integer> list = trainees.get(participant);
		if (list.isEmpty()) {
			throw new IllegalStateException("trainee has no grade: " + name);
		}
		return list.get(list.size() - 1);
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
	public Trainee find(String name) {
		for (Trainee t : trainees.keySet()) {
			if (t.getName().equals(name)) {
				return t;
			}
		}
		throw new IllegalArgumentException("no Trainee found with name " + name);
	}

	/**
	 * @param trainee
	 *            the trainee for which to calculate avg grade
	 * @return a float as average of the grades
	 */
	private float getAvgGrade(Trainee trainee) {
		int sum = 0;
		List<Integer> list = trainees.get(trainee);
		for (Integer i : list) {
			sum += i;
		}
		return sum > 0 ? ((float) sum) / list.size(): 0;
	}

	/**
	 * returns a string with name and the grades of this trainee
	 * 
	 * @param trainee
	 * @param list
	 * @return
	 */
	private String getTraineeGrades(Trainee trainee) {
		List<Integer> list = trainees.get(trainee);
		String out = trainee.getName() + " : ";
		for (Integer i : list) {
			out += i + " ";
		}
		return out;
	}
	/**
	 * Get catalog status
	 * @return
	 */
	public Status getStatus(){
		return this.status;
	}

}
