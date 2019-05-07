package com.rdc.project.traveltrace.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.ai.AdvancedGeneralManager;
import com.rdc.project.traveltrace.ai.Object;
import com.rdc.project.traveltrace.base.BaseBounceFragment;
import com.rdc.project.traveltrace.utils.CollectionUtil;
import com.rdc.project.traveltrace.utils.GsonUtil;
import com.rdc.project.traveltrace.utils.HandlerUtil;
import com.rdc.project.traveltrace.utils.MeasureUtil;
import com.rdc.project.traveltrace.utils.PageSwitchUtil;
import com.rdc.project.traveltrace.utils.ProgressDialogUtil;
import com.rdc.project.traveltrace.utils.UriUtil;
import com.rdc.project.traveltrace.utils.action.Action;
import com.rdc.project.traveltrace.utils.action.ActionManager;
import com.rdc.project.traveltrace.view.nine_grid_view.ImageInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_FIELD_IMG_URL;
import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_FIELD_URL;
import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_NAME_H5;
import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_PRE;

public class AdvancedGeneralFragment extends BaseBounceFragment implements HandlerUtil.OnReceiveMessageListener {

    private static final int MSG_ADVANCED_GENERAL = 0;

    private TextView mObjectNameView;
    private ImageView mObjectImgView;
    private TextView mObjectDescriptionView;
    private PieChart mPieChart;

    private Object mObject;

    private Thread mTask;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_advanced_general;
    }

    @Override
    protected void initData(Bundle bundle) {
        HandlerUtil.getInstance().register(this);
        mTask = new Thread(new AdvancedGeneralTask(bundle.getString(ACTION_FIELD_IMG_URL)));
        mTask.start();
        ProgressDialogUtil.showProgressDialog(getActivity(), "正在识别中,请耐心等候...");
    }

    @Override
    protected void initView() {
        mObjectNameView = mRootView.findViewById(R.id.object_name);
        mObjectImgView = mRootView.findViewById(R.id.object_img);
        mObjectDescriptionView = mRootView.findViewById(R.id.object_description);
        mPieChart = mRootView.findViewById(R.id.pie_chart);

        mPieChart.getDescription().setEnabled(true);
        mPieChart.setHoleRadius(40f);//中间空白圆的半径
        mPieChart.setTransparentCircleRadius(46f);//透明圆的半径
        Legend legend = mPieChart.getLegend();//获取图标的图例
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);//设置图例的垂直对齐
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);//设置图例的水平对齐
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);//设置图例的方向
        legend.setDrawInside(false);
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        HandlerUtil.getInstance().unregister(this);
        if (mTask.isAlive()) {
            mTask.interrupt();
        }
    }

    @Override
    public void handlerMessage(Message msg) {
        if (msg.what == MSG_ADVANCED_GENERAL) {
            if (mObject != null) {
                updateViews();
            }
        }
    }

    private void updateViews() {
        List<Object.Result> resultList = mObject.getResult();
        if (CollectionUtil.isEmpty(resultList)) {
            return;
        }
        Object.Result result = resultList.get(0);
        mObjectNameView.setText(Html.fromHtml("<u>" + result.getKeyword() + "</u>"));
        Object.BaikeInfo baikeInfo = result.getBaike_info();
        final String url = baikeInfo.getBaike_url();
        mObjectNameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Action action = new Action(ACTION_PRE + ACTION_NAME_H5 + "?" + ACTION_FIELD_URL + "=" + UriUtil.encode(url));
                ActionManager.doAction(action, getActivity());
            }
        });
        final String imgUrl = baikeInfo.getImage_url();
        Glide.with(Objects.requireNonNull(getActivity())).load(imgUrl).into(mObjectImgView);
        mObjectImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ImageInfo> imageInfoList = new ArrayList<>();
                ImageInfo imageInfo = new ImageInfo();
                imageInfo.setBigImageUrl(imgUrl);
                imageInfo.setThumbnailUrl(imgUrl);
                imageInfo.imageViewWidth = mObjectImgView.getWidth();
                imageInfo.imageViewHeight = mObjectImgView.getHeight();
                int[] points = new int[2];
                mObjectImgView.getLocationInWindow(points);
                imageInfo.imageViewX = points[0];
                imageInfo.imageViewY = (int) (points[1] - MeasureUtil.getStatusHeight(getActivity()));
                imageInfoList.add(imageInfo);
                PageSwitchUtil.goPreviewPictureActivity(getActivity(), imageInfoList);
            }
        });
        String description = baikeInfo.getDescription();
        mObjectDescriptionView.setText(description);

        if (resultList.size() == 1) {
            return;
        }
        List<PieEntry> pieEntryList = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object.Result r = resultList.get(i);
            pieEntryList.add(new PieEntry((float) r.getScore(), r.getKeyword()));
        }
        PieDataSet pieDataSet = new PieDataSet(pieEntryList, "相似物品或场景");//数据集
        pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);//颜色集
        pieDataSet.setSliceSpace(2f);//片与片的间隔
        pieDataSet.setValueTextColor(Color.WHITE);//饼状图字体颜色
        pieDataSet.setValueTextSize(12f);//饼状图字体大小

        PieData pieData = new PieData(pieDataSet);
        mPieChart.setData(pieData);
        mPieChart.setVisibility(View.VISIBLE);
        ProgressDialogUtil.dismiss();
    }

    private class AdvancedGeneralTask implements Runnable {

        private String mImgUrl;

        AdvancedGeneralTask(String imgUrl) {
            mImgUrl = imgUrl;
        }

        @Override
        public void run() {
            if (!TextUtils.isEmpty(mImgUrl)) {
                String json = AdvancedGeneralManager.advancedGeneral(mImgUrl);
                mObject = GsonUtil.gsonToBean(json, Object.class);
                HandlerUtil.getInstance().getHandler().sendEmptyMessage(MSG_ADVANCED_GENERAL);
            }
        }
    }
}
