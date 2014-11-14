package chathura.research;

import java.io.InputStream;

import android.app.Activity;
import android.content.res.AssetManager;
import android.util.Log;

import com.srplab.www.starcore.StarCoreFactory;
import com.srplab.www.starcore.StarCoreFactoryPath;
import com.srplab.www.starcore.StarObjectClass;
import com.srplab.www.starcore.StarServiceClass;
import com.srplab.www.starcore.StarSrvGroupClass;

public class DataSender {

	
	private StarObjectClass python = null;
	private StarSrvGroupClass SrvGroup = null;
	private StarServiceClass Service = null;
	private StarCoreFactory starcore = null;
	
	public void LoadClients(Activity activity )
	{
		try {
			AssetManager assetManager = activity.getAssets();
			InputStream dataSource = assetManager.open("ClientsFactory.zip");
			StarCoreFactoryPath.Install(dataSource, "/data/data/"
					+ activity.getPackageName() + "/files", true);
		} catch (Exception e) {
			Log.e("Excption ClientSide OnCreate", e.getLocalizedMessage());
		}
		
	}
	
	public String SendData(Activity activity,String protocol,String serverIP,String dataSize,int port)
	{
		try {

				
			/*----init starcore----*/
			StarCoreFactoryPath.StarCoreCoreLibraryPath = "/data/data/"
					+ activity.getPackageName() + "/lib";
			StarCoreFactoryPath.StarCoreShareLibraryPath = "/data/data/"
					+ activity.getPackageName() + "/lib";

			starcore = StarCoreFactory.GetFactory();
			Service = starcore._InitSimple("test", "123", 0, 0);
			SrvGroup = (StarSrvGroupClass) Service
					._Get("_ServiceGroup");
			SrvGroup._InitRaw("python", Service);

			Service._DoFile("python", "/data/data/" + activity.getPackageName()
					+ "/files/ClientsFactory.py", "");
			python = Service._ImportRawContext("python", "", false, "");

		} catch (Exception e) {
			Log.e("Error", e.getMessage());
		}
		String result = (String) python._Call("main",protocol,serverIP,dataSize,port);

	//	tvResult.setText("Selected Prtocol is " + result);
		// SrvGroup._ClearService();
		starcore._ModuleClear();
		return result;
	}
	
	public String TCPSender()
	{
		
		return null;
	}
	
	public String SCTPSender()
	{
		
		return null;
	}
	
	public String DCCPSender()
	{
		
		return null;
	}
	
}
