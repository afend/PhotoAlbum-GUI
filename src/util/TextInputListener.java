package util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import model.User;
import view.AdminLogin;
import view.UserAlbum;
import view.UserHome;
import control.Control;

/**
 * Unversal listner for textfields + buttons, multiple options
 *
 */
public class TextInputListener implements ActionListener, DocumentListener {
	
	private JButton button;
	private JTextField textField1;
	private JTextField textField2;
	private JTextArea textArea;
	private boolean alreadyEnabled = false;
	private int options;
	private JList<User> list;
	private DefaultListModel<User> listModel;
	private Control control = new Control();
	private JFrame window;
	private JPanel panel;
	
	public TextInputListener(JButton button, JTextField textField1, JTextArea textArea, JPanel panel, int options) {
		
		this.button = button;
		this.textField1 = textField1;
		this.options = options;
		this.textArea = textArea;
		this.panel = panel;
	}
	
	public TextInputListener(JFrame window, JButton button, JTextField textField1, JTextArea textArea, int options) {
		
		this.window = window;
		this.button = button;
		this.textField1 = textField1;
		this.options = options;
		this.textArea = textArea;
		
	}
	
	public TextInputListener(JFrame window, JButton button, JTextField textField1, JTextField textField2, JList<User> list, DefaultListModel<User> listModel, int options) {
		
		this.window = window;
		this.button = button;
		this.textField1 = textField1;
		this.textField2 = textField2;
		this.list = list;
		this.listModel = listModel;
		this.options = options;
		this.list = list;
		
		
	}

	public void insertUpdate(DocumentEvent e) {
		
		if (!handleEmptyTextField(e))
		enableButton();
		
	}


	public void removeUpdate(DocumentEvent e) {
		
		handleEmptyTextField(e);
		
	}


	public void changedUpdate(DocumentEvent e) {
		
		if (!handleEmptyTextField(e))
		enableButton();
		
	}


	public void actionPerformed(ActionEvent e) {

		
		if(this.options == 1){
			
			this.textArea.setText("");
			String inputText = textField1.getText();
			
			if(inputText.equalsIgnoreCase("admin")){
				
				//control.addFrame("admin", new AdminLogin());
			
				control.switchScreens(window.toString(), "AdminLogin");
				this.textField1.setText("");
			
			}else{
				
				if(control.login(inputText)){
					
					//this.textArea.setText("Welcome back " + inputText)
					
					control.switchScreens(window.toString(), "UserHome");
					this.textField1.setText("");
					
				}else{
					
					this.textArea.setText("Error: \"" + inputText + "\" is not a valid userID");
					
				}
				
			}
		}else if(this.options == 2){
			
			String inputText = textField1.getText();
			String inputText2 = textField2.getText();
			
			if(control.addNewUser(inputText, inputText2)){
				
				int index = list.getSelectedIndex();
				
				if (index == -1) { //at begin
					
					index = 0;
					
				} else {
					
					index++;
					
				}
				User toAdd = control.getUser(inputText);
				listModel.insertElementAt(toAdd, index);
				
				textField1.setText("");
				textField2.setText("");
				
				sortList();
				index = listModel.indexOf(toAdd);
				list.setSelectedIndex(index);
				list.ensureIndexIsVisible(index);
				AdminLogin.setFeedback("");
				
			}else{
				
				AdminLogin.setFeedback("ERROR: the userID \"" + inputText + "\" is in use"); 
				
			}
			
		}else if (this.options == 3){
			
			String albumName = this.textField1.getText();
			
			if(control.createAlbum(albumName)){
				
				control.currentUser().addAlbumToPanel(panel, albumName);
				this.textField1.setText("");
				UserHome.getDelete().setEnabled(false);
				UserHome.getEdit().setEnabled(false);
				
			}else{
				
				this.textArea.setText("ERROR:\n*album already exists*");
				this.textField1.setText("");
			}
			
		}else if (this.options == 4){
			
			String photoName = this.textField1.getText();
			
			if(control.addPhoto(photoName, control.getCurrentAlbum())){
				
				control.currentUser().hashAlbum(control.getCurrentAlbum()).addPhotoToPanel(panel, photoName);
				this.textField1.setText("");
				UserAlbum.getDelete().setEnabled(false);
				UserAlbum.getEdit().setEnabled(false);
				
			}
			
		}
	}
	
	private void enableButton() {
		
		if (!alreadyEnabled) {
			
			button.setEnabled(true);
			
		}
	}
	
	private boolean handleEmptyTextField(DocumentEvent e) {
		
		if(this.options == 2){
			
			String str1 = textField1.getText();
			String str2 = textField2.getText();
			
			if( str1.isEmpty() || str2.isEmpty() ){
				
				button.setEnabled(false);
				alreadyEnabled = false;
				return true;
			}
			
			return false;
			
		}
		
		if(this.options == 3){
			
			String str1 = textField1.getText();
			
			if( str1.isEmpty()){
				
				button.setEnabled(false);
				alreadyEnabled = false;
				return true;
			
			}
			
			return false;
			
		}
		
		
			
		if (e.getDocument().getLength() <= 0) {
				
				button.setEnabled(false);
				alreadyEnabled = false;
				return true;
				
			}
		
			return false;

		}
	
	public void sortList() {
		User temp;
		User[] ob = new User[listModel.getSize()];
		for (int i = 0; i < listModel.getSize(); i++)
		ob[i] = listModel.getElementAt(i);
		int n = ob.length;
		for (int i = 0; i < n; i++)
		for (int j = 0; j < n - i - 1; j++) {
			if (ob[j].toString().compareTo(ob[j + 1].toString()) > 0) // used to sort strings
			{
				temp = ob[j];
				ob[j] = ob[j + 1];
				ob[j + 1] = temp;
			}

		}
		listModel.removeAllElements();
		for (int i = 0; i < n; i++)
		listModel.addElement( ob[i]);

	}
	
	

}