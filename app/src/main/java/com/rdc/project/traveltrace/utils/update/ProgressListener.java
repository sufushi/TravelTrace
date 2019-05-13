package com.rdc.project.traveltrace.utils.update;

public interface ProgressListener {

    void onProgressChange(long totalBytes, long curBytes, int progress);

}
