package com.rdc.project.traveltrace.arch.view_controller;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.rdc.project.traveltrace.arch.model.ResponseInfo;
import com.rdc.project.traveltrace.arch.model.impl.NoteRecordModel;
import com.rdc.project.traveltrace.entity.NoteRecord;
import com.rdc.project.traveltrace.utils.CollectionUtil;
import com.rdc.project.traveltrace.utils.visibility_util.calculator.DefaultSingleItemCalculatorCallback;
import com.rdc.project.traveltrace.utils.visibility_util.calculator.ListItemsVisibilityCalculator;
import com.rdc.project.traveltrace.utils.visibility_util.calculator.SingleListViewItemActiveCalculator;
import com.rdc.project.traveltrace.utils.visibility_util.items.ListItem;
import com.rdc.project.traveltrace.utils.visibility_util.scroll_util.RecyclerViewItemPositionGetter;
import com.shizhefei.view.multitype.ItemBinderFactory;
import com.shizhefei.view.multitype.MultiTypeAdapter;
import com.shizhefei.view.multitype.MultiTypeView;

import java.util.ArrayList;
import java.util.List;

import static com.rdc.project.traveltrace.constant.NoteType.NOTE_TYPE_PICTURE;
import static com.rdc.project.traveltrace.constant.NoteType.NOTE_TYPE_PLAIN;
import static com.rdc.project.traveltrace.constant.NoteType.NOTE_TYPE_VIDEO;

public class MultiTypeViewController extends BmobObserverViewController<NoteRecord, NoteRecordModel> {

    private MultiTypeView mMultiTypeView;
    private MultiTypeAdapter<ListItem> mMultiTypeAdapter;
    private ItemBinderFactory mItemBinderFactory;
    private List<ListItem> mList = new ArrayList<>();

    private RecyclerViewItemPositionGetter mRecyclerViewItemPositionGetter;
    private final ListItemsVisibilityCalculator mListItemsVisibilityCalculator = new SingleListViewItemActiveCalculator(new DefaultSingleItemCalculatorCallback(), mList);

    private int mScrollState;

    public MultiTypeViewController(Context context, ItemBinderFactory itemBinderFactory) {
        super(context);
        mItemBinderFactory = itemBinderFactory;
    }

    @Override
    protected View onCreateView(Context context) {
        mMultiTypeView = new MultiTypeView(context);
        mMultiTypeView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mMultiTypeView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mRecyclerViewItemPositionGetter = new RecyclerViewItemPositionGetter((LinearLayoutManager) mMultiTypeView.getLayoutManager(), mMultiTypeView);
        mMultiTypeView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                mScrollState = newState;
                if(newState == RecyclerView.SCROLL_STATE_IDLE && !mList.isEmpty()){
                    LinearLayoutManager layoutManager = (LinearLayoutManager) mMultiTypeView.getLayoutManager();
                    if (layoutManager != null) {
                        mListItemsVisibilityCalculator.onScrollStateIdle(
                                mRecyclerViewItemPositionGetter,
                                layoutManager.findFirstVisibleItemPosition(),
                                layoutManager.findLastVisibleItemPosition());
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(!mList.isEmpty()){
                    LinearLayoutManager layoutManager = (LinearLayoutManager) mMultiTypeView.getLayoutManager();
                    if (layoutManager != null) {
                        mListItemsVisibilityCalculator.onScroll(
                                mRecyclerViewItemPositionGetter,
                                layoutManager.findFirstVisibleItemPosition(),
                                layoutManager.findLastVisibleItemPosition() - layoutManager.findFirstVisibleItemPosition() + 1,
                                mScrollState);
                    }
                }
            }
        });
        return mMultiTypeView;
    }

    @Override
    protected void onBindView(ResponseInfo<NoteRecord> data, View view) {
        initAdapter(data);
        mMultiTypeView.setAdapter(mMultiTypeAdapter);
        onResume();
    }

    private void initAdapter(ResponseInfo<NoteRecord> data) {
        List<NoteRecord> resultList = data.getDataList();
        if (!CollectionUtil.isEmpty(resultList)) {
            for (int i = 0; i < resultList.size(); i++) {
                NoteRecord noteRecord = resultList.get(i);
                int noteType = noteRecord.getNoteType();
                switch (noteType) {
                    case NOTE_TYPE_VIDEO:
                        mList.add(noteRecord.getVideoNote());
                        break;
                    case NOTE_TYPE_PICTURE:
                        mList.add(noteRecord.getPictureNote());
                        break;
                    case NOTE_TYPE_PLAIN:
                    default:
                        mList.add(noteRecord.getPlainNote());
                        break;
                }
            }
        }
        mMultiTypeAdapter = new MultiTypeAdapter<>(mList, mItemBinderFactory);
    }

    public void onResume() {
        if(!mList.isEmpty()){
            mMultiTypeView.post(new Runnable() {
                @Override
                public void run() {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) mMultiTypeView.getLayoutManager();
                    mListItemsVisibilityCalculator.onScrollStateIdle(
                            mRecyclerViewItemPositionGetter,
                            layoutManager.findFirstVisibleItemPosition(),
                            layoutManager.findLastVisibleItemPosition());
                }
            });
        }
    }
}
