package com.rdc.project.traveltrace.view.custom_view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.rdc.project.traveltrace.adapter.PuzzleTempleAdapter;
import com.rdc.project.traveltrace.arch.view.IView;
import com.rdc.project.traveltrace.base.BaseHListView;
import com.rdc.project.traveltrace.base.BaseRecyclerViewAdapter;
import com.rdc.project.traveltrace.entity.PuzzleTemple;
import com.rdc.project.traveltrace.entity.PuzzleTempleList;
import com.rdc.project.traveltrace.utils.DensityUtil;

import java.util.List;

public class PuzzleTempleHListView extends BaseHListView implements IView {

    private PuzzleTempleAdapter mAdapter;

    public PuzzleTempleHListView(Context context) {
        this(context, null);
    }

    public PuzzleTempleHListView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PuzzleTempleHListView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected BaseRecyclerViewAdapter createAdapter(Context context) {
        mAdapter = new PuzzleTempleAdapter(context);
        return mAdapter;
    }

    @Override
    protected int getItemDistance() {
        return DensityUtil.dp2px(10, getContext());
    }

    @Override
    public void setData(Object data) {
        if (data instanceof PuzzleTempleList) {
            List<PuzzleTemple> list = ((PuzzleTempleList) data).getPuzzleTempleList();
            mAdapter.updateData(list);
            setVisibility(VISIBLE);
        } else {
            setVisibility(INVISIBLE);
        }
    }

    @Override
    public void onActive() {

    }
}
