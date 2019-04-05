package com.rdc.project.traveltrace.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.ArrayList;
import java.util.List;

public class HandlerUtil {

    private static Handler sHandler = new InnerHandler(Looper.getMainLooper());
    private static List<OnReceiveMessageListener> mReceiveMsgListenerList;

    private HandlerUtil() {
        init();
    }

    public static HandlerUtil getInstance() {
        return HandlerUtilHolder.INSTANCE;
    }

    private static final class HandlerUtilHolder {
        private static final HandlerUtil INSTANCE = new HandlerUtil();
    }

    private void init() {
        if (mReceiveMsgListenerList == null) {
            mReceiveMsgListenerList = new ArrayList<>();
        }
    }

    public void register(OnReceiveMessageListener listener) {
        init();
        mReceiveMsgListenerList.add(listener);
    }

    public void unregister(int index) {
        if (mReceiveMsgListenerList != null
                && index >= 0 && index < mReceiveMsgListenerList.size()) {
            mReceiveMsgListenerList.remove(index);
        }
    }

    public void unregister(OnReceiveMessageListener listener) {
        if (mReceiveMsgListenerList != null && listener != null) {
            mReceiveMsgListenerList.remove(listener);
        }
    }

    public Handler getHandler() {
        return sHandler;
    }

    public List<OnReceiveMessageListener> getReceiveMsgListenerList() {
        return mReceiveMsgListenerList;
    }

    public void clear() {
        if (mReceiveMsgListenerList != null && mReceiveMsgListenerList.size() > 0) {
            mReceiveMsgListenerList.clear();
        }
    }

    public interface OnReceiveMessageListener {
        void handlerMessage(Message msg);
    }

    private static class InnerHandler extends Handler {

        InnerHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mReceiveMsgListenerList != null && mReceiveMsgListenerList.size() > 0) {
                for (OnReceiveMessageListener listener : mReceiveMsgListenerList) {
                    listener.handlerMessage(msg);
                }
            }
        }
    }
}
