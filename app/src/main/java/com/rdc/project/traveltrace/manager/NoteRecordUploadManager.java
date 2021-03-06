package com.rdc.project.traveltrace.manager;

import android.util.Log;

import com.rdc.project.traveltrace.contract.IUploadContract;
import com.rdc.project.traveltrace.entity.NoteRecord;
import com.rdc.project.traveltrace.presenter.UploadPresenterImpl;

public class NoteRecordUploadManager implements IUploadContract.View {

    private static final String TAG = "NoteRecordUploadManager";

    private UploadPresenterImpl<NoteRecord> mNoteRecordUploadPresenter;

    private static class NoteRecordUploadHolder {
        private static final NoteRecordUploadManager INSTANCE = new NoteRecordUploadManager();
    }

    private NoteRecordUploadManager() {
        mNoteRecordUploadPresenter = new UploadPresenterImpl<>(this);
    }

    public static NoteRecordUploadManager getInstance() {
        return NoteRecordUploadHolder.INSTANCE;
    }

    public void uploadNote(NoteRecord noteRecord) {
        mNoteRecordUploadPresenter.upload(noteRecord);
    }

    @Override
    public void onUploadSuccess(String response) {
        Log.i(TAG, "NoteRecord onUploadSuccess:" + response);
    }

    @Override
    public void onUploadFailed(String response) {
        Log.i(TAG, "NoteRecord onUploadFailed:" + response);
    }

}
