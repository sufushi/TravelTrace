package com.rdc.project.traveltrace.fragment.dialog_fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.ui.PictureProcessActivity;
import com.rdc.project.traveltrace.ui.PicturePuzzleActivity;
import com.rdc.project.traveltrace.utils.action.Action;
import com.rdc.project.traveltrace.utils.action.IActionListener;
import com.rdc.project.traveltrace.view.PublishDrawerItemView;
import com.rdc.project.traveltrace.view.PublishDrawerView;
import com.rdc.project.traveltrace.view.float_background.FloatBackground;
import com.rdc.project.traveltrace.view.float_background.FloatViewFactory;

import java.util.ArrayList;
import java.util.List;

import static com.rdc.project.traveltrace.fragment.PicturePuzzleFragment.PUZZLE_PIECE_SIZE;
import static com.rdc.project.traveltrace.fragment.PicturePuzzleFragment.PUZZLE_THEME;
import static com.rdc.project.traveltrace.fragment.PicturePuzzleFragment.PUZZLE_TYPE;

public class PublishDrawerDialogFragment extends TopDialogFragment implements IActionListener {

    private FloatBackground mFloatBackground;
    private PublishDrawerView mPublishDrawerView;

    @Override
    protected void onLayout() {
        mWindow.setLayout(mWidth, mHeight * 2 / 5);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_publish_drawer_dialog;
    }

    @Override
    protected void initView() {
        initFloatBackground();
        initPublishDrawerView();
    }

    private void initFloatBackground() {
        mFloatBackground = mRootView.findViewById(R.id.float_background);
        mFloatBackground.addFloatViewList(FloatViewFactory.createFloatViewList(getActivity()));
        mFloatBackground.post(() -> {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                int x = mFloatBackground.getMeasuredWidth() / 2;
                int y = 0;
                Animator animator = ViewAnimationUtils.createCircularReveal(mFloatBackground, x, y, 0, mFloatBackground.getMeasuredHeight());
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        mFloatBackground.setVisibility(View.VISIBLE);
                        mFloatBackground.startFloat();
                    }
                });
                animator.setDuration(800);
                animator.start();
            }
        });
    }

    private void initPublishDrawerView() {
        mPublishDrawerView = mRootView.findViewById(R.id.publish_drawer_view);
        List<PublishDrawerItemView.DrawerItemData> list = new ArrayList<>();
        list.add(new PublishDrawerItemView.DrawerItemData(R.drawable.ic_action_article, R.color.orangeRed, "说说", new Action()));
        list.add(new PublishDrawerItemView.DrawerItemData(R.drawable.ic_action_picture, R.color.colorPrimary, "相册", new Action()));
        list.add(new PublishDrawerItemView.DrawerItemData(R.drawable.ic_action_video, R.color.seaGreen, "拍摄", new Action()));
        mPublishDrawerView.setDrawerItemList(list, 3);
    }

    @Override
    protected void initListener() {
        mPublishDrawerView.setActionListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mFloatBackground != null) {
            mFloatBackground.endFloat();
        }
    }

    @Override
    public void onActionClick(Action action, View view) {
        if (getDialog().isShowing()) {
            getDialog().dismiss();
        }
//        Intent intent = new Intent(getActivity(), PicturePuzzleActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putInt(PUZZLE_TYPE, 1);
//        bundle.putInt(PUZZLE_PIECE_SIZE, 3);
//        bundle.putInt(PUZZLE_THEME, 2);
//        intent.putExtra("bundle", bundle);
//        startActivity(intent);

        Intent intent = new Intent(getActivity(), PictureProcessActivity.class);
        startActivity(intent);
    }

}
