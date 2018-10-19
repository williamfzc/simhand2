package com.github.williamfzc.uioserver;

import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;

public class Selector {
    public static UiObject findElementByText(UiDevice mDevice, String targetStr) {
        UiObject targetObject = mDevice.findObject(new UiSelector().text(targetStr));
        if (!targetObject.exists()) {
            Log.w("UI_SELECTOR", "object " + targetStr + " not found");
            return null;
        }
        return targetObject;
    }
}
