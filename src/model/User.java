package model;

import java.awt.GridBagConstraints;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

import javax.swing.JPanel;

import util.TagToken;

public class User implements Serializable, UserHash{
	
	private static final long serialVersionUID = 1L;
	private String name; 
	private String userID; //each user will have a directory based on his UserID
	private HashMap<String, Album> albums;
	private HashMap<String, Tag> locations;
	private HashMap<String, Tag> people;
	private HashMap<String, Tag> misc;
	
	
	public User(String name, String userID){
		this.name = name;
		this.userID = userID;
		this.albums = new HashMap<String, Album>();
		this.locations = new HashMap<String, Tag>();
		this.people = new HashMap<String, Tag>();
		this.misc = new HashMap<String, Tag>();
	}
	

	/**
	 * get userID provides access to user's unique ID, in order to facilitate list all for admin mode
	 * @return
	 */
	public String getUser() {
		return this.name;
	}
	
	public String toString(){
		return this.userID;
	}

	public ArrayList<Album> listAlbums() {
		
		return new ArrayList<Album>(this.albums.values());
		
	}
	
	public void loadAlbums(JPanel panel){
		
		GridBagConstraints insert = new GridBagConstraints();
		insert.gridy = 0;
		insert.gridx = 0;
		insert.weightx = .24;
		insert.weighty = 1;
		
		insert.anchor = GridBagConstraints.NORTHWEST;
		
		if(!this.albums.isEmpty()){
			
			
			for (Iterator<Album> iterator = this.albums.values().iterator(); iterator
					.hasNext();) {
				
				Album album = iterator.next();
				
				panel.add(album.getThumbnail(album.toString()), insert);
				//System.out.println("LOAD PANEL INSERT: " + insert.gridy + " : " + insert.gridx );
				insert.gridx++;
				
				if(insert.gridx == 4){
					
					insert.gridy++;
					insert.gridx = 0;
					
				}
				
				
			}
			
		}
		
	}
	
	
	public int getAlbumCount(){
		
		return this.albums.size();
		
	}
	
	public int getPhotoCount(){
		
		int photos = 0;
		
		ArrayList<Album> albums = this.listAlbums();
		
		if(albums.size() > 0){
			
			for(Album album : albums){
				
				photos += album.numPhotos();
				
			}
			
		}
		
		return photos;
	}

	public boolean addAlbum(String albumName) {
		
		if(albums.containsKey(albumName)){
			
			return false;
			
		}else{
			
			albums.put(albumName, new Album(albumName));
			return true;
		}
			
	}
	
	public void addAlbumToPanel(JPanel panel, String albumName){
		
		GridBagConstraints insert = getIterator();
		
		panel.add(this.albums.get(albumName).getThumbnail(albumName), insert);
		
		panel.revalidate();
		
	}
	
	public boolean changeCaption(String oldCaptionName, String newCaptionName, String albumName){
		
		for (Iterator<Album> iterator = albums.values().iterator(); iterator
				.hasNext();) {
			Album album = iterator.next();
			
			if(album.hasPhoto(newCaptionName)){
				return false;
			}
		}
		
		this.hashAlbum(albumName).changeCaption(oldCaptionName, newCaptionName);
		
		return true;
		
	}
	
	public GridBagConstraints getIterator(){
		
		GridBagConstraints insert = new GridBagConstraints();
		
		int count = (getAlbumCount() - 1);
		
		insert.gridy = Math.floorDiv(count, 4);
		insert.gridx = count % 4;
		insert.weightx = .25;
		insert.weighty = 1;
		insert.anchor = GridBagConstraints.NORTHWEST;
		System.out.println(insert.gridx);
		System.out.println(insert.gridy);
		
		return insert;
	}

	public boolean deleteAblum(String albumName) {
		
		if(albums.get(albumName) == null){
			
			return false;
			
		}else{
			
			albums.remove(albumName);
			return true;
		}
	}
	
	
	public void addTagToMap(Tag tag){
		
		if(tag.getTagType().equals("location")){

			this.locations.put(tag.getTagData(), tag);
				
		}else if(tag.getTagType().equals("people")){

			this.people.put(tag.getTagData(), tag);
			
		}else{

			this.misc.put(tag.getTagData(), tag);
		}
	}

	public boolean renameAlbum(String albumName, String newAlbumName) {
		
		
		
		if(albums.get(albumName) == null){
			//System.out.println("ALBUM RENAME FAIL");
			return false;
			
		}else if(albums.get(newAlbumName) == null){
			
			//System.out.println("ALBUM RENAME WIN");
			this.albums.get(albumName).setAlbumName(newAlbumName);
			this.albums.put(newAlbumName, this.albums.get(albumName));
			this.albums.remove(albumName);
			
			return true;
			
		}else{
			
			return false;
		}
	}

	public Album hashAlbum(String albumName){
		return this.albums.get(albumName);
	}
	
	public boolean checkForAblum(String albumName){
		
		return this.albums.containsKey(albumName);
		
	}
	
	public ArrayList<Photo> searchForPhotos(String photoName)
	{
		ArrayList<Photo> photos = new ArrayList<Photo>();
		Set<Entry<String, Album>> set = albums.entrySet();
		
		Iterator<Entry<String, Album>> i = set.iterator();
		
		while(i.hasNext()){
			
			Entry<String, Album> me = i.next();
			
			if(me.getValue().hashPhoto(photoName) != null)
			{
				photos.add(me.getValue().hashPhoto(photoName));
				break;
			}
	
		}
		
		return photos;
		
	}
	
	public ArrayList<Photo> searchForDates(Calendar startDate, Calendar endDate)
	{
		ArrayList<Photo> photos = new ArrayList<Photo>();
		ArrayList<Photo> temp = new ArrayList<Photo>();
		Set<Entry<String, Album>> set = albums.entrySet();
		
		Iterator<Entry<String, Album>> i = set.iterator();
		
		while(i.hasNext()){
			
			Entry<String, Album> me = i.next();
			
			temp = me.getValue().listPhotos();
			
			for(Photo photo : temp){
				
			//	System.out.println("PHOTO" + photo.getDate().getTime().toString());
			//	System.out.println("START" + startDate.getTime().toString());
			//	System.out.println("END" + startDate.getTime().toString());
				// not sure if this is the correct comparison statements, make sure to double check
				if(photo.getDate().compareTo(startDate) >= 0 && photo.getDate().compareTo(endDate) <= 0)
				{
					photos.add(photo);
				}
			}
	
		}
		
		return photos;
		
	}
	
	public ArrayList<Photo> searchForTags(ArrayList<TagToken> tag){
		
		ArrayList<Photo> photos = new ArrayList<Photo>();
		
		for(TagToken tagToken: tag){
			if(tagToken.getType() == null){
				
				//check all maps
				if(locations.containsKey(tagToken.getData()))
					photos.addAll(locations.get(tagToken.getData()).photosWithThisTag());
				
				else if(people.containsKey(tagToken.getData()))
					photos.addAll(people.get(tagToken.getData()).photosWithThisTag());
				
				else if(misc.containsKey(tagToken.getData()))
					photos.addAll(misc.get(tagToken.getData()).photosWithThisTag());
				
			}else{
				
				if(tagToken.getType().equals("location")){
					
					if(locations.containsKey(tagToken.getData()))
						photos.addAll(locations.get(tagToken.getData()).photosWithThisTag());
					
				}else if(tagToken.getType().equals("people")){
					
					if(people.containsKey(tagToken.getData()))
						photos.addAll(people.get(tagToken.getData()).photosWithThisTag());
					
				}else{
					if(misc.containsKey(tagToken.getData()))
						photos.addAll(misc.get(tagToken.getData()).photosWithThisTag());
				}
			}
		}
		
		return photos;
	}
	

	public Tag addTag(String tagType, String tagName) {
			
		Tag copy = checkTag(tagType,tagName);
			if(copy == null)
				return new Tag(tagType, tagName);
			else 
				return copy;	
	}
	
	//return null if tag isn't found, a copy otherwise.
	public Tag checkTag(String tagType, String tagName){
		
			if(tagType.equals("location")){
				if(locations.get(tagName) != null){
					return locations.get(tagName);
				}
					
			}else if(tagType.equals("people")){
				if(people.get(tagName) != null){
					return people.get(tagName);
				}
				
			}else{
				if(misc.get(tagName) != null){
					return misc.get(tagName);
				}
			}

		return null;
	}
	
	public void setName(String name){
		
		this.name = name;
	}
	

	

}
