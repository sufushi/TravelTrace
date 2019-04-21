package com.rdc.project.traveltrace.view.custom_view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.arch.view.IView;
import com.rdc.project.traveltrace.ui.PersonCollectionActivity;
import com.rdc.project.traveltrace.ui.PersonFocusActivity;
import com.rdc.project.traveltrace.ui.PersonGroupActivity;
import com.rdc.project.traveltrace.ui.PersonMessageActivity;
import com.rdc.project.traveltrace.utils.DensityUtil;
import com.rdc.project.traveltrace.view.DrawableTextView;

public class PersonSocialView extends LinearLayout implements IView, View.OnClickListener {

    private DrawableTextView mSocialGroupBtn;
    private DrawableTextView mSocialFocusBtn;
    private DrawableTextView mSocialCollectionBtn;
    private DrawableTextView mSocialMessageBtn;

    public PersonSocialView(Context context) {
        this(context, null);
    }

    public PersonSocialView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PersonSocialView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_person_social, this);
        setOrientation(HORIZONTAL);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        int padding = DensityUtil.dp2px(10, context);
        setPadding(padding, padding, padding, padding);

        initViews();
    }

    private void initViews() {
        mSocialGroupBtn = findViewById(R.id.btn_social_group);
        mSocialFocusBtn = findViewById(R.id.btn_social_focus);
        mSocialCollectionBtn = findViewById(R.id.btn_social_collection);
        mSocialMessageBtn = findViewById(R.id.btn_social_message);

        mSocialGroupBtn.setOnClickListener(this);
        mSocialFocusBtn.setOnClickListener(this);
        mSocialCollectionBtn.setOnClickListener(this);
        mSocialMessageBtn.setOnClickListener(this);
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
            case R.id.btn_social_group:
                intent.setClass(getContext(), PersonGroupActivity.class);
                break;
            case R.id.btn_social_focus:
                intent.setClass(getContext(), PersonFocusActivity.class);
                break;
            case R.id.btn_social_collection:
                intent.setClass(getContext(), PersonCollectionActivity.class);
                break;
            case R.id.btn_social_message:
                intent.setClass(getContext(), PersonMessageActivity.class);
                break;
            default:
                break;
        }
        if (intent.getClass() != null) {
            getContext().startActivity(intent);
        }
    }
}
