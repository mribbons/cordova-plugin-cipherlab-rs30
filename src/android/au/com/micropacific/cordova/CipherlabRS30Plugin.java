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
import org.apache.cordova.PluginResult;
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

/**
 * This class echoes a string called from JavaScript.
 */
public class CipherlabRS30Plugin extends CordovaPlugin {

	// IntentFilter is used to get the intent we need.
	private IntentFilter filter;
	
	// A very important class used to communicate with driver and  service. 
	private com.cipherlab.barcode.ReaderManager mReaderManager;
	private DataReceiver mDataReceiver;
	private CallbackContext receiveScanCallback;

	public CipherlabRS30Plugin()
	{
	}

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException 
	{

        //System.out.println("============== execute ===========");
        Log.v("CipherlabRS30Plugin", "============== execute ===========: " + action);

        if (action.equals("echo")) {
            String message = args.getString(0);
            this.echo(message, callbackContext);
            return true;
        }

		if (action.equals("initialise")) {
			this.initialise(callbackContext);
			return true;
		}
		
		if (action.equals("setReceiveScanCallback")) {
			Log.v("CipherlabRS30Plugin", " ==== setReceiveScanCallback ===");
			receiveScanCallback = callbackContext;
			
			if (callbackContext == null)
			{
				Log.v("CipherlabRS30Plugin", "callbackContext is null.");
			} else {
				Log.v("CipherlabRS30Plugin", "callbackContext is not null");
			}
			
			return true;
		}
		
		if (action.equals("requestScan"))
		{
			//Log.v("CipherlabRS30Plugin", "requestScan");
			
			if (mReaderManager != null)
			{
				mReaderManager.SoftScanTrigger();
			}
		
			return true;
		}
		
		if (action.equals("destroy"))
		{
		
			if (mReaderManager != null)
			{
				// Call this from window.onbeforeunload
				Log.v("CipherlabRS30Plugin", "destroy(): cleaning up.");
			
				cordova.getActivity().unregisterReceiver(mDataReceiver);
				mReaderManager.Release();
			
				mDataReceiver = null;
				mReaderManager = null;
			}
		
			return true;
		}
		
		Log.v("CipherlabRS30Plugin", "============== done   ===========: " + action);

        return false;
    }
	
	public void receieveScan(String data)
	{	
		PluginResult progressResult = new PluginResult(PluginResult.Status.OK, data);
		progressResult.setKeepCallback(true);
	
		if (receiveScanCallback == null)
		{
			Log.v("CipherlabRS30Plugin", "receiveScanCallback is null.");
		} else {
			receiveScanCallback.sendPluginResult(progressResult);
		}
	}

    private void echo(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }

	public void initialise(CallbackContext callbackContext)
	{
		Log.v("CipherlabRS30Plugin", "CipherlabRS30Plugin.initialise()");
		try
		{
			Log.v("CipherlabRS30Plugin", "com.cipherlab.barcode.GeneralString.Intent_SOFTTRIGGER_DATA: " + com.cipherlab.barcode.GeneralString.Intent_SOFTTRIGGER_DATA);
			if (cordova.getActivity() == null)
			{
				Log.v("CipherlabRS30Plugin", "cordova.getActivity() is null");
			} else {
				Log.v("CipherlabRS30Plugin", "cordova.getActivity() is something");
			}

			filter = new IntentFilter(); 
			filter.addAction(com.cipherlab.barcode.GeneralString.Intent_SOFTTRIGGER_DATA);
			filter.addAction(com.cipherlab.barcode.GeneralString.Intent_PASS_TO_APP);
			filter.addAction(com.cipherlab.barcode.GeneralString.Intent_READERSERVICE_CONNECTED);
			
			mReaderManager = ReaderManager.InitInstance(cordova.getActivity());
			mDataReceiver = new DataReceiver(this, this.mReaderManager);
			cordova.getActivity().registerReceiver(mDataReceiver, filter);

			Log.v("CipherlabRS30Plugin", "Got reader");
		} catch (Exception e)
		{
			StringWriter writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter( writer );
			e.printStackTrace( printWriter );
			printWriter.flush();

			String stackTrace = writer.toString();

			Log.v("CipherlabRS30Plugin", "Error starting reader manager: " + stackTrace);
		}

		Log.v("CipherlabRS30Plugin", "CipherlabRS30Plugin.initialise() Done");

		callbackContext.success();
	}
}
