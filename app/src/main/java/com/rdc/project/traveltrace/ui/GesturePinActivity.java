package com.rdc.project.traveltrace.ui;

import android.content.Intent;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.base.BaseSwipeBackActivity;

import java.util.List;

public class GesturePinActivity extends BaseSwipeBackActivity implements PatternLockViewListener {

    private PatternLockView mPatternLockView;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_gesture_pin;
    }

    @Override
    protected boolean isEnableSwipeBack() {
        return false;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mPatternLockView = (PatternLockView) findViewById(R.id.pattern_lock_view);
    }

    @Override
    protected void initListener() {
        mPatternLockView.addPatternLockListener(this);
    }

    @Override
    public void onStarted() {

    }

    @Override
    public void onProgress(List<PatternLockView.Dot> list) {

    }

    @Override
    public void onComplete(List<PatternLockView.Dot> list) {
        Intent intent = new Intent(GesturePinActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onCleared() {

    }
}
