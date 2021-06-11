package com.hero.wireless.web.service.param;


import com.drondea.wireless.util.DateTime;

import org.apache.commons.lang.StringUtils;

import java.util.Date;

public class TaskParam {

	private String taskName;//任务名称
	private String date;//执行日期：
	private String database;//要操作的数据库：s_history、s_send
	private String table;//要操作的表：input_log、report、submit

    public Date getMinDate() {
        return StringUtils.isEmpty(date) ? null : DateTime.getDate(date+" 00:00:00");
    }

    public Date getMaxDate() {
        return StringUtils.isEmpty(date) ? null : DateTime.getDate(date+" 23:59:59");
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }
}
