package com.rdc.project.traveltrace.model.query;

import com.rdc.project.traveltrace.contract.IQueryContract;
import com.rdc.project.traveltrace.entity.UpdateInfo;
import com.rdc.project.traveltrace.model.QueryModelImpl;

import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class UpdateInfoQueryModelImpl extends QueryModelImpl<UpdateInfo> {

    @Override
    public void query(Map<String, Object> params, final IQueryContract.Presenter<UpdateInfo> presenter) {
        BmobQuery<UpdateInfo> query = new BmobQuery<>();
        addParams(query, params);
        query.order("-updatedAt");
        query.findObjects(new FindListener<UpdateInfo>() {
            @Override
            public void done(List<UpdateInfo> list, BmobException e) {
                onResponse(list, e, presenter);
            }
        });
    }
}
