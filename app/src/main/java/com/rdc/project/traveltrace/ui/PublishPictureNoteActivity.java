package com.rdc.project.traveltrace.ui;

import android.view.View;

import com.gyf.barlibrary.ImmersionBar;
import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.base.BaseFragment;
import com.rdc.project.traveltrace.base.BaseRTRActivity;
import com.rdc.project.traveltrace.fragment.PublishPictureNoteFragment;

public class PublishPictureNoteActivity extends BaseRTRActivity {

    private PublishPictureNoteFragment mPublishPictureNoteFragment;

    @Override
    protected String getToolBarTitle() {
        return "发表";
    }

    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(this).titleBar(R.id.tool_bar)
                .navigationBarColor(R.color.gradient1)
                .init();
    }

    @Override
    protected BaseFragment createPTRFragment() {
        mPublishPictureNoteFragment = new PublishPictureNoteFragment();
        return mPublishPictureNoteFragment;
    }

    @Override
    protected boolean isNeedCreateViewStub() {
        return false;
    }

    @Override
    protected void onCreateViewStub(View view) {

    }

    @Override
    protected int getLayoutResID() {
        return 0;
    }

    @Override
    protected boolean isEnableSwipeBack() {
        return true;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onBackPressed() {
        if (mPublishPictureNoteFragment != null && mPublishPictureNoteFragment.isEditMode()) {
            mPublishPictureNoteFragment.onBackPressed();
        } else {
            super.onBackPressed();
        }

    }
}
