package com.rdc.project.traveltrace.ai;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class AiTokenService {

    private static final String TAG = "AiTokenService";
    private static final String sAuthHost = "https://aip.baidubce.com/oauth/2.0/token?";

    public static AiToken getAuth() {
        // 获取token地址
        String getAccessTokenUrl = sAuthHost
                // 1. grant_type为固定参数
                + "grant_type=client_credentials"
                // 2. 官网获取的 API Key
                + "&client_id=" + AiConfig.API_KEY
                // 3. 官网获取的 Secret Key
                + "&client_secret=" + AiConfig.SECRET_KEY;
        try {
            URL realUrl = new URL(getAccessTokenUrl);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.err.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                stringBuilder.append(line);
            }
            System.err.println("stringBuilder:" + stringBuilder);
            JSONObject jsonObject = new JSONObject(stringBuilder.toString());
            AiToken token = new AiToken();
            token.setAccess_token(jsonObject.getString("access_token"));
            token.setExpires_in(jsonObject.getInt("expires_in"));
            return token;
        } catch (Exception e) {
            Log.e(TAG,"获取token失败！");
            e.printStackTrace();
        }
        return null;
    }



}
