package com.rdc.project.traveltrace.utils.visibility_util.items;

import android.app.LauncherActivity;
import android.view.View;

import com.rdc.project.traveltrace.utils.CollectionUtil;
import com.rdc.project.traveltrace.utils.visibility_util.utils.Config;
import com.rdc.project.traveltrace.utils.visibility_util.utils.Logger;

import java.util.List;

public class ListItemData {

    private static final boolean SHOW_LOGS = Config.SHOW_LOGS;
    private static final String TAG = LauncherActivity.ListItem.class.getSimpleName();

    private Integer mIndexInAdapter;
    private View mView;

    private boolean mIsMostVisibleItemChanged;

    public int getIndex() {
        return mIndexInAdapter;
    }

    public View getView() {
        return mView;
    }

    public ListItemData fillWithData(int indexInAdapter, View view) {
        mIndexInAdapter = indexInAdapter;
        mView = view;
        return this;
    }

    public boolean isAvailable() {
        boolean isAvailable = mIndexInAdapter != null && mView != null;
        if(SHOW_LOGS) Logger.v(TAG, "isAvailable " + isAvailable);
        return isAvailable;
    }

    public int getVisibilityPercents(List<? extends ListItem> listItems) {
        int index = getIndex();
        if (!CollectionUtil.inRange(listItems, index)) {
            return 0;
        }
        int visibilityPercents = listItems.get(index).getVisibilityPercents(getView());
        if(SHOW_LOGS) Logger.v(TAG, "getVisibilityPercents, visibilityPercents " + visibilityPercents);
        return visibilityPercents;
    }

    public void setMostVisibleItemChanged(boolean isDataChanged) {
        mIsMostVisibleItemChanged = isDataChanged;
    }

    public boolean isMostVisibleItemChanged() {
        return mIsMostVisibleItemChanged;
    }

    @Override
    public String toString() {
        return "ListItemData{" +
                "mIndexInAdapter=" + mIndexInAdapter +
                ", mView=" + mView +
                ", mIsMostVisibleItemChanged=" + mIsMostVisibleItemChanged +
                '}';
    }

}
