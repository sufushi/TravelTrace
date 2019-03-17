package com.rdc.project.traveltrace.arch.view_model;

public class BaseData<T> {

    public BaseData() {

    }

    public BaseData(T data) {
        mData = data;
    }

    private T mData;

    public T getData() {
        return mData;
    }

    public void setData(T data) {
        mData = data;
    }
}
