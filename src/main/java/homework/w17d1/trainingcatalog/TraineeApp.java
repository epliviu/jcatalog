/**
 * 
 */
package homework.w17d1.trainingcatalog;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import homework.w17d1.trainingcatalog.catalog.Catalog;
import homework.w17d1.trainingcatalog.messenger.SimpleMessenger;
import homework.w17d1.trainingcatalog.person.SiteManager;
import homework.w17d1.trainingcatalog.person.Trainee;
import homework.w17d1.trainingcatalog.person.Trainer;

/**
 * @author mco
 *
 */
public class TraineeApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		final  Messenger messenger = SimpleMessenger.getInstance();
		Persistence storage = new Persistence();
		final Catalog catalog = storage.loadCatalog();
		if (catalog  == null) {
			System.out.println("no catalog available");
			return;
		}
		catalog.setMessenger(messenger);

		TrainerCatalogInterface trainerInterface = catalog;
		final Trainer trainer = new Trainer("madalin", "madalin.cosma@gmail.com", trainerInterface, messenger);

		SiteManagerCatalogInterface siteManagerInterface = catalog;
		final SiteManager siteManager = new SiteManager("diana", "diana@gmail.com", siteManagerInterface, messenger);

		// create the GUI and handle events in EDT
		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				createGUI(catalog, messenger, trainer, siteManager);
			}
		});

	}

	/**
	 * constructs the GUI
	 * 
	 * @param messenger
	 * @param catalog
	 * @param siteManager
	 * @param trainer
	 */
	private static void createGUI(final Catalog catalog, final  Messenger messenger, final  Trainer trainer, final  SiteManager siteManager) {
		final JFrame window = new JFrame("Trainee");
		window.setSize(600, 400);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		window.getContentPane().setLayout(new GridLayout(2, 3));

		JLabel nameLabel = new JLabel("Name ");
		window.add(nameLabel);
		final JTextField name = new JTextField("");
		window.add(name);
		final JButton nameBtn = new JButton("Validate");
		window.add(nameBtn);
		final  JLabel gradeLabel = new JLabel("Your last grade is ");
		window.add(gradeLabel);
		final  JLabel gradeLabel1 = new JLabel();
		window.add(gradeLabel1);
		final JButton feedbackBtn = new JButton("Send Feedback");
		feedbackBtn.setEnabled(false);
		window.add(feedbackBtn);

		nameBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					final Trainee trainee = catalog.find(name.getText());
					gradeLabel1.setText(trainee.getLastGrade() + "");
					nameBtn.setEnabled(false);
					name.setEnabled(false);
					feedbackBtn.setEnabled(true);
				} catch (IllegalArgumentException ex) {
					JOptionPane.showMessageDialog(window, "The trainee does not exist.");
				}

			}
		});

		feedbackBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Trainee trainee = catalog.find(name.getText());
					trainee.sendFeedback(trainer);
					feedbackBtn.setEnabled(false);
					JOptionPane.showMessageDialog(window, "The feedback has been sent to " + trainer.getName());
				} catch (IllegalArgumentException ex) {
					JOptionPane.showMessageDialog(window, ex.getMessage());
				}

			}
		});

		window.setVisible(true);
	}

}
