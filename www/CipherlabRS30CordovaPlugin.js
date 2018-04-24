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
        
        this.setEnableBinaryData = function(enable, callback) {
            cordova.exec(callback, function (err) {
			}, "CipherlabRS30CordovaPlugin", "setEnableBinaryData", [enable]);
        }

        this.getEnableBinaryData = function(callback) {
			cordova.exec(callback, function (err) {
			}, "CipherlabRS30CordovaPlugin", "getEnableBinaryData", []);
        }
    }

    module.exports = new EchoService();
}

CipherlabRS30CordovaPlugin(require, exports, module);

cordova.define("cordova/plugin/EchoService", CipherlabRS30CordovaPlugin);