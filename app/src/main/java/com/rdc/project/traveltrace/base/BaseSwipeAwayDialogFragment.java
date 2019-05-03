package com.rdc.project.traveltrace.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.rdc.project.traveltrace.view.swipe_away_dialog.support.v4.SwipeAwayDialogFragment;

import java.io.Serializable;

public class BaseSwipeAwayDialogFragment extends SwipeAwayDialogFragment {

    private static final String DIALOG_BUILDER = "dialog_builder";

    public static BaseSwipeAwayDialogFragment newInstance(DialogBuilder dialogBuilder, Bundle args) {
        BaseSwipeAwayDialogFragment fragment = new BaseSwipeAwayDialogFragment();
        Bundle bundle = new Bundle();
        if (args != null) {
            bundle.putAll(args);
        }
        bundle.putSerializable(DIALOG_BUILDER, dialogBuilder);
        fragment.setArguments(bundle);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        DialogBuilder dialogBuilder;
        if (getArguments() != null) {
            dialogBuilder = (DialogBuilder) getArguments().getSerializable(DIALOG_BUILDER);
        } else {
            dialogBuilder = new DefaultDialog();
        }
        assert dialogBuilder != null;
        return dialogBuilder.create(getActivity(), this);
    }

    public interface DialogBuilder extends Serializable {
        Dialog create(Context context, SwipeAwayDialogFragment fragment);
    }

    private class DefaultDialog implements DialogBuilder {

        @Override
        public Dialog create(Context context, SwipeAwayDialogFragment fragment) {
            return new android.support.v7.app.AlertDialog.Builder(context)
                    .setTitle("Title")
                    .setMessage("Message")
                    .setPositiveButton("OK", null)
                    .setNegativeButton("Cancel", null)
                    .create();
        }
    }
}
