package com.rdc.project.traveltrace.view.custom_view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.rdc.project.traveltrace.arch.view.IView;

public class PersonSocialView extends LinearLayout implements IView {

    public PersonSocialView(Context context) {
        this(context, null);
    }

    public PersonSocialView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PersonSocialView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setData(Object data) {

    }

    @Override
    public void onActive() {

    }
}
