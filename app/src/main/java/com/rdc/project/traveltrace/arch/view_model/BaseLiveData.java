package com.rdc.project.traveltrace.arch.view_model;

import android.arch.lifecycle.MutableLiveData;

public class BaseLiveData<T> extends MutableLiveData<T> implements IRecyclable {

    @Override
    public void recycler() {

    }
}
