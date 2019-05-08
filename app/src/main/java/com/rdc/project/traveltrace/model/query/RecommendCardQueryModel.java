package com.rdc.project.traveltrace.model.query;

import com.rdc.project.traveltrace.contract.IQueryContract;
import com.rdc.project.traveltrace.entity.RecommendCard;
import com.rdc.project.traveltrace.model.QueryModelImpl;
import com.rdc.project.traveltrace.model.pager.Pager;
import com.rdc.project.traveltrace.model.pager.PagerUtil;

import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class RecommendCardQueryModel extends QueryModelImpl<RecommendCard> {

    @Override
    public void query(Map<String, Object> params, final IQueryContract.Presenter<RecommendCard> presenter) {
        BmobQuery<RecommendCard> query = new BmobQuery<>();
        addParams(query, params);
        query.setLimit(Pager.PAGE_SIZE);
        Pager pager = PagerUtil.getInstance().getPager(RecommendCardQueryModel.class);
        if (pager != null) {
            query.setSkip(pager.getPageContext());
        }
        query.findObjects(new FindListener<RecommendCard>() {
            @Override
            public void done(List<RecommendCard> list, BmobException e) {
                onResponse(list, e, presenter);
            }
        });
    }
}
