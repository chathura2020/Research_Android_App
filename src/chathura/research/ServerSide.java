package chathura.research;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.srplab.www.starcore.StarCoreFactory;
import com.srplab.www.starcore.StarCoreFactoryPath;
import com.srplab.www.starcore.StarObjectClass;
import com.srplab.www.starcore.StarServiceClass;
import com.srplab.www.starcore.StarSrvGroupClass;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.net.wifi.*;

public class ServerSide extends Activity {

	
	StarObjectClass python = null;
	StarSrvGroupClass SrvGroup = null;
	StarServiceClass Service = null;
	StarCoreFactory starcore = null;
	
	String ipAddress,port=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_server_side);
		
		 try {
			Process process = Runtime.getRuntime().exec("su");
			  DataOutputStream outputStream = new DataOutputStream(process.getOutputStream()); 
		        DataInputStream inputStream = new DataInputStream(process.getInputStream());

		        outputStream.writeBytes("Super User" + "\n");
		        outputStream.flush();

		        outputStream.writeBytes("exit\n"); 
		        outputStream.flush(); 
		        process.waitFor();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Button btnUDP=(Button)findViewById(R.id.buttonUDPServer);
		Button btnTCP=(Button)findViewById(R.id.buttonTCPServer);
		TextView tvLog=(TextView)findViewById(R.id.textViewLog);
		
		WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		int ip = wifiInfo.getIpAddress();
		 ipAddress = intToIp(ip);
		
		
		
		try {
			AssetManager assetManager = getAssets();
			InputStream dataSource = assetManager.open("UDPServer.zip");
			StarCoreFactoryPath.Install(dataSource, "/data/data/"
					+ getPackageName() + "/files", true);
		} catch (Exception e) {
			Log.e("Excption OnCreate", e.getLocalizedMessage());
		}
		
		
		btnUDP.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new AsyncUDPServer().execute();
			}
		});
		
	}

 private class	AsyncUDPServer extends AsyncTask<String, String, String> 
 {

	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		
		try {

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
					+ "/files/UDPServer.py", "");
			python = Service._ImportRawContext("python", "", false, "");

		} catch (Exception e) {
			Log.e("Error", e.getMessage());
		}
		String result = (String) python._Call("main",ipAddress,20001);
		
	
		// SrvGroup._ClearService();
		starcore._ModuleClear();
		
		
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}
	 
 }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.server_side, menu);
		return true;
	}

	public String intToIp(int i) {

		// return ((i >> 24 ) & 0xFF ) + "." +
		// ((i >> 16 ) & 0xFF) + "." +
		// ((i >> 8 ) & 0xFF) + "." +
		// ( i & 0xFF) ;
		   
		return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
				+ "." + ((i >> 24) & 0xFF);

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
