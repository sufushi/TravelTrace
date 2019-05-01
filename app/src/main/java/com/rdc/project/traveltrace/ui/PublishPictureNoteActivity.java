package com.rdc.project.traveltrace.ui;

import android.content.Intent;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;
import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.base.BaseBounceActivity;
import com.rdc.project.traveltrace.base.BaseFragment;
import com.rdc.project.traveltrace.fragment.PublishPictureNoteFragment;

public class PublishPictureNoteActivity extends BaseBounceActivity {

    private PublishPictureNoteFragment mPublishPictureNoteFragment;

    @Override
    protected String getToolBarTitle() {
        return "发表";
    }

    @Override
    protected BaseFragment createPTRFragment() {
        mPublishPictureNoteFragment = new PublishPictureNoteFragment();
        return mPublishPictureNoteFragment;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mActionBtn.setText(R.string.string_complete);
    }

    @Override
    protected void initListener() {
        mActionBtn.setOnClickListener(v -> {
            if (mPublishPictureNoteFragment != null) {
                mPublishPictureNoteFragment.onActionBtnClick();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mPublishPictureNoteFragment != null) {
            mPublishPictureNoteFragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
