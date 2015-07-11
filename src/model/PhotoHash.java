package model;

import java.util.ArrayList;

/**
 * Photohash implements methods for the Photo class as they relate to the hashmap datastucture 
 *
 */
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