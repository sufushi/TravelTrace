package com.rdc.project.traveltrace.ui;

import android.graphics.drawable.Drawable;
import android.view.View;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.base.BaseBounceActivity;
import com.rdc.project.traveltrace.base.BaseFragment;
import com.rdc.project.traveltrace.fragment.PersonDetailFragment;
import com.rdc.project.traveltrace.utils.DensityUtil;

public class PersonDetailActivity extends BaseBounceActivity {

    private PersonDetailFragment mPersonDetailFragment;

    @Override
    protected String getToolBarTitle() {
        return getString(R.string.string_person_detail);
    }

    @Override
    protected BaseFragment createPTRFragment() {
        mPersonDetailFragment = new PersonDetailFragment();
        return mPersonDetailFragment;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        int size = DensityUtil.dp2px(24, this);
        Drawable drawable = getResources().getDrawable(R.drawable.ic_complete);
        drawable.setBounds(0, 0, size, size);
        mActionBtn.setCompoundDrawables(drawable, null, null, null);
        mActionBtn.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initListener() {
        mActionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPersonDetailFragment != null) {
                    mPersonDetailFragment.onActionBtnClick();
                }
            }
        });
    }

}
