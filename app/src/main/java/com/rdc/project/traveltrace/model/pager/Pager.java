package com.rdc.project.traveltrace.model.pager;

public class Pager {

    public static final int PAGE_SIZE = 10;

    private static final int ACTION_REFRESH = 0;
    private static final int ACTION_LOAD_MORE = 1;

    private int mAction;
    private int mPageContext;

    public Pager() {
        mAction = ACTION_REFRESH;
        mPageContext = 0;
    }

    public void refresh() {
        mAction = ACTION_REFRESH;
    }

    public void loadMore(int pageContext) {
        mPageContext = pageContext;
        mAction = ACTION_LOAD_MORE;
    }

    public boolean isRefresh() {
        return mAction == ACTION_REFRESH;
    }

    public boolean isLoadMore() {
        return mAction == ACTION_LOAD_MORE;
    }

    public int getPageContext() {
        return mPageContext;
    }
}
