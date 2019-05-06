package com.rdc.project.traveltrace.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.adapter.ImgListRecyclerViewAdapter;
import com.rdc.project.traveltrace.base.BaseBounceFragment;
import com.rdc.project.traveltrace.base.OnClickRecyclerViewListener;
import com.rdc.project.traveltrace.decorator.SpaceGridItemDecoration;
import com.rdc.project.traveltrace.entity.PictureNote;
import com.rdc.project.traveltrace.manager.PictureNoteListManager;
import com.rdc.project.traveltrace.utils.PageSwitchUtil;
import com.rdc.project.traveltrace.view.nine_grid_view.ImageInfo;

import java.util.ArrayList;
import java.util.List;

public class PersonAlbumFragment extends BaseBounceFragment implements OnClickRecyclerViewListener {

    private RecyclerView mPersonAlbumListView;
    private ImgListRecyclerViewAdapter mImgListRecyclerViewAdapter;

    private List<ImageInfo> mImageInfoList;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_person_album;
    }

    @Override
    protected void initData(Bundle bundle) {
        mImageInfoList = new ArrayList<>();
        mImgListRecyclerViewAdapter = new ImgListRecyclerViewAdapter(getActivity());
        List<PictureNote> list = PictureNoteListManager.getInstance().getPictureNoteList();
        List<String> imgUrls = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            List<String> urls = list.get(i).getImgUrls();
            imgUrls.addAll(urls);
            for (int j = 0; j < urls.size(); j++) {
                ImageInfo imageInfo = new ImageInfo();
                String url = urls.get(j);
                imageInfo.setBigImageUrl(url);
                imageInfo.setThumbnailUrl(url);
                mImageInfoList.add(imageInfo);
            }
        }
        mImgListRecyclerViewAdapter.updateData(imgUrls);
    }

    @Override
    protected void initView() {
        mPersonAlbumListView = mRootView.findViewById(R.id.person_album_list);
        mPersonAlbumListView.setItemAnimator(new DefaultItemAnimator());
        mPersonAlbumListView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mPersonAlbumListView.addItemDecoration(new SpaceGridItemDecoration(8));
        mPersonAlbumListView.setAdapter(mImgListRecyclerViewAdapter);
    }

    @Override
    protected void setListener() {
        mImgListRecyclerViewAdapter.setOnRecyclerViewListener(this);
    }

    @Override
    public void onItemClick(int position, View view) {
        PageSwitchUtil.goPreviewPictureActivity(getActivity(), mPersonAlbumListView, mImageInfoList, position);
    }

    @Override
    public boolean onItemLongClick(int position) {
        return false;
    }
}
