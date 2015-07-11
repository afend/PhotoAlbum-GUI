package view;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.User;
import util.DeleteButtonListener;
import util.ExitProcedure;
import util.LogoutButtonActionListener;
import util.TextInputListener;
import control.Control;

@SuppressWarnings("serial")
public class AdminLogin extends JFrame {

	private final static String frameName = "AdminLogin";
	private JPanel contentPane;
	private JTextField userNameTextField;
	private JTextArea loginDialogue;
	private JTextField pwTextField;
	private JButton deleteUserButton;
	private JTextArea userDetailTextArea;
	private JButton logoutButton;
	private JTextField textField_2;
	private JList<User> list;
	private DefaultListModel<User> listModel;
	private TextInputListener textInputListenerLogin;
	private TextInputListener textInputListenerAdd;
	private singleListSelectionListener listListener;
	private Control control;
	private ArrayList<User> users;
	private DeleteButtonListener deleteButtonListener;
	private LogoutButtonActionListener logoutButtonListener;
	private static JTextArea adminFeedback;

	/**
	 * Create the frame.
	 */
	public AdminLogin() {
		control = new Control();
		setTitle("PhotoAlbum30");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 800);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel userListPanel = new JPanel();
		userListPanel.setBorder(new TitledBorder(null, "User List", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		userListPanel.setBounds(20, 20, 189, 450);
		contentPane.add(userListPanel);
		userListPanel.setLayout(null);
		
		listModel = new DefaultListModel<User>();
		list = new JList<User>(listModel);
		listListener = new singleListSelectionListener();
		list.addListSelectionListener(listListener);
		list.setBounds(16, 23, 156, 410);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setSelectedIndex(-1);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(1);
		userListPanel.add(list);
		
		JPanel adminCommandPanel = new JPanel();
		adminCommandPanel.setBorder(new TitledBorder(null, "Admin Commands", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		adminCommandPanel.setBounds(20, 482, 189, 276);
		contentPane.add(adminCommandPanel);
		adminCommandPanel.setLayout(null);
		
		userNameTextField = new JTextField();
		userNameTextField.setBounds(16, 26, 151, 36);
		adminCommandPanel.add(userNameTextField);
		userNameTextField.setColumns(10);
		userNameTextField.setText("<user ID>");
		
		pwTextField = new JTextField();
		pwTextField.setBounds(16, 66, 151, 36);
		adminCommandPanel.add(pwTextField);
		pwTextField.setColumns(10);
		pwTextField.setText("<user name>");
		
		JButton addUserButton = new JButton("Add User");

		addUserButton.setBounds(16, 114, 151, 40);
		adminCommandPanel.add(addUserButton);
		
		deleteUserButton = new JButton("Delete User");
		deleteUserButton.setEnabled(false);

		deleteUserButton.setBounds(16, 166, 151, 40);
		adminCommandPanel.add(deleteUserButton);
		
		logoutButton = new JButton("Logout");

		logoutButton.setBounds(16, 218, 151, 40);
		adminCommandPanel.add(logoutButton);
		
		JPanel adminLoginPanel = new JPanel();
		adminLoginPanel.setBorder(new TitledBorder(null, "Administrative Login", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		adminLoginPanel.setBounds(279, 482, 686, 276);
		contentPane.add(adminLoginPanel);
		adminLoginPanel.setLayout(null);
		
		JLabel lblLogin = new JLabel("Login:");
		lblLogin.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		lblLogin.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLogin.setBounds(162, 6, 61, 264);
		adminLoginPanel.add(lblLogin);
		
		textField_2 = new JTextField();
		textField_2.setBounds(235, 119, 215, 40);
		adminLoginPanel.add(textField_2);
		textField_2.setColumns(10);
		
		loginDialogue = new JTextArea();
		loginDialogue.setBounds(235, 99, 215, 20);	
		loginDialogue.setColumns(10);
		loginDialogue.setBackground(UIManager.getColor("Button.background"));
		loginDialogue.setEditable(false);
		adminLoginPanel.add(loginDialogue);
		
		JButton loginButton = new JButton("Login");
		loginButton.setBounds(333, 171, 117, 40);
		adminLoginPanel.add(loginButton);
		
		JLabel lblAdministrator = new JLabel("Administrator");
		lblAdministrator.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		lblAdministrator.setBounds(475, 49, 269, 59);
		contentPane.add(lblAdministrator);
		
		JPanel userDetailPanel = new JPanel();
		userDetailPanel.setBorder(new TitledBorder(null, "User Details", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		userDetailPanel.setBounds(279, 173, 686, 276);
		contentPane.add(userDetailPanel);
		userDetailPanel.setLayout(null);
		
		userDetailTextArea = new JTextArea();
		userDetailTextArea.setBackground(UIManager.getColor("Button.background"));
		userDetailTextArea.setText("User Name:\n\nAlbum Count: \n\nPhoto Count:  ");
		userDetailTextArea.setTabSize(30);
		userDetailTextArea.setRows(7);
		userDetailTextArea.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		userDetailTextArea.setEditable(false);
		userDetailTextArea.setBounds(38, 35, 621, 190);
		userDetailPanel.add(userDetailTextArea);
		adminFeedback = new JTextArea();
		adminFeedback.setBackground(UIManager.getColor("Button.background"));
		adminFeedback.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		adminFeedback.setEditable(false);
		adminFeedback.setBounds(38, 232, 400, 30);
		adminFeedback.setText("");
		userDetailPanel.add(adminFeedback);
		
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				control.logout();
				ExitProcedure.exitProcedure();
			}
		});
	
		logoutButtonListener = new LogoutButtonActionListener(this);
		logoutButton.addActionListener(logoutButtonListener);
		textInputListenerLogin = new TextInputListener(this, loginButton, textField_2, loginDialogue, 1);
		loginButton.addActionListener(textInputListenerLogin);
		textField_2.addActionListener(textInputListenerLogin);
		textField_2.getDocument().addDocumentListener(textInputListenerLogin);
		textInputListenerAdd = new TextInputListener(this, addUserButton, userNameTextField, pwTextField, list, listModel, 2);
		addUserButton.addActionListener(textInputListenerAdd);
		userNameTextField.addActionListener(textInputListenerAdd);
		userNameTextField.getDocument().addDocumentListener(textInputListenerAdd);
		pwTextField.addActionListener(textInputListenerAdd);
		pwTextField.getDocument().addDocumentListener(textInputListenerAdd);
		deleteButtonListener = new DeleteButtonListener(list, listModel, deleteUserButton);
		deleteUserButton.addActionListener(deleteButtonListener);
		addUserButton.setEnabled(false);
		users = control.listUsers();
		
		if (users.size() > 0) {
			deleteUserButton.setEnabled(true);
			
			for (User user: users) {	
				listModel.addElement(user);
			}
		}
		
		userNameTextField.addMouseListener(new MouseAdapter() {

		    public void mouseClicked(MouseEvent e) {
		    	if (userNameTextField.getText().equalsIgnoreCase("<user ID>"))
		    		userNameTextField.setText("");
		    }
		});
		
		pwTextField.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) { 	
		    	if (pwTextField.getText().equalsIgnoreCase("<user name>"))
		    		pwTextField.setText("");
		    }
		});
	}
	
	class singleListSelectionListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent listSelectionEvent) {
			if (listModel.size() > 0) {
				deleteUserButton.setEnabled(true);
			}
			
			if (!listSelectionEvent.getValueIsAdjusting()) {
				int i = list.getSelectedIndex();
				int size = listModel.getSize();
				if (i >= 0) {
					displayUser(listModel.elementAt(list.getSelectedIndex()).toString());
					textField_2.setText(listModel.elementAt(list.getSelectedIndex()).toString());
				} else if (size == 0) {		
					emptyDisplay();
					textField_2.setText("");
				}
			}
		}
	}
	
	public void displayUser(String userID) {
		User user = control.getUser(userID);
		userDetailTextArea.setText("User Name: " + user.getUser() + "\n\nAlbum Count: " + user.getAlbumCount() + "\n\nPhoto Count: " + user.getPhotoCount());
	}
	
	public void emptyDisplay() {
		userDetailTextArea.setText("User Name:\n\nAlbum Count: \n\nPhoto Count:  ");
	}

	public static String getFrameName() {
		return frameName;
	}
	
	public static void setFeedback(String feedback) {
		adminFeedback.setText(feedback);
	}
	
	public static AdminLogin factory() {
		return new AdminLogin();
	}
	
	public String toString() {
		return frameName;	
	}	
}