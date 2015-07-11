package view;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

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

import model.Photo;
import util.BackButtonListener;
import util.ExitProcedure;
import util.LogoutButtonActionListener;
import util.ParseDate;
import util.TagToken;
import control.Control;

public class SearchScreen extends JFrame {

	private static final long serialVersionUID = 1L;
	public static HashMap<String, Photo> potentialAlbum = new HashMap<String, Photo>();
	private JPanel contentPane;
	private JScrollPane scrollPane2;
	private Control control;
	private LogoutButtonActionListener logoutButtonActionListener;
	private JTextField searchStringText;
	private JPanel albumCommandPanel;
	private JButton searchDate;
	private static JButton searchTag;
	private static JButton newAlbum;
	private JPanel globalCommandPanel;
	private JButton logoutButton;
	private JPanel photoSearchPanel;
	private static JTextArea searchCommandInfo;
	private JButton searchTags;
	private JLabel startDateLabel;
	private JTextField startDateStringText;
	private JLabel endDateLabel;
	private JTextField endDateStringText;
	private JButton searchDates;
	private JTextField albumNameTextField;
	private final static String frameName = "SearchScreen";

	public SearchScreen() {
		
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
		albumCommandPanel.setBorder(new TitledBorder(null, "Search Commands", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		albumCommandPanel.setBounds(20, 55, 200, 381);
		contentPane.add(albumCommandPanel);
		albumCommandPanel.setLayout(null);
		
		

		
		searchCommandInfo = new JTextArea();
		searchCommandInfo.setBounds(16, 62, 165, 38);
		albumCommandPanel.add(searchCommandInfo);
		searchCommandInfo.setColumns(10);
		searchCommandInfo.setBackground(UIManager.getColor("Button.background"));
		searchCommandInfo.setEditable(false);
		searchCommandInfo.setLineWrap(true);
		
		searchDate = new JButton("Search by Date");
		searchDate.setBounds(16, 120, 165, 40);
		searchDate.setEnabled(true);
		searchDate.addActionListener(new ActionListener(){

			
			public void actionPerformed(ActionEvent e) {
				toggleSearchByDate();
				searchDate.setEnabled(false);
				searchTag.setEnabled(false);
			}
			
			
		});
		
		
		albumCommandPanel.add(searchDate);
		

		
		searchTag = new JButton("Search by Tag(s)");
		searchTag.setEnabled(true);
		searchTag.setBounds(16, 172, 165, 40);
		searchTag.addActionListener(new ActionListener(){

			
			public void actionPerformed(ActionEvent e) {
				toggleSearchByTag();
				searchTag.setEnabled(false);
				searchDate.setEnabled(false);
			}
			
			
			
		});
		albumCommandPanel.add(searchTag);
		
		
		newAlbum = new JButton("Create Album");
		newAlbum.setEnabled(false);
		newAlbum.setBounds(16, 224, 165, 40);
		newAlbum.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String temp = albumNameTextField.getText();
				
				if(temp.length() > 0){
				if(control.createAlbum(temp)){
					
					List<String> photos = new ArrayList<String>(potentialAlbum.keySet());
					
					
					for(String photo : photos){
						
						control.addPhoto(potentialAlbum.get(photo).getFilePath(), temp);
						
					}
					
					searchCommandInfo.setText("*New Album Created*\n" + temp);
					
				}else{
					
					searchCommandInfo.setText("ERROR:\n*Could not create album*");
					
				}
				
				albumNameTextField.setText("");
				
				
			}else{
				
				searchCommandInfo.setText("ERROR:\n*Could not create album*");
				
			}
			}
			
			
			
		});
		albumCommandPanel.add(newAlbum);
		
		albumNameTextField = new JTextField();
		albumNameTextField.setBounds(16, 269, 165, 36);
		albumCommandPanel.add(albumNameTextField);
		albumNameTextField.setColumns(10);
		albumNameTextField.setText("");
		albumNameTextField.setEnabled(false);
		albumNameTextField.setEditable(false);
		albumNameTextField.addMouseListener(new MouseAdapter() {
			
		    public void mouseClicked(MouseEvent e) {
		
		    	if(albumNameTextField.getText().equalsIgnoreCase("<new album name>"))
		    		albumNameTextField.setText("");
		    }
		});
		
		
		
		globalCommandPanel = new JPanel();
		globalCommandPanel.setBorder(new TitledBorder(null, "Global Commands", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		globalCommandPanel.setBounds(20, 448, 200, 313);
		contentPane.add(globalCommandPanel);
		globalCommandPanel.setLayout(null);
		
		logoutButton = new JButton("Logout");
		logoutButton.setBounds(16, 82, 165, 40);
		globalCommandPanel.add(logoutButton);
		
		photoSearchPanel = new JPanel();
		photoSearchPanel.setSize(774, 706);
		photoSearchPanel.setBounds(269, 91, 774, 670);
		
		
		startDateLabel = new JLabel("Start");
		startDateLabel.setBounds(239, 60, 60 , 36);
		startDateLabel.setEnabled(false);
		startDateLabel.setVisible(false);
		startDateStringText = new JTextField();
		startDateStringText.setBounds(269, 60, 270, 36);
		startDateStringText.setVisible(false);
		startDateStringText.setText("<MM/DD/YYYY-HH:MM:SS>");
		startDateStringText.setEnabled(false);
		startDateStringText.setEditable(false);
		startDateStringText.addMouseListener(new MouseAdapter() {
			
		    public void mouseClicked(MouseEvent e) {
		
		    	if(startDateStringText.getText().equalsIgnoreCase("<MM/DD/YYYY-HH:MM:SS>"))
		    		startDateStringText.setText("");
		    }
		});
		
		endDateLabel = new JLabel("End");
		endDateLabel.setBounds(540, 60, 60, 36);
		endDateLabel.setEnabled(false);
		endDateLabel.setVisible(false);
		endDateStringText = new JTextField();
		endDateStringText.setText("<MM/DD/YYYY-HH:MM:SS>");
		endDateStringText.setBounds(567, 60, 270, 36);
		endDateStringText.setVisible(false);
		endDateStringText.setEnabled(false);
		endDateStringText.setEditable(false);
		endDateStringText.addMouseListener(new MouseAdapter() {
			
		    public void mouseClicked(MouseEvent e) {
		    
		    	
		    	if(endDateStringText.getText().equalsIgnoreCase("<MM/DD/YYYY-HH:MM:SS>"))
		    		endDateStringText.setText("");
		    }
		});
		searchStringText = new JTextField();
		searchStringText.setBounds(233, 60, 604, 36);
		
		contentPane.add(startDateLabel);
		contentPane.add(startDateStringText);
		contentPane.add(endDateLabel);
		contentPane.add(endDateStringText);

		searchStringText.setText("<tagType:tagName tagType:tagName tagType:tagName>");
		searchStringText.setEnabled(false);
		searchStringText.setVisible(false);
		contentPane.add(searchStringText);
		
		searchDates = new JButton("Search");
		searchDates.setBounds(837, 61, 165, 35);
		searchDates.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String input1 = startDateStringText.getText();
				String input2 = endDateStringText.getText();
				System.out.println(input1);
				System.out.println(input2);
				Calendar start = ParseDate.strToDate(input1);
  				Calendar end = ParseDate.strToDate(input2);
  				 //   <YY/MM/DD-HH:MM:SS>
  				 
  				if(start != null && end != null){
  					control.getPhotosByDate(start, end, photoSearchPanel);
  					searchCommandInfo.setText("*Seach Results*\n" + "*" + potentialAlbum.size() + " matches*");
  				}else{
  					searchCommandInfo.setText("ERROR:\n*Incorrect input format*");
  				}
				toggleSearchByDate();
				searchDate.setEnabled(true);
				searchTag.setEnabled(true);
				
				if(potentialAlbum.size() > 0){
					newAlbum.setEnabled(true);
					toggleActiveAlbumText();
				}else{
					newAlbum.setEnabled(false);
					turnOffActiveAlbum();
				}
			}
			
			
			
		});
		searchDates.setEnabled(false);
		searchDates.setVisible(false);
		contentPane.add(searchDates);
		
		searchTags = new JButton("Search");
		searchTags.setBounds(837, 61, 165, 35);
		searchTags.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String temp = searchStringText.getText();
				String[] rawTags = temp.split(" ");
				
				
				ArrayList<TagToken> tags = new ArrayList<TagToken>();
				
				for(int i = 0; i < rawTags.length;i++){
					
					if(rawTags[i].contains(":")){
						
						String[] temp1 = rawTags[i].split(":");
						tags.add(new TagToken(temp1[0], temp1[1]));
					}else{
						tags.add(new TagToken(rawTags[i]));
					}
					
				}
				
				
				control.getPhotosByTag(tags, photoSearchPanel);
				
				searchCommandInfo.setText("*Seach Results*\n" + "*" + potentialAlbum.size() + " matches*");
				
				toggleSearchByTag();
				searchTag.setEnabled(true);
				searchDate.setEnabled(true);
				if(potentialAlbum.size() > 0){
					newAlbum.setEnabled(true);
					toggleActiveAlbumText();
				}else{
					newAlbum.setEnabled(false);
					turnOffActiveAlbum();
				}
			}
			
			
			
		});
		searchTags.setEnabled(false);
		searchTags.setVisible(false);
		searchStringText.addMouseListener(new MouseAdapter() {
			
		    public void mouseClicked(MouseEvent e) {
		    	
		    	
		    	if(searchStringText.getText().equalsIgnoreCase("<tagType:tagName tagType:tagName tagType:tagName>"))
		    		searchStringText.setText("");
		    }
		});
		contentPane.add(searchTags);
		
		//GridBagConstraints grid = new GridBagConstraints();
		GridBagLayout gridLayout = new GridBagLayout();

		
		gridLayout.layoutContainer(photoSearchPanel);

		
		
		photoSearchPanel.setLayout(gridLayout);
		
		contentPane.add(photoSearchPanel);
		scrollPane2 = new JScrollPane(photoSearchPanel);
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


		JButton backButton = new JButton("Back");
		backButton.addActionListener(new BackButtonListener(this));
		backButton.setEnabled(true);

		backButton.setBounds(890, 6, 117, 37);
		contentPane.add(backButton);
		JLabel titleLabel = new JLabel("Search for Photos");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		titleLabel.setBounds(0, 6, 1024, 37);
		contentPane.add(titleLabel);

	}
	
	public static JButton getDelete(){
		
		return searchTag;
		
	}
	
	public static JButton getEdit(){
		
		return newAlbum;
	}

	public static String getFrameName() {
		
		return frameName;
		
	}
	
	public String toString(){
		
		return frameName;
		
	}
	
	public static SearchScreen factory(){
		
		return new SearchScreen();
		
	}
	
	public static void setMessage(String message){
		
		searchCommandInfo.setText(message);
	}
	
	public void toggleSearchByDate(){
		
		if(!startDateLabel.isEnabled()){
			
			startDateLabel.setEnabled(true);
			startDateLabel.setVisible(true);
			startDateStringText.setEnabled(true);
			startDateStringText.setVisible(true);
			startDateStringText.setEditable(true);
			startDateStringText.setText("<MM/DD/YYYY-HH:MM:SS>");
			endDateLabel.setEnabled(true);
			endDateLabel.setVisible(true);
			endDateStringText.setEnabled(true);
			endDateStringText.setVisible(true);
			endDateStringText.setEditable(true);
			endDateStringText.setText("<MM/DD/YYYY-HH:MM:SS>");
			searchDates.setEnabled(true);
			searchDates.setVisible(true);
			
			
			
		}else{
			
			startDateLabel.setEnabled(false);
			startDateLabel.setVisible(false);
			startDateStringText.setEnabled(false);
			startDateStringText.setEditable(false);
			startDateStringText.setVisible(false);
			startDateStringText.setText("");
			endDateLabel.setEnabled(false);
			endDateLabel.setVisible(false);
			endDateStringText.setEnabled(false);
			endDateStringText.setVisible(false);
			endDateStringText.setEditable(false);
			endDateStringText.setText("");
			searchDates.setEnabled(false);
			searchDates.setVisible(false);
			
		}
		
	}
	
	public void toggleSearchByTag(){
		
		
		if(!searchTags.isEnabled()){
			
			searchTags.setVisible(true);
			searchTags.setEnabled(true);
			searchStringText.setVisible(true);
			searchStringText.setEditable(true);
			searchStringText.setEnabled(true);
			searchStringText.setText("<tagType:tagName tagType:tagName tagType:tagName>");
			
		}else{
			
			searchTags.setVisible(false);
			searchTags.setEnabled(false);
			searchStringText.setVisible(false);
			searchStringText.setEditable(false);
			searchStringText.setEnabled(false);
		
			
		}
	}
	
	public void toggleActiveAlbumText(){
		
			albumNameTextField.setEditable(true);
			albumNameTextField.setEnabled(true);
			albumNameTextField.setText("<new album name>");

	}
	
	public void turnOffActiveAlbum(){
		
		albumNameTextField.setEditable(false);
		albumNameTextField.setEnabled(false);
		albumNameTextField.setText("");
		
	}
	
}
