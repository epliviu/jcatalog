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

		final Messenger messenger = SimpleMessenger.getInstance();
		final Persistence storage = new Persistence();
		final Catalog catalog = storage.loadCatalog();
		catalog.setMessenger(messenger);
		final SiteManagerCatalogInterface siteManagerInterface = catalog;
		final TraineeCatalogInterface traineeCatalogInterface = catalog;
		final SiteManager siteManager = new SiteManager("Diana", "diana.tataran@gmail.com", siteManagerInterface, messenger);
		
		// create the GUI and handle events in EDT
		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				createGUI(catalog, messenger, siteManager, traineeCatalogInterface);
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
	private static void createGUI(final Catalog catalog, final Messenger messenger, final SiteManager siteManager, final TraineeCatalogInterface traineeCatalogInterface ) {
		JFrame window = new JFrame("Trainee");
		window.setSize(800, 400);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		window.getContentPane().setLayout(new GridLayout(2, 1));
		JPanel manageTraineePanel = createManageTraineePanel(catalog, siteManager, messenger, traineeCatalogInterface);
		window.add(manageTraineePanel);
		
		window.getContentPane().setLayout(new GridLayout(2, 1));
		JPanel manageCatalogPanel = createManageCatalogPanel(catalog, siteManager, messenger);
		window.add(manageCatalogPanel);		
		
		
		JPanel traineeGradesAndCatalogPanel = new JPanel();
		window.add(traineeGradesAndCatalogPanel);
		traineeGradesAndCatalogPanel.setLayout(new GridLayout(1, 2));
		
//		JPanel viewGradesPanel = createViewGradesPanel(trainer);
//		traineeGradesAndCatalogPanel.add(viewGradesPanel);
		
		//creates display catalog panel
		JPanel catalogPanel = createCatalogPanel(siteManager);
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

	private static JPanel createCatalogPanel(final SiteManager siteManager) {
		JPanel catalogPanel = new JPanel();
		catalogPanel.setLayout(new GridLayout(2,1));
		JButton nameBtn = new JButton("View catalog");
		catalogPanel.add(nameBtn);
		final JTable catalog = new JTable();
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

	private static JPanel createManageTraineePanel(Catalog catalog, final SiteManager siteManager, final Messenger messenger, final TraineeCatalogInterface traineeCatalogInterface) {
		final JPanel manageTrainee = new JPanel();
		manageTrainee.setLayout(new GridLayout(1,5));
		JLabel nameLabel = new JLabel("Name ");
		manageTrainee.add(nameLabel);
		final JTextField name = new JTextField("");
		manageTrainee.add(name);
		
		JLabel emailLabel = new JLabel("Email ");
		manageTrainee.add(emailLabel);

		final JTextField mail = new JTextField("");
		manageTrainee.add(mail);

		JButton addBtn = new JButton("Add Trainee");
		manageTrainee.add(addBtn);
		final JTextField catalogName = new JTextField("");
		manageTrainee.add(catalogName);
		
		addBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Trainee  t = new Trainee(name.getText(),  mail.getText(), messenger, traineeCatalogInterface);
					siteManager.addTrainee(t);
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
	
	private static JPanel createManageCatalogPanel(Catalog catalog, final SiteManager siteManager, final Messenger messenger) {
		final JPanel addCatalogPanel = new JPanel();
		addCatalogPanel.setLayout(new GridLayout(1,5));
		JLabel nameLabel = new JLabel("Name ");
		addCatalogPanel.add(nameLabel);
		final JTextField name = new JTextField("");
		addCatalogPanel.add(name);
		
		JButton createBtn = new JButton("Create catalog");
		
		addCatalogPanel.add(createBtn);
		
		createBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(catalog == null){
						catalog = new Catalog(name.getText(), messenger);
					}else{
						throw new IllegalStateException("Catalog already exists");
					}
					
				}catch (IllegalArgumentException e1) {
					JOptionPane.showMessageDialog(addCatalogPanel, e1.getMessage());
				}catch (IllegalStateException e1) {
					JOptionPane.showMessageDialog(addCatalogPanel, e1.getMessage());
				}
			}
		});
		return addCatalogPanel;
	}

}
