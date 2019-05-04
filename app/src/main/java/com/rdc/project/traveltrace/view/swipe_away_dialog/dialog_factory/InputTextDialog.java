package com.rdc.project.traveltrace.view.swipe_away_dialog.dialog_factory;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.base.BaseSwipeAwayDialogFragment;
import com.rdc.project.traveltrace.view.swipe_away_dialog.support.v4.SwipeAwayDialogFragment;

public class InputTextDialog implements BaseSwipeAwayDialogFragment.DialogBuilder {

    private String mTips;
    private String mHint;
    private String mOriginalText;
    private OnInputTextListener mOnInputTextListener;

    public InputTextDialog(String tips, String hint, String originalText, OnInputTextListener onInputTextListener) {
        mTips = tips;
        mHint = hint;
        mOriginalText = originalText;
        mOnInputTextListener = onInputTextListener;
    }

    @Override
    public Dialog create(Context context, SwipeAwayDialogFragment fragment) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_input_layout, null);
        final EditText editText = view.findViewById(R.id.edit_text);
        setInputType(editText);
        editText.setHint(mHint);
        editText.setText(mOriginalText);
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(mTips)
                .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mOnInputTextListener != null) {
                            mOnInputTextListener.onInputText(editText.getText().toString());
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        return builder.create();
    }

    protected void setInputType(EditText editText) {
        editText.setInputType(InputType.TYPE_CLASS_PHONE);
    }

    public interface OnInputTextListener {

        void onInputText(String text);

    }
}
