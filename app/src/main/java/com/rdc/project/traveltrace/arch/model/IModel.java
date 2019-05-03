package com.rdc.project.traveltrace.arch.model;

public interface IModel<T> {

    void sendRequest(IModelListener<T> listener);

    interface IModelListener<T> {

        void onFinish(ResponseInfo<T> responseInfo);

    }
}
