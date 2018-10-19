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

    public static UiObject waitElementByText(UiDevice mDevice, String targetStr, Integer delayTime) {
        try {
            Thread.sleep(delayTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
        return findElementByText(mDevice, targetStr);
    }
}
