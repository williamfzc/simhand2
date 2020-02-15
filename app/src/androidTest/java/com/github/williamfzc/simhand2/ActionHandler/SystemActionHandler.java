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
                case "turnOnBluetooth":
                    execResult = switchBluetoothMode(true);
                    break;
                case "turnOffBluetooth":
                    execResult = switchBluetoothMode(false);
                    break;
                case "pressBack":
                    execResult = pressButton("back").toString();
                    break;
                case "pressHome":
                    execResult = pressButton("home").toString();
                    break;
                case "pressMenu":
                    execResult = pressButton("menu").toString();
                    break;
                case "pressEnter":
                    execResult = pressButton("enter").toString();
                    break;
                case "pressDelete":
                    execResult = pressButton("delete").toString();
                    break;
                case "pressPower":
                    execResult = pressButton("power").toString();
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

    private String switchBluetoothMode(boolean on) throws IOException {
        String stateFlag;
        if (on) {
            stateFlag = "enable";
        } else {
            stateFlag = "disable";
        }
        return mDevice.executeShellCommand("svc bluetooth " + stateFlag);
    }

    private Boolean pressButtonByCode(Integer keyCode) {
        return mDevice.pressKeyCode(keyCode);
    }

    private Boolean pressButton(String buttonName) {
        Boolean pressResult;
        switch (buttonName) {
            case "home":
                pressResult = mDevice.pressHome();
                break;
            case "back":
                pressResult = mDevice.pressBack();
                break;
            case "delete":
                pressResult = mDevice.pressDelete();
                break;
            case "enter":
                pressResult = mDevice.pressEnter();
                break;
            case "menu":
                pressResult = mDevice.pressMenu();
                break;
            case "power":
                pressResult = pressButtonByCode(26);
                break;
            default:
                pressResult = false;
                Log.w(TAG, "button " + buttonName + " not found");
        }
        return pressResult;
    }
}
