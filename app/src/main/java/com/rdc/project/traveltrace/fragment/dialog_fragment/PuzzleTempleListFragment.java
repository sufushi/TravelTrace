package com.rdc.project.traveltrace.fragment.dialog_fragment;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.entity.PuzzleTemple;
import com.rdc.project.traveltrace.entity.PuzzleTempleList;
import com.rdc.project.traveltrace.utils.DensityUtil;
import com.rdc.project.traveltrace.view.custom_view.PuzzleTempleHListView;
import com.rdc.project.traveltrace.view.puzzle_view.core.PuzzleLayout;
import com.rdc.project.traveltrace.view.puzzle_view.impl.provider.PuzzleProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PuzzleTempleListFragment extends BottomDialogFragment {

    private PuzzleTempleList mPuzzleTempleList;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_puzzle_temple_list;
    }

    @Override
    protected void onLayout() {
        mWindow.setLayout(mWidth, mHeight / 5 + DensityUtil.dp2px(10, Objects.requireNonNull(getContext())) * 2);
    }

    @Override
    protected void initData() {
        List<PuzzleLayout> layoutList = PuzzleProvider.getAllPuzzleLayouts();
        List<PuzzleTemple> list = new ArrayList<>();
        for (int i = 0; i < layoutList.size(); i++) {
            PuzzleTemple puzzleTemple = new PuzzleTemple(layoutList.get(i), null);
            list.add(puzzleTemple);
        }
        mPuzzleTempleList = new PuzzleTempleList(list);
    }

    @Override
    protected void initView() {
        PuzzleTempleHListView puzzleTempleHListView = mRootView.findViewById(R.id.puzzle_temple_list_view);
        puzzleTempleHListView.setData(mPuzzleTempleList);
    }
}
