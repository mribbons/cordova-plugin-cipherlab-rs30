# cordova-plugin-cipherlab-rs30
Apache Cordova Barcode Scanner Plugin for Cipherlab RS30

## Supported Platforms
Cipherlab RS30 Scanner
(BarcodeAPI_V1_0_31.jar)

## Legacy Note
This branch supports a legacy version of BarcodeAPI.jar (As provided by Cipherlab)

If you get issues like [this](https://github.com/mribbons/cordova-plugin-cipherlab-rs30/issues/1) then you should be using a later version.

I apologise for not being able to provide device build numbers as I don't have devices available to test with. Contributions are welcomed.

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

    function onDeviceReady() {
        // Handle the Cordova pause and resume events
        document.addEventListener( 'pause', onPause.bind( this ), false );
        document.addEventListener('resume', onResume.bind(this), false);

        document.getElementById("scan_button").addEventListener('click', function () {
            cordova.plugins.CipherlabRS30CordovaPlugin.requestScan(function () {
                // MDR 30/11/2015 - This is just a placeholder callback. Results will be handled by setReceiveScanCallback() parameter below
            });
        });
        
        // TODO: Cordova has been loaded. Perform any initialization that requires Cordova here.
        cordova.plugins.CipherlabRS30CordovaPlugin.initialise(function () {

            append("init done");
            
            cordova.plugins.CipherlabRS30CordovaPlugin.setReceiveScanCallback(function (data) {
                append("scan received: " + data);
            });

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