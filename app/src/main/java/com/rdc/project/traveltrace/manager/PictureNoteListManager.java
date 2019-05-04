package com.rdc.project.traveltrace.manager;

import com.rdc.project.traveltrace.contract.IQueryContract;
import com.rdc.project.traveltrace.entity.PictureNote;
import com.rdc.project.traveltrace.entity.User;
import com.rdc.project.traveltrace.model.query.PictureNoteQueryModel;
import com.rdc.project.traveltrace.presenter.QueryPresenterImpl;
import com.rdc.project.traveltrace.presenter.query.QueryPresenterImplFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;

public class PictureNoteListManager implements IQueryContract.View<PictureNote> {

    private QueryPresenterImpl<PictureNote, PictureNoteQueryModel> mPresenter;

    private List<PictureNote> mPictureNoteList;

    private GetPictureUrlCallback mGetPictureUrlCallback;

    private PictureNoteListManager() {
        mPresenter = QueryPresenterImplFactory.createPictureNotePresenterImpl(this);
        mPictureNoteList = new ArrayList<>();
    }

    private static class PictureNoteListHolder {
        private static final PictureNoteListManager INSTANCE = new PictureNoteListManager();
    }

    public static PictureNoteListManager getInstance() {
        return PictureNoteListHolder.INSTANCE;
    }

    public void queryPictureNoteList(GetPictureUrlCallback callback) {
        mGetPictureUrlCallback = callback;
        User user = BmobUser.getCurrentUser(User.class);
        BmobPointer pointer = new BmobPointer(user);
        Map<String, Object> params = new HashMap<>();
        params.put("mUser", pointer);
        mPresenter.query(params);
    }

    public List<PictureNote> getPictureNoteList() {
        return mPictureNoteList;
    }

    @Override
    public void onQuerySuccess(List<PictureNote> list) {
        mPictureNoteList.clear();
        mPictureNoteList.addAll(list);
        List<String> urls = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            PictureNote pictureNote = list.get(i);
            List<String> imgUrls = pictureNote.getImgUrls();
            for (int j = 0; j < imgUrls.size(); j++) {
                urls.add(imgUrls.get(j));
                if (urls.size() >= 10) {
                    break;
                }
            }
        }
        if (mGetPictureUrlCallback != null) {
            mGetPictureUrlCallback.onGetPictureUrls(urls);
        }
    }

    @Override
    public void onQueryFailed(String response) {
        if (mGetPictureUrlCallback != null) {
            mGetPictureUrlCallback.onGetPictureUrls(Collections.emptyList());
        }
    }

    public interface GetPictureUrlCallback {

        void onGetPictureUrls(List<String> list);

    }
}
