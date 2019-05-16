package com.rdc.project.traveltrace.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;

public class NetWorkConnectivityReceiver extends BroadcastReceiver {
    private static final String TAG = "NetWorkConnectivityRece";

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isNetworkAvailable = cm.getActiveNetworkInfo() != null;
        boolean isNetworkConnected = isNetworkAvailable &&
                cm.getActiveNetworkInfo().isConnected();

        if (!isNetworkConnected) {
            Log.d(TAG, "onReceive: NetWorkConnectivityRece is not connected");
//            Toast.makeText(context, "网络连接不可用", Toast.LENGTH_SHORT).show();
            new AlertDialog.Builder(context)
                    .setTitle("警告！")
                    .setMessage("网络连接不可用，您可能会错过重要信息,请尽快联网。")
                    .setPositiveButton("确定", null)
                    .show();
        } else {
            Log.d(TAG, "onReceive: NetWorkConnectivityRece is connected");
        }
    }
}
