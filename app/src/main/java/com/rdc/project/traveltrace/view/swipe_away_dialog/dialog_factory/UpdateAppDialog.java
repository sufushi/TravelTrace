package com.rdc.project.traveltrace.view.swipe_away_dialog.dialog_factory;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.base.BaseSwipeAwayDialogFragment;
import com.rdc.project.traveltrace.view.swipe_away_dialog.support.v4.SwipeAwayDialogFragment;

public class UpdateAppDialog implements BaseSwipeAwayDialogFragment.DialogBuilder {

    private UpdateAppConfirmListener mListener;

    public UpdateAppDialog(UpdateAppConfirmListener listener) {
        mListener = listener;
    }

    @Override
    public Dialog create(final Context context, SwipeAwayDialogFragment fragment) {
        return new AlertDialog.Builder(context)
                .setTitle(R.string.string_update_version)
                .setMessage(R.string.string_update_version_tips)
                .setPositiveButton(R.string.string_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mListener != null) {
                            mListener.onConfirm(true);
                        }
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.string_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mListener != null) {
                            mListener.onConfirm(false);
                        }
                        dialog.dismiss();
                    }
                })
                .create();
    }

    public interface UpdateAppConfirmListener {

        void onConfirm(boolean isPositive);

    }
}
