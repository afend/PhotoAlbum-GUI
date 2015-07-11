package util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import view.UserAlbum;
import control.Control;

public class BackButtonListener implements ActionListener{
	
	private Control control = new Control();
	private JFrame frame;
	private static Boolean flag = false;
	public static Boolean flag2 = false;
	
	public BackButtonListener(JFrame frame){
		
		this.frame = frame;
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(Control.getPrevious().equals("AdminLogin")){
			
			control.switchScreens(frame.toString(), "MainLogin");
			
		}else if(flag2){
			
			UserAlbum.toggleSlideShow();
			UserAlbum.loadPhotos();
			toggleFlag2();
			
		
		}else if(flag){
			
			UserAlbum.loadPhotos();
			toggleFlag();
			
		}else{
			
			control.switchScreens(frame.toString(), Control.getPrevious());
			UserAlbum.images.clear();
			
		}
		
	}
	
	public static void toggleFlag(){
		
		if(!flag){
			
			flag = true;
			
		}else{
			
			flag = false;
			
		}
	}
	
	public static void toggleFlag2(){
		
		if(!flag2){
			flag2 = true;
			
		}else{
			
			flag2 = false;
		}
	}

}
