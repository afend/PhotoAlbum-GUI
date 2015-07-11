package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import util.KeyListenerAlbum;

@SuppressWarnings("serial")
public class Thumbnail extends JPanel{

	//private static final long serialVersionUID = 1L;
	private static Color idle = new Color(238,238,238,25);
	private static Color highlight = new Color(0,0,90,25);
	private String thumbNailName;
	private JLabel imageShrink;
	private JLabel iconName;
	private JLabel output;
	private JTextArea name;
	private Graphics current;
	private ImageIcon icon;
	private KeyListenerAlbum keyListener;

	public Thumbnail(String thumbNailName){
		
		this.thumbNailName = thumbNailName;
		this.setThumbnailName(this.thumbNailName);
		iconName = new JLabel(this.thumbNailName);
		this.name = new JTextArea(this.thumbNailName);
		this.name.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		this.name.setBackground(idle);
		this.name.setSize(100, 20);
		this.name.setEditable(false);
		this.name.setEnabled(false);
		keyListener = new KeyListenerAlbum(this.name, this.thumbNailName, this);
		this.name.addKeyListener(keyListener);
		icon = new ImageIcon("data" + File.separator + "albumIcon.png");

		imageShrink = new JLabel(icon);
		output = new JLabel(this.thumbNailName, icon, JLabel.CENTER);
		this.setBounds(0, 0, output.getWidth() + iconName.getWidth(), output.getHeight() + iconName.getHeight());
		this.setLayout(new GridBagLayout());
		GridBagConstraints grid = new GridBagConstraints();
		grid.anchor = GridBagConstraints.CENTER;
		grid.weightx = 1;
		grid.weighty = 0.9;
		grid.gridx = 0;
		grid.gridy = 0;
		grid.insets.bottom = 15;
		grid.insets.top = 15;
		grid.insets.left = 15;
		grid.insets.right = 15;
		this.add(imageShrink, grid);
		grid.weighty = 0.1;
		grid.gridy = 1;
		this.add(name, grid);

	}
	
	
	
	public void select(Graphics g){
		
		super.paint(g);
		g.setColor(highlight);
		g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 20, 20);
		this.current = g;
		
	}
	
	public void updateName(String name){
		
		this.thumbNailName = name;
		
	}
	
	public void idle(Graphics g){
		
		super.paint(g);
		g.setColor(idle);
		g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 20, 20);
		this.current = g;
		
	}
	
	public void destroy(){
		
		this.current.clearRect(0, 0, this.getWidth(), this.getHeight());
		
	}

	public String getThumbNailName() {
		return this.thumbNailName;
	}

	public void setThumbnailName(String thumbNailName) {
		
		this.thumbNailName = thumbNailName;
		
	}
	
	public void toggleThumbnailName(){
		
		if(!this.name.isEditable()){
			this.name.setEnabled(true);
			this.name.setEditable(true);
			this.name.requestFocusInWindow();
			this.name.getCaret().setVisible(true);
			this.name.setText("<rename>: " + this.getThumbNailName());
			this.name.selectAll();
			//this.name.setForeground(highlight);
		}
		else{
			String temp = this.name.getText();
			
			if(!temp.equals(thumbNailName)){
				this.name.setText(thumbNailName);
			}
			this.name.setEditable(false);
			this.name.setEnabled(false);
		}
	}
	
	public void addName(){
		
		if(!this.name.isEditable()){
			this.name.setEnabled(true);
			this.name.setEditable(true);
			this.name.requestFocusInWindow();
			this.name.getCaret().setVisible(true);
			this.name.setText("<input caption>");
			this.name.selectAll();
			//this.name.setForeground(highlight);
		}
		else{
			String temp = this.name.getText();
			
			if(!temp.equals(thumbNailName)){
				this.name.setText(thumbNailName);
			}
			this.name.setEditable(false);
			this.name.setEnabled(false);
		}	
		
		
	}
	
	public boolean isEditable(){
		
		return this.name.isEditable();
	}
	
	public double getSizeCoEf(int side){
		
		return (1.0 - ( ( (double) side - 200.0 ) / (double) side ));
	}
	
	
}



