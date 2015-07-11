package cs213.photoAlbum.control;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import cs213.photoAlbum.model.*;
import cs213.photoAlbum.util.ParseFIlePath;
import cs213.photoAlbum.util.ParseTagString;
import cs213.photoAlbum.util.TagToken;

public class Control implements ControlLink{
	
	static Backend state;
	static User currentUser;
	
	public Control(){
	}
	
	public boolean login(String userID) {
		
			if(state.hasUser(userID)){
				currentUser = state.getUser(userID);
				return true; 
			}else{
				System.out.println("user " + userID + " does not exist");
				return false;
			}
		
	}

	public void logout() {
	
		state.savePhotoAlbum();
	}

	public void addNewUser(String userID, String userName) {
		
		if(state.addUser(userID, userName))
			System.out.println("created user " + userID + " with name " + userName);
		else
			System.out.println("user " + userID + " already exists with name " + state.getUser(userID).toString());
	}

	public void deleteUser(String userID) {
		
		if(state.delUser(userID))
			System.out.println("deleted user " + userID);
		else
			System.out.println("user " + userID + " does not exist");
		
	}
	
	
	//only need userIDs
	public void listUsers() {
		
		if(state.listUsers().size() > 0){
			for(String user : state.listUsers()){
				System.out.println(user);
			}
		}else{
			
			System.out.println("no users exist");
			
		}
	}


	public void load() {
		state = new Backend();
		state.loadPhotoAlbum();
	}

	public void createAlbum(String albumName) {
		
		if(currentUser != null)
			if(!currentUser.checkForAblum(albumName)){
				
				currentUser.addAlbum(albumName);
				System.out.println("created album for " + currentUser.getID() +":\n" + albumName);
				return;
				
			}else{
				
				System.out.println("album exists for " + currentUser.getID() +":\n" + albumName);
				
			}
		
	}

	public void deleteAlbum(String albumName) {
		
		if(currentUser != null)
			if(currentUser.checkForAblum(albumName)){
				
				currentUser.deleteAblum(albumName);
				System.out.println("deleted album for " + currentUser.getID() +":\n" + albumName);
				return;
				
			}else{
				System.out.println("album does not exist for " + currentUser.getID() +":\n" + albumName);
			}
	}

	public void listAllAlbums() {
		
		if(currentUser != null){
			if(currentUser.listAlbums().size() > 0){
				for(Album album: currentUser.listAlbums()){
					System.out.println(album.albumInfo());
				}
			}else{
				System.out.println("No albums exist for user " + currentUser.getID());
			}
		}
	}

	public void listAllPhotos(String albumName) {
		
		ArrayList<Photo> photos = new ArrayList<Photo>();
		if(currentUser != null){
			
			if(currentUser.checkForAblum(albumName)){

				photos = currentUser.hashAlbum(albumName).listPhotos();
			
				System.out.println("Photos for album " + albumName + ":");
				
				for(Photo photo : photos){
					System.out.println(photo.toString() + " - " + photo.getDate().getTime().toString());
				}
				return;
					
			}else{
				System.out.println("Album " + albumName + " does not exist");
				return;
			}
		}
	}

	public void addPhoto(String photoName, String caption, String albumName) {
		
		ArrayList<String> userInput = ParseFIlePath.strToPath(photoName);
		
		String path = "";
		
		for(String str : userInput)
			path += str;
		
		//scrap file name
		photoName = userInput.get(userInput.size() - 1);
		
		Photo photoToAdd = null;
		
		if(currentUser != null){
			
			//check if photo exists somewhere else to get correct caption
			for(Album album : currentUser.listAlbums()){
				
					if(album.hasPhoto(photoName)){
						photoToAdd = album.hashPhoto(photoName);
						break;
					
				}
			}
			
			//photo wasn't found
			if(photoToAdd == null && caption.equals("")){
				System.out.println("Error: File " + photoName + " does not exist");
				return;
			}
			else if(photoToAdd == null){
				photoToAdd = new Photo(photoName, caption);
				photoToAdd.setFilePath(path);
			}
			
				
			if(currentUser.checkForAblum(albumName)){
				
				if(!currentUser.hashAlbum(albumName).hasPhoto(photoName)){
					
					photoToAdd.addAlbumRef(albumName);
					currentUser.hashAlbum(albumName).addPhoto(photoToAdd);
					System.out.println("Added photo " + photoName + ":\n" + caption +  " - Album: " + albumName);
					return;
					
				}else{
					
					System.out.println("Photo " + photoName+ " already exists");
					return;
				}
				
			}else{
				
				System.out.println("Album " + albumName + " does not exist");
				return;
			}
			

		}
		
	}

	public void movePhoto(String photoName, String currentAlbum, String newAlbum) {
		
		Album source = null;
		Album dest = null;
		Photo toMove = null;
		
		if(currentUser != null){
			
			if(currentUser.checkForAblum(currentAlbum)){
				
				source = currentUser.hashAlbum(currentAlbum);
				
			}else{
				
				System.out.println("Album " + currentAlbum + " does not exist");
				
			}
			
			if(currentUser.checkForAblum(newAlbum)){
				
				dest = currentUser.hashAlbum(newAlbum);
				
			}else{
				
				System.out.println("Album " + newAlbum + " does not exist");
				return;
			}
			
			
			
			if(source != null && dest != null){
				
				if(source.hasPhoto(photoName))
					toMove = source.hashPhoto(photoName);
				
				if(toMove != null && !dest.hasPhoto(photoName)){
					
					toMove.addAlbumRef(dest.toString());
					toMove.removeAlbumRef(source.toString());
					source.deletePhoto(photoName);
					dest.addPhoto(toMove);
					
					System.out.println("Moved Photo " + photoName + ":\n" + photoName + " - From album " + currentAlbum + " to album " + newAlbum);
					
					return;
					
				}else{
					
					System.out.println("Photo " + photoName + " already exists in " + newAlbum);
					return;
				}
			}
		}
		
	}

	public void removePhoto(String photoName, String albumName) {
		
		if(currentUser != null){
			
			if(!currentUser.checkForAblum(albumName)){
				System.out.println("Album " + albumName + " does not exist");
				return;
			}
				
				if(currentUser.hashAlbum(albumName).hasPhoto(photoName)){
					
					currentUser.hashAlbum(albumName).hashPhoto(photoName).removeAlbumRef(albumName);
					currentUser.hashAlbum(albumName).deletePhoto(photoName);
					System.out.println("Removed photo:\n" + photoName + " - From album " + albumName);
					return;
					
				}else{
					
					System.out.println("Photo " + photoName + " is not in album " + albumName);
					return;
				}
			
		}
		
	}

	public void addTagToPhoto(String photoName, String tagType, String tagValue) {
		
		ArrayList<Photo> photos = new ArrayList<Photo>();
		if(currentUser != null){
			
			//a list might not be needed here, simply store reference so we only need one item to operate on...
			photos = currentUser.searchForPhotos(photoName);
			
			if(!photos.isEmpty()){
				
				//create new tag and store in tag user's tag repository, then reference to add to photos. 
				Tag toAdd = currentUser.addTag(tagType, tagValue);
				
				//photos only keep a copy of the key
					for(Photo photo: photos){
					
						//pass copy of new tag to photo for reference 
						if(photo.addTag(toAdd)){
							
							toAdd.addPhoto(photo);
							currentUser.addTagToMap(toAdd);
							System.out.println("Added Tag:\n" + photoName + " " + toAdd.toString());
							return;
							
						}else{
							
							System.out.println("Tag already exist for " + photoName + " " + toAdd.toString());
							return;
						}
				}
			}
			
			System.out.println("Photo " + photoName + " does not exist");
			return;
		}
		
	}

	public void delTagPhoto(String photoName, String tagType, String tagValue) {
		
		Tag temp = new Tag(tagType, tagValue);
		
		ArrayList<Photo> photos = currentUser.searchForPhotos(photoName);
		
		if(photos.size() > 0){
		//photos = currentUser.searchForPhotos(photoName);
		
			for(Photo photo: photos){
				if(photo.hasTag(tagType, tagValue)){
					photo.deleteTag(temp.getTagData());
					System.out.println("Deleted Tag:\n" + photoName + " " + temp.toString());
				}else{
					System.out.println("Tag does not exist for " + photoName + " " + temp.toString());
				}
			}
		}else{
			System.out.println("Photo " + photoName + "does not exist");
		}
		
	}

	public void listPhotoInfo(String photoName) {
		
		ArrayList<Photo> photos = new ArrayList<Photo>();
		
		if(currentUser != null){
			
			photos = currentUser.searchForPhotos(photoName);
			
			if(photos.size() == 0){
				System.out.println("Error: " + photoName + " does not exist");
			}
			for(Photo photo: photos){
				
				System.out.println("Photo file name: " + photo.toString()); // this needs formating to match the project specifications "
				
				System.out.print("Albums: ");
				for(String album : photo.listAlbumRef()){
					System.out.print(album.toString() + " ");
				}
				
				System.out.println("\nDate: " + photo.getDate().getTime().toString() + "\nCaption: " +photo.getCaption() + "\nTags:");
				
				for(Tag tag : photo.listTags())
					System.out.println(tag.toString());
			}
		}
	}

	public void getPhotosByDate(Calendar start, Calendar end) {
		
		start.set(Calendar.MILLISECOND,0);
		end.set(Calendar.MILLISECOND,0);
		
		
		ArrayList<Photo> photos = new ArrayList<Photo>();
		
		photos = currentUser.searchForDates(start, end);
		
		if(photos.size() == 0)
		{
			System.out.println("No photos found btween those dates");
			return;
		}
			
			
		Collections.sort(photos);
		
		//clear duplicates
		HashSet<Photo> hs = new HashSet<Photo>();
		hs.addAll(photos);
		photos.clear();
		photos.addAll(hs);
		for(Photo photo : photos){
			
			System.out.print(photo.getCaption() + " - Album: ");
			
			for(String album : photo.listAlbumRef()){
				System.out.print(album + ", ");
			}
			
			System.out.print("- Date: " + photo.getDate().getTime().toString() + "\n");
		}
		
	}

	public void getPhotosByTag(ArrayList<TagToken> tags) {
		
		ArrayList<Photo> photos = currentUser.searchForTags(tags);
		
		Collections.sort(photos);
		
		System.out.println("Photos for user " + currentUser.getID() + " with tags " + ParseTagString.printSearchString(tags) + ":");
		
		//clear duplicates
		HashSet<Photo> hs = new HashSet<Photo>();
		hs.addAll(photos);
		photos.clear();
		photos.addAll(hs);
		for(Photo photo : photos){
			
			
			System.out.print(photo.getCaption() + " - Album: ");
			
			for(String album : photo.listAlbumRef()){
				System.out.print(album + ", ");
			}
			System.out.print("- Date: " + photo.getDate().getTime().toString() + "\n");
		}
	}




}