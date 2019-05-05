package com.rdc.project.traveltrace.model.query;

import com.rdc.project.traveltrace.contract.IQueryContract;
import com.rdc.project.traveltrace.entity.VideoNote;
import com.rdc.project.traveltrace.model.QueryModelImpl;

import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class VideoNoteQueryModel extends QueryModelImpl<VideoNote> {

    @Override
    public void query(Map<String, Object> params, final IQueryContract.Presenter<VideoNote> presenter) {
        BmobQuery<VideoNote> query = new BmobQuery<>();
        addParams(query, params);
        query.findObjects(new FindListener<VideoNote>() {
            @Override
            public void done(List<VideoNote> list, BmobException e) {
                onResponse(list, e, presenter);
            }
        });
    }
}
