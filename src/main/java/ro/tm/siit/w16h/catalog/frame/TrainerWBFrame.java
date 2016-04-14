package ro.tm.siit.w16h.catalog.frame;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import javax.swing.ListSelectionModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import ro.tm.siit.w10h.SerializableStorage;
import ro.tm.siit.w10h.StorageInterface;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.SpringLayout;

public class TrainerWBFrame {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TrainerWBFrame window = new TrainerWBFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TrainerWBFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 650, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(2, 1));
		frame.add(createAddGradePanel(frame));
	}

	private JPanel createAddGradePanel(JFrame frame) {
		// Add grade lavel
		JPanel jPanelAddGrade = new JPanel();
		jPanelAddGrade.setLayout(new GridLayout(1, 5));
		JLabel JLabelName = new JLabel("Name");
		JButton JButtonAddGrade = new JButton("Add Grade");
		JTextField JTextfielName = new JTextField();
		JLabel JLabelGrade = new JLabel("Grade");
		JTextField JTextfielGrade = new JTextField();
		JButton JButtonAddGrade = new JButton("Add Grade");
		

		jPanelAddGrade.add(JLabelName);
		jPanelAddGrade.add(JTextfielName);
		jPanelAddGrade.add(JLabelGrade);
		jPanelAddGrade.add(JTextfielGrade);
		
		jPanelAddGrade.add(JButtonAddGrade);
		
		JButtonAddGrade.addActionListener(new ActionListener()
	    {
		      public void actionPerformed(ActionEvent e){
		    	  
		      }
		});
		return jPanelAddGrade;
		
	}
}
