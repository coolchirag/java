package com.chirag.licensedemo.config;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chirag.licensedemo.config.dto.LicenseCheckModel;
import com.chirag.licensedemo.util.StringUtils;

public abstract class AbstractServerInfos {
	private static Logger logger = LogManager.getLogger(AbstractServerInfos.class);

	public LicenseCheckModel getServerInfos() {
		LicenseCheckModel result = new LicenseCheckModel();

		try {
			result.setIpAddress(this.getIpAddress());
			result.setMacAddress(this.getMacAddress());

			//result.setCpuSerial(this.getCPUSerial());
			//result.setMainBoardSerial(this.getMainBoardSerial());

		} catch (Exception e) {
			logger.error("Failed to get server hardware information", e);
		}

		return result;
	}

	/**
	 * Get IP address
	 * 
	 * @author zifangsky
	 * @date 2018/4/23 11:32
	 * @since 1.0.0
	 * @return java.util.List<java.lang.String>
	 */
	protected abstract List<String> getIpAddress() throws Exception;

	/**
	 * Get Mac address
	 * 
	 * @author zifangsky
	 * @date 2018/4/23 11:32
	 * @since 1.0.0
	 * @return java.util.List<java.lang.String>
	 */
	protected abstract List<String> getMacAddress() throws Exception;

	/**
	 * Get CPU serial number
	 * 
	 * @author zifangsky
	 * @date 2018/4/23 11:35
	 * @since 1.0.0
	 * @return java.lang.String
	 */
	protected abstract String getCPUSerial() throws Exception;

	/**
	 * bios id
	 * 
	 * @author zifangsky
	 * @date 2018/4/23 11:35
	 * @since 1.0.0
	 * @return java.lang.String
	 */
	protected abstract String getMainBoardSerial() throws Exception;

	/**
	 * Get all the qualified InetAddress of the current server
	 * 
	 * @author zifangsky
	 * @date 2018/4/23 17:38
	 * @since 1.0.0
	 * @return java.util.List<java.net.InetAddress>
	 */
	protected List<InetAddress> getLocalAllInetAddress() throws Exception {
		List<InetAddress> result = new ArrayList<>(4);

		// Traverse all network interfaces
		for (Enumeration networkInterfaces = NetworkInterface.getNetworkInterfaces(); networkInterfaces
				.hasMoreElements();) {
			NetworkInterface iface = (NetworkInterface) networkInterfaces.nextElement();
			// Traverse IP again under all interfaces
			for (Enumeration inetAddresses = iface.getInetAddresses(); inetAddresses.hasMoreElements();) {
				InetAddress inetAddr = (InetAddress) inetAddresses.nextElement();

				// Exclude IP addresses of type LoopbackAddress, SiteLocalAddress,
				// LinkLocalAddress, MulticastAddress
				if (!inetAddr.isLoopbackAddress() /* && !inetAddr.isSiteLocalAddress() */
						&& !inetAddr.isLinkLocalAddress() && !inetAddr.isMulticastAddress()) {
					result.add(inetAddr);
				}
			}
		}

		return result;
	}

	/**
	 * Get the Mac address of a network interface
	 * 
	 * @author zifangsky
	 * @date 2018/4/23 18:08
	 * @since 1.0.0
	 * @param
	 * @return void
	 */
	protected String getMacByInetAddress(InetAddress inetAddr) {
		try {
			byte[] mac = NetworkInterface.getByInetAddress(inetAddr).getHardwareAddress();
			StringBuffer stringBuffer = new StringBuffer();
			if (mac != null) {
				for (int i = 0; i < mac.length; i++) {
					if (i != 0) {
						stringBuffer.append("-");
					}

					// Convert hex byte to string
					String temp = Integer.toHexString(mac[i] & 0xff);
					if (temp.length() == 1) {
						stringBuffer.append("0" + temp);
					} else {
						stringBuffer.append(temp);
					}
				}
			}

			return stringBuffer.toString().toUpperCase();
		} catch (SocketException e) {
			e.printStackTrace();
		}

		return null;
	}

}
