package com.rdc.project.traveltrace.view.custom_view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.arch.view.IView;
import com.rdc.project.traveltrace.utils.DensityUtil;

public class PersonExtendView extends LinearLayout implements IView, View.OnClickListener {

    private TextView mSettingView;
    private TextView mAboutView;

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
        mSettingView.setOnClickListener(this);
        mAboutView.setOnClickListener(this);
    }

    @Override
    public void setData(Object data) {

    }

    @Override
    public void onActive() {

    }

    @Override
    public void onClick(View v) {

    }
}
