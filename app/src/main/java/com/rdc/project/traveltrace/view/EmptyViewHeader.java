package com.rdc.project.traveltrace.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;

public class EmptyViewHeader extends View implements RefreshHeader {

    public EmptyViewHeader(Context context) {
        this(context, null);
    }

    public EmptyViewHeader(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyViewHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    @Override
    public void setPrimaryColors(int... ints) {

    }

    @Override
    public void onInitialized(@NonNull RefreshKernel refreshKernel, int i, int i1) {

    }

    @Override
    public void onMoving(boolean b, float v, int i, int i1, int i2) {

    }

    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int i, int i1) {
        refreshLayout.finishRefresh(0);
        refreshLayout.closeHeaderOrFooter();
    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int i, int i1) {

    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean b) {
        return 10;
    }

    @Override
    public void onHorizontalDrag(float v, int i, int i1) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState refreshState, @NonNull RefreshState refreshState1) {

    }
}
