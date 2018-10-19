package com.github.williamfzc.uioserver.ActionHandler;

import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.util.Log;

import com.github.williamfzc.uioserver.Selector;
import com.github.williamfzc.uioserver.UIOUtils;

import java.util.Map;

public class ClickActionHandler extends BaseActionHandler {
    public ClickActionHandler(UiDevice mDevice) {
        super(mDevice);
        Log.i("Click Action", "on device: " + mDevice.getProductName());
    }

    public boolean apply(Map<String, String> paramsMap) {
        String widgetName = UIOUtils.getParamFromMap(paramsMap, "widgetName", "");
        if ("".equals(widgetName)) {
            return false;
        }
        UiObject targetElement = Selector.findElementByText(mDevice, widgetName);
        try {
            targetElement.click();
        } catch (UiObjectNotFoundException | NullPointerException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
