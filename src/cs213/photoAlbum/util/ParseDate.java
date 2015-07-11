package cs213.photoAlbum.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

public class ParseDate {
	
    public static Calendar strToDate(String time) {
    	
    	
     	 ArrayList<Integer> date = new ArrayList<Integer>();
     	 
     	 StringTokenizer st = new StringTokenizer(time,"/-:");
     	 
     	 Calendar newDate = new GregorianCalendar();
     	 newDate.setLenient(false);
     	 
     	 while(st.hasMoreTokens()) {
     		 String temp = st.nextToken();
     		 if(isInteger(temp)){
     			date.add(Integer.parseInt(temp));
     		 }else{
     			 return null;
     		 }
          	 
      	 }
     	 
     	 if(date.size() != 6){
     		 return null;
     	 }else{
     		 try{
     			 newDate.set(date.get(2), date.get(0)-1, date.get(1), date.get(3), date.get(4), date.get(5));
     			 newDate.getTime();
     			 return newDate;
     		 }catch(Exception e){
     			 return null;
     		 }
     		 
     	 }
      }
    
    public static boolean isInteger(String s) {
        try { 
            Integer.parseInt(s); 
        } catch(NumberFormatException e) { 
            return false; 
        }
        return true;
    }

}


