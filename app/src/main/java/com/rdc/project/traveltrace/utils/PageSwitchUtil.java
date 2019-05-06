package com.rdc.project.traveltrace.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import com.rdc.project.traveltrace.view.nine_grid_view.ImageInfo;
import com.rdc.project.traveltrace.view.nine_grid_view.ImagePreviewActivity;

import java.io.Serializable;
import java.util.List;

import static com.rdc.project.traveltrace.view.nine_grid_view.ImagePreviewActivity.CURRENT_ITEM;
import static com.rdc.project.traveltrace.view.nine_grid_view.ImagePreviewActivity.IMAGE_INFO;

public class PageSwitchUtil {

    public static void goPreviewPictureActivity(Context context, ViewGroup attachView, List<ImageInfo> list, int position) {
        for(int i = 0; i < list.size(); ++i) {
            ImageInfo info = list.get(i);
            View imageView = attachView.getChildAt(i);
            if (imageView != null) {
                info.imageViewWidth = imageView.getWidth();
                info.imageViewHeight = imageView.getHeight();
                int[] points = new int[2];
                imageView.getLocationInWindow(points);
                info.imageViewX = points[0];
                info.imageViewY = (int) (points[1] - MeasureUtil.getStatusHeight(context));
            }
        }
        Intent intent = new Intent(context, ImagePreviewActivity.class);
        intent.putExtra(IMAGE_INFO, (Serializable) list);
        intent.putExtra(CURRENT_ITEM, position);
        context.startActivity(intent);
        ((Activity)context).overridePendingTransition(0, 0);
    }

    public static void goPreviewPictureActivity(Context context, List<ImageInfo> list) {
        Intent intent = new Intent(context, ImagePreviewActivity.class);
        intent.putExtra(IMAGE_INFO, (Serializable) list);
        context.startActivity(intent);
        ((Activity)context).overridePendingTransition(0, 0);
    }
}
