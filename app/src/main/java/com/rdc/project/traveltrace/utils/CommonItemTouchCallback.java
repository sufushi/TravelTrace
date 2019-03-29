package com.rdc.project.traveltrace.utils;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.rdc.project.traveltrace.base.BaseRecyclerViewAdapter;

import java.util.Collections;
import java.util.List;

public class CommonItemTouchCallback extends ItemTouchHelper.Callback {

    private BaseRecyclerViewAdapter mAdapter;

    public void setAdapter(BaseRecyclerViewAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        // 获取触摸响应的方向   包含两个 1.拖动dragFlags 2.侧滑删除swipeFlags
        // 代表只能是向左侧滑删除，当前可以是这样ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT
        if (mAdapter != null) {
            int size = mAdapter.getItemCount();
            int position = viewHolder.getAdapterPosition();
            if (position == size - 1) {
                return makeMovementFlags(0, 0);
            }
        }
        int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        int dragFlags;
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            dragFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        } else {
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        }
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        //获取到原来的位置
        int fromPosition = viewHolder.getAdapterPosition();
        //获取到拖到的位置
        int targetPosition = target.getAdapterPosition();
        if (mAdapter != null) {
            List dataList = mAdapter.getDataList();
            int size = mAdapter.getItemCount();
            if (fromPosition == size - 1 || targetPosition == size - 1) {
                return false;
            }
            if (fromPosition < targetPosition) {
                for (int i = fromPosition; i < targetPosition; i++) {
                    Collections.swap(dataList, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > targetPosition; i--) {
                    Collections.swap(dataList, i, i - 1);
                }
            }
            mAdapter.notifyItemMoved(fromPosition, targetPosition);
        }
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
        int position = viewHolder.getAdapterPosition();
        if (mAdapter != null) {
            List dataList = mAdapter.getDataList();
            int size = mAdapter.getItemCount();
            if (position == size - 1) {
                return;
            }
            dataList.remove(position);
            mAdapter.notifyItemRemoved(position);
        }
    }
}
