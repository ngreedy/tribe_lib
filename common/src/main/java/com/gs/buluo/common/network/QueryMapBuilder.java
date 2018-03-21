package com.gs.buluo.common.network;

import android.text.TextUtils;
import android.util.Base64;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

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
        if (value == null) value = "";
        sortedMap.put(key, value);
        return builder;
    }

    public QueryMapBuilder putAll(Map map) {
        sortedMap.putAll(map);
        return builder;
    }


    public QueryMapBuilder putObject(Object object) {
        sortedMap.putAll(convertObjToMap(object));
        return builder;
    }


    public Map<String, String> convertObjToMap(Object obj) {
        Map<String, String> reMap = new HashMap<>();
        if (obj == null)
            return null;
        Field[] fields = obj.getClass().getDeclaredFields();
        try {
            for (int i = 0; i < fields.length; i++) {
                try {
                    String fieldName = fields[i].getName();
                    Field f = obj.getClass().getDeclaredField(fieldName);
                    f.setAccessible(true);
                    Object o = f.get(obj);
                    if (o != null && !fieldName.contains("$") && !TextUtils.equals("CREATOR", fieldName) && !TextUtils.equals(fieldName, "serialVersionUID")) {
                        reMap.put(fieldName, o.toString());
                    }
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return reMap;
    }

    public SortedTreeMap<String, String> buildGet() {
        sortedMap.put("timestamp", System.currentTimeMillis() / 1000 + "");
        return sortedMap;
    }

    public SortedTreeMap<String, String> buildPost() {
        sortedMap.put("timestamp", System.currentTimeMillis() / 1000 + "");
        StringBuilder sb = new StringBuilder();
        for (String key : sortedMap.keySet()) {
            sb.append(key).append("=").append(sortedMap.get(key)).append("&");
        }
        String sign = EncryptUtil.getSha1(Base64.encode(sb.substring(0, sb.length() - 1).getBytes(), Base64.NO_WRAP)).toUpperCase();
        sortedMap.put("sign", sign);
        return sortedMap;
    }
}
