package com.rdc.project.traveltrace.view.custom_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.rdc.project.traveltrace.entity.PictureNote;
import com.rdc.project.traveltrace.entity.PlainNote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PictureNoteView extends PLainNoteView {

    private NineGridView mNineGridView;

    private static Map<String, Float> sPictureRadioMap = new HashMap<>();

    public PictureNoteView(Context context) {
        this(context, null);
    }

    public PictureNoteView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PictureNoteView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected View createNoteExtendView(Context context) {
        mNineGridView = new NineGridView(context);
        return mNineGridView;
    }

    @Override
    public void setData(Object data) {
        if (data instanceof PictureNote) {
            PictureNote pictureNote = (PictureNote) data;
            PlainNote plainNote = new PlainNote();
            plainNote.setUser(pictureNote.getUser());
            plainNote.setText(pictureNote.getText());
            plainNote.setLikeCount(pictureNote.getLikeCount());
            plainNote.setCommentCount(pictureNote.getCommentCount());
            plainNote.setLike(pictureNote.isLike());
            super.setData(plainNote);
            ArrayList<ImageInfo> imageInfoList = new ArrayList<>();
            List<String> imgUrls = pictureNote.getImgUrls();
            if (imgUrls != null) {
                configSinglePictureRadio(imgUrls);
                for (String url : imgUrls) {
                    ImageInfo imageInfo = new ImageInfo();
                    imageInfo.setThumbnailUrl(url);
                    imageInfo.setBigImageUrl(url);
                    imageInfoList.add(imageInfo);
                }
                mNineGridView.setAdapter(new NineGridViewClickAdapter(getContext(), imageInfoList));
            }
        }
    }

    private void configSinglePictureRadio(List<String> imgUrls) {
        if (imgUrls.size() == 1) {
            final String url = imgUrls.get(0);
            if (sPictureRadioMap.containsKey(url)) {
                float radio = sPictureRadioMap.get(url);
                mNineGridView.setSingleImageRatio(radio);
            } else {
                Glide.with(getContext())
                        .asBitmap()
                        .load(url)
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                                int width = bitmap.getWidth();
                                int height = bitmap.getHeight();
                                float radio = width * 1.0f / height;
                                if (width == 0 || height == 0) {
                                    return;
                                }
                                if (radio < 1) {
                                    radio = 1;
                                } else if (radio > 5) {
                                    radio = 5;
                                }
                                sPictureRadioMap.put(url, radio);
                                mNineGridView.setSingleImageRatio(radio);
                            }
                        });
            }
        } else {
            mNineGridView.setSingleImageRatio(1);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mNineGridView != null) {
            mNineGridView.requestLayout();
        }
    }
}
