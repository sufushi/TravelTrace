package com.rdc.project.traveltrace.arch.view_model;

import com.rdc.project.traveltrace.arch.repository.BaseRepository;
import com.rdc.project.traveltrace.arch.repository.CommonRepository;

public class CommonViewModel<T> extends BaseViewModel<T> {

    public CommonViewModel() {
        super();
    }

    @Override
    protected BaseRepository<T> createRepository() {
        return new CommonRepository<>();
    }


}
