package com.rdc.project.traveltrace.arch.repository;

import com.rdc.project.traveltrace.arch.data_getter.BmobDataGetter;
import com.rdc.project.traveltrace.arch.data_getter.IDataGetter;
import com.rdc.project.traveltrace.arch.model.BmobModel;
import com.rdc.project.traveltrace.arch.model.ResponseInfo;

import cn.bmob.v3.BmobObject;

public class BmobRepository<T extends BmobObject, Model extends BmobModel<T>> extends BaseRepository<ResponseInfo<T>> {

    private Model mModel;

    public BmobRepository(Model model) {
        mModel = model;
        init();
    }

    @Override
    protected IDataGetter<ResponseInfo<T>> createDataGetter() {
        return new BmobDataGetter<>(mModel);
    }

    @Override
    public void close() {

    }
}
