package com.rdc.project.traveltrace.presenter;

import com.rdc.project.traveltrace.base.mvp_base.BasePresenterImpl;
import com.rdc.project.traveltrace.contract.IUpdateContract;
import com.rdc.project.traveltrace.model.UpdateModelImpl;

import cn.bmob.v3.BmobObject;

public class UpdatePresenterImpl<T extends BmobObject> extends BasePresenterImpl<UpdateModelImpl<T>> implements IUpdateContract.Presenter<T> {

    private IUpdateContract.View mView;

    public UpdatePresenterImpl(IUpdateContract.View view) {
        super();
        mView = view;
    }

    @Override
    protected UpdateModelImpl<T> createModelImpl() {
        return new UpdateModelImpl<>();
    }

    @Override
    public void update(T t) {
        mModel.update(t, this);
    }

    @Override
    public void onUpdateSuccess(String response) {
        mView.onUpdateSuccess(response);
    }

    @Override
    public void onUpdateFailed(String response) {
        mView.onUpdateFailed(response);
    }
}
