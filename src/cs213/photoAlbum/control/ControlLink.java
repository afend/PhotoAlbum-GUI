package cs213.photoAlbum.control;

import java.util.ArrayList;
import java.util.Calendar;
import cs213.photoAlbum.util.TagToken;

public interface ControlLink{
	
	/**
	 * This function logs an individual user in. Returns true on successful login and false on failure.
	 * @param userID
	 * @return
	 */
	boolean login(String userID);
	
	/**
	 * This function logs an individual user off, returns false if the logout failed. 
	 * @param userID
	 * @return
	 */
	void logout();
	
	/**
	 * Creates a new user via an inputed string, returns true on success, false otherwise. 
	 * @param userID
	 * @return
	 */
	void addNewUser(String userID, String userName);
	
	/**
	 * Removes an existing user, returns true on success, false otherwise. 
	 * @param userID
	 * @return
	 */
	void deleteUser(String userID);
	
	/**
	 * Returns a list of existing users. 
	 * @return
	 */
	void listUsers();
	
	/**
	 * Load previous application state
	 */
	void load();
	
	void createAlbum(String albumName);
	
	/**
	 * prompt from user to delete an album
	 * deleteAlbum "<name>"
	 */
	void deleteAlbum(String albumName);
	
	/**
	 * list all albums
	 * listAlbums
	 */
	void listAllAlbums();
	
	/**
	 * list all photos in an album
	 * listPhotos "<name>"
	 * @param AlbumName
	 */
	void listAllPhotos(String albumName);
	
	

	/**
	 * prompt for adding a photo
	 * addPhoto "<fileName>" "<caption>" "<albumName>"
	 * @param photoName
	 * @param caption
	 * @param albumName
	 */
	void addPhoto(String photoName, String caption, String albumName);
	

	/**
	 * prompt to move a photo to a different album
	 * movePhoto "<fileName>" "<oldAlbumName>" "<newAlbumName>"
	 * @param photoName
	 * @param currentAlbum
	 * @param newAlbum
	 */
	void movePhoto(String photoName, String currentAlbum, String newAlbum);
	
	/**
	 * prompt to move delete a photo from an album
	 * removePhoto "<filename>" "<albumname>"
	 * @param photoName
	 * @param albumName
	 */
	void removePhoto(String photoName, String albumName);
	
	
	/**
	 * add a tag to a photo
	 * addTag "<fileName>" <tagType>:"<tagValue>"
	 * @param photoName
	 * @param tagType
	 * @param tagValue
	 * @throws  
	 */
	void addTagToPhoto(String photoName, String tagType, String tagValue);
	
	/**
	 * delete a tag from a photo
	 * deleteTag"<fileName>" <tagType>:"<tagValue>"
	 * @param photoName
	 * @param tagType
	 * @param tagValue
	 */
	void delTagPhoto(String photoName, String tagType, String tagValue);
	
	/**
	 * list associated info form a photo
	 * listPhotoInfo "<fileName>"
	 * @param photoName
	 */
	void listPhotoInfo(String photoName);
	
	/**
	 * display photos within a time range, inclusive
	 * getPhotosByDate <start date> <end date>
	 * @param startDate
	 * @param endDate
	 */
	void getPhotosByDate(Calendar start, Calendar end);
	
	/**
	 * display photos by tqg
	 * getPhotosByTag [<tag type>:]"<tagValue>"[,[<tagType>:]"<tagValue>]...
	 * @param tags
	 */
	void getPhotosByTag(ArrayList<TagToken> tags);
	

}
