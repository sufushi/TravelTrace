package com.rdc.project.traveltrace.view.swipe_away_dialog.dialog_factory;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.meg7.widget.CircleImageView;
import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.base.BaseSwipeAwayDialogFragment;
import com.rdc.project.traveltrace.entity.User;
import com.rdc.project.traveltrace.manager.FollowListManager;
import com.rdc.project.traveltrace.utils.blur.BlurUtil;
import com.rdc.project.traveltrace.view.swipe_away_dialog.support.v4.SwipeAwayDialogFragment;
import com.rdc.project.traveltrace.view.toast.CommonToast;

import cn.bmob.v3.BmobUser;

public class UserDetailDialog implements BaseSwipeAwayDialogFragment.DialogBuilder, View.OnClickListener, FollowListManager.IUnFollowListener, FollowListManager.IFollowListener {

    private User mUser;
    private Context mContext;
    private TextView mFollowBtn;

    @Override
    public Dialog create(Context context, SwipeAwayDialogFragment fragment) {
        mContext = context;
        Bundle args = fragment.getArguments();
        String userIconUrl;
        String backgroundUrl;
        String username;
        User user = null;
        if (args != null) {
            user = (User) args.getSerializable("user");
            mUser = user;
        }
        TransparentBgDialog dialog = new TransparentBgDialog(context);
        dialog.setContentView(R.layout.dialog_user_detail);
        if (user != null) {
            userIconUrl = user.getUserIcon();
            backgroundUrl = user.getBackgroundUrl();
            if (TextUtils.isEmpty(backgroundUrl)) {
                backgroundUrl = userIconUrl;
            }
            username = user.getUsername();
            CircleImageView userIcon = dialog.findViewById(R.id.user_icon);
            ImageView background = dialog.findViewById(R.id.user_background);
            TextView userName = dialog.findViewById(R.id.user_name);
            mFollowBtn = dialog.findViewById(R.id.btn_follow);
            assert userIcon != null;
            Glide.with(fragment).load(userIconUrl).into(userIcon);
            Glide.with(fragment).asBitmap().load(backgroundUrl).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                    BlurUtil.with(fragment.getContext()).from(bitmap).into(background);
                }
            });
            assert userName != null;
            userName.setText(username);
            assert mFollowBtn != null;
            if (FollowListManager.getInstance().hasFollow(user)) {
                mFollowBtn.setText(R.string.string_has_follow);
            } else {
                mFollowBtn.setText(R.string.string_follow_user);
            }
            mFollowBtn.setOnClickListener(this);
        }
        return dialog;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_follow:
                if (BmobUser.isLogin() && mUser != null) {
                    if (FollowListManager.getInstance().hasFollow(mUser)) {
                        FollowListManager.getInstance().unFollowUser(mUser, this);
                    } else {
                        FollowListManager.getInstance().followUser(mUser, this);
                    }
                } else {
                    CommonToast.info(mContext, "请先登录").show();
                }
                break;
        }
    }

    @Override
    public void onFollowSuccess(String response) {
        CommonToast.success(mContext, "关注成功").show();
        mFollowBtn.setText(R.string.string_has_follow);
        FollowListManager.getInstance().addFollow(mUser);
    }

    @Override
    public void onFollowFailed(String response) {
        CommonToast.error(mContext, "关注失败").show();
        mFollowBtn.setText(R.string.string_follow_user);
    }

    @Override
    public void onUnFollowSuccess(String response) {
        CommonToast.success(mContext, "关注取消").show();
        mFollowBtn.setText(R.string.string_follow_user);
        FollowListManager.getInstance().removeFollow(mUser);
    }

    @Override
    public void onUnFollowFailed(String response) {
        CommonToast.success(mContext, "取消失败").show();
        mFollowBtn.setText(R.string.string_has_follow);
    }

    private class TransparentBgDialog extends AppCompatDialog {

        public TransparentBgDialog(@NonNull Context context) {
            super(context, R.style.TransparentDialogStyle);
        }

//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            Window window = getWindow();
//            assert window != null;
//            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//            setCanceledOnTouchOutside(true);
//        }

//        @Override
//        public void setContentView(int layoutResID) {
//            CardView cardView = new CardView(getContext());
//            cardView.setRadius(DensityUtil.dp2px(10, getContext()));
//            cardView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//            View content = LayoutInflater.from(getContext()).inflate(layoutResID, null);
//            cardView.addView(content, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//            super.setContentView(cardView);
//        }
    }
}
