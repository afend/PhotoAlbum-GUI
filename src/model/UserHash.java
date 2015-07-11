package model;

import java.util.ArrayList;

public interface UserHash {
	
	/**
	 * Adds an album object to the currently logged in user, returns 
	 * false if the album could not be added.
	 * @param albumName
	 * @return
	 */
	boolean addAlbum(String albumName);
	
	/**
	 * Removes an album object from the current user. Returns 
	 * false if the album requested does not exist. 
	 * @param albumName
	 * @return
	 */
	boolean deleteAblum(String albumName);
	
	/**
	 * Renames the album targeted by the first argument to the string 
	 * passed in via the second argument. 
	 * @param albumName
	 * @param newAblumName
	 * @return
	 */
	boolean renameAlbum(String albumName, String newAblumName);
	
	/**
	 * lists all albums associated with a current user, returns 
	 * false if no ablums exist. 
	 * @return
	 */
	ArrayList<Album> listAlbums();
	
	/**
	 * Hash to an album via a user inputed album name. 
	 * @param albumName
	 * @return
	 */
	Album hashAlbum(String albumName);
	
	/**
	 * This is for commands that request a photo but do not explicitly state which album the photo
	 * is in.
	 * @param photoName
	 */
	public ArrayList<Photo> searchForPhotos(String photoName);
}
