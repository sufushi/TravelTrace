package com.rdc.project.traveltrace.manager;

import android.text.TextUtils;
import android.util.Log;

import com.rdc.project.traveltrace.contract.IQueryContract;
import com.rdc.project.traveltrace.contract.IUpdateContract;
import com.rdc.project.traveltrace.contract.IUploadContract;
import com.rdc.project.traveltrace.entity.FollowList;
import com.rdc.project.traveltrace.entity.User;
import com.rdc.project.traveltrace.model.query.FollowListQueryModelImpl;
import com.rdc.project.traveltrace.presenter.QueryPresenterImpl;
import com.rdc.project.traveltrace.presenter.UpdatePresenterImpl;
import com.rdc.project.traveltrace.presenter.UploadPresenterImpl;
import com.rdc.project.traveltrace.presenter.query.QueryPresenterImplFactory;
import com.rdc.project.traveltrace.utils.CollectionUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.bmob.v3.BmobUser;

public class FollowListManager implements IUpdateContract.View, IQueryContract.View<FollowList>, IUploadContract.View {

    private static final String TAG = "FollowListManager";

    private static final int ACTION_FOLLOW = 0;
    private static final int ACTION_UN_FOLLOW = 1;

    private static final int STATE_IDLE = 5;

    private int mAction;
    private int mState;

    private UploadPresenterImpl<FollowList> mFollowListUploadPresenter;
    private UpdatePresenterImpl<FollowList> mFollowListUpdatePresenter;
    private QueryPresenterImpl<FollowList, FollowListQueryModelImpl> mFollowListQueryPresenter;
    private IFollowListener mFollowListener;
    private IUnFollowListener mUnFollowListener;

    private static FollowList mFollowList;
    private static Set<String> mFollowSet;

    private static class FollowListManagerHolder {
        private static final FollowListManager INSTANCE = new FollowListManager();
    }

    public static FollowListManager getInstance() {
        return FollowListManagerHolder.INSTANCE;
    }

    private FollowListManager() {
        mFollowListUploadPresenter = new UploadPresenterImpl<>(this);
        mFollowListUpdatePresenter = new UpdatePresenterImpl<>(this);
        mFollowListQueryPresenter = QueryPresenterImplFactory.createFollowListPresenterImpl(this);
        mFollowList = new FollowList();
        mFollowSet = new HashSet<>();
        mState = STATE_IDLE;
    }

    public void initFollowList() {
        User owner = BmobUser.getCurrentUser(User.class);
        if (owner == null) {
            return;
        }
        if (!CollectionUtil.isEmpty(mFollowList.getFollowList())) {
            mFollowList.getFollowList().clear();
        }
        mFollowSet.clear();
        Map<String, Object> params = new HashMap<>();
        params.put("mUserId", owner.getObjectId());
        mFollowListQueryPresenter.query(params);
    }

    public boolean hasFollow(User user) {
        if (user == null || TextUtils.isEmpty(user.getObjectId())) {
            return false;
        }
        return mFollowSet.contains(user.getObjectId());
    }

    public void addFollow(User user) {
        if (user == null || TextUtils.isEmpty(user.getObjectId())) {
            return;
        }
        mFollowSet.add(user.getObjectId());
        List<User> list = mFollowList.getFollowList();
        if (CollectionUtil.isEmpty(list)) {
            list = new ArrayList<>();
        }
        list.add(user);
    }

    public void removeFollow(User user) {
        if (user == null || TextUtils.isEmpty(user.getObjectId())) {
            return;
        }
        mFollowSet.remove(user.getObjectId());
        List<User> list = mFollowList.getFollowList();
        if (CollectionUtil.isEmpty(list)) {
            return;
        }
        Iterator<User> iterator = list.iterator();
        while (iterator.hasNext()) {
            User tmp = iterator.next();
            if (tmp.getObjectId().equals(user.getObjectId())) {
                iterator.remove();
                break;
            }
        }
    }

    public void followUser(User user, IFollowListener listener) {
        if (mState != STATE_IDLE) {
            return;
        }
        mAction = ACTION_FOLLOW;
        mFollowListener = listener;
        if (mFollowList != null && !CollectionUtil.isEmpty(mFollowList.getFollowList())) {
            mFollowList.add("mFollowList", user);
            mFollowListUpdatePresenter.update(mFollowList);
        } else {
            mFollowList = new FollowList();
            mFollowList.setUserId(BmobUser.getCurrentUser(User.class).getObjectId());
            List<User> list = new ArrayList<>();
            list.add(user);
            mFollowList.setFollowList(list);
            mFollowListUploadPresenter.upload(mFollowList);
        }
    }

    public void unFollowUser(User user, IUnFollowListener listener) {
        if (mState != STATE_IDLE) {
            return;
        }
        if (mFollowList == null || CollectionUtil.isEmpty(mFollowList.getFollowList())) {
            return;
        } else {
            mFollowList.removeAll("mUserId", Collections.singletonList(user));
            mFollowListUpdatePresenter.update(mFollowList);
        }
        mAction = ACTION_UN_FOLLOW;
        mUnFollowListener = listener;
    }

    public FollowList getFollowList() {
        return mFollowList;
    }

    @Override
    public void onUpdateSuccess(String response) {
        followSuccess(response);
    }

    @Override
    public void onUpdateFailed(String response) {
        followFailed(response);
    }

    @Override
    public void onQuerySuccess(List<FollowList> list) {
        if (CollectionUtil.isEmpty(list)) {
            return;
        }
        mFollowList = list.get(0);
        List<User> userList = mFollowList.getFollowList();
        for (int i = 0; i < userList.size(); i++) {
            mFollowSet.add(userList.get(i).getObjectId());
        }
    }

    @Override
    public void onQueryFailed(String response) {

    }

    @Override
    public void onUploadSuccess(String response) {
        followSuccess(response);
    }

    @Override
    public void onUploadFailed(String response) {
        followFailed(response);
    }

    private void followSuccess(String response) {
        mState = STATE_IDLE;
        Log.i(TAG, "follow success");
        if (mAction == ACTION_FOLLOW) {
            if (mFollowListener != null) {
                mFollowListener.onFollowSuccess(response);
            }
        } else if (mAction == ACTION_UN_FOLLOW) {
            if (mUnFollowListener != null) {
                mUnFollowListener.onUnFollowSuccess(response);
            }
        }
    }

    private void followFailed(String response) {
        mState = STATE_IDLE;
        Log.i(TAG, "BmobException:" + response);
        if (mAction == ACTION_FOLLOW) {
            if (mFollowListener != null) {
                mFollowListener.onFollowFailed(response);
            }
        } else if (mAction == ACTION_UN_FOLLOW) {
            if (mUnFollowListener != null) {
                mUnFollowListener.onUnFollowFailed(response);
            }
        }
    }

    public interface IFollowListener {

        void onFollowSuccess(String response);

        void onFollowFailed(String response);

    }

    public interface IUnFollowListener {

        void onUnFollowSuccess(String response);

        void onUnFollowFailed(String response);

    }
}
