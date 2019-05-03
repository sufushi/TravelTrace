package com.rdc.project.traveltrace.arch.model.impl;

import com.rdc.project.traveltrace.arch.model.BmobModel;
import com.rdc.project.traveltrace.entity.NoteRecord;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class NoteRecordModel extends BmobModel<NoteRecord> {

    @Override
    protected void sendQuery(final IModelListener<NoteRecord> listener) {
        BmobQuery<NoteRecord> query = new BmobQuery<>();
        query.include("mPlainNote.mUser,mPictureNote.mUser,mVideoNote.mUser");
        query.order("-createdAt");
        query.findObjects(new FindListener<NoteRecord>() {
            @Override
            public void done(List<NoteRecord> list, BmobException e) {
                onResponse(list, e, listener);
            }
        });
    }
}
