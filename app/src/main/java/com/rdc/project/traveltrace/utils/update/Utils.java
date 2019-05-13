package com.rdc.project.traveltrace.utils.update;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import java.io.File;

import me.weyye.hipermission.HiPermission;

public class Utils {

    public static final String getFileNameForUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            throw new NullPointerException("url is null");
        }

        return url.substring(url.lastIndexOf("/") + 1);
    }

    public static void installApk(Context context, Uri uri) {
        File file = new File(uri.getPath());
        if (!file.exists()) {
            return;
        }
        install(context, uri, file);
    }

    private static void install(Context context, Uri uri, File file) {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            String packageName = context.getPackageName();
            Uri providerUri = FileProvider.getUriForFile(context, packageName + ".fileProvider", file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(providerUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        context.startActivity(intent);
    }
}