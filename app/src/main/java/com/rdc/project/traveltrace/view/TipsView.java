package com.rdc.project.traveltrace.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.rdc.project.traveltrace.R;

public class TipsView extends FrameLayout {

    private ImageView mTipsImage;
    private TextView mTipsText;

    public TipsView(@NonNull Context context) {
        this(context, null);
    }

    public TipsView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TipsView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_tips_view, this);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        initViews();
    }

    private void initViews() {
        mTipsImage = findViewById(R.id.tips_image);
        mTipsText = findViewById(R.id.tips_text);
    }

    public void showEmptyView(int resId, String tips) {
        showEmptyView(resId, tips, 0);
    }

    public void showEmptyView(int resId, String tips, int padding) {
        mTipsImage.setImageResource(resId);
        mTipsImage.setPadding(padding, padding, padding, padding);
        mTipsText.setText(tips);
    }
}
