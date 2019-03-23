package com.rdc.project.traveltrace.view.custom_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.arch.view.IView;
import com.rdc.project.traveltrace.entity.Note;
import com.rdc.project.traveltrace.utils.DensityUtil;
import com.rdc.project.traveltrace.utils.more_text_util.MoreTextUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoteView extends LinearLayout implements IView {

    private NoteUserView mNoteUserView;
//    private ExpandableTextView mExpandableTextView;
    private TextView mText;
    private NineGridView mNineGridView;
    private NoteOperatorView mNoteOperatorView;

    private static Map<String, Float> sPictureRadioMap = new HashMap<>();

    public NoteView(Context context) {
        this(context, null);
    }

    public NoteView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NoteView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_note_view, this);
        setOrientation(VERTICAL);
        int padding = DensityUtil.dp2px(10, getContext());
        setPadding(padding, padding, padding, padding);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mNoteUserView = findViewById(R.id.note_user_view);
//        mExpandableTextView = findViewById(R.id.expandable_text_view);
        mText = findViewById(R.id.note_user_text_view);
        mNineGridView = findViewById(R.id.nine_grid_view);
        mNoteOperatorView = findViewById(R.id.note_operator_view);
    }

    @Override
    public void setData(Object data) {
        if (data instanceof Note) {
            Note note = (Note) data;
            mNoteUserView.setData(note.getUser());
//            mExpandableTextView.setText(note.getText());
            new MoreTextUtil(mText, note.getText()).setSpanTextColor(R.color.colorPrimary).setLines(4).createString();
            ArrayList<ImageInfo> imageInfoList = new ArrayList<>();
            List<String> imgUrls = note.getImgUrls();
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
            mNoteOperatorView.setLikeCountView(note.getLikeCount());
            mNoteOperatorView.setCommentCountView(String.valueOf(note.getCommentCount()));
            mNoteOperatorView.setIsLike(note.isLike());
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
