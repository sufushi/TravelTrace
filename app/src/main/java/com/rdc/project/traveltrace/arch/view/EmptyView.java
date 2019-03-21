package com.rdc.project.traveltrace.arch.view;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

public class EmptyView extends BaseView {

    private TextView mTextView;

    public EmptyView(Context context) {
        mTextView = new TextView(context);
        mTextView.setGravity(Gravity.CENTER);
        mTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void setData(Object data) {
        if (data != null) {
            mTextView.setText(data.toString());
        }
    }
}
