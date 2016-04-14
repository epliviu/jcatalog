package ro.tm.siit.w16h.catalog.frame;
import ro.tm.siit.w10h.*;
import java.awt.Toolkit;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;

class BaseFrame {
	final protected static JFrame window = new JFrame();
	/**
	 * Display message
	 * @param message
	 * @param type
	 */
	public static void displayMessage(String message, String title, int type){
		Toolkit.getDefaultToolkit().beep();
		JOptionPane.showMessageDialog(window,
			    message, 
			    title,
			    type);
	}
	/**
	 * add element into a Jlist
	 * @param list
	 * @param element
	 */
	public static void addJListItem(JList list, String element){
		DefaultListModel model = (DefaultListModel) list.getModel();
		int index = model.getSize();
		if(!model.contains(element)){
			model.addElement(element);
			list.setSelectedIndex(index);
		}
	}
}
