package com.rdc.project.traveltrace.utils;

import android.net.Uri;
import android.text.TextUtils;

public class UriUtil {

    public static String encode(String data) {
        if (TextUtils.isEmpty(data)) {
            return "";
        } else {
            data = encodeCanNull(data);
            return data == null ? "" : data;
        }
    }

    public static String encodeCanNull(String data) {
        return TextUtils.isEmpty(data) ? "" : Uri.encode(data);
    }

    public static String decode(String data) {
        if (TextUtils.isEmpty(data)) {
            return "";
        } else {
            data = decodeCanNull(data);
            return data == null ? "" : data;
        }
    }

    public static String decodeCanNull(String data) {
        return TextUtils.isEmpty(data) ? data : Uri.decode(data);
    }

}
