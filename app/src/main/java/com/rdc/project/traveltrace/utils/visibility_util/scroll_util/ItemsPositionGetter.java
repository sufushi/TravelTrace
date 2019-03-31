package com.rdc.project.traveltrace.utils.visibility_util.scroll_util;

import android.view.View;

public interface ItemsPositionGetter {

    View getChildAt(int position);

    int indexOfChild(View view);

    int getChildCount();

    int getLastVisiblePosition();

    int getFirstVisiblePosition();

}
