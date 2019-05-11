package com.rdc.project.traveltrace.view.custom_view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.meg7.widget.CircleImageView;
import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.arch.view.IView;
import com.rdc.project.traveltrace.base.BaseSwipeAwayDialogFragment;
import com.rdc.project.traveltrace.entity.User;
import com.rdc.project.traveltrace.view.swipe_away_dialog.dialog_factory.DialogFactory;

public class NoteUserView extends LinearLayout implements IView, View.OnClickListener {

    private CircleImageView mNoteUserIcon;
    private TextView mNoteUserName;
    private TextView mNoteUserExtraMsg;

    private User mUser;

    public NoteUserView(Context context) {
        this(context, null);
    }

    public NoteUserView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NoteUserView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_note_user_view, this);
        setOrientation(HORIZONTAL);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mNoteUserIcon = findViewById(R.id.note_user_icon);
        mNoteUserName = findViewById(R.id.note_user_name);
        mNoteUserExtraMsg = findViewById(R.id.note_user_extra_message);

        mNoteUserIcon.setOnClickListener(this);
    }

    @Override
    public void setData(Object data) {
        if (data instanceof User) {
            mUser = (User) data;
            Glide.with(getContext())
                    .load(mUser.getUserIcon())
                    .apply(new RequestOptions().placeholder(R.drawable.ic_avatar))
                    .into(mNoteUserIcon);
            mNoteUserName.setText(mUser.getUserNikName());
            mNoteUserExtraMsg.setText(mUser.getUserExtraMsg());
        }
    }

    @Override
    public void onActive() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.note_user_icon:
                Bundle args = new Bundle();
                args.putSerializable("user", mUser);
                BaseSwipeAwayDialogFragment
                        .newInstance(DialogFactory.createUserDetailDialog(), args)
                        .show(((FragmentActivity)getContext()).getSupportFragmentManager(), "user_detail");
                break;
        }
    }

}
