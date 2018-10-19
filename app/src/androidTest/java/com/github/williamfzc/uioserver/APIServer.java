package com.github.williamfzc.uioserver;

import android.support.test.uiautomator.UiDevice;

import com.github.williamfzc.uioserver.ActionHandler.ClickActionHandler;

import java.io.IOException;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;

public class APIServer extends NanoHTTPD {
    private static final String ACTION_FLAG = "actionType";
    private UiDevice mDevice;

    public APIServer(int port, UiDevice mDevice) throws IOException {
        super(port);
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        this.mDevice = mDevice;
    }

    @Override
    public Response serve(IHTTPSession session) {
        // get args
        Map<String, String> params = session.getParms();
        String currentActionType = UIOUtils.getParamFromMap(params, ACTION_FLAG, "invaild");
        switch (currentActionType) {
            case "click":
                boolean clickResult = new ClickActionHandler(mDevice).apply(params);
                return newFixedLengthResponse("action is click, and result is: " + clickResult);
            case "exist":
                return newFixedLengthResponse("action is exist");
            default:
                return newFixedLengthResponse("no action match");
        }
    }
}
