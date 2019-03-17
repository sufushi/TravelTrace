package com.rdc.project.traveltrace.arch.view_model;

import android.arch.lifecycle.LifecycleOwner;

import com.rdc.project.traveltrace.arch.view_controller.BaseObserverViewController;

public class ViewModelBinder {

    public <T> void bind(LifecycleOwner lifecycleOwner, CommonViewModel<T> viewModel, BaseObserverViewController<T> view) {
        if (lifecycleOwner == null || viewModel == null || view == null) {
            return;
        }
        viewModel.getLiveData().observe(lifecycleOwner, view);
    }

//    public <T> void bind(LifecycleOwner lifecycleOwner, CommonViewModel<T> viewModel, Observer<BaseData<T>> observer) {
//        if (lifecycleOwner == null || viewModel == null || observer == null) {
//            return;
//        }
//        viewModel.getLiveData().observe(lifecycleOwner, observer);
//    }

}
