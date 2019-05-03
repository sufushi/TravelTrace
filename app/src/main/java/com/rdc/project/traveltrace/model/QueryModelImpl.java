package com.rdc.project.traveltrace.model;

import android.util.Log;

import com.rdc.project.traveltrace.base.mvp_base.BaseModelImpl;
import com.rdc.project.traveltrace.contract.IQueryContract;

import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;

public abstract class QueryModelImpl<T extends BmobObject> extends BaseModelImpl implements IQueryContract.Model<T> {

    private static final String TAG = "QueryModelImpl";

    protected void addParams(BmobQuery<T> query, Map<String, Object> params) {
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                query.addWhereEqualTo(entry.getKey(), entry.getValue());
            }
        }
    }

    protected void onResponse(List<T> list, BmobException e, IQueryContract.Presenter<T> presenter) {
        if (e == null) {
            Log.i(TAG, "query success");
            presenter.onQuerySuccess(list);
        } else {
            Log.i(TAG, e.getMessage());
            presenter.onQueryFailed(e.getMessage());
        }
    }
}
