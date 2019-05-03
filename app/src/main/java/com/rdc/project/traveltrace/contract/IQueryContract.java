package com.rdc.project.traveltrace.contract;

import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobObject;

public interface IQueryContract {

    interface Model<T extends BmobObject> {
        void query(Map<String, Object> params, Presenter<T> presenter);
    }

    interface View<T extends BmobObject> {
        void onQuerySuccess(List<T> list);
        void onQueryFailed(String response);
    }

    interface Presenter<T extends BmobObject> {
        void query(Map<String, Object> params);
        void onQuerySuccess(List<T> list);
        void onQueryFailed(String response);
    }

}
