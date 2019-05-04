package com.rdc.project.traveltrace.fragment.dialog_fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewAnimationUtils;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.utils.action.Action;
import com.rdc.project.traveltrace.utils.action.ActionManager;
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
import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_NAME_PICTURE_PUZZLE;
import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_NAME_PUBLISH_PICTURE_NOTE;
import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_NAME_CAPTURE;
import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_NAME_VIDEO_RECORD;
import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_PRE;

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
        mFloatBackground.post(new Runnable() {
            @Override
            public void run() {
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
            }
        });
    }

    private void initPublishDrawerView() {
        mPublishDrawerView = mRootView.findViewById(R.id.publish_drawer_view);
        List<PublishDrawerItemView.DrawerItemData> list = new ArrayList<>();
        Action action1 = new Action(ACTION_PRE + ACTION_NAME_PUBLISH_PICTURE_NOTE);
        Action action2 = new Action(ACTION_PRE + ACTION_NAME_PICTURE_PUZZLE + "?" + PUZZLE_TYPE + "=1" + "&" + PUZZLE_PIECE_SIZE + "=3" + "&" + PUZZLE_THEME + "=2");
        Action action3 = new Action(ACTION_PRE + ACTION_NAME_VIDEO_RECORD);
        list.add(new PublishDrawerItemView.DrawerItemData(R.drawable.ic_action_article, R.color.orangeRed, "说说", action1));
        list.add(new PublishDrawerItemView.DrawerItemData(R.drawable.ic_action_picture, R.color.colorPrimary, "拼图", action2));
        list.add(new PublishDrawerItemView.DrawerItemData(R.drawable.ic_action_video, R.color.seaGreen, "拍摄", action3));
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
        ActionManager.doAction(action, getActivity());
    }

}
