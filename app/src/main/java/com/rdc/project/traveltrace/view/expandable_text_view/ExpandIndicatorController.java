package com.rdc.project.traveltrace.view.expandable_text_view;

import android.view.View;

public interface ExpandIndicatorController {

    void changeState(boolean collapsed);

    void setView(View toggleView);

}
