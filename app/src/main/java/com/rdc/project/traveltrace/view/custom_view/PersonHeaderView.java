package com.rdc.project.traveltrace.view.custom_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.meg7.widget.CircleImageView;
import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.entity.User;
import com.rdc.project.traveltrace.utils.DensityUtil;

import cn.bmob.v3.BmobUser;

public class PersonHeaderView extends RelativeLayout {

    private CircleImageView mUserIconView;
    private TextView mUserNameView;

    public PersonHeaderView(Context context) {
        this(context, null);
    }

    public PersonHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PersonHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_person_header, this);
        int height = DensityUtil.dp2px(140, context);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
        mUserIconView = findViewById(R.id.user_icon);
        mUserNameView = findViewById(R.id.user_name);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (BmobUser.isLogin()) {
            User user = BmobUser.getCurrentUser(User.class);
            Glide.with(getContext()).load(user.getUserIcon()).into(mUserIconView);
            mUserNameView.setText(user.getUsername());
        }

    }
}
