package com.rdc.project.traveltrace.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.base.BaseFragment;
import com.rdc.project.traveltrace.entity.User;
import com.rdc.project.traveltrace.ui.HomeActivity;
import com.rdc.project.traveltrace.view.fly_edit_text.FlyEditText;
import com.rdc.project.traveltrace.view.toast.CommonToast;

import java.util.Objects;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginFragment extends BaseFragment implements View.OnClickListener {

    private FlyEditText mPhoneEditText;
    private FlyEditText mPasswordEditText;
    private Button mLoginBtn;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    protected void initView() {
        mPhoneEditText = mRootView.findViewById(R.id.et_phone);
        mPasswordEditText = mRootView.findViewById(R.id.et_password);
        mLoginBtn = mRootView.findViewById(R.id.btn_login);

    }

    @Override
    protected void setListener() {
        mLoginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                String phone = String.valueOf(mPhoneEditText.getText());
                String password = String.valueOf(mPasswordEditText.getText());
                User user = new User();
                user.setUsername(phone);
                user.setPassword(password);
                user.login(new SaveListener<Object>() {
                    @Override
                    public void done(Object o, BmobException e) {
                        if (e == null) {
                            Intent intent = new Intent(getActivity(), HomeActivity.class);
                            startActivity(intent);
                            Objects.requireNonNull(getActivity()).finish();
                        } else {
                            CommonToast.error(Objects.requireNonNull(getActivity()), e.getMessage()).show();
                        }
                    }
                });
                break;
            default:
                break;
        }
    }
}
