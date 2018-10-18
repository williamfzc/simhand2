package com.github.williamfzc.uioserver;

import java.io.IOException;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;

public class APIServer extends NanoHTTPD {
    private static final String ACTION_FLAG = "actionType";

    public APIServer(int port) throws IOException {
        super(port);
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
    }

    @Override
    public Response serve(IHTTPSession session) {
        // get args
        Map<String, String> params = session.getParms();
        if (!params.containsKey(ACTION_FLAG)) {
            return newFixedLengthResponse("invalid action type");
        }
        String currentActionType = params.get(ACTION_FLAG);
        switch (currentActionType) {
            case "click":
                return newFixedLengthResponse("action is click");
            case "exist":
                return newFixedLengthResponse("action is exist");
            default:
                return newFixedLengthResponse("no action match");
        }
    }
}
