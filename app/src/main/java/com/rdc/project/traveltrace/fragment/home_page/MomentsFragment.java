package com.rdc.project.traveltrace.fragment.home_page;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.adapter.FollowListViewProvider;
import com.rdc.project.traveltrace.adapter.PictureNoteViewProvider;
import com.rdc.project.traveltrace.adapter.PlainNoteViewProvider;
import com.rdc.project.traveltrace.adapter.VideoNoteViewProvider;
import com.rdc.project.traveltrace.arch.model.impl.NoteRecordModel;
import com.rdc.project.traveltrace.arch.view_controller.MultiTypeViewController;
import com.rdc.project.traveltrace.arch.view_model.BmobViewModel;
import com.rdc.project.traveltrace.arch.view_model.ViewModelBinder;
import com.rdc.project.traveltrace.base.BasePTRFragment;
import com.rdc.project.traveltrace.base.OnRefreshListener;
import com.rdc.project.traveltrace.entity.FollowList;
import com.rdc.project.traveltrace.entity.NoteRecord;
import com.rdc.project.traveltrace.entity.PictureNote;
import com.rdc.project.traveltrace.entity.PlainNote;
import com.rdc.project.traveltrace.entity.VideoNote;
import com.rdc.project.traveltrace.utils.player.VideoListViewManager;
import com.scwang.smartrefresh.header.DeliveryHeader;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.shizhefei.view.multitype.ItemBinderFactory;

import java.util.Objects;

public class MomentsFragment extends BasePTRFragment implements OnRefreshListener, NoteRecordModel.INoteRecordModelListener {

    private MultiTypeViewController mMultiTypeViewController;
    private BmobViewModel<NoteRecord, NoteRecordModel> mBmobViewModel;
    private NoteRecordModel mNoteRecordModel;

//    private MultiTypeAdapter<ListItem> mMultiTypeAdapter;
//    private MultiTypeView mMultiTypeView;
//    private List<ListItem> mList = new ArrayList<>();

    private VideoListViewManager mVideoListViewManager;

//    private RecyclerViewItemPositionGetter mRecyclerViewItemPositionGetter;
//    private final ListItemsVisibilityCalculator mListItemsVisibilityCalculator = new SingleListViewItemActiveCalculator(new DefaultSingleItemCalculatorCallback(), mList);
//
//    private int mScrollState;

    @Override
    protected RefreshHeader createRefreshHeader() {
        DeliveryHeader header = new DeliveryHeader(getActivity());
        header.setBackgroundResource(R.drawable.bg_liner_horizontal_gradient);
        return header;
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
//        mRefreshLayout.autoLoadMore();
        mRefreshLayout.setDragRate(0.8f);
    }

    @Override
    protected OnRefreshListener createRefreshListener() {
        return this;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_moments;
    }

    @Override
    protected void initData(Bundle bundle) {
        mVideoListViewManager = new VideoListViewManager(getActivity());
//        String[] array1 = new String[]{"https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2059324361,2516966890&fm=27&gp=0.jpg",
//        "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3782685451,3066622536&fm=27&gp=0.jpg",
//        "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2791261768,1320060678&fm=27&gp=0.jpg",
//        "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=280692610,4239256719&fm=27&gp=0.jpg",
//        "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=260329114,3367670618&fm=27&gp=0.jpg",
//        "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1178503053,3917746059&fm=26&gp=0.jpg",
//        "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=753449481,625992609&fm=26&gp=0.jpg",
//        "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2116145824,3773530839&fm=26&gp=0.jpg",
//        "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3018734602,300631237&fm=26&gp=0.jpg",
//        "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1272896468,4158026833&fm=26&gp=0.jpg"};
//        String[] array2 = new String[]{"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2679956458,2260393058&fm=26&gp=0.jpg"};
//        String[] array3 = new String[]{"https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1688409857,3608354135&fm=26&gp=0.jpg",
//        "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=4059544711,4256987098&fm=26&gp=0.jpg",
//        "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=590934277,1104229766&fm=26&gp=0.jpg",
//        "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2419113560,193571489&fm=26&gp=0.jpg"};
//        List<User> userList = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            User user = new User();
//            user.setUserIcon("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3065338749,2306246489&fm=26&gp=0.jpg");
//            user.setUserName("熊" + i);
//            userList.add(user);
//        }
//        mList.add(new FollowList(userList));
//        for (int i = 0; i < 3; i++) {
//            VideoNote videoNote = new VideoNote();
//            PictureNote pictureNote = new PictureNote();
//            pictureNote.setText("三亚市位于海南岛最南端，是中国最南部的滨海旅游城市。因三亚河（古名临川水）由三亚东" +
//                    "西河至此会合，成“丫”字形，故取名“三亚”。同时它也是是一个黎、苗、回、汉多民族聚居的地区" +
//                    "三亚拥有被无数城市嫉妒的清新空气，柔和海滩。在沙滩悠闲散步、沐浴傍晚温和阳光，在海边玩耍，" +
//                    "在雨林里面呼吸健康，欣赏自然奇观，一切都是那么令人享受。三亚连续四次成为举办世界小姐总决" +
//                    "赛的所在地，同时世界大力士锦标赛，世界比基尼小姐大赛等等国际大赛都因为三亚迷人的热带海滨" +
//                    "风光而选择了它。毫无疑问，三亚是中国不可多得的能成为世界顶级度假圣地的城市。 习惯了都市" +
//                    "快节奏生活的人们，纷纷涌入这座海滨小城，从而带来了许多城市管理问题。旅游资源的丰富与旅游" +
//                    "服务之间的矛盾，导致各种宰客现象层出不穷，这让不少到过三亚的人为之不满。但是这一切都会慢" +
//                    "慢改变，因为没有什么会阻挡三亚成为国际度假圣地的脚步。 三亚门户网站 http://www.sanya.gov.cn/" + i);
//            videoNote.setText(pictureNote.getText());
//            User user = new User();
//            if (i == 0) {
//                pictureNote.setImgUrls(Arrays.asList(array1));
//                user.setUserIcon("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1247179626,1114338020&fm=26&gp=0.jpg");
//                user.setUserName("pappy");
//                user.setUserExtraMsg("3-23");
//                pictureNote.setUser(user);
//                pictureNote.setLikeCount(16);
//                pictureNote.setCommentCount(99);
//                pictureNote.setLike(false);
//            } else if (i == 1) {
//                pictureNote.setImgUrls(Arrays.asList(array2));
//                user.setUserIcon("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=313744057,187509694&fm=26&gp=0.jpg");
//                user.setUserName("tom");
//                user.setUserExtraMsg("3-21");
//                pictureNote.setUser(user);
//                pictureNote.setLikeCount(9);
//                pictureNote.setCommentCount(666);
//                pictureNote.setLike(true);
//            } else {
//                pictureNote.setImgUrls(Arrays.asList(array3));
//                user.setUserIcon("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3065338749,2306246489&fm=26&gp=0.jpg");
//                user.setUserName("熊大");
//                user.setUserExtraMsg("3-20");
//                pictureNote.setUser(user);
//                pictureNote.setLikeCount(250);
//                pictureNote.setCommentCount(18);
//                pictureNote.setLike(false);
//            }
//            videoNote.setUser(pictureNote.getUser());
//            videoNote.setLikeCount(pictureNote.getLikeCount());
//            videoNote.setCommentCount(pictureNote.getCommentCount());
//            videoNote.setLike(!pictureNote.isLike());
//            videoNote.setVideoUrl("http://www.w3school.com.cn/example/html5/mov_bbb.mp4");
//            mList.add(pictureNote);
//            mList.add(videoNote);
//        }
//        for (int i = 0; i < 4; i++) {
//            mList.add(new FragmentDataListItem(InfoFragment.class, "InfoFragment" + i));
//        }
        ItemBinderFactory itemBinderFactory = new ItemBinderFactory(getFragmentManager());
        itemBinderFactory.registerProvider(PlainNote.class, new PlainNoteViewProvider(getActivity()));
        itemBinderFactory.registerProvider(PictureNote.class, new PictureNoteViewProvider(getActivity()));
        itemBinderFactory.registerProvider(VideoNote.class, new VideoNoteViewProvider(getActivity(), mVideoListViewManager));
        itemBinderFactory.registerProvider(FollowList.class, new FollowListViewProvider(getActivity()));
//        mMultiTypeAdapter = new MultiTypeAdapter<>(mList, itemBinderFactory);

        mMultiTypeViewController = new MultiTypeViewController(getActivity(), itemBinderFactory);
        mNoteRecordModel = new NoteRecordModel();
        mNoteRecordModel.register(this);
        mBmobViewModel = new BmobViewModel<>(mNoteRecordModel);
        ViewModelBinder.bind(getActivity(), mBmobViewModel, mMultiTypeViewController);
    }

    @Override
    protected void initView() {
        LinearLayout mMomentsContainer = mRootView.findViewById(R.id.moments_container);
        View view = mMultiTypeViewController.getView();
        if (view != null) {
            mMomentsContainer.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
//        mMultiTypeView = mRootView.findViewById(R.id.recycler_view_home);
//        mMultiTypeView.setAdapter(mMultiTypeAdapter);
//        mRecyclerViewItemPositionGetter = new RecyclerViewItemPositionGetter((LinearLayoutManager) mMultiTypeView.getLayoutManager(), mMultiTypeView);
    }

    @Override
    protected void setListener() {
//        mMultiTypeView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                mScrollState = newState;
//                if(newState == RecyclerView.SCROLL_STATE_IDLE && !mList.isEmpty()){
//                    LinearLayoutManager layoutManager = (LinearLayoutManager) mMultiTypeView.getLayoutManager();
//                    if (layoutManager != null) {
//                        mListItemsVisibilityCalculator.onScrollStateIdle(
//                                mRecyclerViewItemPositionGetter,
//                                layoutManager.findFirstVisibleItemPosition(),
//                                layoutManager.findLastVisibleItemPosition());
//                    }
//                }
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                if(!mList.isEmpty()){
//                    LinearLayoutManager layoutManager = (LinearLayoutManager) mMultiTypeView.getLayoutManager();
//                    if (layoutManager != null) {
//                        mListItemsVisibilityCalculator.onScroll(
//                                mRecyclerViewItemPositionGetter,
//                                layoutManager.findFirstVisibleItemPosition(),
//                                layoutManager.findLastVisibleItemPosition() - layoutManager.findFirstVisibleItemPosition() + 1,
//                                mScrollState);
//                    }
//                }
//            }
//        });
    }

    @Override
    public void onRefresh() {
        if (mBmobViewModel != null) {
            mBmobViewModel.refresh();
        }
        mRefreshLayout.finishRefresh(2000);
    }

    @Override
    public void onLoadMore() {
        mRefreshLayout.finishLoadMore(2000);
    }

    @Override
    public void onResume() {
        super.onResume();
//        if(!mList.isEmpty()){
//            mMultiTypeView.post(() -> {
//                LinearLayoutManager layoutManager = (LinearLayoutManager) mMultiTypeView.getLayoutManager();
//                mListItemsVisibilityCalculator.onScrollStateIdle(
//                        mRecyclerViewItemPositionGetter,
//                        layoutManager.findFirstVisibleItemPosition(),
//                        layoutManager.findLastVisibleItemPosition());
//            });
//        }
        if (mMultiTypeViewController != null) {
            mMultiTypeViewController.onResume();
        }
    }

    @Override
    public void onLoadFinish(int errorCode) {
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadMore();
    }
}
