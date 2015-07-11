package cs213.photoAlbum.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Backend implements Serializable, BackEndHash{
	
	private static final long serialVersionUID = 1L;
	private transient ObjectInputStream in;
	private transient ObjectOutputStream out;
	
	private HashMap<String, User> users;
	
	public Backend()
	{
		this.users = new HashMap<String, User>();
	}
	
	@SuppressWarnings("unchecked")
	public void loadPhotoAlbum(){
		
		try {
			
			this.in = new ObjectInputStream(new FileInputStream("object.ser"));
			this.users = (HashMap<String, User>) in.readObject();
			
		} catch (IOException e) {

			System.out.print("");
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
	}
	
	public void savePhotoAlbum(){
		
		try {
			
			this.out = new ObjectOutputStream(new FileOutputStream("object.ser"));
			this.out.writeObject(this.users);
			this.out.close();
			
		} catch (IOException e) {
			
			System.out.print("");
		}
	}

	public User getUser(String userID){
		
		if(userID != null && !userID.isEmpty()){
			
			return users.get(userID);
		}
		
		return null;
	}
	
	public boolean hasUser(String userID){
		
		return this.users.containsKey(userID);
	}
	
	public boolean addUser(String userID, String userName){
		
		if(userID != null && !userID.isEmpty()){
			
			if(!users.containsKey(userID)){
				
				users.put(userID, new User(userName,userID));
				return true;
				
			}
		}
		
		return false;
	}
	
	public boolean delUser(String userID){
		
		if(userID != null && !userID.isEmpty()){ 
			
			if(users.get(userID) != null){
				
				users.remove(userID);
				return true;
			}
		}
		
		return false; 
	}
	
	public List<String> listUsers(){
		
		return new ArrayList<String>(users.keySet());
		
	}

	public boolean readUsersfileIO() {
		return false;
	}

	public boolean writeUsersfileIO() {
		return false;
	}
	
	
}
