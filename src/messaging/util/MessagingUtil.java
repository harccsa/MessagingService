package messaging.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.ServletContext;
import javax.servlet.sip.SipFactory;

import org.apache.log4j.Logger;

public class MessagingUtil {
	
	private static Logger log = Logger.getLogger(MessagingUtil.class);
	
	public static final String CALL_SESSION_KEY = "MESSAGING_CALL";
	
	private static ServletContext context;

	private static String BASE_URL;

	private static SipFactory sipFactory;

	private static String vmsSipURI;

	public static String getBaseUrl() {
		return BASE_URL;
	}
	
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
