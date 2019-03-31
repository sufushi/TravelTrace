package com.rdc.project.traveltrace.utils;

import android.content.Context;
import android.view.ViewGroup;

import com.pili.pldroid.player.PLOnCompletionListener;
import com.pili.pldroid.player.widget.PLVideoTextureView;

import java.lang.ref.WeakReference;

public class VideoListViewManager implements PLOnCompletionListener {

    private PLVideoTextureView mPLVideoTextureView;
    private WeakReference<ViewGroup> mRootLayoutRef;
    private String mUrl = "";

    public VideoListViewManager(Context context) {
        mPLVideoTextureView = new PLVideoTextureView(context);
        mPLVideoTextureView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dp2px(200, context)));
        mPLVideoTextureView.setDisplayAspectRatio(PLVideoTextureView.ASPECT_RATIO_PAVED_PARENT);
        mPLVideoTextureView.setOnCompletionListener(this);
    }

    public void attach(ViewGroup rootLayout, String url) {
        if (mRootLayoutRef != null && mRootLayoutRef.get() == rootLayout && mUrl.equals(url)) {
            return;
        }
        mRootLayoutRef = new WeakReference<>(rootLayout);
        mUrl = url;
        mPLVideoTextureView.stopPlayback();
        ViewGroup parent = (ViewGroup) mPLVideoTextureView.getParent();
        if (parent != null) {
            parent.removeView(mPLVideoTextureView);
        }
        rootLayout.addView(mPLVideoTextureView);
        mPLVideoTextureView.setVideoPath(url);
        mPLVideoTextureView.start();
    }

    public void detach(ViewGroup rootLayout) {
//        mPLVideoTextureView.stopPlayback();
//        rootLayout.removeView(mPLVideoTextureView);
    }

    @Override
    public void onCompletion() {
        mPLVideoTextureView.start();
    }
}
