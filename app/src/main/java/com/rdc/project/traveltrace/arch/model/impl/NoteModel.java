package com.rdc.project.traveltrace.arch.model.impl;

import com.rdc.project.traveltrace.arch.model.BmobModel;
import com.rdc.project.traveltrace.entity.Note;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class NoteModel extends BmobModel<Note> {

    @Override
    protected void sendQuery(final IModelListener<Note> listener) {
        BmobQuery<Note> bmobQuery = new BmobQuery<>();
        bmobQuery.order("-createdAt");
        bmobQuery.findObjects(new FindListener<Note>() {
            @Override
            public void done(List<Note> list, BmobException e) {
                onResponse(list, e, listener);
            }
        });
    }
}
