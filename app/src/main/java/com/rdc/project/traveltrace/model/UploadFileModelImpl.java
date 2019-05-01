package com.rdc.project.traveltrace.model;

import com.rdc.project.traveltrace.base.mvp_base.BaseModelImpl;
import com.rdc.project.traveltrace.contract.IUploadFileContract;
import com.rdc.project.traveltrace.utils.CollectionUtil;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UploadBatchListener;

public class UploadFileModelImpl extends BaseModelImpl implements IUploadFileContract.Model {

    @Override
    public void uploadFile(List<String> fileList, IUploadFileContract.Presenter presenter) {
        if (CollectionUtil.isEmpty(fileList)) {
            return;
        }
        String[] paths = new String[fileList.size()];
        fileList.toArray(paths);
        BmobFile.uploadBatch(paths, new UploadBatchListener() {
            @Override
            public void onSuccess(List<BmobFile> files, List<String> urls) {
                if (urls.size() == paths.length) {
                    presenter.onUploadFileSuccess(urls, "upload file success");
                } else {
                    int curIndex = urls.size();
                    int total = paths.length;
                    int curPercent = (int) (curIndex * 1.0f / total * 100);
                    int totalPercent = 100;
                    onProgress(curIndex, curPercent, total, totalPercent);
                }
            }

            @Override
            public void onProgress(int curIndex, int curPercent, int total, int totalPercent) {
                //1、curIndex--表示当前第几个文件正在上传
                //2、curPercent--表示当前上传文件的进度值（百分比）
                //3、total--表示总的上传文件数
                //4、totalPercent--表示总的上传进度（百分比）
                presenter.onUploadFileProgress(curIndex, curPercent, total, totalPercent);
            }

            @Override
            public void onError(int i, String s) {
                presenter.onUploadFileFailed(s);
            }
        });
    }
}
