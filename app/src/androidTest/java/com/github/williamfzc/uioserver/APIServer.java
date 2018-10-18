package com.github.williamfzc.uioserver;

import java.io.IOException;

import fi.iki.elonen.NanoHTTPD;

public class APIServer extends NanoHTTPD {
    public APIServer() throws IOException {
        super(8080);
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
    }

    @Override
    public Response serve(IHTTPSession session) {
//        // get args
//        Map<String, String> params = session.getParms();
        return newFixedLengthResponse("server is ready!");
    }
}
