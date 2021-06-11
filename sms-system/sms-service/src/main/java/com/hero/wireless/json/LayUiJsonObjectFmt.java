package com.hero.wireless.json;

import java.util.Map;

public class LayUiJsonObjectFmt {
    private String code;
    private String msg;
    private String url; //拦截后要跳转的url，2019/12/26
    private Object data;
    private Map<String ,Object> map;

    public LayUiJsonObjectFmt() {
    }

    public LayUiJsonObjectFmt(String code, String msg, Object data) {
        super();
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    public LayUiJsonObjectFmt(String code, String msg, Object data, Map<String ,Object> map) {
        super();
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.map = map;
    }

    public LayUiJsonObjectFmt(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
