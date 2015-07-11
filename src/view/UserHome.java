package view;


import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import util.BackButtonListener;
import util.ExitProcedure;
import util.LogoutButtonActionListener;
import util.ObjectHighlightListener;
import util.TextInputListener;
import control.Control;

public class UserHome extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JScrollPane scrollPane2;
	private Control control;
	private LogoutButtonActionListener logoutButtonActionListener;
	private JTextField albumNameTextField;
	private JPanel albumCommandPanel;
	private JButton addAlbumButton;
	private static JButton deleteAlbumButton;
	private static JButton renameAlbumButton;
	private JPanel globalCommandPanel;
	private JButton searchButton;
	private JButton logoutButton;
	private JPanel displayAlbumPanel;
	private static JTextArea albumCommandInfo;
	private TextInputListener addAlbumTextInputListener;
	private final static String frameName = "UserHome";

	public UserHome() {
		
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
		
		
		albumCommandPanel = new JPanel();
		albumCommandPanel.setBorder(new TitledBorder(null, "Album Commands", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		albumCommandPanel.setBounds(20, 55, 200, 381);
		contentPane.add(albumCommandPanel);
		albumCommandPanel.setLayout(null);
		
		
		albumNameTextField = new JTextField();
		albumNameTextField.setBounds(16, 26, 165, 36);
		albumCommandPanel.add(albumNameTextField);
		albumNameTextField.setColumns(10);
		albumNameTextField.setText("<new album name>");
		
		albumNameTextField.addMouseListener(new MouseAdapter() {
			
		    public void mouseClicked(MouseEvent e) {
		    	
				if(ObjectHighlightListener.getCurrent() != null){
					
					if(ObjectHighlightListener.getCurrent().isEditable()){
						
						ObjectHighlightListener.getCurrent().toggleThumbnailName();
						
					}
					
				}
		    	
		    	if(albumNameTextField.getText().equalsIgnoreCase("<new album name>"))
		    		albumNameTextField.setText("");
		    }
		});
		
		albumCommandInfo = new JTextArea();
		albumCommandInfo.setBounds(16, 62, 165, 38);
		albumCommandPanel.add(albumCommandInfo);
		albumCommandInfo.setColumns(10);
		albumCommandInfo.setBackground(UIManager.getColor("Button.background"));
		albumCommandInfo.setEditable(false);
		albumCommandInfo.setLineWrap(true);
		
		addAlbumButton = new JButton("Add Album");
		addAlbumButton.setBounds(16, 120, 165, 40);
		addAlbumButton.setEnabled(false);
		albumCommandPanel.add(addAlbumButton);
		

		
		deleteAlbumButton = new JButton("Delete Album");
		deleteAlbumButton.setEnabled(false);
		deleteAlbumButton.setBounds(16, 172, 165, 40);
		albumCommandPanel.add(deleteAlbumButton);
		
		
		renameAlbumButton = new JButton("Rename Album");
		renameAlbumButton.setEnabled(false);
		renameAlbumButton.setBounds(16, 224, 165, 40);
		albumCommandPanel.add(renameAlbumButton);
		
		
		globalCommandPanel = new JPanel();
		globalCommandPanel.setBorder(new TitledBorder(null, "Global Commands", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		globalCommandPanel.setBounds(20, 448, 200, 313);
		contentPane.add(globalCommandPanel);
		globalCommandPanel.setLayout(null);
		
		searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				control.switchScreens(frameName, "SearchScreen");
				
			}
			
			
			
		});

		searchButton.setBounds(16, 30, 165, 40);
		globalCommandPanel.add(searchButton);
		
		logoutButton = new JButton("Logout");
		logoutButton.setBounds(16, 82, 165, 40);
		globalCommandPanel.add(logoutButton);
		
		displayAlbumPanel = new JPanel();
		displayAlbumPanel.setSize(774, 706);
		displayAlbumPanel.setBounds(233, 55, 774, 706);
		
		//GridBagConstraints grid = new GridBagConstraints();
		GridBagLayout gridLayout = new GridBagLayout();

		
		gridLayout.layoutContainer(displayAlbumPanel);

		
		
		displayAlbumPanel.setLayout(gridLayout);
		
		control.listAllAlbums(displayAlbumPanel);
		contentPane.add(displayAlbumPanel);
		scrollPane2 = new JScrollPane(displayAlbumPanel);
		scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane2.setBounds(233, 55, 774, 706);
		contentPane.add(scrollPane2); 
		logoutButtonActionListener = new LogoutButtonActionListener(this);
		logoutButton.addActionListener(logoutButtonActionListener);
		
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				control.logout();
				ExitProcedure.exitProcedure();
			}
		});

		addAlbumTextInputListener = new TextInputListener(addAlbumButton, albumNameTextField, albumCommandInfo, displayAlbumPanel, 3);
		albumNameTextField.getDocument().addDocumentListener(addAlbumTextInputListener);
		albumNameTextField.addActionListener(addAlbumTextInputListener);
		addAlbumButton.addActionListener(addAlbumTextInputListener);

		JButton backButton = new JButton("Back");
		backButton.addActionListener(new BackButtonListener(this));
		backButton.setEnabled(true);

		backButton.setBounds(890, 6, 117, 37);
		contentPane.add(backButton);
		deleteAlbumButton.addActionListener(new ObjectHighlightListener(displayAlbumPanel, 1));
		renameAlbumButton.addActionListener(new ObjectHighlightListener(displayAlbumPanel, 2));
		JLabel titleLabel = new JLabel(control.currentUser().toString() + "'s albums");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		titleLabel.setBounds(0, 6, 1024, 37);
		contentPane.add(titleLabel);

	}
	
	public static JButton getDelete(){
		
		return deleteAlbumButton;
		
	}
	
	public static JButton getEdit(){
		
		return renameAlbumButton;
	}

	public static String getFrameName() {
		
		return frameName;
		
	}
	
	public String toString(){
		
		return frameName;
		
	}
	
	public static UserHome factory(){
		
		return new UserHome();
		
	}
	
	public static void setMessage(String message){
		
		albumCommandInfo.setText(message);
	}

	
}
