package cs213.photoAlbum.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class Album implements Serializable, AlbumHash{

	private static final long serialVersionUID = 1L;
	private String albumName;
	private HashMap<String, Photo> photos;
	
	/**
	 * default constructor, does not require any photos.
	 * @param name
	 */
	Album(String name){
		this.albumName = name;
		this.photos = new HashMap<String, Photo>();
	}


	public boolean addPhoto(Photo photo) {
		
		if(photos.containsKey(photo.toString()))
			return false;
		
		photos.put(photo.toString(), photo);
		return true;
	}


	public boolean deletePhoto(String photoName) {
		
		if(!photos.containsKey(photoName))
			return false;
		
		photos.remove(photoName);
		return true;
	}
	
	public String albumInfo(){
		
		Calendar startDate = new GregorianCalendar();
		Calendar endDate = new GregorianCalendar();
		ArrayList<Photo> photos = this.listPhotos();
		
		if(photos.size() == 0){
			return this.albumName + " is empty.";
		}
		startDate = photos.get(0).getDate();
		endDate = photos.get(0).getDate();
		
		for(Photo photo : photos){
			
			if(startDate.compareTo(photo.getDate()) > 0)
				startDate = photo.getDate();

			if(endDate.compareTo(photo.getDate()) < 0)
				endDate = photo.getDate();
		
		}
		
		return this.albumName + " number of photos: " + photos.size() + ", " + startDate.getTime().toString() + " - " + endDate.getTime().toString();
	}

	public boolean equals(Album album) {
		
		return this.albumName.equals(album.albumName) && this.photos.values().equals(album.photos.values());

	}
	
	public String toString(){
		return this.albumName;
	}


	public ArrayList<Photo> listPhotos() {
		
		return new ArrayList<Photo>(this.photos.values());
	}


	public Photo hashPhoto(String photoName) {
		
		return photos.get(photoName);
	}
	
	public boolean hasPhoto(String photoName){
		
		return photos.containsKey(photoName);
	}


}
