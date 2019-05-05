package com.rdc.project.traveltrace.fragment;

import android.os.Bundle;

import com.kk.taurus.playerbase.assist.OnVideoViewEventHandler;
import com.kk.taurus.playerbase.entity.DataSource;
import com.kk.taurus.playerbase.receiver.ReceiverGroup;
import com.kk.taurus.playerbase.widget.BaseVideoView;
import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.base.BaseBounceFragment;
import com.rdc.project.traveltrace.view.player_cover.CompleteCover;
import com.rdc.project.traveltrace.view.player_cover.ControllerCover;
import com.rdc.project.traveltrace.view.player_cover.LoadingCover;

import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_FIELD_VIDEO_PATH;
import static com.rdc.project.traveltrace.utils.player.InterData.ReceiverKey.KEY_COMPLETE_COVER;
import static com.rdc.project.traveltrace.utils.player.InterData.ReceiverKey.KEY_CONTROLLER_COVER;
import static com.rdc.project.traveltrace.utils.player.InterData.ReceiverKey.KEY_LOADING_COVER;

public class VideoPreviewFragment extends BaseBounceFragment {

    private BaseVideoView mVideoView;
    private String mVideoPath;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_video_preview;
    }

    @Override
    protected void initData(Bundle bundle) {
        mVideoPath = bundle.getString(ACTION_FIELD_VIDEO_PATH);
    }

    @Override
    protected void initView() {
        mVideoView = mRootView.findViewById(R.id.video_view);

        ReceiverGroup receiverGroup = new ReceiverGroup();
        receiverGroup.addReceiver(KEY_LOADING_COVER, new LoadingCover(getActivity()));
        receiverGroup.addReceiver(KEY_CONTROLLER_COVER, new ControllerCover(getActivity(), true));
        receiverGroup.addReceiver(KEY_COMPLETE_COVER, new CompleteCover(getActivity()));

        mVideoView.setReceiverGroup(receiverGroup);
        mVideoView.setEventHandler(new OnVideoViewEventHandler());

        DataSource dataSource = new DataSource();
        dataSource.setData(mVideoPath);
        mVideoView.setDataSource(dataSource);
        mVideoView.start();
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mVideoView.stopPlayback();
    }
}
