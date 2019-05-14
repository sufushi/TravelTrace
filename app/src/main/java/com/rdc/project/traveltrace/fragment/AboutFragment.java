package com.rdc.project.traveltrace.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.LinearLayout;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.app.App;
import com.rdc.project.traveltrace.base.BaseBounceFragment;
import com.rdc.project.traveltrace.base.BaseSwipeAwayDialogFragment;
import com.rdc.project.traveltrace.manager.UpdateAppManager;
import com.rdc.project.traveltrace.utils.SharePreferenceUtil;
import com.rdc.project.traveltrace.utils.UriUtil;
import com.rdc.project.traveltrace.utils.action.Action;
import com.rdc.project.traveltrace.utils.action.ActionManager;
import com.rdc.project.traveltrace.view.swipe_away_dialog.dialog_factory.DialogFactory;
import com.rdc.project.traveltrace.view.swipe_away_dialog.dialog_factory.UpdateAppDialog;
import com.rdc.project.traveltrace.view.toast.CommonToast;

import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_FIELD_URL;
import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_NAME_H5;
import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_PRE;
import static com.rdc.project.traveltrace.utils.update.Updater.DOWNLOADING_APK_KEY;

public class AboutFragment extends BaseBounceFragment implements View.OnClickListener, UpdateAppManager.UpdateAppListener {

    private static final String PAGE_ABOUT_AUTHOR = "https://github.com/sufushi";

    private LinearLayout mPersonAboutLayout;
    private LinearLayout mVersionUpdateLayout;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_about;
    }

    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    protected void initView() {
        mPersonAboutLayout = mRootView.findViewById(R.id.ll_about_author);
        mVersionUpdateLayout = mRootView.findViewById(R.id.ll_version_update);
    }

    @Override
    protected void setListener() {
        mPersonAboutLayout.setOnClickListener(this);
        mVersionUpdateLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_about_author:
                Action action = new Action(ACTION_PRE + ACTION_NAME_H5 + "?" + ACTION_FIELD_URL + "=" + UriUtil.encode(PAGE_ABOUT_AUTHOR));
                ActionManager.doAction(action, getActivity());
                break;
            case R.id.ll_version_update:
                boolean isDownloading = (boolean) SharePreferenceUtil.get(App.getAppContext(), DOWNLOADING_APK_KEY, false);
                if (isDownloading) {
                    CommonToast.info(Objects.requireNonNull(getActivity()), "正在下载中...").show();
                    return;
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    boolean hasInstallPermission = Objects.requireNonNull(getActivity()).getPackageManager().canRequestPackageInstalls();
                    if (hasInstallPermission) {
                        UpdateAppManager.getInstance().checkUpdate(this);
                    } else {
                        Uri packageURI = Uri.parse("package:" + Objects.requireNonNull(getActivity()).getPackageName());
                        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
                        getActivity().startActivityForResult(intent, 0);
                    }
                } else {
                    UpdateAppManager.getInstance().checkUpdate(this);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onUpdateInfo(boolean updatable) {
        if (updatable) {
            BaseSwipeAwayDialogFragment.newInstance(DialogFactory.createUpdateAppDialog(new UpdateAppDialog.UpdateAppConfirmListener() {
                @Override
                public void onConfirm(boolean isPositive) {
                    if (isPositive) {
                        UpdateAppManager.getInstance().startUpdate();
                    }
                }
            }), null).show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "updateApp");
        } else {
            CommonToast.info(Objects.requireNonNull(getActivity()), "当前已是最新版本").show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 0) {
            UpdateAppManager.getInstance().checkUpdate(this);
        }
    }
}
