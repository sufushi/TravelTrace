package com.rdc.project.traveltrace.view.custom_view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.arch.view.IView;
import com.rdc.project.traveltrace.entity.VideoNote;
import com.rdc.project.traveltrace.utils.DensityUtil;
import com.rdc.project.traveltrace.view.round_image_view.RoundedImageView;

public class VideoHListItemView extends LinearLayout implements IView {

    private RoundedImageView mRoundedImageView;

    public VideoHListItemView(Context context) {
        this(context, null);
    }

    public VideoHListItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoHListItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.item_person_video_list, this);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        int padding = DensityUtil.dp2px(4, context);
        setPadding(padding, padding, padding, padding);
        mRoundedImageView = findViewById(R.id.rounded_image_view);
    }

    @Override
    public void setData(Object data) {
        if (data instanceof VideoNote) {
            VideoNote videoNote = (VideoNote) data;
            Glide.with(getContext())
                    .load(videoNote.getVideoCoverUrl())
                    .into(mRoundedImageView);
        }
    }

    @Override
    public void onActive() {

    }
}
