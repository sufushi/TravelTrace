package com.rdc.project.traveltrace.arch.view_controller;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;

import com.rdc.project.traveltrace.arch.model.BmobModel;
import com.rdc.project.traveltrace.arch.model.ResponseInfo;
import com.rdc.project.traveltrace.arch.view_model.BaseData;
import com.rdc.project.traveltrace.arch.view_model.BmobViewModel;

import cn.bmob.v3.BmobObject;

public abstract class BmobObserverViewController<T extends BmobObject, Model extends BmobModel<T>> extends BaseObserverViewController<ResponseInfo<T>> {

    private View mView;
    private BmobViewModel<T, Model> mBmobViewModel;

    public BmobObserverViewController(Context context) {
        mView = createView(context);
    }

    public void attachViewModel(BmobViewModel<T, Model> viewModel) {
        mBmobViewModel = viewModel;
    }

    @Override
    protected void onChanged(@Nullable BaseData<ResponseInfo<T>> data, View view) {
        if (data == null) {
            return;
        }
        ResponseInfo<T> t = data.getData();
        onBindView(t, view);
    }

    @Override
    public View getView() {
        return mView;
    }

    private View createView(Context context) {
        return onCreateView(context);
    }

    protected abstract View onCreateView(Context context);

    protected abstract void onBindView(ResponseInfo<T> data, View view);
}
