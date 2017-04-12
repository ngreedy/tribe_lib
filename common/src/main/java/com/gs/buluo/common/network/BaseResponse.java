package com.gs.buluo.common.network;

/**
 * Created by hjn on 2016/11/23.
 */
public class BaseResponse<T extends BaseCode> {
    public int code;
    public T data;
    public String message;

    public boolean isCodeInvalid() {
        return code>300;
    }
}
