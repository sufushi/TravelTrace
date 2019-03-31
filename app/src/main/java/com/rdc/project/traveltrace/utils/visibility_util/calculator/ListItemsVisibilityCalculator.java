package com.rdc.project.traveltrace.utils.visibility_util.calculator;

import com.rdc.project.traveltrace.utils.visibility_util.scroll_util.ItemsPositionGetter;

public interface ListItemsVisibilityCalculator {

    void onScrollStateIdle(ItemsPositionGetter itemsPositionGetter, int firstVisiblePosition, int lastVisiblePosition);
    void onScroll(ItemsPositionGetter itemsPositionGetter, int firstVisibleItem, int visibleItemCount, int scrollState);

}
