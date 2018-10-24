package com.github.williamfzc.simhand2.ActionHandler;

import android.support.test.uiautomator.UiDevice;
import android.util.Log;

import java.io.IOException;
import java.util.Map;

public class SystemActionHandler extends BaseActionHandler {
    private static final String TAG = "SystemActionHandler";

    public SystemActionHandler(UiDevice mDevice) {
        super(mDevice);
        Log.i(TAG, "on device: " + mDevice.getProductName());
    }

    @Override
    public boolean apply(Map<String, String> paramsMap) {
        initParams(paramsMap);
        return applySystemFunction(actionName);
    }

    public boolean applySystemFunction(String actionName) {
        String execResult = "";
        try {
            switch (actionName) {
                case "turnOnAirplaneMode":
                    execResult = switchAirplaneMode(true);
                    break;
                case "turnOffAirplaneMode":
                    execResult = switchAirplaneMode(false);
                    break;
                default:
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.i(TAG, "result of " + actionName + ": " + e.toString());
            return false;
        }
        Log.i(TAG, "result of " + actionName + ": " + execResult);
        return true;
    }

    private String switchAirplaneMode(boolean on) throws IOException {
        String modeCode;
        String stateFlag;
        String execResult;
        if (on) {
            modeCode = "1";
            stateFlag = "true";
        } else {
            modeCode = "0";
            stateFlag = "false";
        }
        execResult = mDevice.executeShellCommand("settings put global airplane_mode_on " + modeCode);
        execResult += mDevice.executeShellCommand("am broadcast -a android.intent.action.AIRPLANE_MODE --ez state " + stateFlag);
        return execResult;
    }
}
