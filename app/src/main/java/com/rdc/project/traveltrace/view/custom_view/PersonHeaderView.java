package com.rdc.project.traveltrace.view.custom_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.utils.DensityUtil;

public class PersonHeaderView extends RelativeLayout {

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

    }
}
