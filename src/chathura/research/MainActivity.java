package chathura.research;

import java.io.IOException;
import java.io.InputStream;

import com.srplab.www.starcore.StarCoreFactory;
import com.srplab.www.starcore.StarCoreFactoryPath;
import com.srplab.www.starcore.StarObjectClass;
import com.srplab.www.starcore.StarParaPkgClass;
import com.srplab.www.starcore.StarServiceClass;
import com.srplab.www.starcore.StarSrvGroupClass;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	TextView txt;
	
	final int NONE = 0;
	final int RELIABLE = 1;
	final int UNRELIABLE = 2;
	final int HIGH_FREQUENCY = 4;
	final int LOW_FREQUENCY = 8;
	final int LARGE_DATA = 16;
	final int MEDIUM_DATA = 32;
	final int SMALL_DATA = 64;
	final int VERY_SMALL_DATA = 128;
	final int HIGH_PRIORITY = 256;
	final int LOW_PRIORITY = 512;
	
	int feature_list=0;
	
	public static final String TAG = MainActivity.class.getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//txt = (TextView) findViewById(R.id.txt);
		
		Button btn1=(Button) findViewById(R.id.buttonAPISelection);
		Button btn2=(Button) findViewById(R.id.buttonClient);
		Button btn3=(Button) findViewById(R.id.buttonServers);
		
		btn1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(MainActivity.this,PrtocolSelectorTest.class);
				startActivity(i);
				
				
			}
		});
		
	btn2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(MainActivity.this,ClientSide.class);
				startActivity(i);
				
				
			}
		});
		
	
	btn3.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent i=new Intent(MainActivity.this,ServerSide.class);
			startActivity(i);
			
			
		}
	});
	
	
}
	
	
//	public void click(View v){
//		 try{
//             AssetManager assetManager = getAssets();
//             InputStream dataSource = assetManager.open("pycode.zip");
//             StarCoreFactoryPath.Install(dataSource, "/data/data/"+getPackageName()+"/files", true);
//		 }
//	     catch(IOException e ){
//	     }                       
//			 
//	     /*----init starcore----*/
//			 
//		 StarCoreFactoryPath.StarCoreCoreLibraryPath = "/data/data/"+getPackageName()+"/lib";
//	     StarCoreFactoryPath.StarCoreShareLibraryPath = "/data/data/"+getPackageName()+"/lib";
//	     StarCoreFactory starcore= StarCoreFactory.GetFactory();
//	     StarServiceClass Service=starcore._InitSimple("test","123",0,0);
//	     StarSrvGroupClass SrvGroup = (StarSrvGroupClass)Service._Get("_ServiceGroup");
//	     SrvGroup._InitRaw("python",Service);
//	     
//	     Service._DoFile("python", "/data/data/"+getPackageName()+"/files/ProtocolSelector.py", "");
//	     
//	     StarObjectClass python = Service._ImportRawContext("python","",true,"");
//	    
//	     feature_list=this.RELIABLE|this.LARGE_DATA;
//	     
//	     String result = (String) python._Call("main",feature_list);	     
//	     txt.setText("Output : "+ result);
//
//	     
//	     SrvGroup._ClearService();
//	     starcore._ModuleExit();
//	}
	
}
