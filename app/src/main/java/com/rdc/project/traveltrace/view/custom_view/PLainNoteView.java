package com.rdc.project.traveltrace.view.custom_view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.arch.view.IView;
import com.rdc.project.traveltrace.entity.PlainNote;
import com.rdc.project.traveltrace.utils.DensityUtil;
import com.rdc.project.traveltrace.utils.more_text_util.MoreTextUtil;

import java.util.HashMap;
import java.util.Map;

public class PLainNoteView extends LinearLayout implements IView {

    protected NoteUserView mNoteUserView;
    protected TextView mText;
    protected FrameLayout mExtendViewContainer;
    protected NoteOperatorView mNoteOperatorView;

    public PLainNoteView(Context context) {
        this(context, null);
    }

    public PLainNoteView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PLainNoteView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        mText = findViewById(R.id.note_user_text_view);
        mNoteOperatorView = findViewById(R.id.note_operator_view);
        mExtendViewContainer = findViewById(R.id.note_extend_view_container);
        View extendView = createNoteExtendView(context);
        if (extendView != null) {
            mExtendViewContainer.addView(extendView);
        }
    }

    protected View createNoteExtendView(Context context) {
        return null;
    }

    @Override
    public void setData(Object data) {
        if (data instanceof PlainNote) {
            PlainNote plainNote = (PlainNote) data;
            mNoteUserView.setData(plainNote.getUser());
            new MoreTextUtil(mText, plainNote.getText()).setSpanTextColor(R.color.colorPrimary).setLines(4).createString();
            mNoteOperatorView.setLikeCountView(plainNote.getLikeCount());
            mNoteOperatorView.setCommentCountView(String.valueOf(plainNote.getCommentCount()));
            mNoteOperatorView.setIsLike(plainNote.isLike());
        }
    }

    @Override
    public void onActive() {

    }

}
