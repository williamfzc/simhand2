package com.github.williamfzc.uioserver.ActionHandler;


import android.support.test.uiautomator.UiDevice;

public class BaseActionHandler {
    UiDevice mDevice;

    BaseActionHandler(UiDevice mDevice) {
        this.mDevice = mDevice;
    }
}
