package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.UIManager;

import control.Control;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import util.ExitButtonListener;
import util.ExitProcedure;
import util.TextInputListener;

public class MainLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final String frameName = "MainLogin";
	private JPanel contentPane;
	private JTextField loginTextField;
	private JTextArea loginDialogue;
	private ExitButtonListener logoutButtonActionListener;
	private TextInputListener textInput;
	private Control control;

	/**
	 * Contains primary login information
	 */
	public MainLogin() {
		
		control = new Control();
		
		setTitle("PhotoAlbum30");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 800);
		setResizable(true);
		setLocationRelativeTo(null);
		
		

		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		
		JLabel titleLabel = new JLabel("Photo Album 30");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		titleLabel.setBounds(6, 158, 1012, 82);
		contentPane.add(titleLabel);
		
		JLabel loginLabel = new JLabel("Login:");
		loginLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		loginLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		loginLabel.setBounds(245, 292, 131, 40);
		contentPane.add(loginLabel);
		
		/*
		JLabel pwLabel = new JLabel("Password:");
		pwLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		pwLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		pwLabel.setBounds(245, 365, 131, 40);
		contentPane.add(pwLabel);
		*/
		loginTextField = new JTextField();
		loginTextField.setHorizontalAlignment(SwingConstants.LEFT);
		loginTextField.setBounds(388, 292, 270, 40);
		contentPane.add(loginTextField);
		loginTextField.setColumns(10);
		
		
		loginDialogue = new JTextArea();
		loginDialogue.setBounds(388, 272, 270, 20);	
		contentPane.add(loginDialogue);
		loginDialogue.setColumns(10);
		loginDialogue.setBackground(UIManager.getColor("Button.background"));
		loginDialogue.setEditable(false);
		/*
		pwTextField = new JTextField();
		pwTextField.setBounds(388, 365, 270, 40);
		contentPane.add(pwTextField);
		pwTextField.setColumns(10);
		*/
		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		loginButton.setBounds(388, 437, 131, 40);
		loginButton.setEnabled(false);
		contentPane.add(loginButton);
		
		JButton exitButton = new JButton("Exit");

		exitButton.setBounds(527, 437, 131, 40);
		contentPane.add(exitButton);
		
		textInput = new TextInputListener(this, loginButton, loginTextField, loginDialogue, 1);
		loginButton.addActionListener(textInput);
		loginTextField.addActionListener(textInput);
		loginTextField.getDocument().addDocumentListener(textInput);
		
		logoutButtonActionListener = new ExitButtonListener(this);
		exitButton.addActionListener(logoutButtonActionListener);
		
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				control.logout();
				ExitProcedure.exitProcedure();
			}
		});
		
		/*
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		*/
		
		contentPane.setLayout(null);
	}

	public static String getFrameName() {
		return frameName;
	}
	
	public static MainLogin factory(){
		
		return new MainLogin();
		
	}
	
	public String toString(){
		
		return frameName;
		
	}
	
	
}
