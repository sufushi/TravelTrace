package com.rdc.project.traveltrace.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.base.BaseBounceFragment;
import com.rdc.project.traveltrace.utils.action.Action;
import com.rdc.project.traveltrace.utils.action.ActionManager;

import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_FIELD_SETTING_PIN;
import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_NAME_GESTURE_PIN;
import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_PRE;

public class SettingFragment extends BaseBounceFragment implements View.OnClickListener {

    private RelativeLayout mGesturePinLayout;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    protected void initView() {
        mGesturePinLayout = mRootView.findViewById(R.id.layout_gesture_pin);
    }

    @Override
    protected void setListener() {
        mGesturePinLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_gesture_pin:
                Action action = new Action(ACTION_PRE + ACTION_NAME_GESTURE_PIN + "?" + ACTION_FIELD_SETTING_PIN + "=" + "setting");
                ActionManager.doAction(action, getActivity());
                break;
            default:
                break;
        }
    }
}
