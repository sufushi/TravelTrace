package com.rdc.project.traveltrace.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.rdc.project.traveltrace.base.BaseViewHolder;
import com.rdc.project.traveltrace.entity.Note;
import com.rdc.project.traveltrace.view.custom_view.NoteView;
import com.shizhefei.view.multitype.ItemViewProvider;

public class NoteViewProvider extends ItemViewProvider<Note> {

    private Context mContext;

    public NoteViewProvider(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(LayoutInflater layoutInflater, ViewGroup viewGroup, int i) {
        return new BaseViewHolder(new NoteView(mContext));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, Note note) {
        ((NoteView)viewHolder.itemView).setData(note);
    }

}
