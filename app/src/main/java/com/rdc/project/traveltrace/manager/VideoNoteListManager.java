package com.rdc.project.traveltrace.manager;

import com.rdc.project.traveltrace.contract.IQueryContract;
import com.rdc.project.traveltrace.entity.User;
import com.rdc.project.traveltrace.entity.VideoNote;
import com.rdc.project.traveltrace.entity.VideoNoteList;
import com.rdc.project.traveltrace.model.query.VideoNoteQueryModel;
import com.rdc.project.traveltrace.presenter.QueryPresenterImpl;
import com.rdc.project.traveltrace.presenter.query.QueryPresenterImplFactory;
import com.rdc.project.traveltrace.utils.CollectionUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;

public class VideoNoteListManager implements IQueryContract.View<VideoNote> {

    private QueryPresenterImpl<VideoNote, VideoNoteQueryModel> mPresenter;

    private List<VideoNote> mVideoNoteList;

    private GetVideoNoteListCallback mGetVideoNoteListCallback;

    private VideoNoteListManager() {
        mPresenter = QueryPresenterImplFactory.createVideoNotePresenterImpl(this);
        mVideoNoteList = new ArrayList<>();
    }

    private static class VideoNoteListHolder {
        private static final VideoNoteListManager INSTANCE = new VideoNoteListManager();
    }

    public static VideoNoteListManager getInstance() {
        return VideoNoteListHolder.INSTANCE;
    }

    public void queryVideoNoteList(GetVideoNoteListCallback callback) {
        mGetVideoNoteListCallback = callback;
        User user = BmobUser.getCurrentUser(User.class);
        BmobPointer pointer = new BmobPointer(user);
        Map<String, Object> params = new HashMap<>();
        params.put("mUser", pointer);
        mPresenter.query(params);
    }

    public List<VideoNote> getVideoNoteList() {
        return mVideoNoteList;
    }

    @Override
    public void onQuerySuccess(List<VideoNote> list) {
        if (CollectionUtil.isEmpty(list)) {
            mVideoNoteList = Collections.emptyList();
        } else {
            mVideoNoteList.clear();
            mVideoNoteList.addAll(list);
        }
        onCallback();
    }

    @Override
    public void onQueryFailed(String response) {
        mVideoNoteList = Collections.emptyList();
        onCallback();
    }

    private void onCallback() {
        if (mGetVideoNoteListCallback != null) {
            VideoNoteList videoNoteList = new VideoNoteList(mVideoNoteList);
            mGetVideoNoteListCallback.onGetVideoNoteList(videoNoteList);
        }
    }

    public interface GetVideoNoteListCallback {

        void onGetVideoNoteList(VideoNoteList videoNoteList);

    }
}
