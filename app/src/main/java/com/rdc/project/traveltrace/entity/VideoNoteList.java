package com.rdc.project.traveltrace.entity;

import java.util.List;

public class VideoNoteList {

    private List<VideoNote> mVideoNoteList;

    public VideoNoteList(List<VideoNote> videoNoteList) {
        mVideoNoteList = videoNoteList;
    }

    public List<VideoNote> getVideoNoteList() {
        return mVideoNoteList;
    }

    public void setVideoNoteList(List<VideoNote> videoNoteList) {
        mVideoNoteList = videoNoteList;
    }

    @Override
    public String toString() {
        return "VideoNoteList{" +
                "mVideoNoteList=" + mVideoNoteList +
                '}';
    }
}
