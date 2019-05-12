package com.rdc.project.traveltrace.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.base.BaseSwipeBackActivity;
import com.rdc.project.traveltrace.utils.CollectionUtil;
import com.rdc.project.traveltrace.utils.SharePreferenceUtil;
import com.rdc.project.traveltrace.utils.action.Action;
import com.rdc.project.traveltrace.utils.action.ActionManager;
import com.rdc.project.traveltrace.view.toast.CommonToast;

import java.util.List;

import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_FIELD_SETTING_PIN;
import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_NAME_PERSON_DETAIL;
import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_PRE;

public class GesturePinActivity extends BaseSwipeBackActivity implements PatternLockViewListener {

    public static final String PIN_CODE = "pin_code";

    private static final int PIN_STATUS_NONE = -1;
    private static final int PIN_STATUS_INPUT_OLD_CODE = 0;
    private static final int PIN_STATUS_INPUT_NEW_CODE = 1;
    private static final int PIN_STATUS_INPUT_CONFIRM_CODE = 2;

    private PatternLockView mPatternLockView;
    private TextView mProfileName;

    private boolean mIsSetting = false;
    private String mCode;
    private String mTips;
    private int mCodeStatus;

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
        mCode = (String) SharePreferenceUtil.get(this, PIN_CODE, "");
        Bundle bundle = getIntent().getBundleExtra("bundle");
        mIsSetting = !TextUtils.isEmpty(bundle.getString(ACTION_FIELD_SETTING_PIN));
        if (mIsSetting) {
            if (TextUtils.isEmpty(mCode)) {
                mTips = "输入新手势";
                mCodeStatus = PIN_STATUS_INPUT_NEW_CODE;
            } else {
                mTips = "输入旧手势";
                mCodeStatus = PIN_STATUS_INPUT_OLD_CODE;
            }
        } else {
            mTips = "输入验证手势";
            mCodeStatus = PIN_STATUS_NONE;
        }
    }

    @Override
    protected void initView() {
        mPatternLockView = (PatternLockView) findViewById(R.id.pattern_lock_view);
        mProfileName = (TextView) findViewById(R.id.profile_name);

        mProfileName.setText(mTips);
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
        if (CollectionUtil.isEmpty(list)) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            PatternLockView.Dot dot = list.get(i);
            stringBuilder.append(dot.getId()).append(i == list.size() - 1 ? "" : ";");
        }
        String code = stringBuilder.toString();
        Log.i("pin", "PIN CODE=" + code);
        if (mIsSetting) {
            if (mCodeStatus == PIN_STATUS_INPUT_OLD_CODE) {
                if (mCode.equals(code)) {
                    mCodeStatus = PIN_STATUS_INPUT_NEW_CODE;
                    mTips = "输入新手势";
                    mProfileName.setText(mTips);
                } else {
                    CommonToast.error(this, "手势密码错误").show();
                }
                mPatternLockView.clearPattern();
            } else if (mCodeStatus == PIN_STATUS_INPUT_NEW_CODE) {
                mCodeStatus = PIN_STATUS_INPUT_CONFIRM_CODE;
                mTips = "确认新手势";
                mProfileName.setText(mTips);
                mCode = code;
                mPatternLockView.clearPattern();
            } else if (mCodeStatus == PIN_STATUS_INPUT_CONFIRM_CODE) {
                if (mCode.equals(code)) {
                    SharePreferenceUtil.put(this, PIN_CODE, code);
                    CommonToast.success(this, "设置手势密码成功").show();
                    finish();
                } else {
                    mPatternLockView.clearPattern();
                    CommonToast.error(this, "手势密码错误").show();
                }
            }
        } else {
            if (mCode.equals(code)) {
                Action action = new Action(ACTION_PRE + ACTION_NAME_PERSON_DETAIL);
                ActionManager.doAction(action, this);
                finish();
            } else {
                mPatternLockView.clearPattern();
                CommonToast.error(this, "手势密码错误").show();
            }
        }
    }

    @Override
    public void onCleared() {

    }
}
