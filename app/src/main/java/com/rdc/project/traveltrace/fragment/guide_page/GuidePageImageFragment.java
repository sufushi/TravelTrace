package com.rdc.project.traveltrace.fragment.guide_page;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.widget.ImageView;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.base.BaseFragment;
import com.rdc.project.traveltrace.utils.DensityUtil;
import com.rdc.project.traveltrace.utils.MeasureUtil;

import java.util.Objects;

public class GuidePageImageFragment extends BaseFragment {

    public static final String KEY_RES_ID = "ResId";

    private int mImageViewHeight = 0;
    private int mResId;

    public static GuidePageImageFragment newInstance(int resId) {
        GuidePageImageFragment fragment = new GuidePageImageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_RES_ID, resId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_guide_page_image;
    }

    @Override
    protected void initData(Bundle bundle) {
        mResId = bundle.getInt(KEY_RES_ID, 0);
        int parentViewHeight = MeasureUtil.getScreenHeight(Objects.requireNonNull(getActivity())) - DensityUtil.dp2px(60, getActivity()) * 3 - DensityUtil.dp2px(100, getActivity());
        mImageViewHeight = parentViewHeight - DensityUtil.dp2px(40, getActivity());
    }

    @Override
    protected void initView() {
        ImageView contentBg = mRootView.findViewById(R.id.bg_content);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), mResId);
        contentBg.setImageBitmap(scaleBitmap(mImageViewHeight, bitmap));
    }

    @Override
    protected void setListener() {

    }

    private Bitmap scaleBitmap(int h, Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int w = (int) ((float) width) * h / height;

        float scaleX = ((float) w) / width;
        float scaleY = ((float) h) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleX, scaleY);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }
}
