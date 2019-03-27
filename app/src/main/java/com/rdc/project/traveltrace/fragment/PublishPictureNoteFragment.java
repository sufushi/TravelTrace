package com.rdc.project.traveltrace.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.adapter.PictureNoteDynamicGridAdapter;
import com.rdc.project.traveltrace.base.BaseFragment;
import com.rdc.project.traveltrace.entity.Picture;
import com.rdc.project.traveltrace.view.dynamic_grid_view.DynamicGridView;

import java.util.ArrayList;
import java.util.List;

public class PublishPictureNoteFragment extends BaseFragment {

    private DynamicGridView mDynamicGridView;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_publish_picture_note;
    }

    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    protected void initView() {
        mDynamicGridView = mRootView.findViewById(R.id.dynamic_grid_view);
        List<Picture> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Picture picture = new Picture();
            picture.setImgUrl("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3065338749,2306246489&fm=26&gp=0.jpg");
            list.add(picture);
        }
        PictureNoteDynamicGridAdapter adapter = new PictureNoteDynamicGridAdapter(getActivity(), list, 3);
        mDynamicGridView.setAdapter(adapter);
        mDynamicGridView.setOnDragListener(new DynamicGridView.OnDragListener() {
            @Override
            public void onDragStarted(int position) {

            }

            @Override
            public void onDragPositionsChanged(int oldPosition, int newPosition) {

            }
        });
        mDynamicGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mDynamicGridView.startEditMode(position);
                return true;
            }
        });
    }

    @Override
    protected void setListener() {

    }
}
