package com.rdc.project.traveltrace.view.custom_view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.arch.view.IView;
import com.rdc.project.traveltrace.utils.DensityUtil;

public class PersonSocialView extends LinearLayout implements IView {

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
    }

    @Override
    public void setData(Object data) {

    }

    @Override
    public void onActive() {

    }
}
