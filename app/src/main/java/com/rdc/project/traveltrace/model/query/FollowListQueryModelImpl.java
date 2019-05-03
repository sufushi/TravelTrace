package com.rdc.project.traveltrace.model.query;

import com.rdc.project.traveltrace.contract.IQueryContract;
import com.rdc.project.traveltrace.entity.FollowList;
import com.rdc.project.traveltrace.model.QueryModelImpl;

import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class FollowListQueryModelImpl extends QueryModelImpl<FollowList> {

    @Override
    public void query(Map<String, Object> params, final IQueryContract.Presenter<FollowList> presenter) {
        BmobQuery<FollowList> query = new BmobQuery<>();
        addParams(query, params);
        query.findObjects(new FindListener<FollowList>() {
            @Override
            public void done(List<FollowList> list, BmobException e) {
                onResponse(list, e, presenter);
            }
        });
    }
}
