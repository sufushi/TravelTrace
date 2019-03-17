package com.rdc.project.traveltrace.arch.view_controller;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.view.View;

import com.rdc.project.traveltrace.arch.view_model.BaseData;

public abstract class BaseObserverViewController<T> implements IBaseViewController, Observer<BaseData<T>> {

    @Override
    public void onChanged(@Nullable BaseData<T> data) {
        onChanged(data, getView());
    }

    protected abstract void onChanged(@Nullable BaseData<T> data, View view);

}
