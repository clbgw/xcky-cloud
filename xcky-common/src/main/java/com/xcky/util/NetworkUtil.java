package com.xcky.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * 网络工具类
 *
 * @author lbchen
 */
@Slf4j
public class NetworkUtil {
    /**
     * 获取本机的内网ip地址
     *
     * @return IP地址字符串
     */
    public static String getInnetIp() {
        //本地IP，如果没有配置外网IP则返回它
        String localip = null;
        // 外网IP
        String netip = null;
        Enumeration<NetworkInterface> netInterfaces;
        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip;
            // 是否找到外网IP
            boolean finded = false;
            while (netInterfaces.hasMoreElements() && !finded) {
                NetworkInterface ni = netInterfaces.nextElement();
                Enumeration<InetAddress> address = ni.getInetAddresses();
                while (address.hasMoreElements()) {
                    ip = address.nextElement();
                    // 外网IP
                    if (!ip.isSiteLocalAddress()
                            && !ip.isLoopbackAddress()
                            && ip.getHostAddress().indexOf(":") == -1) {
                        netip = ip.getHostAddress();
                        finded = true;
                        break;
                    }
                    // 内网IP
                    else if (ip.isSiteLocalAddress()
                            && !ip.isLoopbackAddress()
                            && ip.getHostAddress().indexOf(":") == -1) {
                        localip = ip.getHostAddress();
                    }
                }
            }
            return !StringUtils.isEmpty(netip) ? netip : localip;
        } catch (SocketException e) {
            log.error(e.getMessage());
        }
        return "8.8.8.8";
    }

    /**
     * 获取请求远程IP
     *
     * @param request HttpServletRequest请求对象
     * @return 请求远程IP
     */
    public static String getRemoteIp(HttpServletRequest request) {
        String ipAddress = "";
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (StringUtils.isEmpty(ipAddress) || Constants.UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ipAddress) || Constants.UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ipAddress) || Constants.UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (Constants.LOCAL_IP.equals(ipAddress)) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = InetAddress.getLocalHost();
                    ipAddress = inet.getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割,每个IP的最大长度是15
            if (ipAddress != null && ipAddress.length() > Constants.FIF_TEEN) {
                if (ipAddress.indexOf(Constants.COMMA) > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(Constants.COMMA));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ipAddress;
    }
}
