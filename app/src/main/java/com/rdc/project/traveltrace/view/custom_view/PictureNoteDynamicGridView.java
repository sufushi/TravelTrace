package com.rdc.project.traveltrace.view.custom_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.arch.view.IView;
import com.rdc.project.traveltrace.entity.Picture;

public class PictureNoteDynamicGridView extends RelativeLayout implements IView, View.OnClickListener {

    private ImageView mPhoto;
    private ImageView mBtnClose;
    private View mBtnBg;

    private boolean mIsEditing = false;
    private int mPosition;

    private OnDynamicGridViewItemClickListener mOnDynamicGridViewItemClickListener;

    public PictureNoteDynamicGridView(Context context) {
        this(context, null);
    }

    public PictureNoteDynamicGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PictureNoteDynamicGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.item_picture_note_dynamic_grid_view, this);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setBackgroundColor(context.getResources().getColor(R.color.lightGray));
        mPhoto = findViewById(R.id.iv_photo);
        mBtnClose = findViewById(R.id.iv_close);
        mBtnBg = findViewById(R.id.bg_close);
        mPhoto.setClickable(false);
        mBtnClose.setOnClickListener(this);
    }

    public void setEditing(boolean editing) {
        mIsEditing = editing;
        updatePhotoClickListener();
        updateCloseBtn();
    }

    private void updatePhotoClickListener() {
        if (mIsEditing) {
            mPhoto.setOnClickListener(this);
        } else {
            mPhoto.setOnClickListener(null);
            mPhoto.setClickable(false);
        }
    }

    public void setOnDynamicGridViewItemClickListener(OnDynamicGridViewItemClickListener onDynamicGridViewItemClickListener) {
        mOnDynamicGridViewItemClickListener = onDynamicGridViewItemClickListener;
    }

    public void setPosition(int position) {
        mPosition = position;
    }

    private void updateCloseBtn() {
        mBtnClose.setVisibility(mIsEditing ? VISIBLE : GONE);
        mBtnBg.setVisibility(mIsEditing ? VISIBLE : GONE);
    }

    @Override
    public void setData(Object data) {
        if (data instanceof Picture) {
            Picture picture = (Picture) data;
            Glide.with(getContext()).load(picture.getImgPath()).into(mPhoto);
            updateCloseBtn();
            updatePhotoClickListener();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_photo:
                if (mOnDynamicGridViewItemClickListener != null) {
                    mOnDynamicGridViewItemClickListener.onPhotoClick(mPosition);
                }
                break;
            case R.id.iv_close:
                if (mOnDynamicGridViewItemClickListener != null) {
                    mOnDynamicGridViewItemClickListener.onCloseClick(mPosition);
                }
                break;
        }
    }

    public interface OnDynamicGridViewItemClickListener {

        void onPhotoClick(int position);

        void onCloseClick(int position);

    }

}
