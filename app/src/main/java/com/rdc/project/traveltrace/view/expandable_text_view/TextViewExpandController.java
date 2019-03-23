package com.rdc.project.traveltrace.view.expandable_text_view;

import android.view.View;
import android.widget.TextView;

class TextViewExpandController implements ExpandIndicatorController {

    private final String mExpandText;
    private final String mCollapseText;

    private TextView mTextView;

    public TextViewExpandController(String expandText, String collapseText) {
        mExpandText = expandText;
        mCollapseText = collapseText;
    }

    @Override
    public void changeState(boolean collapsed) {
        mTextView.setText(collapsed ? mExpandText : mCollapseText);
    }

    @Override
    public void setView(View toggleView) {
        mTextView = (TextView) toggleView;
    }
}

