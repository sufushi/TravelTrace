package com.rdc.project.traveltrace.presenter;

import com.rdc.project.traveltrace.base.mvp_base.BasePresenterImpl;
import com.rdc.project.traveltrace.contract.IUploadFileContract;
import com.rdc.project.traveltrace.model.UploadFileModelImpl;

import java.util.List;

public class UploadFilePresenterImpl extends BasePresenterImpl<UploadFileModelImpl> implements IUploadFileContract.Presenter {

    private IUploadFileContract.View mView;

    public UploadFilePresenterImpl(IUploadFileContract.View view) {
        super();
        mView = view;
    }

    @Override
    protected UploadFileModelImpl createModelImpl() {
        return new UploadFileModelImpl();
    }

    @Override
    public void uploadFile(List<String> fileList) {
        mModel.uploadFile(fileList, this);
    }

    @Override
    public void onUploadFileProgress(int curIndex, int curPercent, int total, int totalPercent) {
        mView.onUploadFileProgress(curIndex, curPercent, total, totalPercent);
    }

    @Override
    public void onUploadFileSuccess(List<String> urlList, String response) {
        mView.onUploadFileSuccess(urlList, response);
    }

    @Override
    public void onUploadFileFailed(String response) {
        mView.onUploadFileFailed(response);
    }
}
