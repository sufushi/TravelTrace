package com.rdc.project.traveltrace.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.entity.Picture;
import com.rdc.project.traveltrace.utils.MeasureUtil;
import com.rdc.project.traveltrace.view.dynamic_grid_view.BaseDynamicGridAdapter;

import java.util.List;

public class PictureNoteDynamicGridAdapter extends BaseDynamicGridAdapter {

    public PictureNoteDynamicGridAdapter(Context context, List<?> items, int columnCount) {
        super(context, items, columnCount);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PictureNoteViewHolder viewHolder;
        if (convertView == null) {
            ImageView imageView = new ImageView(getContext());
            int value = MeasureUtil.getScreenWidth(getContext()) / 3 - 10;
            imageView.setLayoutParams(new ViewGroup.LayoutParams(value, value));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            convertView = imageView;
            viewHolder = new PictureNoteViewHolder(imageView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (PictureNoteViewHolder) convertView.getTag();
        }
        viewHolder.bindView((Picture) getItem(position));
        return convertView;
    }

    private class PictureNoteViewHolder {

        private ImageView mImageView;

        public PictureNoteViewHolder(ImageView imageView) {
            mImageView = imageView;
        }

        public void bindView(Picture picture) {
            mImageView.setImageResource(R.drawable.test);
        }
    }
}
