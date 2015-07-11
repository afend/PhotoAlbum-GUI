package util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JTextArea;
import model.PhotoThumbNail;
import view.UserAlbum;
import control.Control;

public class KeyListenerPhoto implements KeyListener{

	private JTextArea textArea;
	private Control control;
	private String caption;
	private PhotoThumbNail thumbnail;
	private String temp;
	private String original;
	private static Boolean tagFlag = false;
	private static Boolean delTag = false;
	
	
	public KeyListenerPhoto(JTextArea textArea, String caption, PhotoThumbNail thumbnail){
		
		this.textArea = textArea;
		this.control = new Control();
		this.caption = caption;
		this.thumbnail = thumbnail;
		this.original = textArea.getText();
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	public void keyPressed(KeyEvent e) {

		this.temp = textArea.getText();
		
		
			if((e.getKeyCode() == KeyEvent.VK_ENTER) && textArea.isEditable()){
				if(tagFlag){
				if(!temp.equals("<input: tagType:TagName>")){
					
					this.temp.replace("\n", "");
					String[] tags = temp.split(":");
					
					if(tags.length == 2){
						
						if(control.addTagToPhoto(original, tags[0], tags[1])){
							
							UserAlbum.setMessage("*Tag Added!*");
							this.textArea.setText(caption);
							toggleTagFlag();
						}else{
						
						UserAlbum.setMessage("ERROR:\n*Tag already exists*");
						toggleTagFlag();
						}
						
					}else{
						
						this.textArea.setText(caption);
						UserAlbum.setMessage("ERROR:\n*Invalid format*");
						toggleTagFlag();
						
					}


	   					
				}else{
					this.textArea.setText(caption);
					toggleTagFlag();
					
				}

				}else if(delTag){
					
					if(!temp.equals("<input: tagType:TagName>")){
						
						this.temp.replace("\n", "");
						String[] tags = temp.split(":");
						
						if(tags.length == 2){
							
							if(control.delTagPhoto(original, tags[0], tags[1])){
								
								UserAlbum.setMessage("*Tag Deleted!*");
								this.textArea.setText(caption);
								toggleDelFlag();
								
							}else{
							
							UserAlbum.setMessage("ERROR:\n*Tag doesn't exists*");
							toggleDelFlag();
							}
							
						}else{
							
							this.textArea.setText(caption);
							UserAlbum.setMessage("ERROR:\n*Invalid format*");
							toggleDelFlag();
							
						}
					
					
				}else{
					
					this.textArea.setText(caption);
					toggleDelFlag();
					
				}
				
				}else if(!temp.equals("<input caption>")){
				
					
					if(temp.length() > 0 &&!temp.equals("<rename>: " + original)  ){
						if(control.currentUser().changeCaption(original, temp, control.getCurrentAlbum())){
							
							this.temp.replace("\n", "");
							this.textArea.setText(temp);
							this.thumbnail.setName(temp);
							ObjectHighlightListener.setCurrentPhoto(temp);
							original = temp;
							UserAlbum.setMessage("*Photo Renamed*");
							
						}else{
							
						this.textArea.setText(caption);
						UserAlbum.setMessage("ERROR:\n*Caption already exists*");
						
						}
						
					}else{
						UserAlbum.setMessage("ERROR:\n*Enter a valid caption*");
						this.textArea.setText(caption);
						
					}
				}else{
					UserAlbum.setMessage("**DEFAULT CAPTION**\n*File name*");
					this.textArea.setText(caption);
					
					
				}
				
				this.textArea.setEditable(false);
				this.textArea.getCaret().setVisible(false);
				this.textArea.setEnabled(false);
				//textArea.setBackground(new Color(238,238,238,25));
				//System.out.println("test");
				}
		
	}
	

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public static void toggleTagFlag(){
		
		if(!tagFlag){
			tagFlag = true;
		}else{
			tagFlag = false;
		}
	}
	
	public static void toggleDelFlag(){
		
		if(!delTag){
			delTag = true;
		}else{
			delTag = false;
		}
	}
	
}
