package com.rdc.project.traveltrace.ai;

import android.util.Log;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class AdvancedGeneralManager {

    private static final String TAG = "AdvancedGeneralManager";
    private static final String BASE_URL = "https://aip.baidubce.com/rest/2.0/image-classify/v2/advanced_general";

    public static String advancedGeneral(String imgUrl) {
        // 请求url
        try {
            String imgStr = Base64Util.url2Base64(imgUrl);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            Map<String, String> params = new HashMap<>();
            params.put("image", imgStr);
            params.put("baike_num", "1");

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = AiTokenManager.getInstance().getToken();

            String result = HttpUtil.post(BASE_URL, accessToken, params);
            Log.i(TAG, "result=" + result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
