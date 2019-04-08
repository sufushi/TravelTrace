package com.rdc.project.traveltrace.utils.player;

import android.content.Context;

import com.kk.taurus.playerbase.receiver.GroupValue;
import com.kk.taurus.playerbase.receiver.ReceiverGroup;
import com.rdc.project.traveltrace.view.player_cover.CompleteCover;
import com.rdc.project.traveltrace.view.player_cover.LoadingCover;

import static com.rdc.project.traveltrace.utils.player.InterData.ReceiverKey.KEY_COMPLETE_COVER;
import static com.rdc.project.traveltrace.utils.player.InterData.ReceiverKey.KEY_LOADING_COVER;

public class ReceiverGroupManager {

    private static ReceiverGroupManager sReceiverGroupManager;

    private ReceiverGroupManager() {
    }

    public static ReceiverGroupManager get() {
        if (null == sReceiverGroupManager) {
            synchronized (ReceiverGroupManager.class) {
                if (null == sReceiverGroupManager) {
                    sReceiverGroupManager = new ReceiverGroupManager();
                }
            }
        }
        return sReceiverGroupManager;
    }

    public ReceiverGroup getLittleReceiverGroup(Context context) {
        return getLiteReceiverGroup(context, null);
    }

    public ReceiverGroup getLittleReceiverGroup(Context context, GroupValue groupValue) {
        ReceiverGroup receiverGroup = new ReceiverGroup(groupValue);
        receiverGroup.addReceiver(KEY_LOADING_COVER, new LoadingCover(context));
        receiverGroup.addReceiver(KEY_COMPLETE_COVER, new CompleteCover(context));
//        receiverGroup.addReceiver(KEY_ERROR_COVER, new ErrorCover(context));
        return receiverGroup;
    }

    public ReceiverGroup getLiteReceiverGroup(Context context) {
        return getLiteReceiverGroup(context, null);
    }

    public ReceiverGroup getLiteReceiverGroup(Context context, GroupValue groupValue) {
        ReceiverGroup receiverGroup = new ReceiverGroup(groupValue);
        receiverGroup.addReceiver(KEY_LOADING_COVER, new LoadingCover(context));
//        receiverGroup.addReceiver(KEY_CONTROLLER_COVER, new ControllerCover(context));
        receiverGroup.addReceiver(KEY_COMPLETE_COVER, new CompleteCover(context));
//        receiverGroup.addReceiver(KEY_ERROR_COVER, new ErrorCover(context));
        return receiverGroup;
    }

    public ReceiverGroup getReceiverGroup(Context context) {
        return getReceiverGroup(context, null);
    }

    public ReceiverGroup getReceiverGroup(Context context, GroupValue groupValue) {
        ReceiverGroup receiverGroup = new ReceiverGroup(groupValue);
        receiverGroup.addReceiver(KEY_LOADING_COVER, new LoadingCover(context));
//        receiverGroup.addReceiver(KEY_CONTROLLER_COVER, new ControllerCover(context));
//        receiverGroup.addReceiver(KEY_GESTURE_COVER, new GestureCover(context));
        receiverGroup.addReceiver(KEY_COMPLETE_COVER, new CompleteCover(context));
//        receiverGroup.addReceiver(KEY_ERROR_COVER, new ErrorCover(context));
        return receiverGroup;
    }


}
