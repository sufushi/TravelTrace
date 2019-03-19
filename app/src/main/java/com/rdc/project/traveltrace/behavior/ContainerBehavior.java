package com.rdc.project.traveltrace.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.rdc.project.traveltrace.R;

public class ContainerBehavior extends CoordinatorLayout.Behavior {

    public ContainerBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency.getId() == R.id.tool_bar;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        if (child.getId() == R.id.activity_layout_container) {
            int padding = (int) (dependency.getHeight() + dependency.getY());
            Log.i("info", "padding=" + padding);
            child.setPadding(0, padding, 0, 0);
        }
        return true;
    }
}
