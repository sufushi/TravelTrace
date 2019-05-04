package com.rdc.project.traveltrace.view.custom_view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.arch.view.IView;
import com.rdc.project.traveltrace.entity.PersonAlbumList;
import com.rdc.project.traveltrace.ui.PersonAlbumActivity;
import com.rdc.project.traveltrace.utils.CollectionUtil;
import com.rdc.project.traveltrace.utils.DensityUtil;
import com.rdc.project.traveltrace.utils.action.Action;
import com.rdc.project.traveltrace.utils.action.ActionManager;
import com.rdc.project.traveltrace.view.TipsView;
import com.rdc.project.traveltrace.view.puzzle_view.core.PuzzleLayout;
import com.rdc.project.traveltrace.view.puzzle_view.extend.RectanglePuzzleView;
import com.rdc.project.traveltrace.view.puzzle_view.impl.provider.PuzzleProvider;
import com.rdc.project.traveltrace.view.puzzle_view.impl.straight.ZeroStraightLayout;

import java.util.ArrayList;
import java.util.List;

import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_NAME_PERSON_ALBUM;
import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_PRE;

public class PersonAlbumListView extends LinearLayout implements IView, View.OnClickListener {

    private TextView mMoreView;
    private TipsView mTipsView;
    private RectanglePuzzleView mRectanglePuzzleView;
    List<Target> mTargets = new ArrayList<>();

    public PersonAlbumListView(Context context) {
        this(context, null);
    }

    public PersonAlbumListView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PersonAlbumListView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_person_album_list, this);
        setOrientation(VERTICAL);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        int padding = DensityUtil.dp2px(10, context);
        setPadding(padding, padding, padding, padding);
        initViews();
    }

    private void initViews() {
        mMoreView = findViewById(R.id.tv_more_album);
        mTipsView = findViewById(R.id.tips_view);
        mRectanglePuzzleView = findViewById(R.id.rectangle_puzzle_view);

        mMoreView.setOnClickListener(this);

        mTipsView.showEmptyView(R.drawable.bg_empty_album, getResources().getString(R.string.string_album_empty_tips), DensityUtil.dp2px(10, getContext()));

        mRectanglePuzzleView.setTouchEnable(false);
        mRectanglePuzzleView.setNeedDrawLine(false);
        mRectanglePuzzleView.setNeedDrawOuterLine(false);
        mRectanglePuzzleView.setLineSize(4);
    }

    @Override
    public void setData(Object data) {
        if (data instanceof PersonAlbumList) {
            PersonAlbumList personAlbumList = (PersonAlbumList) data;
            final List<String> list = personAlbumList.getAlbumList();
            if (CollectionUtil.isEmpty(list)) {
                return;
            }
            final PuzzleLayout puzzleLayout;
            if (list.size() > 1) {
                int pieceSize = list.size() > 9 ? 9 : list.size() % 9;
                puzzleLayout = PuzzleProvider.getPuzzleLayout(1, pieceSize, 3);
                mRectanglePuzzleView.setPuzzleLayout(puzzleLayout);
                mRectanglePuzzleView.setPiecePadding(4);
            } else if (list.size() == 1) {
                puzzleLayout = new ZeroStraightLayout(0);
                mRectanglePuzzleView.setPuzzleLayout(puzzleLayout);
            } else {
                mTipsView.setVisibility(VISIBLE);
                mRectanglePuzzleView.setVisibility(GONE);
                return;
            }
            mTipsView.setVisibility(GONE);
            mRectanglePuzzleView.setVisibility(VISIBLE);
            final List<Bitmap> pieces = new ArrayList<>();
            final int count = Math.min(list.size(), puzzleLayout.getAreaCount());
            for (int i = 0; i < count; i++) {
                final Target<Bitmap> target = new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                        pieces.add(bitmap);
                        if (pieces.size() == count) {
                            if (list.size() < puzzleLayout.getAreaCount()) {
                                for (int j = 0; j < puzzleLayout.getAreaCount(); j++) {
                                    mRectanglePuzzleView.addPiece(pieces.get(j % count));
                                }
                            } else {
                                mRectanglePuzzleView.addPieces(pieces);
                            }
                        }
                        mTargets.remove(this);
                    }
                };
                Glide.with(getContext())
                        .asBitmap()
                        .load(list.get(i))
                        .apply(new RequestOptions().centerInside())
                        .into(target);
                mTargets.add(target);
            }
        }
        this.setOnClickListener(this);
    }

    @Override
    public void onActive() {

    }

    @Override
    public void onClick(View v) {
        Action action = new Action(ACTION_PRE + ACTION_NAME_PERSON_ALBUM);
        ActionManager.doAction(action, getContext());
    }
}
