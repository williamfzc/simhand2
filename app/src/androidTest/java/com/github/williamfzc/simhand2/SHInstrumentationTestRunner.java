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

import java.net.InetAddress;

/*
load arguments from command at the beginning
 */
public class SHInstrumentationTestRunner extends AndroidJUnitRunner {
    private static final String TAG = "SHRunner";

    @Override
    public void onCreate(Bundle arguments) {
        SHGlobal.port = parsePort(arguments);
        SHGlobal.parentIP = parsePCAddress(arguments);
        SHGlobal.localIP = SHUtils.getLocalIP();

        Log.i(TAG, "port: " + SHGlobal.port);
        Log.i(TAG, "local address: " + SHGlobal.localIP);
        Log.i(TAG, "pc address: " + SHGlobal.parentIP);

        super.onCreate(arguments);
    }

    private Integer parsePort(Bundle arguments) {
        String port = arguments.getString("port");
        if (port != null && !"".equals(port)) {
            return Integer.valueOf(port);
        }
        // default
        return 8080;
    }

    private String parsePCAddress(Bundle arguments) {
        String pcAddress = arguments.getString("pc");
        if (pcAddress != null && !"".equals(pcAddress)) {
            return pcAddress;
        }
        // default
        return "";
    }
}
