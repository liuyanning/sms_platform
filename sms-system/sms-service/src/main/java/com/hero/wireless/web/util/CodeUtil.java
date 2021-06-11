package com.hero.wireless.web.util;

import org.apache.commons.lang3.StringUtils;

import java.lang.management.ManagementFactory;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 编号生成工具类
 * 
 * @date 2019-09-11
 * @author zly
 */
public abstract class CodeUtil {
	// 自增序列
	private static final AtomicInteger INCREMENT_SEQUENCE = new AtomicInteger(1);

	private static int ProcessID = 1010;

	static{
		final String propertiesName = "sms.id";
		String value = null;
		//解决在docker中运行，进程号都一样的问题
		try {
			if (System.getSecurityManager() == null) {
				value = System.getProperty(propertiesName);
			} else {
				value = AccessController.doPrivileged(new PrivilegedAction<String>() {
					@Override
					public String run() {
						return System.getProperty(propertiesName);
					}
				});
			}
		} catch (SecurityException e) {
		}
		//没有配置gateid就取程序进程号
		if(StringUtils.isBlank(value)) {
			String vmName = ManagementFactory.getRuntimeMXBean().getName();
			if(vmName.contains("@")){
				value =vmName.split("@")[0];
			}
		}

		try{
			ProcessID = Integer.valueOf(value);
		}catch(Exception e){

		}

	}

	/**
	 * 构建MsgNo TODO 获取系统时间可能会存在性能问题
	 * 
	 * @return
	 */
	public static String buildMsgNo() {
		return buildNoByTime();
	}

	/**
	 * 构建企业编号
	 * 
	 * @return
	 */
	public static String buildEnterpriseNo() {
		return buildNoByTime();
	}

	public static String buildAgentNo() {
		return buildNoByTime();
	}

	public static String buildProductNo() {
		return buildNoByTime();
	}

	public static String buildNo() {
		return buildNoByTime();
	}

	/**
	 * 移除随机参数
	 * 
	 * @param randomRange
	 * @return
	 */
	@Deprecated
	public static String buildNoByTime(long randomRange) {
		return buildNo();
	}

	public static String buildNoByTime() {
		//纳秒级别  分布式部署出现重复的概率也很低，再加上序列，概率更低
		//如何想彻底解决这个问题，需要机器编码
		long number = Long.reverse(System.nanoTime());
		int seq = INCREMENT_SEQUENCE.updateAndGet((c) -> {
			// 同步检查
			if (c >= 0xFF) {
				return 1;
			}
			return c + 1;
		});
		number = number + seq;
		return ProcessID + Long.toHexString(number).toUpperCase();
	}

	public static String buildProtocolNo(String protocol) {
		return protocol.toUpperCase() + buildNoByTime(1000);
	}

	public static void main(String[] args) throws Exception {

//		Set<String> numbers = Collections.synchronizedSet(new HashSet<>());
//		new Thread(() -> {
//			for (int i = 0; i < 100000; i++) {
//				numbers.add(buildNoByTime(0));
//			}
//			System.out.println(numbers.size());
//		}).start();
//		new Thread(() -> {
//			for (int i = 0; i < 100000; i++) {
//				numbers.add(buildNoByTime(0));
//			}
//			System.out.println(numbers.size());
//		}).start();
//		new Thread(() -> {
//			for (int i = 0; i < 100000; i++) {
//				numbers.add(buildNoByTime(0));
//			}
//			System.out.println(numbers.size());
//		}).start();
		for (int i = 0; i < 10; i++) {
			//System.out.println(buildNoByTime(0));
			//System.out.println(buildNoByTime(0));
			System.out.println(System.nanoTime());
		}
	}
}
