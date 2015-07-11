package model;

import java.awt.GridBagConstraints;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.JPanel;
import util.ObjectHighlightListener;
import util.ParseFilePath;
import view.UserAlbum;


/**
 * Object to represent an album and associated data.
 * Members are package private unless stated otherwise.
 *
 */
public class Album implements Serializable, AlbumHash {
	private static final long serialVersionUID = 1L;
	private String albumName;
	private HashMap<String, Photo> photos;
	
	/**
	 * default constructor, does not require any photos.
	 * @param name
	 */
	Album(String name) {
		this.albumName = name;
		this.photos = new HashMap<String, Photo>();
	}
	
	public Thumbnail getThumbnail(String albumName) {
		Thumbnail thumbnail = new Thumbnail(albumName);
		thumbnail.addMouseListener(new ObjectHighlightListener(thumbnail, 1));
		return thumbnail;
	}
	
	public void changeCaption(String oldCaptionName, String newCaptionName){
		this.photos.get(oldCaptionName).setCaption(newCaptionName);
		this.photos.put(newCaptionName, this.photos.get(oldCaptionName));
		this.photos.remove(oldCaptionName);
		
		if (UserAlbum.images.containsKey(oldCaptionName)) {
			UserAlbum.images.put(newCaptionName, UserAlbum.images.get(oldCaptionName));
			UserAlbum.images.remove(oldCaptionName);
		}
	}
	
	public void addPhotoToPanel(JPanel panel, String photoName){
		ArrayList<String> userInput = ParseFilePath.strToPath(photoName);
		String photoHash = userInput.get(userInput.size() - 1);
		GridBagConstraints insert = getIteratorPhoto();
		PhotoThumbNail thumbnail = this.photos.get(photoHash).getThumbnail(photoHash,photoName);
		photos.get(photoHash).addImageData(thumbnail.getImage());
		UserAlbum.images.put(photoHash, thumbnail);
		panel.add(thumbnail, insert);
		panel.revalidate();
		thumbnail.addName();
	}
	
	public GridBagConstraints getIteratorPhoto() {
		GridBagConstraints insert = new GridBagConstraints();
		int count = (numPhotos() - 1);
		insert.gridy = Math.floorDiv(count, 4);
		insert.gridx = count % 4;
		insert.weightx = .25;
		insert.weighty = 1;
		insert.anchor = GridBagConstraints.NORTHWEST;
		System.out.println(insert.gridx);
		System.out.println(insert.gridy);
		return insert;
	}
		
	public void loadPhotos(JPanel panel) {
		GridBagConstraints insert = new GridBagConstraints();
		insert.gridy = 0;
		insert.gridx = 0;
		insert.weightx = .24;
		insert.weighty = 1;
		insert.anchor = GridBagConstraints.NORTHWEST;
		
		if(!this.photos.isEmpty()){
			
			UserAlbum.getSlideShow().setEnabled(true);
			for (Iterator<Photo> iterator = this.photos.values().iterator(); iterator
					.hasNext();) {
				
				Photo photo = iterator.next();
				panel.add(photo.getThumbnail(photo.toString(), photo.getFilePath()), insert);
				
				if (!UserAlbum.images.containsKey(photo.toString())) {
					UserAlbum.images.put(photo.toString(), photo.getThumbnail(photo.toString(), photo.getFilePath()) );
				}

				insert.gridx++;
				
				if (insert.gridx == 4) {
					insert.gridy++;
					insert.gridx = 0;	
				}	
			}
		} else {	
			UserAlbum.getSlideShow().setEnabled(false);
		}
	}
	
	public boolean addPhoto(Photo photo) {
		if (photos.containsKey(photo.toString())) {
			return false;
		}
		
		photos.put(photo.toString(), photo);
		return true;
	}
	
	public int numPhotos() {	
		return this.photos.size();
	}

	public boolean deletePhoto(String photoName) {
		if (!photos.containsKey(photoName))
			return false;
		
		photos.remove(photoName);
		return true;
	}
	
	public String albumInfo() {
		Calendar startDate = new GregorianCalendar();
		Calendar endDate = new GregorianCalendar();
		ArrayList<Photo> photos = this.listPhotos();
		
		if (photos.size() == 0) {
			return this.albumName + " is empty.";
		}
		
		startDate = photos.get(0).getDate();
		endDate = photos.get(0).getDate();
		
		for (Photo photo : photos) {
			
			if (startDate.compareTo(photo.getDate()) > 0)
				startDate = photo.getDate();

			if (endDate.compareTo(photo.getDate()) < 0)
				endDate = photo.getDate();
		}
		
		return this.albumName + " number of photos: " + photos.size() + ", " + startDate.getTime().toString() + " - " + endDate.getTime().toString();
	}

	public boolean equals(Album album) {
		return this.albumName.equals(album.albumName) && this.photos.values().equals(album.photos.values());
	}
	
	public String toString() {
		return this.albumName;
	}

	public ArrayList<Photo> listPhotos() {
		return new ArrayList<Photo>(this.photos.values());
	}

	public Photo hashPhoto(String photoName) {
		return photos.get(photoName);
	}
	
	public boolean hasPhoto(String photoName) {
		return photos.containsKey(photoName);
	}

	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}
}