package com.rdc.project.traveltrace.utils.update;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.rdc.project.traveltrace.app.App;
import com.rdc.project.traveltrace.utils.SharePreferenceUtil;

import static com.rdc.project.traveltrace.utils.update.Updater.DOWNLOADING_APK_KEY;

public class DownloadReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle == null) return;
        long downId = bundle.getLong(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
        //下载完成或点击通知栏
        if (TextUtils.equals(intent.getAction(), (DownloadManager.ACTION_DOWNLOAD_COMPLETE)) ||
                TextUtils.equals(intent.getAction(), (DownloadManager.ACTION_NOTIFICATION_CLICKED))) {
            queryFileUri(context, downId);
        }
    }

    private void queryFileUri(Context context, long downloadApkId) {
        DownloadManager dManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadApkId);
        if (dManager == null)return;
        Cursor c = dManager.query(query);
        if (c != null && c.moveToFirst()) {
            int status = c.getInt(c.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS));
            switch (status) {
                case DownloadManager.STATUS_PENDING:
                    break;
                case DownloadManager.STATUS_PAUSED:
                    break;
                case DownloadManager.STATUS_RUNNING:
                    break;
                case DownloadManager.STATUS_SUCCESSFUL:
                    SharePreferenceUtil.put(App.getAppContext(), DOWNLOADING_APK_KEY, false);
                    String downloadFileUrl = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                    Utils.installApk(context, Uri.parse(downloadFileUrl));
                    break;
                case DownloadManager.STATUS_FAILED:
                    Updater.showToast(context, "下载失败，开始重新下载...");
                    context.sendBroadcast(new Intent(Updater.DownloadFailedReceiver.tag));
                    break;
            }
            c.close();
        }
    }


}
