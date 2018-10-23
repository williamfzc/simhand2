package com.github.williamfzc.simhand2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.test.uiautomator.UiDevice;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import fi.iki.elonen.NanoHTTPD;

import static fi.iki.elonen.NanoHTTPD.newFixedLengthResponse;

public class ScreenshotHandler {
    private UiDevice mDevice;
    private static final int IN_SAMPLE_SIZE = 6;


    public ScreenshotHandler(UiDevice mDevice) {
        this.mDevice = mDevice;
    }

    public NanoHTTPD.Response getScreenshot() {
        // TODO compress too slow ( should replace with minicap?
        File tempPngFile;

        try {
            tempPngFile = File.createTempFile("simhand_screen", ".png");
            // small size picture is enough
            // but these args actually does not work!
            mDevice.takeScreenshot(tempPngFile, 0.01f, 10);

            // compress
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = IN_SAMPLE_SIZE;
            Bitmap bitmap = BitmapFactory.decodeFile(tempPngFile.getPath(), options);

            ByteArrayOutputStream tempStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 1, tempStream);
            InputStream targetStream = new ByteArrayInputStream(tempStream.toByteArray());
            return newFixedLengthResponse(
                    NanoHTTPD.Response.Status.OK, "image/png", targetStream, tempStream.size());
        } catch (IOException e) {
            // should never happen!
            e.printStackTrace();
            return null;
        }
    }
}
