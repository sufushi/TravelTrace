package com.rdc.project.traveltrace.base.mvp_base;

public abstract class BasePresenterImpl<M extends BaseModelImpl> {

    protected M mModel;

    public BasePresenterImpl() {
        mModel = createModelImpl();
    }

    protected abstract M createModelImpl();
}
