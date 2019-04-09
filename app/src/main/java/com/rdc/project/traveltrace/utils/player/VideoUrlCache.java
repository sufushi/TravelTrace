package com.rdc.project.traveltrace.utils.player;

import android.util.SparseArray;

public class VideoUrlCache {

    private static SparseArray<String> sCacheMap = new SparseArray<>();

    public static void putVideoUrl(int id, String url) {
        sCacheMap.put(id, url);
    }

    public static String getVideoUrl(int id) {
        return sCacheMap.get(id);
    }

}
