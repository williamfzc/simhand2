package com.github.williamfzc.uioserver;

import java.util.Map;

public class UIOUtils {
    public static String getParamFromMap(Map<String, String> targetMap, String targetFlag, String defaultFlag) {
        if (!targetMap.containsKey(targetFlag)) {
            return defaultFlag;
        }
        return targetMap.get(targetFlag);
    }
}
