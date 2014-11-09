package chathura.research;

import java.io.IOException;
import java.io.InputStream;
import java.util.RandomAccess;

import com.srplab.www.starcore.StarCoreFactory;
import com.srplab.www.starcore.StarCoreFactoryPath;
import com.srplab.www.starcore.StarObjectClass;
import com.srplab.www.starcore.StarServiceClass;
import com.srplab.www.starcore.StarSrvGroupClass;

import android.R.bool;
import android.support.v7.app.ActionBarActivity;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

public class PrtocolSelectorTest extends ActionBarActivity {

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

	int feature_list = 0;

	RadioButton rdbtnRelaible, rdbtnUnReliable, rdbtnLarge, rdbtnMedium,
			rdbtnSmall, rdbtnVerySmall, rdbtnHighFreq, rdbtnLowFreq;
	Button btnSelectProtocol;

	StarObjectClass python = null;
	StarSrvGroupClass SrvGroup = null;
	StarServiceClass Service = null;
	StarCoreFactory starcore = null;

	TextView tvResult;

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_prtocol_selector_test);

		rdbtnRelaible = (RadioButton) findViewById(R.id.radioButtonReliable);
		rdbtnUnReliable = (RadioButton) findViewById(R.id.radioButtonUnReliable);
		rdbtnLarge = (RadioButton) findViewById(R.id.radioButtonLarge);
		rdbtnMedium = (RadioButton) findViewById(R.id.radioButtonMedium);
		rdbtnSmall = (RadioButton) findViewById(R.id.radioButtonSmall);
		rdbtnVerySmall = (RadioButton) findViewById(R.id.radioButtonVerySamll);
		rdbtnHighFreq = (RadioButton) findViewById(R.id.radioButtonHighFreq);
		rdbtnLowFreq = (RadioButton) findViewById(R.id.radioButtonLowFreq);
		btnSelectProtocol = (Button) findViewById(R.id.buttonSelectProtocol);
		tvResult = (TextView) findViewById(R.id.textViewResult);

		try {
			AssetManager assetManager = getAssets();
			InputStream dataSource = assetManager.open("pycode.zip");
			StarCoreFactoryPath.Install(dataSource, "/data/data/"
					+ getPackageName() + "/files", true);
		} catch (Exception e) {
			Log.e("Excption OnCreate", e.getLocalizedMessage());
		}

		rdbtnRelaible
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						if (isChecked) {
							feature_list = feature_list | RELIABLE;
						} else
							feature_list = feature_list - RELIABLE;

					}
				});

		rdbtnUnReliable
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						if (isChecked) {
							feature_list = feature_list | UNRELIABLE;
						} else
							feature_list = feature_list - UNRELIABLE;

					}
				});

		rdbtnLarge
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						if (isChecked) {
							feature_list = feature_list | LARGE_DATA;
						} else
							feature_list = feature_list - LARGE_DATA;

					}
				});

		btnSelectProtocol.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
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
							+ "/files/ProtocolSelector.py", "");
					python = Service._ImportRawContext("python", "", false, "");

				} catch (Exception e) {
					Log.e("Error", e.getMessage());
				}
				String result = (String) python._Call("main", feature_list);

				tvResult.setText("Selected Prtocol is " + result);
				// SrvGroup._ClearService();
				starcore._ModuleClear();

			}

		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.prtocol_selector_test, menu);
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
