package com.rdc.project.traveltrace.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.gyf.barlibrary.ImmersionBar;
import com.rdc.project.traveltrace.R;

import java.util.Objects;

public abstract class BaseDialogFragment extends DialogFragment {

    protected Activity mActivity;
    protected View mRootView;
    protected Window mWindow;

    protected int mWidth;

    protected int mHeight;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        //点击外部消失
        dialog.setCanceledOnTouchOutside(true);
        mWindow = dialog.getWindow();
        //测量宽高
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            DisplayMetrics dm = new DisplayMetrics();
            Objects.requireNonNull(getActivity()).getWindowManager().getDefaultDisplay().getRealMetrics(dm);
            mWidth = dm.widthPixels;
            mHeight = dm.heightPixels;
        } else {
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            mWidth = metrics.widthPixels;
            mHeight = metrics.heightPixels;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutResId(), container, false);
        return mRootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isImmersionBarEnabled()) {
            initImmersionBar();
        }
        initData();
        initView();
        initListener();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }

    protected abstract int getLayoutResId();

    protected boolean isImmersionBarEnabled() {
        return true;
    }

    protected void initImmersionBar() {
        ImmersionBar.with(this).init();
    }

    protected void initData() {

    }

    protected void initView() {

    }

    protected void initListener() {

    }

}
