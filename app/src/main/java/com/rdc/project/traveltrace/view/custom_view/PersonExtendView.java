package com.rdc.project.traveltrace.view.custom_view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.arch.view.IView;
import com.rdc.project.traveltrace.ui.AboutActivity;
import com.rdc.project.traveltrace.ui.GuidePageActivity;
import com.rdc.project.traveltrace.ui.SettingActivity;
import com.rdc.project.traveltrace.utils.DensityUtil;

import cn.bmob.v3.BmobUser;

public class PersonExtendView extends LinearLayout implements IView, View.OnClickListener {

    private TextView mSettingView;
    private TextView mAboutView;
    private TextView mLogoutView;

    public PersonExtendView(Context context) {
        this(context, null);
    }

    public PersonExtendView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PersonExtendView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_person_extend, this);
        setOrientation(VERTICAL);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        int padding = DensityUtil.dp2px(10, context);
        setPadding(padding, padding, padding, padding);
        initViews();
    }

    private void initViews() {
        mSettingView = findViewById(R.id.person_setting);
        mAboutView = findViewById(R.id.person_about);
        mLogoutView = findViewById(R.id.person_logout);

        mSettingView.setOnClickListener(this);
        mAboutView.setOnClickListener(this);
        mLogoutView.setOnClickListener(this);
    }

    @Override
    public void setData(Object data) {

    }

    @Override
    public void onActive() {

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.person_setting:
                intent.setClass(getContext(), SettingActivity.class);
                getContext().startActivity(intent);
                break;
            case R.id.person_about:
                intent.setClass(getContext(), AboutActivity.class);
                getContext().startActivity(intent);
                break;
            case R.id.person_logout:
                if (BmobUser.isLogin()) {
                    BmobUser.logOut();
                    intent.setClass(getContext(), GuidePageActivity.class);
                    getContext().startActivity(intent);
                    ((Activity) getContext()).finish();
                }
                break;
            default:
                break;
        }
    }
}
