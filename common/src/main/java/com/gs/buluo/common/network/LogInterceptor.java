package com.gs.buluo.common.network;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * Created by hjn on 2017/3/2.
 */
public class LogInterceptor implements okhttp3.Interceptor {
    private String TAG = "TribeNetwork";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long start = System.currentTimeMillis();
        Log.e(TAG, "request url:" + request.url() + " request body is " + (request.body() != null ? bodyToString(request) : null) + "  token is " + request.header("Authorization"));
        Response response = chain.proceed(request);
        String bodyString = "";
        if (response != null && response.body() != null) {
            bodyString = unicodeToUTF_8(response.body().string());
        }
        long end = System.currentTimeMillis();
        Log.e(TAG, "response from url:" + response.request().url() + " ----------in  " + (end - start) +
                "ms \n response code is  " + response.code() + "  and response body is " + bodyString);

        return response.newBuilder().body(ResponseBody.create(MediaType.parse("UTF-8"), bodyString)).build();
    }

    private String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            if (copy.body() == null)
                return "";
            copy.body().writeTo(buffer);
            return getJsonString(buffer.readUtf8());
        } catch (final IOException e) {
            return "{\"err\": \"" + e.getMessage() + "\"}";
        }
    }

    private static String unicodeToUTF_8(String src) {
        if (null == src) {
            return null;
        }
        System.out.println("src: " + src);
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < src.length(); ) {
            char c = src.charAt(i);
            if (i + 6 < src.length() && c == '\\' && src.charAt(i + 1) == 'u') {
                String hex = src.substring(i + 2, i + 6);
                try {
                    out.append((char) Integer.parseInt(hex, 16));
                } catch (NumberFormatException nfe) {
                    nfe.fillInStackTrace();
                }
                i = i + 6;
            } else {
                out.append(src.charAt(i));
                ++i;
            }
        }
        return out.toString();

    }


    static String getJsonString(String msg) {
        String message;
        try {
            if (msg.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(msg);
                message = jsonObject.toString(3);
            } else if (msg.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(msg);
                message = jsonArray.toString(3);
            } else {
                message = msg;
            }
        } catch (JSONException e) {
            message = msg;
        }
        return message;
    }
}
