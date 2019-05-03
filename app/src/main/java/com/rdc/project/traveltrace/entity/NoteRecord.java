package com.rdc.project.traveltrace.entity;

import cn.bmob.v3.BmobObject;

import static com.rdc.project.traveltrace.constant.NoteType.NOTE_TYPE_PICTURE;
import static com.rdc.project.traveltrace.constant.NoteType.NOTE_TYPE_PLAIN;
import static com.rdc.project.traveltrace.constant.NoteType.NOTE_TYPE_VIDEO;

public class NoteRecord extends BmobObject {

    private int mNoteType;
    private PlainNote mPlainNote;
    private PictureNote mPictureNote;
    private VideoNote mVideoNote;

    public NoteRecord() {

    }

    public int getNoteType() {
        return mNoteType;
    }

    public void setNoteType(int noteType) {
        mNoteType = noteType;
    }

    public PlainNote getPlainNote() {
        return mPlainNote;
    }

    public void setPlainNote(PlainNote plainNote) {
        mPlainNote = plainNote;
        setNoteType(NOTE_TYPE_PLAIN);
    }

    public PictureNote getPictureNote() {
        return mPictureNote;
    }

    public void setPictureNote(PictureNote pictureNote) {
        mPictureNote = pictureNote;
        setNoteType(NOTE_TYPE_PICTURE);
    }

    public VideoNote getVideoNote() {
        return mVideoNote;
    }

    public void setVideoNote(VideoNote videoNote) {
        mVideoNote = videoNote;
        setNoteType(NOTE_TYPE_VIDEO);
    }
}
