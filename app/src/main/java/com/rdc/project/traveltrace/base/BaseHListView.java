package com.rdc.project.traveltrace.base;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ViewGroup;

public abstract class BaseHListView extends RecyclerView {

    protected BaseRecyclerViewAdapter mAdapter;

    public BaseHListView(Context context) {
        this(context, null);
    }

    public BaseHListView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseHListView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setOverScrollMode(OVER_SCROLL_NEVER);
        setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        mAdapter = createAdapter(context);
        setAdapter(mAdapter);
        setItemAnimator(new DefaultItemAnimator());
    }

    protected abstract BaseRecyclerViewAdapter createAdapter(Context context);

}
