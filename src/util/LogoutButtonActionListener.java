package util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import view.UserAlbum;
import control.Control;

public class LogoutButtonActionListener implements ActionListener {
	
	private JFrame window;
	private Control control;
	
	public LogoutButtonActionListener(JFrame window) {
		this.window = window;
		this.control = new Control();
	}

	public void actionPerformed(ActionEvent e) {
		control.switchScreens(window.toString(), "MainLogin");
		UserAlbum.images.clear();
	}
	
}
