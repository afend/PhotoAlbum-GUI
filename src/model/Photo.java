package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashMap;
import javax.swing.JLabel;
import util.ObjectHighlightListener;

public class Photo implements Serializable, PhotoHash, Comparable<Photo>{

	private static final long serialVersionUID = 1L;
	private String fileName;
	private String filePath;
	private Calendar accessDate;
	private String caption;
	private ArrayList<Tag> tags;
	private transient JLabel image;
	private HashMap<String, String> albums;

	
	/**
	 * Default constructor, all photos must have a filename.
	 * @param fileName
	 */
	public Photo(String fileName, String caption){
		this.fileName = fileName;
		this.filePath = "";
		this.caption = caption; 
		this.accessDate = new GregorianCalendar();
		this.accessDate.set(Calendar.MILLISECOND,0);
		this.tags = new ArrayList<Tag>();
		this.albums = new HashMap<String, String>();
	}

	public PhotoThumbNail getThumbnail(String caption, String filePath){
		
		PhotoThumbNail thumbnail = new PhotoThumbNail(caption, filePath);
		thumbnail.addMouseListener(new ObjectHighlightListener(thumbnail, 3));
		return thumbnail;
	}
	
	public void addImageData(JLabel image){
		
		this.image = image;
		
	}
	
	public JLabel getImage(){
		
		return this.image;
	}

	public boolean addTag(Tag tag) {
		
		
	if(tag.getTagType().equals("location")){
		for(Tag tg: tags){
			if(tg.getTagType().equals("location"))
				return false;	
		}
	}
		
		for(Tag tg: tags){
			if(tag.getTagType().equals(tg.getTagType()) && tag.getTagData().equals(tg.getTagData()))
				return false;
		}
		
		tags.add(tag);
		
		return true;
		
	}
	
	public boolean hasTag(String tagType, String tagValue){
		
		for(Tag tag: this.tags){
			if(tag.getTagType().equals(tagType) && tag.getTagData().equals(tagValue))
				return true;
		}
		return false;
	}
	
	public boolean addAlbumRef(String albumName){
		
		if(!this.albums.containsKey(albumName)){
			this.albums.put(albumName, albumName);
			return true;
		}
		
		return false;
	}
	
	public boolean removeAlbumRef(String albumName){
		
		if(this.albums.containsKey(albumName)){
			this.albums.remove(albumName);
			return true;
		}
		
		return false;
	}

	
	public Calendar getDate(){
		return this.accessDate;
	}


	//@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList<Tag> listTags() {
		
		Comparator comp1 = new SortTagLocaitonAndPerson();
		Collections.sort(tags,comp1);
		return tags;
		
	}

	public void changeCaption(String newCaption) {
		this.caption = newCaption;
		
	}

	public boolean deleteTag(String tagData) {
		
		for(Tag tagLocalData: this.tags){
			if(tagLocalData.getTagData().equals(tagData)){
				this.tags.remove(tagLocalData);
				return true;
			}
		}
		
		return false;
	}

	public String getFileName(){
		
		return this.fileName;
	}
	
	public void setCaption(String caption){
		
		this.caption = caption;
		
	}
	
	public String getFilePath(){
		
		return this.filePath;
	}
	
	public void setFilePath(String path) {
		this.filePath = path;
	}

	public String toString(){
		
		return this.caption;
	}
	
	public void refreshAccessDate(){
		this.accessDate = new GregorianCalendar();
		this.accessDate.set(Calendar.MILLISECOND,0);
	}
	
	public ArrayList<String> listAlbumRef(){
		
		return new ArrayList<String>(albums.values());
	}



	public int compareTo(Photo arg0) {

		return this.accessDate.compareTo(arg0.accessDate);
	}
	
	class SortTagLocaitonAndPerson implements Comparator<Tag>{

		public int compare(Tag arg0, Tag arg1) {
			if(arg0.getTagType().equals("location") && !arg1.getTagType().equals("location")){
				return -1;
			}else if(arg0.getTagType().equals("people") && !arg1.getTagType().equals("people") && !arg1.getTagType().equals("location"))
				return -1;
			else if(arg0.getTagType().equals("location") && arg1.getTagType().equals("location"))
				return 0;
			else if(arg0.getTagType().equals("poeple") && !arg1.getTagType().equals("people"))
				return 0;
			else return 
					1;
	}
	}
	
}
