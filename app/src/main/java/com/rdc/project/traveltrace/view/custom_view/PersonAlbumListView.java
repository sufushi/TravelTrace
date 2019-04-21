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

public class PersonAlbumListView extends LinearLayout implements IView {

    public PersonAlbumListView(Context context) {
        this(context, null);
    }

    public PersonAlbumListView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PersonAlbumListView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_person_album_list, this);
        setOrientation(VERTICAL);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
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
