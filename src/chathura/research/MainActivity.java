package chathura.research;

import java.io.IOException;
import java.io.InputStream;

import com.srplab.www.starcore.StarCoreFactory;
import com.srplab.www.starcore.StarCoreFactoryPath;
import com.srplab.www.starcore.StarObjectClass;
import com.srplab.www.starcore.StarParaPkgClass;
import com.srplab.www.starcore.StarServiceClass;
import com.srplab.www.starcore.StarSrvGroupClass;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	TextView txt;
	public static final String TAG = MainActivity.class.getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		txt = (TextView) findViewById(R.id.txt);	
	}
	
	public void click(View v){
		 try{
             AssetManager assetManager = getAssets();
             InputStream dataSource = assetManager.open("pycode.zip");
             StarCoreFactoryPath.Install(dataSource, "/data/data/"+getPackageName()+"/files", true);
		 }
	     catch(IOException e ){
	     }                       
			 
	     /*----init starcore----*/
			 
		 StarCoreFactoryPath.StarCoreCoreLibraryPath = "/data/data/"+getPackageName()+"/lib";
	     StarCoreFactoryPath.StarCoreShareLibraryPath = "/data/data/"+getPackageName()+"/lib";
	     StarCoreFactory starcore= StarCoreFactory.GetFactory();
	     StarServiceClass Service=starcore._InitSimple("test","123",0,0);
	     StarSrvGroupClass SrvGroup = (StarSrvGroupClass)Service._Get("_ServiceGroup");
	     SrvGroup._InitRaw("python",Service);
	     
	     Service._DoFile("python", "/data/data/"+getPackageName()+"/files/ProtocolSelector.py", "");
	     
	     StarObjectClass python = Service._ImportRawContext("python","",true,"");
	     String result = (String) python._Call("main");	     
	     txt.setText("Output : "+ result);

	     
	     SrvGroup._ClearService();
	     starcore._ModuleExit();
	}
	
}
