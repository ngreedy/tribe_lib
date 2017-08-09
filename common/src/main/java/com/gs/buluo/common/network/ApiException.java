package com.gs.buluo.common.network;

/**
 * Created by hjn on 2017/3/20.
 */

public class ApiException extends RuntimeException {
    private int code;          //http 返回码
    private String displayMessage;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ApiException(int code, String message, String type) {
        this.code = code;
        displayMessage = message;
        this.type = type;
    }

    public void setCode(int code) {
        this.code = code;
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
