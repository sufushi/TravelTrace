package com.rdc.project.traveltrace.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.app.App;
import com.rdc.project.traveltrace.base.BaseBounceFragment;
import com.rdc.project.traveltrace.utils.FileSizeUtil;
import com.rdc.project.traveltrace.utils.FileUtil;
import com.rdc.project.traveltrace.utils.action.Action;
import com.rdc.project.traveltrace.utils.action.ActionManager;
import com.rdc.project.traveltrace.view.toast.CommonToast;

import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.schedulers.Schedulers;

import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_FIELD_SETTING_PIN;
import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_NAME_GESTURE_PIN;
import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_PRE;

public class SettingFragment extends BaseBounceFragment implements View.OnClickListener {

    private RelativeLayout mGesturePinLayout;
    private RelativeLayout mCleanCacheLayout;
    private TextView mCacheView;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    protected void initView() {
        mGesturePinLayout = mRootView.findViewById(R.id.layout_gesture_pin);
        mCleanCacheLayout = mRootView.findViewById(R.id.layout_clean_cache);
        mCacheView = mRootView.findViewById(R.id.tv_cache);

        String[] cachePaths = new String[]{FileUtil.getInternalCacheDir(App.getAppContext()),
                FileUtil.getExternalCacheDir(App.getAppContext())};
        Observable.just(cachePaths)
                .map(new Function<String[], String>() {
                    @Override
                    public String apply(String[] strings) throws Exception {
                        return FileSizeUtil.getAutoFileOrFilesSize(strings);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<String>() {
                    @Override
                    public void onNext(String s) {
                        mCacheView.setText(s);
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void setListener() {
        mGesturePinLayout.setOnClickListener(this);
        mCleanCacheLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_gesture_pin:
                Action action = new Action(ACTION_PRE + ACTION_NAME_GESTURE_PIN + "?" + ACTION_FIELD_SETTING_PIN + "=" + "setting");
                ActionManager.doAction(action, getActivity());
                break;
            case R.id.layout_clean_cache:
                Observable.just(FileUtil.delete(FileUtil.getInternalCacheDir(App.getAppContext())))
                        .map(new Function<Boolean, Boolean>() {
                            @Override
                            public Boolean apply(Boolean result) throws Exception {
                                return result && FileUtil.delete(FileUtil.getExternalCacheDir(App.getAppContext()));
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new DefaultObserver<Boolean>() {
                            @Override
                            public void onNext(Boolean aBoolean) {
                                mCacheView.setText(FileSizeUtil.getAutoFileOrFilesSize(FileUtil.getInternalCacheDir(App.getAppContext()),
                                        FileUtil.getExternalCacheDir(App.getAppContext())));
                                CommonToast.info(Objects.requireNonNull(getActivity()), "缓存清理成功").show();
                            }

                            @Override
                            public void onError(Throwable throwable) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                break;
            default:
                break;
        }
    }
}
