package cs213.photoAlbum.util;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class ParseTagString {

	public static TagToken parseSearchString(String searchString){
	
		
		ArrayList<String> output = new ArrayList<String>();
		
		StringTokenizer st = new StringTokenizer(searchString, ":");

		while(st.hasMoreTokens()){
			output.add(st.nextToken());
		}
		

		
		if(output.size()==1){
			return new TagToken(output.get(0));
		}
		else if(output.size()==2){
			return new TagToken(output.get(0), output.get(1));
		}
		else
			return null;
	}
	
	
	public static String printSearchString(ArrayList<TagToken> tags){
		
		String output = "";
		
		for(TagToken tag : tags){
			if(tag.isFormatted()){
				output += tag.getType() + ":" + tag.getData() + ", ";
			}else{
				output += tag.getData() + ", ";
			}
			
		}
		output = output.replaceAll(", $", "");
		
		return output;
	}
}
