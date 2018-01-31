package com.gs.buluo.common.network;

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

    public SortedTreeMap<String, String> build() {
        sortedMap.put("timestamp",System.currentTimeMillis() + "");
        return sortedMap;
    }
}
