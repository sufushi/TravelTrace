package com.rdc.project.traveltrace.utils.action;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import com.rdc.project.traveltrace.constant.Constant;
import com.rdc.project.traveltrace.short_video.activity.VideoRecordActivity;
import com.rdc.project.traveltrace.short_video.utils.PermissionChecker;
import com.rdc.project.traveltrace.short_video.utils.ToastUtils;
import com.rdc.project.traveltrace.ui.AdvancedGeneralActivity;
import com.rdc.project.traveltrace.ui.CaptureActivity;
import com.rdc.project.traveltrace.ui.GesturePinActivity;
import com.rdc.project.traveltrace.ui.H5Activity;
import com.rdc.project.traveltrace.ui.HomeActivity;
import com.rdc.project.traveltrace.ui.PersonAlbumActivity;
import com.rdc.project.traveltrace.ui.PersonDetailActivity;
import com.rdc.project.traveltrace.ui.PictureProcessActivity;
import com.rdc.project.traveltrace.ui.PicturePuzzleActivity;
import com.rdc.project.traveltrace.ui.PublishPictureNoteActivity;
import com.rdc.project.traveltrace.ui.VideoPreviewActivity;
import com.rdc.project.traveltrace.utils.UriUtil;
import com.seu.magiccamera.activity.CameraActivity;

import java.util.HashMap;
import java.util.Map;

import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_NAME_ADVANCED_GENERAL;
import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_NAME_CAMERA;
import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_NAME_GESTURE_PIN;
import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_NAME_H5;
import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_NAME_HOME;
import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_NAME_PERSON_ALBUM;
import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_NAME_PERSON_DETAIL;
import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_NAME_PICTURE_PROCESS;
import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_NAME_PICTURE_PUZZLE;
import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_NAME_PUBLISH_PICTURE_NOTE;
import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_NAME_CAPTURE;
import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_NAME_VIDEO_PREVIEW;
import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_NAME_VIDEO_RECORD;
import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_PRE;

public class ActionManager {

    private static String sPreActionUrl = Constant.EMPTY;

    public static String getActionName(String actionUrl) {
        if (TextUtils.isEmpty(actionUrl)) {
            return null;
        }
        int start = actionUrl.indexOf(ACTION_PRE);
        if (start == -1) {
            return null;
        }
        int end = actionUrl.indexOf('?');
        if (end == -1) {
            return actionUrl.substring(start + ACTION_PRE.length());
        } else {
            if (end < start) {
                return null;
            }
            return actionUrl.substring(start + ACTION_PRE.length(), end);
        }
    }

    public static HashMap<String, String> getActionParams(String actionUrl) {
        if (TextUtils.isEmpty(actionUrl)) {
            return null;
        }
        int start = actionUrl.indexOf("?");
        if (start == -1) {
            return null;
        }
        return getKVFromStr(actionUrl.substring(start + 1));
    }

    public static HashMap<String, String> getKVFromStr(String paramsString) {
        if (TextUtils.isEmpty(paramsString)) {
            return null;
        }
        HashMap<String, String> paramsMap = new HashMap<>();
        String[] keyValueString = paramsString.split("&");
        for (String string : keyValueString) {
            String[] keyValue = string.split("=");
            if (keyValue.length == 2) {
                String key = keyValue[0];
                String value = UriUtil.decode(keyValue[1]);
                if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
                    paramsMap.put(key, value);
                }
            }
        }
        return paramsMap;
    }

    public static void doAction(Action action, Context context) {
        if (action == null || TextUtils.isEmpty(action.getActionUrl()) || sPreActionUrl.equals(action.getActionUrl())) {
            return;
        }
        sPreActionUrl = action.getActionUrl();
        executeAction(action.getActionUrl(), context);
        sPreActionUrl = Constant.EMPTY;
    }

    private static void executeAction(String actionUrl, Context context) {
        if (TextUtils.isEmpty(actionUrl) || context == null) {
            return;
        }
        String actionName = getActionName(actionUrl);
        if (actionName == null) {
            return;
        }
        Intent intent = getIntent(actionName, context);
        if (intent == null) {
            return;
        }
        HashMap<String, String> params = getActionParams(actionUrl);
        Bundle bundle = new Bundle();
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                bundle.putString(key, value);
            }
        }
        intent.putExtra("bundle", bundle);
        context.startActivity(intent);
    }

    private static Intent getIntent(String actionName, Context context) {
        Intent intent = new Intent();
        switch (actionName) {
            case ACTION_NAME_PUBLISH_PICTURE_NOTE:
                intent.setClass(context, PublishPictureNoteActivity.class);
                break;
            case ACTION_NAME_PICTURE_PUZZLE:
                intent.setClass(context, PicturePuzzleActivity.class);
                break;
            case ACTION_NAME_PICTURE_PROCESS:
                intent.setClass(context, PictureProcessActivity.class);
                break;
            case ACTION_NAME_HOME:
                intent.setClass(context, HomeActivity.class);
                break;
            case ACTION_NAME_PERSON_DETAIL:
                intent.setClass(context, PersonDetailActivity.class);
                break;
            case ACTION_NAME_GESTURE_PIN:
                intent.setClass(context, GesturePinActivity.class);
                break;
            case ACTION_NAME_PERSON_ALBUM:
                intent.setClass(context, PersonAlbumActivity.class);
                break;
            case ACTION_NAME_CAMERA:
                intent.setClass(context, CameraActivity.class);
                break;
            case ACTION_NAME_CAPTURE:
                intent.setClass(context, CaptureActivity.class);
                break;
            case ACTION_NAME_VIDEO_RECORD:
                if (isPermissionOK(context)) {
                    intent.setClass(context, VideoRecordActivity.class);
                } else {
                    return null;
                }
                break;
            case ACTION_NAME_VIDEO_PREVIEW:
                intent.setClass(context, VideoPreviewActivity.class);
                break;
            case ACTION_NAME_H5:
                intent.setClass(context, H5Activity.class);
                break;
            case ACTION_NAME_ADVANCED_GENERAL:
                intent.setClass(context, AdvancedGeneralActivity.class);
                break;
            default:
                intent.setClass(context, HomeActivity.class);
                break;
        }
        return intent;
    }

    private static boolean isPermissionOK(Context context) {
        PermissionChecker checker = new PermissionChecker((Activity) context);
        boolean isPermissionOK = Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checker.checkPermission();
        if (!isPermissionOK) {
            ToastUtils.s(context, "Some permissions is not approved !!!");
        }
        return isPermissionOK;
    }
}
