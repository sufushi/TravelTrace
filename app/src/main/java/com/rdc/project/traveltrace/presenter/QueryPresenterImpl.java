package com.rdc.project.traveltrace.presenter;

import com.rdc.project.traveltrace.contract.IQueryContract;
import com.rdc.project.traveltrace.model.QueryModelImpl;

import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobObject;

public class QueryPresenterImpl<T extends BmobObject, Model extends QueryModelImpl<T>> implements IQueryContract.Presenter<T> {

    private Model mModel;
    private IQueryContract.View<T> mView;

    public QueryPresenterImpl(Model model, IQueryContract.View<T> view) {
        mModel = model;
        mView = view;
    }

    @Override
    public void query(Map<String, Object> params) {
        mModel.query(params, this);
    }

    @Override
    public void onQuerySuccess(List<T> list) {
        mView.onQuerySuccess(list);
    }

    @Override
    public void onQueryFailed(String response) {
        mView.onQueryFailed(response);
    }

}
