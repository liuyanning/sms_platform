package com.hero.wireless.web.entity.business.ext;

import com.hero.wireless.web.entity.business.MmsTemplate;

public class MmsTemplateExt extends MmsTemplate {
    private String mmsTitle;
    private String fileMap;
    private String content;
    private String format;
    private String fileType;
    private String url;
    private Integer fileSize;
    private MmsTemplate mmsTemplate;

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    public MmsTemplate getMmsTemplate() {
        return mmsTemplate;
    }

    public void setMmsTemplate(MmsTemplate mmsTemplate) {
        this.mmsTemplate = mmsTemplate;
    }


    public String getMmsTitle() {
        return mmsTitle;
    }

    public void setMmsTitle(String mmsTitle) {
        this.mmsTitle = mmsTitle;
    }

    public String getFileMap() {
        return fileMap;
    }

    public void setFileMap(String fileMap) {
        this.fileMap = fileMap;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
