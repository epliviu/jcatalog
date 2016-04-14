/**
 * 
 */
package homework.w17d1.trainingcatalog;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
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
public class TrainerApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		final Messenger messenger = SimpleMessenger.getInstance();
		final Persistence storage = new Persistence();
		final Catalog catalog = storage.loadCatalog();
		catalog.setMessenger(messenger);
		TrainerCatalogInterface trainerInterface = catalog;
		final Trainer trainer = new Trainer("madalin", "madalin.cosma@gmail.com", trainerInterface, messenger);
		
		final SiteManagerCatalogInterface siteManagerInterface = catalog;
		final SiteManager siteManager = new SiteManager("diana", "diana@gmail.com", siteManagerInterface, messenger);

		// create the GUI and handle events in EDT
		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				createGUI(catalog, messenger, trainer, siteManager);
			}
		});

	}

	private static void save(Catalog catalog) {
		Persistence storage = new Persistence();
		storage.saveCatalog(catalog);
		
	}

	/**
	 * constructs the GUI
	 * 
	 * @param messenger
	 * @param catalog
	 * @param siteManager
	 * @param trainer
	 */
	private static void createGUI(final Catalog catalog, final Messenger messenger, final Trainer trainer, SiteManager siteManager) {
		JFrame window = new JFrame("Trainee");
		window.setSize(600, 400);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		window.getContentPane().setLayout(new GridLayout(2, 1));
		
		JPanel addGradePanel = createAddGradePanel(trainer);
		window.add(addGradePanel);
		JPanel traineeGradesAndCatalogPanel = new JPanel();
		window.add(traineeGradesAndCatalogPanel);
		traineeGradesAndCatalogPanel.setLayout(new GridLayout(1, 2));
		
//		JPanel viewGradesPanel = createViewGradesPanel(trainer);
//		traineeGradesAndCatalogPanel.add(viewGradesPanel);
		
		//creates display catalog panel
		JPanel catalogPanel = createCatalogPanel(trainer);
		traineeGradesAndCatalogPanel.add(catalogPanel);
		
		// listeners that saves catalog on exit
		window.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				save(catalog);
			}
			
		});

		window.setVisible(true);
	}

	private static JPanel createCatalogPanel(final Trainer trainer) {
		JPanel catalogPanel = new JPanel();
		catalogPanel.setLayout(new GridLayout(2,1));
		JButton nameBtn = new JButton("View catalog");
		catalogPanel.add(nameBtn);
		final JTable catalog = new JTable();
		nameBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				trainer.displayCatalog(catalog);
				
			}
		});
		JScrollPane scrollPane = new JScrollPane(catalog);
		catalog.setFillsViewportHeight(true);
		catalogPanel.add(scrollPane);
		return catalogPanel;
	}

	private static JPanel createAddGradePanel(final Trainer trainer) {
		final JPanel addGradePanel = new JPanel();
		addGradePanel.setLayout(new GridLayout(1,5));
		JLabel nameLabel = new JLabel("Name ");
	    addGradePanel.add(nameLabel);
		final JTextField name = new JTextField("");
		addGradePanel.add(name);
		final JLabel gradeLabel = new JLabel("Grade ");
		addGradePanel.add(gradeLabel);
		final JTextField grade = new JTextField("");
		addGradePanel.add(grade);
		JButton nameBtn = new JButton("Add Grade");
		addGradePanel.add(nameBtn);
		
		nameBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					trainer.addGrade(name.getText(), Integer.parseInt(grade.getText()));
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(addGradePanel, e1.getMessage());
				}catch (IllegalArgumentException e1) {
					JOptionPane.showMessageDialog(addGradePanel, e1.getMessage());
				}catch (IllegalStateException e1) {
					JOptionPane.showMessageDialog(addGradePanel, e1.getMessage());
				}
			}
		});
		return addGradePanel;
	}

}
