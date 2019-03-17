package com.rdc.project.traveltrace.arch.data_getter;

import com.rdc.project.traveltrace.arch.view_model.BaseData;
import com.rdc.project.traveltrace.arch.view_model.BaseLiveData;

public interface IDataGetter<T> {

    BaseLiveData<BaseData<T>> getDataSource();

}
