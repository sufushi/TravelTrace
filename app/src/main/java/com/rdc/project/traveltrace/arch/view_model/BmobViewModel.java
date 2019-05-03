package com.rdc.project.traveltrace.arch.view_model;

import android.util.Log;

import com.rdc.project.traveltrace.arch.model.BmobModel;
import com.rdc.project.traveltrace.arch.model.ResponseInfo;
import com.rdc.project.traveltrace.arch.repository.BaseRepository;
import com.rdc.project.traveltrace.arch.repository.BmobRepository;

import cn.bmob.v3.BmobObject;

public class BmobViewModel<T extends BmobObject, Model extends BmobModel<T>> extends BaseViewModel<ResponseInfo<T>> {

    private Model mModel;

    public BmobViewModel() {

    }

    public BmobViewModel(Model model) {
        mModel = model;
        init();
    }

    @Override
    protected BaseRepository<ResponseInfo<T>> createRepository() {
        return new BmobRepository<>(mModel);
    }
}
