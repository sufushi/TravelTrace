package com.rdc.project.traveltrace.view.custom_view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.meg7.widget.CircleImageView;
import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.arch.view.IView;
import com.rdc.project.traveltrace.entity.User;
import com.rdc.project.traveltrace.utils.DensityUtil;

public class FollowListItemView extends LinearLayout implements IView {

    private CircleImageView mFollowUserIconView;
    private TextView mFollowUserNameView;

    public FollowListItemView(Context context) {
        this(context, null);
    }

    public FollowListItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FollowListItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.item_follow_list, this);
        setOrientation(VERTICAL);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        int padding = DensityUtil.dp2px(10, context);
        setPadding(padding, padding, padding, padding);
        mFollowUserIconView = findViewById(R.id.follow_user_icon);
        mFollowUserNameView = findViewById(R.id.follow_user_name);
    }

    @Override
    public void setData(Object data) {
        if (data instanceof User) {
            User user = (User) data;
            Glide.with(getContext())
                    .load(user.getUserIcon())
                    .apply(new RequestOptions().placeholder(R.drawable.ic_avatar))
                    .into(mFollowUserIconView);
            mFollowUserNameView.setText(user.getUserName());
        }
    }

    @Override
    public void onActive() {

    }
}
