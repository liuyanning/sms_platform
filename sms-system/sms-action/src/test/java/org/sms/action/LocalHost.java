package org.sms.action;

import java.net.InetAddress;

public class LocalHost {
	public static void main(String[] args) throws Exception {
		System.out.println(InetAddress.getLocalHost().getHostName());
	}
}
