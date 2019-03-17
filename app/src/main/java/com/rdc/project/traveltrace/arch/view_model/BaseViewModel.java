package com.rdc.project.traveltrace.arch.view_model;

import android.arch.lifecycle.ViewModel;

import com.rdc.project.traveltrace.arch.repository.BaseRepository;

public abstract class BaseViewModel<T> extends ViewModel {

    private BaseLiveData<BaseData<T>> mLiveData;
    private BaseRepository<T> mRepository;

    public BaseViewModel() {
        initRepository();
        initLiveData();
    }

    private void initLiveData() {
        if (mLiveData != null) {
            return;
        }
        mLiveData = getDataSource();
    }

    private void initRepository() {
        mRepository = createRepository();
    }

    protected abstract BaseRepository<T> createRepository();

    private BaseLiveData<BaseData<T>> getDataSource() {
        if (mRepository != null) {
            return mRepository.getDataSource();
        }
        return null;
    }

    public BaseLiveData<BaseData<T>> getLiveData() {
        return mLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (mLiveData != null) {
            mLiveData.recycler();
        }
        if (mRepository != null) {
            mRepository.close();
        }
    }
}
