var exec = require('cordova/exec');

var CipherlabRS30CordovaPlugin = function (require, exports, module) {

    function EchoService() {

        this.echo = function (str, callback) {
            cordova.exec(callback, function (err) {
                //alert("Something wrong");
                //alert("error: " + err);
                //callback('Nothing to echo.');
            }, "CipherlabRS30CordovaPlugin", "echo", [str]);
        }

        this.initialise = function (callback) {
            cordova.exec(callback, function (err) {
            }, "CipherlabRS30CordovaPlugin", "initialise", []);
        }

        this.destroy = function (callback) {
            cordova.exec(callback, function (err) {
            }, "CipherlabRS30CordovaPlugin", "destroy", []);
        }
		
		this.setReceiveScanCallback = function (callback) {
			cordova.exec(callback, function (err) {
			}, "CipherlabRS30CordovaPlugin", "setReceiveScanCallback", []);
		}
		
		this.requestScan = function(callback) {
			cordova.exec(callback, function (err) {
			}, "CipherlabRS30CordovaPlugin", "requestScan", []);
		}
    }

    module.exports = new EchoService();
}

CipherlabRS30CordovaPlugin(require, exports, module);

cordova.define("cordova/plugin/EchoService", CipherlabRS30CordovaPlugin);