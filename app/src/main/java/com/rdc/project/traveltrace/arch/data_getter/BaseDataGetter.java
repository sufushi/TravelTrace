package com.rdc.project.traveltrace.arch.data_getter;

import com.rdc.project.traveltrace.arch.view_model.BaseData;
import com.rdc.project.traveltrace.arch.view_model.BaseLiveData;

public class BaseDataGetter<T> implements IDataGetter<T> {

    @Override
    public BaseLiveData<BaseData<T>> getDataSource() {
        return new BaseLiveData<>();
    }

}
