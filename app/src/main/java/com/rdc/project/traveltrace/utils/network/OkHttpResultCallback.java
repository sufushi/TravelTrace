package com.rdc.project.traveltrace.utils.network;


import okhttp3.Call;

public interface  OkHttpResultCallback {

    void onError(Call call, Exception e);

    void onResponse(byte[] bytes);
}
