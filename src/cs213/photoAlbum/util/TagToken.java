package cs213.photoAlbum.util;

public class TagToken {

		private String type;
		private String data;
		
		
		public TagToken(String data){
			this.type = null;
			this.data = data;
		}
		
		public TagToken(String type, String data){
			
			this.type = type;
			this.data = data;
		}
		
		public String getType(){
			
			return this.type;
		}
		
		public String getData(){
			
			return this.data;
		}

		public boolean isFormatted(){
		
		if(this.type == null){
			return false;
		}else{
			return true;
		}
	}
}
