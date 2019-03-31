package com.rdc.project.traveltrace.app;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;

import com.kk.taurus.playerbase.config.PlayerConfig;
import com.kk.taurus.playerbase.config.PlayerLibrary;
import com.kk.taurus.playerbase.record.PlayRecordManager;
import com.lzy.ninegrid.NineGridView;
import com.rdc.project.traveltrace.utils.GlideNineGirdImageLoader;

public class App extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        NineGridView.setImageLoader(new GlideNineGirdImageLoader());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        PlayerConfig.setUseDefaultNetworkEventProducer(true);
        PlayerLibrary.init(this);
        PlayerConfig.playRecord(true);
        PlayRecordManager.setRecordConfig(new PlayRecordManager.RecordConfig.Builder().setMaxRecordCount(100).build());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        sContext = this;
    }

    public static Context getAppContext() {
        return sContext;
    }
}
