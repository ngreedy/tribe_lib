package com.gs.buluo.common.network;

/**
 * Created by hjn on 2017/3/20.
 */

public class ApiException extends RuntimeException {
    private  int code;
    private String displayMessage;

    public ApiException(int code, String message) {
        this.code = code;
        displayMessage = message;
    }

    public int getCode() {
        return code;
    }
    public String getDisplayMessage() {
        return displayMessage;
    }
    public void setDisplayMessage(String msg) {
        this.displayMessage = msg + "(code:" + code + ")";
    }
}
