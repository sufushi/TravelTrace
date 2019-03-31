package com.rdc.project.traveltrace.utils.visibility_util.items;

import android.view.View;

public interface ListItem {

    int getVisibilityPercents(View view);

    void setActive(View newActiveView, int newActiveViewPosition);

    void deactivate(View currentView, int position);

}
