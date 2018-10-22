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

import android.support.test.uiautomator.UiDevice;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.github.williamfzc.simhand2.ActionHandler.ClickActionHandler;
import com.github.williamfzc.simhand2.ActionHandler.ExistActionHandler;

import java.io.IOException;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;


class SHResponse {
    private Integer responseCode;
    private String responseMsg;
    private String requestType;

    public SHResponse(Integer code, String msg, String requestType) {
        this.responseCode = code;
        this.responseMsg = msg;
        this.requestType = requestType;
    }

    public Integer getResponseCode() {
        return this.responseCode;
    }

    public String getResponseMsg() {
        return this.responseMsg;
    }

    public String getRequestType() {
        return this.requestType;
    }

    public String toJsonString() {
        return JSON.toJSONString(this);
    }
}

public class APIServer extends NanoHTTPD {
    private UiDevice mDevice;

    private String respStr;
    private boolean actionResult;

    public APIServer(int port, UiDevice mDevice) throws IOException {
        super(port);
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        this.mDevice = mDevice;
    }

    @Override
    public Response serve(IHTTPSession session) {
        // if request url is:
        // http://192.168.0.10:8080/abcd?actionType=exist&widgetName=Lea
        // uri: /abcd
        // params: {actionType=exist, widgetName=Lea}

        Map<String, String> params = session.getParms();
        String uri = session.getUri();
        Log.i("APIServer", "uri: " + uri);
        Log.i("APIServer", "params: " + params.toString());

        // router configure
        switch (uri) {
            case "/api/action/click":
                actionResult = new ClickActionHandler(mDevice).apply(params);
                respStr = new SHResponse(
                        SimhandUtils.getResponseCode(actionResult),
                        "" + actionResult,
                        "click"
                ).toJsonString();
                break;

            case "/api/action/exist":
                actionResult = new ExistActionHandler(mDevice).apply(params);
                respStr = new SHResponse(
                        SimhandUtils.getResponseCode(actionResult),
                        "" + actionResult,
                        "exist"
                ).toJsonString();
                break;

            default:
                respStr = new SHResponse(
                        SimhandUtils.getResponseCode(false),
                        "no action match",
                        uri
                ).toJsonString();
        }

        Log.i("APIServer", respStr);
        return newFixedLengthResponse(respStr);
    }
}
