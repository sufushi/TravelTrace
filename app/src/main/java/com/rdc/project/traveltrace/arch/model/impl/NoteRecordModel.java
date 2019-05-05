package com.rdc.project.traveltrace.arch.model.impl;

import android.util.Log;

import com.rdc.project.traveltrace.arch.model.BmobModel;
import com.rdc.project.traveltrace.entity.NoteRecord;
import com.rdc.project.traveltrace.utils.CollectionUtil;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class NoteRecordModel extends BmobModel<NoteRecord> {

    private INoteRecordModelListener mListener;

    private static final String TAG = "NoteRecordModel";

    @Override
    protected void sendQuery(final IModelListener<NoteRecord> listener) {
        BmobQuery<NoteRecord> query = new BmobQuery<>();
        query.include("mPlainNote.mUser,mPictureNote.mUser,mVideoNote.mUser");
        query.order("-createdAt");
        query.findObjects(new FindListener<NoteRecord>() {
            @Override
            public void done(List<NoteRecord> list, BmobException e) {
                onResponse(list, e, listener);
                Log.i(TAG, "onResponse list size:" + (CollectionUtil.isEmpty(list) ? 0 : list.size()));
                if (mListener != null) {
                    if (CollectionUtil.isEmpty(list)) {
                        mListener.onLoadFinish(0);
                    } else {
                        mListener.onLoadFinish(1);
                    }
                }
            }
        });
    }

    public void register(INoteRecordModelListener listener) {
        mListener = listener;
    }

    public interface INoteRecordModelListener {

        void onLoadFinish(int errorCode);

    }
}
