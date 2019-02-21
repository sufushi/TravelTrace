package com.rdc.project.traveltrace.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter {

    protected List<T> mDataList = new ArrayList<>();
    protected OnClickRecyclerViewListener mOnRecyclerViewListener;

    public void updateData(List<T> dataList) {
        mDataList.clear();
        appendData(dataList);
    }

    public void appendData(List<T> dataList) {
        if (null != dataList && !dataList.isEmpty()) {
            mDataList.addAll(dataList);
            notifyDataSetChanged();
        } else if (dataList != null && dataList.isEmpty()) {
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    /**
     * RecyclerView不提供点击事件，自定义点击事件
     */
    public void setOnRecyclerViewListener(OnClickRecyclerViewListener onRecyclerViewListener) {
        mOnRecyclerViewListener = onRecyclerViewListener;
    }

    public abstract class BaseRvHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public BaseRvHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        protected abstract void bindView(T t);

        @Override
        public void onClick(View v) {
            if (mOnRecyclerViewListener != null) {
                mOnRecyclerViewListener.onItemClick(getLayoutPosition(), v);
            }
        }
    }

}

