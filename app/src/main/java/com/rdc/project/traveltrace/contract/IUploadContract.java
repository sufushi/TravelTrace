package com.rdc.project.traveltrace.contract;

import cn.bmob.v3.BmobObject;

public interface IUploadContract {

    interface Model<T extends BmobObject> {
        void upload(T t, Presenter presenter);
    }

    interface View {
        void onUploadSuccess(String response);
        void onUploadFailed(String response);
    }

    interface Presenter<T extends BmobObject> {
        void upload(T t);
        void onUploadSuccess(String response);
        void onUploadFailed(String response);
    }

}
