package com.rdc.project.traveltrace.model;

import com.rdc.project.traveltrace.base.mvp_base.BaseModelImpl;
import com.rdc.project.traveltrace.contract.IUpdateContract;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class UpdateModelImpl<T extends BmobObject> extends BaseModelImpl implements IUpdateContract.Model<T> {

    @Override
    public void update(T t, final IUpdateContract.Presenter presenter) {
        t.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    presenter.onUpdateSuccess("update success");
                } else {
                    presenter.onUpdateFailed(e.getMessage());
                }
            }
        });
    }
}
