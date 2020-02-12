package com.github.williamfzc.simhand2.ActionHandler;

import android.support.test.uiautomator.UiDevice;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TouchActionHandler extends BaseActionHandler {
    private static final String TAG = "TouchActionHandler";

    public TouchActionHandler(UiDevice mDevice) {
        super(mDevice);
        Log.i(TAG, "on device: " + mDevice.getProductName());
    }

    @Override
    public boolean apply(Map<String, String> paramsMap) {
        initParams(paramsMap);

        // points
        // 12,34|13,35
        String[] pointStrList = points.split("\\|");
        List<Integer[]> pointList = new ArrayList<Integer[]>();
        for (String each : pointStrList) {
            String[] point = each.split(",");
            Integer[] intPoint = new Integer[point.length];
            for (int i = 0; i < point.length; i++) {
                intPoint[i] = Integer.valueOf(point[i]);
            }
            pointList.add(intPoint);
        }
        Log.i(TAG, "points: " + pointList.toString());
        Log.i(TAG, "start action: " + actionName);
        switch (actionName) {
            case "click":
                for (Integer[] point : pointList) {
                    boolean ret = mDevice.click(point[0], point[1]);
                    if (!ret)
                        return false;
                }
                return true;
            case "swipe":
                Integer[] from = pointList.get(0);
                Integer[] to = pointList.get(1);
                return mDevice.swipe(from[0], from[1], to[0], to[1], 50);
            default:
                Log.e(TAG, "no action named " + actionName);
                return false;
        }
    }
}
