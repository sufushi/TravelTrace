package com.rdc.project.traveltrace.arch.repository;

import com.rdc.project.traveltrace.arch.view_model.BaseData;

public interface IDataListener<T> {

    void onDataResponse(BaseData<T> data);

}
