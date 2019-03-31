package com.rdc.project.traveltrace.utils.player;

import android.content.Context;
import android.view.ViewGroup;

//import com.pili.pldroid.player.widget.PLVideoTextureView;

import com.kk.taurus.playerbase.assist.OnAssistPlayEventHandler;
import com.kk.taurus.playerbase.assist.RelationAssist;
import com.kk.taurus.playerbase.entity.DataSource;
import com.kk.taurus.playerbase.receiver.ReceiverGroup;

import java.lang.ref.WeakReference;

public class VideoListViewManager {

//    private PLVideoTextureView mPLVideoTextureView;
    private WeakReference<ViewGroup> mRootLayoutRef;
    private String mUrl = "";

    private RelationAssist mRelationAssist;

    public VideoListViewManager(Context context) {
//        mPLVideoTextureView = new PLVideoTextureView(context);
//        mPLVideoTextureView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dp2px(200, context)));
//        mPLVideoTextureView.setDisplayAspectRatio(PLVideoTextureView.ASPECT_RATIO_PAVED_PARENT);
        mRelationAssist = new RelationAssist(context);
        mRelationAssist.setEventAssistHandler(new OnAssistPlayEventHandler());
        ReceiverGroup receiverGroup = ReceiverGroupManager.get().getLiteReceiverGroup(context);
        mRelationAssist.setReceiverGroup(receiverGroup);
    }

    public void attach(ViewGroup rootLayout, String url) {
        if (mRootLayoutRef != null && mRootLayoutRef.get() == rootLayout && mUrl.equals(url)) {
            return;
        }
        mRootLayoutRef = new WeakReference<>(rootLayout);
        mUrl = url;
        DataSource dataSource = new DataSource();
        dataSource.setData(url);
        mRelationAssist.setDataSource(dataSource);
        mRelationAssist.attachContainer(rootLayout);
        mRelationAssist.play();
//        mPLVideoTextureView.stopPlayback();
//        ViewGroup parent = (ViewGroup) mPLVideoTextureView.getParent();
//        if (parent != null) {
//            parent.removeView(mPLVideoTextureView);
//        }
//        rootLayout.addView(mPLVideoTextureView);
//        mPLVideoTextureView.setVideoPath(url);
//        mPLVideoTextureView.start();
    }

}
