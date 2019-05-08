package com.rdc.project.traveltrace.manager;

import com.rdc.project.traveltrace.contract.IQueryContract;
import com.rdc.project.traveltrace.entity.RecommendCard;
import com.rdc.project.traveltrace.model.pager.Pager;
import com.rdc.project.traveltrace.model.pager.PagerUtil;
import com.rdc.project.traveltrace.model.query.RecommendCardQueryModel;
import com.rdc.project.traveltrace.presenter.QueryPresenterImpl;
import com.rdc.project.traveltrace.presenter.query.QueryPresenterImplFactory;
import com.rdc.project.traveltrace.utils.CollectionUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecommendCardListManager implements IQueryContract.View<RecommendCard> {

    private QueryPresenterImpl<RecommendCard, RecommendCardQueryModel> mPresenter;

    private List<RecommendCard> mRecommendCardList;

    private GetRecommendCardListCallback mCallback;

    private int mRetryCount;

    private RecommendCardListManager() {
        mPresenter = QueryPresenterImplFactory.createRecommendCardPresenterImpl(this);
        mRecommendCardList = new ArrayList<>();
        mRetryCount = 0;
        PagerUtil.getInstance().registerPager(RecommendCardQueryModel.class);
    }

    private static class RecommendCardListHolder {
        private static final RecommendCardListManager INSTANCE = new RecommendCardListManager();
    }

    public static RecommendCardListManager getInstance() {
        return RecommendCardListHolder.INSTANCE;
    }

    public void queryRecommendCardList(GetRecommendCardListCallback callback) {
        mCallback = callback;
        Pager pager = PagerUtil.getInstance().getPager(RecommendCardQueryModel.class);
        pager.loadMore(mRecommendCardList.size());
        mPresenter.query(null);
    }

    @Override
    public void onQuerySuccess(List<RecommendCard> list) {
        if (!CollectionUtil.isEmpty(list)) {
            mRecommendCardList.addAll(list);
            if (mCallback != null) {
                mCallback.onGetRecommendCardList(list);
            }
            mRetryCount = 0;
        } else {
            mRecommendCardList.clear();
            if (mRetryCount >= 3) {
                return;
            }
            queryRecommendCardList(mCallback);
            mRetryCount ++;
        }
    }

    @Override
    public void onQueryFailed(String response) {
        if (mCallback != null) {
            mCallback.onGetRecommendCardList(Collections.<RecommendCard>emptyList());
        }
    }

    public interface GetRecommendCardListCallback {

        void onGetRecommendCardList(List<RecommendCard> cardList);

    }
}
