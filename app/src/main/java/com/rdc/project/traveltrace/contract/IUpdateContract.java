package com.rdc.project.traveltrace.contract;

import cn.bmob.v3.BmobObject;

public interface IUpdateContract {

    interface Model<T extends BmobObject> {
        void update(T t, IUpdateContract.Presenter presenter);
    }

    interface View {
        void onUpdateSuccess(String response);
        void onUpdateFailed(String response);
    }

    interface Presenter<T extends BmobObject> {
        void update(T t);
        void onUpdateSuccess(String response);
        void onUpdateFailed(String response);
    }

}
