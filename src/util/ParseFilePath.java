package util;

import java.io.File;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ParseFilePath {

	public static ArrayList<String> strToPath (String str) {
		
        ArrayList<String> temp = new ArrayList<String>();
        
        
        StringTokenizer st = new StringTokenizer(str,File.separator);
                   
        while(st.hasMoreTokens()) {
            temp.add(st.nextToken());
            temp.add(File.separator);
        }
        
        temp.remove(temp.size()-1);
        
        return temp;
}

}
