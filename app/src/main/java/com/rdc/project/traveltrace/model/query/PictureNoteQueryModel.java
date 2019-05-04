package com.rdc.project.traveltrace.model.query;

import com.rdc.project.traveltrace.contract.IQueryContract;
import com.rdc.project.traveltrace.entity.PictureNote;
import com.rdc.project.traveltrace.model.QueryModelImpl;

import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class PictureNoteQueryModel extends QueryModelImpl<PictureNote> {

    @Override
    public void query(Map<String, Object> params, final IQueryContract.Presenter<PictureNote> presenter) {
        BmobQuery<PictureNote> query = new BmobQuery<>();
        addParams(query, params);
        query.findObjects(new FindListener<PictureNote>() {
            @Override
            public void done(List<PictureNote> list, BmobException e) {
                onResponse(list, e, presenter);
            }
        });
    }
}
