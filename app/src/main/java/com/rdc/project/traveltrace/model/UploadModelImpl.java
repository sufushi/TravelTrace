package com.rdc.project.traveltrace.model;

import com.rdc.project.traveltrace.base.mvp_base.BaseModelImpl;
import com.rdc.project.traveltrace.contract.IUploadContract;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class UploadModelImpl<T extends BmobObject> extends BaseModelImpl implements IUploadContract.Model<T>{

    @Override
    public void upload(T t, final IUploadContract.Presenter presenter) {
        t.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    presenter.onUploadSuccess(s);
                } else {
                    presenter.onUploadFailed(e.getMessage());
                }
            }
        });
    }
}
