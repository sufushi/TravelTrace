package com.rdc.project.traveltrace.entity;

import android.os.Parcel;
import android.support.v4.app.Fragment;
import android.view.View;

import com.shizhefei.view.multitype.provider.FragmentData;
import com.rdc.project.traveltrace.utils.visibility_util.items.ListItem;

public class FragmentDataListItem extends FragmentData implements ListItem {

    public FragmentDataListItem(Class<? extends Fragment> fragmentClass, String fragmentTag) {
        super(fragmentClass, fragmentTag);
    }

    protected FragmentDataListItem(Parcel in) {
        super(in);
    }

    @Override
    public int getVisibilityPercents(View view) {
        return 0;
    }

    @Override
    public void setActive(View newActiveView, int newActiveViewPosition) {

    }

    @Override
    public void deactivate(View currentView, int position) {

    }
}
