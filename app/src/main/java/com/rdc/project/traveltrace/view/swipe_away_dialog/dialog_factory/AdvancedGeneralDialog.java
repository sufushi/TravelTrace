package com.rdc.project.traveltrace.view.swipe_away_dialog.dialog_factory;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.base.BaseSwipeAwayDialogFragment;
import com.rdc.project.traveltrace.utils.UriUtil;
import com.rdc.project.traveltrace.utils.action.Action;
import com.rdc.project.traveltrace.utils.action.ActionManager;
import com.rdc.project.traveltrace.view.swipe_away_dialog.support.v4.SwipeAwayDialogFragment;

import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_FIELD_IMG_URL;
import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_NAME_ADVANCED_GENERAL;
import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_PRE;

public class AdvancedGeneralDialog implements BaseSwipeAwayDialogFragment.DialogBuilder {

    private String mUrl;

    public AdvancedGeneralDialog(String url) {
        mUrl = url;
    }

    @Override
    public Dialog create(final Context context, SwipeAwayDialogFragment fragment) {
        return new AlertDialog.Builder(context)
                .setTitle(R.string.string_pattern_recognition)
                .setMessage(R.string.string_pattern_recognition_tips)
                .setPositiveButton(R.string.string_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Action action = new Action(ACTION_PRE + ACTION_NAME_ADVANCED_GENERAL + "?" + ACTION_FIELD_IMG_URL + "=" + UriUtil.encode(mUrl));
                        ActionManager.doAction(action, context);
                    }
                })
                .setNegativeButton(R.string.string_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
    }
}
