package view;

import java.io.IOException;
import java.util.HashMap;
import javax.swing.JFrame;
import control.Control;

public class CmdView {
	
	static HashMap<String, JFrame> GUI = new HashMap<String, JFrame>();
	
	public static JFrame getScreen(String screenName){
		
		return GUI.get(screenName);
		
	}
	
	public static void addScreen(String frameName, JFrame window){
		
		GUI.put(frameName, window);
		window.setVisible(true);
		
	}
	
	public static void removeScreen(String frameName){
		
		
		GUI.get(frameName).dispose();
		GUI.remove(frameName);
	}



	public static void interactiveMode(Control control) throws IOException{
		 

		
		MainLogin mainLogin = new MainLogin();

		GUI.put(MainLogin.getFrameName(), mainLogin);

		mainLogin.setVisible(true);

	}

	public static void main(String[] args){
		
		Control control = new Control();
		
		control.load();
		try {
			interactiveMode(control);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	
	}
	
	
}
	

	
