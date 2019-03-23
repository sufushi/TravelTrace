package com.rdc.project.traveltrace.view.custom_view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.utils.DensityUtil;
import com.rdc.project.traveltrace.view.thumb_up_view.ThumbUpView;

public class NoteOperatorView extends LinearLayout implements View.OnClickListener {

    private TextView mLikeCountView;
    private TextView mCommentCountView;

    private int mLikeCount;
    private boolean mIsLike = false;

    private ThumbUpView mThumbUpView;

    public NoteOperatorView(Context context) {
        this(context, null);
    }

    public NoteOperatorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NoteOperatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_note_operator_view, this);
        setOrientation(HORIZONTAL);
        int left = DensityUtil.dp2px(10, context);
        int top = DensityUtil.dp2px(10, context);
        int right = DensityUtil.dp2px(8, context);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setPadding(left, top, right, 0);
        mLikeCountView = findViewById(R.id.note_like_count);
        mCommentCountView = findViewById(R.id.note_comment_count);
        mLikeCountView.setOnClickListener(this);
        mThumbUpView = new ThumbUpView(context);
        mThumbUpView.reset();
    }

    public void setLikeCountView(int likeCount) {
        mLikeCount = likeCount;
        mLikeCountView.setText(String.valueOf(likeCount));
    }

    public void setCommentCountView(String commentCountView) {
        mCommentCountView.setText(commentCountView);
    }

    public void setIsLike(boolean isLike) {
        mLikeCountView.setSelected(isLike);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.note_like_count:
                mIsLike = !mIsLike;
                mLikeCount += mIsLike ? 1 : -1;
                mLikeCountView.setSelected(mIsLike);
                mLikeCountView.setText(String.valueOf(mLikeCount));
                mThumbUpView.setText(mIsLike ? "+1" : "-1");
                mThumbUpView.show(mLikeCountView);
                break;
            case R.id.note_comment_count:
                break;
            default:
                break;
        }
    }
}
