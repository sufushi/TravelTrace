package com.rdc.project.traveltrace.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.rdc.project.traveltrace.base.BaseViewHolder;
import com.rdc.project.traveltrace.entity.PictureNote;
import com.rdc.project.traveltrace.view.custom_view.PictureNoteView;
import com.shizhefei.view.multitype.ItemViewProvider;

public class PictureNoteViewProvider extends ItemViewProvider<PictureNote> {

    private Context mContext;

    public PictureNoteViewProvider(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(LayoutInflater layoutInflater, ViewGroup viewGroup, int i) {
        return new BaseViewHolder(new PictureNoteView(mContext));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, PictureNote pictureNote) {
        ((PictureNoteView)viewHolder.itemView).setData(pictureNote);
    }

}
