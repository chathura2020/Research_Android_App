package chathura.research;

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
import android.widget.EditText;

public class ClientSide extends Activity {

	EditText etServerIP;
	Button btnSendToUDP, btnSendToTCP, btnSendToDCCP;

	String serverIP = "0.0.0.0";

	SocketManager dataSender;

	
	GlobalClass gloabClass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_client_side);

		dataSender = new SocketManager();
		dataSender.LoadClients(this);

		etServerIP = (EditText) findViewById(R.id.editTextServerIP);
		btnSendToUDP = (Button) findViewById(R.id.buttonSendToUDPServer);
		btnSendToTCP = (Button) findViewById(R.id.buttonSendToTCPServer);
		btnSendToDCCP = (Button) findViewById(R.id.buttonSendToDCCPServer);
		

		gloabClass=GlobalClass.getInstance();
		
		
		btnSendToUDP.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				
				gloabClass.setServerIP(etServerIP.getText().toString());
			//	new AsyncSendUDP().execute();
//				String result = dataSender.SendData(ClientSide.this, "UDP",
//						etServerIP.getText().toString(), "Large", 20000);
//
//				Log.d("Result UDP", result);
			}
		});

		btnSendToTCP.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

//				tcpClient.setIpAddress("192.168.123.4");
//				tcpClient.setPort(20001);
//				
				
				
//				String result = dataSender.SendData(ClientSide.this, "TCP",
//						etServerIP.getText().toString(), "Large", 20001);
//
//				Log.d("Result TCP", result);
			}
		});

		btnSendToDCCP.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				
				
				String result = dataSender.SendData(ClientSide.this, "DCCP",
						etServerIP.getText().toString(), "Large", 20002);

				//Log.d("Result DCCP", result);
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
