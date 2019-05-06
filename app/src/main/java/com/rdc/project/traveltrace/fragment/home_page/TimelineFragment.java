package com.rdc.project.traveltrace.fragment.home_page;

import android.os.Bundle;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.adapter.RecommendCardStackAdapter;
import com.rdc.project.traveltrace.base.BasePTRFragment;
import com.rdc.project.traveltrace.base.OnRefreshListener;
import com.rdc.project.traveltrace.entity.RecommendCard;
import com.rdc.project.traveltrace.manager.RecommendCardListManager;
import com.rdc.project.traveltrace.view.swipe_stack_view.SwipeStack;
import com.scwang.smartrefresh.header.DropBoxHeader;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;

import java.util.List;
import java.util.Objects;

public class TimelineFragment extends BasePTRFragment implements OnRefreshListener, RecommendCardListManager.GetRecommendCardListCallback {

//    private FoldingCell mFoldingCell;
//    private RecyclerView mRecyclerView;
//    private TimelineContentAdapter mAdapter;

//    private ArrayList<String> mData;
    private SwipeStack mSwipeStack;
//    private SwipeStackAdapter mSwipeStackAdapter;
    private RecommendCardStackAdapter mAdapter;

    @Override
    protected RefreshHeader createRefreshHeader() {
        return new DropBoxHeader(Objects.requireNonNull(getActivity()));
    }

    @Override
    protected RefreshFooter createRefreshFooter() {
        return new BallPulseFooter(Objects.requireNonNull(getActivity()))
                .setSpinnerStyle(SpinnerStyle.Translate)
                .setAnimatingColor(getResources().getColor(R.color.colorPrimary))
                .setNormalColor(getResources().getColor(R.color.colorPrimary));
    }

    @Override
    protected void configRefreshLayout() {
        mRefreshLayout.setDragRate(0.8f);
    }

    @Override
    protected OnRefreshListener createRefreshListener() {
        return this;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_timeline;
    }

    @Override
    protected void initData(Bundle bundle) {
//        mAdapter = new TimelineContentAdapter(getActivity());
//        List<TimeLineContent> list = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            list.add(new TimeLineContent());
//        }
//        mAdapter.appendData(list);

//        mData = new ArrayList<>();
//        for (int x = 0; x < 5; x++) {
//            mData.add(getString(R.string.app_name) + " " + (x + 1));
//        }
//        mSwipeStackAdapter = new SwipeStackAdapter(getActivity(), mData);

//        String[] array = new String[]{"https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2059324361,2516966890&fm=27&gp=0.jpg",
//                "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3782685451,3066622536&fm=27&gp=0.jpg",
//                "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2791261768,1320060678&fm=27&gp=0.jpg",
//                "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=280692610,4239256719&fm=27&gp=0.jpg",
//                "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=260329114,3367670618&fm=27&gp=0.jpg",
//                "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1178503053,3917746059&fm=26&gp=0.jpg",
//                "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=753449481,625992609&fm=26&gp=0.jpg"};
//
//        List<String> list = new ArrayList<>(Arrays.asList(array));
//
//        List<RecommendCard> cardList = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            RecommendCard card = new RecommendCard();
//            card.setContent("hahahahhahahahahhahahahah" + i);
//            card.setImgUrls(list);
//            cardList.add(card);
//        }

        mAdapter = new RecommendCardStackAdapter(getActivity());
        RecommendCardListManager.getInstance().queryRecommendCardList(this);
//        mAdapter.updateData(cardList);

//        List<BmobObject> objectList = new ArrayList<>(cardList);
//        mUploadBatchPresenter = new UploadBatchPresenterImpl(this);
//        mUploadBatchPresenter.uploadBatch(objectList);
    }

    @Override
    protected void initView() {
//        mFoldingCell = mRootView.findViewById(R.id.folding_cell);
//        mFoldingCell.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mFoldingCell.toggle(false);
//            }
//        });
//        mRecyclerView = mRootView.findViewById(R.id.cell_content_recycler_view);
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
//        mRecyclerView.setAdapter(mAdapter);

        mSwipeStack = mRootView.findViewById(R.id.swipe_stack);
//        mSwipeStack.setAdapter(mSwipeStackAdapter);
        mSwipeStack.setAdapter(mAdapter);
    }

    @Override
    protected void setListener() {
//        mAdapter.setOnRecyclerViewListener(new OnClickRecyclerViewListener() {
//            @Override
//            public void onItemClick(int position, View view) {
//                mFoldingCell.toggle(false);
//            }
//
//            @Override
//            public boolean onItemLongClick(int position) {
//                return false;
//            }
//        });
        mSwipeStack.setListener(new SwipeStack.SwipeStackListener() {
            @Override
            public void onViewSwipedToLeft(int position) {

            }

            @Override
            public void onViewSwipedToRight(int position) {

            }

            @Override
            public void onStackEmpty() {
                RecommendCardListManager.getInstance().queryRecommendCardList(TimelineFragment.this);
            }
        });
    }

    @Override
    public void onRefresh() {
        mRefreshLayout.finishRefresh(2000);
    }

    @Override
    public void onLoadMore() {
        mRefreshLayout.finishLoadMore(2000);
    }

    @Override
    public void onGetRecommendCardList(List<RecommendCard> cardList) {
        mAdapter.updateData(cardList);
        mSwipeStack.resetStack();
    }
}
