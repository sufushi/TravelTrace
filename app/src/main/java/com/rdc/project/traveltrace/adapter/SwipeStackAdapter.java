package com.rdc.project.traveltrace.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.utils.CollectionUtil;

import java.util.List;

public class SwipeStackAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mData;

    public SwipeStackAdapter(Context context, List<String> data) {
        mContext = context;
        this.mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public String getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        SwipeStackViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new SwipeStackViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_swipe_stack_card, parent, false);
            viewHolder.textView = convertView.findViewById(R.id.text_view_card);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (SwipeStackViewHolder) convertView.getTag();
        }
        if (CollectionUtil.inRange(mData, position)) {
            viewHolder.textView.setText(mData.get(position));
        }
        return convertView;
    }

    private class SwipeStackViewHolder {

        TextView textView;

    }
}
