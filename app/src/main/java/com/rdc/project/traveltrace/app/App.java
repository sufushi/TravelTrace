package com.rdc.project.traveltrace.app;

import android.app.Application;

import com.lzy.ninegrid.NineGridView;
import com.rdc.project.traveltrace.utils.GlideImageLoader;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NineGridView.setImageLoader(new GlideImageLoader());
    }
}
