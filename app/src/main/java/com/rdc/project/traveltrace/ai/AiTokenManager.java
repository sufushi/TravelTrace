package com.rdc.project.traveltrace.ai;

import android.text.TextUtils;

import com.rdc.project.traveltrace.app.App;
import com.rdc.project.traveltrace.utils.SharePreferenceUtil;

public class AiTokenManager {

    private static final String EXPIRES_IN_KEY = "expires_in";
    private static final int MIN_INTERVAL = 5000;

    private String mToken;

    private AiTokenManager() {
        long next_time = (long) SharePreferenceUtil.get(App.getAppContext(), EXPIRES_IN_KEY, 0L);
        long current_time = System.currentTimeMillis();
        if (current_time - next_time >= MIN_INTERVAL) {
            updateToken();
        }
    }

    private void updateToken() {
        long next_time = (long) SharePreferenceUtil.get(App.getAppContext(), EXPIRES_IN_KEY, 0L);
        AiToken token = AiTokenService.getAuth();
        if (token != null) {
            mToken = token.getAccess_token();
            int expires_in = token.getExpires_in();
            long new_time = next_time + expires_in * 1000;
            SharePreferenceUtil.put(App.getAppContext(), EXPIRES_IN_KEY, new_time);
        }
    }

    private static class AiTokenHolder {
        private static final AiTokenManager INSTANCE = new AiTokenManager();
    }

    public static AiTokenManager getInstance() {
        return AiTokenHolder.INSTANCE;
    }

    public String getToken() {
        if (TextUtils.isEmpty(mToken)) {
            updateToken();
        }
        return mToken;
    }

}
