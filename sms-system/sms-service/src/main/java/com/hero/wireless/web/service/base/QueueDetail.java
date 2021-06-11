package com.hero.wireless.web.service.base;
public class QueueDetail {

	private String topicName;
	private String groupName;
	private QueueDataDetail queueDataDetail;

	public String getTopicName() {
		return topicName;
	}
	public QueueDataDetail getQueueDataDetail() {
		return queueDataDetail;
	}
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	public void setQueueDataDetail(QueueDataDetail queueDataDetail) {
		this.queueDataDetail = queueDataDetail;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
}