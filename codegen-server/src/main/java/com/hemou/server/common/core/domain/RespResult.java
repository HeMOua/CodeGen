package com.hemou.server.common.core.domain;

import com.hemou.server.common.constants.Constants;

import java.util.HashMap;

public class RespResult extends HashMap<String, Object> {

    public static final String CODE_TAG = "code";
    public static final String MSG_TAG = "msg";
    public static final String DATA_TAG = "data";

    public RespResult(int code, String msg, Object data) {
        put(CODE_TAG, code);
        put(MSG_TAG, msg);
        put(DATA_TAG, data);
    }

    public static RespResult success(String msg, Object data) {
        return new RespResult(Constants.SUCCESS, msg, data);
    }

    public static RespResult success(Object data) {
        return success("操作成功", data);
    }

    public static RespResult success() {
        return success("操作成功", null);
    }

    public static RespResult error(String msg, Object data) {
        return new RespResult(Constants.ERROR, msg, data);
    }

    public static RespResult error(String msg) {
        return error(msg, null);
    }

    public static RespResult error() {
        return error("操作失败");
    }
}
