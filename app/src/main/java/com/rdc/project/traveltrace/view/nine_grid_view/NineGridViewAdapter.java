package com.rdc.project.traveltrace.view.nine_grid_view;

import android.content.Context;
import android.widget.ImageView;

import com.rdc.project.traveltrace.R;

import java.io.Serializable;
import java.util.List;

public abstract class NineGridViewAdapter implements Serializable {

    protected Context context;
    private List<ImageInfo> imageInfo;

    public NineGridViewAdapter(Context context, List<ImageInfo> imageInfo) {
        this.context = context;
        this.imageInfo = imageInfo;
    }

    /**
     * 如果要实现图片点击的逻辑，重写此方法即可
     *
     * @param context      上下文
     * @param nineGridView 九宫格控件
     * @param index        当前点击图片的的索引
     * @param imageInfo    图片地址的数据集合
     */
    protected void onImageItemClick(Context context, NineGridView nineGridView, int index, List<ImageInfo> imageInfo) {
    }

    protected boolean onImageItemLongClick(Context context, NineGridView nineGridView, int index, List<ImageInfo> imageInfo) {
        return false;
    }
    protected ImageView generateImageView(Context context) {
        NineGridViewWrapper imageView = new NineGridViewWrapper(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.drawable.ic_default_color);
        return imageView;
    }

    public List<ImageInfo> getImageInfo() {
        return imageInfo;
    }

    public void setImageInfoList(List<ImageInfo> imageInfo) {
        this.imageInfo = imageInfo;
    }
}
