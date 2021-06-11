package com.drondea.wireless.util;

/**
 * 
 * 
 * EPlatform
 * 
 * @author 张丽阳
 * @createTime 2015年12月24日 上午10:30:09
 * @version 1.0.0
 * 
 */
/**
 * 平台
 * 
 * @author isea533
 */
public enum EPlatform {
	Any("any"), Linux("Linux"), Mac_OS("Mac OS"), Mac_OS_X("Mac OS X"), Windows(
			"Windows"), OS2("OS/2"), Solaris("Solaris"), SunOS("SunOS"), MPEiX(
			"MPE/iX"), HP_UX("HP-UX"), AIX("AIX"), OS390("OS/390"), FreeBSD(
			"FreeBSD"), Irix("Irix"), Digital_Unix("Digital Unix"), NetWare_411(
			"NetWare"), OSF1("OSF1"), OpenVMS("OpenVMS"), Others("Others");

	private EPlatform(String desc) {
		this.description = desc;
	}

	public String toString() {
		return description;
	}

	private String description;
}
