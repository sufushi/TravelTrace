package com.rdc.project.traveltrace.utils.update;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.res.ResourcesCompat;
import android.text.TextUtils;
import android.widget.Toast;

import com.rdc.project.traveltrace.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;
import me.weyye.hipermission.PermissionItem;

public class Updater {

    private String apkFileName;
    private String apkFilePath;
    private String apkDirName;
    private String title;
    private String downloadUrl;
    private Context context;
    private DownloadManager downloadManager;
    private long mTaskId;
    private boolean hideNotification = false;
    private ArrayList<ProgressListener> listeners;
    private boolean allowedOverRoaming = false;
    private DownloadReceiver downloadReceiver;
    private DownloadObserver downloadObserver;
    private boolean claerCache = false;

    private String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int RC_SDCARD = 123;

    private List<PermissionItem> mPermissionItems = new ArrayList<>();

    private DownloadFailedReceiver downloadFailedReceiver = new DownloadFailedReceiver();


    private Updater(Context context) {
        this.context = context;
        mPermissionItems.clear();
        mPermissionItems.add(new PermissionItem(Manifest.permission.WRITE_EXTERNAL_STORAGE, "写入", R.drawable.permission_ic_storage));
        mPermissionItems.add(new PermissionItem(Manifest.permission.READ_EXTERNAL_STORAGE, "读取", R.drawable.permission_ic_storage));
    }

    private void performPermissions() {
        if (checkPermissions()) {
            download();
        } else {
            requestPermission();
        }
    }

    private boolean checkPermissions() {
        boolean permission = false;
        for (int i = 0; i < mPermissionItems.size(); i++) {
            permission |= HiPermission.checkPermission(context, mPermissionItems.get(i).Permission);
        }
        return permission;
    }

    private void requestPermission() {
        HiPermission.create(context)
                .title("亲爱的上帝")
                .permissions(mPermissionItems)
                .filterColor(ResourcesCompat.getColor(context.getResources(), R.color.white, context.getTheme()))//图标的颜色
                .msg("为了保护世界的和平，开启这些权限吧！\n你我一起拯救世界！")
                .style(R.style.PermissionDefaultBlueStyle)
                .checkMutiPermission(new PermissionCallback() {
                    @Override
                    public void onClose() {

                    }

                    @Override
                    public void onFinish() {
                        download();
                    }

                    @Override
                    public void onDeny(String permission, int position) {

                    }

                    @Override
                    public void onGuarantee(String permission, int position) {

                    }
                });
    }

    private void download() {
        if (context == null) {
            throw new NullPointerException("context must not be null");
        }

        if (TextUtils.isEmpty(downloadUrl)) {
            throw new NullPointerException("downloadUrl must not be null");
        }

        if (downloadManager == null) {
            downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        }

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadUrl));

        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager
                .Request.NETWORK_WIFI);

        request.setAllowedOverRoaming(allowedOverRoaming);

        request.setTitle(TextUtils.isEmpty(title) ? apkFileName : title);

        request.setNotificationVisibility(hideNotification ? DownloadManager.Request.VISIBILITY_HIDDEN
                : DownloadManager.Request.VISIBILITY_VISIBLE);
        if (TextUtils.isEmpty(apkFileName)) {
            apkFileName = Utils.getFileNameForUrl(downloadUrl);
        }

        if (!apkFileName.endsWith(".apk")) {
            apkFileName += ".apk";
        }

        //设置下载路径
        if (TextUtils.isEmpty(apkFilePath) && TextUtils.isEmpty(apkDirName)) {
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, apkFileName);
        } else if (!TextUtils.isEmpty(apkDirName)) {
            request.setDestinationInExternalPublicDir(apkDirName, apkFileName);
        } else {
            String apkAbsPath = apkFilePath + File.separator + apkFileName;
            request.setDestinationUri(Uri.fromFile(new File(apkAbsPath)));
        }

        //将下载请求加入下载队列
        //加入下载队列后会给该任务返回一个long型的id，
        //通过该id可以取消任务，重启任务等等
        mTaskId = downloadManager.enqueue(request);
        if (downloadFailedReceiver != null) {
            context.registerReceiver(downloadFailedReceiver,
                    new IntentFilter(Updater.DownloadFailedReceiver.tag));
        }
    }

    public void registerDownloadReceiver() {
        if (downloadReceiver == null) {
            downloadReceiver = new DownloadReceiver();
        }
        context.registerReceiver(downloadReceiver,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    public void unRegisterDownloadReceiver() {
        if (downloadReceiver != null) {
            context.unregisterReceiver(downloadReceiver);
        }
    }

    public void addProgressListener(ProgressListener progressListener) {
        if (listeners == null) {
            listeners = new ArrayList<>();
        }
        if (!listeners.contains(progressListener)) {
            listeners.add(progressListener);
        }
        if (downloadObserver == null && handler != null && downloadManager != null) {
            downloadObserver = new DownloadObserver(handler, downloadManager, mTaskId);
            context.getContentResolver().registerContentObserver(Uri.parse("content://downloads/"),
                    true, downloadObserver);
        }
    }

    public void removeProgressListener(ProgressListener progressListener) {
        if (!listeners.contains(progressListener)) {
            throw new NullPointerException("this progressListener not attch Updater");
        }
        listeners.remove(progressListener);
        if (listeners.isEmpty() && downloadObserver != null) {
            context.getContentResolver().unregisterContentObserver(downloadObserver);
        }
    }

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Bundle data = msg.getData();
            long cutBytes = data.getLong(DownloadObserver.CURBYTES);
            long totalBytes = data.getLong(DownloadObserver.TOTALBYTES);
            int progress = data.getInt(DownloadObserver.PROGRESS);
            if (listeners != null && !listeners.isEmpty()) {
                for (ProgressListener listener : listeners) {
                    listener.onProgressChange(totalBytes, cutBytes, progress);
                }
            }
            return false;
        }
    });

    public static class Builder {

        private Updater mUpdater;

        public Builder(Context context) {
            synchronized (Updater.class) {
                if (mUpdater == null) {
                    synchronized (Updater.class) {
                        mUpdater = new Updater(context);
                    }
                }
            }
        }

        public Builder setApkFileName(String apkName) {
            mUpdater.apkFileName = apkName;
            return this;
        }

        public Builder setApkPath(String apkPath) {
            mUpdater.apkFilePath = apkPath;
            return this;
        }

        public Builder setApkDir(String dirName) {
            mUpdater.apkDirName = dirName;
            return this;
        }

        public Builder setDownloadUrl(String downloadUrl) {
            mUpdater.downloadUrl = downloadUrl;
            return this;
        }

        public Builder setNotificationTitle(String title) {
            mUpdater.title = title;
            return this;
        }

        public Builder hideNotification() {
            mUpdater.hideNotification = true;
            return this;
        }

        public Builder allowedOverRoaming() {
            mUpdater.allowedOverRoaming = true;
            return this;
        }


        public Builder clearCache() {
            mUpdater.claerCache = true;
            return this;
        }

        public Updater start() {
            mUpdater.performPermissions();
            return mUpdater;
        }

    }

    public class DownloadFailedReceiver extends BroadcastReceiver {

        public static final String tag = "DownloadFailedReceiver";

        @Override
        public void onReceive(Context context, Intent intent) {
            download();
        }
    }

}
