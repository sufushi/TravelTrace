package com.rdc.project.traveltrace.arch.repository;

import com.rdc.project.traveltrace.arch.data_getter.BaseDataGetter;
import com.rdc.project.traveltrace.arch.data_getter.IDataGetter;

public class CommonRepository<T> extends BaseRepository<T> {

    public CommonRepository() {
        init();
    }

    @Override
    protected IDataGetter<T> createDataGetter() {
        return new BaseDataGetter<>();
    }

    @Override
    public void close() {

    }
}
