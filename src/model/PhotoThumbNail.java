package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import util.KeyListenerPhoto;
import view.UserAlbum;

@SuppressWarnings("serial")
public class PhotoThumbNail extends JPanel{
	
	private static Color idle = new Color(238,238,238,25);
	private static Color highlight = new Color(0,0,90,25);
	private String thumbNailName;
	private String photoThumb;
	private JLabel imageShrink;
	private JLabel iconName;
	private JLabel output;
	private JTextArea name;
	private Graphics current;
	private ImageIcon icon;
	private ImageIcon full;
	private JLabel fullImage;

	private KeyListenerPhoto keyListenerPhoto;
	
	public PhotoThumbNail(String thumbNailName, String filePath){
			
			Image img = null; 
			
	        try {
	        	img = ImageIO.read(new File(filePath));
	
	            
	            double scale;
	            

	            if(img.getHeight(null) > img.getWidth(null)){
	            	
	            	if(img.getHeight(null) > 1800){
	            		
	            		scale = getSizeCoEfFull(img.getHeight(null));
	            		
	            	}else if(img.getHeight(null) > 900){
	            		
	            		scale = .5;
	            		
	            	}else{
	            		
	            		scale = 1.0;
	            	}
	            		
	            	
	            }else{
	            	
	            	if(img.getWidth(null) > 1800){
	            		
	            		scale = getSizeCoEfFull(img.getWidth(null));
	            		
	            	}else if(img.getWidth(null) > 900){
	            		
	            		scale = .5;
	            		
	            	}else{
	            		
	            		scale = 1;
	            	}
	            	
	            }
	            
	            int width =  (int) ((double) img.getWidth(null) * scale);
	            int height = (int) ((double) img.getHeight(null) * scale);
	            
	            full = new ImageIcon(img.getScaledInstance(width, height, 0));
	            
	            
	            
	            
	            
	            
	            if(img.getHeight(null) > img.getWidth(null)){
	            	
	            	if(img.getHeight(null) > 400){
	            		
	            		scale = getSizeCoEf(img.getHeight(null));
	            		
	            	}else{
	            		
	            		scale = .5;
	            		
	            	}
	            		
	            	
	            }else{
	            	
	            	if(img.getWidth(null) > 400){
	            		
	            		scale = getSizeCoEf(img.getWidth(null));
	            		
	            	}else{
	            		
	            		scale = .5;
	            		
	            	}
	            	
	            }
	            
	            width =  (int) ((double) img.getWidth(null) * scale);
	            height = (int) ((double) img.getHeight(null) * scale);
	            
	            icon = new ImageIcon(img.getScaledInstance(width, height, 0));
	
	            //this.thumbnail = new ImageIcon(img.getScaledInstance(width, height, 0));
	        } catch (Exception e) {
	        	
	       
	        }
	        	this.fullImage = new JLabel(full);
				this.photoThumb = new String(thumbNailName);
				this.thumbNailName = thumbNailName;
				this.iconName = new JLabel(this.thumbNailName);
				this.name = new JTextArea(this.thumbNailName);
				this.name.setFont(new Font("Lucida Grande", Font.BOLD, 18));
				this.name.setBackground(idle);
				this.name.setSize(100, 20);
				this.name.setEditable(false);
				this.name.setEnabled(false);
				this.keyListenerPhoto = new KeyListenerPhoto(this.name, photoThumb, this);
				this.name.addKeyListener(keyListenerPhoto);
				
				this.imageShrink = new JLabel(icon);
				this.output = new JLabel(this.thumbNailName, icon, JLabel.CENTER);
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
				//System.out.println("New ThumbNail: " + this.thumbNailName);
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
		return iconName.getText();
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
			UserAlbum.setMessage("Warning:\nCaptions are unique\nper user for\nall albums\n\nDefault caption\nis the file name.");
		}	
		
		
	}
	
	public void addTag(){
		
		if(!this.name.isEditable()){
			this.name.setEnabled(true);
			this.name.setEditable(true);
			this.name.requestFocusInWindow();
			this.name.getCaret().setVisible(true);
			this.name.setText("<input: tagType:TagName>");
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
	
	public JLabel getImage(){
		
		return this.fullImage;
		
	}
	
	public double getSizeCoEf(int side){
		
		return (1.0 - ( ( (double) side - 200 ) / (double) side ));
	}
	
	public double getSizeCoEfFull(int side){
		
		return (1.0 - ( ( (double) side - 900 ) / (double) side ));
	}
}
