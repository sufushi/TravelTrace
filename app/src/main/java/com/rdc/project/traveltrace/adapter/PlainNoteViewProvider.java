package com.rdc.project.traveltrace.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.rdc.project.traveltrace.base.BaseViewHolder;
import com.rdc.project.traveltrace.entity.PlainNote;
import com.rdc.project.traveltrace.view.custom_view.PLainNoteView;
import com.shizhefei.view.multitype.ItemViewProvider;

public class PlainNoteViewProvider extends ItemViewProvider<PlainNote> {

    private Context mContext;

    public PlainNoteViewProvider(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(LayoutInflater layoutInflater, ViewGroup viewGroup, int i) {
        return new BaseViewHolder(new PLainNoteView(mContext));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, PlainNote plainNote) {
        ((PLainNoteView)viewHolder.itemView).setData(plainNote);
    }
}
