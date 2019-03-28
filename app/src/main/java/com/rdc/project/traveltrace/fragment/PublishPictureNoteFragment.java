package com.rdc.project.traveltrace.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.adapter.PictureNoteDynamicGridAdapter;
import com.rdc.project.traveltrace.base.BaseFragment;
import com.rdc.project.traveltrace.entity.Picture;
import com.rdc.project.traveltrace.view.custom_view.PictureNoteDynamicGridView;
import com.rdc.project.traveltrace.view.dynamic_grid_view.DynamicGridView;

import java.util.ArrayList;
import java.util.List;

public class PublishPictureNoteFragment extends BaseFragment implements PictureNoteDynamicGridView.OnDynamicGridViewItemClickListener {

    private DynamicGridView mDynamicGridView;
    private PictureNoteDynamicGridAdapter mAdapter;

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
        for (int i = 0; i < 20; i++) {
            Picture picture = new Picture();
            picture.setImgPath("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3065338749,2306246489&fm=26&gp=0.jpg");
            list.add(picture);
        }
        mAdapter = new PictureNoteDynamicGridAdapter(getActivity(), list, 3);
        mAdapter.setOnDynamicGridViewItemClickListener(this);
        mDynamicGridView.setAdapter(mAdapter);
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
                mAdapter.setIsEdit(true);
                return true;
            }
        });
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onBackPressed() {
        mDynamicGridView.stopEditMode();
        mAdapter.setIsEdit(false);
    }

    public boolean isEditMode() {
        return mDynamicGridView.isEditMode();
    }

    @Override
    public void onPhotoClick(int position) {
//        if (isEditMode()) {
//            mDynamicGridView.stopEditMode();
//            mAdapter.setIsEdit(false);
//        }
    }

    @Override
    public void onCloseClick(int position) {

    }
}
