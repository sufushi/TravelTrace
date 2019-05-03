package com.rdc.project.traveltrace.arch.model;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.exception.BmobException;

public abstract class BmobModel<T extends BmobObject> extends BaseModel<T> {

    @Override
    public void sendRequest(IModelListener<T> listener) {
        sendQuery(listener);
    }

    protected abstract void sendQuery(IModelListener<T> listener);

    protected void onResponse(List<T> list, BmobException e, IModelListener<T> listener) {
        ResponseInfo<T> responseInfo = new ResponseInfo<>();
        if (e == null) {
            responseInfo.setErrorCode(RESULT_CODE_SUCCESS);
            responseInfo.setErrorMsg("query success");
            responseInfo.setDataList(list);
        } else {
            responseInfo.setErrorCode(RESULT_CODE_FAILED);
            responseInfo.setErrorMsg("query failed:" + e.getMessage());
            responseInfo.setDataList(null);
        }
        if (listener != null) {
            listener.onFinish(responseInfo);
        }
    }
}
