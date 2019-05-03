package com.rdc.project.traveltrace.manager;

import android.text.TextUtils;
import android.util.Log;

import com.rdc.project.traveltrace.contract.IUpdateContract;
import com.rdc.project.traveltrace.entity.FollowList;
import com.rdc.project.traveltrace.entity.User;
import com.rdc.project.traveltrace.presenter.UpdatePresenterImpl;
import com.rdc.project.traveltrace.utils.CollectionUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;

public class FollowListManager implements IUpdateContract.View {

    private static final String TAG = "FollowListManager";

    private static final int ACTION_FOLLOW = 0;
    private static final int ACTION_UN_FOLLOW = 1;

    private static final int STATE_FOLLOWING = 3;
    private static final int STATE_UN_FOLLOWING = 4;
    private static final int STATE_IDLE = 5;

    private int mAction;
    private int mState;

    private UpdatePresenterImpl<User> mUserUpdatePresenter;
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
        mUserUpdatePresenter = new UpdatePresenterImpl<>(this);
        mFollowList = new FollowList();
        mFollowSet = new HashSet<>();
        initFollowList();
        mState = STATE_IDLE;
    }

    private void initFollowList() {
        User owner = BmobUser.getCurrentUser(User.class);
        if (owner == null || owner.getFollowUserList() == null) {
            return;
        }
        List<BmobPointer> list = owner.getFollowUserList().getObjects();
        if (CollectionUtil.isEmpty(list)) {
            return;
        }
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            User user = new User();
            String objectId = list.get(i).getObjectId();
            user.setObjectId(objectId);
            userList.add(user);
            mFollowSet.add(objectId);
        }
        mFollowList.setFollowList(userList);
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
        User owner = BmobUser.getCurrentUser(User.class);
        BmobRelation relation = new BmobRelation();
        relation.add(user);
        owner.setFollowUserList(relation);
        mUserUpdatePresenter.update(owner);
    }

    public void unFollowUser(User user, IUnFollowListener listener) {
        if (mState != STATE_IDLE) {
            return;
        }
        mAction = ACTION_UN_FOLLOW;
        mUnFollowListener = listener;
        User owner = BmobUser.getCurrentUser(User.class);
        BmobRelation relation = new BmobRelation();
        relation.remove(user);
        owner.setFollowUserList(relation);
        mUserUpdatePresenter.update(owner);
    }

    @Override
    public void onUpdateSuccess(String response) {
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

    @Override
    public void onUpdateFailed(String response) {
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
