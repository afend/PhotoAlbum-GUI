package cs213.photoAlbum.model;

import java.util.ArrayList;

public interface PhotoHash {

	/**
	 * Adds a new tag key to a photo object
	 * @param tagKey
	 * @return 
	 */
	boolean addTag(Tag tag);
	
	/**
	 * Removes a tag from an object, returns false if 
	 * requested tag doesn't exist. 
	 * @return
	 */
	boolean deleteTag(String tafData);
	
	/**
	 * List all associated tags with a photo. 
	 * @return 
	 */
	ArrayList<Tag> listTags();
	
	/**
	 * Change the current caption to the argument of this function.
	 * @param newCaption
	 * @return
	 */
	void changeCaption(String newCaption);
	
}
