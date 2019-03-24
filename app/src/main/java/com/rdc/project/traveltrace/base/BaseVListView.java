package com.rdc.project.traveltrace.base;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.app.App;
import com.rdc.project.traveltrace.utils.DensityUtil;
import com.rdc.project.traveltrace.view.EmptyViewHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;

public abstract class BaseVListView extends SmartRefreshLayout {

    protected static final int DEFAULT_PADDING_SIZE = DensityUtil.dp2px(10, App.getAppContext());

    protected BaseRecyclerViewAdapter mAdapter;

    public BaseVListView(Context context) {
        this(context, null);
    }

    public BaseVListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseVListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setOverScrollMode(OVER_SCROLL_NEVER);
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        View topView = getTopView(context);
        if (topView != null) {
            linearLayout.addView(topView);
        }
        linearLayout.addView(createRecyclerView(context));
        linearLayout.setPadding(getLeftPadding(), getTopPadding(), getRightPadding(), getBottomPadding());
        this.addView(linearLayout);
        this.setRefreshHeader(new EmptyViewHeader(context));
        this.setRefreshFooter(new BallPulseFooter(context)
                .setSpinnerStyle(SpinnerStyle.Translate)
                .setAnimatingColor(getResources().getColor(R.color.colorPrimary))
                .setNormalColor(getResources().getColor(R.color.colorPrimary)));
        this.setDragRate(0.8f);
        this.setBackgroundColor(context.getResources().getColor(R.color.white));
    }

    protected View getTopView(Context context) {
        return null;
    }

    protected RecyclerView createRecyclerView(Context context) {
        RecyclerView recyclerView = new RecyclerView(context);
        recyclerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        recyclerView.setOverScrollMode(OVER_SCROLL_NEVER);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = createAdapter(context);
        recyclerView.setAdapter(mAdapter);
        return recyclerView;
    }

    protected int getLeftPadding() {
        return DEFAULT_PADDING_SIZE;
    }

    protected int getTopPadding() {
        return DEFAULT_PADDING_SIZE;
    }

    protected int getRightPadding() {
        return DEFAULT_PADDING_SIZE;
    }

    protected int getBottomPadding() {
        return DEFAULT_PADDING_SIZE;
    }

    protected abstract BaseRecyclerViewAdapter createAdapter(Context context);

}
