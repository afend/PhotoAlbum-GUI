package util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import model.PhotoThumbNail;
import model.Thumbnail;
import view.UserAlbum;
import view.UserHome;
import control.Control;

public class ObjectHighlightListener implements MouseListener, ActionListener{
	
	private Thumbnail thumbnail;
	private PhotoThumbNail photoThumbNail;
	private int options;
	private JPanel panel;
	private Control control = new Control();
	private static Thumbnail currentAlbum;
	private static PhotoThumbNail currentPhoto;
	private static String selectedAlbum;
	private static String selectedPhoto;
	public static boolean stop;
	public static int iter;
	public static int count;
	public static List<String> images;
	
	/**
	 * Default constructor
	 * @param thumbnail
	 * @param options
	 */
	public ObjectHighlightListener(Thumbnail thumbnail, int options){
		
		this.thumbnail = thumbnail;
		this.options = options;
		
		//this.currColor = this.label.getBackground();
		
	}
	
	public ObjectHighlightListener(PhotoThumbNail photoThumb, int options){
		
		this.photoThumbNail = photoThumb;
		this.options = options;
		
	}
	
	public ObjectHighlightListener(JPanel panel, int options){
		
		this.options = options;
		this.panel = panel;
	}

	public void mouseClicked(MouseEvent e) {
		
		if(e.getClickCount() == 2 && (options == 1 || options == 2)){
			
			control.setCurrentAlbum(selectedAlbum);
			currentAlbum = null;
			control.switchScreens("UserHome", "UserAlbum");
		//	System.out.println("THIS WONT PRINT IN PHOTO");
			
        }else if(e.getClickCount() == 2 && options > 2){
			
        	UserAlbum.displayPhoto(currentPhoto.getImage(), selectedPhoto);
			currentPhoto = null;
			
		}
		
	}


	public void mousePressed(MouseEvent e) {
		
		if(options == 1 || options == 2){
			
		if(currentAlbum != null){
			
			if(currentAlbum.isEditable()){
				
				currentAlbum.toggleThumbnailName();
				
			}
			
		}
		
		//START ALBUM
		if(currentAlbum != null){
			
			//new selected object isn't old object.
			if(currentAlbum != this.thumbnail){
				
				currentAlbum.idle(currentAlbum.getGraphics());
				this.thumbnail.select(this.thumbnail.getGraphics());
				currentAlbum = this.thumbnail;
				selectedAlbum = currentAlbum.getThumbNailName();
				
			//	System.out.println("CURRET UPDATED: " + currentAlbum.getThumbNailName());

					UserHome.getEdit().setEnabled(true);
					UserHome.getDelete().setEnabled(true);

    			
			}
			
			}else{
				
				//first set
				
				this.thumbnail.select(this.thumbnail.getGraphics());
				currentAlbum = this.thumbnail;
				selectedAlbum = currentAlbum.getThumbNailName();
				//System.out.println("CURRET UPDATED: " + currentAlbum.getThumbNailName());
				
	
					UserHome.getEdit().setEnabled(true);
					UserHome.getDelete().setEnabled(true);

				
	    	}
		}else if(options == 3 || options == 4){ //START PHOTO
					
			
			if(currentPhoto != null){
				
			if(currentPhoto != this.photoThumbNail){
				
				currentPhoto.idle(currentPhoto.getGraphics());
				this.photoThumbNail.select(this.photoThumbNail.getGraphics());
				currentPhoto = this.photoThumbNail;
				selectedPhoto = currentPhoto.getThumbNailName();
				
				//System.out.println("CURRET UPDATED: " + currentPhoto.getThumbNailName());

					UserAlbum.getEdit().setEnabled(true);
					UserAlbum.getDelete().setEnabled(true);
					UserAlbum.getTagButton().setEnabled(true);
					UserAlbum.getDelButton().setEnabled(true);
					UserAlbum.getMoveButton().setEnabled(true);
					UserAlbum.getSlideShow().setEnabled(true);
					
				
    			
			}
			
		}else{
			
			//first set
			
			this.photoThumbNail.select(this.photoThumbNail.getGraphics());
			currentPhoto = this.photoThumbNail;
			selectedPhoto = currentPhoto.getThumbNailName();
			//System.out.println("CURRET UPDATED: " + currentPhoto.getThumbNailName());
			

				UserAlbum.getEdit().setEnabled(true);
				UserAlbum.getDelete().setEnabled(true);
				UserAlbum.getTagButton().setEnabled(true);
				UserAlbum.getDelButton().setEnabled(true);
				UserAlbum.getMoveButton().setEnabled(true);
				UserAlbum.getSlideShow().setEnabled(true);
			
    	}
			
			
		}
		
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	public void actionPerformed(ActionEvent e) {
		
		//delete album
		if(this.options == 1){
			
			//System.out.println("should not print");
			control.deleteAlbum(currentAlbum.getThumbNailName());
			this.panel.removeAll();
			this.panel.invalidate();
			control.listAllAlbums(this.panel);
			this.panel.revalidate();
			this.panel.repaint();
			UserHome.getDelete().setEnabled(false);
			UserHome.getEdit().setEnabled(false);
			//control.listAllAlbums(this.panel);
			
			currentAlbum = null;
			this.thumbnail = null;
		}else if(this.options == 2){
			
			currentAlbum.toggleThumbnailName();
			UserHome.getEdit().setEnabled(false);
			UserHome.getDelete().setEnabled(false);
			
		}else if(this.options == 3){
			
			
			//	System.out.println("ATTEMPTING TO DEL: " + currentPhoto.getThumbNailName());
			
			System.out.println(control.getCurrentAlbum());
			this.control.removePhoto(currentPhoto.getThumbNailName(), control.getCurrentAlbum());
			this.panel.removeAll();
			this.panel.invalidate();
			control.listAllPhotos(control.getCurrentAlbum(), this.panel);
			this.panel.revalidate();
			this.panel.repaint();
			UserAlbum.disableCommands();
			currentPhoto = null;
			this.photoThumbNail = null;
			
		}else if(this.options == 4){
			
			currentPhoto.toggleThumbnailName();
			UserAlbum.disableCommands();
			currentPhoto = null;
			
		}else if(this.options == 5){
			
			KeyListenerPhoto.toggleTagFlag();
			currentPhoto.addTag();
			UserAlbum.disableCommands();
			currentPhoto = null;
			
		}else if(this.options == 6){
			
			KeyListenerPhoto.toggleDelFlag();
			currentPhoto.addTag();
			UserAlbum.disableCommands();
			currentPhoto = null;
			
		}else if(this.options == 7){
			
			UserAlbum.setToMove(selectedPhoto);
			UserAlbum.enableList();
			UserAlbum.disableCommands();

			currentPhoto.idle(currentPhoto.getGraphics());
			currentPhoto = null;
		}else if(this.options == 8){
			
			currentPhoto = null;
			UserAlbum.disableCommands();
			iter = 0;
			stop = false;
			images = new ArrayList<String>(UserAlbum.images.keySet());
			count = images.size();
			
			//System.out.println(images.size());
			UserAlbum.toggleSlideShow();
			UserAlbum.displayPhoto(UserAlbum.images.get(images.get(0)).getImage(), images.get(0));
			
		
			
			

		}
		
	}
	
	//public static void clearCurrent(){
		
		//current = null;
		
	//}
	
	/**
	 * reload panel
	 */
	public void clear(){
		
		panel.removeAll();
		panel.invalidate();
		control.listAllAlbums(panel);
		panel.revalidate();
		panel.repaint();
	}
	
	public static void setCurrentAlbum(String albumName){
		
		selectedAlbum = albumName;
		
	}
	
	public static void setCurrentPhoto(String photoName){
		
		selectedPhoto = photoName;
		
	}
	
	public static Thumbnail getCurrent(){
		
		return currentAlbum;
	}
	
	public static void next(){
		
		iter++;
		if(iter >= count){
			iter = 0;
		}
		
		UserAlbum.displayPhoto(UserAlbum.images.get(images.get(iter)).getImage(), images.get(iter));
		
	}
	
	
	public static void prev(){
		
		iter--;
		if(iter < 0){
			iter = count - 1;
		}
		
		UserAlbum.displayPhoto(UserAlbum.images.get(images.get(iter)).getImage(), images.get(iter));
	}


}
