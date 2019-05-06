package com.rdc.project.traveltrace.app;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.support.multidex.MultiDex;

import com.kk.taurus.exoplayer.ExoMediaPlayer;
import com.kk.taurus.playerbase.config.PlayerConfig;
import com.kk.taurus.playerbase.record.PlayRecordManager;
import com.rdc.project.traveltrace.utils.GlideNineGirdImageLoader;
import com.rdc.project.traveltrace.view.nine_grid_view.NineGridView;
import com.rdc.project.traveltrace.view.toast.CommonToast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;

public class App extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        initNineGridViewConfig();
        initPlayerConfig();
        initToastConfig();
        initBmob();
    }

    private void initNineGridViewConfig() {
        NineGridView.setImageLoader(new GlideNineGirdImageLoader());
    }

    private void initPlayerConfig() {
        PlayerConfig.setUseDefaultNetworkEventProducer(true);
//        PlayerLibrary.init(this);
        ExoMediaPlayer.init(this);
        PlayerConfig.playRecord(true);
        PlayRecordManager.setRecordConfig(new PlayRecordManager.RecordConfig.Builder().setMaxRecordCount(100).build());
    }

    private void initToastConfig() {
        CommonToast.Config.getInstance()
                .tintIcon(true)
                .setTextSize(12)
                .allowQueue(true)
                .apply();
    }

    private void initBmob() {
        BmobConfig bmobConfig = new BmobConfig.Builder(this)
                .setApplicationId("d7396c68d5b566a8ab88509b2baf89d5")
                .setConnectTimeout(30)
                .setUploadBlockSize(1024 * 1024)
                .setFileExpiration(2500)
                .build();
        Bmob.initialize(bmobConfig);
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        sContext = this;
        MultiDex.install(this);
    }

    public static Context getAppContext() {
        return sContext;
    }
}
