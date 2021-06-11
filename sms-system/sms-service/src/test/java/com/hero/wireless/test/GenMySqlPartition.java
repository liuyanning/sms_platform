package com.hero.wireless.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 
 * @author zly
 * 创建表分区
 */
public class GenMySqlPartition {
	public static void main(String[] args) {
		Calendar startDate = Calendar.getInstance();
		
		SimpleDateFormat partitionValueFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat partitionNameFormat = new SimpleDateFormat("yyyyMMdd");
		for (int i = 0; i < 1095; i++) {
			startDate.add(Calendar.DATE, 1);
			System.out.println("partition p" + partitionNameFormat.format(startDate.getTime())
					+ " values less than (to_days('" + partitionValueFormat.format(startDate.getTime()) + "')),");
		}
	}
}