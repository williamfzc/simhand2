package com.github.williamfzc.uioserver;


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

    private static final String BASE_PACKAGE_NAME
            = "com.github.williamfzc.uioserver";
    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String STRING_TO_BE_TYPED = "UiAutomator";
    private UiDevice mDevice;
    private APIServer mServer;

    private boolean runServer() {
        try {
            mServer = new APIServer();
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
        if (runServer()) {
            Log.i("APIServer", "server already started");
        } else {
            Log.e("APIServer", "server start failed");
            throw new RuntimeException("server start up failed");
        }
    }

    @Test
    @LargeTest
    public void SayHello() throws InterruptedException {
        while (true) {
            Log.i("SERVER HEARTBEAT", "UIO server is alive :)");
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