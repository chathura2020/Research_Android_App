package chathura.research;

public class GlobalClass {

	
	 private static GlobalClass singleton = new GlobalClass( );
	 
	 private  String  protocol;
	 private String serverIP;
	 
	  
	   public String getServerIP() {
		return serverIP;
	}


	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}


	private GlobalClass(){ }
	   
	
	   public static GlobalClass getInstance( ) {
	      return singleton;
	   }


	public  String getProtocol() {
		return this.protocol;
	}


	public  void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	 
	   
	
}
