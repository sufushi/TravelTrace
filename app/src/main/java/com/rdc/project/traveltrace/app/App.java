package com.rdc.project.traveltrace.app;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.lzy.ninegrid.NineGridView;
import com.rdc.project.traveltrace.utils.GlideImageLoader;

public class App extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        NineGridView.setImageLoader(new GlideImageLoader());
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
