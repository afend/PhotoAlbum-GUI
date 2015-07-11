package cs213.photoAlbum.simpleview;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Calendar;
import cs213.photoAlbum.control.Control;
import cs213.photoAlbum.util.ParseDate;
import cs213.photoAlbum.util.ParseTagString;
import cs213.photoAlbum.util.TagToken;

public class CmdView {
	
	// @SuppressWarnings("null")
	public static void interactiveMode(Control control, String userID) throws IOException{
		 
		 control.login(userID);
		 
	   	 Boolean session = true;
	     Scanner reader = new Scanner(System.in);
	   	 String input = new String("");
	   	 
	   	 while (session == true) {
	   		
	   		 ArrayList<String> userCommand = new ArrayList<String>();
	   		 
	   		 input = reader.nextLine();
	   		 int e = -1;
	   		 e = input.indexOf(' ');
	   		 if(e != -1 )
	   			 userCommand.add(input.substring(0,e));
	   		 
	   		 input = input.substring(e+1);
	   		 
	   		 if (input.contains("\"")) {
	   			StringTokenizer token = new StringTokenizer(input,"\"");
	   			while (token.hasMoreTokens()) {
	 	           userCommand.add(token.nextToken());
	 	        }
	   		 }
	   		 else {
	   			StringTokenizer token = new StringTokenizer(input); 
	   			while (token.hasMoreTokens()) {
	 	           userCommand.add(token.nextToken());
	 	        }
	   		 }
	   		
	   		
	   		 // Check if any arguments are given
	   		 if (userCommand.get(0) == null) {
	   		 }
	   		 // There are arguments. Check what type
	   		 else {
	   			 // Check if the first argument is createAlbum
	   			 if (userCommand.get(0).equalsIgnoreCase("createalbum") 
	   					 || userCommand.get(0).equalsIgnoreCase("createalbum ")) {  				 
	 
	   				 if(userCommand.size() == 2){
	   					control.createAlbum(userCommand.get(1));
	   				 }else{
	   					 System.out.println("Error: " + userCommand.get(0) + " takes a single String argument <album name>");
	   				 }
	   			 }
	   			 
	   			 // Check if the first argument is deleteAlbum
	   			 else if (userCommand.get(0).equalsIgnoreCase("deletealbum")
	   					 || userCommand.get(0).equalsIgnoreCase("deletealbum ")) {
	   				 if(userCommand.size() == 2){
	   					control.deleteAlbum(userCommand.get(1));
		   			 }else{
		   				System.out.println("Error: " + userCommand.get(0) + " takes a single String argument <album name>");
		   			 } 
	   			 }
	   			 
	   			 // Check if the first argument is listAlbums
	   			 else if (userCommand.get(0).equalsIgnoreCase("listalbums")
	   					|| userCommand.get(0).equalsIgnoreCase("listalbums ")) {
	   				if(userCommand.size() == 1){
	   					control.listAllAlbums();
		   			 }else{
		   				System.out.println("Error: " + userCommand.get(0) + " takes no arguments");
		   			 } 
	   				 
	   			 }
	   			 
	   			 // Check if the first argument is listPhotos
	   			 else if (userCommand.get(0).equalsIgnoreCase("listphotos")
	   					 || userCommand.get(0).equalsIgnoreCase("listphotos ")) {
	   				 if(userCommand.size() == 2){
	   					control.listAllPhotos(userCommand.get(1));
			   		 }else{
			   				System.out.println("Error: " + userCommand.get(0) + " takes a single String argument <album name>");
			   		 } 
	   			 }
	   			 
	   			 // Check if the first argument is addPhoto
	   			 else if (userCommand.get(0).equalsIgnoreCase("addphoto")
	   					 || userCommand.get(0).equalsIgnoreCase("addphoto ")) {
	   				if(userCommand.size() == 6){
	   					control.addPhoto(userCommand.get(1), userCommand.get(3), userCommand.get(5));
			   		 }else if(userCommand.size() == 4){
			   			control.addPhoto(userCommand.get(1), "", userCommand.get(3));
			   		 }else{
			   			System.out.println("Error: " + userCommand.get(0) + " takes three String arguments, <filename> <caption> <album name>, the caption may be omitted if the photo already exists in the album");
			   		 }
	   					 
	   			 }
	   			 
	   			 // Check if the first argument is movePhoto
	   			 else if (userCommand.get(0).equalsIgnoreCase("movephoto")
	   					|| userCommand.get(0).equalsIgnoreCase("movephoto ")) {
	   				if(userCommand.size() == 6){
	   					control.movePhoto(userCommand.get(1), userCommand.get(3), userCommand.get(5));
			   		 }else{
			   			System.out.println("Error: " + userCommand.get(0) + " takes three String arguments, <filename> <current album> <new album>");
			   		 } 
	   				 
	   			 }
	   			 
	   			 // Check if the first argument is removePhoto
	   			 else if (userCommand.get(0).equalsIgnoreCase("removephoto")
	   					 || userCommand.get(0).equalsIgnoreCase("removephoto ")) {
	   				if(userCommand.size() == 4){
	   					control.removePhoto(userCommand.get(1), userCommand.get(3));
			   		 }else{
			   			System.out.println("Error: " + userCommand.get(0) + " takes two String arguments, <filename> <albumname>");
			   		 } 
	   				 
	   			 }
	   			 
	   			 //NEED TO IMPLEMENT PARSER 
	   			 else if (userCommand.get(0).equalsIgnoreCase("addtag")
	   					 || userCommand.get(0).equalsIgnoreCase("addtag ")){
	   				 if(userCommand.size() == 4){
	   					ArrayList<TagToken> tags = new ArrayList<TagToken>();
		   				userCommand.remove(0);
		   					tags.add(ParseTagString.parseSearchString(userCommand.get(1).trim() + userCommand.get(2)));   					


		   				if(tags.size() == 1)
		   					control.addTagToPhoto(userCommand.get(0), tags.get(0).getType(), tags.get(0).getData().trim());
		   				else
		   					System.out.print("Error: Incorrect tag format <tagType:tagValue>");
	   				 }else{
	   					 System.out.println("Error: " + userCommand.get(0) + " takes two String arguments <filename> <tagType:TagValue>, if the type is omitted or not of people/location it will be deemed a \"misc\" tag");
	   				 }
	   			 }
	   			 
	   			 //NEED TO IMPLEMENT PARSER
	   			 else if (userCommand.get(0).equalsIgnoreCase("deletetag")
	   					|| userCommand.get(0).equalsIgnoreCase("deletetag ")) {
	   				 if(userCommand.size() == 4){
		   					ArrayList<TagToken> tags = new ArrayList<TagToken>();
			   				userCommand.remove(0);
			   				
			   				tags.add(ParseTagString.parseSearchString(userCommand.get(1).trim() + userCommand.get(2)));
			   				
			   				if(tags.size() == 1 && tags.get(0).getType() != null && tags.get(0).getData() != null)
			   					control.delTagPhoto(userCommand.get(0), tags.get(0).getType(), tags.get(0).getData());
			   				else
			   					System.out.print("Error: Incorrect tag format <tagType:tagValue>");
		   				 }else{
		   					 System.out.println("Error: " + userCommand.get(0) + " takes two String arguments <filename> <tagType:TagValue>, if the type is omitted or not of people/location it will be deemed a \"misc\" tag");
		   				 }
	   				 
	   			 }
	   			 
	   			 // Check if the first argument is listPhotoInfo
	   			 else if (userCommand.get(0).equalsIgnoreCase("listphotoinfo")
	   					 || userCommand.get(0).equalsIgnoreCase("listphotoinfo ")) {
	   				if(userCommand.size() == 2){
	   					control.listPhotoInfo(userCommand.get(1));
			   		}else{
			   			System.out.println("Error: " + userCommand.get(0) + " takes one String argument <filename>");
			   		} 
   				 	
	   			 }
	   			 
	   			 // Check if the first argument is getPhotosbyDate
	   			 else if (userCommand.get(0).equalsIgnoreCase("getphotosbydate")
	   					 || userCommand.get(0).equalsIgnoreCase("getphotosbydate")) {
	   				if(userCommand.size() == 3){
	   					
	   					 Calendar start = ParseDate.strToDate(userCommand.get(1));
		   				 Calendar end = ParseDate.strToDate(userCommand.get(2));
		   				 
		   				 
		   				if(start != null && end != null)
		   					control.getPhotosByDate(start, end);
		   				 else
		   					 System.out.println("Error: Incorrect date format, please use <year/month/day/hour/minute/second> or illegal date entered");
	   					
		   				 
		   				 
			   		}else{
			   			System.out.println("Error: " + userCommand.get(0)+ " takes two String arguments representing the date in the following format <year/month/day/hour/minute/second> <year/month/day/hour/minute/second>");
			   		} 
	   				 
	   			 }
	   			 
	   			 // Check if the first argument is getPhotosbyTag
	   			 else if (userCommand.get(0).equalsIgnoreCase("getphotosbytag")
	   					 || userCommand.get(0).equalsIgnoreCase("getphotosbytag ")) {
	   				if(userCommand.size() > 1){
	   					ArrayList<TagToken> tags = new ArrayList<TagToken>();
		   				userCommand.remove(0);
		   				
		   				for(int i = 0; i < userCommand.size();){
		   					
		   					if(userCommand.get(i).contains(",")){
		   						userCommand.get(i).trim();
		   						userCommand.set(i, userCommand.get(i).substring(i));
		   					}
		   					
		   						
		   					if(userCommand.get(i).contains(":")){
		   						userCommand.get(i).trim();
		   						userCommand.set(i, userCommand.get(i).substring(0,userCommand.get(i).length()-1));
		   						tags.add(new TagToken(userCommand.get(i), userCommand.get(i+1).trim()));
		   						i += 2;
		   					}
		   					else{
		   						tags.add(new TagToken(userCommand.get(i).trim()));
		   						i++;
		   					}
		   					}
		   				
		   				
		   				
		
		   				control.getPhotosByTag(tags);
			   		}else{
			   			System.out.println("Error: " + userCommand.get(0) + " takes any amount of tag arguments with or without their type <tagValue> OR <tagType:tagValue> DO NOT USE BRACKETS");
			   		}
	   				
	   				
	   			 }
	   			 // Check if the first argument is logout
	   			 else if (userCommand.get(0).equalsIgnoreCase("logout")) {
	   				 session = false;
	   			 }
	   			 
	   			 else{
	   				 System.out.println("Error: " + userCommand.get(0) + " is not a valid command");
	   			 }
	   			 
	   			
	   		 }
	   		 System.out.println();
	   	 }
	   	 
	   	 return;
	 }

	 

	public static void main(String[] args){
		
		Control control = new Control();
		
		control.load();
		

	   	 // Check if any arguments are given
	   	 if (args.length == 0) {
	   		 System.out.println("Error: no arguments given");
	   	 }

	   	 // Check if the argument is for listusers
	   	 else if (args[0].equalsIgnoreCase("listusers")) {
	   		 // Check if a valid number of arguments are given
	   		 if (args.length != 1) {
	   			 System.out.println("Error: only one argument needed for listusers");
	   		 }
	   		 // if valid, list the users
	   		 else {
	   			 control.listUsers();
	   		 }
	   	 }

	   	 // Check if the argument is for adduser
	   	 else if (args[0].equalsIgnoreCase("adduser")) {
	   		 // Check if a valid number of arguments are given
	   		 if (args.length != 3) {
	   			 System.out.println("Error: wrong number of arguments given");
	   			 System.out.println("adduser template: adduser <user id> \"<user name>\"");
	   		 }
	   		 // if valid, add the user
	   		 else {
	   			 control.addNewUser(args[1], args[2]);
	   		 }
	   	 }

	   	 // Check if the argument is for deleteuser
	   	 else if (args[0].equalsIgnoreCase("deleteuser")) {
	   		 // Check if a valid number of arguments are given
	   		 if (args.length != 2) {
	   			 System.out.println("Error: wrong number of arguments given");
	   			 System.out.println("deleteuser template: deleteuser <user id>");
	   		 }
	   		 // if valid, delete the user
	   		 else {
	   			 control.deleteUser(args[1]);
	   		 }
	   	 }

	   	 // Check if the argument is for login
	   	 else if (args[0].equalsIgnoreCase("login")) {
	   		 // Check if a valid number of arguments are given
	   		 if (args.length != 2) {
	   			 System.out.println("Error: wrong number of arguments given");
	   			 System.out.println("login template: login <user id>");
	   		 }
	   		 // if valid, login
	   		 else {
	   			 //**************
	   			 //need to check if user exists
	   			 //if he does then log in, if not throw error
	   			 //**************
	   			 try {
	   				 if(control.login(args[1]))
	   					 interactiveMode(control, args[1]);
	   				 else
	   					 return;
				} catch (IOException e) {
					e.printStackTrace();
				}
	   		 }
	   	 }

	   	 // Handles any invalid input
	   	 else {
	   		 System.out.println("Error: valid arguments are listusers, adduser, deleteuser ");
	   	 }
	   	 
	   	 control.logout();

	
	}
	
	
	
}