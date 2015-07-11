package util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JTextArea;
import model.Thumbnail;
import view.UserHome;
import control.Control;

public class KeyListenerAlbum implements KeyListener{

	private JTextArea textArea;
	private Control control;
	private String albumName;
	private Thumbnail thumbnail;
	
	
	public KeyListenerAlbum(JTextArea textArea, String albumName, Thumbnail thumbnail){
		
		this.textArea = textArea;
		this.control = new Control();
		this.albumName = albumName;
		this.thumbnail = thumbnail;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	public void keyPressed(KeyEvent e) {

			if((e.getKeyCode() == KeyEvent.VK_ENTER) && textArea.isEditable()){
				
				String temp = textArea.getText();
				
				if(temp.length() > 0 && !temp.equals("<rename>: " + thumbnail.getThumbNailName())){
					
					if(control.currentUser().renameAlbum(albumName, temp)){
						temp.replace("\n", "");
						textArea.setText(temp);
						thumbnail.setName(temp);
						ObjectHighlightListener.setCurrentAlbum(temp);
						UserHome.setMessage("*Album renamed*");
						
					}else{
					textArea.setText(albumName);
					UserHome.setMessage("ERROR:\n*Album already exists*");
					}
				}else{
					textArea.setText(albumName);
					UserHome.setMessage("ERROR:\n*Invalid name*");
				}
				
				textArea.setEditable(false);
				textArea.getCaret().setVisible(false);
				textArea.setEnabled(false);
				//textArea.setBackground(new Color(238,238,238,25));
				//System.out.println("test");
			}
		
	}
	


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}