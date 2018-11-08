package com.quarkdata.data.util;

import java.net.*;
import java.util.Enumeration;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HostUtils {
	
	protected static Logger logger = LoggerFactory.getLogger(HostUtils.class);
	
	//获取本机ip地址
    @SuppressWarnings("rawtypes")
	public static String localIp(){
         String ip = null;
         Enumeration allNetInterfaces;
         try {
             allNetInterfaces = NetworkInterface.getNetworkInterfaces();            
             while (allNetInterfaces.hasMoreElements()) {
                 NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                 List<InterfaceAddress> InterfaceAddress = netInterface.getInterfaceAddresses();
                 for (InterfaceAddress add : InterfaceAddress) {
                     InetAddress Ip = add.getAddress();
                     if (Ip != null && Ip instanceof Inet4Address) {
                         ip = Ip.getHostAddress();
                     }
                 }
             }
         } catch (SocketException e) {
        	 e.printStackTrace();
        	 logger.warn("获取本机Ip失败:异常信息:"+e.getMessage());
         }
         return ip;
     }

    public static void main(String[] args) throws UnknownHostException {
        String ip = HostUtils.localIp();
        System.out.println(ip);

        InetAddress ia = InetAddress.getLocalHost();
        System.out.println(ia.getHostAddress());
    }
}
