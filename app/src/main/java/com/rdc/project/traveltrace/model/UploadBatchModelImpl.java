package com.rdc.project.traveltrace.model;

import com.rdc.project.traveltrace.base.mvp_base.BaseModelImpl;
import com.rdc.project.traveltrace.contract.IUploadBatchContract;

import java.util.List;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListListener;

public class UploadBatchModelImpl extends BaseModelImpl implements IUploadBatchContract.Model {

    @Override
    public void uploadBatch(List<BmobObject> objectList, IUploadBatchContract.Presenter presenter) {
        BmobBatch bmobBatch = new BmobBatch();
        bmobBatch.insertBatch(objectList).doBatch(new QueryListListener<BatchResult>() {
            @Override
            public void done(List<BatchResult> resultList, BmobException e) {
                if (e == null) {
                    for (int i = 0; i < resultList.size(); i++) {
                        BatchResult result = resultList.get(i);
                        BmobException bmobException = result.getError();
                        if (bmobException == null) {
                            presenter.onUploadBatchSuccess(String.valueOf(i));
                        } else {
                            presenter.onUploadBatchFailed(bmobException.getMessage());
                        }
                    }
                } else {
                    presenter.onUploadBatchFailed(e.getMessage());
                }
            }
        });
    }
}
