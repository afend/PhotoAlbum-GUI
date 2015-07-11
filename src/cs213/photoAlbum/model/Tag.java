package cs213.photoAlbum.model;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Tag implements Serializable{
	
	private String tagName;
	private String tagType;
	private ArrayList<Photo> photo;
	
	public Tag(String tagType, String tagName){
		this.tagName = tagName;
		this.tagType = tagType;
		this.photo = new ArrayList<Photo>();
	}
	
	public String toString(){
		return this.tagType + ":" + this.tagName;
	}
	
	public String getTagData(){
		return this.tagName;
	}
	
	public String getTagType(){
		return this.tagType;
	}
	
	public void addPhoto(Photo photo){
		this.photo.add(photo);
	}
	
	public ArrayList<Photo> photosWithThisTag(){
		return this.photo;
	}

 }
