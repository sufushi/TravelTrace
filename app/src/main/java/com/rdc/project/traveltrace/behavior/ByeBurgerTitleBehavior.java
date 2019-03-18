package com.rdc.project.traveltrace.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

public class ByeBurgerTitleBehavior extends ByeBurgerBehavior {

    public ByeBurgerTitleBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        if (canInit) {
            mAnimateHelper = TranslateAnimateHelper.get(child);
            canInit = false;
        }
        return super.layoutDependsOn(parent, child, dependency);
    }


    @Override
    protected void onNestPreScrollInit(View child) {

        if (isFirstMove) {
            isFirstMove = false;
            mAnimateHelper.setStartY(child.getY());
            mAnimateHelper.setMode(TranslateAnimateHelper.MODE_TITLE);
        }
    }

}
