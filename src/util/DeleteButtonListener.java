package util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import control.Control;
import model.User;

public class DeleteButtonListener implements ActionListener {
	
	private JList<User> list;
	private DefaultListModel<User> listModel;
	private JButton button;
	private Control control;
	
	public DeleteButtonListener(JList<User> list, DefaultListModel<User> listModel, JButton button){
		
		this.list = list;
		this.listModel = listModel;
		this.button = button;
		this.control = new Control();
	}
	
	public void actionPerformed(ActionEvent e) {
		//This method can be called only if
		//there's a valid selection
		//so go ahead and remove whatever's selected.
		
		int index = list.getSelectedIndex();
		User toDelete = (User) listModel.get(index);
		control.deleteUser(toDelete.toString());
		listModel.remove(index);
		int size = listModel.getSize();

		if (size == 0) { //Nobody's left, disable firing.
			button.setEnabled(false);

		} else { //Select an index.
			if (index == listModel.getSize()) {
				//removed item in last position
				index--;
			}

			list.setSelectedIndex(index);
			//  list.ensureIndexIsVisible(index);
		}
	}

}
