package com.rdc.project.traveltrace.view.custom_view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.arch.view.IView;
import com.rdc.project.traveltrace.entity.Picture;
import com.rdc.project.traveltrace.utils.DensityUtil;
import com.rdc.project.traveltrace.utils.MeasureUtil;

public class SelectedPictureGridItemView extends RelativeLayout implements IView {

    private ImageView mPhoto;

    public SelectedPictureGridItemView(Context context) {
        this(context, null);
    }

    public SelectedPictureGridItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SelectedPictureGridItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.item_selected_picture_grid_view, this);
        int size = (MeasureUtil.getScreenWidth(context) - DensityUtil.dp2px(12, context) * 2) / 3;
        setLayoutParams(new ViewGroup.LayoutParams(size, size));
        setBackgroundColor(context.getResources().getColor(R.color.lightGray));
        setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);
        mPhoto = findViewById(R.id.iv_photo);
    }

    @Override
    public void setData(Object data) {
        if (data instanceof Picture) {
            Picture picture = (Picture) data;
            String url = picture.getImgPath();
            if (!TextUtils.isEmpty(url)) {
                Glide.with(getContext()).load(url).into(mPhoto);
            } else {
                mPhoto.setBackgroundResource(R.drawable.ic_action_add_photo);
            }
        }
    }

}
