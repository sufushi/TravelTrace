package com.rdc.project.traveltrace.view.swipe_away_dialog.dialog_factory;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.base.BaseSwipeAwayDialogFragment;
import com.rdc.project.traveltrace.view.swipe_away_dialog.support.v4.SwipeAwayDialogFragment;

public class SelectSexDialog implements BaseSwipeAwayDialogFragment.DialogBuilder {

    private boolean mOriginalSex;
    private boolean mIsMale;
    private OnSelectListener mOnSelectListener;

    public SelectSexDialog(boolean originalSex, OnSelectListener onSelectListener) {
        mOriginalSex = originalSex;
        mOnSelectListener = onSelectListener;
    }

    @Override
    public Dialog create(Context context, SwipeAwayDialogFragment fragment) {
        mIsMale = mOriginalSex;
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle("选择性别")
                .setIcon(R.drawable.ic_sex)
                .setSingleChoiceItems(new String[]{"男", "女"}, mOriginalSex ? 0 : 1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mIsMale = which == 0;
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mOnSelectListener != null) {
                            mOnSelectListener.onSelect(mIsMale);
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

    public interface OnSelectListener {

        void onSelect(boolean isMale);

    }
}
