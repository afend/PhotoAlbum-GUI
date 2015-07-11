package model;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * This class will store tag objects for photos. It is being 
 * Separated into a different class to avoid the issue of having 
 * duplicate tags for being used for different photos. TLDR to save
 * space. Member are package private unless stated otherwise 
 *
 */
@SuppressWarnings("serial")
public class Tag implements Serializable {
	
	private String tagName;
	private String tagType;
	private ArrayList<Photo> photo;
	
	public Tag(String tagType, String tagName) {
		this.tagName = tagName;
		this.tagType = tagType;
		this.photo = new ArrayList<Photo>();
	}
	
	public String toString() {
		return this.tagType + ":" + this.tagName;
	}
	
	public String getTagData() {
		return this.tagName;
	}
	
	public String getTagType() {
		return this.tagType;
	}
	
	public void addPhoto(Photo photo) {
		this.photo.add(photo);
	}
	
	public ArrayList<Photo> photosWithThisTag() {
		return this.photo;
	}

}