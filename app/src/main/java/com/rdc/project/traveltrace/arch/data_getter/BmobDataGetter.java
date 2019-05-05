package com.rdc.project.traveltrace.arch.data_getter;

import android.util.Log;

import com.rdc.project.traveltrace.arch.model.BmobModel;
import com.rdc.project.traveltrace.arch.model.IModel;
import com.rdc.project.traveltrace.arch.model.ResponseInfo;
import com.rdc.project.traveltrace.arch.view_model.BaseData;
import com.rdc.project.traveltrace.arch.view_model.BaseLiveData;

import cn.bmob.v3.BmobObject;

public class BmobDataGetter<T extends BmobObject, Model extends BmobModel<T>> extends BaseDataGetter<ResponseInfo<T>> {

    private static final String TAG = "BmobDataGetter";

    private Model mModel;
    private BaseLiveData<BaseData<ResponseInfo<T>>> mLiveData;

    public BmobDataGetter(Model model) {
        mModel = model;
    }

    @Override
    public BaseLiveData<BaseData<ResponseInfo<T>>> getDataSource() {
        if (mLiveData == null) {
            mLiveData = new BaseLiveData<>();
        }
        if (mModel == null) {
            return mLiveData;
        }
        mModel.sendRequest(new IModel.IModelListener<T>() {
            @Override
            public void onFinish(ResponseInfo<T> responseInfo) {
                BaseData<ResponseInfo<T>> baseData = new BaseData<>();
                baseData.setData(responseInfo);
                mLiveData.postValue(baseData);
            }
        });
        return mLiveData;
    }

}
