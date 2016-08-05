package org.huzhu.result;

import com.alibaba.fastjson.JSON;

/**
 * Created by jihui2 on 2015/3/12.
 */
public class Result {
    public int code = 0;
    public Object data;
    public Object debug = null;
    public Result(int code, Object data) {
        this.code = code;
        this.data = data;
    }
    public Result(int code, Object data, Object debug) {
        this.code = code;
        this.data = data;
        this.debug = debug;
    }
    public Result(Object data) {
        this.data = data;
    }
    public int getCode() {
        return code;
    }
    public Object getData() {
        return data;
    }
    public String toString() {
        return JSON.toJSONString(this);
    }
    public Object getDebug() {
        return debug;
    }
    public Result setDebug(Object debug) {
        this.debug = debug;
        return this;
    }
}
