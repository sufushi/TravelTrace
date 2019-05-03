package com.rdc.project.traveltrace.view.custom_view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.meg7.widget.CircleImageView;
import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.arch.view.IView;
import com.rdc.project.traveltrace.entity.User;
import com.rdc.project.traveltrace.manager.FollowListManager;
import com.rdc.project.traveltrace.view.toast.CommonToast;

import cn.bmob.v3.BmobUser;

public class NoteUserView extends LinearLayout implements IView, View.OnClickListener, FollowListManager.IFollowListener, FollowListManager.IUnFollowListener {

    private CircleImageView mNoteUserIcon;
    private TextView mNoteUserName;
    private TextView mNoteUserExtraMsg;
    protected TextView mUserFollowBtn;

    private FollowListManager mFollowListManager;

    private User mUser;

    public NoteUserView(Context context) {
        this(context, null);
    }

    public NoteUserView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NoteUserView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_note_user_view, this);
        setOrientation(HORIZONTAL);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mNoteUserIcon = findViewById(R.id.note_user_icon);
        mNoteUserName = findViewById(R.id.note_user_name);
        mNoteUserExtraMsg = findViewById(R.id.note_user_extra_message);
        mUserFollowBtn = findViewById(R.id.btn_follow_user);
        mFollowListManager = new FollowListManager();
    }

    @Override
    public void setData(Object data) {
        if (data instanceof User) {
            mUser = (User) data;
            Glide.with(getContext())
                    .load(mUser.getUserIcon())
                    .apply(new RequestOptions().placeholder(R.drawable.ic_avatar))
                    .into(mNoteUserIcon);
            mNoteUserName.setText(mUser.getUsername());
            mNoteUserExtraMsg.setText(mUser.getUserExtraMsg());
            if (!BmobUser.isLogin() || BmobUser.getCurrentUser(User.class).getObjectId().equals(mUser.getObjectId())) {
                mUserFollowBtn.setVisibility(GONE);
            } else {
                mUserFollowBtn.setVisibility(VISIBLE);
                mUserFollowBtn.setOnClickListener(this);
                if (mFollowListManager.hasFollow(mUser)) {
                    mUserFollowBtn.setText(R.string.string_has_follow);
                } else {
                    mUserFollowBtn.setText(R.string.string_follow_user);
                }
            }
        }
    }

    @Override
    public void onActive() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_follow_user:
                if (BmobUser.isLogin() && mUser != null) {
                    if (mFollowListManager.hasFollow(mUser)) {
                        mFollowListManager.unfollowUser(mUser, this);
                    } else {
                        mFollowListManager.followUser(mUser, this);
                    }
                } else {
                    CommonToast.info(getContext(), "请先登录").show();
                }
                break;
        }
    }

    @Override
    public void onFollowSuccess(String response) {
        CommonToast.success(getContext(), "关注成功").show();
        mUserFollowBtn.setText(R.string.string_has_follow);
        mFollowListManager.addFollow(mUser);
    }

    @Override
    public void onFollowFailed(String response) {
        CommonToast.error(getContext(), "关注失败").show();
        mUserFollowBtn.setText(R.string.string_follow_user);
    }

    @Override
    public void onUnFollowSuccess(String response) {
        CommonToast.success(getContext(), "关注取消").show();
        mUserFollowBtn.setText(R.string.string_follow_user);
        mFollowListManager.removeFollow(mUser);
    }

    @Override
    public void onUnFollowFailed(String response) {
        CommonToast.success(getContext(), "取消失败").show();
        mUserFollowBtn.setText(R.string.string_has_follow);
    }
}
