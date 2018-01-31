package com.gs.buluo.common.network;

import java.util.Comparator;
import java.util.TreeMap;

/**
 * Created by hjn91 on 2018/1/30.
 */

public class SortedTreeMap<K,V> extends TreeMap<K,V> {
    @Override
    public Comparator comparator() {
        return new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                if (EncryptUtil.isMoreThan(o1.toString(), o2.toString()))
                    return 1;
                else
                    return -1;
            }
        };
    }
}
