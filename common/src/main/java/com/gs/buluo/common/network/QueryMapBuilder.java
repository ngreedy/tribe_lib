package com.gs.buluo.common.network;

import android.util.Base64;

/**
 * Created by hjn91 on 2018/1/30.
 */

public class QueryMapBuilder {
    private static QueryMapBuilder builder;
    private static SortedTreeMap<String, String> sortedMap;

    private QueryMapBuilder() {
    }

    public static QueryMapBuilder getIns() {
        if (builder == null) {
            builder = new QueryMapBuilder();
        }
        sortedMap = new SortedTreeMap<>();
        return builder;
    }

    public QueryMapBuilder put(String key, String value) {
        sortedMap.put(key, value);
        return builder;
    }

    public SortedTreeMap<String, String> buildGet() {
        sortedMap.put("timestamp", System.currentTimeMillis()/1000 + "");
        return sortedMap;
    }

    public SortedTreeMap<String, String> buildPost() {
        sortedMap.put("timestamp", System.currentTimeMillis()/1000 + "");
        StringBuilder sb = new StringBuilder();
        for (String key : sortedMap.keySet()) {
            sb.append(key).append("=").append(sortedMap.get(key)).append("&");
        }
        String sign = EncryptUtil.getSha1(Base64.encode(sb.substring(0, sb.length() - 1).getBytes(), Base64.NO_WRAP)).toUpperCase();
        sortedMap.put("sign", sign);
        return sortedMap;
    }
}
