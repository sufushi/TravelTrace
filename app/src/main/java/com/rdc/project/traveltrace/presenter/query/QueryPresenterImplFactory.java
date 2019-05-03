package com.rdc.project.traveltrace.presenter.query;

import com.rdc.project.traveltrace.contract.IQueryContract;
import com.rdc.project.traveltrace.entity.FollowList;
import com.rdc.project.traveltrace.model.query.FollowListQueryModelImpl;
import com.rdc.project.traveltrace.presenter.QueryPresenterImpl;

public class QueryPresenterImplFactory {

    public static QueryPresenterImpl<FollowList, FollowListQueryModelImpl> createFollowListPresenterImpl(IQueryContract.View<FollowList> view) {
        return new QueryPresenterImpl<>(new FollowListQueryModelImpl(), view);
    }

}
