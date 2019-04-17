package com.rdc.project.traveltrace.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rdc.project.traveltrace.R;

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
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_swipe_stack_card, parent, false);
        TextView textViewCard = convertView.findViewById(R.id.text_view_card);
        textViewCard.setText(mData.get(position));
        return convertView;
    }
}
