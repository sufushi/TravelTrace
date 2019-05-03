package com.rdc.project.traveltrace.view.swipe_away_dialog.dialog_factory;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.base.BaseSwipeAwayDialogFragment;
import com.rdc.project.traveltrace.ui.GuidePageActivity;
import com.rdc.project.traveltrace.view.swipe_away_dialog.support.v4.SwipeAwayDialogFragment;

import cn.bmob.v3.BmobUser;

public class LogoutDialog implements BaseSwipeAwayDialogFragment.DialogBuilder {

    @Override
    public Dialog create(final Context context, SwipeAwayDialogFragment fragment) {
        return new AlertDialog.Builder(context)
                .setTitle(R.string.string_logout)
                .setMessage(R.string.string_logout_tips)
                .setPositiveButton(R.string.string_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (BmobUser.isLogin()) {
                            Intent intent = new Intent(context, GuidePageActivity.class);
                            BmobUser.logOut();
                            context.startActivity(intent);
                            ((Activity) context).finish();
                        }
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
