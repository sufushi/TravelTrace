package com.rdc.project.traveltrace.arch.view_controller;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;

import com.rdc.project.traveltrace.arch.view_model.BaseData;
import com.rdc.project.traveltrace.arch.view_model.CommonViewModel;

public abstract class CommonObserverViewController<T> extends BaseObserverViewController<T> implements View.OnClickListener {

    private View mView;
    protected CommonViewModel<T> mCommonViewModel;

    public CommonObserverViewController(Context context) {
        mView = createView(context);
        if (mView != null) {
            mView.setOnClickListener(this);
        }
    }

    public void attachViewModel(CommonViewModel<T> viewModel) {
        mCommonViewModel = viewModel;
    }

    @Override
    protected void onChanged(@Nullable BaseData<T> data, View view) {
        if (data == null) {
            return;
        }
        T t = data.getData();
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

    protected abstract void onBindView(T data, View view);

    @Override
    public void onClick(View v) {

    }
}
