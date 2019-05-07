package com.rdc.project.traveltrace.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.base.BaseBounceFragment;
import com.rdc.project.traveltrace.utils.PageSwitchUtil;
import com.rdc.project.traveltrace.view.h5_view.ClickableWebView;
import com.rdc.project.traveltrace.view.h5_view.WebViewClickCallback;
import com.rdc.project.traveltrace.view.nine_grid_view.ImageInfo;

import java.util.ArrayList;
import java.util.List;

import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_FIELD_URL;

public class H5Fragment extends BaseBounceFragment {

    private ClickableWebView mWebView;

    private String mUrl;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_h5;
    }

    @Override
    protected void initData(Bundle bundle) {
        mUrl = bundle.getString(ACTION_FIELD_URL);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initView() {
        mWebView = mRootView.findViewById(R.id.web_view);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl(mUrl);
    }

    @Override
    protected void setListener() {
        mWebView.setOnWebViewClickListener(new WebViewClickCallback() {
            @Override
            public void onClick(String url) {
                ImageInfo imageInfo = new ImageInfo();
                imageInfo.setBigImageUrl(url);
                imageInfo.setThumbnailUrl(url);
                List<ImageInfo> list = new ArrayList<>();
                list.add(imageInfo);
                PageSwitchUtil.goPreviewPictureActivity(getActivity(), list);
            }
        });
    }
}
