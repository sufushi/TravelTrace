package com.rdc.project.traveltrace.view.custom_view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class CommentUserView extends NoteUserView {

    public CommentUserView(Context context) {
        this(context, null);
    }

    public CommentUserView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommentUserView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setData(Object data) {
        super.setData(data);
        mUserFollowBtn.setVisibility(GONE);
    }
}
