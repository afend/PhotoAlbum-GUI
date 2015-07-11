package util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import control.Control;

public class ExitButtonListener implements ActionListener{
	
	private Control control;
	private JFrame window;
	
	public ExitButtonListener(JFrame window){
		
		this.window = window;
		this.control = new Control();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		control.logout();
		window.dispose();
		System.exit(0);
		
	}
	
	
}
