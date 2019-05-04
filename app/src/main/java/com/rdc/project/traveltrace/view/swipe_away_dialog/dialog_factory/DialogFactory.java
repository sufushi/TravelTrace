package com.rdc.project.traveltrace.view.swipe_away_dialog.dialog_factory;

import com.rdc.project.traveltrace.base.BaseSwipeAwayDialogFragment.DialogBuilder;

public class DialogFactory {

    public static DialogBuilder createLogoutDialog() {
        return new LogoutDialog();
    }

    public static DialogBuilder createUserDetailDialog() {
        return new UserDetailDialog();
    }

    public static DialogBuilder createChangeNikNameDialog(String nikName, InputTextDialog.OnInputTextListener listener) {
        return new InputTextDialog("修改昵称", "请输入昵称", nikName, listener);
    }

    public static DialogBuilder createChangePhoneDialog(String phone, InputTextDialog.OnInputTextListener listener) {
        return new InputPhoneDialog("修改手机号码", "请输入收集号码", phone, listener);
    }

    public static DialogBuilder createChangePasswordDialog(String password, InputTextDialog.OnInputTextListener listener) {
        return new InputPasswordDialog("修改密码", "请输入密码", password, listener);
    }

    public static DialogBuilder createSelectSexDialog(boolean isMale, SelectSexDialog.OnSelectListener listener) {
        return new SelectSexDialog(isMale, listener);
    }

}
