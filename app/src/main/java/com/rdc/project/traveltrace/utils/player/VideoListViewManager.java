package com.rdc.project.traveltrace.utils.player;

import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;

//import com.pili.pldroid.player.widget.PLVideoTextureView;

import com.kk.taurus.playerbase.assist.OnAssistPlayEventHandler;
import com.kk.taurus.playerbase.assist.RelationAssist;
import com.kk.taurus.playerbase.entity.DataSource;
import com.kk.taurus.playerbase.receiver.OnReceiverEventListener;
import com.kk.taurus.playerbase.receiver.ReceiverGroup;
import com.rdc.project.traveltrace.utils.UriUtil;
import com.rdc.project.traveltrace.utils.action.Action;
import com.rdc.project.traveltrace.utils.action.ActionManager;

import java.lang.ref.WeakReference;

import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_FIELD_VIDEO_PATH;
import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_NAME_VIDEO_PREVIEW;
import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_PRE;

public class VideoListViewManager {

//    private PLVideoTextureView mPLVideoTextureView;
    private WeakReference<ViewGroup> mRootLayoutRef;
    private String mUrl = "";

    private RelationAssist mRelationAssist;

    public VideoListViewManager(final Context context) {
        mRelationAssist = new RelationAssist(context);
        mRelationAssist.setEventAssistHandler(new OnAssistPlayEventHandler());
        ReceiverGroup receiverGroup = ReceiverGroupManager.get().getLiteReceiverGroup(context);
        mRelationAssist.setReceiverGroup(receiverGroup);
        mRelationAssist.setOnReceiverEventListener(new OnReceiverEventListener() {
            @Override
            public void onReceiverEvent(int eventCode, Bundle bundle) {
                if (eventCode == InterData.Event.EVENT_CODE_REQUEST_TOGGLE_SCREEN) {
                    Action action = new Action(ACTION_PRE + ACTION_NAME_VIDEO_PREVIEW + "?" + ACTION_FIELD_VIDEO_PATH + "=" + UriUtil.encode(mUrl));
                    ActionManager.doAction(action, context);
                }
            }
        });
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
    }

}
