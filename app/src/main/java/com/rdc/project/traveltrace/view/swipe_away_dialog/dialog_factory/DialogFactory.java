package com.rdc.project.traveltrace.view.swipe_away_dialog.dialog_factory;

import com.rdc.project.traveltrace.base.BaseSwipeAwayDialogFragment.DialogBuilder;

public class DialogFactory {

    public static DialogBuilder createLogoutDialog() {
        return new LogoutDialog();
    }

    public static DialogBuilder createUserDetailDialog() {
        return new UserDetailDialog();
    }

}
