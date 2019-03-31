package com.rdc.project.traveltrace.view.custom_view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.arch.view.IView;
import com.rdc.project.traveltrace.entity.Comment;

public class CommentListItemView extends LinearLayout implements IView {

    private CommentUserView mCommentUserView;
    private TextView mCommentMessageView;

    public CommentListItemView(Context context) {
        this(context, null);
    }

    public CommentListItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommentListItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.item_comment_list, this);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setOrientation(VERTICAL);
        mCommentUserView = findViewById(R.id.comment_user_view);
        mCommentMessageView = findViewById(R.id.comment_message);
    }

    @Override
    public void setData(Object data) {
        if (data instanceof Comment) {
            Comment comment = (Comment) data;
            mCommentUserView.setData(comment.getSendUser());
            mCommentMessageView.setText(comment.getMessage());
        }
    }

    @Override
    public void onActive() {

    }
}
