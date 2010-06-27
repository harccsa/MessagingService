package hu.messaging.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

public class MessageUtil {
	
	private static Logger log = Logger.getLogger(MessageUtil.class);
	
	public static String getLocalIPAddress() {
		InetAddress internetAddress = null;
		try {
			internetAddress = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// Default to loopback
			return "127.0.0.1";
		}
		StringBuffer address = new StringBuffer();
		byte[] bytes = internetAddress.getAddress();
		for (int j = 0; j < bytes.length; j++) {
			int i = bytes[j] < 0 ? bytes[j] + 256 : bytes[j];
			address.append(i);
			if (j < 3)
				address.append('.');
		}
		
		log.info("getLocalIp return: " + address.toString());
		return address.toString();
	
	}
}
