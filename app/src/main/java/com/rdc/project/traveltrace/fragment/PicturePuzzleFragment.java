package com.rdc.project.traveltrace.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.base.BaseFragment;
import com.rdc.project.traveltrace.view.DegreeSeekBar;
import com.rdc.project.traveltrace.view.puzzle_view.core.PuzzleLayout;
import com.rdc.project.traveltrace.view.puzzle_view.extend.SquarePuzzleView;
import com.rdc.project.traveltrace.view.puzzle_view.impl.controller.PuzzlePanelPanelController;
import com.rdc.project.traveltrace.view.puzzle_view.impl.provider.PuzzleProvider;
import com.rdc.project.traveltrace.view.puzzle_view.impl.ui.PuzzlePanelView;
import com.rdc.project.traveltrace.view.toast.CommonToast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PicturePuzzleFragment extends BaseFragment {

    public static final String PUZZLE_TYPE = "puzzle_type";
    public static final String PUZZLE_PIECE_SIZE = "puzzle_piece_size";
    public static final String PUZZLE_THEME = "puzzle_theme";
    public static final String PUZZLE_PICTURE_LIST = "puzzle_picture_list";

    private SquarePuzzleView mSquarePuzzleView;
    private DegreeSeekBar mDegreeSeekBar;
    private PuzzlePanelView mPuzzlePanelView;

    private PuzzleLayout mPuzzleLayout;
    private List<String> mPictureList;
    List<Target> mTargets = new ArrayList<>();

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_picture_puzzle;
    }

    @Override
    protected void initData(Bundle bundle) {
        if (bundle != null) {
            int type = bundle.getInt(PUZZLE_TYPE, 0);
            int pieceSize = bundle.getInt(PUZZLE_PIECE_SIZE, 0);
            int theme = bundle.getInt(PUZZLE_THEME, 0);
            mPictureList = bundle.getStringArrayList(PUZZLE_PICTURE_LIST);
            mPuzzleLayout = PuzzleProvider.getPuzzleLayout(type, pieceSize, theme);
        }
    }

    @Override
    protected void initView() {
        mSquarePuzzleView = mRootView.findViewById(R.id.square_puzzle_view);
        mDegreeSeekBar = mRootView.findViewById(R.id.degree_seek_bar);
        mPuzzlePanelView = mRootView.findViewById(R.id.puzzle_panel_view);

        mSquarePuzzleView.setPuzzleLayout(mPuzzleLayout);
        mSquarePuzzleView.setTouchEnable(true);
        mSquarePuzzleView.setNeedDrawLine(false);
        mSquarePuzzleView.setNeedDrawOuterLine(false);
        mSquarePuzzleView.setLineSize(4);
        mSquarePuzzleView.setLineColor(Color.BLACK);
        mSquarePuzzleView.setSelectedLineColor(Color.BLACK);
        mSquarePuzzleView.setHandleBarColor(Color.BLACK);
        mSquarePuzzleView.setAnimateDuration(300);
//        mSquarePuzzleView.setPiecePadding(10);
        mSquarePuzzleView.setBackgroundColor(Color.WHITE);
        mSquarePuzzleView.post(this::loadPictures);

        PuzzlePanelPanelController controller = new PuzzlePanelPanelController(getActivity(), mSquarePuzzleView);
        mPuzzlePanelView.setIPuzzlePanelController(controller);

        mDegreeSeekBar.setCurrentDegrees(0);
        mDegreeSeekBar.setDegreeRange(0, 30);
    }

    private void loadPictures() {
        if (mPictureList == null) {
            loadPicturesFromRes();
            return;
        }
        final List<Bitmap> pieces = new ArrayList<>();
        final int count = Math.min(mPictureList.size(), mPuzzleLayout.getAreaCount());
        for (int i = 0; i < count; i++) {
            final Target<Bitmap> target = new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                    pieces.add(bitmap);
                    if (pieces.size() == count) {
                        if (mPictureList.size() < mPuzzleLayout.getAreaCount()) {
                            for (int j = 0; j < mPuzzleLayout.getAreaCount(); j++) {
                                mSquarePuzzleView.addPiece(pieces.get(j % count));
                            }
                        } else {
                            mSquarePuzzleView.addPieces(pieces);
                        }
                    }
                    mTargets.remove(this);
                }
            };
            Glide.with(Objects.requireNonNull(getActivity()))
                    .asBitmap()
                    .load(mPictureList.get(i))
                    .apply(new RequestOptions().centerInside())
                    .into(target);
            mTargets.add(target);
        }
    }

    private void loadPicturesFromRes() {
        final List<Bitmap> pieces = new ArrayList<>();
        final int[] resIds = new int[] {R.drawable.test, R.drawable.test, R.drawable.test, R.drawable.test};
        final int count = Math.min(mPuzzleLayout.getAreaCount(), resIds.length);
        for (int i = 0; i < count; i++) {
            final Target<Bitmap> target = new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                    pieces.add(bitmap);
                    if (pieces.size() == count) {
                        if (resIds.length < mPuzzleLayout.getAreaCount()) {
                            for (int j = 0; j < mPuzzleLayout.getAreaCount(); j++) {
                                mSquarePuzzleView.addPiece(pieces.get(j % count));
                            }
                        } else {
                            mSquarePuzzleView.addPieces(pieces);
                        }
                    }
                    mTargets.remove(this);
                }
            };
            Glide.with(Objects.requireNonNull(getActivity()))
                    .asBitmap()
                    .load(resIds[i])
                    .into(target);
            mTargets.add(target);
        }
    }

    @Override
    protected void setListener() {
        mSquarePuzzleView.setOnPieceSelectedListener((piece, position) -> CommonToast.normal(Objects.requireNonNull(getActivity()), "position=" + position).show());
        mDegreeSeekBar.setScrollingListener(new DegreeSeekBar.ScrollingListener() {
            @Override
            public void onScrollStart() {

            }

            @Override
            public void onScroll(int currentDegrees) {
                mSquarePuzzleView.setPiecePadding(currentDegrees);
            }

            @Override
            public void onScrollEnd() {

            }
        });
    }
}
