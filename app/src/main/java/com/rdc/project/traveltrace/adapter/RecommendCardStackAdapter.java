package com.rdc.project.traveltrace.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.entity.RecommendCard;
import com.rdc.project.traveltrace.utils.CollectionUtil;
import com.rdc.project.traveltrace.view.puzzle_view.core.PuzzleLayout;
import com.rdc.project.traveltrace.view.puzzle_view.extend.RectanglePuzzleView;
import com.rdc.project.traveltrace.view.puzzle_view.impl.provider.PuzzleProvider;
import com.rdc.project.traveltrace.view.puzzle_view.impl.straight.ZeroStraightLayout;
import com.rdc.project.traveltrace.view.toast.CommonToast;

import java.util.ArrayList;
import java.util.List;

public class RecommendCardStackAdapter extends BaseAdapter {

    private Context mContext;
    private List<RecommendCard> mDataList;

    public RecommendCardStackAdapter(Context context) {
        mContext = context;
    }

    public void updateData(List<RecommendCard> list) {
        mDataList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return CollectionUtil.isEmpty(mDataList) ? 0 : mDataList.size();
    }

    @Override
    public RecommendCard getItem(int position) {
        return CollectionUtil.inRange(mDataList, position) ? null : mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final SwipeStackViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_swipe_stack_card, parent, false);
            viewHolder = new SwipeStackViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (SwipeStackViewHolder) convertView.getTag();
        }
        if (CollectionUtil.inRange(mDataList, position)) {
            RecommendCard card = mDataList.get(position);
            viewHolder.bindView(card);
        }
        return convertView;
    }

    private class SwipeStackViewHolder {

        RectanglePuzzleView puzzleView;
        TextView textView;
        private List<Target> mTargets;

        SwipeStackViewHolder(View itemView) {
            textView = itemView.findViewById(R.id.text_view_card);
            puzzleView = itemView.findViewById(R.id.rectangle_puzzle_view);
            puzzleView.setTouchEnable(false);
            puzzleView.setNeedDrawLine(true);
            puzzleView.setNeedDrawOuterLine(false);
            puzzleView.setLineSize(4);
            puzzleView.setLineColor(Color.WHITE);
            mTargets = new ArrayList<>();
        }

        public void bindView(RecommendCard card) {
            textView.setText(card.getContent());
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = card.getLinkUrl();
                    if (!TextUtils.isEmpty(url)) {

                    } else {
                        CommonToast.normal(mContext, card.getContent()).show();
                    }
                }
            });
            List<String> list = card.getImgUrls();
            if (CollectionUtil.isEmpty(list)) {
                return;
            }
            final PuzzleLayout puzzleLayout;
            if (list.size() > 1) {
                int pieceSize = list.size() >= 9 ? 9 : list.size() % 9;
                puzzleLayout = PuzzleProvider.getPuzzleLayout(1, pieceSize, 3);
                puzzleView.setPuzzleLayout(puzzleLayout);
            } else if (list.size() == 1) {
                puzzleLayout = new ZeroStraightLayout(0);
                puzzleView.setPuzzleLayout(puzzleLayout);
            } else {
                return;
            }
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
                                    puzzleView.addPiece(pieces.get(j % count));
                                }
                            } else {
                                puzzleView.addPieces(pieces);
                            }
                        }
                        mTargets.remove(this);
                    }
                };
                Glide.with(mContext)
                        .asBitmap()
                        .load(list.get(i))
                        .apply(new RequestOptions().centerInside())
                        .into(target);
                mTargets.add(target);
            }
        }

    }

}
