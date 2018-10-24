/*
MIT License

Copyright (c) 2018 William Feng

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
package com.github.williamfzc.simhand2;

import android.os.Bundle;
import android.support.test.runner.AndroidJUnitRunner;
import android.util.Log;

public class SHInstrumentationTestRunner extends AndroidJUnitRunner {
    private static final String TAG = "SHRunner";

    // default to 8080
    private static String port;
    // IP address (PC which connected to android
    private static String parentIP;

    public static String getPort() {
        if (port == null) {
            return "8080";
        }
        return port;
    }

    public static String getParentIP() {
        if (parentIP == null) {
            return "127.0.0.1";
        }
        return parentIP;
    }

    @Override
    public void onCreate(Bundle arguments) {
        if (arguments != null) {
            port = arguments.getString("port");
            parentIP = arguments.getString("parentIP");
        }
        Log.i(TAG, "port: " + port);
        Log.i(TAG, "parent pc's ip: " + parentIP);
        super.onCreate(arguments);
    }
}
