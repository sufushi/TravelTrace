package com.rdc.project.traveltrace.arch.repository;

import com.rdc.project.traveltrace.arch.data_getter.IDataGetter;
import com.rdc.project.traveltrace.arch.view_model.BaseData;
import com.rdc.project.traveltrace.arch.view_model.BaseLiveData;

public abstract class BaseRepository<T> implements ICloseable {

    private IDataGetter<T> mDataGetter;

    public BaseRepository() {
        mDataGetter = createDataGetter();
    }

    protected abstract IDataGetter<T> createDataGetter();

    public BaseLiveData<BaseData<T>> getDataSource() {
        if (mDataGetter != null) {
            return mDataGetter.getDataSource();
        }
        return null;
    }

}
