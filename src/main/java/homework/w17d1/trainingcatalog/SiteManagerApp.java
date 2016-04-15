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
 * @author nicolicioiul
 *
 */
public class SiteManagerApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Messenger messenger = SimpleMessenger.getInstance();
		Persistence storage = new Persistence();
		Catalog catalog = storage.loadCatalog();
		catalog.setMessenger(messenger);
		SiteManagerCatalogInterface siteManagerInterface = catalog;
		TraineeCatalogInterface traineeCatalogInterface = catalog;
		TrainerCatalogInterface trainerCatalogInterface = catalog;
		SiteManager siteManager = new SiteManager("Diana", "diana.tataran@gmail.com", siteManagerInterface, messenger);

		// create the GUI and handle events in EDT
		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				createGUI(catalog, messenger, siteManager, traineeCatalogInterface, trainerCatalogInterface);
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
	private static void createGUI(Catalog catalog,  Messenger messenger,  SiteManager siteManager,  TraineeCatalogInterface traineeCatalogInterface, TrainerCatalogInterface trainerCatalogInterface) {
		JFrame window = new JFrame("Trainee");
		window.setSize(900, 500);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		window.getContentPane().setLayout(new GridLayout(3,2));
		JPanel manageTraineePanel = createManageTraineePanel(catalog, siteManager, messenger, traineeCatalogInterface);
		window.add(manageTraineePanel);


		JPanel traineeGradesAndCatalogPanel = new JPanel();
		traineeGradesAndCatalogPanel.setLayout(new GridLayout(1, 2));
		window.add(traineeGradesAndCatalogPanel);


		//creates display catalog panel
		JPanel catalogPanel = createCatalogPanel(siteManager);
		traineeGradesAndCatalogPanel.add(catalogPanel);

		JPanel manageCatalogPanel = createManageCatalogPanel(catalog, trainerCatalogInterface, messenger);
		window.add(manageCatalogPanel);		


		// listeners that saves catalog on exit
		window.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				save(catalog);
			}

		});

		window.setVisible(true);
	}
	/**
	 * Create catalog list of trainee
	 * @param siteManager
	 * @return JPanel
	 */
	private static JPanel createCatalogPanel( SiteManager siteManager) {
		JPanel catalogPanel = new JPanel();
		catalogPanel.setLayout(new GridLayout(2,1));
		JButton nameBtn = new JButton("View catalog");
		catalogPanel.add(nameBtn);
		JTable catalog = new JTable();
		nameBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				siteManager.displayCatalog(catalog);

			}
		});
		JScrollPane scrollPane = new JScrollPane(catalog);
		catalog.setFillsViewportHeight(true);
		catalogPanel.add(scrollPane);
		return catalogPanel;
	}
	/**
	 * Create manage train
	 * @param catalog
	 * @param siteManager
	 * @param messenger
	 * @param traineeCatalogInterface
	 * @return JPanel
	 */
	private static JPanel createManageTraineePanel(Catalog catalog,  SiteManager siteManager,  Messenger messenger,  TraineeCatalogInterface traineeCatalogInterface) {
		JPanel manageTrainee = new JPanel();
		manageTrainee.setLayout(new GridLayout(1,5));
		JLabel nameLabel = new JLabel("Trainee name ");
		manageTrainee.add(nameLabel);
		JTextField name = new JTextField("");
		manageTrainee.add(name);

		JLabel emailLabel = new JLabel("Email ");
		manageTrainee.add(emailLabel);

		JTextField mail = new JTextField("");
		manageTrainee.add(mail);

		JButton addBtn = new JButton("Add Trainee");
		manageTrainee.add(addBtn);

		addBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Trainee  t = new Trainee(name.getText(),  mail.getText(), messenger, traineeCatalogInterface);
					siteManager.addTrainee(t);
					JOptionPane.showMessageDialog(manageTrainee, "The trainee has been added");
					name.setText("");
					mail.setText("");
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(manageTrainee, e1.getMessage());
				}catch (IllegalArgumentException e1) {
					JOptionPane.showMessageDialog(manageTrainee, e1.getMessage());
				}catch (IllegalStateException e1) {
					JOptionPane.showMessageDialog(manageTrainee, e1.getMessage());
				}
			}
		});
		return manageTrainee;
	}
	/**
	 * Create panel used to manage catalog
	 * @param catalog
	 * @param trainerCatalog
	 * @param messenger
	 * @return JPanel
	 */
	private static JPanel createManageCatalogPanel(Catalog catalog, TrainerCatalogInterface trainerCatalog,  Messenger messenger) {
		JPanel manageCatalogPanel = new JPanel();
		manageCatalogPanel.setLayout(new GridLayout(1,2));
		JLabel nameLabel = new JLabel("Trainer name ");
		manageCatalogPanel.add(nameLabel);
		JTextField name = new JTextField("");
		manageCatalogPanel.add(name);

		JLabel emailLabel = new JLabel("Email ");
		manageCatalogPanel.add(emailLabel);

		JTextField mail = new JTextField("");
		manageCatalogPanel.add(mail);
		JButton startBtn = new JButton("Start training");
		manageCatalogPanel.add(startBtn);

		JButton finishBtn = new JButton("Finish training");
		manageCatalogPanel.add(finishBtn);
		
		startBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Trainer trainer = new Trainer(name.getText(),  mail.getText(), trainerCatalog, messenger);
					catalog.startTraining(trainer);
					JOptionPane.showMessageDialog(manageCatalogPanel, "Training started");
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(manageCatalogPanel, e1.getMessage());
				}catch (IllegalArgumentException e1) {
					JOptionPane.showMessageDialog(manageCatalogPanel, e1.getMessage());
				}catch (IllegalStateException e1) {
					JOptionPane.showMessageDialog(manageCatalogPanel, e1.getMessage());
				}
			}
		});

		finishBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					catalog.stopTraining();
					JOptionPane.showMessageDialog(manageCatalogPanel, "Training finished");
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(manageCatalogPanel, e1.getMessage());
				}catch (IllegalArgumentException e1) {
					JOptionPane.showMessageDialog(manageCatalogPanel, e1.getMessage());
				}catch (IllegalStateException e1) {
					JOptionPane.showMessageDialog(manageCatalogPanel, e1.getMessage());
				}
			}
		});
		return manageCatalogPanel;
	}



}
