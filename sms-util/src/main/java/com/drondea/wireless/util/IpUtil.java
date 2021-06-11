package com.drondea.wireless.util;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @version V3.0.0
 * @description: ip获取工具类
 * @author: 刘彦宁
 * @date: 2020年08月06日11:06
 **/
public class IpUtil {

    public static String getLocalIp() {
        List<String> ipList = new ArrayList<>();
        Enumeration<NetworkInterface> networkInterfaces;
        try {
            networkInterfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
            return "127.0.0.1";
        }
        List<String> ipEth0 = new ArrayList<>();
        //优先eth0
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            String name = networkInterface.getName();
            Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
            while (inetAddresses.hasMoreElements()) {
                InetAddress inetAddress = inetAddresses.nextElement();
                //回路地址，如127.0.0.1
                if (inetAddress.isLoopbackAddress()) {
                    continue;
                } else if (inetAddress.isLinkLocalAddress()) {
                    //169.254.x.x
                    continue;
                } else {
                    String localip = inetAddress.getHostAddress();
                    //非链接和回路真实ip
                    if (name.startsWith("eth")) {
                        ipEth0.add(localip);
                    } else {
                        ipList.add(localip);
                    }
                }
            }
        }
        //先拿eth开头的
        if (ipEth0.size() > 0) {
            return ipEth0.get(0);
        }
        //非eth开头网卡地址
        if (ipList.size() > 0) {
            return ipList.get(0);
        }
        return "127.0.0.1";
    }

    /**
     * 将 ip 字符串转换为 int 类型的数字
     * <p>
     * 思路就是将 ip 的每一段数字转为 8 位二进制数，并将它们放在结果的适当位置上
     *
     * @param ipString ip字符串，如 127.0.0.1
     * @return ip字符串对应的 int 值
     */
    public static Integer ip2Int(String ipString) {
        try {
            // 取 ip 的各段
            String[] ipSlices = ipString.split("\\.");
            int rs = 0;
            for (int i = 0; i < ipSlices.length; i++) {
                // 将 ip 的每一段解析为 int，并根据位置左移 8 位
                int intSlice = Integer.parseInt(ipSlices[i]) << 8 * i;
                // 求与
                rs = rs | intSlice;
            }
            return rs;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将 int 转换为 ip 字符串
     *
     * @param ipInt 用 int 表示的 ip 值
     * @return ip字符串，如 127.0.0.1
     */
    public static String int2Ip(int ipInt) {
        String[] ipString = new String[4];
        for (int i = 0; i < 4; i++) {
            // 每 8 位为一段，这里取当前要处理的最高位的位置
            int pos = i * 8;
            // 取当前处理的 ip 段的值
            int and = ipInt & (255 << pos);
            // 将当前 ip 段转换为 0 ~ 255 的数字，注意这里必须使用无符号右移
            ipString[i] = String.valueOf(and >>> pos);
        }
        return String.join(".", ipString);
    }

    private static final String[] HEADERS_TO_TRY = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR",
            "X-Real-IP"};


    /**
     * 获取用户真实IP地址，不使用IpUtil.getRemoteIpAddr(request);的原因是有可能用户使用了代理软件方式避免真实IP地址,
     *
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
     *
     * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
     * 192.168.1.100
     *
     * 用户真实IP为： 192.168.1.110
     *
     * @param request
     * @return
     */
    public static String getRemoteIpAddr(HttpServletRequest request) {
        //先从header里面获取
        for (String header : HEADERS_TO_TRY) {
            String ip = request.getHeader(header);
            if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                return getFirstIp(ip);
            }
        }
        return request.getRemoteAddr();
    }

    /**
     * 获取第一个非unknown的ip
     * @param ip
     * @return
     */
    private static String getFirstIp(String ip) {

        final String[] arr = ip.split(",");
        for (final String str : arr) {
            if (!"unknown".equalsIgnoreCase(str)) {
                return ip;
            }
        }
        return ip;
    }

//    public static void main(String[] args) {
//        String[] ips4Test = new String[]{"106.113.124.176"};
//        for (String ip : ips4Test) {
//            test(ip);
//        }

//        int ip = -1334021782;
//        System.out.println(int2Ip(ip));
//    }

    public static void test(String ip) {
        int ipInt = ip2Int(ip);
        String ipString = int2Ip(ipInt);
        System.out.println("用于测试的ip地址: " + ip + ", int表示: " + ipInt + ", 二进制: " + Long.toBinaryString(ipInt)
                + ", 转回String: " + ipString + "，与测试 ip 地址是否相等: " + ip.equals(ipString));
    }
}
