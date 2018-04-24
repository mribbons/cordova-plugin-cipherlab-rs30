# cordova-plugin-cipherlab-rs30
Apache Cordova Barcode Scanner Plugin for Cipherlab RS30

## Supported Platforms
Cipherlab RS30 Scanner
(BarcodeAPI_V1_0_35.jar)

## Installation

```
cordova plugin add https://github.com/mribbons/cordova-plugin-cipherlab-rs30
```

# Sample Code

Initialise the reader manager and filters using initialise:

```
cordova.plugins.CipherlabRS30CordovaPlugin.initialise()
```

Subscribe to scans by providing a callback method:
(This will receive scans triggered by hardware button or software trigger (below)
```
cordova.plugins.CipherlabRS30CordovaPlugin.setReceiveScanCallback(function (data) {
    append("scan received: " + data);
});
```

Request a scan on button click event:
```
document.getElementById("scan_button").addEventListener('click', function () {
	cordova.plugins.CipherlabRS30CordovaPlugin.requestScan(function () {
		// This callback doesn't need to do anything, the setReceiveScanCallback callback is what receives the scan data.
	});
});
```

Clean up resources when our app exits:
```
window.onbeforeunload = function () {
	cordova.plugins.CipherlabRS30CordovaPlugin.destroy(function () { });
}
```

Full sample (eg index.js):
```
(function () {
    "use strict";

    document.addEventListener('deviceready', onDeviceReady.bind(this), false);

    function append(message) {
        var node = document.createElement("p");
        node.appendChild(document.createTextNode(message));
        document.body.appendChild(node);
    }

    async function onDeviceReady() {
        // Handle the Cordova pause and resume events
        document.addEventListener( 'pause', onPause.bind( this ), false );
        document.addEventListener('resume', onResume.bind(this), false);

        document.getElementById("scan_button").addEventListener('click', function () {
            cordova.plugins.CipherlabRS30CordovaPlugin.requestScan(function () {
                // MDR 30/11/2015 - This is just a placeholder callback. Results will be handled by setReceiveScanCallback() parameter below
            });
        });
        
        // TODO: Cordova has been loaded. Perform any initialization that requires Cordova here.
        await cordova.plugins.CipherlabRS30CordovaPlugin.initialise(async function () {

            append("init done");
            
            cordova.plugins.CipherlabRS30CordovaPlugin.setReceiveScanCallback(function (data, type) {
                append("scan received: " + data + "(" + type + ")");
            });

            // or alternatively, if your data contains binary data you can get an array of integers:
            await cordova.plugins.CipherlabRS30CordovaPlugin.setEnableBinaryData(true);
            cordova.plugins.CipherlabRS30CordovaPlugin.setReceiveScanCallback(function (data, type, binary) { 
                // process binary array
            }
        });

        window.onbeforeunload = function () {
            cordova.plugins.CipherlabRS30CordovaPlugin.destroy(function () { });
        }

        append("setup done");
    };

    function onPause() {
        // TODO: This application has been suspended. Save application state here.
    };

    function onResume() {
        // TODO: This application has been reactivated. Restore application state here.
    };
} )();
```

## TODO

Receive barcode types as part of ReceiveScanCallback

Implement scanner configuration support (ReaderOutputConfiguration)
