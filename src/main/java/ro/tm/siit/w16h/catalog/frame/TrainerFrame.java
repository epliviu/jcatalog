package ro.tm.siit.w16h.catalog.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.InvalidParameterException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import ro.tm.siit.w10h.*;

public class TrainerFrame extends BaseFrame{

	public static void main(String[] args) {
		// make the window visible
		Messenger messenger = SimpleMessenger.getInstance();
		
		Catalog catalog =  Catalog.getInstance("Java S2", messenger);
		
		drawInterface("Trainer", catalog);
	}
	
	public static void drawInterface(String title, final Catalog catalog){
		window.setTitle(title);
		try {
			// TODO Auto-generated method stub
			window.setSize(1000, 600); //width, height
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			
			// Add student input text 
			final JTextField  textfieldAddStudent = new JTextField();
			textfieldAddStudent.setPreferredSize( new Dimension( 200, 24 ) );
			// Add buttons
			JButton  buttonAddStudent =  new JButton("Add trainee");
			JButton  buttonSave =  new JButton("Save");
			JButton  buttonStartTrainning =  new JButton("Start trainig");
			buttonSave.setHorizontalAlignment(SwingConstants.RIGHT);
			buttonSave.setVerticalAlignment(SwingConstants.BOTTOM);
			
			
			// load saved 
			DefaultListModel listModel = new DefaultListModel();
			final JList studentsLists = new JList();
		    studentsLists.setModel(listModel);
		    JScrollPane scrollPane = new JScrollPane();
		    scrollPane.setViewportView(studentsLists);
		    
		    JLabel label = new JLabel("Trainee");
		    // Add student panels
		    JPanel panelStudent = new JPanel();
		    panelStudent.add(label);
		    panelStudent.add(scrollPane);
			panelStudent.add(textfieldAddStudent);
			panelStudent.add(buttonAddStudent);
			window.add(panelStudent, BorderLayout.WEST);
			// Add manage panel
			JPanel manageTraining = new JPanel();
			manageTraining.add(buttonStartTrainning);
			manageTraining.add(buttonSave);
			
			window.add(manageTraining);
			window.setVisible(true);
			
			// Attach events
			buttonAddStudent.addActionListener(new ActionListener()
		    {
			      public void actionPerformed(ActionEvent e)
			      {
			    	  String name = textfieldAddStudent.getText();
			    	  if(name.length() == 0){
			    		  displayMessage("Please enter student name", "Error", JOptionPane.ERROR_MESSAGE);
			    	  }
			    	  // add item
			    	  addJListItem(studentsLists, name);
			    	  textfieldAddStudent.setText("");
			      }
			});
			buttonSave.addActionListener(new ActionListener()
		    {
			      public void actionPerformed(ActionEvent e)
			      {
			    	  try {
			    		  StorageInterface storage = new SerializableStorage();
			    		  storage.writeObject("catalog", catalog);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			      }
			 });
		}catch(InvalidParameterException e){
			System.out.println(e.getMessage());
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
}
