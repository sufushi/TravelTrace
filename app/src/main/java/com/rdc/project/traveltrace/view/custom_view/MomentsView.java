package com.rdc.project.traveltrace.view.custom_view;

import android.content.Context;
import android.util.AttributeSet;

import com.rdc.project.traveltrace.arch.view.IView;
import com.rdc.project.traveltrace.utils.visibility_util.items.ListItem;
import com.shizhefei.view.multitype.MultiTypeAdapter;
import com.shizhefei.view.multitype.MultiTypeView;

public class MomentsView extends MultiTypeView implements IView {

    private MultiTypeAdapter<ListItem> mMultiTypeAdapter;

    public MomentsView(Context context) {
        this(context, null);
    }

    public MomentsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MomentsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setData(Object data) {

    }

    @Override
    public void onActive() {

    }
}
