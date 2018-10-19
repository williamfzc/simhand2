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


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.Until;
import android.util.Log;

import java.io.IOException;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;


@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class StubTestCase {

    private static final String BASE_PACKAGE_NAME = "com.github.williamfzc.simhand2";
    private static final int LAUNCH_TIMEOUT = 5000;
    private static final int SERVER_PORT = 8080;
    private UiDevice mDevice;
    private APIServer mServer;

    private boolean runServer(UiDevice targetDevice) {
        try {
            mServer = new APIServer(SERVER_PORT, targetDevice);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Before
    public void startMainActivityFromHomeScreen() {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // Start from the home screen
        mDevice.pressHome();

        // Wait for launcher
        final String launcherPackage = mDevice.getLauncherPackageName();
        Log.i("launch package name", launcherPackage);
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)),
                LAUNCH_TIMEOUT);

        // TODO as a server, don't need a specify app.
        // Launch the app
        Context context = InstrumentationRegistry.getContext();

        // Get intent to target app
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(BASE_PACKAGE_NAME);

        // Clear out any previous instances, and new one
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

        // Wait for the app to appear
        mDevice.wait(Until.hasObject(By.pkg(BASE_PACKAGE_NAME).depth(0)),
                LAUNCH_TIMEOUT);

        // startup server
        if (runServer(mDevice)) {
            Log.i("APIServer", "simhand already started");
        } else {
            Log.e("APIServer", "simhand start failed");
            throw new RuntimeException("simhand start up failed");
        }
    }

    @Test
    @LargeTest
    public void KeepAlive() throws InterruptedException {
        while (true) {
            Log.i("SERVER HEARTBEAT", "simhand is alive :)");
            Thread.sleep(5000);
        }
    }

    @After
    public void stopServer() {
        if (mServer != null) {
            mServer.stop();
        }
        mDevice = null;
    }
}