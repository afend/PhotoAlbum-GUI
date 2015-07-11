package view;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Album;
import model.PhotoThumbNail;
import util.BackButtonListener;
import util.ExitProcedure;
import util.LogoutButtonActionListener;
import util.ObjectHighlightListener;
import util.TextInputListener;
import control.Control;

@SuppressWarnings("serial")
public class UserAlbum extends JFrame {

	private static boolean slideShow = false;
	private JPanel contentPane;
	private JScrollPane scrollPane2;
	private static JButton next;
	private static JButton prev;
	private static Control control;
	private LogoutButtonActionListener logoutButtonActionListener;
	private JTextField photoNameTextField;
	private JPanel photoCommandPanel;
	private static JButton addPhotoButton;
	private static JButton deletePhotoButton;
	private static JButton renamePhotoButton;
	private JPanel globalCommandPanel;
	private JButton logoutButton;
	private static JPanel displayPhotoPanel;
	private static JTextArea photoCommandInfo;
	private TextInputListener addPhotoTextInputListener;
	private static JButton slideShowPhotoButton;
	private static JButton addToAlbumPhotoButton;
	private static JButton delTagPhotoButton;
	private static JButton addTagPhotoButton;
	private singleListSelectionListener listListener;
	private static JList<Album> list;
	private static DefaultListModel<Album> listModel;
	private static JButton addConfirm;
	private static JButton addCancel;
	private static String toMove;
	private Album selected;
	private JScrollPane listScroller;
	private JLabel listLabel;
	public static HashMap<String,PhotoThumbNail> images = new HashMap<String,PhotoThumbNail>();
	private final static String frameName = "UserAlbum";

	public UserAlbum() {
		
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
		
		
		photoCommandPanel = new JPanel();
		photoCommandPanel.setBorder(new TitledBorder(null, "Photo Commands", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		photoCommandPanel.setBounds(20, 55, 200, 621);
		contentPane.add(photoCommandPanel);
		photoCommandPanel.setLayout(null);
		
		
		photoNameTextField = new JTextField();
		photoNameTextField.setBounds(16, 26, 165, 36);
		photoCommandPanel.add(photoNameTextField);
		photoNameTextField.setColumns(10);
		photoNameTextField.setText("<photo file path>");
		
		photoNameTextField.addMouseListener(new MouseAdapter() {
			
		    public void mouseClicked(MouseEvent e) {
		    	
		    	if(photoNameTextField.getText().equalsIgnoreCase("<photo file path>"))
		    		photoNameTextField.setText("");
		    }
		});
		
		photoCommandInfo = new JTextArea();
		photoCommandInfo.setBounds(16, 62, 165, 48);
		photoCommandPanel.add(photoCommandInfo);
		photoCommandInfo.setColumns(10);
		photoCommandInfo.setBackground(UIManager.getColor("Button.background"));
		photoCommandInfo.setEditable(false);
		photoCommandInfo.setLineWrap(true);
		
		addPhotoButton = new JButton("Add Photo");
		addPhotoButton.setBounds(16, 120, 165, 40);
		addPhotoButton.setEnabled(true);
		photoCommandPanel.add(addPhotoButton);
		

		
		deletePhotoButton = new JButton("Delete Photo");
		deletePhotoButton.setEnabled(false);
		deletePhotoButton.setBounds(16, 172, 165, 40);
		photoCommandPanel.add(deletePhotoButton);
		
		
		renamePhotoButton = new JButton("Recaption Photo");
		renamePhotoButton.setEnabled(false);
		renamePhotoButton.setBounds(16, 224, 165, 40);
		photoCommandPanel.add(renamePhotoButton);
		
		
		addTagPhotoButton = new JButton("Tag Photo");
		addTagPhotoButton.setEnabled(false);
		addTagPhotoButton.setBounds(16, 276, 165, 40);
		photoCommandPanel.add(addTagPhotoButton);
		
		delTagPhotoButton = new JButton("Delete Tag");
		delTagPhotoButton.setEnabled(false);
		delTagPhotoButton.setBounds(16, 328, 165, 40);
		photoCommandPanel.add(delTagPhotoButton);

		slideShowPhotoButton = new JButton("SlideShow");
		slideShowPhotoButton.setEnabled(false);
		slideShowPhotoButton.setBounds(16, 380, 165, 40);
		photoCommandPanel.add(slideShowPhotoButton);

		addToAlbumPhotoButton = new JButton("Move to Album");
		addToAlbumPhotoButton.setEnabled(false);
		addToAlbumPhotoButton.setBounds(16, 432, 165, 40);
		photoCommandPanel.add(addToAlbumPhotoButton);
		String title = control.currentUser().toString() + "'s albums";
		
		listLabel = new JLabel("<html><div style=\"text-align: center;\">" + title + "</html>", JLabel.CENTER);
		listLabel.setBounds(23, 469, 150, 20);
		photoCommandPanel.add(listLabel);
		
		listModel = new DefaultListModel<Album>();
		list = new JList<Album>(listModel);
		listListener = new singleListSelectionListener();
		list.addListSelectionListener(listListener);
		list.setBounds(20, 489, 156, 90);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setSelectedIndex(-1);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(1);
		list.setEnabled(false);
		listScroller = new JScrollPane(list);
		listScroller.setBounds(20, 489, 156, 90);
		listScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		listScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		//photoCommandPanel.add(list);
		photoCommandPanel.add(listScroller);

	

		
		addConfirm = new JButton("Move");
		addConfirm.setEnabled(false);
		addConfirm.setBounds(16, 589, 80, 25);
		photoCommandPanel.add(addConfirm);
		addConfirm.addActionListener(new addButtonListener());

		
		
		addCancel = new JButton("Cancel");
		addCancel.setEnabled(false);
		addCancel.setBounds(96, 589, 80, 25);
		photoCommandPanel.add(addCancel);
		addCancel.addActionListener(new cancelButtonListener());
		
		
		globalCommandPanel = new JPanel();
		globalCommandPanel.setBorder(new TitledBorder(null, "Global Commands", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		globalCommandPanel.setBounds(20, 688, 200, 83);
		contentPane.add(globalCommandPanel);
		globalCommandPanel.setLayout(null);
		
		
		logoutButton = new JButton("Logout");
		logoutButton.setBounds(16, 25, 165, 40);
		globalCommandPanel.add(logoutButton);
		
		displayPhotoPanel = new JPanel();
		displayPhotoPanel.setSize(774, 706);
		displayPhotoPanel.setBounds(233, 55, 774, 706);
		
		//GridBagConstraints grid = new GridBagConstraints();
		GridBagLayout gridLayout = new GridBagLayout();

		
		gridLayout.layoutContainer(displayPhotoPanel);

		
		
		displayPhotoPanel.setLayout(gridLayout);
		
		//load photos
		control.listAllPhotos(control.getCurrentAlbum(), displayPhotoPanel);
		contentPane.add(displayPhotoPanel);
		scrollPane2 = new JScrollPane(displayPhotoPanel);
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

		addPhotoTextInputListener = new TextInputListener(addPhotoButton, photoNameTextField, photoCommandInfo, displayPhotoPanel, 4);
		photoNameTextField.getDocument().addDocumentListener(addPhotoTextInputListener);
		photoNameTextField.addActionListener(addPhotoTextInputListener);
		addPhotoButton.addActionListener(addPhotoTextInputListener);

		JButton backButton = new JButton("Back");
		backButton.setEnabled(true);
		backButton.addActionListener(new BackButtonListener(this));

		backButton.setBounds(890, 6, 117, 37);
		contentPane.add(backButton);
		deletePhotoButton.addActionListener(new ObjectHighlightListener(displayPhotoPanel, 3));
		renamePhotoButton.addActionListener(new ObjectHighlightListener(displayPhotoPanel, 4));
		addTagPhotoButton.addActionListener(new ObjectHighlightListener(displayPhotoPanel, 5));
		delTagPhotoButton.addActionListener(new ObjectHighlightListener(displayPhotoPanel, 6));
		addToAlbumPhotoButton.addActionListener(new ObjectHighlightListener(displayPhotoPanel, 7));
		slideShowPhotoButton.addActionListener(new ObjectHighlightListener(displayPhotoPanel, 8));
		JLabel titleLabel = new JLabel(control.getCurrentAlbum());
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		titleLabel.setBounds(0, 6, 1024, 37);
		contentPane.add(titleLabel);
		
		ArrayList<Album> albums = new ArrayList<Album>();
		albums = control.currentUser().listAlbums();
		slideShowPhotoButton.setEnabled(true);
		if(albums.size() > 0){
			
			addToAlbumPhotoButton.setEnabled(true);
			
			for(Album album : albums){
				
				if(!album.toString().equals(control.getCurrentAlbum()))
					listModel.addElement(album);
			}
			
		}

	}
	
	public static JButton getDelete(){
		
		return deletePhotoButton;
		
	}
	
	public static JButton getEdit(){
		
		return renamePhotoButton;
	}

	public static String getFrameName() {
		
		return frameName;
		
	}
	
	public static JButton getTagButton(){
		
		return addTagPhotoButton;
		
	}
	
	public static JButton getSlideShow(){
		
		return slideShowPhotoButton;
		
	}
	
	public static JButton getMoveButton(){
		
		return addToAlbumPhotoButton;
	}
	
	public static JButton getDelButton(){
		
		return delTagPhotoButton;
	}
	
	public String toString(){
		
		return frameName;
		
	}
	
	public static UserAlbum factory(){
		
		return new UserAlbum();
		
	}
	
	public static void setMessage(String message){
		
		photoCommandInfo.setText(message);
	}
	
	public static void enableList(){
		
		if(!list.isEnabled()){
			list.setEnabled(true);
			addConfirm.setEnabled(true);
			addCancel.setEnabled(true);
			addToAlbumPhotoButton.setEnabled(false);
		}else{
			list.setEnabled(false);
			addConfirm.setEnabled(false);
			addCancel.setEnabled(false);
			addToAlbumPhotoButton.setEnabled(false);
			
		}
	}
	
	public static void setToMove(String photoToMove){
		
		toMove = photoToMove;
	}
	
	class addButtonListener implements ActionListener{

		
		public void actionPerformed(ActionEvent e) {
			
			if(control.movePhoto(toMove, control.getCurrentAlbum(), selected.toString())){
				
				photoCommandInfo.setText("*SUCCESS*\n*" + toMove + " *is now in*\n" + selected.toString());
				toMove = "";
				displayPhotoPanel.removeAll();
				displayPhotoPanel.invalidate();
				control.listAllPhotos(control.getCurrentAlbum(), displayPhotoPanel);
				displayPhotoPanel.revalidate();
				displayPhotoPanel.repaint();
				enableList();
				
			}else{
				
				photoCommandInfo.setText("ERROR:\n*Invalid Selection*");
				toMove = "";
			}
			
		}
		
		
	}
	
	class cancelButtonListener implements ActionListener{

		
		public void actionPerformed(ActionEvent e) {
			
			enableList();
			toMove = "";
		}
		
		
	}
	
	class singleListSelectionListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent listSelectionEvent) {
			
			/*
			if(listModel.size() > 0){
				
				addToAlbumPhotoButton.setEnabled(true);
				
			}
			*/
			
			if (!listSelectionEvent.getValueIsAdjusting()) {
				int i = list.getSelectedIndex();
				int size = listModel.getSize();
				if (i >= 0){
					
					displayAlbum(listModel.elementAt(list.getSelectedIndex()).toString());
					//textField_2.setText(listModel.elementAt(list.getSelectedIndex()).toString());
					
					}
				else if (size == 0){
					
					//emptyDisplay();
					//textField_2.setText("");
				}
			}


		}
	}
	

	
	public void displayAlbum(String albumName){
		
		selected = control.currentUser().hashAlbum(albumName);
		
		//userDetailTextArea.setText("User Name: " + user.getUser() + "\n\nAlbum Count: " + user.getAlbumCount() + "\n\nPhoto Count: " + user.getPhotoCount());
		
	}
	
	public static void displayPhoto(JLabel image, String photoName){
		
		slideShowPhotoButton.setEnabled(false);
		GridBagConstraints insert = new GridBagConstraints();
		
		insert.gridy = 0;
		
		insert.weighty = 1;
		
		displayPhotoPanel.removeAll();
		displayPhotoPanel.invalidate();
		if(slideShow){
			
			insert.weightx = .33;
			next = new JButton("<<");
			next.setSize(80, 40);
			next.setEnabled(true);
			next.addActionListener(new ActionListener(){

				
				public void actionPerformed(ActionEvent e) {
					ObjectHighlightListener.next();
					
				}
				
				
			});


			prev = new JButton(">>");
			prev.setSize(80, 40);
			prev.addActionListener(new ActionListener(){

				
				public void actionPerformed(ActionEvent e) {
					ObjectHighlightListener.prev();					
				}
				
				
				
			});
			
			insert.anchor = GridBagConstraints.NORTHWEST;
			displayPhotoPanel.add(next, insert);
			insert.gridx = 1;

			insert.anchor = GridBagConstraints.NORTHEAST;
			displayPhotoPanel.add(prev, insert);
			insert.gridy++;
			BackButtonListener.flag2=true;
		}else{
			
			BackButtonListener.toggleFlag();
		}
		insert.gridx = 0;
		insert.anchor = GridBagConstraints.CENTER;
		displayPhotoPanel.add(image, insert);
		//control.listPhotoInfo(photoName)
		JTextArea photoInfo = new JTextArea(control.listPhotoInfo(photoName));
		photoInfo.setSize(500, 120);
		photoInfo.setEnabled(false);
		photoInfo.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		photoInfo.setBackground(UIManager.getColor("Button.background"));
		insert.anchor = GridBagConstraints.NORTHWEST;
		insert.gridy++;
		displayPhotoPanel.add(photoInfo, insert);
		displayPhotoPanel.revalidate();
		displayPhotoPanel.repaint();
		deletePhotoButton.setEnabled(false);
		renamePhotoButton.setEnabled(false);
		addPhotoButton.setEnabled(false);
		delTagPhotoButton.setEnabled(false);
		addTagPhotoButton.setEnabled(false);
		addToAlbumPhotoButton.setEnabled(false);

		/*
		if(slideShow){
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				
			}
		}
		*/
	}
	
	public static void loadPhotos(){
		
		displayPhotoPanel.removeAll();
		displayPhotoPanel.invalidate();
		control.listAllPhotos(control.getCurrentAlbum(), displayPhotoPanel);
		displayPhotoPanel.revalidate();
		displayPhotoPanel.repaint();
		
	}
	
	public static void disableCommands(){
		
		deletePhotoButton.setEnabled(false);
		renamePhotoButton.setEnabled(false);
		addPhotoButton.setEnabled(false);
		delTagPhotoButton.setEnabled(false);
		addTagPhotoButton.setEnabled(false);
		addToAlbumPhotoButton.setEnabled(false);
	}
	
	public static void addToImageHash(PhotoThumbNail thumb, String caption){
		
		images.put(caption, thumb);
	}
	
	public static void delImageHash(String caption){
		
		images.remove(caption);
	}
	
	public static void toggleSlideShow(){
		
		if(!slideShow){
			slideShow = true;
		}else{
			slideShow = false;
		}
	}
	

}

	



