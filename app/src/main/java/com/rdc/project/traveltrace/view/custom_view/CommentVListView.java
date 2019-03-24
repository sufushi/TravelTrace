package com.rdc.project.traveltrace.view.custom_view;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.adapter.CommentListAdapter;
import com.rdc.project.traveltrace.base.BaseRecyclerViewAdapter;
import com.rdc.project.traveltrace.base.BaseVListView;
import com.rdc.project.traveltrace.entity.Comment;
import com.rdc.project.traveltrace.utils.DensityUtil;

import java.util.List;

public class CommentVListView extends BaseVListView {

    private CommentListAdapter mCommentListAdapter;

    public CommentVListView(Context context) {
        super(context);
    }

    @Override
    protected BaseRecyclerViewAdapter createAdapter(Context context) {
        mCommentListAdapter = new CommentListAdapter(context);
        return mCommentListAdapter;
    }

    @Override
    protected View getTopView(Context context) {
        TextView textView = new TextView(context);
        textView.setTextSize(DensityUtil.sp2px(context, 5));
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setTextColor(context.getResources().getColor(R.color.black));
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setPadding(0, 0, 0, DensityUtil.dp2px(6, context));
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setText(R.string.string_total_comment);
        return textView;
    }

    public void setDataList(List<Comment> commentList) {
        if (commentList == null || commentList.size() == 0) {
            setVisibility(GONE);
        } else {
            mCommentListAdapter.appendData(commentList);
            setVisibility(VISIBLE);
        }
    }
}
