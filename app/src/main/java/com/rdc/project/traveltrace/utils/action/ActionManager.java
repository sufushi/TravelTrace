package com.rdc.project.traveltrace.utils.action;

import android.text.TextUtils;

import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_PRE;

public class ActionManager {

    public static String getActionName(String actionUrl) {
        if (!actionUrl.startsWith(ACTION_PRE) || TextUtils.isEmpty(actionUrl)) {
            return "";
        }
        int start = ACTION_PRE.length();
        int end = actionUrl.indexOf('?');
        if (end == -1) {
            return "";
        }
        return actionUrl.substring(start, end);
    }

}
