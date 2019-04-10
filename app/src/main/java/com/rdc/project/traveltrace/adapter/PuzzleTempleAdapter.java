package com.rdc.project.traveltrace.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.rdc.project.traveltrace.arch.view.IView;
import com.rdc.project.traveltrace.base.BaseRecyclerViewAdapter;
import com.rdc.project.traveltrace.entity.PuzzleTemple;
import com.rdc.project.traveltrace.view.custom_view.PuzzleTempleItemView;

public class PuzzleTempleAdapter extends BaseRecyclerViewAdapter<PuzzleTemple> {

    private Context mContext;

    public PuzzleTempleAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PuzzleTempleViewHolder(new PuzzleTempleItemView(mContext));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((PuzzleTempleViewHolder) viewHolder).bindView(mDataList.get(i));
    }

    private class PuzzleTempleViewHolder extends BaseRvHolder {

        PuzzleTempleViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindView(PuzzleTemple puzzleTemple) {
            if (itemView instanceof IView) {
                ((IView) itemView).setData(puzzleTemple);
            }
        }
    }
}
