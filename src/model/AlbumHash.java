package model;

import java.util.ArrayList;

/**
 * Implements methods for the Album class as they relate to the hashtable data structure 
 *
 */
public interface AlbumHash {
	
	/**
	 * Takes a String <photo file name> and adds it to the 
	 * currently referenced album.
	 * @param photoName
	 * @param caption
	 * @return
	 */
	boolean addPhoto(Photo photo);
	
	/**
	 * Takes a String <photo file name> and adds it to the 
	 * currently referenced album.
	 * @param photoName
	 * @return
	 */
	boolean deletePhoto(String photoName);
	
	/**
	 * compares two album objects, returns true if their 
	 * contents are an exact match, false otherwise. 
	 * @param album
	 * @return
	 */
	boolean equals(Album album);
	
	/**
	 * list all photo objects currently an an album, returns 
	 * false if empty. 
	 * @return
	 */
	ArrayList<Photo> listPhotos();
	
	/**
	 * Returns the requested photo. Null if the photo doesn't exist. 
	 * @param photoName
	 * @return
	 */
	Photo hashPhoto(String photoName);

}