package com.gs.buluo.common.network;

/**
 * Created by hjn on 2016/11/23.
 */
public class BaseResponse<T>{
    public int err_code;
    public T data;
    public String err_msg;

    public boolean isCodeInvalid() {
        return err_code >300;
    }
}
