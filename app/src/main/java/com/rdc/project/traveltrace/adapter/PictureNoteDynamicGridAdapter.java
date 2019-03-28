package com.rdc.project.traveltrace.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.rdc.project.traveltrace.entity.Picture;
import com.rdc.project.traveltrace.utils.DensityUtil;
import com.rdc.project.traveltrace.utils.MeasureUtil;
import com.rdc.project.traveltrace.view.custom_view.PictureNoteDynamicGridView;
import com.rdc.project.traveltrace.view.dynamic_grid_view.BaseDynamicGridAdapter;

import java.util.List;

public class PictureNoteDynamicGridAdapter extends BaseDynamicGridAdapter {

    private PictureNoteDynamicGridView.OnDynamicGridViewItemClickListener mOnDynamicGridViewItemClickListener;
    private boolean mIsEditing = false;

    public PictureNoteDynamicGridAdapter(Context context, List<?> items, int columnCount) {
        super(context, items, columnCount);
    }

    public void setOnDynamicGridViewItemClickListener(PictureNoteDynamicGridView.OnDynamicGridViewItemClickListener onDynamicGridViewItemClickListener) {
        mOnDynamicGridViewItemClickListener = onDynamicGridViewItemClickListener;
    }

    public void setIsEdit(boolean isEdit) {
        mIsEditing = isEdit;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PictureNoteViewHolder viewHolder;
        if (convertView == null) {
            PictureNoteDynamicGridView view = new PictureNoteDynamicGridView(getContext());
            int value = (MeasureUtil.getScreenWidth(getContext()) - DensityUtil.dp2px(14, getContext()) * 2) / 3;
            view.setLayoutParams(new ViewGroup.LayoutParams(value, value));
            view.setOnDynamicGridViewItemClickListener(mOnDynamicGridViewItemClickListener);
            view.setPosition(position);
            convertView = view;
            viewHolder = new PictureNoteViewHolder(view);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (PictureNoteViewHolder) convertView.getTag();
        }
        viewHolder.bindView((Picture) getItem(position));
        viewHolder.updateView(mIsEditing);
        return convertView;
    }

    private class PictureNoteViewHolder {

        private PictureNoteDynamicGridView mPictureNoteDynamicGridView;

        PictureNoteViewHolder(PictureNoteDynamicGridView view) {
            mPictureNoteDynamicGridView = view;
        }

        void bindView(Picture picture) {
            mPictureNoteDynamicGridView.setData(picture);
        }

        void updateView(boolean isEdit) {
            mPictureNoteDynamicGridView.setEditing(isEdit);
        }
    }

}
