package au.com.micropacific.cordova;

import com.cipherlab.barcode.*;
import com.cipherlab.barcode.decoder.*;
import com.cipherlab.barcode.decoderparams.*;
import com.cipherlab.barcodebase.*;

import android.util.Log;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.*;

/// Create a broadcast object to receive the intent coming from service.
public class DataReceiver extends BroadcastReceiver {

	private ReaderManager mReaderManager;
	private CipherlabRS30Plugin plugin;

	public DataReceiver(CipherlabRS30Plugin _plugin, ReaderManager _ReaderManager)
	{
		this.mReaderManager = _ReaderManager;
		this.plugin = _plugin;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// If intent of the Intent_SOFTTRIGGER_DATA string is received
		if (intent.getAction().equals(GeneralString.Intent_SOFTTRIGGER_DATA)) {
				
			// fetch the data within the intent
			String data = intent.getStringExtra(GeneralString.BcReaderData);
				
			// display the fetched data
			//e1.setText(data);
			Log.v("CipherlabRS30Plugin", "got data, 1: " + data);
			this.plugin.receieveScan(data);
		}else if (intent.getAction().equals(GeneralString.Intent_PASS_TO_APP)){
				
			// fetch the data within the intent
			String data = intent.getStringExtra(GeneralString.BcReaderData);
				
			// display the fetched data
			//e1.setText(data);
			Log.v("CipherlabRS30Plugin", "got data, 2: " + data);
			this.plugin.receieveScan(data);
				
		}else if(intent.getAction().equals(GeneralString.Intent_READERSERVICE_CONNECTED)){
				
			BcReaderType myReaderType =  mReaderManager.GetReaderType();	
			//e1.setText(myReaderType.toString());

            ReaderOutputConfiguration settings = new ReaderOutputConfiguration();
            mReaderManager.Get_ReaderOutputConfiguration(settings);
            settings.enableKeyboardEmulation = Enable_State.FALSE;
            mReaderManager.Set_ReaderOutputConfiguration(settings);
			
			
			Log.v("CipherlabRS30Plugin", "got data, 3");

			/*NotificationParams settings = new NotificationParams();
			mReaderManager.Get_NotificationParams(settings);
				
			ReaderOutputConfiguration settings2 = new ReaderOutputConfiguration();
			mReaderManager.Get_ReaderOutputConfiguration(settings2);
			*/
		}

	}
};