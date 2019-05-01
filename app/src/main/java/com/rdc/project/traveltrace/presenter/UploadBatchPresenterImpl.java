package com.rdc.project.traveltrace.presenter;

import com.rdc.project.traveltrace.base.mvp_base.BasePresenterImpl;
import com.rdc.project.traveltrace.contract.IUploadBatchContract;
import com.rdc.project.traveltrace.model.UploadBatchModelImpl;

import java.util.List;

import cn.bmob.v3.BmobObject;

public class UploadBatchPresenterImpl extends BasePresenterImpl<UploadBatchModelImpl> implements IUploadBatchContract.Presenter {

    private IUploadBatchContract.View mView;

    public UploadBatchPresenterImpl(IUploadBatchContract.View view) {
        super();
        mView = view;
    }

    @Override
    protected UploadBatchModelImpl createModelImpl() {
        return new UploadBatchModelImpl();
    }

    @Override
    public void uploadBatch(List<BmobObject> list) {
        mModel.uploadBatch(list, this);
    }

    @Override
    public void onUploadBatchSuccess(String response) {
        mView.onUploadBatchSuccess(response);
    }

    @Override
    public void onUploadBatchFailed(String response) {
        mView.onUploadBatchFailed(response);
    }
}
