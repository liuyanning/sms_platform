package com.hero.wireless.web.action.entity;

import com.hero.wireless.web.entity.business.ext.CodeExt;

import java.util.List;

public class BaseParamEntity {
    private List<Integer> ckIds;

    private List<Long> ckLongs;

    private List<String> ckStrings;

    private List<Integer> ckRoleId;

    private List<Integer> ckLimitId;

    private CodeExt code;

    private String flag;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public List<Integer> getCkIds() {
        return ckIds;
    }

    public void setCkIds(List<Integer> ckIds) {
        this.ckIds = ckIds;
    }

    public List<Integer> getCkRoleId() {
        return ckRoleId;
    }

    public void setCkRoleId(List<Integer> ckRoleId) {
        this.ckRoleId = ckRoleId;
    }

    public List<Integer> getCkLimitId() {
        return ckLimitId;
    }

    public void setCkLimitId(List<Integer> ckLimitId) {
        this.ckLimitId = ckLimitId;
    }

    public CodeExt getCode() {
        return code;
    }

    public void setCode(CodeExt code) {
        this.code = code;
    }

    public List<String> getCkStrings() {
        return ckStrings;
    }

    public void setCkStrings(List<String> ckStrings) {
        this.ckStrings = ckStrings;
    }

    public List<Long> getCkLongs() {
        return ckLongs;
    }

    public void setCkLongs(List<Long> ckLongs) {
        this.ckLongs = ckLongs;
    }
}
