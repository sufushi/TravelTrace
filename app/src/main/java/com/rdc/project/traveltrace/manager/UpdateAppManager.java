package com.rdc.project.traveltrace.manager;

import com.rdc.project.traveltrace.contract.IQueryContract;
import com.rdc.project.traveltrace.entity.UpdateInfo;
import com.rdc.project.traveltrace.model.query.UpdateInfoQueryModelImpl;
import com.rdc.project.traveltrace.presenter.QueryPresenterImpl;
import com.rdc.project.traveltrace.presenter.query.QueryPresenterImplFactory;

import java.util.List;

public class UpdateAppManager implements IQueryContract.View<UpdateInfo> {

    private QueryPresenterImpl<UpdateInfo, UpdateInfoQueryModelImpl> mPresenter;

    private static final String APK_NAME = "travel_trace.apk";
    private static final String APK_DIR = "apk_download";

    private UpdateAppManager() {
        mPresenter = QueryPresenterImplFactory.createUpdateInfoPresenterImpl(this);
    }

    private static class UpdateAppHolder {
        private static final UpdateAppManager INSTANCE = new UpdateAppManager();
    }

    public static UpdateAppManager getInstance() {
        return UpdateAppHolder.INSTANCE;
    }

    public void checkUpdate() {
        mPresenter.query(null);
    }

    @Override
    public void onQuerySuccess(List<UpdateInfo> list) {

    }

    @Override
    public void onQueryFailed(String response) {

    }

}
