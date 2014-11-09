package chathura.research;

import java.io.InputStream;

import com.srplab.www.starcore.StarCoreFactory;
import com.srplab.www.starcore.StarCoreFactoryPath;
import com.srplab.www.starcore.StarObjectClass;
import com.srplab.www.starcore.StarServiceClass;
import com.srplab.www.starcore.StarSrvGroupClass;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ClientSide extends Activity {

	EditText etServerIP;
	Button btnConnectToServer;
	
	StarObjectClass python = null;
	StarSrvGroupClass SrvGroup = null;
	StarServiceClass Service = null;
	StarCoreFactory starcore = null;
	
	String serverIP="0.0.0.0";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_client_side);
		
		
		try {
			AssetManager assetManager = getAssets();
			InputStream dataSource = assetManager.open("ClientsFactory.zip");
			StarCoreFactoryPath.Install(dataSource, "/data/data/"
					+ getPackageName() + "/files", true);
		} catch (Exception e) {
			Log.e("Excption ClientSide OnCreate", e.getLocalizedMessage());
		}
		
		
		etServerIP=(EditText)findViewById(R.id.editTextServerIP);
		btnConnectToServer=(Button)findViewById(R.id.buttonConnectToServer);
		
		
		btnConnectToServer.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				try {

					serverIP=etServerIP.getText().toString();	
					/*----init starcore----*/
					StarCoreFactoryPath.StarCoreCoreLibraryPath = "/data/data/"
							+ getPackageName() + "/lib";
					StarCoreFactoryPath.StarCoreShareLibraryPath = "/data/data/"
							+ getPackageName() + "/lib";

					starcore = StarCoreFactory.GetFactory();
					Service = starcore._InitSimple("test", "123", 0, 0);
					SrvGroup = (StarSrvGroupClass) Service
							._Get("_ServiceGroup");
					SrvGroup._InitRaw("python", Service);

					Service._DoFile("python", "/data/data/" + getPackageName()
							+ "/files/ClientsFactory.py", "");
					python = Service._ImportRawContext("python", "", false, "");

				} catch (Exception e) {
					Log.e("Error", e.getMessage());
				}
				String result = (String) python._Call("main","TCP","Large");

			//	tvResult.setText("Selected Prtocol is " + result);
				// SrvGroup._ClearService();
				starcore._ModuleClear();

				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.client_side, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
