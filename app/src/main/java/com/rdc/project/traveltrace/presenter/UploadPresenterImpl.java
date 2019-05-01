package com.rdc.project.traveltrace.presenter;

import com.rdc.project.traveltrace.base.mvp_base.BasePresenterImpl;
import com.rdc.project.traveltrace.contract.IUploadContract;
import com.rdc.project.traveltrace.model.UploadModelImpl;

import cn.bmob.v3.BmobObject;

public class UploadPresenterImpl<T extends BmobObject> extends BasePresenterImpl<UploadModelImpl<T>> implements IUploadContract.Presenter<T> {

    private IUploadContract.View mView;

    public UploadPresenterImpl(IUploadContract.View view) {
        super();
        mView = view;
    }

    @Override
    protected UploadModelImpl<T> createModelImpl() {
        return new UploadModelImpl<>();
    }

    @Override
    public void upload(T t) {
        mModel.upload(t, this);
    }

    @Override
    public void onUploadSuccess(String response) {
        mView.onUploadSuccess(response);
    }

    @Override
    public void onUploadFailed(String response) {
        mView.onUploadFailed(response);
    }
}
