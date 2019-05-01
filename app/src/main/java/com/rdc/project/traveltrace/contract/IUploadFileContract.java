package com.rdc.project.traveltrace.contract;

import java.util.List;

public interface IUploadFileContract {

    interface Model {
        void uploadFile(List<String> fileList, Presenter presenter);
    }

    interface View {
        void onUploadFileProgress(int curIndex, int curPercent, int total, int totalPercent);
        void onUploadFileSuccess(List<String> urlList, String response);
        void onUploadFileFailed(String response);
    }

    interface Presenter {
        void uploadFile(List<String> fileList);
        void onUploadFileProgress(int curIndex, int curPercent, int total, int totalPercent);
        void onUploadFileSuccess(List<String> urlList, String response);
        void onUploadFileFailed(String response);
    }

}
