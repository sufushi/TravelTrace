package com.rdc.project.traveltrace.fragment.home_page;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.meg7.widget.CircleImageView;
import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.base.BasePTRFragment;
import com.rdc.project.traveltrace.base.OnRefreshListener;
import com.rdc.project.traveltrace.entity.PersonAlbumList;
import com.rdc.project.traveltrace.entity.VideoNote;
import com.rdc.project.traveltrace.entity.VideoNoteList;
import com.rdc.project.traveltrace.ui.PersonDetailActivity;
import com.rdc.project.traveltrace.view.EmptyViewFooter;
import com.rdc.project.traveltrace.view.EmptyViewHeader;
import com.rdc.project.traveltrace.view.custom_view.PersonAlbumListView;
import com.rdc.project.traveltrace.view.custom_view.PersonVideoListView;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PersonCenterFragment extends BasePTRFragment implements View.OnClickListener {

    private PersonAlbumListView mPersonAlbumListView;
    private PersonVideoListView mPersonVideoListView;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_person_center;
    }

    @Override
    protected RefreshHeader createRefreshHeader() {
        return new EmptyViewHeader(getActivity());
    }

    @Override
    protected RefreshFooter createRefreshFooter() {
        return new EmptyViewFooter(mRefreshLayout, getActivity());
    }

    @Override
    protected void configRefreshLayout() {
        mRefreshLayout.setDragRate(0.8f);
    }

    @Override
    protected OnRefreshListener createRefreshListener() {
        return null;
    }

    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    protected void initView() {
        mPersonAlbumListView = mRootView.findViewById(R.id.person_album_view);
        mPersonVideoListView = mRootView.findViewById(R.id.person_video_list_view);

        String[] array = new String[]{"https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2059324361,2516966890&fm=27&gp=0.jpg",
                "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3782685451,3066622536&fm=27&gp=0.jpg",
                "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2791261768,1320060678&fm=27&gp=0.jpg",
                "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=280692610,4239256719&fm=27&gp=0.jpg",
                "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=260329114,3367670618&fm=27&gp=0.jpg",
                "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1178503053,3917746059&fm=26&gp=0.jpg",
                "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=753449481,625992609&fm=26&gp=0.jpg"};

        List<String> list = new ArrayList<>(Arrays.asList(array));
        PersonAlbumList personAlbumList = new PersonAlbumList(list);
        mPersonAlbumListView.setData(personAlbumList);

        List<VideoNote> videoNotes = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            VideoNote videoNote = new VideoNote();
            videoNote.setVideoCoverUrl(list.get(i));
            videoNotes.add(videoNote);
        }
        VideoNoteList videoNoteList = new VideoNoteList(videoNotes);
        mPersonVideoListView.setData(videoNoteList);
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_icon:
                if (getActivity() == null) {
                    return;
                }
                Intent intent = new Intent(getActivity(), PersonDetailActivity.class);
//                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), mUserIcon, "share_img");
                startActivity(intent);
                break;
            default:
                break;
        }
    }


}
