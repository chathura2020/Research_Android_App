package chathura.research;

import java.io.InputStream;

import com.srplab.www.starcore.StarCoreFactory;
import com.srplab.www.starcore.StarCoreFactoryPath;
import com.srplab.www.starcore.StarObjectClass;
import com.srplab.www.starcore.StarServiceClass;
import com.srplab.www.starcore.StarSrvGroupClass;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainDataType extends Activity {

	RadioGroup rdbGrpData;

	Button btnProtocolSelector;

	GlobalClass gloabClass;

	final int NONE = 0;
	final int RELIABLE = 1;
	final int UNRELIABLE = 2;
	final int HIGH_FREQUENCY = 4;
	final int TRIGGERED_FREQUENCY = 8;
	final int URGENT_FREQUENCY = 16;
	final int LARGE_DATA = 32;
	final int MEDIUM_DATA = 64;
	final int SMALL_DATA = 128;
	final int VERY_SMALL_DATA = 256;
	final int HIGH_PRIORITY = 512;
	final int LOW_PRIORITY = 1024;

	int feature_list = 0;

	ProtocolSelector protocolSelector;
	SocketManager dataSender;

	StarObjectClass python = null;
	StarSrvGroupClass SrvGroup = null;
	StarServiceClass Service = null;
	StarCoreFactory starcore = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_data_type);

		rdbGrpData = (RadioGroup) findViewById(R.id.rbtnGrpData);
		
		btnProtocolSelector = (Button) findViewById(R.id.buttonProtocolSelector);

		gloabClass = GlobalClass.getInstance();
		protocolSelector = new ProtocolSelector(this);
		dataSender = new SocketManager();

		btnProtocolSelector.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				 gloabClass.setProtocol(protocolSelector.SelectProtocol(feature_list));

				 Toast.makeText(MainDataType.this,"Selected Protocol is "+gloabClass.getProtocol(),Toast.LENGTH_SHORT).show();
				 
				 Intent i=new Intent(MainDataType.this, DataSender.class);
				 startActivity(i);
				

			}
		});


		rdbGrpData
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						// TODO Auto-generated method stub
						feature_list = 0;
						switch (checkedId) {
						case R.id.rbtnGPSData:

							feature_list = VERY_SMALL_DATA + UNRELIABLE
									+ HIGH_FREQUENCY;
							break;

						case R.id.rbtnWifiData:

							feature_list = VERY_SMALL_DATA + UNRELIABLE
									+ HIGH_FREQUENCY;
							break;

						case R.id.rbtnObstacleFixedData:

							feature_list = MEDIUM_DATA + RELIABLE
									+ TRIGGERED_FREQUENCY;
							break;

						case R.id.rbtnObstacleNonStaionaryData:

							feature_list = SMALL_DATA + RELIABLE
									+ URGENT_FREQUENCY;
							break;

						case R.id.rbtnMapData:

							feature_list = LARGE_DATA + RELIABLE
									+ TRIGGERED_FREQUENCY;
							break;

						case R.id.rbtnUserLocation:

							feature_list = VERY_SMALL_DATA + UNRELIABLE
									+ HIGH_FREQUENCY;
							break;

						case R.id.rbtnSensorData:

							feature_list = VERY_SMALL_DATA + UNRELIABLE
									+ HIGH_FREQUENCY;
							break;

						case R.id.rbtnInstrction:

							feature_list = VERY_SMALL_DATA + RELIABLE
									+ URGENT_FREQUENCY;
							break;

						}
						gloabClass.setFeature_list(0);
						gloabClass.setFeature_list(feature_list);

					}
				});

	}

	private class AsyncSendData extends AsyncTask<Void, String, String> {

		@Override
		protected String doInBackground(Void... params) {

			Log.d("doInBackground", "Passed 1");
			Log.d("Protocol", gloabClass.getProtocol());

			
			return null;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_data_type, menu);
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
