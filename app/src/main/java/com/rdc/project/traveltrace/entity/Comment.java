package com.rdc.project.traveltrace.entity;

public class Comment {

    private User mSendUser;
    private User mReceiveUser;
    private String mMessage;

    public Comment() {

    }

    public User getSendUser() {
        return mSendUser;
    }

    public void setSendUser(User sendUser) {
        mSendUser = sendUser;
    }

    public User getReceiveUser() {
        return mReceiveUser;
    }

    public void setReceiveUser(User receiveUser) {
        mReceiveUser = receiveUser;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "mSendUser=" + mSendUser +
                ", mReceiveUser=" + mReceiveUser +
                ", mMessage='" + mMessage + '\'' +
                '}';
    }
}
