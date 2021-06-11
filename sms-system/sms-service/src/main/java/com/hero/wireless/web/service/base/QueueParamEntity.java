package com.hero.wireless.web.service.base;
public class QueueParamEntity {

	private String topicName;
	private String groupTag;//页面分类tag：发送、用户、other等
	private String platformTag;//平台tag:admin、sorter、submit等

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getGroupTag() {
        return groupTag;
    }

    public void setGroupTag(String groupTag) {
        this.groupTag = groupTag;
    }

    public String getPlatformTag() {
        return platformTag;
    }

    public void setPlatformTag(String platformTag) {
        this.platformTag = platformTag;
    }
}