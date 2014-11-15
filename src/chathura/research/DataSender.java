package chathura.research;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DataSender extends Activity {

	TextView tvSelectedProtocol, tvServerRespond;
	Button btnSendDataToServer;
	EditText etServerIP;
	
	GlobalClass gloabClass;
	SocketManager socketManager;

	final int LARGE_DATA = 32;
	final int MEDIUM_DATA = 64;
	final int SMALL_DATA = 128;
	final int VERY_SMALL_DATA = 256;
	
	

	String serverRespond;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_data_sender);

		tvSelectedProtocol = (TextView) findViewById(R.id.textViewSelectedProtocol);
		tvServerRespond = (TextView) findViewById(R.id.textViewServerRespond);
		btnSendDataToServer = (Button) findViewById(R.id.buttonSendData);
		etServerIP=(EditText)findViewById(R.id.editTextMainServerIP);
		
		gloabClass = GlobalClass.getInstance();
		socketManager = new SocketManager();

		socketManager.LoadClients(this);

		tvSelectedProtocol.setText("Selected Protocol is "
				+ gloabClass.getProtocol());

		btnSendDataToServer.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				gloabClass.setServerIP(etServerIP.getText().toString());
				
				if (gloabClass.getProtocol().equals("UDP")) {

					Log.d("doInBackground", "Passed 2");

					Log.d("If",
							String.valueOf((gloabClass.feature_list & LARGE_DATA) == LARGE_DATA));

					if ((gloabClass.feature_list & LARGE_DATA) == LARGE_DATA) {

						serverRespond = socketManager.SendData(DataSender.this,
								"UDP", gloabClass.getServerIP(), "Large", 20000);
					} else if ((gloabClass.feature_list & MEDIUM_DATA) == MEDIUM_DATA) {

						serverRespond = socketManager.SendData(DataSender.this,
								"UDP", gloabClass.getServerIP(), "Medium",
								20000);
					} else if ((gloabClass.feature_list & SMALL_DATA) == SMALL_DATA) {
						serverRespond = socketManager.SendData(DataSender.this,
								"UDP", gloabClass.getServerIP(), "Small", 20000);
					} else if ((gloabClass.feature_list & VERY_SMALL_DATA) == VERY_SMALL_DATA) {

						serverRespond = socketManager.SendData(DataSender.this,
								"UDP", gloabClass.getServerIP(), "VerySmall",
								20000);
					}

				} else if (gloabClass.getProtocol().equals("TCP")) {

					Log.d("doInBackground", "Passed 3");
					if ((gloabClass.feature_list & LARGE_DATA) == LARGE_DATA) {
						serverRespond = socketManager.SendData(DataSender.this,
								"TCP", gloabClass.getServerIP(), "Large", 20001);
					} else if ((gloabClass.feature_list & MEDIUM_DATA) == MEDIUM_DATA) {
						serverRespond = socketManager.SendData(DataSender.this,
								"TCP", gloabClass.getServerIP(), "Medium",
								20001);
					} else if ((gloabClass.feature_list & SMALL_DATA) == SMALL_DATA) {
						serverRespond = socketManager.SendData(DataSender.this,
								"TCP", gloabClass.getServerIP(), "Small", 20001);
					} else if ((gloabClass.feature_list & VERY_SMALL_DATA) == VERY_SMALL_DATA) {
						serverRespond = socketManager.SendData(DataSender.this,
								"TCP", gloabClass.getServerIP(), "VerySmall",
								20001);
					}
				} else if (gloabClass.getProtocol() == "DCCP") {

					Log.d("doInBackground", "Passed 4");
					 Toast.makeText(DataSender.this,"DCCP Not Supported in Andorid",
					 Toast.LENGTH_SHORT).show();
				} else if (gloabClass.getProtocol() == "SCTP") {

					Log.d("doInBackground", "Passed 5");
					 Toast.makeText(DataSender.this,"SCTP Not Supported in Andorid",
					 Toast.LENGTH_SHORT).show();
				}

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.data_sender, menu);
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
