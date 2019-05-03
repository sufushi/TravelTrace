package com.rdc.project.traveltrace.arch.model;

public class BaseModel<T> implements IModel<T> {

    public static final int RESULT_CODE_SUCCESS = 0;
    public static final int RESULT_CODE_FAILED = 1;

    @Override
    public void sendRequest(IModelListener<T> listener) {

    }

}
