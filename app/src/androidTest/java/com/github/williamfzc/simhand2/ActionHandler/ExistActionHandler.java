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
import android.support.test.uiautomator.UiObject;
import android.util.Log;

import com.github.williamfzc.simhand2.Selector;
import com.github.williamfzc.simhand2.SHUtils;

import java.util.Map;

public class ExistActionHandler extends BaseActionHandler {
    public ExistActionHandler(UiDevice mDevice) {
        super(mDevice);
        Log.i("Exist Action", "on device: " + mDevice.getProductName());
    }

    public boolean apply(Map<String, String> paramsMap) {
        initParams(paramsMap);
        UiObject targetElement;

        // invalid widget name
        if ("".equals(widgetName)) {
            return false;
        }
        // need no delay
        if (SHUtils.isNumeric(delayTime)) {
            targetElement = Selector.waitElementByText(mDevice, widgetName, Integer.valueOf(delayTime));
        } else {
            targetElement = Selector.findElementByText(mDevice, widgetName);
        }
        try {
            targetElement.exists();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
