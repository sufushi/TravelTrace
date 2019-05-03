package com.rdc.project.traveltrace.arch.model;

import java.util.List;

public class ResponseInfo<DataType> {

    private int mErrorCode;
    private String mErrorMsg;
    private List<DataType> mDataList;

    public ResponseInfo() {

    }

    public int getErrorCode() {
        return mErrorCode;
    }

    public void setErrorCode(int errorCode) {
        this.mErrorCode = errorCode;
    }

    public String getErrorMsg() {
        return mErrorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        mErrorMsg = errorMsg;
    }

    public List<DataType> getDataList() {
        return mDataList;
    }

    public void setDataList(List<DataType> dataList) {
        mDataList = dataList;
    }
}
