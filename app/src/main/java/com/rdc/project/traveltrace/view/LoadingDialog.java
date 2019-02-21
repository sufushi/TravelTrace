package com.rdc.project.traveltrace.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.Window;
import android.widget.TextView;

import com.rdc.project.traveltrace.R;

public class LoadingDialog extends Dialog {

    private TextView mMessageTextView;

    public LoadingDialog(@NonNull Context context) {
        super(context);
        Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        setContentView(R.layout.layout_dialog_loading);
        init();
    }

    private void init() {
        mMessageTextView = findViewById(R.id.tv_loading);
        setCanceledOnTouchOutside(false);
    }

    public LoadingDialog setMessage(String message) {
        mMessageTextView.setText(message);
        return this;
    }

}
