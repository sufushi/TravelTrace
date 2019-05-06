package com.rdc.project.traveltrace.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.base.BaseRecyclerViewAdapter;
import com.seu.magiccamera.adapter.FilterAdapter;
import com.seu.magiccamera.helper.FilterTypeHelper;
import com.seu.magicfilter.filter.helper.MagicFilterType;

public class PictureFilterAdapter extends BaseRecyclerViewAdapter<MagicFilterType> {

    private Context mContext;
    private int selected = 0;

    public PictureFilterAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_picture_filter, parent, false);
        return new PictureFilterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ((PictureFilterViewHolder) viewHolder).bindView(mDataList.get(position));
    }

    private class PictureFilterViewHolder extends BaseRvHolder {

        ImageView thumbImage;
        TextView filterName;
        FrameLayout thumbSelected;
        FrameLayout filterRoot;
        View thumbSelected_bg;
        
        PictureFilterViewHolder(View itemView) {
            super(itemView);
            thumbImage = itemView.findViewById(R.id.filter_thumb_image);
            filterName = itemView.findViewById(R.id.filter_thumb_name);
            filterRoot = itemView.findViewById(R.id.filter_root);
            thumbSelected = itemView.findViewById(R.id.filter_thumb_selected);
            thumbSelected_bg = itemView.findViewById(R.id.filter_thumb_selected_bg);
        }

        @Override
        protected void bindView(final MagicFilterType magicFilterType) {
            final int position = getLayoutPosition();
            thumbImage.setImageResource(FilterTypeHelper.FilterType2Thumb(magicFilterType));
            filterName.setText(FilterTypeHelper.FilterType2Name(magicFilterType));
            filterName.setBackgroundColor(mContext.getResources().getColor(
                    FilterTypeHelper.FilterType2Color(magicFilterType)));
            if(position == selected){
                thumbSelected.setVisibility(View.VISIBLE);
                thumbSelected_bg.setBackgroundColor(mContext.getResources().getColor(
                        FilterTypeHelper.FilterType2Color(magicFilterType)));
                thumbSelected_bg.setAlpha(0.7f);
            }else {
                thumbSelected.setVisibility(View.GONE);
            }

            filterRoot.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if(selected == position)
                        return;
                    int lastSelected = selected;
                    selected = position;
                    notifyItemChanged(lastSelected);
                    notifyItemChanged(position);
                    onFilterChangeListener.onFilterChanged(magicFilterType);
                }
            });
        }
    }

    public interface onFilterChangeListener{
        void onFilterChanged(MagicFilterType filterType);
    }

    private FilterAdapter.onFilterChangeListener onFilterChangeListener;

    public void setOnFilterChangeListener(FilterAdapter.onFilterChangeListener onFilterChangeListener){
        this.onFilterChangeListener = onFilterChangeListener;
    }
}
