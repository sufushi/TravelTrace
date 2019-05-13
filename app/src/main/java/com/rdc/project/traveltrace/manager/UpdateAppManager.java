package com.rdc.project.traveltrace.manager;

import com.rdc.project.traveltrace.BuildConfig;
import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.app.App;
import com.rdc.project.traveltrace.contract.IQueryContract;
import com.rdc.project.traveltrace.entity.UpdateInfo;
import com.rdc.project.traveltrace.model.query.UpdateInfoQueryModelImpl;
import com.rdc.project.traveltrace.presenter.QueryPresenterImpl;
import com.rdc.project.traveltrace.presenter.query.QueryPresenterImplFactory;
import com.rdc.project.traveltrace.utils.CollectionUtil;
import com.rdc.project.traveltrace.utils.update.Updater;

import java.util.List;

public class UpdateAppManager implements IQueryContract.View<UpdateInfo> {

    private QueryPresenterImpl<UpdateInfo, UpdateInfoQueryModelImpl> mPresenter;

    private static final String APK_NAME = "travel_trace.apk";
    private static final String APK_DIR = "apk_download";

    private UpdateAppListener mUpdateAppListener;
    private UpdateInfo mUpdateInfo;

    private UpdateAppManager() {
        mPresenter = QueryPresenterImplFactory.createUpdateInfoPresenterImpl(this);
    }

    private static class UpdateAppHolder {
        private static final UpdateAppManager INSTANCE = new UpdateAppManager();
    }

    public static UpdateAppManager getInstance() {
        return UpdateAppHolder.INSTANCE;
    }

    public void checkUpdate(UpdateAppListener listener) {
        mUpdateAppListener = listener;
        mPresenter.query(null);
    }

    public void startUpdate() {
        if (mUpdateInfo != null) {
            new Updater.Builder(App.getAppContext())
                    .setDownloadUrl(mUpdateInfo.getApkUrl())
                    .setApkFileName(APK_NAME)
                    .setApkDir(APK_DIR)
                    .setNotificationTitle(App.getAppContext().getString(R.string.app_name))
                    .start();
        }
    }

    @Override
    public void onQuerySuccess(List<UpdateInfo> list) {
        if (CollectionUtil.isEmpty(list)) {
            if (mUpdateAppListener != null) {
                mUpdateAppListener.onUpdateInfo(false);
            }
        } else {
            boolean updatable = false;
            for (int i = 0; i < list.size(); i++) {
                UpdateInfo updateInfo = list.get(i);
                int versionCode = BuildConfig.VERSION_CODE;
                if (versionCode < updateInfo.getVersionCode()) {
                    mUpdateInfo = updateInfo;
                    updatable = true;
                    break;
                }
            }
            if (mUpdateAppListener != null) {
                mUpdateAppListener.onUpdateInfo(updatable);
            }
        }
    }

    @Override
    public void onQueryFailed(String response) {
        if (mUpdateAppListener != null) {
            mUpdateAppListener.onUpdateInfo(false);
        }
    }

    public interface UpdateAppListener {

        void onUpdateInfo(boolean updatable);

    }
}
