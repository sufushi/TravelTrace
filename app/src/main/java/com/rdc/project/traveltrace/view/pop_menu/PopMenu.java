package com.rdc.project.traveltrace.view.pop_menu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;
import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

public class PopMenu {

    private static final int DEFAULT_COLUMN_COUNT = 3;
    private static final int DEFAULT_DURATION = 300;
    private static final int DEFAULT_TENSION = 10;
    private static final int DEFAULT_FRICTION = 5;
    private static final int DEFAULT_HORIZONTAL_PADDING = 40;
    private static final int DEFAULT_VERTICAL_PADDING = 15;

    private int mTextSize = -1;
    private int mTextColor = -1;

    private OnMenuCloseListener mOnMenuCloseListener;

    private Activity mActivity;
    private int mColumnCount;
    private List<PopMenuItem> mMenuItems = new ArrayList<>();
    private RelativeLayout mAnimateLayout;
    private GridLayout mGridLayout;
    private int mDuration;
    private double mTension;
    private double mFriction;
    private int mHorizontalPadding;
    private int mVerticalPadding;
    private PopMenuItemClickListener mPopMenuItemClickListener;
    private boolean mIsCloseVisible = true;

    private int mScreenWidth;
    private int mScreenHeight;

    private int mCloseMenuMarginBottom = 15;
    private int mBackgroundColor = Color.parseColor("#f0f3f3f3");
    private int mCloseBtnResId = R.drawable.ic_action_close;
    private float mMarginTopRemainSpace = 1.5f;
    private boolean mIsmMalPositionAnimationOut = true;
    private int mMalPosition = 50;

    private boolean mIsShowing = false;

    private SpringSystem mSpringSystem;

    {
        mSpringSystem = SpringSystem.create();
    }

    public void setTextSize(int textSize) {
        this.mTextSize = textSize;
    }

    public void setTextColor(int textColor) {
        this.mTextColor = textColor;
    }

    public void setOnMenuCloseListener(OnMenuCloseListener onMenuCloseListener) {
        this.mOnMenuCloseListener = onMenuCloseListener;
    }

    public void setCloseVisible(boolean closeVisible) {
        this.mIsCloseVisible = closeVisible;
    }

    public PopSubView getMenuItem(int pos) {
        PopSubView subView = null;
        try {
            subView = (PopSubView) mGridLayout.getChildAt(pos);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        if (subView != null) {
            return subView;
        } else {
            return null;
        }
    }

    public void setBackgroundColor(int backgroundColor) {
        this.mBackgroundColor = backgroundColor;
    }

    public void setBackGroundTrasnparent() {
        this.mBackgroundColor = Color.parseColor("#00ffffff");
    }

    public void setCloseButtomResourceid(int closeBtnResId) {
        this.mCloseBtnResId = closeBtnResId;
    }

    public void setCloseMenuMarginBottom(int closeMenuMarginBottom) {
        this.mCloseMenuMarginBottom = closeMenuMarginBottom;
    }

    public float getmMarginTopRemainSpace() {
        return mMarginTopRemainSpace;
    }

    public void setmMarginTopRemainSpace(float mMarginTopRemainSpace) {
        this.mMarginTopRemainSpace = mMarginTopRemainSpace;
    }

    public boolean ismIsmalpositionAnimatOut() {
        return mIsmMalPositionAnimationOut;
    }

    public void setmIsmalpositionAnimatOut(boolean mIsmalpositionAnimatOut) {
        this.mIsmMalPositionAnimationOut = mIsmalpositionAnimatOut;
    }


    public int getMalPosition() {
        return mMalPosition;
    }

    public void setMalPosition(int malPosition) {
        this.mMalPosition = malPosition;
    }

    private PopMenu(Builder builder) {
        this.mActivity = builder.activity;
        this.mMenuItems.clear();
        this.mMenuItems.addAll(builder.itemList);

        this.mColumnCount = builder.columnCount;
        this.mDuration = builder.duration;
        this.mTension = builder.tension;
        this.mFriction = builder.friction;
        this.mHorizontalPadding = builder.horizontalPadding;
        this.mVerticalPadding = builder.verticalPadding;
        this.mPopMenuItemClickListener = builder.mPopMenuItemClickListener;

        mScreenWidth = mActivity.getResources().getDisplayMetrics().widthPixels;
        mScreenHeight = mActivity.getResources().getDisplayMetrics().heightPixels;
    }

    public void show() {
        buildAnimateGridLayout();

        if (mAnimateLayout.getParent() != null) {
            ViewGroup viewGroup = (ViewGroup) mAnimateLayout.getParent();
            viewGroup.removeView(mAnimateLayout);
        }

        ViewGroup decorView = (ViewGroup) mActivity.getWindow().getDecorView();
        decorView.addView(mAnimateLayout);

        // decorView.setPadding(0,0,0,getNavigationBarHeight(mActivity));
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) mAnimateLayout.getLayoutParams();
        lp.setMargins(0, 0, 0, getNavigationBarHeight(mActivity));
        mAnimateLayout.setLayoutParams(lp);

        //执行显示动画
        showSubMenus(mGridLayout);

        mIsShowing = true;
    }

    public void hide() {
        //先执行消失的动画
        if (mIsShowing && mGridLayout != null) {
            hideSubMenus(mGridLayout, new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    ViewGroup decorView = (ViewGroup) mActivity.getWindow().getDecorView();
                    decorView.removeView(mAnimateLayout);
                    if (mOnMenuCloseListener != null) {
                        mOnMenuCloseListener.onClose(mGridLayout);
                    }
                }
            });
            mIsShowing = false;
        }
    }

    public boolean isShowing() {
        return mIsShowing;
    }

    private void buildAnimateGridLayout() {
        mAnimateLayout = new RelativeLayout(mActivity);
        mAnimateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });

        mGridLayout = new GridLayout(mActivity);
        mGridLayout.setColumnCount(mColumnCount);
        mGridLayout.setBackgroundColor(mBackgroundColor);
        int hPadding = DensityUtil.dp2px(mHorizontalPadding, mActivity);
        int vPadding = DensityUtil.dp2px(mVerticalPadding, mActivity);
        int itemWidth = (mScreenWidth - (mColumnCount + 1) * hPadding) / mColumnCount;

        int rowCount = mMenuItems.size() % mColumnCount == 0 ? mMenuItems.size() / mColumnCount :
                mMenuItems.size() / mColumnCount + 1;

        int topMargin = (int) ((mScreenHeight - (itemWidth + vPadding) * rowCount + vPadding) / mMarginTopRemainSpace);

        for (int i = 0; i < mMenuItems.size(); i++) {
            final int position = i;
            PopSubView subView = new PopSubView(mActivity);
            if (mTextColor != -1) {
                subView.getTextView().setTextColor(mActivity.getResources().getColor(mTextColor));

            }
            if (mTextSize != -1) {
                subView.getTextView().setTextSize(mTextSize);

            }
            PopMenuItem menuItem = mMenuItems.get(i);
            subView.setPopMenuItem(menuItem);
            subView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mPopMenuItemClickListener != null) {
                        mPopMenuItemClickListener.onItemClick(PopMenu.this, position);
                    }
                    hide();
                }
            });

            GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
            lp.width = itemWidth;
            lp.leftMargin = hPadding;

            if (i / mColumnCount == 0) {
                lp.topMargin = topMargin;
            } else {
                lp.topMargin = vPadding;
            }
            mGridLayout.addView(subView, lp);
        }


        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams2.addRule(RelativeLayout.CENTER_HORIZONTAL);

        mAnimateLayout.addView(mGridLayout, layoutParams2);


        ImageView closeIv = new ImageView(mActivity);
        closeIv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        closeIv.setImageResource(mCloseBtnResId);
        closeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });
        if (mIsCloseVisible) {
            closeIv.setVisibility(View.VISIBLE);
        } else {
            closeIv.setVisibility(View.GONE);
        }

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layoutParams.bottomMargin = DensityUtil.dp2px(mCloseMenuMarginBottom, mActivity);
        mAnimateLayout.addView(closeIv, layoutParams);
    }

    private void showSubMenus(ViewGroup viewGroup) {
        if (viewGroup == null) return;
        int childCount = viewGroup.getChildCount();

        for (int i = 0; i < childCount; i++) {
            final View view = viewGroup.getChildAt(i);
            view.setVisibility(View.INVISIBLE);

            animationAction(i, view);

        }
    }

    private void animationAction(int i, final View view) {

        if (mIsmMalPositionAnimationOut) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.setVisibility(View.VISIBLE);
                    animateViewDirection(view, mScreenHeight, 0, mTension, mFriction);
                }
            }, i * mMalPosition);
        } else {

            view.setVisibility(View.VISIBLE);
            animateViewDirection(view, mScreenHeight, 0, mTension, mFriction);

        }
    }

    private void hideSubMenus(ViewGroup viewGroup, final AnimatorListenerAdapter listener) {
        if (viewGroup == null) return;
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = viewGroup.getChildAt(i);
            view.animate().translationY(mScreenHeight).setDuration(mDuration).setListener(listener).start();
        }
    }

    private void animateViewDirection(final View v, float from, float to, double tension, double friction) {
        Spring spring = mSpringSystem.createSpring();
        spring.setCurrentValue(from);
        spring.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(tension, friction));
        spring.addListener(new SimpleSpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                v.setTranslationY((float) spring.getCurrentValue());
            }
        });
        spring.setEndValue(to);
    }

    public static class Builder {
        private Activity activity;
        private int columnCount = DEFAULT_COLUMN_COUNT;
        private List<PopMenuItem> itemList = new ArrayList<>();
        private int duration = DEFAULT_DURATION;
        private double tension = DEFAULT_TENSION;
        private double friction = DEFAULT_FRICTION;
        private int horizontalPadding = DEFAULT_HORIZONTAL_PADDING;
        private int verticalPadding = DEFAULT_VERTICAL_PADDING;
        private PopMenuItemClickListener mPopMenuItemClickListener;

        public Builder attachToActivity(Activity activity) {
            this.activity = activity;
            return this;
        }

        public Builder columnCount(int count) {
            this.columnCount = count;
            return this;
        }

        public Builder addMenuItem(PopMenuItem menuItem) {
            this.itemList.add(menuItem);
            return this;
        }

        public Builder duration(int duration) {
            this.duration = duration;
            return this;
        }

        public Builder tension(double tension) {
            this.tension = tension;
            return this;
        }

        public Builder friction(double friction) {
            this.friction = friction;
            return this;
        }

        public Builder horizontalPadding(int padding) {
            this.horizontalPadding = padding;
            return this;
        }

        public Builder verticalPadding(int padding) {
            this.verticalPadding = padding;
            return this;
        }

        public Builder setOnItemClickListener(PopMenuItemClickListener listener) {
            this.mPopMenuItemClickListener = listener;
            return this;
        }

        public PopMenu build() {
            return new PopMenu(this);
        }
    }

    public boolean checkDeviceHasNavigationBar(Context context) {
        WindowManager windowManager = ((Activity) context).getWindowManager();
        DisplayMetrics dm = new DisplayMetrics();
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(dm);
        int screenHeight = dm.heightPixels;
        DisplayMetrics realDisplayMetrics = new DisplayMetrics();
        display.getRealMetrics(realDisplayMetrics);
        int screenRealHeight = realDisplayMetrics.heightPixels;
        return (screenRealHeight - screenHeight) > 0;
    }

    private int getNavigationBarHeight(Context context) {
        int navigationBarHeight = 0;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("navigation_bar_height", "dimen", "android");
        if (id > 0 && checkDeviceHasNavigationBar(context)) {
            navigationBarHeight = rs.getDimensionPixelSize(id);
        }
        return navigationBarHeight;
    }

    public interface OnMenuCloseListener {
        void onClose(View v);
    }

}
