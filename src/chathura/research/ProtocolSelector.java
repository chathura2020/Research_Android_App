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

public class ProtocolSelector {

	StarObjectClass python = null;
	StarSrvGroupClass SrvGroup = null;
	StarServiceClass Service = null;
	StarCoreFactory starcore = null;
	
	Activity activity;
	public ProtocolSelector(Activity activity) {
		// TODO Auto-generated constructor stub
		try {
			this.activity=activity;
			
			AssetManager assetManager = this.activity.getAssets();
			InputStream dataSource = assetManager.open("pycode.zip");
			StarCoreFactoryPath.Install(dataSource, "/data/data/"
					+ this.activity.getPackageName() + "/files", true);
			
			StarCoreFactoryPath.StarCoreCoreLibraryPath = "/data/data/"
					+ activity.getPackageName() + "/lib";
			StarCoreFactoryPath.StarCoreShareLibraryPath = "/data/data/"
					+ activity.getPackageName() + "/lib";

			starcore = StarCoreFactory.GetFactory();
			Service = starcore._InitSimple("potocolSelector", "123", 0, 0);
		} catch (Exception e) {
			Log.e("Excption OnCreate", e.getLocalizedMessage());
		}
		
	}
	
	public String SelectProtocol(int featureList)
	{
		try {

			/*----init starcore----*/
			
			SrvGroup = (StarSrvGroupClass) Service
					._Get("_ServiceGroup");
			SrvGroup._InitRaw("python", Service);

			Service._DoFile("python", "/data/data/" + activity.getPackageName()
					+ "/files/ProtocolSelector.py", "");
			python = Service._ImportRawContext("python", "", false, "");
			
			
				
		} catch (Exception e) {
			Log.e("Error", e.getMessage());
		}
		String result = (String) python._Call("mainProtocol", featureList);
		//starcore._ModuleExit();
		//SrvGroup._ClearService();
		return result;
	}
	
	public String SendData(Activity activity,String protocol,String serverIP,String dataSize,int port)
	{
		try {

				
			/*----init starcore----*/
//			StarCoreFactoryPath.StarCoreCoreLibraryPath = "/data/data/"
//					+ activity.getPackageName() + "/lib";
//			StarCoreFactoryPath.StarCoreShareLibraryPath = "/data/data/"
//					+ activity.getPackageName() + "/lib";
//
//			starcore = StarCoreFactory.GetFactory();
//			Service = starcore._InitSimple("DataSender", "123", 0, 0);
			SrvGroup = (StarSrvGroupClass) Service
					._Get("_ServiceGroup");
			SrvGroup._InitRaw("python", Service);

			Service._DoFile("python", "/data/data/" + activity.getPackageName()
					+ "/files/ProtocolSelector.py", "");
			python = Service._ImportRawContext("python", "", false, "");

		} catch (Exception e) {
			Log.e("Error", e.getMessage());
		}
		String result = (String) python._Call("mainClient",protocol,serverIP,port);

	//	tvResult.setText("Selected Prtocol is " + result);
		// SrvGroup._ClearService();
		starcore._ModuleClear();
		return result;
	}
	
	
	
}
