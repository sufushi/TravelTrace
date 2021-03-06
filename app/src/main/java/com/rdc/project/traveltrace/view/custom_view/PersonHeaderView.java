package com.rdc.project.traveltrace.view.custom_view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.meg7.widget.CircleImageView;
import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.entity.User;
import com.rdc.project.traveltrace.utils.DensityUtil;
import com.rdc.project.traveltrace.utils.SharePreferenceUtil;
import com.rdc.project.traveltrace.utils.action.Action;
import com.rdc.project.traveltrace.utils.action.ActionManager;

import cn.bmob.v3.BmobUser;

import static com.rdc.project.traveltrace.ui.GesturePinActivity.PIN_CODE;
import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_FIELD_SETTING_PIN;
import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_NAME_GESTURE_PIN;
import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_NAME_PERSON_DETAIL;
import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_PRE;

public class PersonHeaderView extends RelativeLayout implements View.OnClickListener {

    private CircleImageView mUserIconView;
    private TextView mUserNameView;

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
        mUserIconView = findViewById(R.id.user_icon);
        mUserNameView = findViewById(R.id.user_name);
        mUserIconView.setOnClickListener(this);
    }

    public void onResume() {
        if (BmobUser.isLogin()) {
            User user = BmobUser.getCurrentUser(User.class);
            Glide.with(getContext()).load(user.getUserIcon()).into(mUserIconView);
            mUserNameView.setText(user.getUserNikName());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_icon:
                String code = (String) SharePreferenceUtil.get(getContext(), PIN_CODE, "");
                Action action;
                if (TextUtils.isEmpty(code)) {
                    action = new Action(ACTION_PRE + ACTION_NAME_PERSON_DETAIL);
                } else {
                    action = new Action(ACTION_PRE + ACTION_NAME_GESTURE_PIN);
                }
                ActionManager.doAction(action, getContext());
                break;
            default:
                break;
        }
    }
}
