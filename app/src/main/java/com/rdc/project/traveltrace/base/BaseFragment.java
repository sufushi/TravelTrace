package com.rdc.project.traveltrace.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {

    protected AppCompatActivity mBaseActivity;
    protected View mRootView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mBaseActivity = (AppCompatActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView != null) {
            return mRootView;
        }
        onCreateContentView(inflater, container);
        initData(getArguments());
        initView();
        setListener();
        return mRootView;
    }

    protected void onCreateContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        mRootView = inflater.inflate(getLayoutResourceId(), container, false);
    }

    protected abstract int getLayoutResourceId();

    protected abstract void initData(Bundle bundle);

    protected abstract void initView();

    protected abstract void setListener();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
