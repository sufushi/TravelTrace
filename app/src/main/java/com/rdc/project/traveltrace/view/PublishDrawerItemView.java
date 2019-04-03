package com.rdc.project.traveltrace.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.utils.DensityUtil;
import com.rdc.project.traveltrace.utils.TintUtil;
import com.rdc.project.traveltrace.utils.action.Action;

public class PublishDrawerItemView extends RelativeLayout {

    private ImageView mDrawerIcon;
    private TextView mDrawerTitle;

    public PublishDrawerItemView(Context context) {
        this(context, null);
    }

    public PublishDrawerItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PublishDrawerItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.item_publish_drawer, this);
        setBackgroundColor(context.getResources().getColor(R.color.transparent));
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setGravity(Gravity.CENTER);
        int padding = DensityUtil.dp2px(10, context);
        setPadding(padding, padding, padding, padding);
        mDrawerIcon = findViewById(R.id.drawer_icon);
        mDrawerTitle = findViewById(R.id.drawer_title);
    }

    public void setDrawerItemData(DrawerItemData data) {
        if (data == null) {
            return;
        }
        int resId = data.getResId();
        int color = data.getColor();
        String text = data.getText();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            TintUtil.tint(mDrawerIcon, resId, color);
        }
        mDrawerTitle.setText(text);
    }

    public static class DrawerItemData {

        private int mResId;
        private int mColor;
        private String mText;
        private Action mAction;

        public DrawerItemData() {

        }

        public DrawerItemData(int resId, int color, String text, Action action) {
            mResId = resId;
            mColor = color;
            mText = text;
            mAction = action;
        }

        public int getResId() {
            return mResId;
        }

        public void setResId(int resId) {
            mResId = resId;
        }

        public int getColor() {
            return mColor;
        }

        public void setColor(int color) {
            mColor = color;
        }

        public String getText() {
            return mText;
        }

        public void setText(String text) {
            mText = text;
        }

        public Action getAction() {
            return mAction;
        }

        public void setAction(Action action) {
            mAction = action;
        }
    }
}
