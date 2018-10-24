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
