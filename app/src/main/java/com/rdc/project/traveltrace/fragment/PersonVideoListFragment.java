package com.rdc.project.traveltrace.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.adapter.VideoListAdapter;
import com.rdc.project.traveltrace.base.BaseBounceFragment;
import com.rdc.project.traveltrace.base.OnClickRecyclerViewListener;
import com.rdc.project.traveltrace.decorator.SpaceGridItemDecoration;
import com.rdc.project.traveltrace.entity.VideoNote;
import com.rdc.project.traveltrace.entity.VideoNoteList;
import com.rdc.project.traveltrace.manager.VideoNoteListManager;
import com.rdc.project.traveltrace.utils.CollectionUtil;
import com.rdc.project.traveltrace.utils.DensityUtil;
import com.rdc.project.traveltrace.utils.UriUtil;
import com.rdc.project.traveltrace.utils.action.Action;
import com.rdc.project.traveltrace.utils.action.ActionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_FIELD_VIDEO_PATH;
import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_NAME_VIDEO_PREVIEW;
import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_PRE;

public class PersonVideoListFragment extends BaseBounceFragment implements VideoNoteListManager.GetVideoNoteListCallback {

    private VideoListAdapter mVideoListAdapter;
    private List<VideoNote> mVideoNoteList;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_person_video_list;
    }

    @Override
    protected void initData(Bundle bundle) {
        mVideoListAdapter = new VideoListAdapter(getActivity());
        mVideoNoteList = new ArrayList<>();
        mVideoNoteList.addAll(VideoNoteListManager.getInstance().getVideoNoteList());
        if (CollectionUtil.isEmpty(mVideoNoteList)) {
            VideoNoteListManager.getInstance().queryVideoNoteList(this);
        }
        mVideoListAdapter.updateData(mVideoNoteList);
    }

    @Override
    protected void initView() {
        RecyclerView videoListView = mRootView.findViewById(R.id.video_list);
        videoListView.setItemAnimator(new DefaultItemAnimator());
        videoListView.addItemDecoration(new SpaceGridItemDecoration(DensityUtil.dp2px(2, Objects.requireNonNull(getActivity()))));
        videoListView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        videoListView.setAdapter(mVideoListAdapter);
    }

    @Override
    protected void setListener() {
        mVideoListAdapter.setOnRecyclerViewListener(new OnClickRecyclerViewListener() {
            @Override
            public void onItemClick(int position, View view) {
                if (CollectionUtil.inRange(mVideoNoteList, position)) {
                    VideoNote videoNote = mVideoNoteList.get(position);
                    Action action = new Action(ACTION_PRE + ACTION_NAME_VIDEO_PREVIEW + "?" + ACTION_FIELD_VIDEO_PATH + "=" + UriUtil.encode(videoNote.getVideoUrl()));
                    ActionManager.doAction(action, getContext());
                }
            }

            @Override
            public boolean onItemLongClick(int position) {
                return false;
            }
        });
    }

    @Override
    public void onGetVideoNoteList(VideoNoteList videoNoteList) {
        mVideoNoteList.clear();
        mVideoNoteList.addAll(videoNoteList.getVideoNoteList());
        mVideoListAdapter.updateData(mVideoNoteList);
    }
}
