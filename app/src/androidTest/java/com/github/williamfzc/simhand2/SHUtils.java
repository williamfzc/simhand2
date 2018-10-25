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

import android.util.Log;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Map;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SHUtils {
    public static String TAG = "SHUtils";

    public static String getParamFromMap(Map<String, String> targetMap, String targetFlag, String defaultFlag) {
        if (!targetMap.containsKey(targetFlag)) {
            return defaultFlag;
        }
        return targetMap.get(targetFlag);
    }

    public static boolean isNumeric(String s) {
        if (s == null || "".equals(s)) {
            return false;
        }
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    // TODO: temp design
    public static Integer getResponseCode(boolean b) {
        if (b) {
            return 0;
        } else {
            return 1;
        }
    }

    public static String getLocalIP() {
        try {
            for (Enumeration<NetworkInterface> enNetI = NetworkInterface
                    .getNetworkInterfaces(); enNetI.hasMoreElements(); ) {
                NetworkInterface netI = enNetI.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = netI
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (inetAddress instanceof Inet4Address && !inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean authDevice() {
        String deviceID = SHGlobal.deviceID;
        String pcAddress = SHGlobal.getPCServerAddress();
        if ("".equals(deviceID) || "".equals(pcAddress)) {
            Log.w(TAG, "device id and pc address can't be empty");
            return false;
        }

        OkHttpClient client = new OkHttpClient();
        FormBody formBody = new FormBody.Builder()
                .add("deviceID", deviceID)
                .build();
        final Request request = new Request.Builder()
                .url(pcAddress + "/device")
                .post(formBody)
                .build();
        Call call = client.newCall(request);

        Response resp = null;
        try {
            resp = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (resp != null && resp.isSuccessful()) {
            // TODO should check the response content?
            Log.i(TAG, "device auth successful");
            return true;
        }
        Log.w(TAG, "device auth failed");
        return false;
    }
}
