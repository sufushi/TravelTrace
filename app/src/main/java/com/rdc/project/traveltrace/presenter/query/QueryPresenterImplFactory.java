package com.rdc.project.traveltrace.presenter.query;

import com.rdc.project.traveltrace.contract.IQueryContract;
import com.rdc.project.traveltrace.entity.FollowList;
import com.rdc.project.traveltrace.entity.PictureNote;
import com.rdc.project.traveltrace.entity.RecommendCard;
import com.rdc.project.traveltrace.entity.UpdateInfo;
import com.rdc.project.traveltrace.entity.VideoNote;
import com.rdc.project.traveltrace.model.query.FollowListQueryModelImpl;
import com.rdc.project.traveltrace.model.query.PictureNoteQueryModel;
import com.rdc.project.traveltrace.model.query.RecommendCardQueryModel;
import com.rdc.project.traveltrace.model.query.UpdateInfoQueryModelImpl;
import com.rdc.project.traveltrace.model.query.VideoNoteQueryModel;
import com.rdc.project.traveltrace.presenter.QueryPresenterImpl;

public class QueryPresenterImplFactory {

    public static QueryPresenterImpl<FollowList, FollowListQueryModelImpl> createFollowListPresenterImpl(IQueryContract.View<FollowList> view) {
        return new QueryPresenterImpl<>(new FollowListQueryModelImpl(), view);
    }

    public static QueryPresenterImpl<PictureNote, PictureNoteQueryModel> createPictureNotePresenterImpl(IQueryContract.View<PictureNote> view) {
        return new QueryPresenterImpl<>(new PictureNoteQueryModel(), view);
    }

    public static QueryPresenterImpl<VideoNote, VideoNoteQueryModel> createVideoNotePresenterImpl(IQueryContract.View<VideoNote> view) {
        return new QueryPresenterImpl<>(new VideoNoteQueryModel(), view);
    }

    public static QueryPresenterImpl<RecommendCard, RecommendCardQueryModel> createRecommendCardPresenterImpl(IQueryContract.View<RecommendCard> view) {
        return new QueryPresenterImpl<>(new RecommendCardQueryModel(), view);
    }

    public static QueryPresenterImpl<UpdateInfo, UpdateInfoQueryModelImpl> createUpdateInfoPresenterImpl(IQueryContract.View<UpdateInfo> view) {
        return new QueryPresenterImpl<>(new UpdateInfoQueryModelImpl(), view);
    }
}
