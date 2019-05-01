package com.rdc.project.traveltrace.contract;

import java.util.List;

import cn.bmob.v3.BmobObject;

public interface IUploadBatchContract {

    interface Model {
        void uploadBatch(List<BmobObject> list, Presenter presenter);
    }

    interface View {
        void onUploadBatchSuccess(String response);
        void onUploadBatchFailed(String response);
    }

    interface Presenter {
        void uploadBatch(List<BmobObject> list);
        void onUploadBatchSuccess(String response);
        void onUploadBatchFailed(String response);
    }

}
