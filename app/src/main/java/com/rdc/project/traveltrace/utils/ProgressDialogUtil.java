package com.rdc.project.traveltrace.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.rdc.project.traveltrace.view.LoadingDialog;

public class ProgressDialogUtil {

    private static ProgressDialog sProgressDialog;
    private static LoadingDialog sLoadingDialog;
    private static boolean isShow = false;

    public static final int GRAVITY_TOP = 0;
    public static final int GRAVITY_CENTER = 1;
    public static final int GRAVITY_BOTTOM = 2;

    public static void showProgressDialog(Context context, CharSequence msg) {
        sProgressDialog = new ProgressDialog(context);
        sProgressDialog.setMessage(msg);
        sProgressDialog.setCancelable(true);
        sProgressDialog.show();
        isShow = true;
    }

    public static void showLoadingDislog(Context context, String msg) {
        sLoadingDialog = new LoadingDialog(context);
        sLoadingDialog.setMessage(msg);
        sLoadingDialog.show();
        isShow = true;
    }

    public static void setMsg(CharSequence msg) {
        if (sProgressDialog != null) {
            sProgressDialog.setMessage(msg);
        }
    }

    public static void setGravity(int gravity) {
        Window window = sProgressDialog.getWindow();
        if (window != null) {
            switch (gravity) {
                case GRAVITY_TOP:
                    window.setGravity(Gravity.TOP);
                    break;
                case GRAVITY_CENTER:
                    window.setGravity(Gravity.CENTER);
                    break;
                case GRAVITY_BOTTOM:
                    window.setGravity(Gravity.BOTTOM);
                    WindowManager.LayoutParams layoutParams = window.getAttributes();
                    layoutParams.y = 160;
                    window.setAttributes(layoutParams);
                    break;
                default:
                    window.setGravity(Gravity.CENTER);
                    break;
            }
        }

    }

    public static void dismiss() {
        if (sProgressDialog != null && sProgressDialog.isShowing()) {
            sProgressDialog.dismiss();
            isShow = false;
        }
        if (sLoadingDialog != null && sLoadingDialog.isShowing()) {
            sLoadingDialog.dismiss();
            isShow = false;
        }
    }

    public static boolean isShow() {
        return isShow;
    }
}