package com.rdc.project.traveltrace.view.custom_view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.rdc.project.traveltrace.arch.view.IView;

public class PersonAlbumListView extends LinearLayout implements IView {

    public PersonAlbumListView(Context context) {
        super(context);
    }

    public PersonAlbumListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PersonAlbumListView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setData(Object data) {

    }

    @Override
    public void onActive() {

    }
}
