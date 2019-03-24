package com.rdc.project.traveltrace.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.rdc.project.traveltrace.arch.view.IView;
import com.rdc.project.traveltrace.base.BaseRecyclerViewAdapter;
import com.rdc.project.traveltrace.entity.Comment;
import com.rdc.project.traveltrace.view.custom_view.CommentListItemView;

public class CommentListAdapter extends BaseRecyclerViewAdapter<Comment> {

    private Context mContext;

    public CommentListAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        CommentListItemView view = new CommentListItemView(mContext);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ((CommentViewHolder) viewHolder).bindView(mDataList.get(position));
    }

    private class CommentViewHolder extends BaseRvHolder {

        CommentViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindView(Comment comment) {
            if (itemView instanceof IView) {
                ((IView) itemView).setData(comment);
                itemView.setVisibility(View.VISIBLE);
            } else {
                itemView.setVisibility(View.GONE);
            }
        }
    }
}
