package cs213.photoAlbum.model;

import java.util.List;

public interface BackEndHash {
	
	/**
	 * Adds a new user to the program, if the user could not be added return false.
	 * @param userID
	 * @return
	 */
	public boolean addUser(String userID, String userName);
	
	/**
	 * Removes a user object from the program (this will delete all their albums/photos.
	 * Returns false if the requested user does not exist. 
	 * @param UserID
	 * @return
	 */
	public boolean delUser(String UserID);
	

	/**
	 * Save all Users and their associated objects. 
	 */
	public boolean writeUsersfileIO();
	
	/**
	 * Load all users associated with the photoAlbum class.  
	 */
	public boolean readUsersfileIO();
	
	
	/**
	 * This function returns a list of all existing users. 
	 * @return
	 */
	List<String> listUsers();
	
}
