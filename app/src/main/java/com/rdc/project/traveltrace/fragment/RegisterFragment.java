package com.rdc.project.traveltrace.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.base.BaseFragment;
import com.rdc.project.traveltrace.entity.User;
import com.rdc.project.traveltrace.view.VerificationCodeView;
import com.rdc.project.traveltrace.view.fly_edit_text.FlyEditText;
import com.rdc.project.traveltrace.view.toast.CommonToast;

import java.util.Objects;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisterFragment extends BaseFragment implements View.OnClickListener {

    private FlyEditText mPhoneNumEditText;
    private FlyEditText mCodeNumEditText;
    private VerificationCodeView mCodeView;
    private Button mSendBtn;
    private Button mRegisterBtn;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_register;
    }

    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    protected void initView() {
        mPhoneNumEditText = mRootView.findViewById(R.id.et_write_phone);
        mCodeNumEditText = mRootView.findViewById(R.id.et_put_identify);
        mCodeView = mRootView.findViewById(R.id.verification_code_view);
        mSendBtn = mRootView.findViewById(R.id.btn_send);
        mRegisterBtn = mRootView.findViewById(R.id.btn_register);
    }

    @Override
    protected void setListener() {
        mCodeView.setOnClickListener(this);
        mSendBtn.setOnClickListener(this);
        mRegisterBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.verification_code_view:
            case R.id.btn_send:
                mCodeView.setvCode("1234");
                mCodeView.refreshCode();
                mCodeView.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_register:
                register();
                break;
            default:
                break;
        }
    }

    private void register() {
        String input = String.valueOf(mCodeNumEditText.getText()).toLowerCase();
        String code = mCodeView.getvCode().toLowerCase();
        if (!input.equals(code)) {
            CommonToast.error(Objects.requireNonNull(getActivity()), "验证码错误").show();
        } else {
            User user = new User();
            String phone = String.valueOf(mPhoneNumEditText.getText());
            user.setUsername(phone);
            user.setPassword("123456");
            user.setUserIcon("http://b-ssl.duitang.com/uploads/item/201607/16/20160716151334_LuskR.thumb.700_0.jpeg");
            user.setMobilePhoneNumber(phone);
            user.signUp(new SaveListener<Object>() {
                @Override
                public void done(Object o, BmobException e) {
                    if (e == null) {
                        CommonToast.success(Objects.requireNonNull(getActivity()), "注册成功").show();
                    } else {
                        CommonToast.error(Objects.requireNonNull(getActivity()), "注册失败").show();
                    }
                }
            });
        }
    }
}
