/*
The MIT License (MIT)
Copyright (c) 2015 Michael Ribbons
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
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
            settings.enableKeyboardEmulation = KeyboardEmulationType.None;
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